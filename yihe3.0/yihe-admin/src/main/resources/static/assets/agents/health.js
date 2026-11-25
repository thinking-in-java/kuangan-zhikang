(function () {
 'use strict';
 const agent=window.DigitalHealth,$=s=>document.querySelector(s),esc=v=>String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const paths={clipboard:'M8 4H5v18h14V4h-3 M8 2h8v5H8Z M8 12h8 M8 16h6',alert:'m12 3 10 18H2Z M12 9v5 M12 17v1',user:'M16 7a4 4 0 1 1-8 0 4 4 0 0 1 8 0 M4 22v-3a8 8 0 0 1 16 0v3',search:'M16 10a6 6 0 1 1-12 0 6 6 0 0 1 12 0 m-1 5 6 6',spark:'m12 2 3 7 7 3-7 3-3 7-3-7-7-3 7-3Z',bell:'M4 17h16l-2-4V9a6 6 0 0 0-12 0v4Z M10 21h4 M12 1v2',network:'M12 2a3 3 0 1 0 0 6 3 3 0 0 0 0-6 M5 16a3 3 0 1 0 0 6 3 3 0 0 0 0-6 M19 16a3 3 0 1 0 0 6 3 3 0 0 0 0-6 M12 8v4L5 16 M12 12l7 4',file:'M14 2H4v20h16V8Z M14 2v6h6 M8 12h8 M8 16h5',calendar:'M3 5h18v17H3Z M7 2v6 M17 2v6 M3 10h18 M8 14h2v3H8Z',chart:'M3 3v18h19 M6 15l5-6 5 3 6-8',layers:'m12 2 10 6-10 6L2 8Z M2 13l10 6 10-6 M2 18l10 6 10-6',heart:'M12 21 3 12a5 5 0 0 1 7-7l2 2 2-2a5 5 0 0 1 7 7Z'};
 function icon(name){return '<svg viewBox="0 0 24 25" aria-hidden="true"><path d="'+(paths[name]||paths.file)+'"/></svg>';}
 document.querySelectorAll('[data-icon]').forEach(n=>n.innerHTML=icon(n.dataset.icon));
 const workers=[['archive','档案小谷','file','#1884ff','#eaf5ff'],['assess','评估小谷','alert','#e99b12','#fff6df'],['follow','随访小谷','calendar','#7a48f5','#f1edff'],['plan','方案小谷','file','#406eff','#eaf0ff'],['care','心理小谷','heart','#f16b69','#fff0ef']];
 const statusLabels={queued:'待处理',in_progress:'正在执行',pending_review:'待人工审核',completed:'已完成',failed:'处理失败',cancelled:'已取消',unknown:'状态待核实'};
 let tab='all',stars=[],overview,context,loading=false,taskFilter={},taskPage=1,taskTotal=0,taskEpoch=0;
 const starKey='digital-health:favorites:'+agent.getIdentity().code;
 try{stars=JSON.parse(localStorage.getItem(starKey)||'[]');if(!Array.isArray(stars))stars=[];}catch(e){stars=[];}
 function navigate(path){const u=new URL(path,location.origin);if(u.origin!==location.origin)return;try{const host=parent!==window?parent:window;if(host.layui?.index){host.layui.index.openTab({url:u.pathname+u.search,title:({'/assets/agents/health.html':'数字健康师','/assets/agents/overview.html':'AI 智能体总览'})[u.pathname]||'健康服务工作台'});return;}}catch(e){}location.href=u.pathname+u.search;}
 function pendingCount(s){if(!s)return 0;return s.serverId&&s.messages.length?s.actions.length:Number(s.pendingCount||s.actions.length||0);}
 function drawTabs(){
  const sessions=agent.getSessions();
  document.querySelectorAll('#dh-session-list .dh-session-row').forEach(row=>{const id=row.querySelector('[data-session]').dataset.session,s=sessions.find(s=>s.id===id);row.hidden=tab==='starred'?!stars.includes(id):tab==='pending'?!pendingCount(s):false;});
  const active=agent.getCurrentSession(),starred=!!(active&&stars.includes(active.id));$('#hub-star').setAttribute('aria-pressed',String(starred));$('#hub-star').textContent=starred?'★ 已关注':'☆ 关注';
  $('#metric-confirm').textContent=sessions.reduce((n,s)=>n+pendingCount(s),0);
  let empty=$('#hub-session-empty');if(!empty){empty=document.createElement('p');empty.id='hub-session-empty';empty.className='dh-no-session';$('#dh-session-list').appendChild(empty);}const visible=[...document.querySelectorAll('#dh-session-list .dh-session-row')].some(r=>!r.hidden);empty.hidden=visible||!!$('#dh-session-list .dh-no-session:not(#hub-session-empty)');empty.textContent=tab==='pending'?'暂无待确认的对话':tab==='starred'?'关注的对话会保留在这里':'没有匹配的会话';
 }
 function setTab(value){tab=value;document.querySelectorAll('.hub-session-tabs button').forEach(b=>b.setAttribute('aria-selected',String(b.dataset.tab===value)));drawTabs();if(tab!=='all'&&matchMedia('(max-width:650px)').matches)toast('请在桌面宽度查看会话分类');}
 function toast(message){const el=$('#dh-toast');el.textContent=message;el.hidden=false;clearTimeout(el._hideTimer);el._hideTimer=setTimeout(()=>el.hidden=true,4500);}
 function count(v){return v==null?'—':Number(v).toLocaleString('zh-CN');}
 function sumBacklog(worker,status){return (overview?.backlog||[]).filter(x=>(!worker||x.worker===worker)&&(!status||x.status===status)).reduce((n,x)=>n+Number(x.count),0);}
 async function refresh(){
  if(loading)return;loading=true;$('#hub-refresh').disabled=true;
  const reads=await Promise.allSettled([agent.request('/agent/context'),agent.request('/agents/overview'),agent.request('/agents/tasks?period=all&priority=high_or_urgent&status=all&limit=1')]);
  try{
   if(reads[0].status==='fulfilled'){context=reads[0].value;const s=context.statistics||{};$('#hub-total').textContent='全矿区职工（共 '+count(s.total)+' 人）';$('#hub-role').textContent=context.canModify?'授权医生 / 健康管理员':'健康服务人员';$('#reminder-risk').textContent=count(Number(s.high||0)+Number(s.critical||0))+' 人';}
   if(reads[1].status==='fulfilled'){overview=reads[1].value;$('#metric-review').textContent=count(sumBacklog(null,'pending_review'));$('#reminder-review').textContent=count(sumBacklog(null,'pending_review'))+' 项';$('#reminder-follow').textContent=count(sumBacklog('follow'))+' 项';
    $('#hub-workers').innerHTML=workers.map(([id,name,ico,ink,wash])=>{const current=overview.byWorker[id]||{},active=sumBacklog(id),review=sumBacklog(id,'pending_review');return '<button class="hub-worker" data-worker="'+id+'" style="--ink:'+ink+';--wash:'+wash+'"><i data-icon="'+ico+'">'+icon(ico)+'</i><div><strong>'+name+'</strong><em>'+((current.in_progress||0)?'执行中':active?'有待办':'暂无执行中任务')+'</em><p>今日 '+count(current.total)+' 项 · 待处理 '+count(active)+' 项'+(review?' · 待审核 '+count(review)+' 项':'')+'</p></div><span>›</span></button>';}).join('');$('#hub-sync').textContent='15秒刷新';
   }
   if(reads[2].status==='fulfilled')$('#metric-high').textContent=count(reads[2].value.total);
   const errors=reads.filter(r=>r.status==='rejected');$('#hub-data-status').textContent=errors.length?'部分数据暂不可用：'+errors.map(x=>x.reason.message).join('；'):'当前单位真实数据 · '+new Date().toLocaleTimeString('zh-CN',{timeZone:'Asia/Shanghai',hour12:false})+' 更新。任务数量不等同于职工人数。';
   $('#hub-sync').textContent=errors.length?'部分数据不可用':'15秒刷新';
  }finally{loading=false;$('#hub-refresh').disabled=false;}
 }
 function taskHref(t){if(t.href)return t.href;if(t.canOpen===0)return null;const query=new URLSearchParams({worker:t.worker});if(t.source==='assignment'){query.set('task',t.id);return '/manage/digital-workers/workbench?'+query;}if(t.source==='ocr'){query.set('task',t.id);return '/manage/digital-workers/records?'+query;}if(t.source==='alert'){query.set('alert',t.id);return '/manage/digital-workers/dispatch?'+query;}return null;}
 async function loadTasks(){
  const epoch=++taskEpoch;$('#hub-task-body').innerHTML='<p role="status">正在读取业务任务…</p>';$('#hub-task-prev').disabled=true;$('#hub-task-next').disabled=true;
  try{const result=await agent.request('/agents/tasks?'+new URLSearchParams({period:'all',status:'all',...taskFilter,page:taskPage,limit:8}));if(epoch!==taskEpoch)return;taskTotal=result.total;$('#hub-task-body').innerHTML=result.items.length?result.items.map(t=>{const href=taskHref(t);return '<article><h3>'+esc(t.title)+' <small>· '+esc(statusLabels[t.status]||t.status)+'</small></h3><p>'+esc(workers.find(w=>w[0]===t.worker)?.[1]||t.worker)+' · '+esc(t.memberName||'未关联职工')+' '+esc(t.memberCode||'')+' · '+esc(t.updatedAt||'')+'</p>'+(href?'<a href="'+esc(href)+'" data-navigate="'+esc(href)+'">前往业务工作台处理 ›</a>':'<p>关联资料暂不可访问，请在任务中心核对。</p>')+'</article>';}).join(''):'<p>当前筛选下暂无业务任务。</p>';$('#hub-task-page').textContent='第 '+taskPage+' 页 / 共 '+Math.max(1,Math.ceil(result.total/8))+' 页 · '+result.total+' 项';$('#hub-task-prev').disabled=taskPage<=1;$('#hub-task-next').disabled=taskPage*8>=taskTotal;}catch(e){if(epoch!==taskEpoch)return;$('#hub-task-body').innerHTML='<p role="alert">'+esc(e.message)+'</p><button data-retry-tasks>重试</button>';$('#hub-task-page').textContent='未能读取任务';}
 }
 function tasks(filter,title){taskFilter=filter;taskPage=1;$('#hub-task-title').textContent=title;const dialog=$('#hub-task-dialog');if(!dialog.open)dialog.showModal();loadTasks();}
 document.addEventListener('click',e=>{
  const b=e.target.closest('button,a[data-navigate]');if(!b)return;
  if(b.dataset.navigate){e.preventDefault();navigate(b.dataset.navigate);return;}
  if(b.dataset.prompt){agent.ask(b.dataset.prompt);return;}
  if(b.dataset.tab){setTab(b.dataset.tab);return;}
  if(b.id==='hub-star'){const s=agent.getCurrentSession();if(!s)return;if(!s.serverId){toast('发送第一条消息后可以关注此对话');return;}stars=stars.includes(s.id)?stars.filter(x=>x!==s.id):stars.concat(s.id);try{localStorage.setItem(starKey,JSON.stringify(stars));}catch(e){toast('当前浏览器无法保存关注设置');}drawTabs();return;}
  if(b.dataset.worker){tasks({worker:b.dataset.worker},workers.find(w=>w[0]===b.dataset.worker)[1]+' · 业务任务');return;}
  if(b.dataset.tasks){tasks({status:b.dataset.tasks},'等待人工审核的业务任务');return;}
  if(b.hasAttribute('data-high-tasks')){tasks({priority:'high_or_urgent'},'高优先与紧急任务');return;}
  if(b.id==='hub-refresh')refresh();
  if(b.id==='hub-notice-close')$('#hub-notice').hidden=true;
  if(b.id==='hub-task-close')$('#hub-task-dialog').close();
  if(b.id==='hub-task-prev'&&taskPage>1){taskPage--;loadTasks();}
  if(b.id==='hub-task-next'&&taskPage*8<taskTotal){taskPage++;loadTasks();}
  if(b.hasAttribute('data-retry-tasks'))loadTasks();
 });
 $('#hub-task-dialog').addEventListener('close',()=>taskEpoch++);
 $('#dh-patient-context').addEventListener('change',()=>{const select=$('#dh-patient-context'),target=$('#hub-selected');target.hidden=!select.value;target.textContent=select.value?'正在服务：'+select.options[select.selectedIndex].text:'';});
 $('#dh-history-search').addEventListener('input',drawTabs);window.addEventListener('digital-health:update',drawTabs);
 // The shared module owns rendering and persistence; apply filters after each session refresh.
 const listObserver=new MutationObserver(records=>{if(records.some(r=>[...r.addedNodes].some(n=>n.nodeType===1&&n.classList.contains('dh-session-row'))))drawTabs();});listObserver.observe($('#dh-session-list'),{childList:true});
 const timer=setInterval(()=>{if(!document.hidden)refresh();},15000);window.addEventListener('pagehide',()=>{clearInterval(timer);listObserver.disconnect();});
 refresh();drawTabs();
})();
