"""Additive V018; snapshot affected schema before first apply; preserve business data."""
import argparse,hashlib,json
from pathlib import Path
from datetime import datetime
from health_archive_db import connect

def apply():
    path=Path(__file__).parent/'migrations/V018__a_service_auto_reply.sql'
    raw=path.read_bytes();checksum=hashlib.sha256(raw).hexdigest();version='V018_a_service_auto_reply';con=connect()
    try:
        with con.cursor() as q:
            q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) acquired")
            if q.fetchone()['acquired']!=1:raise RuntimeError('Schema lock unavailable')
            q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(version,));prior=q.fetchone()
            if prior:
                if prior['checksum']!=checksum:raise RuntimeError('Migration checksum changed')
                return {'version':version,'alreadyApplied':True}
            tables=['a_service_reply_configs','a_service_reply_releases','a_service_reply_events']
            for t in tables:
                q.execute('SELECT COUNT(*) n FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name=%s',(t,))
                if q.fetchone()['n']:raise RuntimeError('Unversioned table '+t)
            q.execute('SHOW CREATE TABLE a_service_messages');snapshot=q.fetchone()['Create Table']
            backup=Path('F:/workstation/.tools/runtime')/('before-auto-reply-'+datetime.now().strftime('%Y%m%d-%H%M%S')+'.sql');backup.parent.mkdir(parents=True,exist_ok=True);backup.write_text(snapshot+';\n',encoding='utf8')
            for statement in raw.decode('utf8').split(';'):
                if statement.strip():q.execute(statement)
            q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())',(version,checksum,checksum,'Versioned auto replies and isolated runtime events'))
            con.commit();q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
            return {'version':version,'created':tables,'schemaBackup':str(backup)}
    finally:con.close()
if __name__=='__main__':
    p=argparse.ArgumentParser();p.add_argument('--apply',required=True,action='store_true');p.parse_args();print(json.dumps(apply()))
