"""Explicit local maintenance only: erase all a_ report data, retain employee identities.
Pause upload services and OCR adapters before --all; originals are removed only after commit.
"""
import argparse,json,sys,uuid
from pathlib import Path
from decimal import Decimal,ROUND_HALF_UP
from health_archive_db import connect
ROOT=Path(__file__).resolve().parents[1]
REPORT_TABLES=['a_report_series','a_health_reports','a_report_sources','a_report_revision_sources','a_report_upload_metadata','a_extraction_jobs','a_ocr_dispatch_tasks','a_ocr_dispatch_attempts','a_ehr_ocr_receipts','a_health_items','a_report_evidence','a_health_conclusions','a_health_advice','a_occupational_profiles','a_review_records','a_report_review_snapshots','a_report_publications','a_report_quality_checks','a_report_sync_outbox','a_followup_tasks']
BASE_TABLES=['a_employees','a_health_records','a_employee_roster_profiles','a_miniapp_users','a_miniapp_employee_bindings']
def counts(q,tables):
 result={}
 for t in tables:q.execute('SELECT COUNT(*) n FROM '+t);result[t]=q.fetchone()['n']
 return result
def erase_all():
 c=connect(read_timeout=120)
 try:
  with c.cursor() as q:
   before=counts(q,REPORT_TABLES);base=counts(q,BASE_TABLES)
   q.execute('SELECT report_id,scope_code,data_mode FROM a_report_series ORDER BY report_id FOR UPDATE');reports=q.fetchall()
   q.execute('SELECT DISTINCT record_id FROM a_health_reports WHERE record_id IS NOT NULL');records=[r['record_id'] for r in q.fetchall()]
   for r in reports:
    q.execute('CALL a_delete_health_report(%s,%s,%s,%s,%s)',(r['report_id'],r['scope_code'],r['data_mode'],b'operator:all-reports-clear','User requested all uploaded exam reports erased; employee identities retained'))
    while q.nextset():pass
   # Replace completeness snapshots containing report references with base-profile-only checks.
   q.execute('DELETE FROM a_health_record_completeness')
   q.execute('SELECT r.record_id,r.scope_code,r.data_mode,p.* FROM a_health_records r JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id WHERE p.deleted_at IS NULL');profiles=q.fetchall()
   snapshots=[]
   for p in profiles:
    required=[('employee_name','\u59d3\u540d'),('employee_no','\u5de5\u53f7'),('organization_name','\u5355\u4f4d'),('team_name','\u73ed\u7ec4'),('position_name','\u5c97\u4f4d')]
    missing=[label for field,label in required if p[field] is None or not p[field].strip()]+['\u62a5\u544a\u539f\u4ef6','\u6838\u5b9a\u4f53\u68c0\u65e5\u671f','\u68c0\u67e5\u9879\u76ee\u539f\u503c','\u5b8c\u6574\u4f53\u68c0\u7ed3\u8bba']
    done=9-len(missing);rate=(Decimal(done)/9).quantize(Decimal('.0001'),rounding=ROUND_HALF_UP)
    snapshots.append((str(uuid.uuid4()),p['scope_code'],p['data_mode'],p['record_id'],done,json.dumps(missing,ensure_ascii=False),json.dumps({'reportId':None,'revision':None}),rate))
   if snapshots:q.executemany("INSERT INTO a_health_record_completeness(snapshot_id,scope_code,data_mode,record_id,required_count,completed_count,missing_items,rule_version,basis,completeness_rate) VALUES(%s,%s,%s,%s,9,%s,%s,'ehr-required-documents-v1',%s,%s)",snapshots)
   after=counts(q,REPORT_TABLES);assert not any(after.values()),after
   assert counts(q,BASE_TABLES)==base,'Employee identities unexpectedly changed'
   receipt={'before':before,'after':after,'employeeTablesPreserved':base,'reportsByMode':{m:sum(r['data_mode']==m for r in reports) for m in ['real','synthetic']},'committed':True}
  c.commit()
  (ROOT/'database/all-report-deletion-result.json').write_text(json.dumps(receipt,indent=2),encoding='utf8')
  print(json.dumps(receipt,indent=2))
 except Exception:c.rollback();raise
 finally:c.close()
if __name__=='__main__':
 p=argparse.ArgumentParser();p.add_argument('--all',action='store_true');a=p.parse_args()
 if not a.all:p.error('Explicit --all is required')
 erase_all()
