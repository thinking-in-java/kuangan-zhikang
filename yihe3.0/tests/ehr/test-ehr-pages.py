"""Synthetic-only integration check. See tests/ehr/README.md."""
from pathlib import Path
import subprocess,os,time,json,requests,websocket,base64,uuid
from PIL import Image
from PIL.PngImagePlugin import PngInfo
ROOT=Path(os.environ.get('EHR_TEST_WORKSPACE','F:/workstation'));BASE=os.getenv('EHR_TEST_URL','http://localhost:18081');checks=[]
s=requests.Session();s.trust_env=False;s.get(BASE+'/login',timeout=10)
r=s.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=20);assert r.json()['code']==200
ctx=s.get(BASE+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic'
chrome=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--no-default-browser-check','--remote-debugging-port=9224','--user-data-dir=F:/workstation/.tools/runtime/ehr-ui-chrome','about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
ws=None;sequence=0;errors=[];out=Path(__file__).resolve().parents[2]/'database/ehr-page-verification.json';code='UI_'+uuid.uuid4().hex[:10];record=None
def rpc(method,params=None):
 global sequence
 sequence+=1;key=sequence;ws.send(json.dumps({'id':key,'method':method,'params':params or {}}))
 while True:
  msg=json.loads(ws.recv())
  if msg.get('method')=='Runtime.exceptionThrown':errors.append(msg['params']['exceptionDetails'].get('text'))
  if msg.get('id')==key:
   assert 'error' not in msg,(method,msg)
   return msg.get('result',{})
def js(expression):
 r=rpc('Runtime.evaluate',{'expression':expression,'returnByValue':True,'awaitPromise':True})
 assert 'exceptionDetails' not in r,r
 return r.get('result',{}).get('value')
def wait(expression,seconds=25):
 end=time.monotonic()+seconds
 while time.monotonic()<end:
  if js('Boolean('+expression+')'):return
  time.sleep(.15)
 raise AssertionError('Timeout: '+expression+'; '+str(js('document.body.innerText')).replace('\n',' ')[:1500])
def click(selector):js('document.querySelector('+json.dumps(selector)+').click()')
def values(form,fields):
 js('(()=>{const f=document.querySelector('+json.dumps(form)+');for(const [k,v] of Object.entries('+json.dumps(fields,ensure_ascii=False)+'))f.elements.namedItem(k).value=v;})()')
def submit(form):js('document.querySelector('+json.dumps(form)+').requestSubmit()')
def check(name):checks.append(name);print('PASS',name,flush=True)
def go(page):rpc('Page.navigate',{'url':BASE+'/assets/health-archive/'+page});wait("document.querySelector('.page-nav') && !document.querySelector('#app .loading')");wait("document.body.innerText.includes('数据库实时数据')")
try:
 for _ in range(100):
  try:tabs=s.get('http://localhost:9224/json',timeout=1).json();break
  except Exception:time.sleep(.1)
 ws=websocket.create_connection(next(t['webSocketDebuggerUrl'] for t in tabs if t['type']=='page'),timeout=30,suppress_origin=True)
 rpc('Page.enable');rpc('Runtime.enable');rpc('Network.enable');rpc('Network.setCacheDisabled',{'cacheDisabled':True});rpc('Emulation.setDeviceMetricsOverride',{'width':1920,'height':1080,'deviceScaleFactor':1,'mobile':False})
 rpc('Network.setCookies',{'cookies':[{'name':c.name,'value':c.value,'domain':'localhost','path':'/'} for c in s.cookies]})
 # Synthetic instance is deliberately marked test mode; real-data badge is not expected here.
 def go(page):
  rpc('Page.navigate',{'url':BASE+'/assets/health-archive/'+page});wait("document.querySelector('.page-nav') && document.body.innerText.includes('synthetic隔离')")
 go('management.html');check('management_loaded_real_api_no_demo_store')
 assert not js("Array.from(document.scripts).some(s=>/store.js|management.js/.test(s.src))")
 click('[data-do="new"]');values('#edit-form',{'name':'页面联调职工','code':code,'org':'界面测试单位','team':'测试一队','job':'测试岗位','sex':'女','birth':'1988-01-01'});submit('#edit-form');wait('!document.querySelector("#archive-dialog").open');wait('document.body.innerText.includes('+json.dumps(code)+')');check('ui_create_record_database_persisted')
 records=s.get(BASE+'/ehr/api/records',params={'code':code},timeout=10).json()['data']['items'];record=records[0]['id'];assert records[0]['name']=='页面联调职工'
 click('[data-do="edit"][data-id="'+record+'"]');wait('document.querySelector("#archive-dialog").open && document.querySelector(".dialog-head").innerText.includes("编辑授权档案")');values('#edit-form',{'team':'已通过页面编辑'});submit('#edit-form');wait('!document.querySelector("#archive-dialog").open');wait("document.body.innerText.includes('已通过页面编辑')");check('ui_edit_record')
 values('#filters',{'search':code});submit('#filters');wait('document.querySelectorAll(".archive-table tbody tr").length===1');check('ui_search_and_filters')
 click('[data-do="binding-code"][data-id="'+record+'"]');wait('document.querySelector("#binding-code-form")');submit('#binding-code-form');wait('document.querySelector("#archive-dialog input[readonly]")');secret=js('document.querySelector("#archive-dialog input[readonly]").value');assert len(secret.replace('-',''))==16
 assert not js('Object.values(localStorage).some(v=>v.includes('+json.dumps(secret)+'))');check('ui_personal_binding_code_generated_without_local_storage')
 click('[data-do="close"]');click('[data-do="binding-code"][data-id="'+record+'"]');wait('document.querySelector("#binding-code-form")');assert not js('document.querySelector("#archive-dialog").innerText.includes('+json.dumps(secret)+')');click('[data-do="revoke-binding-code"]');wait('!document.querySelector("#archive-dialog").open');metadata=s.get(BASE+'/ehr/api/records/'+record+'/binding',timeout=10).json()['data'];assert metadata['lastCode']['status']=='revoked';check('ui_code_plaintext_not_reloaded_and_revocation_persisted')
 go('detail.html?id='+record);wait("document.body.innerText.includes('页面联调职工')");click('[data-do="upload"]');wait('document.querySelector("#upload-form")');values('#upload-form',{'record':record})
 img=ROOT/'.tools/runtime/ehr-ui-upload.png';meta=PngInfo();meta.add_text('syntheticTestId',code);Image.new('RGB',(24,24),(55,200,180)).save(img,pnginfo=meta)
 doc=rpc('DOM.getDocument');node=rpc('DOM.querySelector',{'nodeId':doc['root']['nodeId'],'selector':'#upload-form input[type=file]'})['nodeId'];rpc('DOM.setFileInputFiles',{'nodeId':node,'files':[str(img)]});(ROOT/'.tools/runtime/ocr-upload-dialog.png').write_bytes(base64.b64decode(rpc('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']));submit('#upload-form');wait('document.querySelector("#ocr-progress")');assert '等待识别服务领取' in js('document.querySelector("#ocr-progress").innerText');check('ui_upload_private_report_and_durable_queue_progress')
 if os.getenv('EHR_OCR_WORKER_TOKEN'):
  worker_headers={'Authorization':'Bearer '+os.environ['EHR_OCR_WORKER_TOKEN']};reply=s.post(BASE+'/ehr/worker/tasks/claim',json={'workerId':'browser-fixture','requestKey':str(uuid.uuid4())},headers=worker_headers,timeout=20);assert reply.status_code==200,reply.text;task=reply.json()['data']['task'];assert task['jobId']==js('document.querySelector("#archive-dialog").dataset.ocrJob')
  heartbeat=s.post(BASE+'/ehr/worker/tasks/'+task['jobId']+'/heartbeat',json={'stage':'field_extraction','percent':60},headers=dict(worker_headers,**{'X-OCR-Lease':task['leaseToken']}),timeout=20);assert heartbeat.status_code==200,heartbeat.text;wait('document.querySelector("#ocr-progress").innerText.includes("60%")');check('ui_automatically_polls_worker_progress');(ROOT/'.tools/runtime/ocr-upload-progress.png').write_bytes(base64.b64decode(rpc('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 click('[data-do="close"]');wait('document.querySelector(".report-manage")')
 click('[data-do="candidate"]');wait('document.querySelector("#candidate-form")');values('#candidate-form',{'fullName':'页面联调职工','examDate':'2026-11-14','institution':'界面联调机构','formalConclusion':'仅用于界面流程验证的原文结论','advice':'原文建议，非临床建议'})
 js('(()=>{let r=document.querySelector("#metric-rows tr");for(let [k,v] of Object.entries({name:"测试原文项目",value:"阴性 / ＜5",unit:"原文单位",referenceRange:"原文范围",abnormal:"",page:"1"}))r.querySelector(`[data-item="${k}"]`).value=v})()');submit('#candidate-form');wait('!document.querySelector("#archive-dialog").open');wait("document.body.innerText.includes('阴性 / ＜5')");check('ui_save_raw_candidate')
 for action in ['submit','approve','archive']:
  click('[data-do="action"][data-action="'+action+'"]');wait('document.querySelector("#archive-dialog").open && document.querySelector("#action-form")');values('#action-form',{'note':'synthetic页面联调确认原件与资料'})
  if action=='approve':js('document.querySelector("#action-form input[name=identity]").checked=true')
  submit('#action-form');wait('!document.querySelector("#archive-dialog").open');time.sleep(.3)
  check('ui_'+action+'_workflow')
 go('index.html');values('#people-filter',{'search':code});submit('#people-filter');wait("document.body.innerText.includes('页面联调职工') && document.querySelector('.report-manage')");check('smart_archive_only_published_report_visible')
 go('statistics.html');wait('document.querySelectorAll(".live-chart svg").length>=5');check('statistics_charts_with_database_data')
 shot=ROOT/'.tools/runtime/ehr-statistics-live.png';shot.write_bytes(base64.b64decode(rpc('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 go('review.html');wait('document.querySelector(".review-tabs")');check('review_page_real_filters')
 go('management.html');click('[data-do="delete"][data-id="'+record+'"]');wait('document.querySelector("#delete-form")');values('#delete-form',{'reason':'synthetic页面联调完成'});submit('#delete-form');wait('!document.querySelector("#archive-dialog").open');wait('!document.body.innerText.includes('+json.dumps(code)+')');check('ui_person_delete_cascades_reports')
 assert not errors,errors
 out.write_text(json.dumps({'mode':'synthetic','checks':checks,'count':len(checks),'consoleErrors':errors},ensure_ascii=False,indent=2),encoding='utf-8');print('PASS pages:',len(checks),'checks',flush=True)
finally:
 if ws:ws.close()
 chrome.terminate()
 if record:
  r=s.get(BASE+'/ehr/api/records/'+record,timeout=10)
  if r.status_code==200:s.delete(BASE+'/ehr/api/records/'+record,json={'rowVersion':r.json()['data']['rowVersion'],'reason':'UI synthetic test cleanup'},headers={'X-CSRF-Token':ctx['csrfToken']},timeout=10)
