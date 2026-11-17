"""Synthetic WeChat correction workflow, authorization, concurrency and browser review."""
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor
import os,json,uuid,time,subprocess,requests,websocket,sys,hashlib
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
B=os.getenv('EHR_TEST_URL','http://localhost:18081');run='wxitest-'+uuid.uuid4().hex[:14];checks=[];created=[];ws=None;chrome=None;sequence=0;errors=[]
web=requests.Session();web.trust_env=False;http=requests.Session();http.trust_env=False
web.get(B+'/login',timeout=10);assert web.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20).json()['code']==200
ctx=web.get(B+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic';csrf={'X-CSRF-Token':ctx['csrfToken']}
def check(n,v=True):assert v,n;checks.append(n);print('PASS',n,flush=True)
def admin(method,path,body=None,status=200):
 r=web.request(method,B+'/ehr/api'+path,json=body,headers=csrf,timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:300]);return r.json().get('data') if status==200 else r.json()
def call(method,path,u=None,body=None,status=200):
 r=http.request(method,B+'/ehr/miniapp'+path,json=body,headers={'Authorization':'Bearer '+u['token']} if u else {},timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:400]);return r.json().get('data') if status==200 else r.json()
config=call('GET','/config')
def login(who):return call('POST','/wechat/login',body={'code':run+'.'+who+'.'+uuid.uuid4().hex,'privacyAccepted':True,'consentVersion':config['consentVersion']})
def staff(i):
 p=admin('POST','/records',{'name':'更正接口测试'+str(i),'code':run+'-'+str(i),'sex':'女','org':'隔离更正测试单位','team':'原班组','job':'原岗位','birth':'1990-01-01','phone':'13800000000'});created.append(p['id']);return p
def issue(p):return admin('POST','/records/'+p['id']+'/binding-code',{'requestKey':str(uuid.uuid4()),'expiresIn':3600})
def verify(u,p,c,status=200):return call('POST','/binding/verify',u,{'name':p['name'],'employeeNo':p['code'],'verificationCode':c['verificationCode']},status)
def submit(u,preview,changes=None,**extra):return dict(confirmationToken=preview['confirmationToken'],changes=changes or {'team':'修正后班组','position':'修正后岗位','phone':'13900001111'},reason='本人核对后发现班组与岗位资料已过期',requestKey=str(uuid.uuid4()),**extra)
def edit(p,**extra):
 b={k:p.get(k) or '' for k in ['name','code','org','team','job','sex','phone','birth','legacyUserCode','exposure']};b.update(extra,rowVersion=p['rowVersion']);return admin('PUT','/records/'+p['id'],b)
def review(r,decision='approve',status=200):return admin('POST','/correction-requests/'+r['id']+'/review',{'decision':decision,'note':'人工核对资料依据：隔离验证','requestKey':str(uuid.uuid4()),'rowVersion':r['rowVersion']},status)
def rpc(method,params=None):
 global sequence
 sequence+=1;k=sequence;ws.send(json.dumps({'id':k,'method':method,'params':params or {}}))
 while True:
  m=json.loads(ws.recv())
  if m.get('method')=='Runtime.exceptionThrown':errors.append(m['params']['exceptionDetails'].get('text'))
  if m.get('id')==k:assert 'error' not in m,m;return m.get('result',{})
