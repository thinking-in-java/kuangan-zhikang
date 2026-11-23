(function () {
'use strict';
const I=(id,cls='')=>`<svg class="icon ${cls}"><use href="#i-${id}"/></svg>`;
const employees=[
{id:'archive',name:'档案小谷',eng:'ARCHIVE SPECIALIST',role:'健康档案',title:'体检报告与健康档案智能管家',desc:'自动整理健康档案、解析体检报告、识别数据缺失并生成核验清单。',color:'#2a9ede',coat:'#318acb',hair:'#756044',strength:['体检报告结构化','档案信息检索','缺失项核验','历史数据比对']},
{id:'assess',name:'评估小谷',eng:'HEALTH ASSESSMENT',role:'综合评估',title:'职业健康风险智能评估专家',desc:'聚合体检指标与职业危害因素，辅助识别风险、解释依据并生成复核建议。',color:'#8b66e7',coat:'#8064db',hair:'#403a4b',strength:['指标数据分析','风险分层','生活方式评估','体检报告解读','历史趋势对比','职业暴露关联']},
{id:'follow',name:'随访小谷',eng:'FOLLOW-UP ASSISTANT',role:'随访管理',title:'职工健康随访与复查提醒助手',desc:'制定随访计划、整理沟通记录、跟踪复查节点并提醒管理人员。',color:'#1cb69a',coat:'#1db39d',hair:'#594f55',strength:['复查节点提醒','随访计划编排','随访记录摘要','逾期任务识别']},
{id:'plan',name:'方案小谷',eng:'HEALTH PLAN DESIGNER',role:'健康方案',title:'个性化健康管理方案助手',desc:'结合风险评估结果生成分层管理建议，辅助管理师制定干预计划。',color:'#ef8c40',coat:'#ec8747',hair:'#5f4a39',strength:['干预建议生成','健康教育内容','行动计划拆解','计划效果回顾']},
{id:'care',name:'心理小谷',eng:'WELLBEING SUPPORT',role:'心理关怀',title:'心理健康科普与关怀助手',desc:'提供心理健康科普、压力管理资源和关怀服务引导，必要时转交专业人员。',color:'#aa86dd',coat:'#967bc6',hair:'#5f5365',strength:['心理健康科普','压力管理建议','关怀资源引导','专业支持转介']}
];
function escapeHtml(s){return String(s??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));}
function svgAvatar(e){let isAss=e.id==='assess';let hair=e.hair,coat=e.coat;let accessories=isAss?`<rect x="37" y="53" width="20" height="12" rx="5" fill="none" stroke="#493e55" stroke-width="2.6"/><rect x="63" y="53" width="20" height="12" rx="5" fill="none" stroke="#493e55" stroke-width="2.6"/><path d="M57 57h6" stroke="#493e55" stroke-width="2.5"/>`:e.id==='follow'?`<path d="M33 51q-2-15 17-21m37 21q2-15-17-21" fill="none" stroke="#f5d4b7" stroke-width="4"/><rect x="27" y="54" width="7" height="17" rx="3" fill="#e3f5e4"/><rect x="86" y="54" width="7" height="17" rx="3" fill="#e3f5e4"/>`:'';
let badge=e.id==='archive'?'▤':e.id==='assess'?'✚':e.id==='follow'?'☎':e.id==='plan'?'✦':'♡';
return `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 120 120" role="img" aria-label="${escapeHtml(e.name)}的插画头像"><defs><linearGradient id="g${e.id}" x1="0" y1="0" x2="1" y2="1"><stop stop-color="${e.color}" stop-opacity=".19"/><stop offset="1" stop-color="#f4ffff"/></linearGradient></defs><circle cx="60" cy="60" r="56" fill="url(#g${e.id})"/><ellipse cx="61" cy="112" rx="41" ry="6" fill="#1b3445" opacity=".09"/><path d="M22 120V102q2-24 25-28h26q25 4 26 28v18" fill="${coat}"/><path d="M47 76q13 24 26 0l-5-8H52z" fill="#e7b795"/><path d="M52 82 60 100 69 82 77 87 73 120H48l-6-33z" fill="#fff" opacity=".94"/><path d="M57 92h6l-2 7 3 18h-9l4-18z" fill="${e.color}"/><path d="M31 55q-8-31 15-40 29-13 44 10 8 13-1 30l-7-18-47 2z" fill="${hair}"/><rect x="35" y="31" width="50" height="47" rx="23" fill="#f4ceb0"/><ellipse cx="35" cy="56" rx="5" ry="8" fill="#f4ceb0"/><ellipse cx="85" cy="56" rx="5" ry="8" fill="#f4ceb0"/><path d="M34 47Q30 17 53 17q30-4 34 20-11-2-17-10-8 12-36 20Z" fill="${hair}"/><path d="M42 50h13M66 50h13" stroke="${hair}" stroke-width="3" stroke-linecap="round" opacity=".8"/><circle cx="49" cy="57" r="2.7" fill="#25364a"/><circle cx="72" cy="57" r="2.7" fill="#25364a"/><path d="M56 70q5 4 10 0" stroke="#b56b62" stroke-width="2" stroke-linecap="round" fill="none"/><ellipse cx="41" cy="67" rx="5" ry="2.3" fill="#f2a5a0" opacity=".45"/><ellipse cx="79" cy="67" rx="5" ry="2.3" fill="#f2a5a0" opacity=".45"/>${accessories}<circle cx="85" cy="96" r="12" fill="#fff" opacity=".95"/><text x="85" y="101" font-size="14" text-anchor="middle" fill="${e.color}">${badge}</text></svg>`}
function detail(){const e=emp();return `${pageHeader(e.name+' · 数字员工工作台','实时了解员工能力、待办工单与协作动态；业务数据按当前账号的权限展示。')}
<div class="ribbon"><div class="left">${I('shield')}<span><strong>安全协作提醒</strong> · AI 负责资料整理和建议草拟；涉及健康结论、通知发送和档案变更，均需授权人员审核。</span></div><div class="right"><span class="ribbon-tag">正式业务数据</span></div></div>
<div class="detail-layout">${rail()}<div class="detail-content">
<section class="hero"><div class="hero-left"><div class="hero-label">${I('bot','sm')} DIGITAL EMPLOYEE PROFILE / ${e.eng}</div><div class="hero-title"><h2>${e.name}</h2><span class="hero-badge">● ${status()}</span></div><p class="hero-desc">${e.title} · ${e.desc}</p><div class="hero-tags"><span>工号 VH-${e.id.toUpperCase()}-001</span><span>服务领域：${e.role}</span><span>${escapeHtml(worker().source)}</span></div></div><div class="hero-right"><div class="hero-avatar">${svgAvatar(e)}</div><div class="hero-control"><div class="switchline"><span>自主接单</span><span class="small">待接入</span></div><button class="light-btn" data-act="assign">＋ 给 TA 派活</button></div></div></section>
<div class="statgrid">${metrics(e)}</div>
<div class="section-grid"><section class="card"><div class="card-head"><div class="card-title"><span class="stripe"></span>需要我处理的事 <span class="count">${count(pending())} 项待办</span></div><button class="btn flat" data-act="dispatch">全部任务 ${I('arrow','sm')}</button></div><div class="card-body">${taskPanel()}</div></section>
<section class="card"><div class="card-head"><div class="card-title"><span class="stripe"></span>本周 AI 洞察</div><span class="card-sub">业务摘要 · 待接入 AI</span></div><div class="card-body insights-body">${insights()}</div></section></div>
<div class="lower-grid"><section class="card"><div class="card-head"><div class="card-title"><span class="stripe"></span>能力与边界</div><div class="tabs" role="tablist" aria-label="员工能力信息">${[['ability','TA 会什么'],['limits','能力边界'],['connect','接入情况']].map(([k,v])=>`<button role="tab" class="tab ${state.tab===k?'active':''}" aria-selected="${state.tab===k}" data-tab="${k}">${v}</button>`).join('')}</div></div><div class="card-body" id="tabBody">${tabContent(e)}</div></section>
<section class="card"><div class="card-head"><div class="card-title"><span class="stripe"></span>最近协作动态</div><button class="btn flat" data-act="records">执行记录 ${I('arrow','sm')}</button></div><div class="card-body"><div class="activity-list">${activitiesHTML()}</div><div class="handover-banner"><div><b>人工接管与审核</b><p>敏感操作需权限校验与人工批准</p></div><button class="btn small primary" data-act="handover">进入接管台</button></div></div></section></div>
</div></div>${foot()}`}
const service = window.DigitalWorkerService;
const state = { emp: 'assess', filter: 'all', source: 'assigned', tab: 'ability', page: 1 };
let tasks = [], recent = [], total = null, summary = null, statistics = null, recognition = null;
let assignedSummary = null;
let lastSynced = null, syncFailed = false, partialFailure = false, openTask = null;
let loading = false, error = '', generation = 0, previousFocus, drawerGeneration = 0;
const emp = () => employees.find(item => item.id === state.emp);
const worker = () => service.workers.find(item => item.id === state.emp);
const count = value => value == null ? '—' : Number(value).toLocaleString('zh-CN');
const pending = () => state.source === 'assigned' ? assignedSummary?.pending : state.emp === 'archive' ? summary?.pending : null;
const status = () => state.source === 'assigned' ? '人工派活已接入' : state.emp === 'archive' ? (recognition ? recognition.enabled ? '识别已配置' : '识别未启用' : '配置待查询') :
    state.emp === 'assess' ? '指标预警已接入' : '任务接口待接入';
function metrics() {
    const succeeded = Number(summary?.succeeded), failed = Number(summary?.failed);
    const rate = summary && succeeded + failed > 0 ? (succeeded / (succeeded + failed) * 100).toFixed(1) + '%' : '—';
    const data = [
        ['今日已完成', state.source === 'assigned' ? count(assignedSummary?.todayCompleted) : state.emp === 'archive' ? count(summary?.todayCompleted) : '—', '项', state.source === 'assigned' ? '今日审核完成的派活任务' : '北京时间 · OCR 成功任务', 'check', 'green'],
        [state.source !== 'assigned' && state.emp === 'assess' ? '指标预警' : '当前待办', count(state.source !== 'assigned' && state.emp === 'assess' ? total : pending()), '项', state.source === 'assigned' ? '等待人工处理的派活任务' : state.emp === 'assess' ? '当前医生可见预警' : '等待领取或重试', 'clipboard', 'orange'],
        ['任务成功率', state.source !== 'assigned' && state.emp === 'archive' ? rate : '—', '', state.source === 'assigned' ? '派活任务经人工审核确认完成' : '累计成功 /（成功 + 失败）', 'activity', ''],
        ['人工待审核', state.source === 'assigned' ? count(assignedSummary?.review) : state.emp === 'archive' ? count(statistics?.pending) : '—', state.source === 'assigned' ? '项' : '人', state.source === 'assigned' ? '等待医生审核的派活任务' : '当前组织待审核档案人数', 'shield', 'red']
    ];
    return data.map(([label,value,unit,hint,icon,color]) => `<div class="stat"><div><div class="eyebrow">${label}</div>
        <div><strong>${value}</strong><span class="suffix">${value === '—' ? '' : unit}</span></div>
        <div class="hint">${state.source !== 'assigned' && state.emp !== 'archive' && label !== '指标预警' ? '数据尚未接入' : hint}</div></div>
        <span class="stat-icon ${color}">${I(icon,'lg')}</span></div>`).join('');
}
function pageHeader(title, desc) {
    document.getElementById('crumbCurrent').textContent = title;
    return `<div class="page-head"><div><div class="page-kicker">${I('sparkle','sm')} DIGITAL WORKFORCE · 矿安智康</div>
        <h1>${title}</h1><p>${desc}</p></div><div class="head-acts"><button class="btn" data-act="overview">${I('back')} 返回员工总览</button>
        ${state.emp === 'archive' ? '<a class="btn" href="' + escapeHtml(service.uploadLink()) + '" target="_blank" rel="noopener">布置报告识别任务</a>' : ''}
        <button class="btn primary" data-act="assign">${I('play')} 给 TA 派活</button></div></div>`;
}
function rail() {
    return `<nav class="employee-rail" aria-label="五位数字员工"><div class="rail-label">数字员工 · 05</div>${employees.map(item =>
        `<button class="employee-mini ${item.id === state.emp ? 'active' : ''}" data-emp="${item.id}"
        aria-label="切换到${item.name}" aria-pressed="${item.id === state.emp}"><span class="rail-state">
        ${item.id === state.emp ? '当前工作台' : '可派活'}</span>
        <span class="mini-avatar">${svgAvatar(item)}<i class="emp-dot"></i></span><b>${item.name}</b><small>${item.role}</small></button>`).join('')}</nav>`;
}
function taskHTML(task) {
    return `<div class="task"><span class="task-ico ${task.status === 'failed' ? 'red' : ['succeeded','completed'].includes(task.status) ? 'green' : 'blue'}">
        ${I(['succeeded','completed'].includes(task.status) ? 'check' : 'clipboard')}</span><div class="task-middle"><div class="task-title">
        ${escapeHtml(task.title)} <span class="tag">${escapeHtml(task.label)}</span> ${task.priority ? '<span class="tag">' + escapeHtml(window.DigitalWorkerDispatch.priorities[task.priority]) + '</span>' : ''}</div>
        <div class="task-sub">${escapeHtml(task.subject)} · ${escapeHtml(task.time)} · ${escapeHtml(task.id)}</div></div>
        <button class="btn small primary" data-task="${escapeHtml(task.id)}">查看详情</button></div>`;
}
function taskPanel() {
    const content = loading ? '<div style="padding:30px;text-align:center">正在读取真实任务…</div>' : error ?
        '<p role="alert">' + escapeHtml(error) + ' <button class="btn small" data-act="refresh">重试</button></p>' :
        tasks.length ? tasks.map(taskHTML).join('') : '<div style="text-align:center;padding:30px 0;color:#95a9b7">' +
            (state.source !== 'assigned' && state.emp === 'assess' && state.filter !== 'all' ? '预警状态尚未映射为任务状态，请选择“全部”查看。' :
            (state.source === 'assigned' || ['archive','assess'].includes(state.emp)) ? '当前筛选条件下暂无任务' : '该员工的正式任务队列待接入') + '</div>';
    return `<div class="task-filters" style="margin-bottom:10px"><button class="filter ${state.source === 'assigned' ? 'active' : ''}" data-source="assigned">派活任务</button>
    <button class="filter ${state.source === 'business' ? 'active' : ''}" data-source="business">${state.emp === 'archive' ? '报告 OCR' : '业务数据'}</button>
    <button class="filter" data-act="refresh">刷新</button></div><div class="task-filters" style="margin-bottom:14px">${[['all','全部'],['pending','待处理'],['running','执行中'],['done','已完成']]
        .map(([id,label]) => `<button class="filter ${state.filter === id ? 'active' : ''}" data-filter="${id}">${label}</button>`).join('')}</div>
        <div class="tasks-list" aria-live="polite">${content}</div><div class="task-footer"><span>共 ${count(total)} 条 · 第 ${state.page} 页</span>
        <div><button class="btn small" data-act="previous" ${loading || state.page <= 1 ? 'disabled' : ''}>上一页</button>
        <button class="btn small" data-act="next" ${loading || total == null || state.page * 5 >= total || (state.source !== 'assigned' && state.emp === 'assess' && state.filter !== 'all') ? 'disabled' : ''}>下一页</button></div></div>`;
}
function insights() {
    const items = state.source === 'assigned' ? [
        ['任务分配', '派活任务已保存到当前单位任务队列，工作人员处理后提交审核。'],
        ['当前待办', '待处理 '+count(assignedSummary?.pending)+' 项，待审核 '+count(assignedSummary?.review)+' 项。'],
        ['执行方式', '任务内容由工作人员核对处理；档案修改、通知和干预在原业务模块办理。']
    ] : state.emp === 'archive' ? [
        ['重点关注', statistics ? '当前组织有 ' + count(statistics.total) + ' 份职工档案，' + count(statistics.pending) + ' 人待审核。' : '档案统计暂未获取。'],
        ['处理建议', summary ? '待领取或重试 ' + count(summary.pending) + ' 项，识别中 ' + count(summary.running) + ' 项。' : 'OCR 队列统计暂未获取。'],
        ['服务状态', recognition ? (recognition.enabled ? '识别服务已配置，处理进度以任务记录为准。' : '识别服务未启用，已入队任务需要等待处理服务。') : '识别服务配置暂未获取。']
    ] : state.emp === 'assess' ? [['指标预警', total == null ? '预警数据暂未获取。' : '当前医生可见 ' + count(total) + ' 条预警。'],
        ['处理建议','请在原指标预警管理页面核对原始报告与处置记录。'],['分析状态','自动洞察生成服务待接入。']] :
        [['接入状态', worker().source],['处理流程','请通过已有业务页面办理正式服务。'],['分析状态','自动洞察生成服务待接入。']];
    return items.map(([title,text], index) => `<div class="insight"><span class="insight-index">${String(index + 1).padStart(2,'0')}</span>
        <div><b>${title}</b><p>${escapeHtml(text)}</p></div></div>`).join('') +
        '<div class="insight-footer">' + I('sparkle') + '<span>以上为业务数据摘要。AI 洞察生成待接入。</span></div>';
}
function tabContent(employee) {
    if (state.tab === 'limits') return '<div class="notice-list">' + [
        '可以：查询已授权的档案、识别任务与指标预警，核对结果来源。',
        '健康结论、通知发送和档案变更均需授权人员审核。',
        '异常指标、参考区间缺失或报告冲突应交由专业人员核验。'
    ].map(text => '<div class="notice-item">' + I('shield') + '<span>' + text + '</span></div>').join('') + '</div>';
    if (state.tab === 'connect') return '<div class="notice-list">' + [worker().source, status(),
        '档案按当前组织隔离，指标预警按当前医生权限读取。'].map(text =>
            '<div class="notice-item">' + I('check') + '<span>' + escapeHtml(text) + '</span></div>').join('') + '</div>';
    return '<div class="ability-grid">' + employee.strength.map((text,index) => '<div class="ability-item"><div class="ability-ico">' +
        I(['activity','file','shield','pulse','list','brain'][index % 6]) + '</div><div><strong>' + text +
        '</strong><p>' + (['archive','assess'].includes(employee.id) ? '已接入数据供核对，正式处理在原业务页面办理' : '岗位能力规划，业务接口待接入') +
        '</p></div></div>').join('') + '</div>';
}
function activitiesHTML() {
    return recent.length ? recent.map(task => `<div class="activity"><span class="activity-dot"></span><div>
        <time>${escapeHtml(service.timestamp(task.updatedAt))} · ${emp().name}</time><br/><b>${task.assigned ? escapeHtml(task.title) : '报告 OCR'} · ${escapeHtml((task.assigned ? window.DigitalWorkerDispatch.statuses : service.states)[task.state] || '未知')}</b>
        <p>工号 ${escapeHtml(task.employeeNo || '—')} · ${escapeHtml(task.jobId)}</p></div></div>`).join('') :
        '<p class="muted">' + (state.emp === 'archive' ? '暂无已结束的执行记录，或数据暂未获取。' : '该岗位的执行记录待接入。') + '</p>';
}
function foot() {
    return '<footer class="bottom-foot"><span>矿安智康 · 数字员工协作系统</span><span id="workbench-sync" role="status"></span></footer>';
}
function syncStatus() {
    const status = document.getElementById('workbench-sync');
    if (!status) return;
    const last = lastSynced ? '最近同步 ' + new Date(lastSynced).toLocaleTimeString('zh-CN', {hour12:false}) : '尚未同步成功';
    status.textContent = (syncFailed ? '部分数据同步失败 · ' : loading ? '正在同步 · ' : '') + last + ' · 每15秒刷新';
}
function render() {
    const focused = document.activeElement;
    const attribute = ['data-filter','data-emp','data-tab','data-act','data-task'].find(key => focused?.hasAttribute(key));
    const focus = attribute ? [attribute, focused.getAttribute(attribute)] : null;
    const left = window.scrollX, top = window.scrollY;
    document.getElementById('pageRoot').innerHTML = detail();
    if (focus) {
        Array.from(document.querySelectorAll('[' + focus[0] + ']')).find(element =>
            element.getAttribute(focus[0]) === focus[1])?.focus({preventScroll:true});
    }
    window.scrollTo(left, top);
    syncStatus();
}
async function load(silent = false) {
    if (silent && loading) return;
    const current = ++generation;
    loading = true; error = ''; partialFailure = false;
    if (!silent) { tasks = []; total = null; render(); }
    else syncStatus();
    const safe = promise => promise.catch(() => {
        if (current === generation) partialFailure = true;
        return null;
    });
    if (state.source === 'assigned') {
        const filter = {all:'',pending:'queued',running:'working',done:'finished'}[state.filter];
        const [data,results] = await Promise.all([
            service.assignments({worker:state.emp,status:filter,page:state.page,limit:5})
                .catch(e=>{if(current===generation){error=e.message;partialFailure=true;}return null;}),
            safe(service.assignments({worker:state.emp,status:'finished',page:1,limit:4}))
        ]);
        if(current!==generation) return;
        assignedSummary=data?.summary || (silent ? assignedSummary : null);
        total=data?.total ?? (silent ? total : null);
        tasks=data ? data.items.map(task=>({...task,assigned:true,label:window.DigitalWorkerDispatch.statuses[task.status],
            subject:task.memberCode ? '会员编码 '+task.memberCode : '未关联会员',time:service.timestamp(task.updatedAt)})) : (silent ? tasks : []);
        recent=results ? results.items.map(task=>({...task,assigned:true,state:task.status,jobId:task.id,employeeNo:task.memberCode})) : (silent ? recent : []);
    } else if (state.emp === 'archive') {
        const filter = { all: 'all', pending: 'waiting', running: 'running', done: 'finished' }[state.filter];
        const [queue, counts, stats, config, results] = await Promise.all([
            service.tasks(filter,state.page,5).catch(e => { if (current === generation) { error = e.message; partialFailure = true; } return null; }),
            safe(service.summary()), safe(service.statistics()), safe(service.recognition()), safe(service.tasks('finished',1,4))
        ]);
        if (current !== generation) return;
        summary = counts || (silent ? summary : null);
        statistics = stats || (silent ? statistics : null);
        recognition = config || (silent ? recognition : null);
        recent = results?.items || (silent ? recent : []);
        total = queue?.total ?? (silent ? total : null);
        tasks = queue ? queue.items.map(task => ({id:task.jobId,title:'体检报告识别',status:task.state,
            label:service.states[task.state] || '未知',subject:'工号 ' + (task.employeeNo || '—'),
            time:service.timestamp(task.updatedAt),recordId:task.recordId})) : (silent ? tasks : []);
    } else if (state.emp === 'assess') {
        try {
            const data = await service.alerts({page:state.page,limit:5});
            if (current !== generation) return;
            total = data.total;
            tasks = state.filter === 'all' ? data.items.map(item => ({id:item.id,title:item.title,status:'alert',
                label:'来源状态 ' + item.status,subject:'指标预警',time:item.time})) : [];
        } catch(e) { if (current === generation) { error = e.message; partialFailure = true; } }
        if (current !== generation) return;
        recent = [];
    } else recent = [];
    if (current !== generation) return;
    loading = false; syncFailed = partialFailure;
    if (!syncFailed) lastSynced = Date.now();
    render();
    if (silent && openTask) await taskDrawer(openTask.id, true);
}
function switchEmployee(id) {
    if (!employees.some(item => item.id === id)) return;
    lastSynced = null; syncFailed = false;
    state.emp = id; state.source = 'assigned'; assignedSummary = null; state.filter = 'all'; state.tab = 'ability'; state.page = 1;
    summary = null; statistics = null; recognition = null; recent = [];
    history.replaceState(null,'','#' + id);
    if (window.parent !== window && window.parent.saveDigitalWorkerSelection) {
        window.parent.saveDigitalWorkerSelection(id);
    }
    load();
}
function drawer(title,html) {
    drawerGeneration++;
    previousFocus = document.activeElement;
    document.getElementById('drawerTitle').textContent = title;
    document.getElementById('drawerContent').innerHTML = html;
    document.getElementById('drawerOverlay').classList.add('open');
    document.body.style.overflow = 'hidden';
    document.getElementById('closeDrawer').focus();
}
function closeDrawer() {
    drawerGeneration++;
    openTask = null;
    document.getElementById('drawerOverlay').classList.remove('open');
    document.body.style.overflow = '';
    if (previousFocus?.isConnected) previousFocus.focus({preventScroll:true});
    else document.querySelector('[data-act="handover"]')?.focus({preventScroll:true});
}
function handoverDrawer() {
    drawer('人工接管与审核','<div class="drawer-info">选择原业务入口办理报告核对、识别重试、取消或审核。权限校验和操作记录由原业务模块保存。</div>' +
        '<div class="drawer-acts"><button class="btn" data-close>关闭</button><a class="btn primary" href="' +
        escapeHtml(service.archiveLink()) + '" target="_blank" rel="noopener">进入档案审核与归档</a></div>');
}
async function taskDrawer(id, silent = false) {
    const selected = silent ? openTask : tasks.find(item => item.id === id);
    if (!selected) return;
    if (selected.assigned) {
        window.DigitalWorkerDispatch.detail(id,()=>load(true));
        return;
    }
    if (state.emp === 'assess') {
        drawer('指标预警详情','<div class="drawer-info">' + escapeHtml(selected.title) + '<br/>' +
            escapeHtml(selected.label) + '<br/>' + escapeHtml(selected.time) +
            '</div><div class="drawer-acts"><button class="btn" data-close>关闭</button></div>');
        return;
    }
    if (!silent) drawer('OCR 执行详情','<p>正在读取执行记录…</p>');
    openTask = selected;
    const current = drawerGeneration;
    try {
        const data = await service.task(id);
        if (current !== drawerGeneration) return;
        const content = '<div class="drawer-info">任务编号 ' + escapeHtml(id) +
            '<br/>状态 ' + escapeHtml(service.states[data.state] || '未知') + ' · ' + escapeHtml(data.percent ?? '—') +
            '%<br/>最后更新 ' + escapeHtml(service.timestamp(data.updatedAt)) + '</div><div class="detail-list">' +
            (data.attempts || []).map(attempt => '<p>第 ' + escapeHtml(attempt.attemptNumber) + ' 次 · ' +
                escapeHtml(service.states[attempt.outcome] || ({expired:'租约到期',running:'处理中'})[attempt.outcome] || '未知') +
                ' · ' + escapeHtml(service.timestamp(attempt.startedAt)) + '</p>').join('') +
            '</div><div class="drawer-acts"><button class="btn" data-close>关闭</button><a class="btn primary" href="' +
            escapeHtml(service.archiveLink(selected.recordId)) + '" target="_blank" rel="noopener">前往报告核对与处理</a></div>';
        const target = document.getElementById('drawerContent');
        if (target.innerHTML !== content) {
            const oldFocus = document.activeElement;
            const closeFocused = target.contains(oldFocus) && oldFocus.matches('[data-close]');
            const focusedLink = target.contains(oldFocus) && oldFocus.matches('a[href]') ? oldFocus.getAttribute('href') : null;
            target.innerHTML = content;
            if (closeFocused) target.querySelector('[data-close]').focus({preventScroll:true});
            if (focusedLink) Array.from(target.querySelectorAll('a[href]')).find(link => link.getAttribute('href') === focusedLink)?.focus({preventScroll:true});
        }
    } catch(e) { if (current === drawerGeneration) document.getElementById('drawerContent').textContent = e.message; }
}
document.addEventListener('click',event => {
    const button = event.target.closest('button');
    if (event.target.id === 'drawerOverlay') closeDrawer();
    if (!button) return;
    if (button.dataset.emp) switchEmployee(button.dataset.emp);
    else if (button.dataset.source) { state.source=button.dataset.source; state.filter='all'; state.page=1; load(); }
    else if (button.dataset.act === 'assign') window.DigitalWorkerDispatch.assign(state.emp,()=>{state.source='assigned';state.filter='all';state.page=1;load();});
    else if (button.dataset.task) taskDrawer(button.dataset.task);
    else if (button.hasAttribute('data-close') || button.id === 'closeDrawer') closeDrawer();
    else if (button.dataset.tab) { state.tab = button.dataset.tab; render(); document.querySelector('[data-tab="' + state.tab + '"]').focus(); }
    else if (button.dataset.filter) { state.filter = button.dataset.filter; state.page = 1; load(); }
    else if (button.dataset.act === 'connect') { state.tab = 'connect'; render(); document.getElementById('tabBody').scrollIntoView({block:'center'}); }
    else if (button.dataset.act === 'handover') handoverDrawer();
    else if (['overview','dispatch','records'].includes(button.dataset.act)) service.open(button.dataset.act,{worker:state.emp});
    else if (button.dataset.act === 'refresh') load();
    else if (button.dataset.act === 'previous') { state.page--; load(); }
    else if (button.dataset.act === 'next') { state.page++; load(); }
});
document.addEventListener('keydown',event => {
    const overlay = document.getElementById('drawerOverlay');
    if (!overlay.classList.contains('open')) return;
    if (event.key === 'Escape') closeDrawer();
    if (event.key !== 'Tab') return;
    const items = Array.from(overlay.querySelectorAll('button:not(:disabled),a[href]'));
    const first = items[0], last = items.at(-1);
    if (event.shiftKey && document.activeElement === first) { event.preventDefault(); last.focus(); }
    else if (!event.shiftKey && document.activeElement === last) { event.preventDefault(); first.focus(); }
});
function tick() { document.getElementById('clock').textContent = new Date().toLocaleString('zh-CN',{hour12:false}); }
const requested = location.hash.slice(1) || new URLSearchParams(location.search).get('worker') || 'assess';
switchEmployee(requested === 'psych' ? 'care' : employees.some(item => item.id === requested) ? requested : 'assess');
tick();
setInterval(tick,60000);
service.watch(() => load(true));
})();
