"""Synthetic-only import/export, query and request-boundary verification."""
import os,json,uuid,csv,io
from pathlib import Path
import requests
base=os.getenv('EHR_TEST_URL','http://localhost:18081');s=requests.Session();s.trust_env=False;checks=[];created=[]
def check(k,v=True):assert v,k;checks.append(k);print('PASS',k,flush=True)
s.get(base+'/login',timeout=10);r=s.post(base+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=20);assert r.json()['code']==200
context=s.get(base+'/ehr/api/context',timeout=10).json()['data'];assert context['dataMode']=='synthetic';h={'X-CSRF-Token':context['csrfToken']};prefix='OPS_'+uuid.uuid4().hex[:10]
def call(method,path,body=None,status=200):
 r=s.request(method,base+'/ehr/api'+path,headers=h,json=body,timeout=30);assert r.status_code==status,(path,r.status_code,r.text[:300]);return r.json().get('data')
def profile(code,name):return dict(code=code,name=name,sex='未填写',org='Synthetic import test',team='测试队',job='测试岗位')
try:
 p=profile(prefix+'_rollback','需整批回滚');call('POST','/records/import',[p,p],409)
 check('import_conflict_rolls_back_entire_batch',call('GET','/records?search='+prefix)['total']==0)
 call('POST','/records/import',[profile(prefix+'_1','=SYNTHETIC'),profile(prefix+'_2','导入联调职工')])
 rows=call('GET','/records?code='+prefix)['items'];created=[p['id'] for p in rows];check('import_success_two_records',len(rows)==2)
 r=s.get(base+'/ehr/api/records/export',params={'code':prefix},timeout=20);check('filtered_csv_export',r.status_code==200 and len(list(csv.reader(io.StringIO(r.content.decode('utf-8-sig')))))==3)
 check('csv_formula_prefix_escaped',"'=SYNTHETIC" in r.content.decode('utf-8-sig'))
 check('combined_team_query',call('GET','/records?code='+prefix+'&team='+requests.utils.quote('测试队'))['total']==2)
 r=s.get(base+'/ehr/api/records',headers={'Origin':'https://untrusted.invalid'},timeout=10);check('cross_origin_request_rejected',r.status_code==403)
 r=requests.get(base+'/ehr/api/context',timeout=10);check('anonymous_api_401',r.status_code==401)
finally:
 for rid in created:
  p=call('GET','/records/'+rid);call('DELETE','/records/'+rid,{'rowVersion':p['rowVersion'],'reason':'Synthetic operations verification cleanup'})
Path(__file__).resolve().parents[2].joinpath('database/ehr-operations-verification.json').write_text(json.dumps({'mode':'synthetic','checks':checks,'count':len(checks)},indent=2),encoding='utf-8');print('PASS operations:',len(checks),'checks')
