"""Synthetic-only HTTP integration against the local WeChat provider stub.
Run the test app with EHR_WECHAT_TEST_URL pointing to the loopback stub.
No real employee password, WeChat account or production archive is modified.
"""
from pathlib import Path
import os,uuid,json,io,sys
import requests
from PIL import Image
from PIL.PngImagePlugin import PngInfo
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
B=os.getenv('EHR_TEST_URL','http://localhost:18081');web=requests.Session();web.trust_env=False;http=requests.Session();http.trust_env=False;checks=[];created=[];run='wxitest-'+uuid.uuid4().hex[:14]
def check(n,b=True):assert b,n;checks.append(n);print('PASS',n,flush=True)
def call(method,path,body=None,headers=None,status=200):
 r=http.request(method,B+path,json=body,headers=headers or {},timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:300]);j=r.json();return j.get('data') if status==200 else j
web.get(B+'/login',timeout=10);r=web.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20);assert r.json()['code']==200
ctx=web.get(B+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic';csrf={'X-CSRF-Token':ctx['csrfToken']}
def admin(method,path,body=None,status=200):
 r=web.request(method,B+'/ehr/api'+path,json=body,headers=csrf,timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:300]);return r.json().get('data') if status==200 else r.json()
config=call('GET','/ehr/miniapp/config');check('isolated_wechat_provider_configured',config['wechatConfigured'])
def login(who,code=None):
 return call('POST','/ehr/miniapp/wechat/login',{'code':code or run+'.'+who+'.'+uuid.uuid4().hex,'privacyAccepted':True,'consentVersion':config['consentVersion']})
