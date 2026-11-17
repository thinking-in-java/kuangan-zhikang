"""One synthetic image -> actual model API -> A candidate receipt. Never publishes."""
import io,json,os,time,uuid
from pathlib import Path
import requests
from PIL import Image,ImageDraw,ImageFont
from PIL.PngImagePlugin import PngInfo

ROOT=Path(__file__).resolve().parents[2];BASE='http://localhost:18082'
s=requests.Session();s.trust_env=False
for _ in range(45):
    try:
        if s.get(BASE+'/login',timeout=2).status_code==200:break
    except requests.RequestException:pass
    time.sleep(1)
else:raise AssertionError('Synthetic service not ready')
assert s.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=30).json()['code']==200
ctx=s.get(BASE+'/ehr/api/context',timeout=30).json()['data'];assert ctx['dataMode']=='synthetic'
headers={'X-CSRF-Token':ctx['csrfToken']};created=None;checks=[]
def check(name,value):
    assert value,name;checks.append(name);print('PASS',name,flush=True)
def api(method,path,body=None):
    r=s.request(method,BASE+'/ehr/api'+path,json=body,headers=headers,timeout=45)
    assert r.status_code==200,(path,r.status_code,r.text[:300])
    return r.json()['data']
try:
    status=api('GET','/recognition');check('model_provider_enabled',status['provider']=='model' and status['enabled'])
    created=api('POST','/records',{'name':'模型联调职工','code':'MODEL_'+uuid.uuid4().hex[:14],'sex':'男','org':'隔离测试','team':'测试班组','job':'测试岗位'})
    image=Image.new('RGB',(1600,1400),'white');draw=ImageDraw.Draw(image);font=ImageFont.truetype('C:/Windows/Fonts/msyh.ttc',42)
    lines=['合成测试报告（不用于诊疗）','姓名：模型联调职工','性别：男    年龄：40岁','体检机构：联调测试医院','体检日期：2026-11-16',
           '检查项目        结果        单位','身高              170          cm','体重              70            kg','血压              120/80      mmHg',
           '完整体检结论：本报告仅用于接口联调测试。','医师建议：请核对测试数据，不用于诊疗。']
    for i,line in enumerate(lines):draw.text((60,50+i*105),line,font=font,fill='black')
    buf=io.BytesIO();meta=PngInfo();meta.add_text('syntheticRun',str(uuid.uuid4()));image.save(buf,format='PNG',pnginfo=meta)
    pdf_test=os.environ.get('EHR_TEST_PDF')=='1'
    if pdf_test:
        # Ephemeral, clearly synthetic test fixture; no real report is sent.
        import pymupdf
        doc=pymupdf.open()
        for _ in range(2):
            p=doc.new_page(width=800,height=700);p.insert_image(p.rect,stream=buf.getvalue())
        raw=doc.tobytes();doc.close();file=('synthetic-model-integration.pdf',raw,'application/pdf')
    else:file=('synthetic-model-integration.png',buf.getvalue(),'image/png')
    r=s.post(BASE+'/ehr/api/records/'+created['id']+'/reports',files={'file':file},headers={**headers,'Idempotency-Key':str(uuid.uuid4())},timeout=45)
    assert r.status_code==200,r.text[:300]
    report=r.json()['data'];jid=report['jobId'];check('upload_creates_durable_task',bool(jid))
    end=time.monotonic()+330
    while time.monotonic()<end:
        task=api('GET','/ocr/tasks/'+jid)
        print('TASK',task['state'],task['percent'],flush=True)
        if task['state'] in ('succeeded','failed','cancelled'):break
        time.sleep(5)
    check('model_result_accepted',task['state']=='succeeded')
    report=api('GET','/reports/'+report['id']+'/'+str(report['revision']))
    timing_path=Path('F:/upload/.config/model-results')/(jid+'.timing.json')
    timing=json.loads(timing_path.read_text(encoding='utf8')) if timing_path.exists() else {}
    candidate_path=timing_path.with_name(jid+'.candidate.json')
    candidate=json.loads(candidate_path.read_text(encoding='utf8')) if candidate_path.exists() else {}
    print('CANDIDATE',json.dumps({'institution':candidate.get('institution_raw'),'institutionMatchesDatabase':candidate.get('institution_raw')==report['institution']},ensure_ascii=True),flush=True)
    print('EXTRACTED',json.dumps({'institution':report['institution'],'examDateRaw':report['examDateRaw'],'items':len(report['items']),'conclusions':len(report['conclusions']),'advice':len(report['advice']),'elapsedSeconds':timing.get('elapsedSeconds')},ensure_ascii=True),flush=True)
    check('metadata_returned',report['institution']=='联调测试医院' and report['examDateRaw']=='2026-11-16')
    check('date_not_automatically_verified',report['examDate'] is None)
    check('medical_results_present',len(report['items'])>=3)
    if pdf_test:check('both_pdf_pages_preserved',{i['page'] for i in report['items']}=={1,2})
    check('conclusion_and_advice_present',bool(report['conclusions']) and bool(report['advice']))
    check('not_approved_or_published',report['reviewStatus']=='needs_review' and not report['published'] and not report['reviews'])
    key=Path('F:/upload/.config/model-worker.token').read_text().strip()
    # The B credential can still query status, but its claim endpoint is paused.
    bkey=Path('F:/upload/.config/ocr-worker.synthetic.token').read_text().strip()
    r=s.post(BASE+'/ehr/worker/tasks/claim',headers={'Authorization':'Bearer '+bkey},json={'workerId':'legacy-pause-check','requestKey':str(uuid.uuid4())},timeout=15)
    check('legacy_b_cannot_claim',r.status_code==200 and r.json()['data']['task'] is None and r.json()['data']['paused'])
    r=s.post(BASE+'/ehr/model-worker/tasks/claim',headers={'Authorization':'Bearer '+bkey},json={'workerId':'legacy-pause-check','requestKey':str(uuid.uuid4())},timeout=15)
    check('legacy_key_cannot_claim_model_tasks',r.status_code==401)
    data={'checks':checks,'count':len(checks),'mode':'synthetic','jobId':jid,'reportId':report['id'],'revision':report['revision'],
          'model':status['model'],'items':len(report['items']),'conclusions':len(report['conclusions']),'advice':len(report['advice']),
          'realEmployeeModified':False,'automaticPublication':False,'realModelApiCalled':True,'sourceType':'pdf' if pdf_test else 'png','timing':timing}
    (ROOT/('database/model-recognition-pdf-verification.json' if pdf_test else 'database/model-recognition-verification.json')).write_text(json.dumps(data,ensure_ascii=False,indent=2),encoding='utf-8')
    print(json.dumps(data,ensure_ascii=False),flush=True)
finally:
    if created:
        current=api('GET','/records/'+created['id'])
        api('DELETE','/records/'+created['id'],{'rowVersion':current['rowVersion'],'reason':'模型识别隔离联调完成，联动清理测试职工及其报告和原件'})
