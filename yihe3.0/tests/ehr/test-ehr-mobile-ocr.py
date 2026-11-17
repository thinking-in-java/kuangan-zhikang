"""Synthetic-only integration check. See tests/ehr/README.md."""
from pathlib import Path
import requests,os,uuid,json,io,hashlib,sys
from PIL import Image
from PIL.PngImagePlugin import PngInfo
BASE=os.environ.get('EHR_TEST_URL','http://localhost:18081')
web=requests.Session();web.trust_env=False;mobile=requests.Session();mobile.trust_env=False;checks=[]
def check(label,value=True):
    assert value,label;checks.append(label);print('PASS',label,flush=True)
web.get(BASE+'/login',timeout=10)
r=web.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20);assert r.json()['code']==200
ctx=web.get(BASE+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic';csrf={'X-CSRF-Token':ctx['csrfToken']}
def w(method,path,body=None,expect=200):
 r=web.request(method,BASE+'/ehr/api'+path,json=body,headers=csrf,timeout=30);assert r.status_code==expect,(path,r.status_code,r.text[:500]);return r.json().get('data')
r=mobile.get(BASE+'/ehr/mobile/me',headers={'Authorization':'Bearer DA000087'},timeout=10);check('legacy_user_code_is_not_bearer',r.status_code==401)
r=mobile.post(BASE+'/ehr/mobile/session',json={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD']},timeout=20);assert r.status_code==200,r.text;token=r.json()['data']['token'];auth={'Authorization':'Bearer '+token}
r=mobile.get(BASE+'/ehr/mobile/me',headers=auth,timeout=20);assert r.status_code==200,r.text;own=r.json()['data'];check('mobile_authenticated_self_roster',own['legacyUserCode']=='DA000087')
meta=PngInfo();meta.add_text('syntheticTestId',str(uuid.uuid4()));buf=io.BytesIO();Image.new('RGB',(12,12),(130,200,179)).save(buf,format='PNG',pnginfo=meta);content=buf.getvalue()
headers=dict(auth,**{'Idempotency-Key':str(uuid.uuid4())})
r=mobile.post(BASE+'/ehr/mobile/reports',headers=headers,files={'file':('self-test.png',content,'image/png')},timeout=25);assert r.status_code==200,r.text;report=r.json()['data'];job=report['jobId'];rid=report['id'];check('mobile_png_upload_bound_to_self',report['recordId']==own['id'])
r=mobile.post(BASE+'/ehr/mobile/reports',headers=headers,files={'file':('self-test.png',content,'image/png')},timeout=25);check('mobile_upload_idempotency',r.status_code==200 and r.json()['data']['id']==rid)
r=mobile.get(BASE+'/ehr/mobile/files/'+report['files'][0]['id'],headers=auth,timeout=10);check('mobile_private_file_bytes',r.content==content)
other=w('POST','/records',{'name':'OCR回调联调职工','code':'OCR_'+uuid.uuid4().hex[:12],'sex':'未填写','org':'接口测试单位','team':'测试班组','job':'测试岗位'})
buf2=io.BytesIO();Image.new('RGB',(13,13),(45,120,190)).save(buf2,format='PNG',pnginfo=meta)
r=web.post(BASE+'/ehr/api/records/'+other['id']+'/reports',headers=dict(csrf,**{'Idempotency-Key':str(uuid.uuid4())}),files={'file':('other-test.png',buf2.getvalue(),'image/png')},timeout=25);assert r.status_code==200,r.text;other_report=r.json()['data']
r=mobile.get(BASE+'/ehr/mobile/reports/'+other_report['id']+'/1',headers=auth,timeout=10);check('mobile_cannot_read_other_employee',r.status_code==404)
r=mobile.get(BASE+'/ehr/mobile/files/'+other_report['files'][0]['id'],headers=auth,timeout=10);check('mobile_cannot_download_other_employee',r.status_code==404)
r=web.post(BASE+'/ehr/api/records/'+other['id']+'/reports',headers=dict(csrf,**{'Idempotency-Key':str(uuid.uuid4())}),files={'file':('pretend.pdf',b'not a PDF','application/pdf')},timeout=20);check('file_magic_validation',r.status_code==422)
r=web.post(BASE+'/ehr/api/reports/'+other_report['id']+'/1/ocr',headers=csrf,timeout=15);check('manual_ocr_restart_queues_durable_job',r.status_code==200 and r.json()['data']['status']=='queued')
callback_token=os.environ['EHR_OCR_CALLBACK_TOKEN'];oh={'Authorization':'Bearer '+callback_token}
eid=str(uuid.uuid4());body={'contract_version':'2.0.0','report_id':rid,'revision':1,'institution_raw':'OCR接口测试来源','exam_date_raw':'2026年11月14日','evidence':[{'evidence_id':eid,'source_file_id':report['files'][0]['id'],'page_number':1,'bbox':[0,0,12,12],'coordinate_system':'image_pixels_top_left','page_rotation':0,'render_dpi':96,'original_label':'测试检查项目','raw_fragment':'SYNTHETIC OCR CALLBACK FIXTURE','extraction_method':'ocr','extractor_version':'test-provider-v1','ocr_confidence':0.95}],'field_values':[{'field_key':'full_name','original_field_name':'姓名','value_raw':own['name'],'value_normalized':None,'normalization_rule_version':None,'evidence_ids':[eid],'review_status':'unreviewed'},{'field_key':'job_type','original_field_name':'工种','value_raw':'测试岗位原文','value_normalized':None,'normalization_rule_version':None,'evidence_ids':[eid],'review_status':'unreviewed'}],'items':[{'item_id':str(uuid.uuid4()),'original_field_name':'原始体重指数','item_code':'MI_cee32d541586d6b83cc3','primary_evidence_id':eid,'evidence_ids':[eid],'result_raw':'26.40','unit_raw':' kg/m² ','reference_range_raw':'原文范围（测试）','abnormal_flag_raw':None,'field_values':[]}],'conclusions':[{'original_field_name':'正式体检结论','original_text':'仅用于回调接口测试，不构成医学结论。','primary_evidence_id':eid,'evidence_ids':[eid],'completeness':'complete','standard_category':None}],'advice':[]}
endpoint=BASE+'/ehr/integration/jobs/'+job+'/result'
r=web.post(endpoint,json=body,timeout=20);check('ocr_callback_auth_required',r.status_code==401)
bad=json.loads(json.dumps(body));bad['items'][0]['result_normalized']={'numeric':26.4}
r=web.post(endpoint,json=bad,headers=oh,timeout=20);check('ocr_unapproved_derived_value_rejected',r.status_code==422)
r=web.post(endpoint,json=body,headers=oh,timeout=20);assert r.status_code==200,r.text;parsed=r.json()['data'];check('ocr_raw_unit_and_string_preserved',parsed['items'][0]['value']=='26.40' and parsed['items'][0]['unit']==' kg/m² ')
check('ocr_not_auto_approved_or_date_verified',parsed['reviewStatus']=='needs_review' and parsed['examDate'] is None and not parsed['published'])
r=web.post(endpoint,json=body,headers=oh,timeout=20);check('ocr_callback_replay_idempotent',r.status_code==200)
bad=json.loads(json.dumps(body));bad['items'][0]['result_raw']='99'
r=web.post(endpoint,json=bad,headers=oh,timeout=20);check('ocr_conflicting_replay_409',r.status_code==409)
r=web.get(BASE+'/ehr/integration/jobs/'+job+'/input',headers=oh,timeout=10);check('ocr_input_protected_and_correlated',r.status_code==200 and r.json()['data']['reportId']==rid)
draft={'fullName':other['name'],'examDate':'2026-11-14','institution':'test','formalConclusion':'Synthetic only','advice':'','items':[{'name':'Synthetic raw item','value':'negative','unit':'','page':1}]}
r=web.put(BASE+'/ehr/api/reports/'+other_report['id']+'/1',headers=dict(csrf,**{'If-Match':other_report['etag']}),json=draft,timeout=20);assert r.status_code==200,r.text
r=web.post(BASE+'/ehr/integration/jobs/'+other_report['jobId']+'/result',headers=oh,json=body,timeout=20);check('late_ocr_does_not_overwrite_manual_candidate',r.status_code==409)
r=mobile.delete(BASE+'/ehr/mobile/session',headers=auth,timeout=10);check('mobile_logout',r.status_code==200)
r=mobile.get(BASE+'/ehr/mobile/me',headers=auth,timeout=10);check('revoked_bearer_rejected',r.status_code==401)
# Cleanup only our active synthetic fixtures, with normal API soft deletion.
for p in w('GET','/records?limit=100')['items']:
 if p['code'].startswith(('TEST_','OCR_')) and (p['name'].startswith('API联调职工') or p['name']=='OCR回调联调职工'):
  d=w('GET','/records/'+p['id']);w('DELETE','/records/'+p['id'],{'reason':'synthetic自动化验证完成','rowVersion':d['rowVersion']})
out={'mode':'synthetic','checks':checks,'count':len(checks),'ownRecordId':own['id'],'ocrReportId':rid}
Path(str(Path(__file__).resolve().parents[2])+'/database/ehr-mobile-ocr-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS mobile/OCR:',len(checks),'checks')
