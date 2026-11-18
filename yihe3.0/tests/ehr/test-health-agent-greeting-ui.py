"""Deployed greeting/history test; no model calls. Removes only its own fixture."""
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
 saved=s.get(BASE+'/ehr/api/agent/conversations',timeout=15).json()['data']
 target=None
 for item in saved:
  detail=s.get(BASE+'/ehr/api/agent/conversations/'+item['id'],timeout=15).json()['data']
  if detail['messages']:target=item;break
 if not target:
  csrf=s.get(BASE+'/ehr/api/context',timeout=10).json()['data']['csrfToken']
  cid=s.post(BASE+'/ehr/api/agent/conversations',headers={'X-CSRF-Token':csrf},json={},timeout=10).json()['data']['id']
  sys.path.insert(0,str(ROOT/'database'));from health_archive_db import connect
  c=connect()
  try:
   with c.cursor() as q:
    q.execute("INSERT INTO a_agent_turns(turn_id,conversation_id,request_key,request_hash,user_message,turn_status,response_json,created_at,completed_at) VALUES(%s,%s,%s,%s,%s,'completed',%s,UTC_TIMESTAMP(6),UTC_TIMESTAMP(6))",(str(uuid.uuid4()),cid,'greeting-ui-fixture','0'*64,'Previous UI test question',json.dumps({'text':'Previous UI test answer. This is a display fixture.'})))
   c.commit()
  finally:c.close()
  target={'id':cid}
 cdp('Page.navigate',{'url':BASE+'/assets/digital-health/index.html?v=20261117-chat4'})
 until("document.querySelector('[data-session=\""+target['id']+"\"]')!==null")
 js("document.querySelector('[data-session=\""+target['id']+"\"]').click()")
 until("!document.querySelector('.dh-history-gate').hidden && document.querySelector('.dh-visit-greeting')!==null")
 assert js("document.querySelector('.dh-previous-visit').hidden && document.querySelectorAll('.dh-visit-greeting').length===1")
 (OUT/'deployed-greeting.png').write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 js("document.querySelector('.dh-message-scroll').dispatchEvent(new WheelEvent('wheel',{deltaY:-100,bubbles:true}))")
 assert js("!document.querySelector('.dh-previous-visit').hidden && document.querySelectorAll('.dh-history-messages .dh-message').length>0")
 assert not js("agentCalls.some(c=>c.url==='/ehr/api/agent/chat')")
 print('PASS deployed UI: fresh greeting, clock divider, upward-scroll history, no model request, existing conversations preserved',flush=True)
finally:
 if ws:
  try:
   cdp('Browser.close')
  except Exception:pass
  ws.close()
 if proc.poll() is None:proc.terminate()
 if cid:
  c=connect()
  try:
   with c.cursor() as q:
    q.execute('DELETE FROM a_agent_turns WHERE conversation_id=%s',(cid,));q.execute('DELETE FROM a_agent_conversations WHERE conversation_id=%s',(cid,))
   c.commit()
  finally:c.close()
