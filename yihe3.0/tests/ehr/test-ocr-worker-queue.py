"""A-side OCR worker contract integration; uses synthetic fixtures, not an OCR engine."""
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor
import requests,os,uuid,io,json,sys,time
from PIL import Image
from PIL.PngImagePlugin import PngInfo
from reportlab.pdfgen import canvas
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
B=os.getenv('EHR_TEST_URL','http://localhost:18081');key=os.environ['EHR_OCR_WORKER_TOKEN'];worker_headers={'Authorization':'Bearer '+key};checks=[];created=[];run=uuid.uuid4().hex
s=requests.Session();s.trust_env=False;http=requests.Session();http.trust_env=False
s.get(B+'/login',timeout=10);assert s.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20).json()['code']==200
ctx=s.get(B+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic';csrf={'X-CSRF-Token':ctx['csrfToken']}
def check(n,v=True):assert v,n;checks.append(n);print('PASS',n,flush=True)
def admin(method,path,body=None,status=200):
 r=s.request(method,B+'/ehr/api'+path,json=body,headers=csrf,timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:400]);return r.json().get('data') if status==200 else r.json()
def worker(method,path,body=None,lease=None,status=200,headers=None):
 h=dict(worker_headers);h.update({'X-OCR-Lease':lease} if lease else {});h.update(headers or {});r=http.request(method,B+'/ehr/worker'+path,json=body,headers=h,timeout=35);assert r.status_code==status,(path,r.status_code,r.text[:500]);return r.json().get('data') if status==200 else r.json()
def claim(name=None,requestKey=None):return worker('POST','/tasks/claim',{'workerId':name or 'test-'+uuid.uuid4().hex[:10],'requestKey':requestKey or str(uuid.uuid4())})['task']
def path(t,end):return '/tasks/'+t['jobId']+'/'+end
def report(n,kind='PNG'):
 meta=PngInfo();meta.add_text('syntheticRun',run+str(n));buf=io.BytesIO()
 if kind=='PDF':
  c=canvas.Canvas(buf);c.drawString(30,750,'SYNTHETIC OCR QUEUE '+run+str(n));c.showPage();c.drawString(30,750,'SECOND SYNTHETIC PAGE');c.showPage();c.save();mime='application/pdf';extension='pdf'
 elif kind=='JPEG':Image.new('RGB',(31,29),(int(run[:2],16),n%255,111)).save(buf,format='JPEG');mime='image/jpeg';extension='jpg'
 else:Image.new('RGB',(20+n,24),(37,184,155)).save(buf,format='PNG',pnginfo=meta);mime='image/png';extension='png'
 raw=buf.getvalue();request=str(uuid.uuid4());r=s.post(B+'/ehr/api/records/'+p['id']+'/reports',headers=dict(csrf,**{'Idempotency-Key':request}),files={'file':('queue-'+str(n)+'.'+extension,raw,mime)},timeout=30);assert r.status_code==200,r.text;return r.json()['data'],raw,request,mime
def payload(r,name=None):
 eid=str(uuid.uuid4());return {'contract_version':'2.0.0','scope_code':ctx['scope'],'data_mode':'synthetic','report_kind':'individual','report_id':r['id'],'revision':r['revision'],'institution_raw':'隔离OCR契约测试机构','exam_date_raw':'2026年11月15日','evidence':[{'evidence_id':eid,'source_file_id':r['files'][0]['id'],'page_number':1,'coordinate_system':'object_only','bbox':None,'page_rotation':0,'original_label':'原文测试项目','raw_fragment':'SYNTHETIC RAW VALUE','extraction_method':'ocr','extractor_version':'fixture-v1'}],'field_values':[{'field_key':'full_name','original_field_name':'姓名','value_raw':name or p['name'],'evidence_ids':[eid]},{'field_key':'job_type','original_field_name':'工种','value_raw':'工种原文','evidence_ids':[eid]}],'items':[{'original_field_name':'测试复合原值','item_code':item_code,'primary_evidence_id':eid,'evidence_ids':[eid],'result_raw':'＜120/80（原文）','unit_raw':' mmHg ','reference_range_raw':'原文参考范围','abnormal_flag_raw':'原文标记','field_values':[]}],'conclusions':[{'original_field_name':'正式结论','original_text':'仅用于接口验证，不构成临床结论。','primary_evidence_id':eid,'evidence_ids':[eid],'completeness':'complete'}],'advice':[{'original_field_name':'建议原文','source_evidence_id':eid,'text_raw':'仅用于接口验证的原文建议'}]}
