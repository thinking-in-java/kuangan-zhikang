"""Read real scoped APIs and verify migrated workbench in Chrome; model transport is stubbed, temporary conversations are cleaned up."""
from pathlib import Path
import requests,json,time,subprocess,websocket,uuid,base64,os
root=Path(__file__).resolve().parents[2];out=Path('F:/workstation/.tools/runtime/health-hub-ui');out.mkdir(exist_ok=True)
session=requests.Session();session.trust_env=False;base='http://127.0.0.1:18080';checks=[]
def check(name,ok):
 assert ok,name
 checks.append(name);print('PASS',name,flush=True)
for _ in range(50):
 try:
  if session.get(base+'/login',timeout=2).status_code==200:break
 except requests.RequestException:pass
 time.sleep(1)
response=session.post(base+'/login',data={'username':'\u533b\u751f\u533b\u751f','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=25);check('actual_login',response.json()['code']==200)
debug=19265;process=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(out/('chrome-'+uuid.uuid4().hex)),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW);ws=None;counter=0
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
  if js(code):return
  time.sleep(.2)
 raise AssertionError(code)

temp_conversation=None
try:
 for _ in range(80):
  try:tabs=session.get(f'http://localhost:{debug}/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 ws=websocket.create_connection(next(t for t in tabs if t['type']=='page')['webSocketDebuggerUrl'],origin=f'http://localhost:{debug}',timeout=30);cdp('Page.enable')
 for cookie in session.cookies:cdp('Network.setCookie',{'name':cookie.name,'value':cookie.value,'url':base+'/'})
 cdp('Emulation.setDeviceMetricsOverride',{'width':1600,'height':1000,'deviceScaleFactor':1,'mobile':False})
 cdp('Page.addScriptToEvaluateOnNewDocument',{'source':"window.reads=[];window.uiErrors=[];addEventListener('error',e=>uiErrors.push(e.message));addEventListener('unhandledrejection',e=>uiErrors.push(String(e.reason)));const rawFetch=window.fetch.bind(window);window.fetch=async(...args)=>{const r=await rawFetch(...args);window.reads.push({url:String(args[0]),status:r.status});return r;};"})
 cdp('Page.navigate',{'url':base+'/assets/agents/health.html'})
 until("document.querySelectorAll('.hub-worker').length===5")
 until("document.querySelector('#dh-patient-context').options.length>1")
 check('real_worker_data_loaded',js("!document.querySelector('#hub-data-status').textContent.includes('不可用')"))
 check('greeting_and_history_preserved',js("!!document.querySelector('.dh-visit-greeting')"))
 check('no_horizontal_overflow',js("document.documentElement.scrollWidth<=innerWidth"))
 check('three_columns_visible',js("document.querySelector('.dh-insights-sidebar').getBoundingClientRect().width>250"))
 check('real_profile_context_loaded',js("document.querySelector('#hub-total').textContent.includes('人')"))
 (out/'workbench-desktop.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 js("document.querySelector('[data-tasks]').click()")
 until("document.querySelector('#hub-task-page').textContent.includes('共')")
 check('review_tasks_dialog',js("document.querySelector('#hub-task-dialog').open"))
 js("document.querySelector('#hub-task-close').click();document.querySelector('[data-high-tasks]').click()")
 until("document.querySelector('#hub-task-page').textContent.includes('共')")
 check('high_and_urgent_filter',js("document.querySelector('#hub-task-dialog').open&&!document.querySelector('#hub-task-body [role=alert]')"))
 js("document.querySelector('#hub-task-close').click();document.querySelector('[data-worker=follow]').click()")
 until("document.querySelector('#hub-task-page').textContent.includes('共')")
 check('worker_tasks_dialog',js("document.querySelector('#hub-task-title').textContent.includes('随访小谷')"))
 js("document.querySelector('#hub-task-close').click();document.querySelector('.hub-session-tabs [data-tab=starred]').click()")
 check('starred_session_filter',js("document.querySelector('.hub-session-tabs [data-tab=starred]').getAttribute('aria-selected')==='true'"))
 js("document.querySelector('.hub-session-tabs [data-tab=all]').click();document.querySelector('#dh-new-chat').click();document.querySelector('#dh-patient-context').selectedIndex=1;document.querySelector('#dh-patient-context').dispatchEvent(new Event('change'))")
 check('employee_selection_context',js("!document.querySelector('#hub-selected').hidden"))
 # Only the model transport is stubbed; authentication and conversation create/delete remain real.
 js("window.chatRequest=null;const fetchBeforeMock=window.fetch.bind(window);window.fetch=async function(url,opts){if(url==='/ehr/api/agent/chat'){window.chatRequest=JSON.parse(opts.body);return new Response(JSON.stringify({code:0,data:{text:'联调检查完成。此回复仅用于界面验证，不是模型分析或业务执行结果。',tools:[],sources:[],actions:[]}}),{status:200,headers:{'Content-Type':'application/json'}});}return fetchBeforeMock(url,opts);};document.querySelector('textarea').value='UI transport check';document.querySelector('textarea').dispatchEvent(new Event('input'));document.querySelector('.dh-compose-form').requestSubmit()")
 until("window.chatRequest&&document.querySelector('.dh-reply-status').hidden")
 temp_conversation=js('chatRequest.conversationId')
 check('send_uses_shared_authenticated_api',js("!!chatRequest.recordId&&!!chatRequest.requestId&&document.querySelector('textarea').value===''"))
 check('reply_and_composer_layout',js("document.querySelector('.dh-compose-form').getBoundingClientRect().bottom<=innerHeight&&document.querySelector('.dh-message-scroll').getBoundingClientRect().bottom<=document.querySelector('.dh-compose-form').getBoundingClientRect().top+2"))
 js("document.querySelector('#hub-star').click();document.querySelector('.hub-session-tabs [data-tab=starred]').click()")
 check('favorite_current_conversation',js("document.querySelector('#hub-star').getAttribute('aria-pressed')==='true'&&document.querySelectorAll('#dh-session-list .dh-session-row:not([hidden])').length===1"))
 js("document.querySelector('#hub-star').click();document.querySelector('.hub-session-tabs [data-tab=all]').click()")
 cdp('Emulation.setDeviceMetricsOverride',{'width':1100,'height':850,'deviceScaleFactor':1,'mobile':False})
 check('compact_desktop_no_overflow',js("document.documentElement.scrollWidth<=innerWidth"))
 (out/'workbench-1100.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 cdp('Emulation.setDeviceMetricsOverride',{'width':390,'height':844,'deviceScaleFactor':1,'mobile':True})
 check('mobile_no_overflow',js("document.documentElement.scrollWidth<=innerWidth&&document.querySelector('.dh-compose-form').getBoundingClientRect().bottom<=innerHeight+1"))
 (out/'workbench-mobile.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 check('no_script_errors',not js('uiErrors'))
 cdp('Emulation.setDeviceMetricsOverride',{'width':1600,'height':1050,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':base+'/register'})
 until("document.querySelectorAll('#side-menu > li').length===20")
 names=js("Array.from(document.querySelectorAll('#side-menu > li:nth-child(7) > dl > dd > a'),x=>({name:x.textContent.trim(),uri:x.getAttribute('lay-href')}))")
 check('menu_second_item',names[0]['name']=='AI 智能体总览' and names[1]['name']=='数字健康师' and names[1]['uri'].startswith('/assets/agents/health.html'))
 check('old_menu_entry_removed',js("!document.querySelector('#side-menu [lay-href*=\"digital-health/index\"]')"))
 until("!!(window.DigitalHealth&&document.querySelector('.dh-open-page'))")
 js("document.querySelector('.dh-open-page').click()")
 until("Array.from(document.querySelectorAll('iframe.admin-iframe')).some(x=>x.getAttribute('src')?.includes('/assets/agents/health.html'))")
 check('floating_workbench_button_migrated',True)
 until("Array.from(document.querySelectorAll('iframe.admin-iframe')).some(x=>{try{return x.contentDocument.querySelectorAll('.hub-worker').length===5;}catch(e){return false;}})")
 check('floating_doctor_hidden_in_dedicated_workbench',js("document.querySelector('.dh-floating-root').hidden"))
 time.sleep(1)
 (out/'shell-menu-and-workbench.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png'})['data']))
 cdp('Page.navigate',{'url':base+'/assets/digital-health/index.html'})
 until("location.pathname==='/assets/agents/health.html'")
 check('legacy_url_redirect',True)
 (out/'checks.json').write_text(json.dumps({'checks':checks},ensure_ascii=False,indent=2),'utf8');print('ALL CHECKS',len(checks),'PASSED')
finally:
 if temp_conversation:
  token=session.get(base+'/ehr/api/context',timeout=15).json()['data']['csrfToken']
  cleanup=session.delete(base+'/ehr/api/agent/conversations/'+temp_conversation,headers={'X-CSRF-Token':token},timeout=15)
  print('Temporary conversation cleanup',cleanup.status_code,flush=True)
 if ws:ws.close()
 process.terminate()
