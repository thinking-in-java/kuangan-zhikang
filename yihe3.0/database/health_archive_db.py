"""Create/verify only a_ archive tables. Credentials come from existing project config.

Run --validate first; --apply repeats isolated validation before applying to the project DB.
DDL is not transactionally rollbackable. Existing unversioned/conflicting tables abort the run.
"""
import argparse
import hashlib
import json
import re
import time
import uuid
from datetime import datetime
from pathlib import Path
import pymysql
import yaml

ROOT = Path(__file__).resolve().parents[1]
SQL = ROOT / 'database/migrations/V001__a_ehr_v2_0_0.sql'
VERSION = 'V001_a_ehr_v2_0_0'
from ehr_schema_checks import smoke, seed_checks

def configuration():
    c = yaml.safe_load((ROOT/'yihe-admin/src/main/resources/application-druid.yml').read_text(encoding='utf-8'))['spring']['datasource']['druid']['master']
    m = re.fullmatch(r'jdbc:mysql://([^:/]+)(?::(\d+))?/([^?]+)(?:\?.*)?', c['url'])
    if not m:
        raise ValueError('Unrecognized JDBC URL')
    return dict(host=m[1], port=int(m[2] or 3306), database=m[3], user=c['username'], password=str(c['password']))

def connect(database=None,read_timeout=30):
    c = configuration()
    if database is not None:
        c['database'] = database
    return pymysql.connect(**c,charset='utf8mb4',connect_timeout=8,read_timeout=read_timeout,write_timeout=read_timeout,autocommit=False,init_command="SET time_zone = '+00:00'",cursorclass=pymysql.cursors.DictCursor)

def statements():
    # This migration uses standard quoted literals, no procedures or DELIMITER directives.
    source = SQL.read_text(encoding='utf-8')
    result, part, quote, comment = [], [], None, False
    i = 0
    delimiter = ';'
    while i < len(source):
        ch = source[i]
        if comment:
            if ch == '\n':
                comment = False
                part.append('\n')
        elif quote:
            part.append(ch)
            if ch == '\\' and i+1 < len(source):
                i += 1
                part.append(source[i])
            elif ch == quote:
                if i+1 < len(source) and source[i+1] == quote:
                    i += 1
                    part.append(source[i])
                else:
                    quote = None
        elif (i == 0 or source[i-1] == '\n') and source.startswith('DELIMITER ',i):
            end = source.find('\n',i)
            if end == -1:
                end = len(source)
            if ''.join(part).strip():
                raise ValueError('DELIMITER inside an unfinished statement')
            delimiter = source[i+10:end].strip()
            if delimiter not in [';','$$']:
                raise ValueError('Unsupported delimiter')
            i = end
        elif source[i:i+2] == '--':
            comment = True
            i += 1
        elif ch in "'\"`":
            quote = ch
            part.append(ch)
        elif source.startswith(delimiter,i):
            if ''.join(part).strip():
                result.append(''.join(part).strip())
            part = []
            i += len(delimiter)-1
        else:
            part.append(ch)
        i += 1
    if quote or ''.join(part).strip():
        raise ValueError('Unclosed literal or unterminated SQL statement')
    tables = []
    for stmt in result:
        m = re.match(r'CREATE TABLE (a_[a-z0-9_]+)\s*\(',stmt)
        if m:
            tables.append(m[1])
        elif not re.match(r'(?:ALTER TABLE|INSERT INTO) a_[a-z0-9_]+\b',stmt) and not re.match(r'CREATE TRIGGER a_[a-z0-9_]+ BEFORE (?:INSERT|UPDATE|DELETE) ON a_[a-z0-9_]+ FOR EACH ROW',stmt):
            raise ValueError('Unexpected statement; all writes must target new a_ tables')
    if len(tables) != len(set(tables)):
        raise ValueError('Duplicate CREATE table')
    for stmt in result:
        m = re.match(r'(?:ALTER TABLE|INSERT INTO) (a_[a-z0-9_]+)\b',stmt)
        if m and m[1] not in tables:
            raise ValueError('Mutation targets a table outside this migration')
        m = re.match(r'CREATE TRIGGER \w+ BEFORE \w+ ON (\w+)',stmt)
        if m and m[1] not in tables:
            raise ValueError('Trigger targets a table outside this migration')
    return result, tables

def table_names(c):
    with c.cursor() as q:
        q.execute('SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE()')
        return {r['TABLE_NAME'] for r in q.fetchall()}

