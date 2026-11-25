"""History UI in real headless Chrome. Local fictional responses, no database/model writes."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid
ROOT=Path(__file__).resolve().parents[2];STATIC=ROOT/'yihe-admin/src/main/resources/static'
OUT=Path('F:/workstation/.tools/runtime/auto-reply-ui');OUT.mkdir(parents=True,exist_ok=True)
STUB='const SEED='+ (ROOT/'yihe-framework/src/main/resources/ehr/auto-reply-seed.json').read_text(encoding='utf8')+';'+(ROOT/'tests/ehr/auto-reply-fixture.js').read_text(encoding='utf8')
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*a,**kw):super().__init__(*a,directory=str(STATIC),**kw)
 def log_message(self,*a):pass
 def do_GET(self):
  if not self.path.startswith('/fixture'):return super().do_GET()
  h=(ROOT/'yihe-admin/src/main/resources/templates/communication/auto-replies.html').read_text(encoding='utf8')
  for kind,name in [('href','css/communication.css'),('href','css/auto-replies.css'),('src','js/auto-replies.js')]:h=h.replace(f'th:{kind}="@{{/assets/{name}(v=\'20261125-1\')}}"',f'{kind}="/assets/{name}"')
  h=h.replace('th:attr="data-base=@{/}"','data-base="/"').replace('<script defer','<script>'+STUB+'</script><script defer');data=h.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.end_headers();self.wfile.write(data)
server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start();port=server.server_address[1];debug=19253
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
 for label,w,h in [('desktop',1550,1000),('laptop',1200,850),('tablet',820,900),('mobile',390,840)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':w,'height':h,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':f'http://localhost:{port}/fixture'})
  until("document.querySelectorAll('#ruleRows tr').length===10")
  check(label+'_no_page_overflow',js('document.documentElement.scrollWidth<=innerWidth+1'))
  check(label+'_editor_visible',js("getComputedStyle(document.querySelector('.ar-editor')).display!=='none'"))
  check(label+'_24_defaults',js("document.getElementById('pageLabel').textContent.includes('24')"))
  check(label+'_empty_statistics_honest',js("document.getElementById('stats').textContent.includes('—')"))
  shot(label)
 cdp('Emulation.setDeviceMetricsOverride',{'width':1550,'height':1000,'deviceScaleFactor':1,'mobile':False})
 js("document.getElementById('next').click()")
 check('pagination',js("document.getElementById('pageLabel').textContent.includes('2 / 3')"))
 js("document.querySelector('[data-category=miniapp]').click()")
 check('category_filter',js("document.querySelectorAll('#ruleRows tr').length<10"))
 js("document.getElementById('reset').click();document.getElementById('search').value='不存在';document.getElementById('filterForm').requestSubmit()")
 check('search_empty',js("document.getElementById('ruleRows').textContent.includes('暂无匹配')"))
 js("document.getElementById('reset').click();document.querySelector('[data-edit]').click()")
 check('edit_fills_form',js("document.getElementById('ruleTitle').value.length>0&&document.querySelectorAll('#keywordTags span').length>0"))
 js("document.querySelector('[data-variable=employee_name]').click()")
 check('variable_insertion',js("document.getElementById('ruleReply').value.includes('{{employee_name}}')"))
 js("document.getElementById('ruleForm').requestSubmit()")
 until("calls.some(c=>c.method==='PUT'&&c.path.includes('/rules/'))")
 check('edit_api_connected',True)
 js("document.querySelector('[data-copy]').click()")
 until("document.getElementById('pageLabel').textContent.includes('25')")
 check('copy_api_connected',True)
 js("document.getElementById('testQuestion').value='胸痛';document.getElementById('testForm').requestSubmit()")
 until("document.getElementById('testResult').textContent.includes('紧急提醒')")
 check('boundary_test_connected',js("calls.find(c=>c.path.endsWith('/test')).body.generate===false"))
 js("document.getElementById('testHandoff').click()")
 check('dryrun_handoff_explicit',js("document.getElementById('testResult').textContent.includes('未创建真实会话')"))
 js("document.getElementById('publish').click()")
 until("document.getElementById('releaseStatus').textContent.includes('V1')")
 check('publish_api_connected',True)
 js("document.getElementById('versions').click()")
 until("document.querySelectorAll('[data-rollback]').length===1")
 js("document.querySelector('[data-rollback]').click()")
 until("document.getElementById('releaseStatus').textContent.includes('V2')")
 check('rollback_api_connected',True)
 js("document.getElementById('editKnowledge').click()")
 check('knowledge_six_editors',js("document.querySelectorAll('[data-ki]').length===6"))
 js("document.getElementById('saveKnowledge').click()")
 until("!document.getElementById('knowledgeDialog').open")
 check('knowledge_settings_connected',js("calls.some(c=>c.path.endsWith('/settings')&&c.method==='PUT')"))
 js("document.getElementById('import').click();document.getElementById('importJson').value=JSON.stringify({rules:[SEED.rules[0]]});document.getElementById('confirmImport').click()")
 until("document.getElementById('pageLabel').textContent.includes('26')")
 check('import_connected',True)
 js("document.getElementById('export').click()")
 check('export_invoked',True)
 check('csrf_on_mutations',js("calls.filter(c=>c.method!=='GET').length>5"))
 shot('verified-desktop')
 print('PASS auto reply UI checks:',len(checks))
finally:
 if ws:ws.close()
 proc.terminate();server.shutdown()
