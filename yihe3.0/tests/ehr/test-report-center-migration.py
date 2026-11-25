"""Read real scoped APIs and verify migrated workbench in Chrome; model transport is stubbed, temporary conversations are cleaned up."""
from pathlib import Path
import requests,json,time,subprocess,websocket,uuid,base64,os
root=Path(__file__).resolve().parents[2];out=Path('F:/workstation/.tools/runtime/report-center-migration');out.mkdir(exist_ok=True)
session=requests.Session();session.trust_env=False;base='http://127.0.0.1:18080';checks=[]
def check(name,ok):
 assert ok,name
 checks.append(name);print('PASS',name,flush=True)
for _ in range(50):
 try:
  if session.get(base+'/login',timeout=10).status_code==200:break
 except requests.RequestException:pass
 time.sleep(1)
response=session.post(base+'/login',data={'username':'\u533b\u751f\u533b\u751f','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=25);check('actual_login',response.json()['code']==200)
debug=19268;process=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(out/('chrome-'+uuid.uuid4().hex)),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW);ws=None;counter=0
def cdp(method,params=None):
 global counter
 counter+=1;ident=counter;ws.send(json.dumps({'id':ident,'method':method,'params':params or {}}))
 while True:
  r=json.loads(ws.recv())
  if r.get('id')==ident:
   assert 'error' not in r,r
   return r.get('result',{})
def js(code):
 r=cdp('Runtime.evaluate',{'expression':code,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r
 return r['result'].get('value')
def until(code):
 for _ in range(150):
  if js("Boolean("+code+")"):return
  time.sleep(.2)
 print(js("({body:document.body.innerText.slice(-500),frame:document.querySelector('#shared-report-action')?.contentDocument?.body?.innerText,errors:uiErrors})"));raise AssertionError(code)


try:
 for _ in range(80):
  try:tabs=session.get(f'http://localhost:{debug}/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 ws=websocket.create_connection(next(t for t in tabs if t['type']=='page')['webSocketDebuggerUrl'],origin=f'http://localhost:{debug}',timeout=30);cdp('Page.enable')
 for cookie in session.cookies:cdp('Network.setCookie',{'name':cookie.name,'value':cookie.value,'url':base+'/'})
 cdp('Emulation.setDeviceMetricsOverride',{'width':1600,'height':1000,'deviceScaleFactor':1,'mobile':False})
 cdp('Page.addScriptToEvaluateOnNewDocument',{'source':"window.uiErrors=[];addEventListener('error',e=>uiErrors.push(e.message));addEventListener('unhandledrejection',e=>uiErrors.push(String(e.reason)));"})
 cdp('Page.navigate',{'url':base+'/assets/report-center/index.html'})
 until("document.querySelectorAll('#report-rows [data-row=progress]').length>0")
 check('real_report_rows_loaded',js("document.querySelectorAll('#report-rows tr').length>0"))
 check('no_horizontal_overflow',js("document.documentElement.scrollWidth<=innerWidth"))
 check('migrated_toolbar',js("['camera','sync','new','corrections','import','tasks'].every(a=>document.querySelector('.archive-tools [data-action='+a+']'))"))
 (out/'upload-center.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 def open_action(selector,ready):
  js('document.querySelector('+json.dumps(selector)+').click()')
  until("document.querySelector('#shared-report-action')?.contentDocument?.querySelector("+json.dumps(ready)+")")
  check('shared_action_'+selector,js("document.querySelector('#center-dialog').open"))
 def close_action():
  js("document.querySelector('#shared-report-action').contentDocument.querySelector('[data-do=close]').click()")
  until("!document.querySelector('#center-dialog').open")
 js("document.querySelector('.archive-tools [data-action=single]').click()");check('single_file_picker',js("!document.querySelector('#file-picker').multiple"))
 open_action('[data-action=tasks]','.dialog-body')
 close_action()
 open_action('.archive-tools [data-action=tasks]','.dialog-body')
 until("document.querySelector('#shared-report-action').contentDocument.querySelectorAll('[data-ocr-state]').length>=4")
 check('task_filters_and_pagination',js("document.querySelector('#shared-report-action').contentDocument.querySelectorAll('[data-ocr-page]').length===2"));close_action()
 open_action('[data-row=progress]','.dialog-body')
 check('report_progress_opens',js("document.querySelector('#shared-report-action').contentDocument.querySelector('.dialog-head').textContent.length>0"));close_action()
 open_action('[data-row=candidate]','#candidate-form')
 check('candidate_metadata_fields',js("(()=>{const d=document.querySelector('#shared-report-action').contentDocument;return ['examDate','institution','formalConclusion','advice'].every(n=>d.querySelector('[name='+n+']'))})()"));close_action()
 open_action('[data-row=history]','.dialog-body');close_action()
 open_action('[data-action=new]','#edit-form');close_action()
 open_action('[data-action=import]','#import-form');close_action()
 open_action('[data-action=corrections]','.dialog-body');close_action()
 open_action('[data-action=sync]','.dialog-body');close_action()

 # Isolated wizard transport tests: fixture data only; no real uploads or writes.
 js("""window.wizardCalls=[];window.wizardPerson={id:'fixture-record',name:'Test Worker',code:'TEST-001',profileVersion:1};window.wizardReport={id:'fixture-report',revision:1,items:[{name:'fixture'}],fieldValues:[{field_key:'full_name',value_raw:'Test Worker'}],examDateRaw:'2026-11-25',institution:'Fixture Hospital',ocrTask:{jobId:'fixture-job',state:'queued'}};window.wizardMatch={id:'fixture-report',revision:1,recordId:'fixture-record',etag:'fixture-etag',candidateCount:1,candidates:[{...wizardPerson,exact:true,score:90,evidence:['name/code match']}],ocr:{name:'Test Worker',code:'TEST-001',conflicts:[]}};window.wizardHost={api:async(p,m,b,h)=>{wizardCalls.push({p,m,b,h});if(p.includes('/employees?'))return [wizardPerson];if(p.startsWith('/ocr/tasks/'))return {jobId:'fixture-job',state:'succeeded',percent:100};if(p.startsWith('/reports/'))return wizardReport;return wizardMatch;},modal:(t,h)=>{document.querySelector('#dialog-title').textContent=t;document.querySelector('#dialog-body').innerHTML=h;document.querySelector('#center-dialog').showModal();},uuid:()=>crypto.randomUUID(),upload:async(p,b,k,progress)=>{wizardCalls.push({upload:p,key:k});progress(50);progress(100);return wizardReport;},reload:async()=>{},toast:()=>{},setBusy:()=>{},openCheck:()=>{},openProgress:()=>{}};ReportUploadWizard.open([new File(['fixture'],'synthetic-test.pdf',{type:'application/pdf'})],wizardHost);""")
 until("document.querySelector('#uw-person').options.length===2")
 check('wizard_four_steps',js("document.querySelectorAll('.uw-steps li').length===4"))
 check('no_fabricated_identity_before_ocr',js("!document.querySelector('#uw-metadata').innerText.includes('Test Worker')"))
 check('upload_requires_employee',js("document.querySelector('#uw-next').disabled"))
 js("document.querySelector('#uw-person').value='fixture-record';document.querySelector('#uw-person').dispatchEvent(new Event('change'));document.querySelector('#uw-next').click()")
 until("document.querySelectorAll('.uw-candidate').length===1&&!document.querySelector('#uw-next').disabled")
 check('real_contract_matching_candidates',js("document.querySelector('#uw-metadata').innerText.includes('Test Worker')&&document.querySelector('#uw-metadata').innerText.includes('2026-11-25')"))
 (out/'upload-wizard-matching.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 js("document.querySelector('#uw-next').click()")
 until("document.querySelector('#uw-error').textContent.length>0")
 check('identity_confirmation_required',js("wizardCalls.filter(c=>c.m==='POST').length===0"))
 js("document.querySelector('#uw-draft').click()")
 until("wizardCalls.some(c=>c.b?.action==='selection')&&!document.querySelector('#uw-draft').disabled")
 check('draft_uses_existing_matching_etag',js("wizardCalls.find(c=>c.b?.action==='selection').h['If-Match']==='fixture-etag'"))
 js("document.querySelector('#uw-verified').checked=true;document.querySelector('#uw-next').click()")
 until("wizardCalls.some(c=>c.b?.action==='confirm')")
 check('confirmation_not_publication',js("wizardCalls.find(c=>c.b?.action==='confirm').b.identityVerified===true&&!wizardCalls.some(c=>c.b?.action==='archive'||c.b?.action==='approve')"))
 js("document.querySelector('#center-dialog').close()")
 check('no_javascript_errors',js('uiErrors.length===0'))
 check('old_archive_still_available',session.get(base+'/assets/health-archive/management.html',timeout=10).status_code==200)
 (out/'checks.json').write_text(json.dumps(checks,indent=2),encoding='utf-8')
 print('PASSED',len(checks),'checks; no uploads or business mutations performed.')
finally:
 if ws:ws.close()
 process.terminate()
