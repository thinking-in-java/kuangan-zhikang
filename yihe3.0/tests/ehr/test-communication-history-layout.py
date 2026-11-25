"""History UI in real headless Chrome. Local fictional responses, no database/model writes."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid
ROOT=Path(__file__).resolve().parents[2];STATIC=ROOT/'yihe-admin/src/main/resources/static'
OUT=Path('F:/workstation/.tools/runtime/communication-history-ui');OUT.mkdir(parents=True,exist_ok=True)
STUB=r'''
window.calls=[];window.modelFailure=false;
const CID='00000000-0000-4000-8000-000000000001',RID='00000000-0000-4000-8000-000000000002';
let category='report';let row={id:CID,recordId:RID,name:'合成界面职工',code:'SYN001',status:'closed',category:'report',score:null,messageCount:61,aiReplies:1,humanReplies:3,takeovers:1,firstAt:'2026-11-24 01:00:00',latestAt:'2026-11-25 02:00:00',preview:'我想查看体检报告，如何预约复查？'};
let msgs=Array.from({length:61},(_,i)=>({id:'m'+(i+1),seq:i+1,role:i%4===0?'employee':i%4===1?'ai_assisted':i%4===2?'doctor':'system',senderName:i%4===2?'合成医生':null,text:i%4===3?'合成医生已接管会话。':(i===60?'<img src=x onerror="window.xss=true">\n':'')+'体检报告与复查沟通，仅用于界面测试。'.repeat(3),createdAt:'2026-11-25 02:00:00',attachments:i===58?[{id:'file1',name:'合成测试附件.pdf',mime:'application/pdf',size:10}]:[]}));
window.fetch=async function(url,options={}){let u=new URL(url,location.origin),path=u.pathname,b=options.body?JSON.parse(options.body):{};calls.push({path,query:Object.fromEntries(u.searchParams),method:options.method||'GET',body:b});let data={};
 if(path==='/ehr/api/context')data={csrfToken:'test'};
 else if(path.endsWith('/history/export'))return new Response('\uFEFF姓名,消息\r\n合成职工,合成消息',{status:200,headers:{'Content-Type':'text/csv'}});
 else if(path.endsWith('/history/statistics'))data={conversations:3,aiReplies:12,humanReplies:19,transferred:2,averageSeconds:138,responseSamples:4,satisfaction:null,ratedConversations:0,aiConversations:2,humanConversations:3};
 else if(path.endsWith('/history'))data={items:u.searchParams.get('search')==='不存在'?[]:[row,{...row,id:'00000000-0000-4000-8000-000000000003',name:'合成同事二',code:'SYN002',aiReplies:0}],total:u.searchParams.get('search')==='不存在'?0:2,page:1,limit:10};
 else if(path.endsWith('/detail'))data={conversation:{...row,recordId:RID,awaitingReply:false},profile:{name:row.name,code:row.code,team:'合成班组',job:'测试岗位'},category,score:null,summary:{messages:61,employeeMessages:16,staffMessages:30,firstAt:row.firstAt,latestAt:row.latestAt},participants:[{name:row.name,role:'employee'},{name:'合成医生',role:'doctor'},{name:'合成医生',role:'ai_assisted'}],keywords:['体检报告','复查']};
 else if(path.endsWith('/messages')){let before=Number(u.searchParams.get('before')||0);data={items:before?msgs.filter(m=>m.seq<before):msgs.slice(-50),hasEarlier:!before};}
 else if(path.endsWith('/ai-summary')){if(window.modelFailure)return new Response(JSON.stringify({code:503,msg:'尚未配置模型'}),{status:503});data={text:'这是合成会话摘要，职工咨询报告查看及复查流程。',messageCount:46,note:'以原始记录为准',generatedAt:row.latestAt};}
 else if(path.endsWith('/category')){category=b.category;data={category};}
 else if(path.endsWith('/plans'))data={items:[],note:'暂无已关联健康计划'};
 else if(path.endsWith('/health'))data={followups:[{note:'合成复查随访',status:'pending',due:row.latestAt}]};
 return new Response(JSON.stringify({code:0,data}),{status:200,headers:{'Content-Type':'application/json'}});
};
'''
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*a,**kw):super().__init__(*a,directory=str(STATIC),**kw)
 def log_message(self,*a):pass
 def do_GET(self):
  if not self.path.startswith('/fixture'):return super().do_GET()
  h=(ROOT/'yihe-admin/src/main/resources/templates/communication/history.html').read_text(encoding='utf8')
  for kind,name in [('href','css/communication.css'),('href','css/communication-history.css'),('src','js/communication-history.js')]:h=h.replace(f'th:{kind}="@{{/assets/{name}(v=\'20261125-1\')}}"',f'{kind}="/assets/{name}"')
  h=h.replace("th:src=\"@{/assets/js/communication-history.js(v='20261125-2')}\"", 'src="/assets/js/communication-history.js"')
  h=h.replace('th:attr="data-base=@{/}"','data-base="/"').replace('<script defer','<script>'+STUB+'</script><script defer');data=h.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.end_headers();self.wfile.write(data)
server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start();port=server.server_address[1];debug=19251
proc=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--remote-debugging-port='+str(debug),'--remote-allow-origins=http://localhost:'+str(debug),'--user-data-dir='+str(OUT/('chrome-'+uuid.uuid4().hex)),'about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
ws=None;counter=0;checks=[];http=requests.Session();http.trust_env=False
def cdp(method,params=None):
 global counter
 counter+=1;ident=counter;ws.send(json.dumps({'id':ident,'method':method,'params':params or {}}))
 while True:
  r=json.loads(ws.recv())
  if r.get('id')==ident:
   assert 'error' not in r,r
   return r.get('result',{})
def js(e):
 r=cdp('Runtime.evaluate',{'expression':e,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r
 return r['result'].get('value')
def until(e):
 for _ in range(100):
  if js(e):return
  time.sleep(.1)
 raise AssertionError(e)
def check(n,b):
 assert b,n
 checks.append(n);print('PASS',n,flush=True)
def shot(n):(OUT/(n+'.png')).write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
try:
 for _ in range(80):
  try:tabs=http.get(f'http://localhost:{debug}/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 ws=websocket.create_connection(next(t for t in tabs if t['type']=='page')['webSocketDebuggerUrl'],origin=f'http://localhost:{debug}',timeout=20);cdp('Page.enable');cdp('Browser.setDownloadBehavior',{'behavior':'allow','downloadPath':str(OUT)})
 for label,w,h in [('desktop',1510,940),('laptop',1200,850),('tablet',820,900),('mobile',390,840)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':w,'height':h,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':f'http://localhost:{port}/fixture'})
  until("document.querySelectorAll('#historyMessages .message').length>20 && document.getElementById('info').textContent.includes('会话编号')")
  check(label+'_no_overflow',js('document.documentElement.scrollWidth<=innerWidth+1'))
  if w>1100:check(label+'_desktop_fits_viewport',js("document.querySelector('.history-body').getBoundingClientRect().bottom<=innerHeight"))
  check(label+'_statistics_six_cards',js("document.querySelectorAll('.stat-card').length===6 && document.getElementById('statistics').textContent.includes('2分18秒')"))
  check(label+'_no_fabricated_satisfaction',js("document.getElementById('info').textContent.includes('暂无职工评价')"))
  check(label+'_xss_escaped',js("!window.xss && document.getElementById('historyMessages').textContent.includes('<img src=x')"))
  check(label+'_attachments_linked',js("document.querySelector('.history-attachment').href.includes('/attachments/file1')"))
  shot(label)
  js("document.getElementById('earlier').click()")
  until("document.getElementById('messageInfo').textContent.includes('61 条')")
  check(label+'_earlier_messages_loaded',js("document.getElementById('earlier').hidden"))
  js("document.getElementById('summarize').click()")
  until("document.getElementById('summary').textContent.includes('合成会话摘要')")
  check(label+'_summary_has_provenance',js("document.getElementById('summaryNote').textContent.includes('46 条消息') && !calls.some(c=>c.path.endsWith('/messages')&&c.method==='POST')"))
  js("window.modelFailure=true;document.getElementById('summarize').click()")
  until("document.getElementById('summary').textContent.includes('尚未配置模型')")
  check(label+'_model_failure_preserves_history',js("document.querySelectorAll('#historyMessages .message').length>20"));js("window.modelFailure=false")
  js("document.getElementById('detailBtn').click()")
  check(label+'_detail_dialog',js("document.getElementById('detailDialog').open"))
  js("document.getElementById('editCategory').value='review';document.getElementById('saveCategory').click()")
  until("!document.getElementById('detailDialog').open && document.getElementById('info').textContent.includes('复查咨询')")
  check(label+'_category_saved',js("calls.some(c=>c.path.endsWith('/category')&&c.body.category==='review')"))
  js("document.getElementById('followup').click()")
  until("document.getElementById('detailContent').textContent.includes('合成复查随访')")
  check(label+'_linked_followups',True);js("document.getElementById('detailDialog').close();document.getElementById('plans').click()")
  until("document.getElementById('detailContent').textContent.includes('暂无已关联健康方案')")
  check(label+'_linked_plans_empty_state',True);js("document.getElementById('detailDialog').close();document.getElementById('exportAll').click()")
  until("calls.some(c=>c.path.endsWith('/history/export'))")
  check(label+'_export_request',True)
  js("document.getElementById('search').value='不存在';document.getElementById('start').value='2026-11-01';document.getElementById('end').value='2026-11-25';document.getElementById('filters').requestSubmit()")
  until("document.getElementById('historyList').textContent.includes('没有符合条件')")
  check(label+'_filter_params_sent',js("calls.some(c=>c.path.endsWith('/history')&&c.query.start==='2026-11-01'&&c.query.end==='2026-11-25'&&c.query.search==='不存在')"))
  check(label+'_empty_clears_detail',js("document.getElementById('detailBtn').disabled && document.getElementById('info').textContent.includes('尚未选择')"))
 print(json.dumps({'checks':len(checks),'databaseWrites':False,'externalModelCalls':False,'screenshots':str(OUT)}))
finally:
 if ws:
  try:cdp('Browser.close')
  except Exception:pass
  ws.close()
 if proc.poll() is None:proc.terminate()
 server.shutdown()

