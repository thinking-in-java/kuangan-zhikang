"""Real deployment read-only checks; never claims or writes a real OCR task."""
from pathlib import Path
import json,os,requests
ROOT=Path(__file__).resolve().parents[2];base=os.getenv('EHR_REAL_URL','http://localhost:18080');s=requests.Session();s.trust_env=False;checks=[]
def check(n,v):assert v,n;checks.append(n)
keyfile=Path(os.getenv('EHR_OCR_WORKER_TOKEN_FILE','F:/upload/.config/ocr-worker.token'));secret=keyfile.read_text(encoding='ascii').strip()
r=s.get(base+'/ehr/worker/dictionary',headers={'Authorization':'Bearer '+secret},timeout=20);check('real_worker_credentials_ready',r.status_code==200)
d=r.json()['data'];check('real_dictionary_67_fields_655_items',len(d['standardFields'])==67 and len(d['medicalItems'])==655)
check('anonymous_worker_rejected',s.get(base+'/ehr/worker/dictionary',timeout=10).status_code==401)
s.get(base+'/login',timeout=10);r=s.post(base+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20);check('doctor_login',r.json()['code']==200)
ctx=s.get(base+'/ehr/api/context',timeout=15).json()['data'];check('real_mode',ctx['dataMode']=='real')
r=s.get(base+'/ehr/api/ocr/tasks?state=all',timeout=15);check('real_task_queue_accessible',r.status_code==200 and r.json()['code']==0)
check('task_queue_does_not_leak_secret',secret not in r.text and 'lease_token_hash' not in r.text)
for url in ['/assets/health-archive/management.html','/assets/health-archive/live.js','/assets/health-archive/live.css']:
 r=s.get(base+url,timeout=15);check('live_asset_'+url.rsplit('/',1)[-1],r.status_code==200 and r.content==(ROOT/'yihe-admin/src/main/resources/static'/url.removeprefix('/')).read_bytes())
check('private_storage_not_static',s.get(base+'/profile/.config/ocr-worker.token',timeout=10).status_code!=200)
out={'mode':'real','base':base,'checks':checks,'count':len(checks),'realTaskClaimed':False,'realBusinessDataModified':False,'storageRoot':'F:/upload','workerTokenValuePrinted':False}
(ROOT/'database/ocr-worker-readiness-verification.json').write_text(json.dumps(out,indent=2),encoding='utf-8');print(json.dumps(out))
