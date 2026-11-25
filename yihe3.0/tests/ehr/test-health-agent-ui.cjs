// Real chat DOM with an HTTP contract stub; never calls a model or changes real data.
const {JSDOM}=require('jsdom'),fs=require('node:fs'),path=require('node:path'),assert=require('node:assert/strict');
const root=path.resolve(__dirname,'../../yihe-admin/src/main/resources/static');
const marked=fs.readFileSync(path.join(root,'editormd/lib/marked.min.js'),'utf8');
const script=fs.readFileSync(path.join(root,'assets/js/digital-health.js'),'utf8');
const cid='00000000-0000-4000-8000-000000000001',rid='00000000-0000-4000-8000-000000000002',aid='00000000-0000-4000-8000-000000000003';
const pause=ms=>new Promise(r=>setTimeout(r,ms));
async function until(check){for(let i=0;i<100;i++){if(check())return;await pause(40);}throw Error('UI condition timeout');}
function make(html,saved){const dom=new JSDOM(html,{url:'http://localhost:18083/assets/agents/health.html',runScripts:'outside-only',pretendToBeVisual:true});const w=dom.window,calls=[];
 w.matchMedia=()=>({matches:true,addEventListener(){},removeEventListener(){}});w.ResizeObserver=class{observe(){}unobserve(){}disconnect(){}};w.IntersectionObserver=class{observe(){}unobserve(){}disconnect(){}};
 w.HTMLElement.prototype.scrollTo=function(){};w.HTMLElement.prototype.scrollIntoView=function(){};Object.defineProperty(w.HTMLElement.prototype,'innerText',{get(){return this.textContent},set(v){this.textContent=v},configurable:true});
 w.HTMLDialogElement.prototype.showModal=function(){this.open=true};w.HTMLDialogElement.prototype.close=function(){this.open=false};
 w.fetch=async(url,init={})=>{let b=init.body?JSON.parse(init.body):null;calls.push({url,method:init.method||'GET',body:b,headers:init.headers});let data;
  if(url==='/ehr/api/context')data={csrfToken:'fixture-csrf'};
  else if(url==='/ehr/api/agent/context')data={enabled:true,dataMode:'synthetic',statistics:{total:8,high:2,critical:1,pending:4,archived:3}};
  else if(url==='/ehr/api/agent/conversations')data=init.method==='POST'?{id:cid,title:'测试会话'}:saved?[{id:cid,title:'上次对话',updatedAt:'2026-11-17 11:00:00'}]:[];
  else if(url.includes('/records?'))data={items:[{id:rid,name:'界面测试职工',code:'UI001',team:'测试班组'}],total:1};
  else if(url==='/ehr/api/agent/chat')data={conversationId:cid,turnId:'test-turn',text:'**已查询测试数据**。\n<img src=x onerror=alert(1)>\n[不安全链接](javascript:alert(1))',tools:[{name:'search_employees',status:'ok'}],sources:[{kind:'record',recordId:rid,label:'职工 <img src=x onerror=alert(1)>'},{kind:'record',recordId:rid,label:'同一档案重复项'}],actions:[{id:aid,type:'delete_employee',target:{name:'界面测试职工',code:'UI001',reportCount:2,reportVersions:3},changes:{reason:'用户要求测试删除'}}],elapsedMs:1200};
  else if(url.includes('/actions/'))data={status:b.confirm?'confirmed':'cancelled',text:b.confirm?'已删除测试档案。':'已取消。'};
  else if(url.includes('/conversations/'))data=saved?JSON.parse(JSON.stringify(saved)):{id:cid,messages:[],actions:[]};
  else throw Error('Unexpected route '+url);
  return {ok:true,status:200,json:async()=>({code:0,data})};
 };
 w.eval(marked);w.eval(script);return {dom,w,calls,doc:w.document};}
