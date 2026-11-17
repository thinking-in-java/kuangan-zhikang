"""Add only the API support tables; verify DDL on an owned empty clone first."""
from pathlib import Path
import hashlib,json,re,uuid,argparse
from datetime import datetime
import health_archive_db as core

BASE=Path(__file__).resolve().parent
VERSION='V002_a_ehr_business_api'
SQL=BASE/'migrations/V002__a_ehr_business_api.sql'

def main():
    global SQL,VERSION
    parser=argparse.ArgumentParser();parser.add_argument('--migration',default=SQL.name);parser.add_argument('--version',default=VERSION);parser.add_argument('--report',default='ehr-api-migration-verification.json');parser.add_argument('--minimal-core',action='store_true',help='Validate an additive migration referencing only employees/health_records on their exact V001 table DDL');args=parser.parse_args()
    SQL=BASE/'migrations'/args.migration;VERSION=args.version
    original=core.SQL
    core_sql,_=core.statements()
    if args.minimal_core:core_sql=[s for s in core_sql if s.startswith(('CREATE TABLE a_employees (','CREATE TABLE a_health_records ('))]
    if args.migration=='V005__a_miniapp_correction_requests.sql':
        core.SQL=BASE/'migrations/V004__a_miniapp_identity.sql'
        dependency_sql,_=core.statements()
        core_sql+=dependency_sql
    if args.migration=='V006__a_ocr_worker_queue.sql':
        catalog=json.loads((BASE/'ehr-live-schema-catalog.json').read_text(encoding='utf-8'))
        parents={t['TABLE_NAME']:t for t in catalog['tables']};needed={'a_extraction_jobs'}
        while True:
            expanded=needed|{fk['REFERENCED_TABLE_NAME'] for n in needed for fk in parents[n]['foreign_keys']}
            if expanded==needed:break
            needed=expanded
        core_sql=['SET FOREIGN_KEY_CHECKS=0']+[parents[n]['ddl'] for n in sorted(needed)]+['SET FOREIGN_KEY_CHECKS=1']
    core.SQL=SQL
    sql,tables=core.statements()
    digest=hashlib.sha256(SQL.read_bytes()).hexdigest()
    temp='a_ehr_api_validate_'+uuid.uuid4().hex[:16]
    c=core.connect(read_timeout=180);owned=False
    try:
        with c.cursor() as q:q.execute('CREATE DATABASE `'+temp+'` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin');owned=True
        t=core.connect(temp,read_timeout=180)
        try:
            with t.cursor() as q:
                for stmt in core_sql:
                    if not stmt.startswith('INSERT INTO'):q.execute(stmt)
                for stmt in sql:q.execute(stmt)
            expected=core.schema_hashes(t,tables)
        finally:t.close()
    finally:
        if owned and re.fullmatch('a_ehr_api_validate_[a-f0-9]{16}',temp):
            with c.cursor() as q:q.execute('DROP DATABASE `'+temp+'`')
        c.close()
    c=core.connect(read_timeout=180)
    try:
        before=core.legacy_fingerprint(c)
        with c.cursor() as q:
            q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) locked")
            assert q.fetchone()['locked']==1
            q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(VERSION,))
            row=q.fetchone()
            if row:
                assert row['checksum']==digest,'Migration checksum mismatch'
            else:
                assert not set(tables)&core.table_names(c),'Unregistered API tables exist; inspect rather than overwrite'
                for stmt in sql:q.execute(stmt)
                q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())',(VERSION,digest,json.loads((BASE/'ehr-schema-manifest.json').read_text(encoding='utf-8'))['source']['sha256'],'API authorized roster, immutable review snapshots and opaque mobile sessions'))
                c.commit()
        assert core.schema_hashes(c,tables)==expected
        assert core.legacy_fingerprint(c)==before
        result=dict(version=VERSION,applied=True,tables=tables,sql_sha256=digest,legacy_unchanged=True,verified_at=datetime.now().astimezone().isoformat())
        (BASE/args.report).write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
        print(json.dumps(result,ensure_ascii=False))
    finally:
        c.rollback()
        with c.cursor() as q:q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
        c.close()
if __name__=='__main__':main()
