(function () {
"use strict";
const safe = value => String(value ?? '').replace(/[&<>"']/g, ch => ({
    '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
}[ch]));

const workers=[
 {id:'archive',name:'档案小谷',role:'健康档案专员',desc:'体检报告解析、档案归档、结构化字段提取',tone:'#5c65ec',bg:'#eff0ff',hair:'#383958',clothes:'#746ae7',skin:'#f4c3a6',emoji:'📁',cap:'OCR 识别 · PDF 解析 · 档案去重',scope:'健康档案全生命周期管理，提取姓名、检查指标、报告日期及参考范围，生成规范化档案并记录解析依据。'},
 {id:'assess',name:'评估小谷',role:'健康风险评估师',desc:'体检指标解读、异常识别、风险分层评估',tone:'#eb9348',bg:'#fff3e8',hair:'#5b3e34',clothes:'#e9a26a',skin:'#ffd2b4',emoji:'🩺',cap:'指标分析 · 参考范围匹配 · 风险分层',scope:'对结构化体检结果进行参考范围匹配，识别需关注的异常指标，形成仅供辅助决策的风险提示。'},
 {id:'follow',name:'随访小谷',role:'健康随访助理',desc:'跟进随访计划、提醒复查、追踪处置情况',tone:'#3976e9',bg:'#eaf2ff',hair:'#42516b',clothes:'#5e98e5',skin:'#f3c6a4',emoji:'📞',cap:'提醒触达 · 随访编排 · 闭环跟踪',scope:'管理随访时间表，生成复查提醒和随访任务，支持工作人员确认联系结果并回写记录。'},
 {id:'plan',name:'方案小谷',role:'个性化方案师',desc:'健康管理建议、个体化计划、服务任务分发',tone:'#26ad88',bg:'#e8f9f2',hair:'#374c42',clothes:'#38b29b',skin:'#efbc9b',emoji:'🧩',cap:'健康计划 · 服务编排 · 效果追踪',scope:'根据体检和评估结果生成一般性健康管理建议，交由具备资质的人员审核后再用于实际服务。'},
 {id:'care',name:'心理小谷',role:'心理关怀助理',desc:'情绪支持、心理科普、人工服务转介提醒',tone:'#8f67da',bg:'#f4eeff',hair:'#654664',clothes:'#a483df',skin:'#f7c8ad',emoji:'💜',cap:'温和交流 · 科普支持 · 人工转介',scope:'提供常见心理健康知识与支持性对话；遇到危机或超出能力范围的问题，提示寻求专业与紧急帮助。'}
];
function avatar(w){const hairAlt=w.id==='care'?'<path d="M8 36 Q0 4 28 6 Q58 7 51 44 L43 50 L40 24 Q30 18 20 25 L17 48 Z" fill="'+w.hair+'" opacity=".95"/>':'<path d="M9 37 Q4 6 30 8 Q53 8 51 38 L44 35 Q44 23 37 19 Q20 26 16 23 L14 38Z" fill="'+w.hair+'"/>';
 const extra=w.id==='assess'?'<path d="M25 52 v12 m10-12 v12" stroke="#f5f7ff" stroke-width="1.8"/><path d="M23 49 Q28 53 33 49" stroke="#6d8e9f" fill="none" stroke-width="2"/>':w.id==='follow'?'<path d="M8 32 Q2 35 7 43" stroke="'+w.tone+'" fill="none" stroke-width="3"/><path d="M49 31 Q56 34 51 44" stroke="'+w.tone+'" fill="none" stroke-width="3"/><path d="M50 42 Q49 51 40 50" stroke="'+w.tone+'" fill="none" stroke-width="2"/>':w.id==='archive'?'<rect x="6" y="33" width="7" height="11" rx="3" fill="'+w.tone+'"/><rect x="48" y="33" width="7" height="11" rx="3" fill="'+w.tone+'"/>':w.id==='care'?'<path d="M10 26Q4 35 9 51" stroke="'+w.hair+'" stroke-width="8" fill="none"/><path d="M49 26Q54 35 49 51" stroke="'+w.hair+'" stroke-width="8" fill="none"/>':'<path d="M27 54l2 6 2-6" stroke="white" fill="none" stroke-width="2"/>';
 return `<svg viewBox="0 0 60 68" role="img" aria-label="${safe(w.name)} 数字形象"><circle cx="30" cy="30" r="28" fill="${w.bg}"/><circle cx="30" cy="31" r="20" fill="${w.tone}" opacity=".13"/><path d="M4 68 Q8 49 22 48 L38 48 Q52 50 56 68" fill="${w.clothes}"/><path d="M24 47 L24 54 Q30 61 36 54 L36 47" fill="${w.skin}"/><ellipse cx="30" cy="34" rx="17" ry="20" fill="${w.skin}"/>${hairAlt}<ellipse cx="23" cy="36" rx="1.5" ry="2.1" fill="#41475b"/><ellipse cx="37" cy="36" rx="1.5" ry="2.1" fill="#41475b"/><path d="M26 43 Q30 46 34 43" stroke="#bf7c77" stroke-width="1.5" stroke-linecap="round" fill="none"/><circle cx="18" cy="42" r="3" fill="#e99591" opacity=".28"/><circle cx="42" cy="42" r="3" fill="#e99591" opacity=".28"/>${extra}<path d="M22 50l8 9 8-9" stroke="white" stroke-width="1.8" fill="none" opacity=".65"/></svg>`}
if (document.body.dataset.page !== 'overview') return;
const service = window.DigitalWorkerService;
const {escape, states, open} = service;
const $ = selector => document.querySelector(selector);
const showCount = number => Number.isFinite(number) ? number.toLocaleString('zh-CN') : '—';
const set = (selector, value) => { $(selector).textContent = value; };

const hub=window.DigitalWorkerHub.create($('#taskHub'),()=>refresh());
let refreshing=false,feed=[],firstRefresh=true;
async function refresh(){
    if(refreshing)return;refreshing=true;$('#refreshBtn').disabled=true;$('#dataStatus').textContent='正在读取正式数据…';
    const read=promise=>promise.then(value=>({value}),error=>({error}));
    try{
        const [stats,alerts,summary,active,recent]=await Promise.all([read(service.statistics()),read(service.alerts()),read(service.hubSummary()),read(service.hubTasks({status:'active',page:1,limit:5})),read(service.hubTasks({status:'finished',page:1,limit:5}))]);
        const total=summary.value?.summary;
        set('#archiveTotal',showCount(stats.value?.total));set('#archiveMonth',showCount(stats.value?.month));set('#pendingKpi',showCount(total?.active));set('#alertTotal',showCount(alerts.value?.total));set('#ocrDone',showCount(total?.completed));
        set('#engineTotal',showCount(total?.total));set('#enginePending',showCount(total?.active));set('#engineReview',showCount(total?.pending_review));set('#taskTotal',showCount(total?.active));set('#taskActive',showCount(total?.in_progress));
        feed=[...(active.value?.items||[]),...(recent.value?.items||[])];
        $('#taskList').innerHTML=active.error?'<p>汇总任务暂不可用，请刷新重试。</p>':active.value.items.length?active.value.items.map(task=>'<div class="task"><div class="task-body">'+window.DigitalWorkerHub.taskLink(task)+'<small>'+escape(service.workers.find(w=>w.id===task.worker)?.name)+' · '+escape(service.timestamp(task.updatedAt))+'</small></div><span class="badge">'+escape(window.DigitalWorkerHub.state(task))+'</span></div>').join(''):'<p>五位员工暂无活跃任务，可从工作台派活。</p>';
        $('#alertList').innerHTML=alerts.error?'<p>当前无法读取指标预警。</p>':alerts.value.items.length?alerts.value.items.map(item=>'<button class="alert" data-alert="'+escape(item.id)+'"><span class="alert-title">'+escape(item.title)+'</span><span class="alert-time">'+escape(item.time||'')+'</span></button>').join(''):'<p>当前账号暂无指标预警。</p>';
        $('#activityList').innerHTML=recent.error?'<p>执行记录暂不可用。</p>':recent.value.items.length?recent.value.items.map(task=>'<div class="activity"><div class="act-text"><b>'+escape(service.workers.find(w=>w.id===task.worker)?.name)+' · '+escape(window.DigitalWorkerHub.state(task))+'</b><p>'+window.DigitalWorkerHub.taskLink(task)+'</p></div><time class="act-time">'+escape(service.timestamp(task.updatedAt))+'</time></div>').join(''):'<p>暂无已结束的任务。</p>';
        // Patch counts rather than replacing the cards, preserving keyboard focus during polling.
        if(!$('#workerGrid').children.length)$('#workerGrid').innerHTML=workers.map(worker=>`<article class="worker" style="--accent:${worker.tone};--tint:${worker.bg}"><div class="worker-top"><div class="portrait">${avatar(worker)}</div><div class="worker-info"><div class="worker-name">${worker.name}</div><div class="worker-role">${worker.role}</div></div></div><div class="worker-desc">${worker.desc}</div><div class="hub-worker-counts"><button type="button" data-worker-tasks="${worker.id}" data-worker-status="all"><b data-worker-count="${worker.id}:total">—</b><small>全部任务</small></button><button type="button" data-worker-tasks="${worker.id}" data-worker-status="active"><b data-worker-count="${worker.id}:active">—</b><small>活跃任务</small></button><button type="button" data-worker-tasks="${worker.id}" data-worker-status="pending_review"><b data-worker-count="${worker.id}:pending_review">—</b><small>待审核</small></button></div><div class="worker-actions"><button class="btn btn-outline" data-worker="${worker.id}">进入工作台</button><button class="btn btn-worker" data-worker-tasks="${worker.id}" data-worker-status="all">查看任务</button></div></article>`).join('');
        document.querySelectorAll('[data-worker-count]').forEach(el=>{const [worker,key]=el.dataset.workerCount.split(':');el.textContent=showCount(summary.value?.byWorker[worker]?.[key]);});
        $('#dataStatus').textContent=[stats,alerts,summary,active,recent].some(result=>result.error)?'部分数据暂不可用，请刷新重试':'正式业务数据 · 最近同步 '+new Date().toLocaleTimeString('zh-CN',{hour12:false})+' · 每15秒刷新';
        $('#clock').textContent=new Date().toLocaleTimeString('zh-CN',{hour12:false});
        await hub.reload();
        if(firstRefresh&&!$('#taskHub').hidden)hub.focus();firstRefresh=false;
    }finally{$('#refreshBtn').disabled=false;refreshing=false;}
}
document.addEventListener('click',event=>{
    const button=event.target.closest('button');if(!button)return;
    if(button.id==='refreshBtn')refresh();
    if(button.id==='engineButton')hub.show({worker:'',status:'all',source:'',priority:'',type:'',search:'',page:1});
    if(button.id==='logBtn')open('records');
    if(button.id==='alertsAll')open('dispatch',{worker:'assess'});
    if(button.dataset.worker)open('workbench',{worker:button.dataset.worker});
    if(button.dataset.workerTasks)hub.show({worker:button.dataset.workerTasks,status:button.dataset.workerStatus||'all',source:'',priority:'',type:'',search:'',page:1});
    if(button.dataset.hubShortcut)hub.show({worker:'',status:button.dataset.hubShortcut,source:'',priority:'',type:'',search:'',page:1});
    if(button.dataset.alert)open('dispatch',{source:'real',alert:button.dataset.alert,worker:'assess'});
    if(button.dataset.hubTask&&!button.closest('#taskHub')){const row=feed.find(row=>row.id===button.dataset.hubTask&&row.source===button.dataset.hubSource);if(row)window.DigitalWorkerHub.openTask(row,refresh);}
});
refresh();service.watch(refresh);
})();
