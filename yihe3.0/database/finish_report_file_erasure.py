"""Finish explicit all-report maintenance after DB commit, with upload services stopped."""
import json,re
from pathlib import Path
from health_archive_db import connect
ROOT=Path(__file__).resolve().parents[1]
def run():
 receipt_path=ROOT/'database/all-report-deletion-result.json'
 receipt=json.loads(receipt_path.read_text(encoding='utf8'));assert receipt.get('committed') is True
 c=connect();removed={}
 try:
  with c.cursor() as q:
   for table in ['a_report_sources','a_report_series','a_health_reports','a_extraction_jobs']:
    q.execute('SELECT COUNT(*) n FROM '+table);assert q.fetchone()['n']==0,'Report data appeared; abort file maintenance'
  # Only explicit app-owned locations. No recursive deletes and no symlink traversal.
  locations=[(Path('F:/upload'),lambda n:bool(re.fullmatch(r'[0-9a-fA-F-]{36}\.source|pending-\d+\.bin',n))),
             (Path('F:/upload-test'),lambda n:bool(re.fullmatch(r'[0-9a-fA-F-]{36}\.source|pending-\d+\.bin',n))),
             (Path('F:/upload/.config/model-results'),lambda n:True),
             (Path('F:/workstation/exports/ocr'),lambda n:n.endswith('.b-result.json'))]
  for root,accept in locations:
   if not root.is_dir():continue
   expected=root.absolute();resolved=root.resolve();assert resolved==expected, 'Unexpected storage redirection'
   count=0
   for target in root.iterdir():
    if not accept(target.name) or target.is_dir():continue
    assert not target.is_symlink() and target.resolve().parent==resolved,'Unsafe cleanup target'
    target.unlink();count+=1
   removed[str(root)]=count
  # Missing source/job artifacts are already erased; complete their durable queue entries.
  # Each task must have a supported safe target even during one-time maintenance.
  with c.cursor() as q:
   q.execute("SELECT cleanup_id,data_mode,target_kind,target_ref FROM a_report_file_cleanup_tasks WHERE status<>'done'")
   for task in q.fetchall():
    ref=task['target_ref'].decode()
    if task['target_kind']=='source':
     assert re.fullmatch(r'[0-9a-fA-F-]{36}\.source',ref), 'Unexpected object reference'
     root=Path('F:/upload' if task['data_mode']=='real' else 'F:/upload-test');assert not (root/ref).exists()
    else:
     assert re.fullmatch(r'[0-9a-fA-F-]{36}',ref)
     assert not list(Path('F:/upload/.config/model-results').glob(ref+'.*'))
    q.execute("UPDATE a_report_file_cleanup_tasks SET status='done',attempts=attempts+1,last_error=NULL,updated_at=UTC_TIMESTAMP(6) WHERE cleanup_id=%s",(task['cleanup_id'],))
  c.commit();receipt['removedFiles']=removed;receipt_path.write_text(json.dumps(receipt,indent=2),encoding='utf8');print(json.dumps(removed,indent=2))
 finally:c.close()
if __name__=='__main__':run()