def auth(u):return {'Authorization':'Bearer '+u['token']}
def issue(p):return admin('POST','/records/'+p['id']+'/binding-code',{'requestKey':str(uuid.uuid4()),'expiresIn':3600})
def verify(u,p,c,status=200,**extra):return call('POST','/ehr/miniapp/binding/verify',dict(name=p['name'],employeeNo=p['code'],verificationCode=c['verificationCode'],**extra),auth(u),status)
def confirm(u,t,status=200,yes=True):return call('POST','/ehr/miniapp/binding/confirm',{'confirmationToken':t,'confirmed':yes},auth(u),status)
try:
 call('GET','/ehr/miniapp/me',status=401);check('anonymous_me_rejected')
 call('POST','/ehr/miniapp/wechat/login',{'code':'unused','privacyAccepted':False,'consentVersion':config['consentVersion']},status=422);check('privacy_consent_required')
 call('POST','/ehr/miniapp/wechat/login',{'openid':'forged','privacyAccepted':True,'consentVersion':config['consentVersion']},status=422);check('client_openid_is_not_a_login_credential')
 call('POST','/ehr/miniapp/wechat/login',{'code':'invalid','privacyAccepted':True,'consentVersion':config['consentVersion']},status=401);check('invalid_wechat_code_rejected')
 once=run+'.A.'+uuid.uuid4().hex;a=login('A',once);check('new_wechat_user_is_unbound',a['bindingState']=='UNBOUND' and a['nextAction']=='VERIFY_EMPLOYEE')
 check('wechat_secrets_not_exposed',all(k not in json.dumps(a) for k in ['openid','session_key','appSecret']))
 call('POST','/ehr/miniapp/wechat/login',{'code':once,'privacyAccepted':True,'consentVersion':config['consentVersion']},status=401);check('one_time_wechat_code_not_reused')
 a2=login('A');check('repeat_wechat_login_reuses_user',a2['miniUserId']==a['miniUserId'])
 call('GET','/ehr/miniapp/health-record',headers=auth(a),status=403);check('unbound_health_record_forbidden')
 x=http.post(B+'/ehr/miniapp/reports',headers=dict(auth(a),**{'Idempotency-Key':str(uuid.uuid4())}),files={'file':('x.png',b'no-data','image/png')},timeout=10);check('unbound_upload_forbidden',x.status_code==403)
 before=admin('GET','/statistics')['total'];staff=[]
 for i in range(4):
  p=admin('POST','/records',{'name':'微信绑定联调'+str(i),'code':run+'-'+str(i),'sex':'未填写','org':'隔离接口测试单位','team':'测试班组','job':'测试岗位','birth':'1988-01-01','phone':'13800000000'});staff.append(p);created.append(p['id'])
 p,q,cstaff,dstaff=staff;ca,cq=issue(p),issue(q);check('per_employee_codes_unique',ca['verificationCode']!=cq['verificationCode'] and ca['displayOnce'])
 admin('POST','/records/'+p['id']+'/binding-code',{'requestKey':''},422)
 meta=admin('GET','/records/'+p['id']+'/binding');check('plaintext_code_not_retrievable','verificationCode' not in json.dumps(meta) and not meta['bound'])
 bad=call('POST','/ehr/miniapp/binding/verify',{'name':'wrong name','employeeNo':p['code'],'verificationCode':ca['verificationCode']},auth(a),422);check('wrong_name_reveals_no_profile','employee' not in bad)
 call('POST','/ehr/miniapp/binding/verify',{'name':p['name'],'employeeNo':'wrong number','verificationCode':ca['verificationCode']},auth(a),422);check('wrong_employee_number_rejected')
 call('POST','/ehr/miniapp/binding/verify',{'name':p['name'],'employeeNo':p['code'],'verificationCode':cq['verificationCode']},auth(a),422);check('another_employees_code_rejected')
 preview=verify(a,p,ca);check('verified_preview_matches_backend',preview['employee']['recordId']==p['id'] and preview['employee']['phoneMasked']=='138****0000')
 check('preview_does_not_bind',call('GET','/ehr/miniapp/me',headers=auth(a))['bindingState']=='PENDING_CONFIRMATION' and not admin('GET','/records/'+p['id']+'/binding')['bound'])
 confirm(a,preview['confirmationToken'],422,False);check('explicit_confirmation_required')
 b=login('B');confirm(b,preview['confirmationToken'],422);check('confirmation_token_not_transferable')
 body={k:p.get(k) or '' for k in ['name','code','sex','birth','phone','org','team','job','exposure','legacyUserCode']};body['team']='后台更新班组';body['rowVersion']=p['rowVersion'];p=admin('PUT','/records/'+p['id'],body)
 confirm(a,preview['confirmationToken'],409);check('stale_profile_cannot_be_confirmed')
 preview=verify(a,p,ca);competing=verify(b,p,ca);confirmed=confirm(a,preview['confirmationToken']);check('confirmation_binds_existing_record',confirmed['bindingState']=='BOUND' and confirmed['employee']['recordId']==p['id'])
 confirm(a,preview['confirmationToken']);check('same_confirmation_is_idempotent')
 confirm(b,competing['confirmationToken'],409);check('one_employee_cannot_bind_two_users')
 verify(a,q,cq,409);check('bound_user_cannot_switch_identity')
 record=call('GET','/ehr/miniapp/health-record',headers=auth(a));check('bound_user_sees_own_archive',record['id']==p['id'] and record['reports']==[])
 check('binding_does_not_duplicate_staff',admin('GET','/statistics')['total']==before+4)
 png=io.BytesIO();meta=PngInfo();meta.add_text('testRun',run);Image.new('RGB',(19,19),(40,180,160)).save(png,format='PNG',pnginfo=meta);raw=png.getvalue();upload_headers=dict(auth(a),**{'Idempotency-Key':str(uuid.uuid4())})
 r=http.post(B+'/ehr/miniapp/reports',headers=upload_headers,files={'file':('wx-binding-fixture.png',raw,'image/png')},timeout=30);assert r.status_code==200,r.text;report=r.json()['data'];check('wechat_upload_bound_to_confirmed_employee',report['recordId']==p['id'])
 r=http.post(B+'/ehr/miniapp/reports',headers=upload_headers,files={'file':('wx-binding-fixture.png',raw,'image/png')},timeout=20);check('wechat_upload_idempotency',r.status_code==200 and r.json()['data']['id']==report['id'])
 got=call('GET','/ehr/miniapp/reports/'+report['id']+'/1',headers=auth(a));check('upload_progress_is_candidate',got['stage']=='uploaded' and got['candidate'])
 r=http.get(B+'/ehr/miniapp/files/'+report['files'][0]['id'],headers=auth(a),timeout=20);check('private_file_download_preserves_bytes',r.content==raw)
 call('GET','/ehr/miniapp/files/'+report['files'][0]['id'],headers=auth(b),status=403);check('unbound_user_cannot_download_file')
 cc=issue(cstaff);userc=login('C');oldpreview=verify(userc,cstaff,cc);cc2=issue(cstaff);confirm(userc,oldpreview['confirmationToken'],409);verify(userc,cstaff,cc,422);check('reissue_invalidates_old_code_and_challenge')
 admin('DELETE','/records/'+cstaff['id']+'/binding-code');verify(userc,cstaff,cc2,422);check('revoked_code_not_usable')
 cd=issue(dstaff);userd=login('D');db=connect()
 with db.cursor() as cur:cur.execute("UPDATE a_employee_binding_codes SET expires_at=UTC_TIMESTAMP(6)-INTERVAL 1 SECOND WHERE binding_code_id=%s AND data_mode='synthetic'",(cd['codeId'],))
 db.commit();db.close();verify(userd,dstaff,cd,422);check('expired_code_not_usable')
 e=login('E')
 for _ in range(8):call('POST','/ehr/miniapp/binding/verify',{'name':'wrong','employeeNo':'wrong','verificationCode':'2222-2222-2222-2222'},auth(e),422)
 call('POST','/ehr/miniapp/binding/verify',{'name':'wrong','employeeNo':'wrong','verificationCode':'2222-2222-2222-2222'},auth(e),429);check('failed_attempt_limits_survive_transaction_rollback')
 batch=admin('POST','/binding-codes/batch',[{'recordId':cstaff['id'],'requestKey':str(uuid.uuid4())},{'recordId':dstaff['id'],'requestKey':str(uuid.uuid4())}]);check('batch_generation_produces_distinct_personal_codes',len(batch)==2 and batch[0]['verificationCode']!=batch[1]['verificationCode'])
 target=batch[0]['recordId'];prior=admin('GET','/records/'+target+'/binding')['lastCode']['codeId'];admin('POST','/binding-codes/batch',[{'recordId':target,'requestKey':str(uuid.uuid4())},{'recordId':str(uuid.uuid4()),'requestKey':str(uuid.uuid4())}],404);check('failed_batch_is_atomic',admin('GET','/records/'+target+'/binding')['lastCode']['codeId']==prior)
 admin('POST','/records/'+p['id']+'/binding/revoke',{'reason':'隔离联调：人工核对后解除测试绑定'});call('GET','/ehr/miniapp/me',headers=auth(a),status=401);call('GET','/ehr/miniapp/me',headers=auth(a2),status=401);check('admin_unbind_revokes_all_user_sessions')
 anew=login('A');check('relogin_after_unbind_requires_identity_verification',anew['bindingState']=='UNBOUND')
 call('DELETE','/ehr/miniapp/session',headers=auth(anew));call('GET','/ehr/miniapp/me',headers=auth(anew),status=401);check('logout_revokes_token')
finally:
 for rid in created:
  p=admin('GET','/records/'+rid);admin('DELETE','/records/'+rid,{'reason':'synthetic微信绑定测试完成，保留原件及绑定审计','rowVersion':p['rowVersion']})
out={'mode':'synthetic','checks':checks,'count':len(checks),'provider':'loopback contract stub; not real WeChat','realWeChatConfigured':False,'testRun':run};(ROOT/'database/miniapp-binding-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS WeChat binding:',len(checks),'checks')
