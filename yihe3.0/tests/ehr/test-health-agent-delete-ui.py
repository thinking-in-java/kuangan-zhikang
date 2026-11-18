"""Deployed browser conversation deletion; creates and removes only its own empty fixture, no model calls."""
from pathlib import Path
import requests,websocket,subprocess,json,time,base64,uuid,os,sys

ROOT=Path(__file__).resolve().parents[2];BASE=os.environ.get('EHR_AGENT_UI_URL','http://localhost:18080')
OUT=Path('F:/workstation/.tools/runtime/agent-ui-v2');OUT.mkdir(parents=True,exist_ok=True)
s=requests.Session();s.trust_env=False
s.get(BASE+'/login',timeout=10)
assert s.post(BASE+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'rememberMe':'false','code':'123465'},timeout=30).json()['code']==200
debug=19244;counter=0;ws=None;cid=None
proc=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--no-default-browser-check','--remote-debugging-address=127.0.0.1','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(OUT/('deployed-chrome-'+uuid.uuid4().hex)),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
def cdp(method,params=None):
 global counter
 counter+=1;ident=counter;ws.send(json.dumps({'id':ident,'method':method,'params':params or {}}))
 while True:
  r=json.loads(ws.recv())
  if r.get('id')==ident:
   assert 'error' not in r,r;return r.get('result',{})
def js(expression):
 r=cdp('Runtime.evaluate',{'expression':expression,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r
 return r['result'].get('value')
def until(expression,seconds=75):
 start=time.monotonic()
 while time.monotonic()-start<seconds:
  if js(expression):return
  time.sleep(.2)
 raise AssertionError('UI timeout: '+expression)
try:
 for _ in range(80):
  try:tabs=requests.get('http://localhost:'+str(debug)+'/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 tab=next(x for x in tabs if x['type']=='page');ws=websocket.create_connection(tab['webSocketDebuggerUrl'],origin='http://localhost:'+str(debug),timeout=30)
 cdp('Page.enable');cdp('Network.enable');cdp('Emulation.setDeviceMetricsOverride',{'width':1450,'height':920,'deviceScaleFactor':1,'mobile':False})
 for cookie in s.cookies:cdp('Network.setCookie',{'name':cookie.name,'value':cookie.value,'url':BASE,'path':cookie.path or '/'})
 cdp('Page.addScriptToEvaluateOnNewDocument',{'source':"window.agentCalls=[];const realFetch=window.fetch.bind(window);window.fetch=async function(url,opts){const r=await realFetch(url,opts);let data;try{data=await r.clone().json();}catch(e){}window.agentCalls.push({url,status:r.status,conversationId:data&&data.data&&data.data.conversationId,newId:(url==='/ehr/api/agent/conversations'&&opts&&opts.method==='POST')?data.data.id:null});return r;};"})
 csrf=s.get(BASE+'/ehr/api/context',timeout=10).json()['data']['csrfToken']
 headers={'X-CSRF-Token':csrf}
 previous={x['id'] for x in s.get(BASE+'/ehr/api/agent/conversations',timeout=10).json()['data']}
 cid=s.post(BASE+'/ehr/api/agent/conversations',headers=headers,json={},timeout=10).json()['data']['id']
 cdp('Page.navigate',{'url':BASE+'/assets/digital-health/index.html?v=20261117-chat3'})
 until("document.querySelector('[data-delete-session=\""+cid+"\"]')!==null")
 js("document.querySelector('[data-delete-session=\""+cid+"\"]').click()")
 until("document.querySelector('#dh-delete-session-dialog')?.open")
 (OUT/'delete-conversation-confirm.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 js("document.querySelector('#dh-delete-session-dialog .dh-dialog-close').click()")
 assert s.get(BASE+'/ehr/api/agent/conversations/'+cid,timeout=10).status_code==200
 js("document.querySelector('[data-delete-session=\""+cid+"\"]').click();document.querySelector('#dh-delete-session-dialog form').requestSubmit()")
 until("!document.querySelector('#dh-delete-session-dialog')")
 assert not js("document.querySelector('[data-delete-session=\""+cid+"\"]')!==null")
 assert s.get(BASE+'/ehr/api/agent/conversations/'+cid,timeout=10).status_code==404
 after={x['id'] for x in s.get(BASE+'/ehr/api/agent/conversations',timeout=10).json()['data']}
 assert after==previous,(after,previous)
 assert js("document.querySelectorAll('.dh-session-row.is-active').length")==1
 (OUT/'delete-conversation-complete.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 print('PASS deployed UI: visible delete button, cancel retains conversation, confirmed DELETE, removed on reload, other conversations preserved, active conversation switched',flush=True)
finally:
 if ws:
  try:
   (OUT/'delete-conversation-last.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
   print('UI final state:',js("({url:location.href,deleteButtons:document.querySelectorAll('[data-delete-session]').length,mode:document.querySelector('.dh-mode-tag')?.textContent,error:document.querySelector('.dh-action-error')?.textContent,calls:window.agentCalls})"),flush=True)
   if not cid:cid=js("agentCalls.find(c=>c.newId)?.newId")
   cdp('Browser.close')
  except Exception:pass
  ws.close()
 if proc.poll() is None:proc.terminate()
 if cid:
  sys.path.insert(0,str(ROOT/'database'));from health_archive_db import connect
  c=connect()
  try:
   with c.cursor() as q:
    q.execute('DELETE l FROM a_agent_tool_logs l JOIN a_agent_turns t ON t.turn_id=l.turn_id WHERE t.conversation_id=%s',(cid,))
    q.execute('DELETE FROM a_agent_actions WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_turns WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_conversations WHERE conversation_id=%s',(cid,))
   c.commit()
  finally:c.close()
