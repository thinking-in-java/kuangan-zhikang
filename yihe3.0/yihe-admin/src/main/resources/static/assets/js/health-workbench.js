(function () {
'use strict';
const $ = id => document.getElementById(id), service = window.DigitalWorkerService;
const safe = service.escape, count = n => Number.isFinite(n) ? n.toLocaleString('zh-CN') : '—';
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

const AVATARS=Object.fromEntries(employees.map(e=>[e.id,svgAvatar(e)]));
const base = document.body.dataset.base, fallback=window.HealthWorkbenchData;
let localAlerts=[];
let stats, hub, busy = false;
const charts = [];
function emptyChart(id, message) {const current=echarts.getInstanceByDom($(id));if(current){current.dispose();const i=charts.indexOf(current);if(i>=0)charts.splice(i,1);}$(id).innerHTML='<div class="empty">'+safe(message)+'</div>';}

function chart(id, option) {let instance = echarts.getInstanceByDom($(id)); if (!instance) {$(id).innerHTML='';instance = echarts.init($(id)); charts.push(instance);} instance.setOption(option, true);}
const colors = ['#0ac8a3','#188dff','#ff9d29','#ff5267','#d4dfea'];
function ring(id, values, title, subtitle) {const hasData=values.some(v=>v[1]>0);chart(id, {color:colors,tooltip:{trigger:'item',formatter:'{b}：{c}'},title:{text:title,subtext:subtitle,left:'center',top:'36%',textStyle:{fontSize:25,color:'#152d48'},subtextStyle:{fontSize:11,color:'#71869e'}},series:[{type:'pie',radius:['72%','88%'],center:['50%','52%'],label:{show:false},stillShowZeroSum:false,data:hasData?values.map(v=>({name:v[0],value:v[1]})):[{value:1,itemStyle:{color:'#e6eef7'},tooltip:{show:false},emphasis:{disabled:true}}]}]});}
function legend(id, items) {$(id).innerHTML = items.map((v,i)=>'<div class="legend-row"><span><i style="background:'+colors[i]+'"></i>'+safe(v[0])+'</span><b>'+count(v[1])+'<small>人</small></b></div>').join('');}
function renderStats() {
 const s=stats||{}, follow=hub?.byWorker?.follow;
 const cards=[['在档职工',s.total,'人','当前授权范围','#1769b8','#edf6ff','♧'],['健康档案',s.total,'份','按职工一人一档统计','#0ab69e','#ebfbf7','▤'],['本月新增档案',s.month,'份','本月创建的职工档案','#b76d29','#fff7ed','▧'],['待评估',s.unknown,'人','尚无已确认风险评估','#ee8a27','#fff5ea','⌁'],['高风险人群',stats?s.high+s.critical:null,'人','高风险及极高风险合计','#ef4c66','#fff0f3','△'],['进行中随访',follow?.active,'项','随访小谷活跃任务','#8257e8','#f4efff','▦']];
 $('metrics').innerHTML=cards.map(c=>'<article class="metric" style="--accent:'+c[4]+';--tint:'+c[5]+'"><span class="metric-icon" aria-hidden="true">'+c[6]+'</span><div><div class="metric-label">'+c[0]+'</div><strong>'+count(c[1])+'</strong><span class="unit">'+c[2]+'</span><small>'+c[3]+'</small></div></article>').join('');
 if(stats){const risks=[['低风险',s.low],['中风险',s.medium],['高风险',s.high],['极高风险',s.critical],['未评估',s.unknown]];ring('risk-chart',risks,count(s.total),'在档职工');legend('risk-legend',risks);} else {emptyChart('risk-chart','风险统计暂不可用'); $('risk-legend').innerHTML='';}
 renderTrend();
}
function renderTrend() {
 const abnormal=$('trend-mode').value==='abnormal';$('trend-empty').hidden=true;$('trend-chart').hidden=false;$('trend-title').textContent=abnormal?'近30日健康异常趋势':'健康档案增长趋势';$('trend-chart').setAttribute('aria-label',abnormal?'近30日健康异常与高风险趋势':'档案增长趋势');$('trend-note').textContent=abnormal?'健康异常动态追踪 · 风险趋势持续关注':'近12个月新增档案人数';
 const dates=Array.from({length:30},(_,i)=>{const d=new Date();d.setDate(d.getDate()-29+i);return String(d.getMonth()+1).padStart(2,'0')+'-'+String(d.getDate()).padStart(2,'0');});
 const one=[22,48,26,13,18,35,19,28,75,52,24,41,102,57,20,16,27,22,33,28,36,42,162,91,39,20,15,24,29,26],two=[5,10,8,3,7,10,6,12,24,18,7,13,31,16,8,7,11,8,14,12,16,21,73,38,19,12,8,13,15,11];
 const growth=stats?.growth||fallback.statistics().growth;
 const area=color=>({color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:color+'70'},{offset:1,color:color+'05'}])});
 chart('trend-chart',{color:['#06baa4','#188dff'],tooltip:{trigger:'axis'},legend:{show:abnormal,right:0,top:0,itemWidth:10,itemHeight:5,textStyle:{fontSize:9,color:'#7890aa'}},grid:{left:35,right:12,top:abnormal?30:17,bottom:22},xAxis:{type:'category',boundaryGap:false,data:abnormal?dates:growth.map(x=>x.month.slice(2)),axisLine:{lineStyle:{color:'#d4e3f1'}},axisLabel:{color:'#7890aa',fontSize:9}},yAxis:{type:'value',minInterval:1,axisLabel:{color:'#7890aa',fontSize:9},splitLine:{lineStyle:{color:'#e7eff8',type:'dashed'}}},series:abnormal?[{name:'体检异常',type:'line',smooth:true,symbol:'none',data:one,lineStyle:{width:2},areaStyle:area('#06baa4')},{name:'高风险',type:'line',smooth:true,symbol:'none',data:two,lineStyle:{width:2},areaStyle:area('#188dff')}]:[{name:'新增档案人数',type:'line',smooth:true,symbol:'circle',symbolSize:4,data:growth.map(x=>x.count),lineStyle:{width:2},areaStyle:area('#06baa4')}]});charts.forEach(c=>c.resize());
}
function renderWorkers() {
 const roles=['职工档案 · 报告归档','风险分析 · 指标评估','随访提醒 · 跟进反馈','干预方案 · 计划管理','心理科普 · 关怀支持'];
 $('workers').innerHTML=service.workers.map((w,i)=>{const v=hub?.byWorker?.[w.id];return '<button class="worker-card" data-worker="'+w.id+'">'+AVATARS[w.id]+'<h3>'+w.name+'</h3><span class="worker-state">'+(v?(v.active?'任务处理中':'暂无活跃任务'):'状态未获取')+'</span><p>累计完成 <strong>'+count(v?.completed)+'</strong> 项</p><small>'+roles[i]+'</small></button>';}).join('');
 const plan=hub?.byWorker?.plan;
 if(plan){const rate=plan.total?Math.round(plan.completed/plan.total*1000)/10:null;ring('plan-chart',[['已完成',plan.completed],['其余任务',plan.total-plan.completed]],rate===null?'—':rate+'%',plan.total?'任务完成率':'暂无方案任务');$('plan-legend').innerHTML=[['计划任务',plan.total],['已完成',plan.completed],['进行中',plan.active],['失败',plan.failed]].map((x,i)=>'<div class="legend-row"><span><i style="background:'+colors[i]+'"></i>'+x[0]+'</span><b>'+count(x[1])+'<small>项</small></b></div>').join('');}else{emptyChart('plan-chart','方案统计暂不可用');$('plan-legend').innerHTML='';}
}
async function requestContext(){const r=await fetch(base+'ehr/api/context',{credentials:'same-origin',cache:'no-store',headers:{'X-Requested-With':'XMLHttpRequest'},signal:AbortSignal.timeout(12000)});if(!r.ok)throw Error('登录已失效');const b=await r.json();if(b.code!==0)throw Error('无法读取账号');return b.data;}
async function refresh(){
 if(busy)return;busy=true;$('refresh').disabled=true;$('data-status').textContent='正在同步工作台数据…';
 const read=p=>p.then(value=>({value}),error=>({error}));
 try{const [context,statistics,summary,alerts,todos]=await Promise.all([read(requestContext()),read(service.statistics()),read(service.hubSummary()),read(service.alerts()),Promise.resolve({value:{items:[]}})]);
 stats=statistics.value||fallback.statistics();hub=fallback.fillHub(summary.value);
 if(alerts.error||!alerts.value.items.length){localAlerts=fallback.alerts.slice(0,3);alerts.value={total:fallback.alerts.length,items:localAlerts.map(a=>({...a,title:a.type}))};alerts.error=null;}else{localAlerts=[];}
 const user=context.value?.user;const name=document.body.dataset.user||(typeof user==='object'&&(user?.name||user?.userName||user?.username))||'健康管理师';
 const hour=Number(new Date().toLocaleString('en-US',{hour:'numeric',hour12:false,timeZone:'Asia/Shanghai'}));$('greeting').textContent=(hour<12?'上午好，':hour<18?'下午好，':'晚上好，')+name;
 $('greeting-note').textContent=hub?'当前有 '+count(hub.summary.active)+' 项活跃任务，'+count(hub.summary.pending_review)+' 项任务待审核。':'健康管理、任务协作与风险动态，一站掌握。';
 renderStats();renderWorkers();
 $('alert-count').textContent=alerts.value?'（'+count(alerts.value.total)+'条）':'';
 $('alert-list').innerHTML=alerts.error?'<div class="empty">预警读取失败，请刷新重试</div>':alerts.value.items.length?alerts.value.items.slice(0,3).map(a=>'<div class="alert-item"><span class="alert-symbol">!</span><div><h3>'+safe(a.title)+'</h3><time>'+safe(a.time||'时间未提供')+'</time></div><button class="text-button" data-alert="'+safe(a.id)+'">处理</button></div>').join(''):'<div class="empty"><b>✓</b>当前账号暂无指标预警</div>';

 const failures=[['账号',context],['档案统计',statistics],['任务汇总',summary],['预警',alerts],['待办',todos]].filter(x=>x[1].error).map(x=>x[0]);
 $('data-status').textContent=failures.length?'部分数据读取失败：'+failures.join('、')+'。可刷新重试；如登录已过期，请重新登录。':'最近同步 '+new Date().toLocaleTimeString('zh-CN',{hour12:false,timeZone:'Asia/Shanghai'})+' · 每30秒刷新';
 }catch(e){$('data-status').textContent='工作台暂时无法加载，请刷新重试。';}finally{busy=false;$('refresh').disabled=false;}
}
function tick(){const now=new Date();$('clock').textContent=now.toLocaleTimeString('zh-CN',{hour12:false,timeZone:'Asia/Shanghai'});$('date').textContent=now.toLocaleDateString('zh-CN',{year:'numeric',month:'2-digit',day:'2-digit',weekday:'long',timeZone:'Asia/Shanghai'});}
$('refresh').addEventListener('click',refresh);$('trend-mode').addEventListener('change',renderTrend);
$('fullscreen').addEventListener('click',()=>{const p=document.fullscreenElement?document.exitFullscreen():document.documentElement.requestFullscreen();p.catch(()=>{$('data-status').textContent='浏览器未允许全屏，可使用浏览器全屏功能。';});});
document.addEventListener('click',e=>{const alert=e.target.closest('[data-alert]');if(alert){const local=localAlerts.find(a=>a.id===alert.dataset.alert);if(local){fallback.detail(local);return;}service.open('dispatch',{source:'real',alert:alert.dataset.alert,worker:'assess'});return;}const w=e.target.closest('[data-worker]');if(w)service.open(w.dataset.page||'workbench',{worker:w.dataset.worker});});
window.addEventListener('resize',()=>charts.forEach(c=>c.resize()));
const clockTimer=setInterval(tick,1000),refreshTimer=setInterval(()=>{if(!document.hidden&&(!window.frameElement||window.frameElement.getClientRects().length))refresh();},30000);
window.addEventListener('pagehide',()=>{clearInterval(clockTimer);clearInterval(refreshTimer);charts.forEach(c=>c.dispose());},{once:true});
renderStats();renderWorkers();tick();refresh();
})();