(async()=>{
 const x=make(fs.readFileSync(path.join(root,'assets/agents/health.html'),'utf8'));x.w.DigitalHealth.mountPage();await until(()=>x.doc.querySelector('#dh-patient-context option[value="'+rid+'"]'));
 assert.ok(x.calls.some(c=>c.url==='/ehr/api/agent/context'));assert.ok(!x.doc.querySelector('.dh-mode-tag').textContent.includes('预览'));
 const select=x.doc.querySelector('#dh-patient-context');select.value=rid;select.dispatchEvent(new x.w.Event('change'));
 const input=x.doc.querySelector('textarea');input.value='删除界面测试职工，先预览';input.dispatchEvent(new x.w.Event('input'));x.doc.querySelector('.dh-compose-form').dispatchEvent(new x.w.Event('submit',{bubbles:true,cancelable:true}));await until(()=>x.doc.querySelector('[data-agent-confirm]'));
 const sent=x.calls.find(c=>c.url==='/ehr/api/agent/chat');assert.equal(sent.body.recordId,rid);assert.equal(sent.headers['X-CSRF-Token'],'fixture-csrf');assert.ok(sent.body.requestId);assert.ok(!('messages' in sent.body));
 assert.equal(x.calls.filter(c=>c.url.includes('/actions/')).length,0,'Model response cannot automatically execute');
 assert.equal(x.doc.querySelectorAll('.dh-message-content img,.dh-message-content script,.dh-message-content a[href^="javascript:"]').length,0,'Model Markdown cannot inject active HTML or script links');assert.ok(x.doc.querySelector('.dh-message-ai strong'),'Markdown emphasis rendered');
 assert.equal(x.doc.querySelectorAll('.dh-agent-results img').length,0,'Tool source labels escaped');assert.equal(x.doc.querySelectorAll('.dh-source-link').length,1,'Duplicate sources removed');assert.equal(x.doc.querySelector('.dh-source-details').open,false,'Sources collapsed');assert.ok(x.doc.querySelector('.dh-message-scroll').contains(x.doc.querySelector('.dh-agent-results')),'Sources share message scrollbar');assert.equal(input.value,'','Accepted messages clear composer');assert.equal(x.doc.querySelector('.dh-send-button').disabled,true,'Blank composer cannot send');
 x.doc.querySelector('[data-agent-confirm]').click();assert.ok(x.doc.querySelector('#dh-action-dialog').open);assert.ok(x.doc.querySelector('#dh-action-dialog input').required);assert.equal(x.doc.querySelector('#dh-action-dialog input').value,'');assert.ok(x.doc.querySelector('#dh-action-dialog').textContent.includes('2 份关联体检报告'));
 x.doc.querySelector('#dh-action-dialog input').value='UI001';x.doc.querySelector('#dh-action-dialog form').dispatchEvent(new x.w.Event('submit',{bubbles:true,cancelable:true}));await until(()=>x.calls.some(c=>c.url.includes('/actions/')&&c.body.confirm));await until(()=>!x.doc.querySelector('#dh-action-dialog'));
 assert.equal(x.calls.find(c=>c.url.includes('/actions/')).body.employeeCode,'UI001');assert.equal(x.doc.querySelectorAll('[data-agent-confirm]').length,0);
 x.doc.querySelector('[data-delete-session]').click();assert.ok(x.doc.querySelector('#dh-delete-session-dialog').open);assert.equal(x.calls.filter(c=>c.method==='DELETE').length,0,'Opening delete dialog does not delete');
 x.doc.querySelector('#dh-delete-session-dialog .dh-dialog-close').click();assert.equal(x.calls.filter(c=>c.method==='DELETE').length,0,'Cancelling conversation delete does not call API');
 x.doc.querySelector('[data-delete-session]').click();x.doc.querySelector('#dh-delete-session-dialog form').dispatchEvent(new x.w.Event('submit',{bubbles:true,cancelable:true}));await until(()=>!x.doc.querySelector('#dh-delete-session-dialog'));
 assert.equal(x.calls.filter(c=>c.method==='DELETE').length,1);assert.equal(x.calls.find(c=>c.method==='DELETE').url,'/ehr/api/agent/conversations/'+cid);assert.equal(x.calls.find(c=>c.method==='DELETE').headers['X-CSRF-Token'],'fixture-csrf');assert.equal(x.doc.querySelectorAll('.dh-session-row').length,1);assert.equal(x.doc.querySelector('.dh-session strong').textContent,'新的健康对话');assert.equal(x.doc.querySelectorAll('[data-agent-confirm],.dh-source-link').length,0,'Deleting current session clears previous actions and sources');
 x.doc.querySelector('[data-delete-session]').click();x.doc.querySelector('#dh-delete-session-dialog form').dispatchEvent(new x.w.Event('submit',{bubbles:true,cancelable:true}));await until(()=>!x.doc.querySelector('#dh-delete-session-dialog'));assert.equal(x.calls.filter(c=>c.method==='DELETE').length,1,'An unsaved local conversation deletes without a server request');
 console.log('PASS workbench chat: live chat, confirmation, conversation delete/cancel, CSRF, empty state, no employee deletion');x.dom.window.close();
 const f=make('<div class="layui-body"></div>');f.w.DigitalHealth.mountFloating({code:'doctor',name:'测试医生',gender:1});await until(()=>f.doc.querySelector('.dh-compose-input'));
 f.doc.querySelector('.dh-launcher').click();await pause(50);const fi=f.doc.querySelector('textarea');fi.value='删除界面测试职工';fi.dispatchEvent(new f.w.Event('input'));fi.dispatchEvent(new f.w.KeyboardEvent('keydown',{key:'Enter',isComposing:true,bubbles:true,cancelable:true}));fi.dispatchEvent(new f.w.KeyboardEvent('keydown',{key:'Enter',shiftKey:true,bubbles:true,cancelable:true}));assert.equal(f.calls.filter(c=>c.url==='/ehr/api/agent/chat').length,0,'IME confirmation and Shift Enter do not send');fi.dispatchEvent(new f.w.KeyboardEvent('keydown',{key:'Enter',bubbles:true,cancelable:true}));fi.dispatchEvent(new f.w.KeyboardEvent('keydown',{key:'Enter',bubbles:true,cancelable:true}));assert.equal(f.doc.querySelector('.dh-reply-status').hidden,false,'Waiting indicator visible');await until(()=>f.doc.querySelector('[data-agent-cancel]'));assert.equal(f.calls.filter(c=>c.url==='/ehr/api/agent/chat').length,1,'Repeated Enter cannot duplicate a request');f.doc.querySelector('[data-agent-cancel]').click();await until(()=>f.calls.some(c=>c.url.includes('/actions/')&&c.body.confirm===false));assert.equal(f.calls.filter(c=>c.url.includes('/actions/')&&c.body.confirm===true).length,0);console.log('PASS floating chat: shared API, IME and multiline keyboard, busy state, no duplicate sending, cancellation makes no write');f.dom.window.close();
 const saved={id:cid,messages:[{role:'user',text:'Previous question'},{role:'ai',text:'Previous complete analysis'}],actions:[],lastResult:{sources:[{kind:'record',recordId:rid,label:'Previous source'}],tools:[],text:'Previous complete analysis'}};
 for(const mode of ['page','floating']){
  const h=make(mode==='page'?fs.readFileSync(path.join(root,'assets/agents/health.html'),'utf8'):'<div class="layui-body"></div>',saved);
  if(mode==='page')h.w.DigitalHealth.mountPage();else h.w.DigitalHealth.mountFloating({code:'doctor',name:'Test doctor',gender:2});
  await until(()=>h.doc.querySelector('.dh-history-gate')&&!h.doc.querySelector('.dh-history-gate').hidden);
  if(mode==='floating')h.doc.querySelector('.dh-launcher').click();
  const prior=h.doc.querySelector('.dh-previous-visit'),gate=h.doc.querySelector('.dh-history-gate'),scroll=h.doc.querySelector('.dh-message-scroll');
  assert.ok(prior.hidden,'Previous analysis is collapsed on entering');assert.ok(gate.querySelector('svg'),'History uses a clock icon');assert.equal(h.doc.querySelectorAll('.dh-visit-greeting').length,1,'One current greeting');assert.ok(h.doc.querySelector('.dh-visit-greeting').textContent.includes('\u4e0a\u6ed1'));assert.equal(h.doc.querySelectorAll('.dh-history-messages .dh-message').length,0,'Old bubbles are not mounted before expansion');assert.ok(prior.contains(h.doc.querySelector('.dh-source-details')),'Old sources stay with old messages');
  scroll.dispatchEvent(new h.w.WheelEvent('wheel',{deltaY:-100,bubbles:true}));assert.equal(prior.hidden,false,'Scrolling upwards reveals history');assert.equal(h.doc.querySelectorAll('.dh-history-messages .dh-message').length,2,'Previous messages preserved');assert.equal(gate.getAttribute('aria-expanded'),'true');gate.click();assert.equal(prior.hidden,true,'History can collapse again');gate.click();assert.equal(prior.hidden,false,'Clock is an accessible history button');
  if(mode==='floating'){h.w.DigitalHealth.closeFloating();h.doc.querySelector('.dh-launcher').click();assert.equal(prior.hidden,true,'Reopening starts at the greeting again');assert.equal(h.doc.querySelectorAll('.dh-visit-greeting').length,1,'Reopening does not stack greetings');}
  assert.equal(h.calls.filter(c=>c.url==='/ehr/api/agent/chat').length,0,'Greeting and history access do not call the model');
  const field=h.doc.querySelector('textarea');field.value='Continue the previous question';field.dispatchEvent(new h.w.Event('input'));h.doc.querySelector('.dh-compose-form').dispatchEvent(new h.w.Event('submit',{bubbles:true,cancelable:true}));await until(()=>h.calls.some(c=>c.url==='/ehr/api/agent/chat'));
  assert.equal(h.calls.find(c=>c.url==='/ehr/api/agent/chat').body.conversationId,cid,'New question continues server history');assert.equal(h.calls.filter(c=>c.url==='/ehr/api/agent/conversations'&&c.method==='POST').length,0,'No new server conversation just for a greeting');
  console.log('PASS '+mode+' greeting: collapsed clock history, scroll/click expansion, sources retained, no extra model calls, original context reused');h.dom.window.close();
 }
 const d=make('<div class="layui-body"></div>');d.w.DigitalHealth.mountFloating({code:'dock-doctor',name:'Dock doctor',gender:2});await pause(70);
 const dock=d.doc.querySelector('.dh-doctor-dock'),launcher=d.doc.querySelector('.dh-launcher'),restore=d.doc.querySelector('.dh-widget-restore'),badge=d.doc.querySelector('.dh-doctor-badge');
 assert.equal(dock.querySelectorAll('[data-motion],.dh-motion-controls').length,0,'Automatic gestures do not expose action buttons');assert.ok(dock.querySelector('.dh-launcher-glow svg'),'Occupational health platform present');
 assert.ok(badge.hidden,'No fabricated unread greetings');
 d.doc.querySelector('.dh-widget-minimize').click();assert.ok(dock.classList.contains('dh-widget-compact'));assert.equal(restore.hidden,false);assert.equal(d.calls.filter(c=>c.url==='/ehr/api/agent/chat').length,0);
 restore.click();assert.ok(!dock.classList.contains('dh-widget-compact'));assert.equal(d.doc.querySelector('.dh-floating-panel').hidden,true,'Restoring the doctor does not open chat');
 launcher.click();
 for(let i=1;i<=3;i++){
  const field=d.doc.querySelector('textarea');field.value='Unread fixture '+i;field.dispatchEvent(new d.w.Event('input'));d.doc.querySelector('.dh-compose-form').requestSubmit();
  d.w.DigitalHealth.closeFloating();
  await until(()=>dock.dataset.unread===String(i));
  assert.equal(badge.textContent,String(i));assert.equal(badge.hidden,false);
 }
 d.doc.querySelector('.dh-bubble-dismiss').click();assert.equal(dock.dataset.unread,'3','Dismissing a reminder does not mark replies as read');
 d.doc.querySelector('.dh-widget-minimize').click();assert.equal(d.doc.querySelector('.dh-compact-badge').textContent,'3');assert.equal(d.doc.querySelector('.dh-compact-badge').hidden,false);
 restore.click();assert.equal(dock.dataset.unread,'3','Restoring does not mark messages as read');
 launcher.click();assert.equal(dock.dataset.unread,'0');assert.equal(badge.hidden,true);assert.equal(d.doc.querySelector('.dh-compact-badge').hidden,true);assert.equal(d.doc.querySelector('.dh-previous-visit').hidden,true);assert.ok(d.doc.querySelector('.dh-message-list:not(.dh-history-messages)').textContent.includes('Unread fixture 3'),'Opening unread replies keeps new content visible');
 assert.equal(d.calls.filter(c=>c.url==='/ehr/api/agent/chat').length,3,'Motions, shrink, restore and reading make no model calls');
 console.log('PASS doctor dock: no action buttons, platform, shrink/restore, unread 1/2/3 in both sizes, read reset, replies visible, no spurious model calls');d.dom.window.close();
})().catch(e=>{console.error(e);process.exit(1);});
