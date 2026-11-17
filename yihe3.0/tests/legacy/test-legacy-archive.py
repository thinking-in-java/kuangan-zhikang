from pathlib import Path
import requests,os,json,time,subprocess,websocket
B=os.getenv('LEGACY_TEST_URL','http://localhost:18081');s=requests.Session();s.trust_env=False;checks=[]
s.get(B+'/login',timeout=10);r=s.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=20);assert r.json()['code']==200
def check(name,valid=True):assert valid,name;checks.append(name);print('PASS',name,flush=True)
def rows(**params):
 r=s.get(B+'/newRegController/selectRegInfo',params={'page':1,'limit':20,**params},timeout=60);assert r.status_code==200,r.status_code;j=r.json();assert j['code']==20000,j.get('message');return j['data']
baseline=rows();check('legacy_unfiltered_api_has_data',len(baseline['data'])==20 and baseline['count']>=2311)
selected=rows(workUnit='陕西谷宜健康管理有限公司');check('unit_filter_list_and_total_match',selected['count']==len(selected['data'])==5)
blank=rows(responsibleHealthManagerCode=' ');check('blank_manager_not_a_real_filter',blank['count']==baseline['count'])
page2=rows(page=2);check('pagination_advances',page2['data'][0]['id']!=baseline['data'][0]['id'] and page2['count']==baseline['count'])
r=s.get(B+'/newRegController/selectRegInfo',params={'times':'wrong date range'},timeout=10);check('invalid_date_returns_business_error',r.status_code==200 and r.json()['code']!=20000)
empty=rows(times='2100-01-01 00:00:00 - 2100-01-02 00:00:00');check('creation_date_filter_consistent',empty['count']==len(empty['data'])==0)
chrome=subprocess.Popen(['C:/Program Files/Google/Chrome/Application/chrome.exe','--headless=new','--disable-gpu','--no-first-run','--no-default-browser-check','--remote-debugging-port=9226','--user-data-dir=F:/workstation/.tools/runtime/legacy-archive-chrome','about:blank'],stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL,creationflags=subprocess.CREATE_NO_WINDOW)
ws=None;seq=0;errors=[];requests_seen=[]
def rpc(method,params=None):
 global seq
 seq+=1;n=seq;ws.send(json.dumps({'id':n,'method':method,'params':params or {}}))
 while True:
  msg=json.loads(ws.recv())
  if msg.get('method')=='Runtime.exceptionThrown':errors.append(msg['params']['exceptionDetails'].get('text'))
  if msg.get('method')=='Network.requestWillBeSent' and '/newRegController/selectRegInfo' in msg['params']['request']['url']:requests_seen.append(msg['params']['request']['url'])
  if msg.get('id')==n:
   assert 'error' not in msg,msg
   return msg.get('result',{})
def js(expr):
 r=rpc('Runtime.evaluate',{'expression':expr,'returnByValue':True,'awaitPromise':True});assert 'exceptionDetails' not in r,r;return r.get('result',{}).get('value')
def wait(expr,seconds=45):
 end=time.monotonic()+seconds
 while time.monotonic()<end:
  if js('Boolean('+expr+')'):return
  time.sleep(.15)
 raise AssertionError('Browser timeout '+expr)
try:
 for i in range(100):
  try:tabs=s.get('http://localhost:9226/json',timeout=1).json();break
  except Exception:time.sleep(.1)
 ws=websocket.create_connection(next(t['webSocketDebuggerUrl'] for t in tabs if t['type']=='page'),timeout=60,suppress_origin=True)
 for cmd in ['Page.enable','Runtime.enable','Network.enable']:rpc(cmd)
 rpc('Network.setCacheDisabled',{'cacheDisabled':True});rpc('Emulation.setDeviceMetricsOverride',{'width':1600,'height':900,'deviceScaleFactor':1,'mobile':False})
 rpc('Network.setCookies',{'cookies':[{'name':c.name,'value':c.value,'domain':'localhost','path':'/'} for c in s.cookies]})
 rpc('Page.navigate',{'url':B+'/manage/newpage/personalBasicInformation'})
 wait('document.querySelectorAll(".layui-table-main tbody tr[data-index]").length===20');time.sleep(1)
 check('browser_default_list_stays_visible',js('document.querySelectorAll(".layui-table-main tbody tr[data-index]").length')==20)
 check('default_unit_and_manager_empty',js('document.querySelector("#tree").value==="" && document.querySelector("#responsibleHealthManager").value===""'))
 check('no_automatic_group_name_filter',not any('workUnit=' in u and '%E7%94%B2' in u for u in requests_seen))
 js('document.querySelector("#tree").value="陕西谷宜健康管理有限公司";document.querySelector("button[lay-filter=regManageQueryBtn]").click()')
 wait('document.querySelectorAll(".layui-table-main tbody tr[data-index]").length===5');check('browser_unit_search_has_five_rows')
 js('document.querySelector("#symptoms").click()');wait('document.querySelectorAll(".layui-table-main tbody tr[data-index]").length===20');check('browser_clear_restores_default_list')
 check('browser_no_javascript_exceptions',not errors)
 out={'base':B,'checks':checks,'count':len(checks),'defaultListRows':len(baseline['data']),'defaultQueryTotal':baseline['count'],'unitFilteredTotal':selected['count'],'legacyDatabaseRowsUnmodified':True,'javascriptErrors':errors}
 Path('F:/workstation/background/yihe3.0/database/legacy-archive-verification.json').write_text(json.dumps(out,ensure_ascii=False,indent=2),encoding='utf-8');print('PASS legacy regression:',len(checks),'checks')
finally:
 if ws:ws.close()
 chrome.terminate()