def expire(t):
 db=connect()
 with db.cursor() as q:q.execute("UPDATE a_ocr_dispatch_tasks SET lease_until=UTC_TIMESTAMP(6)-INTERVAL 2 SECOND WHERE job_id=%s AND data_mode='synthetic'",(t['jobId'],))
 db.commit();db.close()
def ready(t):
 db=connect()
 with db.cursor() as q:q.execute("UPDATE a_ocr_dispatch_tasks SET available_at=UTC_TIMESTAMP(6)-INTERVAL 1 SECOND WHERE job_id=%s AND data_mode='synthetic'",(t['jobId'],))
 db.commit();db.close()
try:
 worker('POST','/tasks/claim',{'workerId':'invalid','requestKey':str(uuid.uuid4())},status=401,headers={'Authorization':'Bearer wrong'});check('worker_authentication_required')
 assert http.get(B+'/ehr/api/ocr/tasks',timeout=10).status_code==401;check('anonymous_task_list_401')
 dictionary=worker('GET','/dictionary');check('versioned_67_fields_655_items',len(dictionary['standardFields'])==67 and len(dictionary['medicalItems'])==655);item_code=dictionary['medicalItems'][0]['item_code']
 p=admin('POST','/records',{'name':'OCR队列测试职工','code':'OCRQ_'+run[:14],'sex':'未填写','org':'隔离队列测试单位','team':'测试班组','job':'测试岗位'});created.append(p['id'])
 r,raw,request,mime=report(1);job=r['jobId'];check('upload_atomically_creates_durable_queue',r['ocrTask']['state']=='queued' and r['ocrTask']['jobId']==job)
 u=s.post(B+'/ehr/api/records/'+p['id']+'/reports',headers=dict(csrf,**{'Idempotency-Key':request}),files={'file':('same.png',raw,mime)},timeout=25).json()['data'];check('upload_retry_reuses_same_task',u['jobId']==job)
 ckey=str(uuid.uuid4());t=claim('fixture-main',ckey);check('worker_claims_uploaded_job',t['jobId']==job and t['leaseToken'])
 again=claim('fixture-main',ckey);check('claim_network_retry_returns_same_lease',again['attemptId']==t['attemptId'] and again['leaseToken']==t['leaseToken'])
 check('other_worker_cannot_claim_running_job',claim('fixture-idle') is None)
 worker('GET',path(t,'input'),status=401);check('input_requires_current_lease')
 inp=worker('GET',path(t,'input'),lease=t['leaseToken']);check('input_contains_network_download_paths',inp['files'][0]['downloadPath'].startswith('/ehr/worker/') and 'F:' not in json.dumps(inp))
 check('input_includes_frozen_task_context',inp['scope_code']==t['scope_code']==ctx['scope'] and inp['data_mode']==t['data_mode']=='synthetic' and inp['report_kind']=='individual' and inp['contract_version']==inp['contractVersion']=='2.0.0' and inp['report_id']==inp['reportId']==r['id'] and inp['job_id']==job and inp['record_id']==p['id'])
 response=http.get(B+inp['files'][0]['downloadPath'],headers=dict(worker_headers,**{'X-OCR-Lease':t['leaseToken']}),timeout=20);check('worker_download_preserves_source_bytes',response.status_code==200 and response.content==raw and 'no-store' in response.headers['Cache-Control'])
 worker('GET',path(t,'files/'+str(uuid.uuid4())),lease=t['leaseToken'],status=404);check('file_must_belong_to_claimed_task')
 for stage,percent in [('pdf_parse',10),('ocr',30),('field_extraction',60),('item_matching',80),('validation',95)]:worker('POST',path(t,'heartbeat'),{'stage':stage,'percent':percent},t['leaseToken'])
 check('heartbeat_reports_progress_and_renews_lease',admin('GET','/ocr/tasks/'+job)['percent']==95)
 worker('POST',path(t,'heartbeat'),{'stage':'pdf_parse','percent':10},t['leaseToken'],409);worker('POST',path(t,'heartbeat'),{'stage':'validation','percent':100},t['leaseToken'],422);check('progress_cannot_regress_or_claim_completion')
 public=admin('GET','/ocr/tasks/'+job);check('admin_task_does_not_expose_worker_credentials','leaseToken' not in json.dumps(public) and key not in json.dumps(public))
 b=payload(r);result=worker('POST',path(t,'result'),b,t['leaseToken']);check('valid_worker_result_stored_as_candidate',result['reviewStatus']=='needs_review' and result['examDate'] is None and not result['published'])
 check('raw_values_units_evidence_preserved',result['items'][0]['value']=='＜120/80（原文）' and result['items'][0]['unit']==' mmHg ' and result['items'][0]['page']==1)
 check('success_sets_task_100_after_database_commit',result['ocrTask']['state']=='succeeded' and result['ocrTask']['percent']==100)
 check('automatic_quality_never_approves_medical_data',result['qualityChecks'][0]['status']=='needs_review' and result['qualityChecks'][0]['identityStatus']=='matched')
 worker('POST',path(t,'result'),b,t['leaseToken']);check('identical_result_replay_is_idempotent')
 receipt=worker('GET',path(t,'receipt'),lease=t['leaseToken'])
 check('receipt_confirms_candidate_persisted',receipt['accepted'] and receipt['state']=='succeeded' and receipt['reviewStatus']=='needs_review' and not receipt['published'] and receipt['data_mode']=='synthetic')
 check('receipt_counts_and_digest_are_available',receipt['counts']['health_items']==1 and receipt['counts']['report_evidence']==1 and len(receipt['receipt']['payloadSha256'])==64)
 worker('GET',path(t,'receipt'),status=401);check('receipt_requires_matching_lease')
 wrong_context=dict(b,data_mode='real');worker('POST',path(t,'result'),wrong_context,t['leaseToken'],422);check('rejected_context_replay_preserves_accepted_result',admin('GET','/ocr/tasks/'+job)['state']=='succeeded')
 bad=json.loads(json.dumps(b));bad['items'][0]['result_raw']='different';worker('POST',path(t,'result'),bad,t['leaseToken'],409);check('conflicting_result_replay_cannot_overwrite')
 r2,_,_,_=report(2,'PDF');t2=claim();input2=worker('GET',path(t2,'input'),lease=t2['leaseToken']);check('multi_page_pdf_supported',input2['files'][0]['pageCount']==2)
 for label,change in [('missing_scope',{'scope_code':None}),('missing_data_mode',{'data_mode':None}),('wrong_scope',{'scope_code':'org:other'}),('wrong_data_mode',{'data_mode':'real'}),('wrong_report_kind',{'report_kind':'organization'})]:
  invalid=payload(r2);invalid.update(change);error=worker('POST',path(t2,'result'),invalid,t2['leaseToken'],422)
  check('context_rejects_'+label+'_without_writing_candidate',admin('GET','/reports/'+r2['id']+'/1')['items']==[] and admin('GET','/ocr/tasks/'+t2['jobId'])['state']=='failed')
  failed_context=admin('GET','/ocr/tasks/'+t2['jobId']);check('diagnostic_reason_retained_'+label,failed_context['errorMessage'].endswith(error['msg']) and failed_context['attempts'][0]['errorMessage']==failed_context['errorMessage'])
  admin('POST','/ocr/tasks/'+t2['jobId']+'/retry',{'requestKey':str(uuid.uuid4()),'note':'隔离测试修正任务上下文'});t2=claim()
 invalid=payload(r2);invalid['items'][0]['result_normalized']={'value':100};error=worker('POST',path(t2,'result'),invalid,t2['leaseToken'],422)
 failed=admin('GET','/ocr/tasks/'+t2['jobId']);check('invalid_result_rolls_back_and_marks_failed',failed['state']=='failed' and failed['errorCode']=='INVALID_RESULT' and admin('GET','/reports/'+r2['id']+'/1')['items']==[])
 check('invalid_result_preserves_specific_validation_reason',failed['errorMessage'].endswith(error['msg']) and 'result_normalized' in failed['errorMessage'])
 retry={'requestKey':str(uuid.uuid4()),'note':'已修正回传契约，重新识别'};admin('POST','/ocr/tasks/'+t2['jobId']+'/retry',retry);admin('POST','/ocr/tasks/'+t2['jobId']+'/retry',retry);check('manual_retry_is_idempotent_and_keeps_history',admin('GET','/ocr/tasks/'+t2['jobId'])['attemptCount']==6)
 t2new=claim();worker('POST',path(t2,'heartbeat'),{'stage':'ocr','percent':1},t2['leaseToken'],401);check('new_attempt_fences_old_worker')
 conflict=worker('POST',path(t2new,'result'),payload(r2,'与所选职工不同的姓名'),t2new['leaseToken']);check('identity_conflict_preserved_for_doctor_review',conflict['qualityChecks'][0]['identityStatus']=='conflict' and not conflict['published'])
 r3,_,_,_=report(3,'JPEG');t3=claim();f={'errorCode':'TEMPORARY_UNAVAILABLE','message':'隔离测试：识别服务暂不可用','retryable':True};ret=worker('POST',path(t3,'failure'),f,t3['leaseToken']);worker('POST',path(t3,'failure'),f,t3['leaseToken']);check('transient_failure_backoff_and_idempotent_receipt',ret['state']=='retry_wait' and claim() is None)
 ready(t3);t3b=claim();worker('POST',path(t3b,'failure'),dict(f,retryable=False),t3b['leaseToken']);check('non_retryable_failure_stops_automatic_retries',admin('GET','/ocr/tasks/'+t3b['jobId'])['state']=='failed')
 admin('POST','/ocr/tasks/'+t3b['jobId']+'/retry',{'note':'隔离测试重试','requestKey':str(uuid.uuid4())});admin('POST','/ocr/tasks/'+t3b['jobId']+'/cancel',{'note':'隔离测试停止，保留原件','requestKey':str(uuid.uuid4())});check('queued_task_can_be_cancelled_without_file_deletion',admin('GET','/ocr/tasks/'+t3b['jobId'])['state']=='cancelled')
 r4,_,_,_=report(4);t4=claim();expire(t4);worker('POST',path(t4,'heartbeat'),{'stage':'ocr','percent':1},t4['leaseToken'],409);check('expired_worker_lease_is_rejected')
 deadline=time.monotonic()+12
 while time.monotonic()<deadline:
  if admin('GET','/ocr/tasks/'+t4['jobId'])['state']=='retry_wait':break
  time.sleep(.3)
 check('a_side_scheduler_recovers_expired_lease',admin('GET','/ocr/tasks/'+t4['jobId'])['state']=='retry_wait')
 t4b=claim();check('expired_attempt_retained_on_takeover',t4b['attemptId']!=t4['attemptId'] and admin('GET','/ocr/tasks/'+t4b['jobId'])['attempts'][-1]['outcome']=='expired')
 worker('POST',path(t4,'result'),payload(r4),t4['leaseToken'],401);check('late_result_from_replaced_worker_rejected')
 expire(t4b);t4c=claim();expire(t4c);check('retry_budget_is_finite',claim() is None and admin('GET','/ocr/tasks/'+t4['jobId'])['state']=='failed')
 r5,_,_,_=report(5);r6,_,_,_=report(6)
 def parallel(i):
  h=requests.Session();h.trust_env=False;r=h.post(B+'/ehr/worker/tasks/claim',headers=worker_headers,json={'workerId':'parallel-'+str(i),'requestKey':str(uuid.uuid4())},timeout=30);assert r.status_code==200,r.text;return r.json()['data']['task']
 with ThreadPoolExecutor(max_workers=2) as pool:claimed=list(pool.map(parallel,[1,2]))
 check('concurrent_workers_claim_distinct_tasks',{t['jobId'] for t in claimed}=={r5['jobId'],r6['jobId']})
 five=next(t for t in claimed if t['jobId']==r5['jobId']);six=next(t for t in claimed if t['jobId']==r6['jobId'])
 latest=admin('GET','/reports/'+r5['id']+'/1');manual={'fullName':p['name'],'examDate':'2026-11-15','institution':'人工测试','formalConclusion':'人工保留原文','advice':'','items':[{'name':'人工项目','value':'人工原值','unit':'','page':1}]}
 rr=s.put(B+'/ehr/api/reports/'+r5['id']+'/1',json=manual,headers=dict(csrf,**{'If-Match':latest['etag']}),timeout=30);assert rr.status_code==200,rr.text
 worker('POST',path(five,'result'),payload(r5),five['leaseToken'],409);check('late_ocr_does_not_overwrite_manual_candidate',admin('GET','/reports/'+r5['id']+'/1')['items'][0]['value']=='人工原值')
 admin('POST','/ocr/tasks/'+six['jobId']+'/cancel',{'note':'隔离测试停止正在识别的任务','requestKey':str(uuid.uuid4())});worker('POST',path(six,'result'),payload(r6),six['leaseToken'],409);check('cancel_fences_inflight_result')
 check('queue_history_survives_task_completion',len(admin('GET','/ocr/tasks/'+job)['attempts'])==1 and admin('GET','/ocr/tasks?state=succeeded')['total']>=2)
 # Unknown is a valid OCR uncertainty, not a different organization's report.
 r7,_,_,_=report(7);t7=claim();unknown=dict(payload(r7),report_kind='unknown')
 received=worker('POST',path(t7,'result'),unknown,t7['leaseToken'])
 check('unknown_kind_saved_as_unapproved_candidate',received['reviewStatus']=='needs_review' and not received['published'] and received['requiresReportKindConfirmation'])
 check('unknown_kind_has_explicit_quality_issue',any('REPORT_KIND_UNCERTAIN' in q['issues'] for q in received['qualityChecks']))
 manual7=dict(manual,fullName=p['name']);rr=s.put(B+'/ehr/api/reports/'+r7['id']+'/1',json=manual7,headers=dict(csrf,**{'If-Match':received['etag']}),timeout=30);assert rr.status_code==200,rr.text
 now=admin('GET','/reports/'+r7['id']+'/1');admin('POST','/reports/'+r7['id']+'/1/actions',{'action':'submit','requestKey':str(uuid.uuid4()),'note':'synthetic original verified'})
 now=admin('GET','/reports/'+r7['id']+'/1');approval={'action':'approve','requestKey':str(uuid.uuid4()),'note':'synthetic type verification','identityVerified':True,'etag':now['etag']}
 admin('POST','/reports/'+r7['id']+'/1/actions',approval,status=422);check('unknown_kind_cannot_be_approved_without_type_confirmation',admin('GET','/reports/'+r7['id']+'/1')['reviewStatus']=='needs_review')
 approved=admin('POST','/reports/'+r7['id']+'/1/actions',dict(approval,reportKindVerified=True));check('doctor_can_explicitly_resolve_unknown_kind',approved['reviewStatus']=='confirmed' and not approved['requiresReportKindConfirmation'] and not approved['published'])
 disposition=approved['reviews'][-1]['issues'];disposition=json.loads(disposition) if isinstance(disposition,str) else disposition
 check('type_confirmation_is_audited',disposition['reportKindVerified'] is True)
 # Finish the doctor-only publication path for a successful worker result.
 rnow=admin('GET','/reports/'+r['id']+'/1');manual['formalConclusion']='人工核对后的正式原文';rr=s.put(B+'/ehr/api/reports/'+r['id']+'/1',json=manual,headers=dict(csrf,**{'If-Match':rnow['etag']}),timeout=30);assert rr.status_code==200,rr.text
 for action in ['submit','approve','archive']:
  now=admin('GET','/reports/'+r['id']+'/1');admin('POST','/reports/'+r['id']+'/1/actions',{'action':action,'note':'隔离测试人工核对原件','identityVerified':True,'requestKey':str(uuid.uuid4()),'etag':now['etag']})
 check('doctor_review_and_archive_still_required',admin('GET','/records/'+p['id'])['publishedReports'][0]['published'])
 out={'mode':'synthetic','checks':checks,'count':len(checks),'realOcrEngineCalled':False,'realEmployeeModified':False};(ROOT/'database/ocr-worker-queue-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS OCR worker queue:',len(checks),'checks',flush=True)
finally:
 for rid in created:
  current=admin('GET','/records/'+rid);admin('DELETE','/records/'+rid,{'rowVersion':current['rowVersion'],'reason':'synthetic OCR调度测试完成，原件及历史保留'})
