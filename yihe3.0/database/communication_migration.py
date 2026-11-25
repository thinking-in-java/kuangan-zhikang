"""Install V016 communication tables without changing the legacy chat tables or business rows."""
import argparse
import hashlib
import json
import re
from pathlib import Path
from health_archive_db import connect

ROOT = Path(__file__).resolve().parents[1]
SQL = ROOT / 'database/migrations/V016__a_communication_service.sql'
VERSION = 'V016_a_communication_service'

def apply():
    source = SQL.read_bytes()
    checksum = hashlib.sha256(source).hexdigest()
    sql = re.sub(r'^\s*--.*$', '', source.decode('utf-8'), flags=re.M)
    statements = [s.strip() for s in sql.split(';') if s.strip()]
    tables = []
    for statement in statements:
        m = re.match(r'CREATE TABLE (a_service_\w+)\s*\(', statement)
        if not m:
            raise RuntimeError('Migration must only create a_service_ tables')
        tables.append(m[1])
    if len(tables) != 5 or len(set(tables)) != 5:
        raise RuntimeError('Unexpected V016 table inventory')
    con = connect()
    created = []
    try:
        with con.cursor() as q:
            q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) AS acquired")
            if q.fetchone()['acquired'] != 1:
                raise RuntimeError('Schema migration lock unavailable')
            q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s', (VERSION,))
            prior = q.fetchone()
            if prior and prior['checksum'] != checksum:
                raise RuntimeError('V016 checksum mismatch; refusing to overwrite')
            for table, statement in zip(tables, statements):
                q.execute('SELECT COUNT(*) n FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name=%s', (table,))
                exists = q.fetchone()['n'] != 0
                if exists and not prior:
                    raise RuntimeError('Unversioned table exists: ' + table)
                if not exists:
                    q.execute(statement)
                    created.append(table)
            if not prior:
                q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())', (VERSION, checksum, checksum, 'Employee service conversations, messages, reads, attachments and AI drafts'))
            con.commit()
            q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
            return {'version': VERSION, 'created': created, 'tables': tables, 'checksum': checksum}
    finally:
        con.close()

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--apply', action='store_true', required=True)
    parser.parse_args()
    print(json.dumps(apply(), ensure_ascii=False, indent=2))
