"""Delete-person erasure regression: synthetic API + DB + owned file assertions."""
from pathlib import Path
import requests,uuid,os,io,json,sys,time
from PIL import Image
from PIL.PngImagePlugin import PngInfo
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
B=os.getenv('EHR_TEST_URL','http://localhost:18082');s=requests.Session();s.trust_env=False
s.get(B+'/login',timeout=15)
assert s.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=30).json()['code']==200
ctx=s.get(B+'/ehr/api/context',timeout=15).json()['data'];assert ctx['dataMode']=='synthetic'
headers={'X-CSRF-Token':ctx['csrfToken']};checks=[];created=[]
def check(n,v=True):assert v,n;checks.append(n);print('PASS',n,flush=True)
def api(method,path,body=None,status=200,extra=None):
 r=s.request(method,B+'/ehr/api'+path,json=body,headers=dict(headers,**(extra or {})),timeout=45)
 assert r.status_code==status,(path,r.status_code,r.text[:300])
 return r.json()['data'] if status==200 else r.json()
def person():
 p=api('POST','/records',{'name':'联动删除隔离测试','code':'CASCADE_'+uuid.uuid4().hex[:12],'sex':'未填写','org':'隔离单位','team':'测试班组','job':'测试岗位'});created.append(p['id']);return p
def image():
 out=io.BytesIO();meta=PngInfo();meta.add_text('test',str(uuid.uuid4()));Image.new('RGB',(23,29),(90,123,45)).save(out,format='PNG',pnginfo=meta);return out.getvalue()
def upload(p,raw):
 r=s.post(B+'/ehr/api/records/'+p['id']+'/reports',files={'file':('synthetic.png',raw,'image/png')},headers=dict(headers,**{'Idempotency-Key':str(uuid.uuid4())}),timeout=30);assert r.status_code==200,r.text;return r.json()['data']
def delete(p):
 current=api('GET','/records/'+p['id']);api('DELETE','/records/'+p['id'],{'rowVersion':current['rowVersion'],'reason':'隔离测试：删除人员及体检报告'});created.remove(p['id'])
def count(sql,args=()):
 c=connect()
 try:
  with c.cursor() as q:q.execute(sql,args);return q.fetchone()['n']
 finally:c.close()
try:
 p=person();other=person();raw=image();pending=upload(p,raw);keep=upload(other,image());reviewed=upload(p,image())
 rid=reviewed['id'];path='/reports/'+rid+'/1'
 manual={'fullName':p['name'],'examDate':'2026-11-15','institution':'隔离测试机构','formalConclusion':'仅用于联动删除隔离测试','items':[{'name':'测试项目','value':'原始值','page':1}]}
 api('PUT',path,manual,extra={'If-Match':reviewed['etag']})
 api('POST',path+'/actions',{'action':'submit','note':'synthetic','requestKey':str(uuid.uuid4())})
 latest=api('GET',path)
 api('POST',path+'/actions',{'action':'approve','note':'synthetic','requestKey':str(uuid.uuid4()),'identityVerified':True,'etag':latest['etag']})
 check('reviewed_fixture_created',api('GET',path)['reviewStatus']=='confirmed')
 c=connect()
 try:
  with c.cursor() as q:
   try:q.execute('DELETE FROM a_health_reports WHERE report_id=%s',(rid,));raise AssertionError('guard missing')
   except Exception as e:assert 'immutable' in str(e),type(e).__name__
   check('reviewed_direct_delete_guard_retained')
   try:q.execute('CALL a_delete_health_report(%s,%s,%s,%s,%s)',(rid,'org:wrong-scope','synthetic',b'test','scope test'));raise AssertionError('scope guard missing')
   except Exception as e:assert 'authorized scope' in str(e),str(e)
   check('cross_scope_delete_rejected')
   q.execute('CALL a_delete_health_report(%s,%s,%s,%s,%s)',(rid,ctx['scope'],'synthetic',b'test','rollback test'))
   while q.nextset():pass
  c.rollback()
 finally:c.close()
 check('rollback_restores_reviewed_report',api('GET',path)['reviewStatus']=='confirmed')
 c=connect()
 with c.cursor() as q:
  q.execute("UPDATE a_ocr_dispatch_tasks SET dispatch_state='running',current_attempt_id=%s,worker_id='synthetic-worker',lease_token_hash=UNHEX(SHA2('fixture-only',256)),lease_until=UTC_TIMESTAMP(6)+INTERVAL 600 SECOND WHERE job_id=%s",(str(uuid.uuid4()),pending['jobId']))
  q.execute('SELECT object_ref FROM a_report_sources WHERE source_file_id=%s',(pending['files'][0]['id'],));original=Path('F:/upload-test')/q.fetchone()['object_ref'].decode()
 c.commit();c.close();check('original_exists_before_delete',original.is_file())
 cache=Path('F:/upload/.config/model-results')/(pending['jobId']+'.candidate.json');cache.parent.mkdir(exist_ok=True);cache.write_text('{"synthetic":true}',encoding='utf8')
 delete(p)
 api('GET',path,status=404);check('deleted_person_report_api_returns_404')
 api('GET','/files/'+pending['files'][0]['id'],status=404);check('deleted_original_download_returns_404')
 worker_token=Path('F:/upload/.config/ocr-worker.synthetic.token').read_text(encoding='utf8').strip()
 reply=s.post(B+'/ehr/worker/tasks/'+pending['jobId']+'/result',json={},headers={'Authorization':'Bearer '+worker_token,'X-OCR-Lease':'fixture-only'},timeout=15)
 check('late_worker_result_returns_404',reply.status_code==404)
 for table in ['a_health_reports','a_health_items','a_report_evidence','a_health_conclusions','a_health_advice','a_review_records','a_report_review_snapshots','a_report_series','a_extraction_jobs']:
  check(table+'_erased',count('SELECT COUNT(*) n FROM '+table+' WHERE report_id IN (%s,%s)',(rid,pending['id']))==0)
 check('worker_job_erased',count('SELECT COUNT(*) n FROM a_ocr_dispatch_tasks WHERE job_id=%s',(pending['jobId'],))==0)
 check('other_employee_report_preserved',api('GET','/reports/'+keep['id']+'/1')['recordId']==other['id'])
 for _ in range(20):
  if not original.exists() and not cache.exists():break
  time.sleep(1)
 check('original_and_model_cache_erased',not original.exists() and not cache.exists())
 check('file_erasure_tasks_completed',count("SELECT COUNT(*) n FROM a_report_file_cleanup_tasks WHERE report_id IN (%s,%s) AND status<>'done'",(rid,pending['id']))==0)
 fresh=upload(other,raw);check('same_original_can_upload_again',fresh['id']!=pending['id'] and fresh['revision']==1)
 check('no_source_dedup_collision_after_person_deleted',fresh['recordId']==other['id'])
 (ROOT/'database/report-cascade-verification.json').write_text(json.dumps({'checks':checks,'count':len(checks),'dataMode':'synthetic'},indent=2),encoding='utf8')
finally:
 for record in list(created):
  delete({'id':record})
