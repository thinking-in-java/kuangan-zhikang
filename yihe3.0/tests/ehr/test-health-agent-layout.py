"""Isolated headless Chrome layout regression. Synthetic HTTP stub; no DB/model calls."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid

ROOT=Path(__file__).resolve().parents[2]
STATIC=ROOT/'yihe-admin/src/main/resources/static'
OUT=Path('F:/workstation/.tools/runtime/agent-ui-v2');OUT.mkdir(parents=True,exist_ok=True)
RID='00000000-0000-4000-8000-000000000002'
CID='00000000-0000-4000-8000-000000000001'
SOURCES=[{'kind':'report','recordId':RID,'reportId':'fixture-report','revision':1,'label':'界面测试职工 · 体检报告 V1','files':[{'id':'fixture-file','name':'0123456789abcdef0123456789abcdef.pdf'}]}]*4
STUB='''
window.fixtureCalls=[];
window.fetch=async function(url,options={}){
 const body=options.body?JSON.parse(options.body):{};window.fixtureCalls.push({url,method:options.method||'GET',body});let data;
 if(url==='/ehr/api/context')data={csrfToken:'fixture'};
 else if(url==='/ehr/api/agent/context')data={enabled:true,dataMode:'synthetic',statistics:{total:2305,high:4,pending:8,archived:16}};
 else if(url==='/ehr/api/agent/conversations')data=options.method==='POST'?{id:CID}:[];
 else if(url.includes('/records?'))data={items:[{id:RID,name:'界面测试职工',code:'TEST001',team:'测试班组'}]};
 else if(url==='/ehr/api/agent/chat'){
 await new Promise(r=>setTimeout(r,100));
 data={text:REPLY,tools:[{name:'get_exam_report',status:'ok'}],elapsedMs:17281,sources:SOURCES,actions:[]};
 }else if(url.includes('/conversations/'))data={messages:[{role:'user',text:'Previous report question'},{role:'ai',text:REPLY}],actions:[],lastResult:{text:REPLY,sources:SOURCES,tools:[]}};
 else data={messages:[],actions:[]};
 return {ok:true,status:200,json:async()=>({code:0,data})};
};
'''
REPLY=('## 体检报告分析\n\n以下仅为合成界面测试，用于检查长回复排版。\n\n'
       '**报告状态：待审核**，数据需要医生核对。\n\n'
       '### 需要关注的项目\n\n- 血压：135/88 mmHg，结合报告参考范围核对。\n- 肺功能：原文提示未见明显异常。\n\n'
       '| 项目 | 结果 | 原始单位 |\n| --- | --- | --- |\n| 血糖 | 5.2 | mmol/L |\n\n'
       '### 职业健康相关\n\n煤尘、噪声等职业暴露信息需要结合岗位和历次报告分析。\n\n')*3
FIXTURE=STUB.replace('CID',json.dumps(CID)).replace('RID',json.dumps(RID)).replace('REPLY',json.dumps(REPLY,ensure_ascii=True)).replace('SOURCES',json.dumps(SOURCES,ensure_ascii=True))
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*a,**kw):super().__init__(*a,directory=str(STATIC),**kw)
 def log_message(self,*args):pass
 def do_GET(self):
  if self.path.startswith('/floating-test'):
   html='<!doctype html><meta charset="utf-8"><link rel="stylesheet" href="/assets/css/digital-health.css"><link rel="stylesheet" href="/assets/css/health-agent.css"><style>body{background:#e7eef0}</style><body><script>'+FIXTURE+'</script><script src="/editormd/lib/marked.min.js"></script><script src="/assets/js/digital-health.js"></script><script>DigitalHealth.mountFloating({code:"test",name:"测试医生",gender:2});</script>'
  elif self.path.startswith('/workbench-test'):
   html=(STATIC/'assets/digital-health/index.html').read_text(encoding='utf8').replace('<script src="/editormd/lib/marked.min.js">','<script>'+FIXTURE+'</script><script src="/editormd/lib/marked.min.js">')
  else:return super().do_GET()
  data=html.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.send_header('Content-Length',len(data));self.end_headers();self.wfile.write(data)

server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start()
port=server.server_address[1];debug=19243
profile=OUT/('chrome-'+uuid.uuid4().hex)
proc=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--enable-unsafe-swiftshader','--no-first-run','--no-default-browser-check','--remote-debugging-address=127.0.0.1','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(profile),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
ws=None;counter=0;checks=[]
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
def until(expression,seconds=8):
 for _ in range(int(seconds*10)):
  if js(expression):return
  time.sleep(.1)
 raise AssertionError('UI timeout: '+expression)
def check(label,condition):
 assert condition,label;checks.append(label);print('PASS',label,flush=True)
def shot(name):
 (OUT/(name+'.png')).write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
try:
 for _ in range(60):
  try:
   tabs=requests.get('http://localhost:'+str(debug)+'/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 tab=next(x for x in tabs if x['type']=='page');ws=websocket.create_connection(tab['webSocketDebuggerUrl'],origin='http://localhost:'+str(debug),timeout=20)
 cdp('Page.enable')
 for mode,width,height in [('floating',1280,850),('floating-small',390,620),('workbench',1440,900),('workbench-small',390,730)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':width,'height':height,'deviceScaleFactor':1,'mobile':False})
  cdp('Page.navigate',{'url':f'http://localhost:{port}/'+('floating-test' if mode.startswith('floating') else 'workbench-test')})
  until("document.querySelector('.dh-compose-input')!==null")
  if mode.startswith('floating'):
   until("document.querySelector('.dh-floating-root').dataset.avatarMode==='portrait-rig'")
   check(mode+'_real_portrait_rig',js("document.querySelector('canvas.dh-doctor-canvas').width>100"))
   check(mode+'_no_motion_buttons',js("document.querySelectorAll('[data-motion],.dh-motion-controls').length===0"))
   check(mode+'_minimize_inside_bounds',js("(()=>{const root=document.querySelector('.dh-doctor-dock').getBoundingClientRect(),r=document.querySelector('.dh-widget-minimize').getBoundingClientRect();return r.left>=root.left&&r.right<=root.right+1&&r.top>=root.top&&r.bottom<=root.bottom})()"))
   check(mode+'_no_fake_unread',js("document.querySelector('.dh-doctor-badge').hidden"))
   shot(mode+'-doctor-dock')
   js("document.querySelector('.dh-widget-minimize').click()")
   check(mode+'_compact_56px',js("(()=>{const r=document.querySelector('.dh-doctor-dock').getBoundingClientRect();return r.width===56&&r.height===56&&document.querySelector('.dh-floating-panel').hidden})()"))
   shot(mode+'-doctor-compact')
   js("document.querySelector('.dh-widget-restore').click()")
   check(mode+'_restore_without_chat',js("document.querySelector('.dh-doctor-dock').getBoundingClientRect().height===230 && document.querySelector('.dh-floating-panel').hidden"))
   until("document.querySelector('.dh-avatar-stage').dataset.doctorAction==='listen'",seconds=24)
   check(mode+'_gesture_plays_automatically',True)
   check(mode+'_automatic_gesture_no_model_calls',js("!fixtureCalls.some(c=>c.url==='/ehr/api/agent/chat')"))
   shot(mode+'-doctor-auto-listen')
   drag=js("(()=>{const r=document.querySelector('.dh-doctor-dock').getBoundingClientRect();return {x:r.left+83,y:r.top+112,left:r.left,top:r.top}})()")
   cdp('Input.dispatchMouseEvent',{'type':'mouseMoved','x':drag['x'],'y':drag['y']})
   cdp('Input.dispatchMouseEvent',{'type':'mousePressed','x':drag['x'],'y':drag['y'],'button':'left','buttons':1,'clickCount':1})
   cdp('Input.dispatchMouseEvent',{'type':'mouseMoved','x':drag['x']-100,'y':drag['y']-90,'button':'left','buttons':1})
   cdp('Input.dispatchMouseEvent',{'type':'mouseReleased','x':drag['x']-100,'y':drag['y']-90,'button':'left','buttons':0,'clickCount':1})
   check(mode+'_drag_moves_whole_dock_without_chat',js("(()=>{const r=document.querySelector('.dh-doctor-dock').getBoundingClientRect();return r.left<"+str(drag['left'])+"&&r.top<"+str(drag['top'])+"&&r.left>=7&&r.top>=7&&r.bottom<=innerHeight-7&&document.querySelector('.dh-floating-panel').hidden})()"))
   time.sleep(.5)
   # Shrink/restore at the dragged position keeps the bottom-right anchor in bounds.
   anchor=js("(()=>{const r=document.querySelector('.dh-doctor-dock').getBoundingClientRect();return {right:r.right,bottom:r.bottom}})()")
   js("document.querySelector('.dh-widget-minimize').click();document.querySelector('.dh-widget-restore').click()")
   check(mode+'_dragged_restore_keeps_anchor',js("(()=>{const r=document.querySelector('.dh-doctor-dock').getBoundingClientRect();return Math.abs(r.right-"+str(anchor['right'])+")<1&&Math.abs(r.bottom-"+str(anchor['bottom'])+")<1})()"))
   js("(()=>{const s=document.querySelector('.dh-doctor-dock').style;s.left='';s.top='';s.right='';s.bottom=''})()")
   js("document.querySelector('.dh-launcher').click()")
   # A real pending response arrives while hidden; UI stubs keep all model/data calls isolated.
   for unread in range(1,4):
    js("(()=>{const t=document.querySelector('textarea');t.value='Unread fixture';t.dispatchEvent(new Event('input',{bubbles:true}));document.querySelector('.dh-compose-form').requestSubmit();DigitalHealth.closeFloating()})()")
    until("document.querySelector('.dh-floating-root').dataset.unread==='"+str(unread)+"' && document.querySelector('.dh-reply-status').hidden")
   check(mode+'_unread_three_red_badge',js("(()=>{const b=document.querySelector('.dh-doctor-badge');return b.textContent==='3'&&!b.hidden&&getComputedStyle(b).backgroundColor==='rgb(240, 82, 100)'})()"))
   shot(mode+'-doctor-unread')
   js("document.querySelector('.dh-widget-minimize').click()")
   check(mode+'_compact_unread_retained',js("document.querySelector('.dh-compact-badge').textContent==='3'&&!document.querySelector('.dh-compact-badge').hidden"))
   shot(mode+'-compact-unread')
   js("document.querySelector('.dh-widget-restore').click();document.querySelector('.dh-launcher').click()")
   check(mode+'_reading_clears_badge',js("document.querySelector('.dh-floating-root').dataset.unread==='0'&&document.querySelector('.dh-doctor-badge').hidden"))
  until("document.querySelector('.dh-compose-input').getBoundingClientRect().height>0")
  js("(()=>{const t=document.querySelector('textarea');t.value='帮我分析这份报告';t.dispatchEvent(new Event('input',{bubbles:true}));document.querySelector('.dh-compose-form').requestSubmit();})()")
  until("document.querySelector('.dh-source-details')!==null && document.querySelector('.dh-reply-status').hidden")
  geometry=js("""(()=>{const scope=document.querySelector('.dh-floating-panel')||document.querySelector('.dh-main'),input=scope.querySelector('textarea'),form=scope.querySelector('.dh-compose-form'),scroll=scope.querySelector('.dh-message-scroll'),button=scope.querySelector('.dh-send-button'),svg=button.querySelector('svg');const b=e=>{const r=e.getBoundingClientRect();return {top:r.top,bottom:r.bottom,width:r.width,height:r.height,left:r.left,right:r.right}};return {input:b(input),form:b(form),scroll:b(scroll),button:b(button),svg:b(svg),panel:b(scope),overflow:scope.scrollWidth>scope.clientWidth+1,bodyOverflow:document.documentElement.scrollWidth>innerWidth+1,sourceLinks:scope.querySelectorAll('.dh-source-link').length,scrollBars:[...scope.querySelectorAll('*')].filter(e=>getComputedStyle(e).overflowY==='auto'&&e.scrollHeight>e.clientHeight+2&&e.clientHeight>0).map(e=>e.className),cleared:input.value==='',disabled:button.disabled};})()""")
  check(mode+'_composer_visible',geometry['form']['bottom']<=height+1 and geometry['input']['height']>=42)
  check(mode+'_one_message_scrollbar',geometry['scrollBars']==['dh-message-scroll'])
  check(mode+'_no_overlap',geometry['scroll']['bottom']<=geometry['form']['top']+1)
  check(mode+'_fixed_send_icon',geometry['svg']['width']==20 and geometry['svg']['height']==20)
  check(mode+'_sources_deduplicated_collapsed',geometry['sourceLinks']==2 and not js("document.querySelector('.dh-source-details').open"))
  check(mode+'_no_horizontal_overflow',not geometry['overflow'] and not geometry['bodyOverflow'])
  check(mode+'_input_cleared',geometry['cleared'] and geometry['disabled'])
  shot(mode)
  js("document.querySelector('.dh-source-details').open=true;document.querySelector('.dh-message-scroll').scrollTop=999999")
  shot(mode+'-sources')
  js("(()=>{const t=document.querySelector('textarea');t.value='多行输入\\n第二行\\n第三行\\n第四行\\n第五行';t.dispatchEvent(new Event('input',{bubbles:true}));})()")
  check(mode+'_multiline_bounded',42<=js("document.querySelector('textarea').getBoundingClientRect().height")<=112)
  if mode=='floating':shot(mode+'-multiline')
  if mode.startswith('floating'):
   js("DigitalHealth.closeFloating();document.querySelector('.dh-launcher').click()")
  else:
   js("document.querySelector('[data-session]').click()")
  until("!document.querySelector('.dh-history-gate').hidden")
  time.sleep(.15)
  check(mode+'_starts_with_greeting',js("document.querySelectorAll('.dh-visit-greeting').length===1 && document.querySelector('.dh-previous-visit').hidden && document.querySelector('.dh-history-gate svg')!==null"))
  check(mode+'_greeting_and_composer_visible',js("(()=>{const r=document.querySelector('.dh-visit-greeting').getBoundingClientRect(),s=document.querySelector('.dh-message-scroll').getBoundingClientRect(),f=document.querySelector('.dh-compose-form').getBoundingClientRect();return r.top>=s.top && r.bottom<=s.bottom && f.bottom<=innerHeight+1})()"))
  shot(mode+'-greeting')
  js("document.querySelector('.dh-message-scroll').dispatchEvent(new WheelEvent('wheel',{deltaY:-100,bubbles:true}))")
  check(mode+'_up_scroll_reveals_history',js("!document.querySelector('.dh-previous-visit').hidden && document.querySelector('.dh-history-messages').textContent.length>100"))
  js("document.querySelector('.dh-message-scroll').scrollTop=0")
  shot(mode+'-history')
  check(mode+'_history_shares_scroll_area',js("[...document.querySelectorAll('.dh-history-messages,.dh-previous-visit')].every(n=>!['auto','scroll'].includes(getComputedStyle(n).overflowY))"))
 (OUT/'verification.json').write_text(json.dumps({'checks':checks,'count':len(checks),'realBrowser':'Chrome headless','realModelCalled':False},ensure_ascii=False,indent=2),encoding='utf8')
 print('Screenshots:',OUT,flush=True)
finally:
 if ws:
  try:cdp('Browser.close')
  except Exception:pass
  ws.close()
 if proc.poll() is None:proc.terminate()
 server.shutdown()
