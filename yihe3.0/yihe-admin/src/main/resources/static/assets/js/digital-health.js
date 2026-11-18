/* Digital health workbench: authenticated Responses tools; writes require explicit confirmation. */
(function (window) {
    'use strict';
    var PAGE_PATH = '/assets/digital-health/index.html';
    var PAGE = PAGE_PATH + '?v=20261117-dock2';
    var ASSET = '/assets/images/';
    var profile = { code: '', name: '健康管理师', gender: 0 };
    var sessions = [];
    var activeId;
    var uiListeners = [];
    var chats = [];
    var floating;
    var doctorModule;
    var counter = 0;
    var csrfPromise, agentReady, pending = false, removing = false, selectedRecord = '';
    var toolLabels = { search_employees:'查找职工', get_employee_record:'读取健康档案', list_exam_reports:'查询体检报告', get_exam_report:'读取报告及证据', get_health_statistics:'查询企业统计', find_abnormal_results:'查询异常指标', prepare_employee_delete:'生成删除预览', prepare_employee_update:'生成修改预览' };
    var commands = [
        { key: 'report', name: '体检报告', icon: '▤', prompt: '查看当前服务对象的体检报告；未选择职工时，请先让我提供姓名或工号' },
        { key: 'scan', name: '异常指标', icon: '↗', prompt: '查询已确认报告中的异常指标，汇总需要医生关注的线索，标明依据和范围' },
        { key: 'urgent', name: '风险关注', icon: '♧', prompt: '查看当前单位已确认的高风险和极高风险人数，未评估人数单独说明' },
        { key: 'overview', name: '数据概览', icon: '▦', prompt: '查看职业健康数据概览' }
    ];
    var starters = [
        { icon: '▤', title: '读懂体检报告', text: '读取报告原文，引用证据页码', prompt: '查看并分析当前选中职工的体检报告；没有选中则请让我提供姓名或工号' },
        { icon: '♧', title: '查找职工档案', text: '按姓名、工号查询真实健康档案', prompt: '我想查询职工档案，告诉我需要提供什么信息' },
        { icon: '♡', title: '异常指标分析', text: '汇总报告中的异常标记与关注线索', prompt: '分析已确认报告中的异常指标分布，注明数据范围；不要把单次异常确诊为基础病' },
        { icon: '✧', title: '企业健康概况', text: '汇总档案进度与已确认风险', prompt: '查询当前单位健康档案总量、审核归档情况与已确认风险分布；不要虚构随访数据' }
    ];
    function escape(value) { var n = document.createElement('span'); n.textContent = value == null ? '' : String(value); return n.innerHTML; }
    function avatar(gender) { return Number(gender) === 1 ? ASSET + 'digital-doctor-male.png' : Number(gender) === 2 ? ASSET + 'digital-doctor-female.png' : ASSET + 'kuangan-zhikang-mark.svg'; }
    function identity() { return { code: profile.code, name: profile.name, gender: profile.gender, avatar: avatar(profile.gender) }; }
    function timeLabel() { return new Date().toLocaleTimeString('zh-CN', { timeZone: 'Asia/Shanghai', hour: '2-digit', minute: '2-digit', hour12: false }); }
    function sessionTime(value) { var raw=String(value||'');if(!/^\d{4}-\d\d-\d\d[ T]/.test(raw))return raw;var date=new Date(raw.replace(' ','T').replace(/(\.\d{3})\d+/,'$1')+'Z');return isNaN(date.getTime())?raw.slice(5,16):date.toLocaleString('zh-CN',{timeZone:'Asia/Shanghai',month:'2-digit',day:'2-digit',hour:'2-digit',minute:'2-digit',hour12:false}); }
    function greeting() { var hour = Number(new Date().toLocaleString('en-US', { timeZone: 'Asia/Shanghai', hour: 'numeric', hour12: false })); return hour < 12 ? '上午好' : hour < 18 ? '下午好' : '晚上好'; }
    function welcome() { return greeting() + '，' + profile.name + '。\n想了解哪位职工的健康情况？告诉我姓名或工号，我来帮你查档案、看报告。'; }
    function uid() { return window.crypto.randomUUID ? window.crypto.randomUUID() : 'req_' + Date.now() + '_' + Math.random().toString(36).slice(2); }
    async function api(path, method, body) {
        if (!csrfPromise) { csrfPromise = fetch('/ehr/api/context', { credentials:'same-origin' }).then(function(r){return r.json();}).then(function(j){if(j.code!==0)throw Error(j.msg||'请重新登录');return j.data.csrfToken;}).catch(function(e){csrfPromise=null;throw e;}); }
        var token=await csrfPromise, options={ method:method||'GET', credentials:'same-origin', headers:{'X-CSRF-Token':token} };
        if(body!==undefined){options.headers['Content-Type']='application/json';options.body=JSON.stringify(body);}
        var r=await fetch('/ehr/api'+path,options),j;try{j=await r.json();}catch(e){throw Error('服务未返回有效结果，请检查登录状态');}
        if(!r.ok||j.code!==0){if(r.status===401||r.status===403)csrfPromise=null;throw Error(j.msg||'请求失败（'+r.status+'）');}return j.data;
    }
    async function remoteSession(session){if(session.serverId)return session.serverId;if(!session.creating)session.creating=api('/agent/conversations','POST',{}).then(function(c){session.serverId=c.id;return c.id;});return session.creating;}
    async function loadConversation(session){if(!session.serverId)return;var d=await api('/agent/conversations/'+session.serverId);if(current()!==session)return;session.messages=d.messages||[];session.actions=d.actions||[];session.lastResult=d.lastResult||null;chats.forEach(function(c){c.setMessages(session.messages,session);});renderResults(session);notify();}
    function bootAgent(){if(agentReady)return agentReady;agentReady=Promise.all([api('/agent/context'),api('/agent/conversations')]).then(async function(values){
        var c=values[0],saved=values[1];
        if(saved.length&&current()&&!current().messages.length){sessions=saved.map(function(s){return {id:s.id,serverId:s.id,title:s.title,time:s.updatedAt,messages:[],actions:[]};});activeId=sessions[0].id;await loadConversation(sessions[0]);}
        document.querySelectorAll('.dh-mode-tag,.dh-panel-banner>span').forEach(function(n){n.textContent=c.enabled?'● 智能分析已启用':'模型待配置';});
        document.querySelectorAll('.dh-total-preview').forEach(function(n){n.innerHTML='<span>当前服务模式</span><strong>'+(c.enabled?'业务智能体':'模型待配置')+'</strong><p>账号权限内查询 · 修改先确认</p>';});
        var s=c.statistics||{},numbers=[s.total,(s.high||0)+(s.critical||0),s.pending,s.archived];
        document.querySelectorAll('.dh-overview-grid strong').forEach(function(n,i){n.textContent=numbers[i]==null?'—':numbers[i];});
        document.querySelectorAll('.dh-overview-grid>div>span').forEach(function(n,i){n.textContent=['在档职工','已确认风险关注','待审核档案','已归档档案'][i];});
        document.querySelectorAll('.dh-small-preview').forEach(function(n){n.textContent=c.dataMode==='synthetic'?'隔离测试':'当前单位';});
        return c;
    }).catch(function(e){toast(e.message);agentReady=null;throw e;});return agentReady;}
    function renderResults(session){chats.forEach(function(chat){chat.renderResults(session);});}
    function renderResultHost(host,result,actions,session){
        result=result||{};actions=actions||[];
        var seen={},links=[];
        (result.sources||[]).forEach(function(s){var key=(s.kind||'record')+':'+(s.reportId||s.recordId)+':'+(s.revision||'');if(!seen[key]){seen[key]=true;links.push('<a class="dh-source-link" target="_blank" rel="noopener" href="/assets/health-archive/detail.html?id='+encodeURIComponent(s.recordId)+'"><span class="dh-source-type">'+(s.reportId?'报告':'档案')+'</span><span class="dh-source-title">'+escape(s.label)+'</span><span aria-hidden="true">↗</span></a>');}(s.files||[]).forEach(function(f){var key='file:'+f.id;if(seen[key])return;seen[key]=true;links.push('<a class="dh-source-link" href="/ehr/api/files/'+encodeURIComponent(f.id)+'"><span class="dh-source-type">原件</span><span class="dh-source-title">体检报告原件 · '+escape((f.name||'文件').split('.').pop().toUpperCase())+'</span><span aria-hidden="true">↓</span></a>');});});
        host.innerHTML=(result.tools&&result.tools.length?'<details class="dh-tool-trace"><summary>查询完成 · '+result.tools.length+' 次查询 · '+((result.elapsedMs||0)/1000).toFixed(1)+' 秒</summary><div class="dh-tool-list">'+result.tools.map(function(t){return '<span>'+escape(toolLabels[t.name]||t.name)+' · '+(t.status==='ok'?'完成':'未完成')+'</span>';}).join('')+'</div></details>':'')+(links.length?'<details class="dh-source-details"><summary>参考资料 <span>'+links.length+'</span></summary><div class="dh-source-list">'+links.join('')+'</div></details>':'')+actions.map(function(a){return '<article class="dh-action-card"><strong>'+(a.type==='delete_employee'?'待确认：删除职工档案':'待确认：修改职工资料')+'</strong><p>'+escape(a.target.name)+' · 工号 '+escape(a.target.code)+(a.type==='delete_employee'?'；关联 '+a.target.reportCount+' 份报告（'+a.target.reportVersions+' 个版本）也将删除。':'；核对变更后执行。')+'</p><button data-agent-confirm="'+escape(a.id)+'">查看并确认</button><button data-agent-cancel="'+escape(a.id)+'">取消</button></article>';}).join('');
        host.hidden=!host.innerHTML;
        host.onclick=async function(event){var b=event.target.closest('button');if(!b)return;var aid=b.dataset.agentConfirm||b.dataset.agentCancel,a=actions.find(function(x){return x.id===aid;});if(!a)return;
            if(b.dataset.agentCancel){b.disabled=true;try{await api('/agent/actions/'+aid+'/resolve','POST',{confirm:false});session.actions=actions.filter(function(x){return x.id!==aid;});renderResults(session);toast('已取消，未修改档案');}catch(e){toast(e.message);b.disabled=false;}}else confirmAction(a,session);
        };
    }
    function confirmAction(action,session){var old=document.getElementById('dh-action-dialog');if(old)old.remove();var d=document.createElement('dialog');d.id='dh-action-dialog';d.className='dh-action-dialog';
        var labels={name:'姓名',code:'工号',sex:'性别',birth:'出生日期',phone:'手机号',org:'单位',team:'班组',job:'岗位',exposure:'职业暴露史'},diff=action.diff||(action.changes||{}).diff||{};
        d.innerHTML='<form><h2>'+(action.type==='delete_employee'?'确认删除职工档案':'确认修改职工资料')+'</h2><p><strong>'+escape(action.target.name)+'</strong> · 工号 '+escape(action.target.code)+'</p>'+(action.type==='delete_employee'?'<p class="dh-danger-note">将删除该职工在新健康档案模块中的档案及 '+action.target.reportCount+' 份关联体检报告（'+action.target.reportVersions+' 个版本）。原系统职工资料保留。</p><p>原因：'+escape(action.changes.reason)+'</p>':Object.keys(diff).map(function(k){return '<p>'+escape(labels[k]||k)+'：'+escape(diff[k].from||'未填写')+' → <strong>'+escape(diff[k].to)+'</strong></p>';}).join(''))+'<label>输入工号以确认<input name="employeeCode" autocomplete="off" required placeholder="请手动输入工号"/></label><p class="dh-action-error" role="alert"></p><div><button type="button" class="dh-dialog-close">返回</button><button type="submit" class="dh-dialog-execute">确认执行</button></div><small>预览有效期10分钟；资料或报告变化时需重新生成。</small></form>';
        document.body.appendChild(d);d.querySelector('.dh-dialog-close').onclick=function(){d.close();d.remove();};d.querySelector('form').onsubmit=async function(e){e.preventDefault();var b=d.querySelector('[type=submit]');b.disabled=true;try{var r=await api('/agent/actions/'+action.id+'/resolve','POST',{confirm:true,employeeCode:d.querySelector('input').value.trim()});session.actions=session.actions.filter(function(x){return x.id!==action.id;});addMessage('ai',r.text);renderResults(session);d.close();d.remove();}catch(err){d.querySelector('.dh-action-error').textContent=err.message;b.disabled=false;}};d.showModal();
    }
    function current() { return sessions.filter(function (s) { return s.id === activeId; })[0]; }
    function notify() { uiListeners.forEach(function (listener) { listener(); }); }
    function deleteSession(id) {
        if(pending||removing){toast('正在处理当前请求，请完成后再删除对话');return;}
        var session=sessions.find(function(s){return s.id===id;});if(!session)return;
        var old=document.getElementById('dh-delete-session-dialog');if(old)old.remove();
        var dialog=document.createElement('dialog');dialog.id='dh-delete-session-dialog';dialog.className='dh-action-dialog';
        dialog.innerHTML='<form><h2>删除这条对话？</h2><p class="dh-delete-session-title"></p><p>对话及聊天记录将永久删除，无法恢复。职工档案、体检报告及已执行的业务操作不受影响。</p><p class="dh-action-error" role="alert"></p><div><button type="button" class="dh-dialog-close">取消</button><button type="submit" class="dh-dialog-execute">删除对话</button></div></form>';
        dialog.querySelector('.dh-delete-session-title').textContent=session.title;
        dialog.querySelector('.dh-dialog-close').onclick=function(){dialog.close();dialog.remove();};
        dialog.querySelector('form').onsubmit=async function(e){e.preventDefault();if(pending||removing)return;removing=true;var buttons=dialog.querySelectorAll('button');buttons.forEach(function(b){b.disabled=true;});
            try{if(session.serverId)await api('/agent/conversations/'+encodeURIComponent(session.serverId),'DELETE');
                sessions=sessions.filter(function(s){return s.id!==id;});
                if(activeId===id){if(sessions.length){activeId=sessions[0].id;chats.forEach(function(c){c.setMessages(current().messages);c.clearDraft();});renderResults(current());notify();await loadConversation(current());}else{removing=false;newSession();chats.forEach(function(c){c.clearDraft();});}}else notify();
                dialog.close();dialog.remove();toast('对话已删除');
            }catch(err){dialog.querySelector('.dh-action-error').textContent=err.message;buttons.forEach(function(b){b.disabled=false;});}
            finally{removing=false;}
        };
        document.body.appendChild(dialog);dialog.showModal();dialog.querySelector('.dh-dialog-close').focus();
    }
    function newSession() {
        if(pending||removing){toast('正在处理当前请求，请完成后新建对话');return current();}
        var s = { id: String(Date.now()) + '-' + (++counter), title: '新的健康对话', time: timeLabel(), messages: [], actions:[] };
        sessions.unshift(s); activeId = s.id;
        chats.forEach(function (chat) { if (chat.clearMessages) { chat.clearMessages(); } });
        renderResults(s);notify(); return s;
    }
    function ensureSession() { if (!current()) { newSession(); } }
    function addMessage(role, text, source) {
        ensureSession();
        var session = current();
        session.messages.push({ role: role, text: text });
        if (role === 'ai' && floating && floating.notifyReply) { floating.notifyReply(text); }
        if (role === 'user' && session.title === '新的健康对话') { session.title = text.slice(0, 18); }
        chats.forEach(function (chat) { if (chat !== source && chat.addMessage) { chat.addMessage({ role: role, text: text }); } });
        notify();
    }
    function markdown(text) {
        if (!window.marked) return escape(text).replace(/\n/g, '<br/>');
        var template=document.createElement('template');
        template.innerHTML=window.marked(String(text||''),{sanitize:true,gfm:true,breaks:true,tables:true});
        var allowed=['P','BR','STRONG','EM','B','I','UL','OL','LI','H1','H2','H3','H4','H5','H6','BLOCKQUOTE','PRE','CODE','TABLE','THEAD','TBODY','TR','TH','TD','HR','A','DEL'];
        Array.from(template.content.querySelectorAll('*')).forEach(function(n){
            if(allowed.indexOf(n.tagName)<0){n.replaceWith(document.createTextNode(n.textContent));return;}
            var href=n.tagName==='A'?n.getAttribute('href'):null;
            Array.from(n.attributes).forEach(function(a){n.removeAttribute(a.name);});
            if(href){try{var u=new URL(href,location.origin);if(u.protocol==='https:'||u.protocol==='http:'){n.href=u.href;n.target='_blank';n.rel='noopener noreferrer';}}catch(ignore){}}
        });
        return template.innerHTML;
    }
    function busy(value) { pending=value;chats.forEach(function(c){c.setBusy(value);}); }
    async function sendMessage(text,source) {
        text=String(text||'').trim();if(!text||pending||removing)return false;
        if(text.length>3000){toast('消息最多3000字，请分段发送');return false;}
        busy(true);var session=current();source.clearDraft();
        try{
            await bootAgent();session=current();session.lastResult=null;renderResults(session);addMessage('user',text);var cid=await remoteSession(session);
            var r=await api('/agent/chat','POST',{conversationId:cid,requestId:uid(),message:text,recordId:selectedRecord});
            session.lastResult=r;session.actions=r.actions||[];addMessage('ai',r.text);renderResults(session);
        }catch(e){addMessage('ai','本次请求未完成：'+e.message);if(session&&session.serverId){try{var d=await api('/agent/conversations/'+session.serverId);session.actions=d.actions||[];session.lastResult=d.lastResult||null;renderResults(session);}catch(ignore){}}}
        finally{busy(false);chats.forEach(function(c){c.finishReply();});}
        return true;
    }
    function createChat(host) {
        host.innerHTML='<section class="dh-chat-shell" aria-label="健康师对话"><div class="dh-message-viewport"><div class="dh-message-scroll" role="log" aria-label="聊天记录" aria-live="polite"><div class="dh-message-list"></div><div class="dh-reply-status" role="status" hidden><span class="dh-status-dots"><i></i><i></i><i></i></span><span>正在查询与分析</span><time></time></div><div class="dh-agent-results" hidden></div></div><button class="dh-jump-latest" type="button" hidden>↓ 回到最新</button></div><div class="dh-inline-quick" hidden></div><form class="dh-compose-form"><div class="dh-compose-box"><textarea class="dh-compose-input" rows="1" maxlength="3000" aria-label="输入消息" placeholder="输入姓名、工号，或想了解的问题…"></textarea><button class="dh-send-button" type="submit" aria-label="发送消息" title="发送消息（Enter）" disabled><svg viewBox="0 0 24 24" fill="none" aria-hidden="true"><path d="M12 19V5m-6 6 6-6 6 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/></svg></button></div><div class="dh-compose-footer"><button type="button" class="dh-shortcuts-toggle" aria-expanded="false">快捷查询 <span>⌃</span></button><button type="button" class="dh-upload-shortcut" title="进入档案信息管理上传体检报告">＋ 上传报告</button><span class="dh-keyboard-hint">Enter 发送 · Shift + Enter 换行</span></div></form></section>';
        var shell=host.querySelector('.dh-chat-shell'),scroll=host.querySelector('.dh-message-scroll'),list=host.querySelector('.dh-message-list'),input=host.querySelector('textarea'),send=host.querySelector('.dh-send-button'),form=host.querySelector('form'),status=host.querySelector('.dh-reply-status'),jump=host.querySelector('.dh-jump-latest'),quick=host.querySelector('.dh-inline-quick'),toggle=host.querySelector('.dh-shortcuts-toggle');
        var history=document.createElement('section');history.className='dh-previous-visit';history.hidden=true;history.setAttribute('aria-label','历史对话');
        var historyList=document.createElement('div');historyList.className='dh-message-list dh-history-messages';history.appendChild(historyList);
        var historyResults=document.createElement('div');historyResults.className='dh-agent-results';historyResults.hidden=true;history.appendChild(historyResults);
        var gate=document.createElement('button');gate.type='button';gate.className='dh-history-gate';gate.hidden=true;gate.setAttribute('aria-expanded','false');
        var historyId='dh-previous-'+(++counter);history.id=historyId;gate.setAttribute('aria-controls',historyId);
        scroll.insertBefore(gate,list);scroll.insertBefore(history,gate);scroll.tabIndex=0;
        var currentResults=status.nextElementSibling;
        var messages=[],previous=[],historicalResult=null,viewSession=null,expanded=false,pinned=true,timer,started,touchY=0;
        var clock='<svg viewBox="0 0 24 24" fill="none" aria-hidden="true"><circle cx="12" cy="12" r="8"/><path d="M12 7v5l3 2"/></svg>';
        function gateLabel(){var actions=viewSession&&viewSession.actions||[];gate.innerHTML=clock+'<span>'+(expanded?'历史对话 · '+previous.length+' 条消息':'历史对话 · '+previous.length+' 条消息 · 上滑查看')+(actions.length&&historicalResult===viewSession.lastResult?' · '+actions.length+' 项待确认':'')+'</span><span class="dh-history-chevron">'+(expanded?'收起':'⌃')+'</span>';gate.setAttribute('aria-expanded',String(expanded));}
        function bottom(force){if(force||pinned){scroll.scrollTop=scroll.scrollHeight;pinned=true;jump.hidden=true;}}
        function measure(){input.style.height='auto';input.style.height=Math.min(112,Math.max(42,input.scrollHeight))+'px';send.disabled=pending||!input.value.trim();}
        function append(m,target,hello){
            var node=document.createElement('article');node.className='dh-message dh-message-'+(m.role==='user'?'user':'ai');
            if(hello)node.classList.add('dh-visit-greeting');
            var label=document.createElement('div');label.className='dh-message-label';label.textContent=m.role==='user'?'你':'数字健康师';node.appendChild(label);
            var body=document.createElement('div');body.className='dh-message-content';if(m.role==='user')body.textContent=m.text;else body.innerHTML=markdown(m.text);node.appendChild(body);
            if(m.role!=='user'&&!hello){var copy=document.createElement('button');copy.type='button';copy.className='dh-copy-message';copy.textContent='复制';copy.setAttribute('aria-label','复制回复');copy.onclick=async function(){try{await navigator.clipboard.writeText(m.text);copy.textContent='已复制';setTimeout(function(){copy.textContent='复制';},1600);}catch(e){toast('浏览器不支持直接复制，请选中文字复制');}};node.appendChild(copy);}
            (target||list).appendChild(node);if(!target)bottom();
        }
        function showHistory(){if(expanded||!previous.length)return;var oldHeight=scroll.scrollHeight,oldTop=scroll.scrollTop;expanded=true;historyList.innerHTML='';previous.forEach(function(m){append(m,historyList);});history.hidden=false;gateLabel();scroll.scrollTop=oldTop+scroll.scrollHeight-oldHeight;pinned=false;jump.hidden=false;}
        function startVisit(items,session){
            messages=items.slice();previous=items.slice();viewSession=session||current();historicalResult=viewSession&&viewSession.lastResult||null;expanded=false;pinned=true;
            history.hidden=true;historyList.innerHTML='';historyResults.innerHTML='';historyResults.hidden=true;currentResults.innerHTML='';currentResults.hidden=true;list.innerHTML='';gate.hidden=!previous.length;gateLabel();
            var hello=welcome()+(previous.length?'\n之前的聊天已保留，上滑可以查看，也可以直接接着问。':'');
            append({role:'ai',text:hello},null,true);chat.renderResults(viewSession||{});bottom(true);
        }
        var chat={
            addMessage:function(m){messages.push(m);append(m);},
            clearMessages:function(){startVisit([],current());},
            setMessages:function(items,session){startVisit(items,session);},
            beginVisit:function(){if(!pending)startVisit(current()?current().messages:[],current());},
            renderResults:function(session){viewSession=session;var old=historicalResult&&session.lastResult===historicalResult;renderResultHost(historyResults,historicalResult,old?session.actions:[],session);renderResultHost(currentResults,old?null:session.lastResult,old?[]:session.actions,session);gateLabel();},
            getMessages:function(){return messages.slice();},
            submitUserMessage:function(m){return sendMessage(m.text,chat);},
            clearDraft:function(){input.value='';measure();},
            finishReply:function(){bottom();if(document.activeElement===input)input.focus();},
            setBusy:function(value){shell.setAttribute('aria-busy',String(value));status.hidden=!value;send.disabled=value||!input.value.trim();clearInterval(timer);if(value){started=Date.now();status.querySelector('time').textContent='';timer=setInterval(function(){status.querySelector('time').textContent=Math.floor((Date.now()-started)/1000)+'s';},1000);bottom();}}
        };
        gate.onclick=function(){if(expanded){history.hidden=true;expanded=false;gateLabel();scroll.scrollTop=0;pinned=true;}else{showHistory();scroll.scrollTop=Math.max(0,gate.offsetTop-scroll.offsetTop-120);}};
        scroll.addEventListener('wheel',function(e){if(e.deltaY<0&&scroll.scrollTop<45)showHistory();},{passive:true});
        scroll.addEventListener('touchstart',function(e){touchY=e.touches[0].clientY;},{passive:true});
        scroll.addEventListener('touchmove',function(e){if(e.touches[0].clientY-touchY>24&&scroll.scrollTop<45)showHistory();},{passive:true});
        scroll.addEventListener('keydown',function(e){if(e.target===scroll&&['PageUp','Home','ArrowUp'].indexOf(e.key)>=0&&scroll.scrollTop<45)showHistory();});
        scroll.addEventListener('scroll',function(){pinned=scroll.scrollHeight-scroll.scrollTop-scroll.clientHeight<90;jump.hidden=pinned;});
        jump.onclick=function(){bottom(true);};input.addEventListener('input',measure);
        input.addEventListener('keydown',function(e){if(e.key==='Enter'&&!e.shiftKey&&!e.isComposing&&e.keyCode!==229){e.preventDefault();if(!send.disabled)chat.submitUserMessage({text:input.value});}});
        form.addEventListener('submit',function(e){e.preventDefault();if(!send.disabled)chat.submitUserMessage({text:input.value});});
        quick.innerHTML=commands.map(function(c){return '<button type="button" data-command="'+c.key+'"><span>'+c.icon+'</span>'+c.name+'</button>';}).join('');
        toggle.onclick=function(){quick.hidden=!quick.hidden;toggle.setAttribute('aria-expanded',String(!quick.hidden));bottom();};
        quick.onclick=function(e){var b=e.target.closest('[data-command]');if(!b)return;quick.hidden=true;toggle.setAttribute('aria-expanded','false');submit(chat,commands.find(function(c){return c.key===b.dataset.command;}).prompt);};
        host.querySelector('.dh-upload-shortcut').onclick=openArchives;
        if(window.ResizeObserver){var resize=new ResizeObserver(function(){bottom();});resize.observe(scroll);window.addEventListener('pagehide',function(){resize.disconnect();clearInterval(timer);});}
        chat.setMessages(current()?current().messages:[],current());chats.push(chat);return chat;
    }
    function submit(chat,prompt){if(pending){toast('正在查询与分析，请稍候');return;}if(chat)chat.submitUserMessage({text:prompt});}
    function toast(text) {
        var node = document.getElementById('dh-toast');
        if (!node) { node = document.createElement('div'); node.id = 'dh-toast'; node.className = 'dh-toast'; node.setAttribute('role', 'status'); document.body.appendChild(node); }
        node.textContent = text; node.hidden = false;
        clearTimeout(node._hideTimer); node._hideTimer = setTimeout(function () { node.hidden = true; }, 4000);
    }
    function openPage() {
        closeFloating();
        if (window.layui && window.layui.index) { window.layui.index.openTab({ url: PAGE, title: '数字健康师' }); }
        else { window.location.href = PAGE; }
    }
    function closeFloating() {
        if (!floating) { return; }
        floating.panel.hidden = true; floating.launcher.hidden = false;
        floating.launcher.setAttribute('aria-expanded', 'false');
    }
    function addAnimatedDoctor(launcher, root) {
        var stage=document.createElement('span');stage.className='dh-avatar-stage';stage.setAttribute('aria-hidden','true');launcher.appendChild(stage);
        doctorModule=doctorModule||import('./digital-doctor-portrait.js?v=20261117-dock2');
        doctorModule.then(async function(module){
            if(!stage.isConnected)return;
            var controller=await module.mountDoctor(stage,profile.gender);
            if(!stage.isConnected){controller.dispose();return;}
            launcher._doctor3d=controller;
            launcher.classList.add('dh-live-avatar-ready');
            launcher.title='数字健康师：保留原形象的分区动画，可按住拖动';
            root.dataset.avatarMode='portrait-rig';
        }).catch(function(){
            stage.remove();root.dataset.avatarMode='fallback';
            launcher.title='当前显示原始医生形象；浏览器暂未启用角色动画';
            var fallback=document.createElement('small');fallback.className='dh-avatar-fallback-note';fallback.textContent='静态备用';launcher.appendChild(fallback);
        });
    }
    function makeInteractive(root, launcher, panel, open) {
        var bubble = document.createElement('div'); bubble.className = 'dh-doctor-bubble'; bubble.hidden = true;
        bubble.innerHTML = '<button class="dh-bubble-dismiss" type="button" aria-label="关闭提示">×</button><div class="dh-bubble-heading"><i></i> 数字健康师<span>陪伴提示</span></div><p class="dh-bubble-text" role="status" aria-live="polite"></p><div class="dh-bubble-actions"><button type="button" class="dh-bubble-chat">聊一聊 <span>↗</span></button><small>拖动娃娃，可调整位置</small></div>';
        root.appendChild(bubble);
        var badge = document.createElement('span'); badge.className = 'dh-doctor-badge'; badge.hidden = true; launcher.appendChild(badge);
        var shrink = root.querySelector('.dh-widget-minimize'), restore = root.querySelector('.dh-widget-restore'), compactBadge=restore.querySelector('.dh-compact-badge');
        var unread=0, drag, suppressUntil = 0, hideTimer, greetTimer, interval, lastInteraction = Date.now(), tipIndex = 0;
        var tips = ['职业健康档案、体检报告、随访关怀，想从哪里开始？', '在“健康评估 → 数字健康师”里，可以打开完整工作台。', '想整理体检材料？可以使用对话面板的“上传报告”入口。', '我在这里，随时陪你了解职业健康服务。'];
        function updateUnread() {
            var label=unread>99?'99+':String(unread);
            [badge,compactBadge].forEach(function(n){n.textContent=label;n.hidden=!unread;});
            root.dataset.unread=String(unread);
            launcher.setAttribute('aria-label',unread?'数字健康师，'+unread+'条未读消息，点击查看':'打开数字健康师');
            restore.setAttribute('aria-label','恢复数字健康师'+(unread?'，'+unread+'条未读消息':''));
        }
        updateUnread();
        // Count newly received replies only; greetings, animation controls and old history are not messages.
        floating.notifyReply=function(text){
            if(!panel.hidden&&!root.hidden&&!document.hidden&&!root.classList.contains('dh-widget-compact'))return;
            unread++;updateUnread();say('有新的回复，点击数字健康师查看。',6500);
        };
        function setCompact(value) {
            if(!panel.hidden)return;
            var r=root.getBoundingClientRect();hideBubble(false);
            root.classList.toggle('dh-widget-compact',value);
            restore.hidden=!value;shrink.setAttribute('aria-expanded',String(!value));
            var next=root.getBoundingClientRect();
            root.style.right='auto';root.style.bottom='auto';
            root.style.left=clamp(r.right-next.width,7,window.innerWidth-next.width-7)+'px';
            root.style.top=clamp(r.bottom-next.height,7,window.innerHeight-next.height-7)+'px';
            lastInteraction=Date.now();(value?restore:shrink).focus();
        }
        shrink.addEventListener('click',function(){setCompact(true);});
        restore.addEventListener('click',function(){setCompact(false);});
        function clamp(value, low, high) { return Math.min(Math.max(low, value), Math.max(low, high)); }
        function positionBubble() {
            var r = root.getBoundingClientRect(), bw = bubble.offsetWidth || 248, bh = bubble.offsetHeight || 130;
            var left = r.left > bw + 20 ? r.left - bw - 10 : r.right + 10;
            var top = r.top + 5;
            if (left + bw > window.innerWidth - 10) { left = clamp(r.left + r.width / 2 - bw / 2, 10, window.innerWidth - bw - 10); top = r.top - bh - 12; }
            bubble.style.left = clamp(left, 10, window.innerWidth - bw - 10) + 'px';
            bubble.style.top = clamp(top, 10, window.innerHeight - bh - 10) + 'px';
        }
        function positionPanel() {
            if (panel.hidden) { return; }
            var r = root.getBoundingClientRect(), pw = panel.offsetWidth || 390, ph = panel.offsetHeight || 600;
            panel.style.left = clamp(r.left + r.width - pw, 10, window.innerWidth - pw - 10) + 'px';
            panel.style.top = clamp(r.bottom - ph, 10, window.innerHeight - ph - 10) + 'px';
        }
        function hideBubble(read) {
            bubble.hidden = true; clearTimeout(hideTimer);
            if (read) { unread=0;updateUnread(); }
        }
        function greetMotion() {
            if (launcher._doctor3d) { launcher._doctor3d.play('wave'); }
            root.classList.remove('dh-is-greeting');
            void launcher.offsetWidth;
            root.classList.add('dh-is-greeting');
            clearTimeout(greetTimer); greetTimer = setTimeout(function () { root.classList.remove('dh-is-greeting'); }, 950);
        }
        function say(text, duration) {
            if (root.hidden || !panel.hidden || document.hidden || drag || root.classList.contains('dh-widget-compact')) { return; }
            bubble.querySelector('.dh-bubble-text').textContent = text;
            bubble.hidden = false; positionBubble();
            greetMotion(); clearTimeout(hideTimer); hideTimer = setTimeout(function () { hideBubble(false); }, duration || 11000);
        }
        function activate() {
            if (Date.now() < suppressUntil) { return; }
            var hasUnread=unread>0;
            lastInteraction = Date.now(); hideBubble(true); open(hasUnread); positionPanel();
        }
        launcher.addEventListener('pointerdown', function (event) {
            if (event.button !== 0 || !panel.hidden) { return; }
            var r = root.getBoundingClientRect();
            drag = { id: event.pointerId, x: event.clientX, y: event.clientY, left: r.left, top: r.top, width: r.width, height: r.height, moved: false };
            lastInteraction = Date.now();
            try { launcher.setPointerCapture(event.pointerId); } catch (e) {}
        });
        launcher.addEventListener('pointermove', function (event) {
            if (!drag || event.pointerId !== drag.id) { return; }
            var dx = event.clientX - drag.x, dy = event.clientY - drag.y;
            if (!drag.moved && Math.abs(dx) + Math.abs(dy) < 6) { return; }
            drag.moved = true; hideBubble(false); root.classList.add('dh-is-dragging');
            root.style.right = 'auto'; root.style.bottom = 'auto';
            root.style.left = clamp(drag.left + dx, 7, window.innerWidth - drag.width - 7) + 'px';
            root.style.top = clamp(drag.top + dy, 7, window.innerHeight - drag.height - 7) + 'px';
            root.style.setProperty('--dh-drag-lean', clamp(dx / 16, -8, 8) + 'deg');
            event.preventDefault();
        });
        function endDrag(event) {
            if (!drag || (event.pointerId != null && event.pointerId !== drag.id)) { return; }
            var moved = drag.moved;
            var pointerId = drag.id; drag = null;
            try { launcher.releasePointerCapture(pointerId); } catch (e) {}
            root.classList.remove('dh-is-dragging');
            if (moved) { suppressUntil = Date.now() + 400; greetMotion(); setTimeout(function () { say('位置调整好了。想聊聊健康档案或随访关怀吗？', 6500); }, 450); }
        }
        launcher.addEventListener('pointerup', endDrag); launcher.addEventListener('pointercancel', endDrag);
        launcher.addEventListener('lostpointercapture', endDrag);
        launcher.addEventListener('dragstart', function (event) { event.preventDefault(); });
        launcher.addEventListener('click', activate);
        launcher.addEventListener('mouseenter', function () { if (panel.hidden && !drag) { greetMotion(); } });
        launcher.addEventListener('contextmenu', function (event) { event.preventDefault(); say('按住我可以拖动，轻点我就能打开对话。', 6000); });
        bubble.querySelector('.dh-bubble-dismiss').addEventListener('click', function () { lastInteraction = Date.now(); hideBubble(false); });
        bubble.querySelector('.dh-bubble-chat').addEventListener('click', activate);
        window.addEventListener('resize', function () {
            if (root.style.left) {
                var r = root.getBoundingClientRect();
                root.style.left = clamp(r.left, 7, window.innerWidth - r.width - 7) + 'px';
                root.style.top = clamp(r.top, 7, window.innerHeight - r.height - 7) + 'px';
            }
            if (!bubble.hidden) { positionBubble(); } positionPanel();
        });
        var firstHello = setTimeout(function () { say(greeting() + '，' + profile.name + '。我是数字健康师，点我聊聊，也可以按住我拖动。', 12000); }, 2300);
        interval = setInterval(function () {
            if (Date.now() - lastInteraction > 45000) { say(tips[tipIndex++ % tips.length]); }
            if (root.hidden || !panel.hidden) { hideBubble(false); }
        }, 55000);
        var visibility = new MutationObserver(function () { shrink.hidden=!panel.hidden;if (root.hidden || !panel.hidden) { hideBubble(false); } });
        visibility.observe(root, { attributes: true, attributeFilter: ['hidden'] });
        visibility.observe(panel, { attributes: true, attributeFilter: ['hidden'] });
        document.addEventListener('visibilitychange',function(){if(!document.hidden&&!panel.hidden&&!root.hidden){unread=0;updateUnread();}});
        window.addEventListener('pagehide', function () { clearTimeout(firstHello); clearTimeout(hideTimer); clearTimeout(greetTimer); clearInterval(interval); visibility.disconnect(); });
    }
    function mountFloating(options) {
        if (floating) { return; }
        profile = { code: String(options.code || ''), name: String(options.name || '健康管理师'), gender: Number(options.gender) || 0 };
        ensureSession();
        var root = document.createElement('div'); root.className = 'dh-floating-root';
        root.innerHTML = '<button class="dh-launcher" type="button" title="数字健康师 · 点击打开对话" aria-label="打开数字健康师" aria-expanded="false" aria-controls="dh-floating-panel"><span class="dh-launcher-glow"></span><img src="' + avatar(profile.gender) + '" alt="医生助手"/><span class="dh-launcher-label"><i></i> 数字健康师 <b>＋</b></span></button><section class="dh-floating-panel" id="dh-floating-panel" role="dialog" aria-label="数字健康师对话面板" hidden><header class="dh-panel-header"><span class="dh-panel-avatar"><img src="' + avatar(profile.gender) + '" alt=""/></span><div class="dh-panel-name"><strong>数字健康师 <small>AI</small></strong><span>查档案 · 看报告 · 健康分析</span></div><button type="button" class="dh-open-page" title="打开完整数字健康师工作台">工作台 ↗</button><button class="dh-panel-minimize" type="button" aria-label="收起对话面板" title="收起">−</button></header><div class="dh-panel-banner"><i></i> 欢迎，<strong class="dh-account-name"></strong><span>正在连接</span></div><div class="dh-floating-chat-host"></div></section>';
        document.body.appendChild(root);
        root.classList.add('dh-doctor-dock');
        var mark='<svg viewBox="0 0 32 32" fill="none" aria-hidden="true"><path d="M6 15a10 10 0 0 1 20 0M5 15h22v4H5zM13 6v7m6-7v7M8 21l8 6 8-6" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"/><path d="M10 23h3l2-3 2 5 2-2h3" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/></svg>';
        root.insertAdjacentHTML('afterbegin','<button class="dh-widget-minimize" type="button" aria-label="缩小数字健康师" aria-expanded="true" title="缩小，保留未读消息提醒"><svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M5 10h10" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"/></svg></button><button class="dh-widget-restore" type="button" aria-label="恢复数字健康师" title="点击放大，恢复数字健康师" hidden>'+mark+'<span class="dh-restore-arrow" aria-hidden="true">↗</span><span class="dh-compact-badge" hidden></span></button>');
        var dockLauncher=root.querySelector('.dh-launcher');
        dockLauncher.querySelector('.dh-launcher-glow').innerHTML='<svg viewBox="0 0 160 42" fill="none" aria-hidden="true"><ellipse cx="80" cy="23" rx="70" ry="15" fill="url(#dh-dock-fill)"/><ellipse cx="80" cy="21" rx="68" ry="15" stroke="currentColor" stroke-opacity=".6"/><ellipse cx="80" cy="21" rx="54" ry="10" stroke="currentColor" stroke-opacity=".25"/><path d="M16 22h13l4-4 5 8 5-5h9m56 0h9l5-5 5 9 4-4h13" stroke="currentColor" stroke-width="1.3" stroke-linecap="round" stroke-linejoin="round"/><defs><radialGradient id="dh-dock-fill"><stop stop-color="#57dfc3" stop-opacity=".32"/><stop offset="1" stop-color="#27b99c" stop-opacity=".03"/></radialGradient></defs></svg>';
        dockLauncher.querySelector('.dh-launcher-label').innerHTML='<span class="dh-dock-mark">'+mark+'</span><span class="dh-dock-title"><strong>数字健康师</strong><small class="dh-dock-caption" role="status">职业健康 · 持续守护</small></span><b aria-hidden="true">＋</b>';
        root.querySelector('.dh-account-name').textContent = profile.name;
        var launcher = root.querySelector('.dh-launcher'), panel = root.querySelector('.dh-floating-panel');
        var chat = createChat(root.querySelector('.dh-floating-chat-host'));
        root.querySelector('.dh-panel-name small').textContent='AI';
        root.querySelector('.dh-panel-name>span').textContent='真实档案查询 · 健康数据分析';
        bootAgent().catch(function(){});
        floating = { root: root, launcher: launcher, panel: panel, chat: chat };
        addAnimatedDoctor(launcher, root);
        makeInteractive(root, launcher, panel, function (hasUnread) { panel.hidden = false; launcher.hidden = true; launcher.setAttribute('aria-expanded', 'true');if(!hasUnread)chat.beginVisit();root.querySelector('.dh-panel-minimize').focus(); });
        root.querySelector('.dh-panel-minimize').addEventListener('click', function () { closeFloating(); launcher.focus(); });
        root.querySelector('.dh-open-page').addEventListener('click', openPage);
        document.addEventListener('keydown', function (event) { if (event.key === 'Escape' && !panel.hidden) { closeFloating(); launcher.focus(); } });
        // Keep a single assistant visible when its dedicated workbench is open.
        var frameObserver = new MutationObserver(function () {
            if (!root.isConnected) { return; }
            var frame = document.querySelector('.layui-body iframe.admin-iframe');
            root.hidden = !!(frame && frame.getAttribute('src') && frame.getAttribute('src').split('?')[0] === PAGE_PATH);
        });
        frameObserver.observe(document.querySelector('.layui-body') || document.body, { subtree: true, childList: true, attributes: true, attributeFilter: ['src'] });
        window.addEventListener('pagehide', function () { frameObserver.disconnect(); });
    }
    function mountPage() {
        var parentApi;
        try { if (window.parent !== window && window.parent.DigitalHealth) { parentApi = window.parent.DigitalHealth; profile = parentApi.getIdentity(); } } catch (e) {}
        if (!profile.code) { profile.name = '健康管理师'; }
        ensureSession();
        document.getElementById('dh-page-avatar').src = avatar(profile.gender);
        document.getElementById('dh-side-avatar').src = avatar(profile.gender);
        document.getElementById('dh-welcome-title').textContent = greeting() + '，' + profile.name;
        var chat = createChat(document.getElementById('dh-page-chat-host'));
        bootAgent().catch(function(){});
        var intro = document.getElementById('dh-workbench-intro');
        var starter = document.getElementById('dh-starter-grid');
        starter.innerHTML = starters.map(function (s, i) { return '<button type="button" data-starter="' + i + '"><span>' + s.icon + '</span><strong>' + s.title + '</strong><small>' + s.text + '</small><b>↗</b></button>'; }).join('');
        starter.addEventListener('click', function (event) { var b = event.target.closest('[data-starter]'); if (b) { submit(chat, starters[Number(b.getAttribute('data-starter'))].prompt); } });
        var tools = document.getElementById('dh-workbench-tools');
        tools.innerHTML = commands.map(function (c, i) { return '<button type="button" data-tool="' + i + '"><span>' + c.icon + '</span>' + c.name + '</button>'; }).join('');
        tools.addEventListener('click', function (event) { var b = event.target.closest('[data-tool]'); if (b) { submit(chat, commands[Number(b.getAttribute('data-tool'))].prompt); } });
        function drawSessions() {
            var query = document.getElementById('dh-history-search').value.trim();
            var filtered = sessions.filter(function (s) { return s.title.indexOf(query) !== -1; });
            document.getElementById('dh-session-list').innerHTML = filtered.length ? filtered.map(function (s) { return '<div class="dh-session-row'+(s.id===activeId?' is-active':'')+'"><button type="button" class="dh-session' + (s.id === activeId ? ' is-active' : '') + '" data-session="' + escape(s.id) + '"><strong>' + escape(s.title) + '</strong><span>' + escape(sessionTime(s.time)) + (s.messages.length?' · '+s.messages.length+' 条消息':'') + '</span></button><button type="button" class="dh-session-delete" data-delete-session="'+escape(s.id)+'" aria-label="删除对话：'+escape(s.title)+'" title="删除对话"><svg viewBox="0 0 24 24" fill="none" aria-hidden="true"><path d="M4 7h16M9 7V4h6v3M6 7l1 13h10l1-13M10 11v5m4-5v5" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/></svg></button></div>'; }).join('') : '<p class="dh-no-session">没有匹配的会话</p>';
            intro.hidden = current() && current().messages.length > 0;
            document.getElementById('dh-page-chat-host').classList.toggle('has-conversation', intro.hidden);
        }
        uiListeners.push(drawSessions); drawSessions();
        document.getElementById('dh-new-chat').addEventListener('click', function () { newSession(); });
        document.getElementById('dh-history-search').addEventListener('input', drawSessions);
        document.getElementById('dh-session-list').addEventListener('click', function (event) {
            var remove=event.target.closest('[data-delete-session]');if(remove){deleteSession(remove.dataset.deleteSession);return;}
            var b = event.target.closest('[data-session]'); if (!b) { return; }
            if(pending||removing){toast('正在处理当前请求，请完成后切换会话');return;}
            activeId = b.getAttribute('data-session');
            var selected = current();
            chats.forEach(function(c){c.setMessages(selected?selected.messages:[],selected);});renderResults(selected);
            drawSessions();
            loadConversation(selected).catch(function(e){toast(e.message);});
        });
        var select=document.getElementById('dh-patient-context'),search=document.getElementById('dh-employee-search');
        async function employees(){try{var data=await api('/records?limit=30&search='+encodeURIComponent(search?search.value.trim():''));var options='<option value="">不指定职工（可直接输入姓名或工号提问）</option>';data.items.forEach(function(p){options+='<option value="'+escape(p.id)+'">'+escape(p.name)+' · '+escape(p.code)+' · '+escape(p.team||'未填写班组')+'</option>';});select.innerHTML=options;select.value=selectedRecord;if(select.value!==selectedRecord){selectedRecord='';select.value='';document.getElementById('dh-context-status').textContent='请重新选择搜索到的服务对象';}}catch(e){toast(e.message);}}
        var searchTimer;if(search)search.addEventListener('input',function(){clearTimeout(searchTimer);searchTimer=setTimeout(employees,350);});employees();
        select.addEventListener('change',function(){selectedRecord=select.value;document.getElementById('dh-context-status').textContent=selectedRecord?'当前服务对象：'+select.options[select.selectedIndex].text:'Enter 发送 · Shift + Enter 换行';});
        document.getElementById('dh-page-voice').hidden=true;
        var files = document.getElementById('dh-page-files');
        document.getElementById('dh-page-attach').title='进入档案信息管理上传报告';
        document.getElementById('dh-page-attach').addEventListener('click',openArchives);
    }
    function openArchives(){var url='/assets/health-archive/management.html';try{var w=window.parent!==window?window.parent:window;if(w.layui&&w.layui.index){w.layui.index.openTab({url:url,title:'档案信息管理'});return;}}catch(e){}window.open(url,'_blank','noopener');}
    window.DigitalHealth = { mountFloating: mountFloating, mountPage: mountPage, getIdentity: identity, openPage: openPage, closeFloating: closeFloating };
})(window);