def js(e):
 r=rpc('Runtime.evaluate',{'expression':e,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r;return r.get('result',{}).get('value')
def wait(e):
 end=time.monotonic()+30
 while time.monotonic()<end:
  if js('Boolean('+e+')'):return
  time.sleep(.2)
 raise AssertionError('UI timeout '+e+' '+str(js('document.body.innerText'))[-1200:])
def click(sel):js('document.querySelector('+json.dumps(sel)+').click()')
def browser_review(r,decision):
 global chrome,ws
 if not chrome:
  chrome=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--no-default-browser-check','--remote-debugging-port=9225','--user-data-dir=F:/workstation/.tools/runtime/correction-ui-chrome','about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
  for _ in range(100):
   try:tabs=http.get('http://localhost:9225/json',timeout=1).json();break
   except requests.RequestException:time.sleep(.1)
  ws=websocket.create_connection(next(t['webSocketDebuggerUrl'] for t in tabs if t['type']=='page'),timeout=35,suppress_origin=True)
  rpc('Page.enable');rpc('Runtime.enable');rpc('Network.enable');rpc('Network.setCacheDisabled',{'cacheDisabled':True});rpc('Network.setCookies',{'cookies':[{'name':c.name,'value':c.value,'domain':'localhost','path':'/'} for c in web.cookies]})
  rpc('Page.navigate',{'url':B+'/assets/health-archive/management.html'});wait('document.querySelector("[data-do=corrections]")')
 click('[data-do="corrections"]');selector='form[data-correction-form="'+r['id']+'"]';wait('document.querySelector('+json.dumps(selector)+')')
 js('(()=>{let original=window.fetch;window.fetch=(url,init)=>{if(String(url).endsWith("/review"))window.reviewFixturePayload=JSON.parse(init.body);return original(url,init)}})()')
 js('document.querySelector('+json.dumps(selector+' textarea')+').value="已向企业核对资料，隔离浏览器验证"')
 click(selector+' button[value="'+decision+'"]');wait('!document.querySelector('+json.dumps(selector)+')')
 return js('window.reviewFixturePayload')
try:
 call('POST','/binding/correction-requests',body={},status=401);check('anonymous_correction_401')
 p,q,t=staff(1),staff(2),staff(3);a,b,c,d=login('A'),login('B'),login('C'),login('D');baduser=login('invalid');cp,cq,ct=issue(p),issue(q),issue(t);va=verify(a,p,cp);vb=verify(b,p,cp)
 for changes in [{'name':'冒用身份'},{'employeeNo':'other'},{'scope_code':'org:other'},{'phone':{'x':1}},{'birthDate':'2039-12-31'}]:
  body=submit(baduser,va,changes);call('POST','/binding/correction-requests',baduser,body,422)
 check('forbidden_identity_fields_and_invalid_types_rejected')
 call('POST','/binding/correction-requests',a,submit(a,va,{'team':'原班组'}),422);check('no_op_changes_rejected')
 call('POST','/binding/correction-requests',b,submit(b,va),422);check('another_users_confirmation_cannot_submit')
 body=submit(a,va,recordId=t['id'])
 db=connect()
 with db.cursor() as cur:
  cur.execute("SELECT COUNT(*) n FROM a_miniapp_binding_challenges WHERE mini_user_id=%s AND data_mode='synthetic' AND challenge_hash=%s AND used_at IS NULL AND expires_at>UTC_TIMESTAMP(6)",(a['miniUserId'],hashlib.sha256(va['confirmationToken'].encode()).digest()));assert cur.fetchone()['n']==1,'test confirmation fixture must be valid'
 db.close()
 def concurrent_submit(_):
  client=requests.Session();client.trust_env=False;r=client.post(B+'/ehr/miniapp/binding/correction-requests',json=body,headers={'Authorization':'Bearer '+a['token']},timeout=35);assert r.status_code==200,r.text;return r.json()['data']
 with ThreadPoolExecutor(max_workers=2) as pool:rr=list(pool.map(concurrent_submit,range(2)))
 request=rr[0];check('concurrent_identical_submit_is_idempotent',rr[0]['id']==rr[1]['id'])
 check('client_cannot_redirect_target_record',request['recordId']==p['id'])
 check('request_does_not_change_formal_profile',admin('GET','/records/'+p['id'])['team']=='原班组')
 admin('DELETE','/records/'+p['id'],{'rowVersion':p['rowVersion'],'reason':'不能让待审申请成为孤立记录'},409);check('pending_request_prevents_employee_deletion')
 check('request_snapshot_masks_original_phone',request['before']['phoneMasked']=='138****0000' and '13800000000' not in json.dumps(request))
 check('pending_state_returned',call('GET','/me',a)['bindingState']=='CORRECTION_PENDING')
 call('POST','/binding/correction-requests',a,dict(body,reason='另一个原因'),409);check('idempotency_key_cannot_change_payload')
 check('my_requests_scoped_to_current_wechat_user',call('GET','/binding/correction-requests',a)['total']==1 and call('GET','/binding/correction-requests',b)['total']==0)
 call('POST','/binding/confirm',a,{'confirmationToken':va['confirmationToken'],'confirmed':True},409);call('POST','/binding/confirm',b,{'confirmationToken':vb['confirmationToken'],'confirmed':True},409);verify(a,p,cp,409);verify(b,p,cp,409);check('pending_request_blocks_confirmation_for_all_users')
 call('GET','/health-record',a,status=403);check('correction_does_not_grant_health_access')
 no_csrf=web.post(B+'/ehr/api/correction-requests/'+request['id']+'/review',json={'decision':'approve'},timeout=15);check('review_requires_csrf',no_csrf.status_code==403)
 check('admin_queue_filters_record_and_status',admin('GET','/correction-requests?recordId='+p['id']+'&status=pending')['total']==1)
 admin('POST','/correction-requests/'+str(uuid.uuid4())+'/review',{'decision':'approve','note':'核对','requestKey':str(uuid.uuid4()),'rowVersion':1},404);check('unknown_admin_request_404')
 retry_payload=browser_review(request,'approve');check('browser_approval_persists_requested_changes',admin('GET','/records/'+p['id'])['team']=='修正后班组')
 result=call('GET','/binding/correction-requests',a)['items'][0];check('review_result_keeps_before_after',result['status']=='approved' and result['before']['team']=='原班组' and result['after']['team']=='修正后班组')
 check('approval_preserves_identity_and_legacy_profile',admin('GET','/records/'+p['id'])['code']==p['code'])
 admin('POST','/correction-requests/'+request['id']+'/review',retry_payload);check('same_review_is_idempotent')
 review(request,status=409);check('processed_review_cannot_be_overwritten')
 call('POST','/binding/confirm',a,{'confirmationToken':va['confirmationToken'],'confirmed':True},409);check('old_confirmation_invalid_after_approval')
 latest=verify(a,p,cp);check('new_verification_shows_corrected_backend_profile',latest['employee']['team']=='修正后班组' and latest['employee']['phoneMasked']=='139****1111')
 confirmed=call('POST','/binding/confirm',a,{'confirmationToken':latest['confirmationToken'],'confirmed':True});check('corrected_profile_can_be_confirmed_without_duplicate_record',confirmed['bindingState']=='BOUND' and confirmed['employee']['recordId']==p['id'])
 call('POST','/binding/correction-requests',a,dict(body,requestKey=str(uuid.uuid4())),409);check('bound_user_cannot_use_prebinding_correction')
 vc=verify(c,q,cq);r2=call('POST','/binding/correction-requests',c,submit(c,vc,{'team':'请求变更的班组'}));q=edit(q,phone='13700002222');review(r2,status=409);check('concurrent_backend_edit_prevents_approval_overwrite',admin('GET','/records/'+q['id'])['phone']=='13700002222')
 browser_review(r2,'reject');rejected=call('GET','/binding/correction-requests',c)['items'][0];check('browser_rejection_retains_reason_without_update',rejected['status']=='rejected' and rejected['after'] is None and admin('GET','/records/'+q['id'])['team']=='原班组')
 check('resolved_request_requires_reverification',call('GET','/me',c)['bindingState']=='UNBOUND');verify(c,q,cq);check('rejected_user_can_reverify_current_profile')
 vd=verify(d,t,ct);t=edit(t,team='后台新班组');call('POST','/binding/correction-requests',d,submit(d,vd),409);check('stale_preview_cannot_submit_request')
 vd=verify(d,t,ct);admin('DELETE','/records/'+t['id']+'/binding-code');call('POST','/binding/correction-requests',d,submit(d,vd),409);check('revoked_code_cannot_submit_request')
 ct=issue(t);vd=verify(d,t,ct);db=connect()
 with db.cursor() as cur:cur.execute("UPDATE a_miniapp_binding_challenges SET expires_at=UTC_TIMESTAMP(6)-INTERVAL 1 SECOND WHERE mini_user_id=%s AND data_mode='synthetic'",(d['miniUserId'],))
 db.commit();db.close();call('POST','/binding/correction-requests',d,submit(d,vd),422);check('expired_confirmation_cannot_submit_request')
 check('browser_no_javascript_exceptions',not errors)
 out={'mode':'synthetic','checks':checks,'count':len(checks),'browserErrors':errors,'realEmployeeModified':False,'realWeChatTested':False};(ROOT/'database/miniapp-correction-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS corrections:',len(checks),'checks',flush=True)
finally:
 if ws:ws.close()
 if chrome:chrome.terminate()
 for rid in created:
  for pending in admin('GET','/correction-requests?status=pending&recordId='+rid)['items']:review(pending,'reject')
  p=admin('GET','/records/'+rid);admin('DELETE','/records/'+rid,{'reason':'synthetic更正申请测试完成，保留审核记录','rowVersion':p['rowVersion']})
