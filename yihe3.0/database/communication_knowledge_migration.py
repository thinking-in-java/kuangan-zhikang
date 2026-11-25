"""Additive V019 schema only, advisory lock and checksum; never overwrites business tables."""
import argparse,hashlib,json
from pathlib import Path
from health_archive_db import connect
def apply():
 p=Path(__file__).parent/'migrations/V019__a_service_knowledge.sql';raw=p.read_bytes();sha=hashlib.sha256(raw).hexdigest();version='V019_a_service_knowledge';con=connect()
 try:
  with con.cursor() as q:
   q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) ok");assert q.fetchone()['ok']==1
   q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(version,));prior=q.fetchone()
   if prior:
    assert prior['checksum']==sha,'Migration checksum differs'
    return {'alreadyApplied':True,'version':version}
   names=['a_service_kb_categories','a_service_kb_documents','a_service_kb_versions','a_service_kb_settings','a_service_kb_usage']
   for name in names:
    q.execute('SELECT COUNT(*) n FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name=%s',(name,));assert not q.fetchone()['n'],'Unversioned table exists: '+name
   for statement in raw.decode('utf8').split(';'):
    if statement.strip():q.execute(statement)
   q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())',(version,sha,sha,'Scoped service knowledge with revision snapshots and usage'))
   con.commit();q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
   return {'version':version,'created':names}
 finally:con.close()
if __name__=='__main__':
 p=argparse.ArgumentParser();p.add_argument('--apply',required=True,action='store_true');p.parse_args();print(json.dumps(apply()))