def schema_hashes(c,tables):
    result = {}
    with c.cursor() as q:
        for name in tables:
            q.execute('SHOW CREATE TABLE `'+name+'`')
            ddl = q.fetchone()['Create Table']
            ddl = re.sub(r' AUTO_INCREMENT=\d+','',ddl)
            result[name] = hashlib.sha256(ddl.encode()).hexdigest()
    return result

def trigger_hashes(c):
    with c.cursor() as q:
        q.execute("SELECT TRIGGER_NAME,EVENT_MANIPULATION,EVENT_OBJECT_TABLE,ACTION_TIMING,ACTION_STATEMENT FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA=DATABASE() AND LEFT(TRIGGER_NAME,6)='a_ehr_' ORDER BY TRIGGER_NAME")
        return {r['TRIGGER_NAME']:hashlib.sha256(json.dumps(r,sort_keys=True).encode()).hexdigest() for r in q.fetchall()}

def legacy_fingerprint(c):
    # Batch metadata reads; avoid hundreds of round trips to the LAN server.
    result={}
    with c.cursor() as q:
        for label,sql in {
            'columns':"SELECT TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_TYPE,CHARACTER_SET_NAME,COLLATION_NAME,EXTRA,COLUMN_COMMENT,GENERATION_EXPRESSION FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND LEFT(TABLE_NAME,2)<>'a_' ORDER BY TABLE_NAME,ORDINAL_POSITION",
            'indexes':"SELECT TABLE_NAME,INDEX_NAME,NON_UNIQUE,SEQ_IN_INDEX,COLUMN_NAME,COLLATION,SUB_PART,INDEX_TYPE,EXPRESSION FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND LEFT(TABLE_NAME,2)<>'a_' ORDER BY TABLE_NAME,INDEX_NAME,SEQ_IN_INDEX",
            'constraints':"SELECT TABLE_NAME,CONSTRAINT_NAME,CONSTRAINT_TYPE FROM information_schema.TABLE_CONSTRAINTS WHERE TABLE_SCHEMA=DATABASE() AND LEFT(TABLE_NAME,2)<>'a_' ORDER BY TABLE_NAME,CONSTRAINT_NAME",
            'tables':"SELECT TABLE_NAME,TABLE_TYPE,ENGINE,TABLE_COLLATION,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND LEFT(TABLE_NAME,2)<>'a_' ORDER BY TABLE_NAME",
        }.items():
            q.execute(sql)
            result[label]=hashlib.sha256(json.dumps(q.fetchall(),sort_keys=True,default=str).encode()).hexdigest()
    return result

def create(c):
    sql,tables = statements()
    with c.cursor() as q:
        for stmt in sql:
            name = re.match(r'(?:CREATE TABLE|ALTER TABLE|INSERT INTO|CREATE TRIGGER) (\w+)',stmt)[1]
            q.execute(stmt)
            if stmt.startswith('CREATE TABLE'):
                print('Created',name,flush=True)
    return tables

