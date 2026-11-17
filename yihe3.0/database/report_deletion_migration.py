"""Install scoped erasure support. All FK checks remain enabled."""
import argparse,json,re
from pathlib import Path
from health_archive_db import connect
ROOT=Path(__file__).resolve().parents[1]
FILE=ROOT/'database/migrations/V007__a_report_deletion.sql'
def prepare(c):
 source=FILE.read_text(encoding='utf8')
 if '-- Scoped deletion trigger guards' in source:return
 with c.cursor() as q:
  q.execute("SELECT TRIGGER_NAME,EVENT_OBJECT_TABLE,ACTION_STATEMENT FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA=DATABASE() AND EVENT_MANIPULATION='DELETE'")
  rows=q.fetchall()
 originals=[];updates=[]
 for row in rows:
  name,table,body=row['TRIGGER_NAME'],row['EVENT_OBJECT_TABLE'],row['ACTION_STATEMENT']
  if not table.startswith('a_') or 'a_report_deletions' in body:continue
  assert re.fullmatch(r'a_[a-z0-9_]+',name) and body.strip().startswith('BEGIN') and body.strip().endswith('END')
  original=f'CREATE TRIGGER {name} BEFORE DELETE ON {table} FOR EACH ROW {body}'
  originals.append(original+'$$')
  if table=='a_report_sources':
   allowed="EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.scope_code=OLD.scope_code AND d.data_mode=OLD.data_mode AND JSON_CONTAINS(d.source_ids,JSON_QUOTE(OLD.source_file_id)))"
  elif table=='a_ehr_ocr_receipts':
   allowed="EXISTS(SELECT 1 FROM a_report_deletions d JOIN a_extraction_jobs j ON j.report_id=d.report_id WHERE d.state='purging' AND j.job_id=OLD.job_id)"
  else:allowed="EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)"
  wrapped='BEGIN\nIF NOT ('+allowed+') THEN\n'+body.strip()[5:-3].strip()+'\nEND IF;\nEND'
  updates.append(f'DROP TRIGGER {name}$$\nCREATE TRIGGER {name} BEFORE DELETE ON {table} FOR EACH ROW {wrapped}$$')
 assert len(updates)==13,len(updates)
 (ROOT/'database/report-deletion-original-guards.sql').write_text('DELIMITER $$\n'+'\n'.join(originals)+'\n',encoding='utf8')
 FILE.write_text(source+'\n-- Scoped deletion trigger guards; normal reviewed-data immutability is retained.\n'+'\n'.join(updates)+'\n',encoding='utf8')
def apply(c):
 originals={}
 with c.cursor() as q:
  q.execute("SELECT TRIGGER_NAME,EVENT_OBJECT_TABLE,ACTION_STATEMENT FROM information_schema.TRIGGERS WHERE TRIGGER_SCHEMA=DATABASE() AND EVENT_MANIPULATION='DELETE'")
  for row in q.fetchall():
   originals[row['TRIGGER_NAME']]=f"CREATE TRIGGER {row['TRIGGER_NAME']} BEFORE DELETE ON {row['EVENT_OBJECT_TABLE']} FOR EACH ROW {row['ACTION_STATEMENT']}"
 dropped=None
 try:
  for part in FILE.read_text(encoding='utf8').replace('DELIMITER $$','').split('$$'):
   part='\n'.join(line for line in part.splitlines() if not line.lstrip().startswith('--')).strip()
   if not part:continue
   match=re.fullmatch(r'DROP TRIGGER (a_[a-z0-9_]+)',part)
   with c.cursor() as q:q.execute(part)
   if match:dropped=match.group(1)
   elif part.startswith('CREATE TRIGGER '):dropped=None
 except Exception:
  if dropped and dropped in originals:
   with c.cursor() as q:q.execute(originals[dropped])
  raise
 c.commit()
if __name__=='__main__':
 parser=argparse.ArgumentParser();parser.add_argument('--prepare',action='store_true');parser.add_argument('--apply',action='store_true');args=parser.parse_args()
 c=connect(read_timeout=60)
 try:
  if args.prepare:prepare(c);print('Prepared scoped deletion migration; no report rows changed')
  if args.apply:apply(c);print('Installed V007; FK checks remain enabled; no report rows deleted')
 finally:c.close()
