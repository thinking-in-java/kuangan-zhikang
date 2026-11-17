"""Real service negative checks after configuring WeChat. No valid user code used."""
from pathlib import Path
import sys,requests,json,os
from datetime import datetime,timezone
ROOT=Path(__file__).resolve().parents[2];sys.path.insert(0,str(ROOT/'database'))
from health_archive_db import connect
base='http://localhost:18080';s=requests.Session();s.trust_env=False;checks=[]
def check(n,condition):
 assert condition,n
 checks.append(n);print('PASS',n,flush=True)
def users():
 c=connect()
 with c.cursor() as q:q.execute("SELECT COUNT(*) n FROM a_miniapp_users WHERE data_mode='real'");n=q.fetchone()['n']
 c.close();return n
before=users()
r=s.get(base+'/ehr/miniapp/config',timeout=15);check('config_http200_code0',r.status_code==200 and r.json()['code']==0)
config=r.json()['data'];check('wechat_configuration_enabled',config['wechatConfigured'] is True)
check('binding_required_and_token_expiry',config['bindingRequired'] is True and config['loginTokenExpiresIn']==7200)
check('public_config_does_not_return_secret',all(k not in config for k in ['appSecret','secret','session_key']))
request={'code':'configuration-negative-probe-not-a-user-code','privacyAccepted':False,'consentVersion':config['consentVersion']}
r=s.post(base+'/ehr/miniapp/wechat/login',json=request,timeout=20);check('consent_required_before_wechat_exchange',r.status_code==422 and r.json()['code']==422)
request['privacyAccepted']=True;request.pop('code');request['openid']='client-input-not-trusted'
r=s.post(base+'/ehr/miniapp/wechat/login',json=request,timeout=20);check('client_openid_cannot_register',r.status_code==422)
request.pop('openid');request['code']='configuration-negative-probe-not-a-user-code'
r=s.post(base+'/ehr/miniapp/wechat/login',json=request,timeout=25);check('invalid_code_rejected_after_real_wechat_exchange',r.status_code==401 and r.json()['code']==401 and 'token' not in r.json())
r=s.get(base+'/ehr/miniapp/me',timeout=15);check('anonymous_state_is401',r.status_code==401)
r=s.get(base+'/ehr/miniapp/health-record',timeout=15);check('anonymous_health_data_is401',r.status_code==401)
r=s.get(base+'/profile/.config/wechat-miniapp.json',timeout=15,allow_redirects=False);check('anonymous_cannot_read_credentials_through_profile',r.status_code!=200)
doctor=requests.Session();doctor.trust_env=False;doctor.get(base+'/login',timeout=10)
r=doctor.post(base+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20);check('doctor_login_for_private_path_check',r.json()['code']==200)
r=doctor.get(base+'/profile/.config/wechat-miniapp.json',timeout=15,allow_redirects=False);check('credentials_not_served_even_to_authenticated_doctor',r.status_code==404)
check('invalid_requests_create_no_real_users',before==users())
out={'checkedAt':datetime.now(timezone.utc).isoformat(),'base':base,'checks':checks,'count':len(checks),'wechatConfigured':True,'validUserCodeUsed':False,'realUserRegistered':False,'realUserRowsChanged':False,'actualBoundUserRedirectTested':False}
(ROOT/'database/miniapp-configured-verification.json').write_text(json.dumps(out,indent=2),encoding='utf-8')
print('PASS configured miniapp negative checks:',len(checks),flush=True)
