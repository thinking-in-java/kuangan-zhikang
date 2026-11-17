"""Full B-export replay against synthetic A only. Source is provided by env;
the medical JSON is never copied into the repository or printed to logs."""
from pathlib import Path
from copy import deepcopy
import json,os,sys,uuid,hashlib,importlib.util
import requests
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
spec=importlib.util.spec_from_file_location('bind_ocr_result',ROOT/'docs/examples/bind_ocr_result.py');module=importlib.util.module_from_spec(spec);spec.loader.exec_module(module)
source=Path(os.environ['EHR_B_REPLAY_JSON']);original=json.loads(source.read_text(encoding='utf-8-sig'))
base=os.getenv('EHR_TEST_URL','http://localhost:18081');key=os.environ['EHR_OCR_WORKER_TOKEN'];password=os.environ['EHR_TEST_PASSWORD']
web=requests.Session();web.trust_env=False;worker=requests.Session();worker.trust_env=False;checks=[];created=None
def check(n,value=True):assert value,n;checks.append(n);print('PASS',n,flush=True)
web.get(base+'/login',timeout=10);assert web.post(base+'/login',data={'username':'医生医生','password':password,'rememberMe':'false','code':'123465'},timeout=20).json()['code']==200
ctx=web.get(base+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic';csrf={'X-CSRF-Token':ctx['csrfToken']}
def admin(method,path,body=None):
 r=web.request(method,base+'/ehr/api'+path,json=body,headers=csrf,timeout=30);assert r.status_code==200,(path,r.status_code,r.text[:250]);return r.json()['data']
def call(method,path,body=None,token=None,status=200):
 h={'Authorization':'Bearer '+key}
 if token:h['X-OCR-Lease']=token
 r=worker.request(method,base+'/ehr/worker'+path,json=body,headers=h,timeout=60);assert r.status_code==status,(path,r.status_code,r.text[:350]);return r.json()['data'] if status==200 else r.json()
def claim():return call('POST','/tasks/claim',{'workerId':'b-full-json-replay-check','requestKey':str(uuid.uuid4())})['task']
def path(t,end):return '/tasks/'+t['jobId']+'/'+end
c=connect()
with c.cursor() as q:
 q.execute("SELECT source_file_id,object_ref FROM a_report_sources WHERE data_mode='real' AND sha256=UNHEX(%s)",(original['source_files'][0]['sha256'],));saved=q.fetchone()
c.close();assert saved,'Original A file matching the export hash is required for this local replay'
ref=bytes(saved['object_ref']).decode();storage=Path('F:/upload').resolve();file=(storage/ref).resolve();assert file.parent==storage
raw=file.read_bytes();assert hashlib.sha256(raw).hexdigest()==original['source_files'][0]['sha256'].lower()
try:
 created=admin('POST','/records',{'name':'B回传格式联调职工','code':'BREPLAY_'+uuid.uuid4().hex[:15],'sex':'未填写','org':'隔离格式验证单位','team':'接口验证','job':'验证岗位'})
 r=web.post(base+'/ehr/api/records/'+created['id']+'/reports',headers=dict(csrf,**{'Idempotency-Key':str(uuid.uuid4())}),files={'file':('b-contract-replay.pdf',raw,'application/pdf')},timeout=30);assert r.status_code==200,r.text[:250];report=r.json()['data']
 t=claim();check('replay_claims_own_synthetic_task',t is not None and t['jobId']==report['jobId'])
 inp=call('GET',path(t,'input'),token=t['leaseToken']);check('input_has_canonical_source_hash_and_pages',inp['source_files'][0]['sha256']==original['source_files'][0]['sha256'].lower() and inp['files'][0]['sha256']==inp['source_files'][0]['sha256'] and inp['source_files'][0]['page_count']==12)
 unchanged=json.dumps(original,ensure_ascii=False,sort_keys=True)
 bound=module.bind_to_task(original,inp)
 check('binder_keeps_original_export_unchanged',unchanged==json.dumps(original,ensure_ascii=False,sort_keys=True))
 check('binder_uses_A_authorized_context',bound['scope_code']==ctx['scope'] and bound['data_mode']=='synthetic' and bound['record_id']==created['id'] and bound['report_id']==report['id'] and bound['revision']==1)
 check('binder_maps_all_evidence_to_verified_A_source',all(e['source_file_id']==inp['source_files'][0]['source_file_id'] for e in bound['evidence']))
 mismatch=deepcopy(original);mismatch['source_files'][0]['sha256']='0'*64
 try:module.bind_to_task(mismatch,inp);raise AssertionError('Expected hash mismatch')
 except ValueError:check('binder_refuses_unmatched_file_hash')
 # Each failed candidate must roll back and provide the precise reason.
 cases=[]
 bad=deepcopy(bound);bad['scope_code']='DEMO_ONLY';cases.append(('demo_context',bad))
 bad=deepcopy(bound);bad['items'][0]['scope_code']='org:other';cases.append(('nested_scope',bad))
 bad=deepcopy(bound);bad['items'][0]['revision']=2;cases.append(('nested_revision',bad))
 bad=deepcopy(bound);bad['source_files'][0]['sha256']='0'*64;cases.append(('source_hash',bad))
 bad=deepcopy(bound);bad['source_files'][0]['page_count']=13;cases.append(('source_page_count',bad))
 bad=deepcopy(bound);bad['record_id']=str(uuid.uuid4());cases.append(('record_binding',bad))
 bad=deepcopy(bound);bad['dictionary_version']='9.9.9';cases.append(('dictionary_version',bad))
 bad=deepcopy(bound);bad['items'][1]['ordinal']=bad['items'][0]['ordinal'];cases.append(('duplicate_ordinal',bad))
 bad=deepcopy(bound);bad['advice'][1]['advice_id']=bad['advice'][0]['advice_id'];cases.append(('duplicate_advice_id',bad))
 for label,bad in cases:
  error=call('POST',path(t,'result'),bad,t['leaseToken'],422)
  failed=admin('GET','/ocr/tasks/'+t['jobId']);read=admin('GET','/reports/'+report['id']+'/1')
  check('rejects_'+label+'_without_partial_write',failed['state']=='failed' and read['items']==[] and failed['errorMessage'].endswith(error['msg']))
  admin('POST','/ocr/tasks/'+t['jobId']+'/retry',{'requestKey':str(uuid.uuid4()),'note':'隔离回放验证：修正当前报文后继续'});t=claim();assert t['jobId']==report['jobId']
 result=call('POST',path(t,'result'),bound,t['leaseToken']);check('full_B_export_received_as_candidate',result['ocrTask']['state']=='succeeded' and result['reviewStatus']=='needs_review' and not result['published'] and result['examDate'] is None)
 check('all_B_result_counts_preserved',len(result['fieldValues'])==68 and len(result['items'])==102 and len(result['conclusions'])==1 and len(result['advice'])==7)
 expected={row['item_id']:row for row in bound['items']}
 check('item_ids_and_raw_measurements_preserved',all(row['id'] in expected and row['value']==expected[row['id']]['result_raw'] and row['unit']==expected[row['id']]['unit_raw'] and row['referenceRange']==expected[row['id']]['reference_range_raw'] for row in result['items']))
 c=connect()
 with c.cursor() as q:
  q.execute('SELECT COUNT(*) n FROM a_report_evidence WHERE report_id=%s AND revision=1',(report['id'],));check('all_458_source_evidence_rows_saved',q.fetchone()['n']==458)
  q.execute('SELECT conclusion_id FROM a_health_conclusions WHERE report_id=%s AND revision=1',(report['id'],));check('B_conclusion_id_preserved',{r['conclusion_id'] for r in q.fetchall()}=={r['conclusion_id'] for r in bound['conclusions']})
  q.execute('SELECT advice_id FROM a_health_advice WHERE report_id=%s AND revision=1',(report['id'],));check('B_advice_ids_preserved',{r['advice_id'] for r in q.fetchall()}=={r['advice_id'] for r in bound['advice']})
  q.execute('SELECT item_id,ordinal FROM a_health_items WHERE report_id=%s AND revision=1',(report['id'],));check('B_item_ordinals_preserved',all(r['ordinal']==expected[r['item_id']]['ordinal'] for r in q.fetchall()))
  q.execute('SELECT response_json FROM a_ehr_ocr_receipts WHERE job_id=%s',(t['jobId'],));receipt=json.loads(q.fetchone()['response_json']);check('complete_B_envelope_retained_in_receipt',receipt==bound)
 c.close()
 repeat=call('POST',path(t,'result'),bound,t['leaseToken']);check('full_B_export_replay_is_idempotent',len(repeat['items'])==102)
 check('identity_conflict_remains_for_doctor_review',result['qualityChecks'][0]['identityStatus']=='conflict')
 out={'checks':checks,'count':len(checks),'mode':'synthetic','sourceExportSha256':hashlib.sha256(source.read_bytes()).hexdigest(),'counts':{k:len(original[k]) for k in ['field_values','items','evidence','conclusions','advice']},'distinctReportFieldKeys':len({f['field_key'] for f in original['field_values']}),'realBusinessDataModified':False,'realOcrEngineCalled':False,'jobId':t['jobId'],'originalStandaloneContextImportedAsReal':False}
 (ROOT/'database/b-ocr-replay-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS full B result replay:',len(checks),'checks',flush=True)
finally:
 if created:
  p=admin('GET','/records/'+created['id']);admin('DELETE','/records/'+created['id'],{'rowVersion':p['rowVersion'],'reason':'B端回传格式隔离回放完成，原件和审核历史保留'})
