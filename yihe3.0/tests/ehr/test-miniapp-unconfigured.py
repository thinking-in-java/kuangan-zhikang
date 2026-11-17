"""Read-only business smoke check for a real instance without WeChat credentials."""
from pathlib import Path
import os,json,requests
s=requests.Session();s.trust_env=False
base=os.getenv('EHR_REAL_URL','http://localhost:18080')
r=s.get(base+'/ehr/miniapp/config',timeout=15);assert r.status_code==200
config=r.json()['data'];assert config['wechatConfigured'] is False
checks=['configuration_reports_not_ready']
r=s.post(base+'/ehr/miniapp/wechat/login',json={'code':'unconfigured-smoke-not-a-real-wechat-code','privacyAccepted':True,'consentVersion':config['consentVersion']},timeout=15)
assert r.status_code==503 and r.json()['code']==503
checks.append('login_returns_explicit_503_without_fallback_identity')
for path in ['/me','/health-record']:
 r=s.get(base+'/ehr/miniapp'+path,timeout=10);assert r.status_code==401
 checks.append('anonymous_'+path[1:].replace('-','_')+'_401')
assert 'no-store' in r.headers.get('Cache-Control','')
checks.append('private_responses_not_cached')
out={'base':base,'checks':checks,'count':len(checks),'realWeChatConfigured':False,'fakeIdentityCreated':False}
(Path(__file__).resolve().parents[2]/'database/miniapp-unconfigured-verification.json').write_text(json.dumps(out,indent=2),encoding='utf-8')
print(json.dumps(out))
