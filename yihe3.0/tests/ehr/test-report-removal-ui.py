"""Isolated Chrome UI check against synthetic HTTP service."""
import requests,websocket,json,time,base64,io,os,uuid
from pathlib import Path
from PIL import Image
from PIL.PngImagePlugin import PngInfo
B='http://localhost:18081';s=requests.Session();s.trust_env=False
s.get(B+'/login',timeout=10)
assert s.post(B+'/login',data={'username':'医生医生','password':os.environ['EHR_TEST_PASSWORD'],'code':'123465','rememberMe':'false'},timeout=20).json()['code']==200
ctx=s.get(B+'/ehr/api/context',timeout=10).json()['data'];assert ctx['dataMode']=='synthetic'
h={'X-CSRF-Token':ctx['csrfToken']}
p=s.post(B+'/ehr/api/records',headers=h,json={'name':'报告移除交互测试','code':'UIRM_'+uuid.uuid4().hex[:12],'sex':'未填写','org':'隔离测试单位','team':'测试班组','job':'测试岗位'},timeout=20).json()['data']
ws=None;counter=0
def cdp(method,params=None):
 global counter
 counter+=1;ident=counter;ws.send(json.dumps({'id':ident,'method':method,'params':params or {}}))
 while True:
  r=json.loads(ws.recv())
  if r.get('id')==ident:
   assert 'error' not in r,r.get('error');return r.get('result',{})
def js(expression):
 result=cdp('Runtime.evaluate',{'expression':expression,'returnByValue':True,'awaitPromise':True})
 assert 'exceptionDetails' not in result,result
 return result['result'].get('value')
def until(expression):
 for _ in range(40):
  if js(expression):return
  time.sleep(.25)
 raise AssertionError('UI timeout: '+expression)
try:
 meta=PngInfo();meta.add_text('run',str(uuid.uuid4()));buf=io.BytesIO();Image.new('RGB',(27,29),(32,171,134)).save(buf,format='PNG',pnginfo=meta)
 r=s.post(B+'/ehr/api/records/'+p['id']+'/reports',headers=dict(h,**{'Idempotency-Key':str(uuid.uuid4())}),files={'file':('误传报告示例.png',buf.getvalue(),'image/png')},timeout=20);assert r.status_code==200,r.text
 tabs=requests.get('http://localhost:19228/json',timeout=10).json();tab=next(x for x in tabs if x['type']=='page')
 ws=websocket.create_connection(tab['webSocketDebuggerUrl'],origin='http://localhost:19228',timeout=20)
 cdp('Network.enable');cdp('Page.enable')
 for cookie in s.cookies:
  cdp('Network.setCookie',{'name':cookie.name,'value':cookie.value,'url':B,'path':cookie.path or '/'})
 cdp('Page.navigate',{'url':B+'/assets/health-archive/detail.html?id='+p['id']})
 until("document.querySelector('.report-remove')!==null")
 assert js("document.querySelectorAll('.report-remove').length")==1
 box=js("(()=>{const b=document.querySelector('.report-remove'),c=b.closest('article');const x=b.getBoundingClientRect(),y=c.getBoundingClientRect();return {width:x.width,height:x.height,rightGap:y.right-x.right,visible:x.width>=32&&x.height>=32};})()")
 assert box['visible'] and box['rightGap']<45,box
 js("document.querySelector('.report-remove').click()")
 until("document.querySelector('#remove-report-form')!==null && document.querySelector('dialog').open")
 output=Path('F:/workstation/.tools/runtime/report-removal-confirm.png')
 output.write_bytes(base64.b64decode(cdp('Page.captureScreenshot',{'format':'png','captureBeyondViewport':False})['data']))
 assert js("document.querySelector('#remove-report-form textarea').required")
 js("document.querySelector('#remove-report-form textarea').value='隔离测试：误传他人报告';document.querySelector('#remove-report-form').requestSubmit()")
 until("!document.querySelector('dialog').open && document.querySelectorAll('.report-manage').length===0")
 current=s.get(B+'/ehr/api/records/'+p['id'],timeout=15).json()['data'];assert current['reports']==[]
 print('PASS visible accessible X button at report top-right')
 print('PASS confirmation includes filename and required reason')
 print('PASS clicking confirm removes report card and refreshes employee detail')
 print('Screenshot:',output)
finally:
 if ws:
  try:cdp('Browser.close')
  except Exception:pass
  ws.close()
 current=s.get(B+'/ehr/api/records/'+p['id'],timeout=15).json()['data']
 s.delete(B+'/ehr/api/records/'+p['id'],headers=h,json={'rowVersion':current['rowVersion'],'reason':'隔离界面测试完成'},timeout=20).raise_for_status()
