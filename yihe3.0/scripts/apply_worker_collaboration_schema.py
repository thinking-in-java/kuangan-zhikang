"""Apply only the additive V037/V038 tables to the configured datasource."""
import json,re
from pathlib import Path
from urllib.parse import urlparse
import pymysql,yaml

base=Path(__file__).resolve().parents[1]
config=yaml.safe_load((base/'yihe-admin/src/main/resources/application-druid.yml').read_text(encoding='utf-8'))['spring']['datasource']['druid']['master']
u=urlparse(config['url'].removeprefix('jdbc:'))
conn=pymysql.connect(host=u.hostname,port=u.port or 3306,user=config['username'],password=config['password'],database=u.path.lstrip('/'),charset='utf8mb4',connect_timeout=8,autocommit=True)
try:
 with conn.cursor() as cur:
  cur.execute("SELECT GET_LOCK('a_worker_collaboration_schema',10)")
  if cur.fetchone()[0]!=1: raise RuntimeError('Schema lock unavailable')
  scripts=[base/'database/migrations'/name for name in ['V037__a_psychology_coordinator.sql','V038__a_worker_collaboration.sql']]
  tables=[name for p in scripts for name in re.findall(r'CREATE TABLE IF NOT EXISTS (a_\w+)',p.read_text(encoding='utf-8'))]
  before={}
  for table in tables:
   cur.execute('SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name=%s',(table,))
   if cur.fetchone()[0]:
    cur.execute('SHOW CREATE TABLE `'+table+'`');before[table]=cur.fetchone()[1]
  backup=base/'database/backups';backup.mkdir(exist_ok=True)
  (backup/'worker-collaboration-preexisting-schema.json').write_text(json.dumps(before,ensure_ascii=False,indent=2),encoding='utf-8')
  # Existing tables are never dropped or altered. On reruns verify required columns after CREATE IF NOT EXISTS.
  for path in scripts:
   sql=re.sub(r'^--.*$','',path.read_text(encoding='utf-8'),flags=re.M)
   for statement in sql.split(';'):
    if statement.strip():cur.execute(statement)
  for table in tables:
   cur.execute('SELECT COUNT(*) FROM `'+table+'`');print(table,'rows=',cur.fetchone()[0])
  cur.execute("SELECT RELEASE_LOCK('a_worker_collaboration_schema')")
finally:conn.close()
