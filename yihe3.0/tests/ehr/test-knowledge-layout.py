"""History UI in real headless Chrome. Local fictional responses, no database/model writes."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid
ROOT=Path(__file__).resolve().parents[2];STATIC=ROOT/'yihe-admin/src/main/resources/static'
OUT=Path('F:/workstation/.tools/runtime/knowledge-ui');OUT.mkdir(parents=True,exist_ok=True)
STUB='const SEED='+ (ROOT/'yihe-framework/src/main/resources/ehr/service-knowledge-seed.json').read_text(encoding='utf8')+';'+(ROOT/'tests/ehr/knowledge-fixture.js').read_text(encoding='utf8')
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*a,**kw):super().__init__(*a,directory=str(STATIC),**kw)
 def log_message(self,*a):pass
 def do_GET(self):
  if not self.path.startswith('/fixture'):return super().do_GET()
  h=(ROOT/'yihe-admin/src/main/resources/templates/communication/knowledge.html').read_text(encoding='utf8')
  for kind,name in [('href','css/communication.css'),('href','css/service-knowledge.css'),('src','js/service-knowledge.js')]:h=h.replace(f'th:{kind}="@{{/assets/{name}(v=\'20261125-1\')}}"',f'{kind}="/assets/{name}"')
  h=h.replace('th:attr="data-base=@{/}"','data-base="/"').replace('<script defer','<script>'+STUB+'</script><script defer');data=h.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.end_headers();self.wfile.write(data)
server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start();port=server.server_address[1];debug=19257
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
 for label,w,h in [('desktop',1650,1000),('laptop',1200,850),('tablet',820,900),('mobile',390,840)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':w,'height':h,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':f'http://localhost:{port}/fixture'})
  until("document.querySelectorAll('#documentRows [data-view]').length===10")
  check(label+'_no_page_overflow',js('document.documentElement.scrollWidth<=innerWidth+1'))
  check(label+'_seven_categories',js("document.querySelectorAll('#categoryStats button').length===7"))
  check(label+'_390_documents',js("document.getElementById('totalLabel').textContent.includes('390')"))
  check(label+'_honest_usage',js("document.getElementById('popular').textContent.includes('真实服务引用')"))
  shot(label)
 cdp('Emulation.setDeviceMetricsOverride',{'width':1650,'height':1000,'deviceScaleFactor':1,'mobile':False})
 js("document.getElementById('next').click()")
 until("document.getElementById('pageLabel').textContent.startsWith('2')")
 check('pagination_connected',True)
 js("document.querySelector('[data-category=cat3]').click()")
 until("document.getElementById('totalLabel').textContent.includes('128')")
 check('category_query_connected',True)
 js("document.getElementById('resetSearch').click();document.getElementById('search').value='不存在';document.getElementById('searchForm').requestSubmit()")
 until("document.getElementById('documentRows').textContent.includes('暂无匹配')")
 check('keyword_search',True)
 js("document.getElementById('resetSearch').click()")
 until("document.querySelector('[data-view]')!==null")
 js("document.querySelector('#documentRows [data-view]').click()")
 until("document.getElementById('readerDialog').open")
 check('full_content_reader',js("document.getElementById('readerBody').textContent.length>180"))
 check('sources_displayed',js("document.querySelectorAll('#readerSources a').length>0"))
 js("document.getElementById('readerDialog').close();document.querySelector('#documentRows [data-edit]').click()")
 until("document.getElementById('editorDialog').open")
 check('editor_content_loaded',js("document.getElementById('docBody').value.length>180"))
 js("document.getElementById('docTitle').value='界面修改测试';document.getElementById('documentForm').requestSubmit()")
 until("!document.getElementById('editorDialog').open")
 check('save_api_connected',js("calls.some(c=>c.method==='PUT'&&c.path.includes('/documents/'))"))
 js("document.getElementById('newDocument').click()")
 until("document.getElementById('editorDialog').open")
 js("document.getElementById('docTitle').value='独立界面新增测试';document.getElementById('docBody').value='这是用于界面测试的独立知识正文，超过三十字以满足校验，不是个人医疗建议，也不会写入真实数据库。';document.querySelector('[data-source=name]').value='本地界面测试';document.getElementById('documentForm').requestSubmit()")
 until("!document.getElementById('editorDialog').open")
 check('new_document_connected',js("calls.some(c=>c.method==='POST'&&c.path.endsWith('/documents'))"))
 js("document.querySelector('[data-tab=categories]').click()")
 check('category_management',js("document.querySelectorAll('.kb-category-row').length===7"))
 js("document.querySelector('[data-tab=retrieval]').click()")
 until("document.querySelectorAll('[data-retrieval-category]').length===7")
 js("document.getElementById('topK').value=3;document.getElementById('settingsForm').requestSubmit()")
 until("calls.some(c=>c.path.endsWith('/settings')&&c.method==='PUT')")
 check('retrieval_configuration_connected',True)
 js("document.getElementById('retrievalQuestion').value='粉尘防护';document.getElementById('retrievalTestForm').requestSubmit()")
 until("document.querySelectorAll('.kb-retrieval-card').length>0")
 check('retrieval_test_provenance',js("document.getElementById('retrievalResults').textContent.includes('引用版本')"))
 js("document.querySelector('[data-tab=maintenance]').click()")
 until("document.querySelectorAll('.kb-maintenance-row').length>0")
 check('maintenance_issues',js("document.getElementById('maintenanceStats').textContent.includes('78')"))
 js("document.querySelector('[data-tab=documents]').click();document.getElementById('import').click();document.getElementById('importJson').value=JSON.stringify({documents:[SEED.documents[0]]});document.getElementById('confirmImport').click()")
 until("!document.getElementById('importDialog').open")
 check('import_connected',js("calls.some(c=>c.path.endsWith('/import'))"))
 js("document.querySelector('[data-export=json]').click()")
 until("calls.some(c=>c.path.endsWith('/export'))")
 check('export_connected',True)
 shot('verified-desktop')
 print('PASS knowledge UI checks:',len(checks))
finally:
 if ws:ws.close()
 proc.terminate();server.shutdown()
