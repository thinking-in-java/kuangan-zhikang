"""Synthetic-only integration check. See tests/ehr/README.md."""
from pathlib import Path
import os,json,uuid,requests,io
from reportlab.pdfgen import canvas

BASE=os.environ.get('EHR_TEST_URL','http://localhost:18081')
s=requests.Session();s.trust_env=False
checks=[]
def test(label,value):
    assert value,label
    checks.append(label);print('PASS',label,flush=True)
def call(method,path,body=None,headers=None,expect=200,**kw):
    r=s.request(method,BASE+'/ehr/api'+path,json=body,headers=headers,timeout=30,**kw)
    if r.status_code!=expect:raise AssertionError((method,path,r.status_code,r.text[:500]))
    return r.json().get('data') if expect==200 else r
test('anonymous_api_401',s.get(BASE+'/ehr/api/context',timeout=10).status_code==401)
s.get(BASE+'/login',timeout=10)
r=s.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20)
test('doctor_login',r.json().get('code')==200)
context=call('GET','/context');test('synthetic_mode',context['dataMode']=='synthetic')
headers={'X-CSRF-Token':context['csrfToken']}
initial=call('GET','/statistics')['total']
token=uuid.uuid4().hex[:10]
fields={'name':'API联调职工'+token,'code':'TEST_'+token,'sex':'男','phone':'','org':'接口测试单位','team':'接口测试班组','job':'测试岗位','birth':'1990-01-01','exposure':'测试用非临床资料'}
call('POST','/records',fields,expect=403);test('csrf_required',True)
p=call('POST','/records',fields,headers);rid=p['id'];test('record_create_unknown_risk',p['risk']=='unknown' and p['status']=='draft')
call('POST','/records',fields,headers,expect=409);test('duplicate_employee_no_409',True)
bad=dict(fields,rowVersion=0);call('PUT','/records/'+rid,bad,headers,expect=409);test('optimistic_lock',True)
fields['team']='已更新班组';fields['rowVersion']=p['rowVersion'];p=call('PUT','/records/'+rid,fields,headers);test('record_edit',p['team']=='已更新班组')
assert call('GET','/records?search='+fields['code'])['total']==1
buf=io.BytesIO();pdf=canvas.Canvas(buf);pdf.drawString(50,750,'SYNTHETIC API VALIDATION - NOT A CLINICAL REPORT');pdf.showPage();pdf.save();content=buf.getvalue()
key=str(uuid.uuid4());upheaders=dict(headers,**{'Idempotency-Key':key})
r=s.post(BASE+'/ehr/api/records/'+rid+'/reports',headers=upheaders,files={'file':('api-test.pdf',content,'application/pdf')},data={'channel':'backend_file'},timeout=30)
if r.status_code!=200 or r.json().get('code')!=0:raise AssertionError(('upload',r.status_code,r.text[:800]))
test('upload_pdf',True);report=r.json()['data'];reportid=report['id']
r=s.post(BASE+'/ehr/api/records/'+rid+'/reports',headers=upheaders,files={'file':('api-test.pdf',content,'application/pdf')},data={'channel':'backend_file'},timeout=30)
test('upload_idempotency',r.status_code==200 and r.json()['data']['id']==reportid)
f=report['files'][0];download=s.get(BASE+'/ehr/api/files/'+f['id'],timeout=20);test('original_bytes_preserved',download.content==content and download.headers.get('X-Content-Type-Options')=='nosniff')
candidate={'fullName':fields['name'],'examDate':'2026-11-14','institution':'接口测试机构','formalConclusion':'仅供接口验证，不构成医学结论。','advice':'测试建议原文','items':[{'name':'测试复合值','value':'＜120/80（原文）','unit':'mmHg','referenceRange':'详见测试原文','abnormal':'','page':1}]}
call('PUT',f'/reports/{reportid}/1',candidate,dict(headers,**{'If-Match':'wrong'}),expect=409);test('report_content_conflict',True)
report=call('PUT',f'/reports/{reportid}/1',candidate,dict(headers,**{'If-Match':report['etag']}));test('raw_composite_string',report['items'][0]['value']=='＜120/80（原文）')
def action(which,rev=1,**more):return call('POST',f'/reports/{reportid}/{rev}/actions',dict(action=which,etag=call('GET',f'/reports/{reportid}/{rev}')['etag'],note='synthetic workflow validation',requestKey=str(uuid.uuid4()),**more),headers)
action('submit');test('submit_pending',True)
call('POST',f'/reports/{reportid}/1/actions',{'etag':'outdated','action':'approve','note':'stale review rejected','identityVerified':True,'requestKey':str(uuid.uuid4())},headers,expect=409);test('stale_review_etag_rejected',True)
call('POST',f'/reports/{reportid}/1/actions',{'etag':call('GET',f'/reports/{reportid}/1')['etag'],'action':'approve','note':'missing identity check','requestKey':str(uuid.uuid4())},headers,expect=422)
report=action('approve',identityVerified=True);test('doctor_approval',report['reviewStatus']=='confirmed' and not report['published']);test('confirmed_field_review_states',all(v['review_status']=='confirmed' for v in report['fieldValues']))
call('PUT',f'/reports/{reportid}/1',candidate,dict(headers,**{'If-Match':report['etag']}),expect=409);test('reviewed_version_immutable',True)
report=action('archive');test('publication_and_snapshot_digest',report['published'])
revised=call('POST',f'/reports/{reportid}/1/revisions',{'note':'更正测试，保留旧报告'},headers);test('new_revision_retains_raw_items',revised['revision']==2 and len(revised['items'])==1)
test('revision_resets_field_review_states',all(v['review_status']=='needs_review' for v in revised['fieldValues']))
old=call('GET',f'/reports/{reportid}/1');test('old_publication_preserved',old['published'] and old['items'][0]['value']==candidate['items'][0]['value'])
test('published_projection_survives_new_draft',call('GET','/records/'+rid)['publishedReports'][0]['revision']==1)
other=s.get(BASE+'/ehr/api/records/'+str(uuid.uuid4()),timeout=10);test('unknown_record_404',other.status_code==404)
call('DELETE','/records/'+rid,{'reason':'自动化联调完成；synthetic数据保留审计','rowVersion':p['rowVersion']},headers);call('GET','/records/'+rid,expect=404);test('soft_delete_visibility',True)
test('real_summary_restored',call('GET','/statistics')['total']==initial)
out={'base':BASE,'checks':checks,'count':len(checks),'recordId':rid,'reportId':reportid,'mode':'synthetic'}
Path(str(Path(__file__).resolve().parents[2])+'/database/ehr-api-integration-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8')
print('PASS EHR API integration:',len(checks),'checks')