def isolated_validation():
    # Only this fresh, random, locally tracked validation DB may be removed below.
    name = 'a_health_schema_check_'+uuid.uuid4().hex[:16]
    admin = connect()
    owned = False
    try:
        with admin.cursor() as q:
            q.execute('CREATE DATABASE `'+name+'` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci')
            owned = True
        c = connect(name)
        try:
            tables = create(c)
            c.commit()
            checks = seed_checks(c) + smoke(c)
            hashes = schema_hashes(c,tables)
            print('Isolated validation passed:',len(checks),'checks',flush=True)
            return tables,hashes,trigger_hashes(c),checks
        finally:
            c.close()
    finally:
        if owned and re.fullmatch(r'a_health_schema_check_[a-f0-9]{16}',name):
            with admin.cursor() as q:
                q.execute('DROP DATABASE `'+name+'`')
            print('Removed owned isolated validation database',flush=True)
        admin.close()

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--apply',action='store_true')
    parser.add_argument('--validate',action='store_true')
    parser.add_argument('--recover-verified',action='store_true',help='Register a partial run only after exact structure/trigger/seed comparison and zero business rows')
    parser.add_argument('--report',type=Path,default=ROOT/'database/health_archive_verification.json')
    args = parser.parse_args()
    if not (args.apply or args.validate):
        parser.error('Choose --validate or --apply')
    if args.recover_verified and not args.apply:
        parser.error('--recover-verified requires --apply')
    started = time.monotonic()
    tables,expected,expected_triggers,checks = isolated_validation()
    digest = hashlib.sha256(SQL.read_bytes()).hexdigest()
    result = {'version':VERSION,'sql_sha256':digest,'validation_checks':checks,'tables':tables,'applied':False}
    evidence = dict(result,expected_table_hashes=expected,expected_trigger_hashes=expected_triggers,verified_at=datetime.now().astimezone().isoformat())
    cache = ROOT/'database/ehr-isolated-verification.json'
    cache.write_text(json.dumps(evidence,ensure_ascii=False,indent=2),encoding='utf-8')
    if args.apply:
        c = connect()
        locked = False
        try:
            with c.cursor() as q:
                q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) AS locked")
                locked = q.fetchone()['locked'] == 1
                if not locked:
                    raise RuntimeError('Schema migration lock unavailable')
                before = table_names(c)
                legacy_before = legacy_fingerprint(c)
                source_sha = json.loads((ROOT/'database/ehr-schema-manifest.json').read_text(encoding='utf-8'))['source']['sha256']
                def register():
                    q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES (%s,%s,%s,%s,CURRENT_USER())',(VERSION,digest,source_sha,'EHR v2.0.0; 19 core tables plus documented a_ extensions'))
                if 'a_health_schema_migration' in before:
                    q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(VERSION,))
                    prior = q.fetchone()
                    if not prior and args.recover_verified:
                        if schema_hashes(c,tables) != expected or trigger_hashes(c) != expected_triggers:
                            raise RuntimeError('Partial run does not exactly match isolated validated DDL/triggers; no adoption')
                        seed_checks(c)
                        spec = json.loads((ROOT/'database/ehr-schema-manifest.json').read_text(encoding='utf-8'))
                        for table in set(tables)-set(spec['seed_counts']):
                            q.execute('SELECT COUNT(*) n FROM `'+table+'`')
                            if q.fetchone()['n'] != 0:
                                raise RuntimeError('Partial run contains business/migration records; no automatic adoption: '+table)
                        register()
                        c.commit()
                        result['recovered_verified_partial_run'] = True
                    elif not prior or prior['checksum'] != digest:
                        raise RuntimeError('Existing migration is missing or has a different checksum; no overwrite allowed')
                    if not set(tables).issubset(before):
                        raise RuntimeError('Version recorded but expected tables are missing')
                    result['already_applied'] = True
                else:
                    collision = before.intersection(tables)
                    if collision:
                        raise RuntimeError('Unversioned existing tables; stop for inspection: '+','.join(sorted(collision)))
                    create(c)
                    register()
                    c.commit()
                    result['already_applied'] = False
                actual = schema_hashes(c,tables)
                if actual != expected:
                    raise RuntimeError('Live structure differs from the isolated validated schema')
                if trigger_hashes(c) != expected_triggers:
                    raise RuntimeError('Live triggers differ from the validated schema')
                result['live_checks'] = seed_checks(c) + smoke(c)
                after = table_names(c)
                assert before.issubset(after), 'An existing table disappeared'
                legacy_after = legacy_fingerprint(c)
                assert legacy_before == legacy_after, 'Legacy table structure changed'
                result['legacy_structure_unchanged'] = True
                result['legacy_table_count'] = len(before-set(tables))
                result['trigger_count'] = len(expected_triggers)
                result['total_table_count'] = len(after)
                result['rows'] = {}
                for table in tables:
                    q.execute('SELECT COUNT(*) AS n FROM `'+table+'`')
                    result['rows'][table] = q.fetchone()['n']
                q.execute('SELECT DATABASE() AS db, VERSION() AS version, CURRENT_USER() AS account')
                result['connection'] = q.fetchone()
                result['connection']['host'] = configuration()['host']
                result['connection']['port'] = configuration()['port']
                result['applied'] = True
        finally:
            try:
                c.rollback()
                if locked:
                    with c.cursor() as q:
                        q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
            except pymysql.MySQLError:
                # A lost socket already releases its connection-scoped lock.
                # Do not mask the original database error with rollback failure.
                pass
            c.close()
    result['elapsed_seconds'] = round(time.monotonic()-started,2)
    result['verified_at'] = datetime.now().astimezone().isoformat()
    args.report.parent.mkdir(parents=True,exist_ok=True)
    args.report.write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps(result,ensure_ascii=False,indent=2))

if __name__ == '__main__':
    main()
