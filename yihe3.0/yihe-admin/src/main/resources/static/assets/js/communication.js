(function () {
 'use strict';
 const base=document.body.dataset.base||'/',api=base+'ehr/api/communication';
 const $=id=>document.getElementById(id), esc=v=>String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const icon=n=>`<svg aria-hidden="true"><use href="#c-${n}"/></svg>`;
 const state={csrf:'',user:'',filter:'open',page:1,search:'',total:0,items:[],selected:null,messages:[],files:[],draft:null,health:null,healthTab:'archive',busy:false,aiBusy:false,uploading:false,epoch:0,listEpoch:0,seenCounts:null,request:null,loadingMessages:false,connected:false};
 const riskNames={unknown:'未评估',low:'低风险',medium:'中风险',high:'高风险',critical:'极高风险'};
 const roleNames={employee:'职工',doctor:'医生',manager:'健康管理员',ai_assisted:'人工确认 · AI辅助',auto_reply:'自动回复',system:'服务记录'};
 const statusNames={confirmed:'医生已确认',unreviewed:'未审核',needs_review:'待核对',rejected:'已驳回'};
 const uid=()=>window.crypto&&crypto.randomUUID?crypto.randomUUID():Date.now().toString(36)+'_'+Math.random().toString(36).slice(2);
 function date(v){if(!v)return null;return new Date(String(v).replace(' ','T').replace(/(\.\d{3})\d+/,'$1')+(/(?:Z|[+-]\d\d:\d\d)$/.test(v)?'':'Z'));}
 function time(v,full=false){const d=date(v);return !d||isNaN(d)?'—':d.toLocaleString('zh-CN',full?{month:'2-digit',day:'2-digit',hour:'2-digit',minute:'2-digit',hour12:false}:{hour:'2-digit',minute:'2-digit',hour12:false});}
 function day(v){const d=date(v);return !d||isNaN(d)?'':d.toLocaleDateString('zh-CN');}
 function size(n){return n>=1048576?(n/1048576).toFixed(1)+' MB':Math.max(1,Math.round(n/1024))+' KB';}
 function toast(text,error=false){const box=$('toast');box.textContent=text;box.classList.toggle('error',error);box.hidden=false;clearTimeout(toast.timer);toast.timer=setTimeout(()=>box.hidden=true,4500);}
 async function request(url,options={}){const headers={'X-Requested-With':'XMLHttpRequest',...(options.headers||{})};if(options.method&&options.method!=='GET')headers['X-CSRF-Token']=state.csrf;
  if(options.body&&!(options.body instanceof FormData)){headers['Content-Type']='application/json';options.body=JSON.stringify(options.body);}
  const controller=new AbortController(),timer=setTimeout(()=>controller.abort(),options.timeout||15000);
  try{const r=await fetch(url,{credentials:'same-origin',cache:'no-store',...options,headers,signal:controller.signal});const b=await r.json().catch(()=>({}));if(!r.ok||b.code!==0)throw Error(r.status===401?'登录已失效，请重新登录。':b.msg||'服务暂不可用，请重试。');return b.data;}
  catch(e){if(e.name==='AbortError')throw Error('请求超时，请稍后重试。');throw e;}finally{clearTimeout(timer);}
 }
 const call=(path,options)=>request(api+path,options);
 function currentPath(suffix){if(!state.selected)throw Error('请先选择会话');return '/conversations/'+state.selected.id+suffix;}
 function mine(){return state.selected&&state.selected.status==='open'&&state.selected.mine;}
 function dirty(){return $('messageInput').value.trim()||state.files.length;}
 function confirmLeave(){return !dirty()||window.confirm('有尚未发送的消息或附件，是否保留原会话草稿并切换？附件稍后可在原会话重新选择。');}
 // Text drafts are local to this open page, not shared through browser storage.
 const localDrafts=new Map();
 function saveLocal(){if(state.selected)localDrafts.set(state.selected.id,{text:$('messageInput').value,files:state.files.slice(),draft:state.draft,request:state.request});}
 function composer(){const enabled=mine()&&!state.busy&&!state.aiBusy&&!state.uploading;
  $('messageInput').disabled=!mine()||state.busy;
  $('messageInput').placeholder=mine()?'输入回复，让每一次关怀及时抵达…':state.selected&&state.selected.status==='closed'?'会话已结束，可通过右上角重新开启。':'请先点击右上角“人工接管”，再发送回复。';
  $('sendBtn').disabled=!enabled||(!$('messageInput').value.trim()&&!state.files.length);
  $('sendBtn').innerHTML=icon('send')+(state.busy?'发送中…':'发送');
  $('imageBtn').disabled=$('fileBtn').disabled=!enabled;
  document.querySelectorAll('[data-draft]').forEach(b=>b.disabled=!enabled);
  $('charCount').textContent=$('messageInput').value.length+' / 4000';
  $('draftNote').hidden=!state.draft;if(state.draft)$('draftNote').innerHTML=`<span>${esc(state.draft.safetyNote)}</span><button type="button" id="discardDraft">移除草稿标记</button>`;
  $('pendingFiles').innerHTML=state.files.map(f=>`<div class="pending-file"><span title="${esc(f.name)}">${esc(f.name)}</span><small>${size(f.size)}</small><button type="button" data-remove-file="${esc(f.id)}" aria-label="移除${esc(f.name)}">×</button></div>`).join('');
 }
 function heading(){const c=state.selected;if(!c)return;$('chatIdentity').innerHTML=`<span class="avatar">${esc((c.name||'职').slice(0,1))}</span><div><strong>${esc(c.name)}</strong><div class="identity-code">工号 ${esc(c.code)} <span> · ${c.status==='closed'?'已结束':'进行中'}</span></div></div>`;
  $('takeoverBtn').disabled=c.mine||c.status==='closed';$('takeoverBtn').textContent=c.mine?'由我服务':c.assignee?'同事处理中':'人工接管';
  $('ownershipBar').classList.toggle('owned',!!c.mine);$('ownershipBar').innerHTML=icon(c.mine?'check':'person')+(c.status==='closed'?'本次服务已结束，完整记录仍可查看。':c.mine?'你已接管，回复由你确认后发送。':c.assignee?'已有同事接管，此处可查看记录。':'等待人工接管。AI仅辅助起草，不会自动发送。');
  document.querySelector('[data-action="release"]').disabled=!c.mine||c.status==='closed';document.querySelector('[data-action="close"]').disabled=!c.mine||c.status==='closed';document.querySelector('[data-action="reopen"]').disabled=c.status!=='closed';composer();
 }
 function renderList(){document.querySelectorAll('[data-filter]').forEach(b=>b.classList.toggle('active',b.dataset.filter===state.filter));$('conversationTotal').textContent=state.total;
  $('pageLabel').textContent=`第 ${state.page} 页 · 共 ${state.total} 位职工`;$('previousPage').disabled=state.page<=1;$('nextPage').disabled=state.page*30>=state.total;
  $('conversationList').innerHTML=state.items.length?state.items.map(c=>`<button class="conversation ${state.selected&&state.selected.id===c.id?'selected':''}" data-conversation="${esc(c.id)}" aria-label="${esc(c.name)}，${c.unread}条未读"><span class="avatar">${esc((c.name||'职').slice(0,1))}</span><span class="item-text"><span class="item-title"><strong>${esc(c.name)}</strong>${c.awaitingReply?'<span class="pending-dot" title="待回复"></span>':''}<span class="item-time">${time(c.updatedAt)}</span></span><span class="item-title"><small>${esc(c.code)}${c.mine?' · 由我服务':''}</small></span><span class="item-preview">${esc(c.preview||'尚无消息，等待开始沟通')}</span></span>${c.unread?`<span class="unread-badge">${Math.min(c.unread,99)}${c.unread>99?'+':''}</span>`:''}</button>`).join(''):`<div class="empty compact">${state.search?'没有匹配的会话，请调整搜索条件。':state.filter==='pending'?'当前没有待回复会话。':state.filter==='unread'?'当前没有未读消息。':'这里还没有会话。'}<br><button class="text-button" data-new>查找职工，发起会话</button></div>`;
 }
 async function refreshList(){const token=++state.listEpoch;const result=await call('/conversations?'+new URLSearchParams({page:state.page,limit:30,filter:state.filter,search:state.search}));if(token!==state.listEpoch)return;state.items=result.items;state.total=result.total;if(state.page>1&&!result.items.length&&result.total){state.page=1;return refreshList();}renderList();}
 function connection(ok){state.connected=ok;$('syncState').textContent=ok?'会话实时同步':'连接中断 · 正在重连';$('syncState').classList.toggle('error',!ok);}
 async function refreshContext(){const c=await call('/context');state.user=c.user;$('pendingCount').textContent=c.counts.pending||0;$('unreadCount').textContent=c.counts.unread||0;
  if(state.seenCounts!==null&&Number(c.counts.unread)>state.seenCounts&&document.hidden&&'Notification'in window&&Notification.permission==='granted')new Notification('矿安智康 · 新职工消息',{body:'有新的职工咨询，请进入沟通与服务中心查看。'});
  state.seenCounts=Number(c.counts.unread)||0;document.title=(state.seenCounts?`(${state.seenCounts}) `:'')+'职工会话 · 矿安智康';return c;
 }
 function attachmentUrl(cid,fid){return api+'/conversations/'+encodeURIComponent(cid)+'/attachments/'+encodeURIComponent(fid);}
 function renderMessages(){const c=state.selected;let lastDay='';$('messages').innerHTML=state.messages.map(m=>{let prefix='';if(day(m.createdAt)!==lastDay){lastDay=day(m.createdAt);prefix=`<div class="date-separator">${esc(lastDay)}</div>`;}
   if(m.role==='system')return prefix+`<div class="system-message"><span>${esc(m.text)}</span></div>`;
   const outgoing=m.role!=='employee';const role=roleNames[m.role]||'服务人员';const label=outgoing?(m.senderName||(m.role==='auto_reply'?'自动服务助手':role)):c.name;
   const attachments=(m.attachments||[]).map(f=>{const url=attachmentUrl(c.id,f.id);return f.mime.startsWith('image/')?`<div class="attachment"><img src="${esc(url)}" alt="${esc(f.name)}" loading="lazy" data-preview-image="${esc(f.id)}"></div>`:`<a class="attachment" href="${esc(url)}" target="_blank" rel="noopener">${icon('file')}<span>${esc(f.name)}<small>${size(f.size)} · 下载附件</small></span></a>`;}).join('');
   return prefix+`<article class="message ${outgoing?'outgoing':'employee'}" data-message="${esc(m.id)}"><span class="avatar">${outgoing?icon(m.role==='ai_assisted'?'spark':'person'):esc((c.name||'职').slice(0,1))}</span><div class="message-content"><div class="message-label">${esc(label)}${m.role==='ai_assisted'?'<span class="ai-label">AI辅助 · 人工确认</span>':''}</div><div class="bubble">${esc(m.text)}${attachments}</div><div class="message-time">${time(m.createdAt)}</div></div></article>`;
  }).join('')||'<div class="empty">会话已建立，发送一句问候，开启健康沟通。</div>';
 }
 async function markRead(seq){if(!state.selected||!seq||document.hidden)return;const cid=state.selected.id;await call('/conversations/'+cid+'/read',{method:'POST',body:{seq}});if(state.selected&&state.selected.id===cid){state.selected.unread=0;const item=state.items.find(x=>x.id===cid);if(item)item.unread=0;renderList();}}
 function atBottom(){const s=$('messageScroller');return s.scrollHeight-s.scrollTop-s.clientHeight<90;}
 function bottom(){const s=$('messageScroller');s.scrollTop=s.scrollHeight;$('newMessagesBtn').hidden=true;}
 async function loadMessages(initial=false){if(!state.selected||state.loadingMessages)return;const cid=state.selected.id,epoch=state.epoch,last=initial?0:state.messages.length?Number(state.messages[state.messages.length-1].seq):0;
  state.loadingMessages=true;try{const result=await call('/conversations/'+cid+'/messages'+(last?'?after='+last:''));if(epoch!==state.epoch)return;
   const near=initial||atBottom();state.selected=result.conversation;heading();
   if(initial){state.messages=result.items;$('loadEarlier').hidden=!result.hasEarlier;}
   else{const ids=new Set(state.messages.map(m=>m.id));state.messages.push(...result.items.filter(m=>!ids.has(m.id)));}
   if(initial||result.items.length){renderMessages();if(near)bottom();else $('newMessagesBtn').hidden=false;}
   if(near)await markRead(result.lastReturnedSeq);
   if(result.hasMore)setTimeout(()=>loadMessages(false).catch(()=>{}),10);
  }finally{state.loadingMessages=false;}
 }
 async function openConversation(c){if(state.selected&&state.selected.id===c.id)return;if(!confirmLeave())return;saveLocal();const epoch=++state.epoch;state.selected=c;state.messages=[];state.health=null;state.loadingMessages=false;
  const saved=localDrafts.get(c.id);$('messageInput').value=saved?saved.text:'';state.files=saved?saved.files.slice():[];state.draft=saved?saved.draft:null;state.request=saved?saved.request:null;
  $('chatEmpty').hidden=true;$('chatActive').hidden=false;$('messages').innerHTML='<div class="empty">正在加载会话…</div>';heading();renderList();$('healthContent').innerHTML='<div class="empty">正在读取授权档案…</div>';
  try{await loadMessages(true);const h=await call('/conversations/'+c.id+'/health');if(epoch!==state.epoch)return;state.health=h;renderHealth();}catch(e){if(epoch===state.epoch)toast(e.message,true);}
 }
 const pill=r=>`<span class="pill ${Object.hasOwnProperty.call(riskNames,r)?esc(r):'unknown'}">${esc(riskNames[r]||'未评估')}</span>`;
 function renderHealth(){const root=$('healthContent'),h=state.health,c=state.selected;if(!c)return;
  document.querySelectorAll('[data-health]').forEach(b=>b.classList.toggle('active',b.dataset.health===state.healthTab));
  if(state.healthTab==='events'){root.innerHTML=`<h3>人工服务记录</h3>${h&&h.serviceEvents.length?h.serviceEvents.map(e=>`<div class="service-record"><p>${esc(e.text)}</p><span class="record-time">${time(e.time,true)}</span></div>`).join(''):'<div class="empty">暂无人工服务操作。</div>'}`;return;}
  if(state.healthTab==='summary'){root.innerHTML='<div class="empty">正在统计会话…</div>';const epoch=state.epoch;call('/conversations/'+c.id+'/summary').then(s=>{if(state.epoch!==epoch||state.healthTab!=='summary')return;root.innerHTML=`<h3>会话概况</h3><div class="section-heading">消息数量 <small>以当前会话记录统计</small></div><div class="profile-grid"><div class="profile-tile">职工消息<strong>${s.employeeMessages} 条</strong></div><div class="profile-tile">人工回复<strong>${s.staffMessages} 条</strong></div></div><div class="section-heading">服务状态</div><div class="health-note">${c.status==='closed'?'本次会话已结束。':c.awaitingReply?'职工有消息等待回复。':'当前没有待回复消息。'}<br>最早消息：${time(s.firstAt,true)}<br>最近消息：${time(s.latestAt,true)}</div><div class="notice-line">这里展示真实服务概况，未生成AI会话总结。</div>`;}).catch(e=>{if(state.epoch===epoch)root.innerHTML=`<div class="error-inline">${esc(e.message)}</div>`;});return;}
  if(!h){root.innerHTML='<div class="empty">健康资料尚未加载，请重新选择会话。</div>';return;}const p=h.profile,report=h.latestReport;
  const values=h.indicators.filter(i=>/(血压|收缩|舒张|血糖|葡萄糖|BMI|体质|肺|听力)/i.test(i.name||'')).slice(0,6);
  root.innerHTML=`<div class="profile-name"><span class="avatar">${esc((p.name||'职').slice(0,1))}</span><div><strong>${esc(p.name)}</strong><p class="profile-sub">工号 ${esc(p.code)} · ${esc(p.sex||'性别未填写')} ${p.age?esc(p.age)+'岁':''}</p></div></div><a class="button small" href="${esc(base+'assets/health-archive/detail.html?id='+p.id)}" target="_blank" rel="noopener">${icon('file')}查看完整档案</a><div class="section-heading">职工健康档案</div><div class="profile-grid"><div class="profile-tile">当前风险等级<br>${pill(p.risk)}</div><div class="profile-tile">档案完整度<strong>${esc(p.complete)}%</strong></div><div class="profile-tile">所属班组<strong>${esc(p.team||'未填写')}</strong></div><div class="profile-tile">岗位 / 工种<strong>${esc(p.job||'未填写')}</strong></div></div><div class="section-heading">职业接触信息</div><div class="health-note">${esc(p.exposure||'暂无接触史记录')}</div><div class="section-heading">关键健康指标 <small>${report?(report.date||'日期未核定'):'暂无报告'}</small></div>${report?`<div class="profile-sub">${esc(statusNames[report.status]||report.status)} · ${esc(report.institution||'机构未填写')}</div>`:''}<div class="indicator-grid">${values.length?values.map(i=>`<div class="indicator ${i.flag?'flag':''}">${esc(i.name)}<strong>${esc(i.value||'未记录')} <small>${esc(i.unit||'')}</small></strong><small>${i.flag?'原报告标记：'+esc(i.flag):'未提供异常标记'}</small></div>`).join(''):'<div class="health-note">暂无可展示的关键指标，不推断健康状况。</div>'}</div><div class="section-heading">历史评估 <small>最近 8 条</small></div>${h.assessments.length?h.assessments.map(a=>`<div class="service-record"><div class="record-title"><b>${a.type==='clinician'?'医生评估':a.type==='ai_assisted'?'AI辅助评估':'规则评估'}</b>${a.status==='confirmed'?pill(a.level):'<span class="pill">未确认</span>'}</div><span class="record-time">${time(a.time,true)}</span></div>`).join(''):'<div class="health-note">尚无风险评估记录。</div>'}<div class="section-heading">随访任务 <small>最近 8 条</small></div>${h.followups.length?h.followups.map(f=>`<div class="service-record"><div class="record-title"><b>${esc(f.note||'健康随访')}</b><span class="pill">${esc({pending:'待执行',completed:'已完成',cancelled:'已取消'}[f.status]||f.status)}</span></div><span class="record-time">计划时间 ${time(f.due,true)}</span></div>`).join(''):'<div class="health-note">当前没有关联随访任务。</div>'}`;
 }
 async function send(event){event.preventDefault();if(state.busy||state.aiBusy||state.uploading||!mine()||!dirty())return;const cid=state.selected.id;const body={text:$('messageInput').value.trim(),attachmentIds:state.files.map(f=>f.id),draftId:state.draft?state.draft.id:''};const signature=JSON.stringify(body);if(!state.request||state.request.signature!==signature)state.request={signature,id:uid()};body.requestId=state.request.id;state.busy=true;composer();
  try{await call('/conversations/'+cid+'/messages',{method:'POST',body});if(!state.selected||state.selected.id!==cid)return;$('messageInput').value='';state.files=[];state.draft=null;state.request=null;localDrafts.delete(cid);await loadMessages(false);bottom();await Promise.all([refreshContext(),refreshList()]);}
  catch(e){toast(e.message,true);}finally{state.busy=false;composer();}
 }
 async function act(action){if(!state.selected||state.busy||state.aiBusy||state.uploading)return;if(action==='close'&&!window.confirm('确认结束本次服务？历史消息会保留，职工再次咨询时会重新开启。'))return;state.busy=true;composer();try{state.selected=await call(currentPath('/actions'),{method:'POST',body:{action,version:state.selected.version}});document.querySelector('.more-menu').open=false;heading();await loadMessages(false);state.health=await call(currentPath('/health'));renderHealth();await refreshList();toast({takeover:'已接管，您现在可以回复职工。',release:'已释放会话。',close:'本次服务已结束。',reopen:'会话已重新开启。'}[action]);}catch(e){toast(e.message,true);await loadMessages(false).catch(()=>{});}finally{state.busy=false;composer();}}
 async function draft(purpose){if(!mine()||state.busy||state.aiBusy||state.uploading)return;if($('messageInput').value.trim()&&!window.confirm('生成AI建议将替换输入框中的内容，是否继续？'))return;const cid=state.selected.id,epoch=state.epoch;state.aiBusy=true;composer();$('draftNote').hidden=false;$('draftNote').textContent='正在根据当前咨询生成回复草稿，请稍候…';
  try{const d=await call('/conversations/'+cid+'/drafts',{method:'POST',body:{purpose},timeout:75000});if(state.epoch!==epoch)return;state.draft=d;state.request=null;$('messageInput').value=d.text;toast('草稿已生成，核对后点击发送。');}catch(e){toast(e.message,true);}finally{state.aiBusy=false;composer();}
 }
 async function upload(){const input=$('attachmentInput'),files=Array.from(input.files||[]);input.value='';if(!files.length||!mine()||state.uploading)return;if(files.length+state.files.length>5){toast('每条消息最多5个附件。',true);return;}state.uploading=true;composer();const cid=state.selected.id;try{for(const f of files){if(f.size>20971520||!f.size)throw Error('附件不能为空，单个文件最大20 MB。');const data=new FormData();data.append('file',f);const uploaded=await call('/conversations/'+cid+'/attachments',{method:'POST',body:data,timeout:45000});if(state.selected.id===cid)state.files.push(uploaded);}toast('附件已上传，点击发送后职工才能查看。');}catch(e){toast(e.message,true);}finally{state.uploading=false;state.request=null;composer();saveLocal();}}
 async function removeFile(id){if(state.uploading||state.busy)return;state.uploading=true;composer();try{await call(currentPath('/attachments/'+id),{method:'DELETE'});state.files=state.files.filter(f=>f.id!==id);state.request=null;}catch(e){toast(e.message,true);}finally{state.uploading=false;composer();saveLocal();}}
 async function findEmployees(){const text=$('employeeSearch').value.trim();$('employeeResults').innerHTML='<div class="empty">正在查找职工…</div>';try{const result=await request(base+'ehr/api/records?'+new URLSearchParams({search:text,limit:20,page:1}));$('employeeResults').innerHTML=result.items.length?result.items.map(p=>`<button class="employee-option" data-record="${esc(p.id)}"><span class="avatar">${esc((p.name||'职').slice(0,1))}</span><span>${esc(p.name)}<small>工号 ${esc(p.code)} · ${esc(p.team||'班组未填写')}</small></span></button>`).join(''):'<div class="empty">没有找到授权职工档案。</div>';}catch(e){$('employeeResults').innerHTML=`<div class="error-inline">${esc(e.message)}</div>`;}}
 function newDialog(){if(state.busy||state.aiBusy||state.uploading)return;$('newDialog').showModal();$('employeeSearch').focus();findEmployees();}
 async function earlier(){if(!state.selected||!state.messages.length)return;const epoch=state.epoch,cid=state.selected.id,scroller=$('messageScroller'),oldHeight=scroller.scrollHeight;$('loadEarlier').disabled=true;try{const r=await call('/conversations/'+cid+'/messages?before='+state.messages[0].seq);if(epoch!==state.epoch)return;const ids=new Set(state.messages.map(m=>m.id));state.messages=[...r.items.filter(m=>!ids.has(m.id)),...state.messages];renderMessages();$('loadEarlier').hidden=!r.hasEarlier;scroller.scrollTop+=scroller.scrollHeight-oldHeight;}catch(e){toast(e.message,true);}finally{$('loadEarlier').disabled=false;}}
 async function poll(){try{if(!state.busy&&!state.uploading){if(document.hidden)await refreshContext();else await Promise.all([refreshList(),refreshContext(),loadMessages(false)]);connection(true);}}catch(e){connection(false);}finally{setTimeout(poll,document.hidden?10000:3000);}}
 $('newConversation').onclick=$('emptyCreate').onclick=newDialog;$('refreshBtn').onclick=()=>Promise.all([refreshList(),refreshContext(),loadMessages(false)]).then(()=>connection(true)).catch(e=>toast(e.message,true));
 $('searchForm').onsubmit=e=>{e.preventDefault();state.search=$('conversationSearch').value.trim();state.page=1;refreshList().catch(e=>toast(e.message,true));};
 document.addEventListener('click',async e=>{const b=e.target.closest('button');if(!b)return;
  if(b.dataset.filter){state.filter=b.dataset.filter;state.page=1;refreshList().catch(e=>toast(e.message,true));}
  if(b.dataset.conversation){if(state.busy||state.aiBusy||state.uploading)return;const c=state.items.find(c=>c.id===b.dataset.conversation);if(c)openConversation(c);}
  if(b.hasAttribute('data-new'))newDialog();if(b.dataset.draft)draft(b.dataset.draft);if(b.dataset.action)act(b.dataset.action);if(b.dataset.removeFile)removeFile(b.dataset.removeFile);
  if(b.dataset.health){state.healthTab=b.dataset.health;renderHealth();}
  if(b.id==='discardDraft'){state.draft=null;state.request=null;composer();}
  if(b.dataset.record){b.disabled=true;try{const c=await call('/conversations',{method:'POST',body:{recordId:b.dataset.record}});$('newDialog').close();state.filter='open';state.page=1;await refreshList();await openConversation(c);}catch(e){toast(e.message,true);}finally{b.disabled=false;}}
 });
 $('previousPage').onclick=()=>{state.page--;refreshList().catch(e=>toast(e.message,true));};$('nextPage').onclick=()=>{state.page++;refreshList().catch(e=>toast(e.message,true));};
 $('employeeSearchForm').onsubmit=e=>{e.preventDefault();findEmployees();};$('takeoverBtn').onclick=()=>act('takeover');$('sendForm').onsubmit=send;
 $('messageInput').oninput=()=>{state.request=null;composer();saveLocal();};$('messageInput').addEventListener('keydown',e=>{if(e.key==='Enter'&&!e.shiftKey&&!e.isComposing){e.preventDefault();$('sendForm').requestSubmit();}});
 $('attachmentInput').onchange=upload;$('imageBtn').onclick=()=>{$('attachmentInput').accept='.jpg,.jpeg,.png';$('attachmentInput').click();};$('fileBtn').onclick=()=>{$('attachmentInput').accept='.pdf,.jpg,.jpeg,.png,.txt';$('attachmentInput').click();};
 $('loadEarlier').onclick=earlier;$('newMessagesBtn').onclick=()=>{bottom();if(state.messages.length)markRead(Number(state.messages[state.messages.length-1].seq)).catch(()=>{});};
 $('messageScroller').addEventListener('scroll',()=>{if(atBottom()&&!$('newMessagesBtn').hidden){$('newMessagesBtn').hidden=true;if(state.messages.length)markRead(Number(state.messages[state.messages.length-1].seq)).catch(()=>{});}});
 $('messages').addEventListener('click',e=>{const img=e.target.closest('[data-preview-image]');if(img&&state.selected){$('imagePreview').src=attachmentUrl(state.selected.id,img.dataset.previewImage);$('imageDialog').showModal();}});
 $('profileToggle').onclick=()=>$('healthPanel').classList.toggle('profile-open');$('closeProfile').onclick=()=>$('healthPanel').classList.remove('profile-open');
 $('notifyBtn').onclick=async()=>{if(!('Notification'in window)){toast('当前浏览器不支持系统通知，页面内未读提示仍可使用。');return;}try{const p=await Notification.requestPermission();toast(p==='granted'?'消息提醒已开启，页面在后台时将提示新咨询。':'未开启系统通知，仍可查看页面未读数。');}catch(e){toast('通知需要浏览器许可或安全连接，页面未读数正常使用。');}};
 window.addEventListener('beforeunload',e=>{if(dirty()||state.busy||state.uploading){e.preventDefault();e.returnValue='';}});
 async function init(){try{const c=await request(base+'ehr/api/context');state.csrf=c.csrfToken;await Promise.all([refreshList(),refreshContext()]);connection(true);const cid=new URLSearchParams(location.search).get('conversationId');if(cid){const conversation=await call('/conversations/'+encodeURIComponent(cid)+'/messages');await openConversation(conversation.conversation);}}catch(e){$('conversationList').innerHTML=`<div class="error-inline">${esc(e.message)}<br><button class="text-button" id="retryInit">重新连接</button></div>`;connection(false);$('retryInit').onclick=init;return;}setTimeout(poll,3000);}
 init();
})();
