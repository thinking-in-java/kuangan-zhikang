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
SQL = ROOT / 'database/migrations/V001__a_health_archive.sql'
VERSION = 'V001_a_health_archive'

def configuration():
    c = yaml.safe_load((ROOT/'yihe-admin/src/main/resources/application-druid.yml').read_text(encoding='utf-8'))['spring']['datasource']['druid']['master']
    m = re.fullmatch(r'jdbc:mysql://([^:/]+)(?::(\d+))?/([^?]+)(?:\?.*)?', c['url'])
    if not m:
        raise ValueError('Unrecognized JDBC URL')
    return dict(host=m[1], port=int(m[2] or 3306), database=m[3], user=c['username'], password=str(c['password']))

def connect(database=None):
    c = configuration()
    if database is not None:
        c['database'] = database
    return pymysql.connect(**c,charset='utf8mb4',connect_timeout=8,read_timeout=30,write_timeout=30,autocommit=False,cursorclass=pymysql.cursors.DictCursor)

def statements():
    # This migration uses standard quoted literals, no procedures or DELIMITER directives.
    source = SQL.read_text(encoding='utf-8')
    result, part, quote, comment = [], [], None, False
    i = 0
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
        elif source[i:i+2] == '--':
            comment = True
            i += 1
        elif ch in "'\"`":
            quote = ch
            part.append(ch)
        elif ch == ';':
            if ''.join(part).strip():
                result.append(''.join(part).strip())
            part = []
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
        elif not re.match(r'(?:ALTER TABLE|INSERT INTO) a_[a-z0-9_]+\b',stmt):
            raise ValueError('Unexpected statement; all writes must target new a_ tables')
    if len(tables) != len(set(tables)):
        raise ValueError('Duplicate CREATE table')
    for stmt in result:
        m = re.match(r'(?:ALTER TABLE|INSERT INTO) (a_[a-z0-9_]+)\b',stmt)
        if m and m[1] not in tables:
            raise ValueError('Mutation targets a table outside this migration')
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

def create(c):
    sql,tables = statements()
    with c.cursor() as q:
        for stmt in sql:
            name = re.match(r'(?:CREATE TABLE|ALTER TABLE|INSERT INTO) (\w+)',stmt)[1]
            q.execute(stmt)
            print('Applied',stmt.split()[0],name,flush=True)
    return tables

