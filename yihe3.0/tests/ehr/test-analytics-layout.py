"""Real Chrome, isolated UI fixture, no database or model calls."""
from pathlib import Path
from http.server import ThreadingHTTPServer,SimpleHTTPRequestHandler
from threading import Thread
import subprocess,requests,websocket,json,time,base64,uuid,re
ROOT=Path(__file__).resolve().parents[2];STATIC=ROOT/'yihe-admin/src/main/resources/static';OUT=Path('F:/workstation/.tools/runtime/analytics-ui');OUT.mkdir(parents=True,exist_ok=True)
class Handler(SimpleHTTPRequestHandler):
 def __init__(self,*a,**kw):super().__init__(*a,directory=str(STATIC),**kw)
 def log_message(self,*a):pass
 def do_GET(self):
  if not self.path.startswith('/fixture'):return super().do_GET()
  h=(ROOT/'yihe-admin/src/main/resources/templates/communication/analytics.html').read_text('utf8');h=re.sub(r'th:(href|src)="@\{([^()]+)\(v=\x27[^\x27]+\x27\)\}"',r'\1="\2"',h).replace('th:attr="data-base=@{/}"','data-base="/"').replace('<script defer','<script>'+(ROOT/'tests/ehr/analytics-fixture.js').read_text('utf8')+'</script><script defer');data=h.encode();self.send_response(200);self.send_header('Content-Type','text/html; charset=utf-8');self.end_headers();self.wfile.write(data)
server=ThreadingHTTPServer(('127.0.0.1',0),Handler);Thread(target=server.serve_forever,daemon=True).start();port=server.server_address[1];debug=19259
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
def shot(name):
 (OUT/(name+'.png')).write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':True})['data']))
try:
 for _ in range(80):
  try:tabs=http.get(f'http://localhost:{debug}/json',timeout=1).json();break
  except requests.RequestException:time.sleep(.1)
 ws=websocket.create_connection(next(t for t in tabs if t['type']=='page')['webSocketDebuggerUrl'],origin=f'http://localhost:{debug}',timeout=20);cdp('Page.enable');cdp('Browser.setDownloadBehavior',{'behavior':'allow','downloadPath':str(OUT)})
 for label,w,h in [('desktop',1680,1050),('laptop',1200,900),('tablet',820,950),('mobile',390,840)]:
  cdp('Emulation.setDeviceMetricsOverride',{'width':w,'height':h,'deviceScaleFactor':1,'mobile':False});cdp('Page.navigate',{'url':f'http://localhost:{port}/fixture'});until("document.querySelectorAll('#metrics .metric').length===7&&document.querySelectorAll('#rows tr').length===20")
  check(label+'_no_outer_overflow',js('document.documentElement.scrollWidth<=innerWidth+1'));check(label+'_charts',js("document.querySelectorAll('.chart svg').length===5"));check(label+'_truthful_accuracy',js("document.getElementById('quality').textContent.includes('未采集')"));check(label+'_xss_escaped',js("document.getElementById('rows').textContent.includes('<script>')&&!document.querySelector('#rows script')"));shot(label)
 cdp('Emulation.setDeviceMetricsOverride',{'width':1680,'height':1050,'deviceScaleFactor':1,'mobile':False})
 js("document.getElementById('next').click()");until("document.getElementById('pageNumber').textContent==='2'");check('pagination_api_connected',js("calls.at(-1).includes('page=2')"));js("document.getElementById('pageSize').value='50';document.getElementById('pageSize').dispatchEvent(new Event('change'))");until("document.querySelectorAll('#rows tr').length===42");check('page_size',True)
 js("document.getElementById('range').value='7';document.getElementById('range').dispatchEvent(new Event('change'));document.getElementById('category').value='report';document.getElementById('human').value='yes';document.getElementById('filters').requestSubmit()");until("calls.at(-1).includes('category=report')");check('all_filters_connected',js("calls.at(-1).includes('human=yes')&&calls.at(-1).includes('limit=50')"));check('history_drillthrough',js("document.querySelector('#rows a').getAttribute('href').includes('conversationId=test-')&&document.querySelector('#rows a').getAttribute('href').includes('category=report')"))
 js("document.getElementById('definitions').click()");check('definitions_open',js("document.getElementById('definitionDialog').open"));js("document.getElementById('definitionDialog').close();document.getElementById('export').click()");until("calls.some(x=>x.includes('/export?'))");check('export_connected',True)
 js("document.getElementById('unit').value='无数据矿';document.getElementById('filters').requestSubmit()");until("document.getElementById('detailTotal').textContent.includes('0')");check('empty_state',js("document.getElementById('trend').textContent.includes('暂无')"));check('null_rating_not_zero',js("document.getElementById('satisfaction').textContent.includes('暂无职工评价')"));shot('empty')
 js("document.getElementById('reset').click()");until("document.getElementById('detailTotal').textContent.includes('42')");check('reset_filters',js("document.getElementById('human').value===''&&document.getElementById('unit').value===' '".replace("===' '","===''") ));
 js("document.getElementById('start').value='2020-01-01';document.getElementById('filters').requestSubmit()");check('date_range_guard',js("!document.getElementById('error').hidden"))
 js("document.getElementById('reset').click()");until("document.getElementById('error').hidden");js("window.fetch=async()=>new Response(JSON.stringify({msg:'服务暂不可用'}),{status:503});document.getElementById('filters').requestSubmit()");until("!document.getElementById('error').hidden");check('error_clears_old_metrics',js("!document.querySelector('#metrics .metric')&&document.getElementById('export').disabled&&document.getElementById('detailTotal').textContent==='—'"));shot('error')
 print('ANALYTICS UI CHECKS',len(checks),'PASSED');(OUT/'checks.json').write_text(json.dumps(checks),encoding='utf8')
finally:
 if ws:ws.close()
 proc.terminate();server.shutdown()
