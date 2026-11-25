"""Real headless Chrome layout/interactions; local synthetic transport, no business writes/model calls."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid,re

ROOT=Path(__file__).resolve().parents[2]
STATIC=ROOT/'yihe-admin/src/main/resources/static'
OUT=Path('F:/workstation/.tools/runtime/communication-ui');OUT.mkdir(parents=True,exist_ok=True)
STUB=r'''
window.fixtureCalls=[];window.failNextSend=false;window.offline=false;window.alerts=[];
window.confirm=()=>true;window.alert=x=>alerts.push(x);
const cid='00000000-0000-4000-8000-000000000001',rid='00000000-0000-4000-8000-000000000002';
let c={id:cid,recordId:rid,name:'界面测试职工',code:'SYNTHETIC001',status:'open',mine:false,assignee:null,unread:2,awaitingReply:true,version:1,lastSeq:4,preview:'请问我的体检报告在哪里查看？',updatedAt:'2026-11-25 01:30:00.000000'};
let messages=[{id:'m1',seq:1,role:'employee',sender:'wx:test',text:'请问我的体检报告在哪里查看？\n这是合成界面测试数据。',attachments:[],createdAt:c.updatedAt},{id:'m2',seq:2,role:'doctor',sender:'doctor',senderName:'测试医生',text:'您好，可以在小程序“我的体检报告”中查看。若有疑问，我们会帮助您核对。',attachments:[],createdAt:c.updatedAt},{id:'m3',seq:3,role:'employee',sender:'wx:test',text:'<img src=x onerror="window.xss=true">\n'+('较长的咨询内容，用于验证消息窗口滚动和输入区固定。'.repeat(24)),attachments:[],createdAt:c.updatedAt},{id:'m4',seq:4,role:'employee',sender:'wx:test',text:'还有一份附件',attachments:[{id:'file1',name:'示例报告_这是一个很长的文件名称.pdf',mime:'application/pdf',size:2097152}],createdAt:c.updatedAt}];
window.fetch=async function(url,options={}){
 const b=options.body instanceof FormData?{}:options.body?JSON.parse(options.body):{};fixtureCalls.push({url,method:options.method||'GET',body:b});
 if(window.offline)throw Error('网络断开');
 let data={},status=200,msg='';const u=new URL(url,location.origin),path=u.pathname;
 if(path==='/ehr/api/context')data={csrfToken:'fixture-csrf'};
 else if(path.endsWith('/context'))data={user:'doctor',name:'测试医生',counts:{total:3,pending:1,unread:c.unread},ai:{configured:true}};
 else if(path==='/ehr/api/records')data={items:[{id:rid,name:c.name,code:c.code,team:'测试班组'}]};
 else if(path.endsWith('/conversations'))data=options.method==='POST'?c:{items:[c,...[2,3].map(n=>({...c,id:'00000000-0000-4000-8000-00000000000'+(n+2),name:'测试同事'+n,unread:0,preview:'尚无消息'}))],total:3,page:1,limit:30};
 else if(path.endsWith('/actions')){c={...c,mine:b.action==='takeover',assignee:b.action==='takeover'?'doctor':null,status:b.action==='close'?'closed':'open',version:c.version+1};data=c;}
 else if(path.endsWith('/messages')){
  if(options.method==='POST'){
   if(window.failNextSend){window.failNextSend=false;status=503;msg='发送失败，请重试。';}
   else {const m={id:'m'+(messages.length+1),seq:++c.lastSeq,role:b.draftId?'ai_assisted':'doctor',sender:'doctor',senderName:'测试医生',text:b.text,attachments:[],createdAt:c.updatedAt};messages.push(m);c.awaitingReply=false;data=m;}
  }else {const after=Number(u.searchParams.get('after')||0),before=Number(u.searchParams.get('before')||0);data={conversation:c,items:messages.filter(m=>m.seq>after&&(!before||m.seq<before)),lastReturnedSeq:c.lastSeq,hasEarlier:false,hasMore:false};}
 }else if(path.endsWith('/read')){c.unread=0;data={readThrough:b.seq};}
 else if(path.endsWith('/health'))data={profile:{id:rid,name:c.name,code:c.code,sex:'男',age:42,team:'测试班组',job:'测试岗位',risk:'unknown',complete:66.7,exposure:'暂无接触史记录'},latestReport:{id:'report',date:'2026-11-20',status:'unreviewed',institution:'合成测试机构'},indicators:[{name:'血压',value:'135/88',unit:'mmHg',flag:'',referenceRange:''},{name:'血糖',value:'5.2',unit:'mmol/L',flag:'',referenceRange:''}],assessments:[{id:'r1',type:'ai_assisted',status:'draft',level:'high',time:c.updatedAt}],followups:[],serviceEvents:[]};
 else if(path.endsWith('/summary'))data={messages:messages.length,employeeMessages:3,staffMessages:messages.length-3,firstAt:c.updatedAt,latestAt:c.updatedAt};
 else if(path.endsWith('/drafts')){await new Promise(r=>setTimeout(r,100));data={id:'00000000-0000-4000-8000-000000000009',text:'合成测试回复草稿，请核对后发送。',safetyNote:'AI辅助草稿，请人工核对。',basedOnSeq:c.lastSeq};}
 else if(path.endsWith('/attachments'))data={id:'00000000-0000-4000-8000-000000000008',name:'合成附件.txt',mime:'text/plain',size:12};
 return {ok:status===200,status,json:async()=>({code:status===200?0:status,data,msg})};
};
'''
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*args,**kwargs):super().__init__(*args,directory=str(STATIC),**kwargs)
 def log_message(self,*args):pass
 def do_GET(self):
  if not self.path.startswith('/fixture'):return super().do_GET()
  html=(ROOT/'yihe-admin/src/main/resources/templates/communication/conversations.html').read_text(encoding='utf8')
  html=html.replace("th:href=\"@{/assets/css/communication.css(v='20261125-1')}\"",'href="/assets/css/communication.css"').replace('th:attr="data-base=@{/}"','data-base="/"').replace("th:src=\"@{/assets/js/communication.js(v='20261125-1')}\"",'src="/assets/js/communication.js"')
  html=html.replace('<script defer','<script>'+STUB+'</script><script defer')
  data=html.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.send_header('Content-Length',len(data));self.end_headers();self.wfile.write(data)

server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start();port=server.server_address[1];debug=19249
profile=OUT/('chrome-'+uuid.uuid4().hex)
proc=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--no-default-browser-check','--remote-debugging-address=127.0.0.1','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(profile),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
ws=None;counter=0;checks=[];http=requests.Session();http.trust_env=False
def cdp(method,params=None):
 global counter
 counter+=1;ident=counter;ws.send(json.dumps({'id':ident,'method':method,'params':params or {}}))
 while True:
  r=json.loads(ws.recv())
  if r.get('id')==ident:
   assert 'error' not in r,r
   return r.get('result',{})
def js(expression):
 r=cdp('Runtime.evaluate',{'expression':expression,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r
 return r['result'].get('value')
def until(expression):
 for _ in range(100):
  if js(expression):return
  time.sleep(.1)
 raise AssertionError('UI timeout: '+expression)
def check(name,ok):
 assert ok,name
 checks.append(name);print('PASS',name,flush=True)
def shot(name):
 (OUT/(name+'.png')).write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
try:
 for _ in range(80):
  try:tabs=http.get(f'http://localhost:{debug}/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 tab=next(x for x in tabs if x['type']=='page');ws=websocket.create_connection(tab['webSocketDebuggerUrl'],origin=f'http://localhost:{debug}',timeout=20);cdp('Page.enable')
 for label,width,height in [('desktop',1510,900),('laptop',1200,800),('tablet',820,900),('mobile',390,840)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':width,'height':height,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':f'http://localhost:{port}/fixture'})
  until("document.querySelectorAll('[data-conversation]').length===3")
  check(label+'_empty_state',js("!document.getElementById('chatEmpty').hidden"));js("document.querySelector('[data-conversation]').click()")
  until("document.querySelectorAll('[data-message]').length===4 && document.getElementById('healthContent').textContent.includes('未评估')")
  check(label+'_takeover_required',js("document.getElementById('messageInput').disabled"))
  check(label+'_message_xss_escaped',js("!window.xss && document.querySelector('.bubble').parentElement.parentElement.parentElement.textContent.includes('<img src=x')"))
  js("document.getElementById('takeoverBtn').click()")
  until("!document.getElementById('messageInput').disabled")
  check(label+'_no_horizontal_overflow',js("document.documentElement.scrollWidth<=innerWidth+1"))
  if width>680:check(label+'_composer_inside_viewport',js("(()=>{let r=document.querySelector('.composer').getBoundingClientRect();return r.bottom<=innerHeight && r.top>80})()"))
  if width<=1060:
   js("document.getElementById('profileToggle').click()")
   check(label+'_health_drawer_opens',js("getComputedStyle(document.getElementById('healthPanel')).display!=='none'"));js("document.getElementById('closeProfile').click()")
  check(label+'_pending_risk_not_confirmed',js("document.getElementById('healthContent').textContent.includes('未确认') && !document.getElementById('healthContent').textContent.includes('高风险')"))
  shot(label+'-conversation')
  js("window.failNextSend=true;document.getElementById('messageInput').value='发送失败需保留的合成消息';document.getElementById('messageInput').dispatchEvent(new Event('input'));document.getElementById('sendForm').requestSubmit()")
  until("document.getElementById('toast').textContent.includes('发送失败')")
  check(label+'_failed_send_preserves_text',js("document.getElementById('messageInput').value==='发送失败需保留的合成消息'"))
  js("document.getElementById('sendForm').requestSubmit()")
  until("document.getElementById('messageInput').value==='' && document.querySelectorAll('[data-message]').length===5")
  check(label+'_retry_keeps_request_id',js("(()=>{let c=fixtureCalls.filter(c=>c.method==='POST'&&c.url.endsWith('/messages'));return c.length===2&&c[0].body.requestId===c[1].body.requestId&&c.every(x=>x.body.text==='发送失败需保留的合成消息')})()"))
  js("document.querySelector('[data-draft=general]').click()")
  until("document.getElementById('messageInput').value.includes('合成测试回复草稿')")
  check(label+'_ai_draft_not_auto_sent',js("fixtureCalls.filter(c=>c.method==='POST'&&c.url.endsWith('/messages')).length===2 && !document.getElementById('draftNote').hidden"))
  js("document.getElementById('messageInput').dispatchEvent(new KeyboardEvent('keydown',{key:'Enter',bubbles:true}))")
  until("document.querySelectorAll('.ai-label').length===1")
  check(label+'_confirmed_ai_reply_labeled',True)
  js("document.querySelector('[data-health=summary]').click()")
  until("document.getElementById('healthContent').textContent.includes('会话概况')")
  check(label+'_summary_has_real_counts',js("document.getElementById('healthContent').textContent.includes('6 条')===false && document.getElementById('healthContent').textContent.includes('职工消息')"))
  js("document.querySelector('[data-health=archive]').click();document.getElementById('newConversation').click()")
  until("document.getElementById('newDialog').open && document.querySelector('[data-record]')!==null")
  check(label+'_employee_picker',js("document.getElementById('employeeResults').textContent.includes('SYNTHETIC001')"))
  js("document.getElementById('newDialog').close()")
 print(json.dumps({'count':len(checks),'externalModelCalls':False,'businessDatabaseWrites':False,'screenshots':str(OUT)},ensure_ascii=False))
finally:
 if ws:
  try:cdp('Browser.close')
  except Exception:pass
  ws.close()
 if proc.poll() is None:proc.terminate()
 server.shutdown()