def smoke(c):
    """Exercise real InnoDB CRUD, joins, FK/CHECK/unique/version gates; roll back all fixtures."""
    c.rollback()
    results = []
    token = 'test_'+uuid.uuid4().hex[:16]
    org = '__a_schema_test__'
    actor = '__schema_validation__'
    def execute(sql,args=()):
        with c.cursor() as q:
            q.execute(sql,args)
            return q.lastrowid
    def rejected(label,sql,args,code):
        try:
            execute(sql,args)
        except pymysql.MySQLError as e:
            if e.args[0] != code:
                raise AssertionError((label,e.args)) from e
            results.append(label)
        else:
            raise AssertionError('Constraint did not reject: '+label)
    try:
        aid = execute('INSERT INTO a_health_archive (owner_org_code,archive_no,employee_no,employee_name,source_code,created_by,updated_by) VALUES (%s,%s,%s,%s,%s,%s,%s)',(org,token,token,'建表事务测试','manual',actor,actor))
        rejected('active_employee_unique','INSERT INTO a_health_archive (owner_org_code,archive_no,employee_no,employee_name,source_code,created_by,updated_by) VALUES (%s,%s,%s,%s,%s,%s,%s)',(org,token+'duplicate',token,'重复工号','manual',actor,actor),1062)
        rejected('completeness_range','UPDATE a_health_archive SET completeness=101 WHERE id=%s',(aid,),3819)
        rejected('invalid_status','UPDATE a_health_archive SET status=%s WHERE id=%s',('invalid',aid),3819)
        rejected('archive_requires_publication','UPDATE a_health_archive SET status=%s WHERE id=%s',('archived',aid),3819)
        fid = execute('INSERT INTO a_health_file (owner_org_code,storage_provider,storage_key,original_name,content_type,size_bytes,sha256,scan_status,uploaded_by) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s)',(org,'validation',token,'schema-test.pdf','application/pdf',100,'0'*64,'clean',actor))
        rid = execute('INSERT INTO a_health_exam_report (owner_org_code,archive_id,report_no,report_name,source_code,submitted_by) VALUES (%s,%s,%s,%s,%s,%s)',(org,aid,token,'事务回滚测试报告','photo',actor))
        execute('INSERT INTO a_health_report_file (owner_org_code,report_id,file_id) VALUES (%s,%s,%s)',(org,rid,fid))
        rejected('cross_org_fk','INSERT INTO a_health_archive_exposure (owner_org_code,archive_id,hazard_code,hazard_name,created_by) VALUES (%s,%s,%s,%s,%s)',(org+'other',aid,'noise','噪声',actor),1452)
        task = execute('INSERT INTO a_health_parse_task (owner_org_code,report_id,report_version,request_key) VALUES (%s,%s,1,%s)',(org,rid,token))
        result_id = execute('INSERT INTO a_health_indicator_result (owner_org_code,report_id,parse_task_id,indicator_code,value_numeric,unit,value_source) VALUES (%s,%s,%s,%s,%s,%s,%s)',(org,rid,task,'SBP',120,'mmHg','manual'))
        execute('UPDATE a_health_indicator_result SET confirmed_by=%s,confirmed_at=NOW(3) WHERE id=%s',(actor,result_id))
        with c.cursor() as q:
            q.execute('SELECT a.employee_name,r.report_name,i.value_numeric FROM a_health_archive a JOIN a_health_exam_report r ON r.owner_org_code=a.owner_org_code AND r.archive_id=a.id JOIN a_health_indicator_result i ON i.owner_org_code=r.owner_org_code AND i.report_id=r.id WHERE a.id=%s',(aid,))
            row = q.fetchone()
            assert row and int(row['value_numeric']) == 120
        results.append('insert_update_select_and_relations')
        review = execute('INSERT INTO a_health_archive_review (owner_org_code,archive_id,data_version,completeness_snapshot,submitted_by) VALUES (%s,%s,1,%s,%s)',(org,aid,'{"complete":true}',actor))
        snapshot = '{"schema_test":true}'
        rev_sql = 'INSERT INTO a_health_archive_revision (owner_org_code,archive_id,revision_no,data_version,review_id,snapshot,snapshot_sha256,archived_by) VALUES (%s,%s,%s,%s,%s,%s,%s,%s)'
        rev_args = (org,aid,1,1,review,snapshot,hashlib.sha256(snapshot.encode()).hexdigest(),actor)
        rejected('cannot_publish_pending_review',rev_sql,rev_args,1452)
        execute('UPDATE a_health_archive_review SET decision=%s,risk_confirmed=1,reviewed_by=%s,reviewed_at=NOW(3) WHERE id=%s',('approved',actor,review))
        rejected('review_version_must_match',rev_sql,(org,aid,1,2,review,snapshot,'0'*64,actor),1452)
        execute(rev_sql,rev_args)
        execute('UPDATE a_health_archive SET status=%s,completeness=100,published_revision_no=1,archived_by=%s,archived_at=NOW(3) WHERE id=%s',('archived',actor,aid))
        results.append('approved_review_revision_publication')
        rejected('publication_fk','UPDATE a_health_archive SET published_revision_no=999 WHERE id=%s',(aid,),1452)
        execute('UPDATE a_health_archive SET status=%s,deleted_at=NOW(3),deleted_by=%s,delete_reason=%s WHERE id=%s',('draft',actor,'测试软删除',aid))
        execute('INSERT INTO a_health_archive_event (owner_org_code,archive_id,event_type,data_version,actor_code,reason) VALUES (%s,%s,%s,1,%s,%s)',(org,aid,'schema_test',actor,'验证审计关联'))
        results.append('soft_delete_and_audit_event')
    finally:
        c.rollback()
    with c.cursor() as q:
        q.execute('SELECT COUNT(*) AS n FROM a_health_archive WHERE owner_org_code=%s',(org,))
        assert q.fetchone()['n'] == 0
    results.append('test_records_rolled_back')
    return results

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
            checks = smoke(c)
            hashes = schema_hashes(c,tables)
            print('Isolated validation passed:',len(checks),'checks',flush=True)
            return tables,hashes,checks
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
    parser.add_argument('--report',type=Path,default=ROOT/'database/health_archive_verification.json')
    args = parser.parse_args()
    if not (args.apply or args.validate):
        parser.error('Choose --validate or --apply')
    started = time.monotonic()
    tables,expected,checks = isolated_validation()
    digest = hashlib.sha256(SQL.read_bytes()).hexdigest()
    result = {'version':VERSION,'sql_sha256':digest,'validation_checks':checks,'tables':tables,'applied':False}
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
                if 'a_health_schema_migration' in before:
                    q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(VERSION,))
                    prior = q.fetchone()
                    if not prior or prior['checksum'] != digest:
                        raise RuntimeError('Existing migration is missing or has a different checksum; no overwrite allowed')
                    if not set(tables).issubset(before):
                        raise RuntimeError('Version recorded but expected tables are missing')
                    result['already_applied'] = True
                else:
                    collision = before.intersection(tables)
                    if collision:
                        raise RuntimeError('Unversioned existing tables; stop for inspection: '+','.join(sorted(collision)))
                    create(c)
                    q.execute('INSERT INTO a_health_schema_migration(version,checksum,description,applied_by) VALUES (%s,%s,%s,CURRENT_USER())',(VERSION,digest,'健康档案基础表；全部使用a_前缀'))
                    c.commit()
                    result['already_applied'] = False
                actual = schema_hashes(c,tables)
                if actual != expected:
                    raise RuntimeError('Live structure differs from the isolated validated schema')
                result['live_checks'] = smoke(c)
                after = table_names(c)
                assert before.issubset(after), 'An existing table disappeared'
                result['legacy_table_count'] = len(before-set(tables))
                result['total_table_count'] = len(after)
                result['rows'] = {}
                for table in tables:
                    q.execute('SELECT COUNT(*) AS n FROM `'+table+'`')
                    result['rows'][table] = q.fetchone()['n']
                q.execute('SELECT DATABASE() AS db, VERSION() AS version, CURRENT_USER() AS account')
                result['connection'] = q.fetchone()
                result['applied'] = True
        finally:
            c.rollback()
            if locked:
                with c.cursor() as q:
                    q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
            c.close()
    result['elapsed_seconds'] = round(time.monotonic()-started,2)
    result['verified_at'] = datetime.now().astimezone().isoformat()
    args.report.parent.mkdir(parents=True,exist_ok=True)
    args.report.write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps(result,ensure_ascii=False,indent=2))

if __name__ == '__main__':
    main()
