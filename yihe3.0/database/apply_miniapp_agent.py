"""Validate V009 on an owned schema clone, then apply additive a_ changes with a migration lock."""
from pathlib import Path
import re,uuid,json,hashlib
from datetime import datetime
import health_archive_db as core

BASE=Path(__file__).resolve().parent
SQL=BASE/'migrations/V009__a_miniapp_health_agent.sql'
VERSION='V009_a_miniapp_health_agent'
TABLES=['a_agent_conversations','a_agent_turns']

def main():
    source=SQL.read_text(encoding='utf8');digest=hashlib.sha256(SQL.read_bytes()).hexdigest()
    parts=[s.strip() for s in re.sub(r'^\s*--[^\n]*','',source,flags=re.M).split(';') if s.strip()]
    assert len(parts)==2 and all(s.startswith('ALTER TABLE '+t+'\n') for s,t in zip(parts,TABLES))
    c=core.connect(read_timeout=180);temp='a_mini_agent_validate_'+uuid.uuid4().hex[:16];created=False
    try:
        with c.cursor() as q:
            q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',15) locked");assert q.fetchone()['locked']==1
            q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(VERSION,));old=q.fetchone()
            if old:assert old['checksum']==digest,'Migration checksum mismatch'
            q.execute("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='a_agent_conversations'")
            assert old or 'client_type' not in {r['COLUMN_NAME'] for r in q.fetchall()},'Unregistered partial migration; inspect before proceeding'
            original=core.SQL;core.SQL=BASE/'migrations/V008__a_digital_health_agent.sql'
            try:baseline,_=core.statements()
            finally:core.SQL=original
            ddls=[stmt for stmt in baseline if any(stmt.startswith('CREATE TABLE '+t+' (') for t in TABLES)]
            counts={}
            for t in TABLES+['a_employee_roster_profiles','a_health_reports']:
                q.execute('SELECT COUNT(*) n FROM '+t);counts[t]=q.fetchone()['n']
            q.execute('CREATE DATABASE `'+temp+'` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin');created=True
        clone=core.connect(temp,read_timeout=180)
        try:
            with clone.cursor() as q:
                for ddl in ddls:q.execute(ddl)
                for s in parts:q.execute(s)
            expected=normalized_schema(clone)
        finally:clone.close()
        with c.cursor() as q:
            if not old:
                for s in parts:q.execute(s)
                metadata=json.loads((BASE/'ehr-schema-manifest.json').read_text(encoding='utf8'))
                q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())',(VERSION,digest,metadata['source']['sha256'],'Personal miniapp agent ownership and durable async turns; existing admin rows unchanged'))
        c.commit()
        assert normalized_schema(c)==expected,'Live schema differs from validated V008 + V009'
        with c.cursor() as q:
            for t,n in counts.items():
                q.execute('SELECT COUNT(*) n FROM '+t);assert q.fetchone()['n']==n,(t,'row count changed during migration')
        result={'version':VERSION,'applied':True,'schemaCloneValidated':True,'existingRowsPreserved':True,'tablesAltered':TABLES,'newTables':[],'sqlSha256':digest,'verifiedAt':datetime.now().astimezone().isoformat()}
        (BASE/'miniapp-agent-migration-verification.json').write_text(json.dumps(result,ensure_ascii=False,indent=2),encoding='utf8');print(json.dumps(result))
    finally:
        c.rollback()
        with c.cursor() as q:
            if created and re.fullmatch(r'a_mini_agent_validate_[a-f0-9]{16}',temp):q.execute('DROP DATABASE `'+temp+'`')
            q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
        c.close()

def normalized_schema(c):
    # MySQL may print redundant per-column CHARACTER SET after ALTER/clone.
    result={}
    with c.cursor() as q:
        for table in TABLES:
            q.execute('SHOW CREATE TABLE '+table)
            ddl=q.fetchone()['Create Table'].replace(' CHARACTER SET utf8mb4','')
            result[table]=hashlib.sha256(ddl.encode()).hexdigest()
    return result

if __name__=='__main__':main()
