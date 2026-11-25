"""Apply additive V017; never change existing messages or clinical records."""
import argparse,hashlib,json
from pathlib import Path
from health_archive_db import connect

def apply():
    source=(Path(__file__).parent/'migrations/V017__a_service_history.sql').read_bytes()
    checksum=hashlib.sha256(source).hexdigest();version='V017_a_service_history';table='a_service_conversation_details'
    con=connect()
    try:
        with con.cursor() as q:
            q.execute("SELECT GET_LOCK('a_health_archive_schema_v1',10) acquired")
            if q.fetchone()['acquired']!=1:raise RuntimeError('Schema lock unavailable')
            q.execute('SELECT checksum FROM a_health_schema_migration WHERE version=%s',(version,));prior=q.fetchone()
            if prior and prior['checksum']!=checksum:raise RuntimeError('Migration checksum changed')
            q.execute('SELECT COUNT(*) n FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name=%s',(table,));exists=q.fetchone()['n']>0
            if exists and not prior:raise RuntimeError('Unversioned table exists')
            if not exists:q.execute(source.decode('utf8').strip().rstrip(';'))
            if not prior:q.execute('INSERT INTO a_health_schema_migration(version,checksum,source_sha256,description,applied_by) VALUES(%s,%s,%s,%s,CURRENT_USER())',(version,checksum,checksum,'Service history categories and employee feedback'))
            con.commit();q.execute("SELECT RELEASE_LOCK('a_health_archive_schema_v1')")
            return {'version':version,'table':table,'created':not exists}
    finally:con.close()

if __name__=='__main__':
    p=argparse.ArgumentParser();p.add_argument('--apply',action='store_true',required=True);p.parse_args()
    print(json.dumps(apply()))
