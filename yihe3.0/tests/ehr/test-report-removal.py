"""HTTP removal, queue fencing and same-original reupload in synthetic mode only."""
from pathlib import Path
import requests,uuid,os,io,json,sys
from PIL import Image
from PIL.PngImagePlugin import PngInfo
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
B=os.getenv('EHR_TEST_URL','http://localhost:18081');s=requests.Session();s.trust_env=False
s.get(B+'/login',timeout=10)
assert s.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=25).json()['code']==200
ctx=s.get(B+'/ehr/api/context',timeout=15).json()['data'];assert ctx['dataMode']=='synthetic'
csrf={'X-CSRF-Token':ctx['csrfToken']};checks=[];created=[]
def check(n,v=True):assert v,n;checks.append(n);print('PASS',n,flush=True)
def api(method,path,body=None,status=200,extra=None):
 r=s.request(method,B+'/ehr/api'+path,json=body,headers=dict(csrf,**(extra or {})),timeout=30)
 assert r.status_code==status,(path,r.status_code,r.text[:400])
 return r.json()['data'] if status==200 else r.json()
def person():
 p=api('POST','/records',{'name':'报告移除测试','code':'RM_'+uuid.uuid4().hex[:14],'sex':'未填写','org':'隔离测试','team':'测试班组','job':'测试岗位'});created.append(p['id']);return p
def upload(p,raw,key=None):
 r=s.post(B+'/ehr/api/records/'+p['id']+'/reports',files={'file':('synthetic.png',raw,'image/png')},headers=dict(csrf,**{'Idempotency-Key':key or str(uuid.uuid4())}),timeout=30);assert r.status_code==200,r.text
 return r.json()['data']
def data(n):
 out=io.BytesIO();meta=PngInfo();meta.add_text('syntheticRun',str(uuid.uuid4()));Image.new('RGB',(17+n,23),(n,123,45)).save(out,format='PNG',pnginfo=meta);return out.getvalue()
try:
 p=person();other=person();raw=data(1);r=upload(p,raw);keep=upload(p,data(2))
 check('unreviewed_report_exposes_remove_permission',r['canRemove'])
 body={'reason':'测试：误传了其他人的报告','requestKey':str(uuid.uuid4())}
 path='/reports/'+r['id']+'/1'
 api('DELETE',path,body,400);check('missing_if_match_rejected')
 api('DELETE',path,body,409,{'If-Match':'stale'});check('stale_candidate_cannot_be_removed')
 api('DELETE',path,dict(body,reason=''),422,{'If-Match':r['etag']});check('removal_requires_reason')
 # Simulate the worker owning the report; removal must fence that worker.
 c=connect()
 with c.cursor() as q:
  q.execute("UPDATE a_ocr_dispatch_tasks SET dispatch_state='running',current_attempt_id=%s,worker_id='synthetic-worker',lease_token_hash=UNHEX(SHA2('fixture-only',256)),lease_until=UTC_TIMESTAMP(6)+INTERVAL 120 SECOND WHERE job_id=%s AND data_mode='synthetic'",(str(uuid.uuid4()),r['jobId']))
 c.commit();c.close()
 removed=api('DELETE',path,body,extra={'If-Match':r['etag']})
 check('delete_endpoint_removes_exact_report',removed['removed'] and removed['reportId']==r['id'])
 check('repeat_delete_is_idempotent',api('DELETE',path,body,extra={'If-Match':r['etag']})['removed'])
 current=api('GET','/records/'+p['id']);check('other_report_and_employee_preserved',current['id']==p['id'] and [x['id'] for x in current['reports']]==[keep['id']])
 api('GET',path,status=404);check('removed_report_not_accessible_from_old_employee')
 c=connect()
 with c.cursor() as q:
  q.execute("SELECT dispatch_state FROM a_ocr_dispatch_tasks WHERE job_id=%s AND data_mode='synthetic'",(r['jobId'],));check('running_ocr_cancelled',q.fetchone()['dispatch_state']=='cancelled')
  q.execute('SELECT COUNT(*) n FROM a_report_sources WHERE source_file_id=%s',(r['files'][0]['id'],));check('original_registry_retained',q.fetchone()['n']==1)
  q.execute("SELECT COUNT(*) n FROM a_health_archive_audit_logs WHERE action='report_removed' AND request_key=%s",(body['requestKey'],));check('one_audit_record_on_network_retry',q.fetchone()['n']==1)
 c.close()
 fresh=upload(other,raw);check('removed_original_reuploads_to_correct_employee',fresh['recordId']==other['id'] and fresh['revision']==2 and fresh['jobId']!=r['jobId'] and fresh['items']==[])
 # An old confirmation cannot remove the new association.
 api('DELETE',path,dict(body,requestKey=str(uuid.uuid4())),404,{'If-Match':r['etag']});check('old_version_does_not_remove_reupload')
 approved=upload(p,data(3));rid=approved['id'];manual={'fullName':p['name'],'examDate':'2026-11-15','institution':'测试机构','formalConclusion':'仅用于隔离测试','items':[{'name':'测试项目','value':'原始值','page':1}]}
 api('PUT','/reports/'+rid+'/1',manual,extra={'If-Match':approved['etag']})
 api('POST','/reports/'+rid+'/1/actions',{'action':'submit','note':'synthetic','requestKey':str(uuid.uuid4())})
 latest=api('GET','/reports/'+rid+'/1')
 api('POST','/reports/'+rid+'/1/actions',{'action':'approve','note':'synthetic','requestKey':str(uuid.uuid4()),'identityVerified':True,'etag':latest['etag']})
 latest=api('GET','/reports/'+rid+'/1');check('reviewed_report_has_no_remove_button',not latest['canRemove'])
 api('DELETE','/reports/'+rid+'/1',{'reason':'synthetic denied','requestKey':str(uuid.uuid4())},409,{'If-Match':latest['etag']});check('reviewed_report_removal_rejected_by_backend')
 check('reviewed_report_preserved',api('GET','/reports/'+rid+'/1')['reviewStatus']=='confirmed')
 (ROOT/'database/report-removal-verification.json').write_text(json.dumps({'checks':checks,'count':len(checks),'mode':'synthetic','realEmployeeModified':False},ensure_ascii=False,indent=2),encoding='utf-8')
finally:
 for pid in created:
  current=api('GET','/records/'+pid)
  api('DELETE','/records/'+pid,{'rowVersion':current['rowVersion'],'reason':'隔离测试清理，保留处理历史'})
