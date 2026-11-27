(function () {
    'use strict';
    const service=window.DigitalWorkerService;
    const {escape,timestamp}=service;
    const workflow=window.DigitalWorkerTaskResults;
    const types={
        visit_invitation:'随访问询（发会员）',routine_followup:'常规随访执行',alert_handling:'异常预警处置',
        abnormal_followup:'异常随访',plan_adjustment:'慢病方案调整',report_interpretation:'报告解读',
        archive_completion:'档案补全',assessment:'综合评估',complaint_followup:'会员投诉跟进'
    };
    const statuses={queued:'待处理',in_progress:'处理中',pending_review:'待审核',completed:'已完成',cancelled:'已取消'};
    const priorities={normal:'常规',high:'高',urgent:'紧急'};
    const actions={start:'开始处理',submit:'提交审核',approve:'审核通过',return:'退回处理',cancel:'取消任务',created:'派发任务',archive_linked:'关联职工档案',ai_requested:'请求 AI 处理',ai_generated:'处理结果已生成',ai_failed:'AI 处理失败',ai_discarded:'AI 结果未应用'};
    function requestId() {
        if (typeof crypto.randomUUID === 'function') return crypto.randomUUID();
        const bytes=crypto.getRandomValues(new Uint8Array(16));
        bytes[6]=(bytes[6]&15)|64;
        bytes[8]=(bytes[8]&63)|128;
        const hex=Array.from(bytes,value=>value.toString(16).padStart(2,'0')).join('');
        return hex.slice(0,8)+'-'+hex.slice(8,12)+'-'+hex.slice(12,16)+'-'+hex.slice(16,20)+'-'+hex.slice(20);
    }
    let dialog;
    let restoreFocus;
    function close() {
        dialog?.close();
    }
    function show(title,html) {
        const focused=document.activeElement;
        if (!dialog?.contains(focused)) restoreFocus=focused;
        dialog?.remove();
        dialog=document.createElement('dialog');
        dialog.className='dw-dispatch-dialog';
        dialog.setAttribute('aria-labelledby','dispatch-title');
        dialog.innerHTML='<header><h2 id="dispatch-title">'+title+'</h2><button type="button" data-close-dispatch aria-label="关闭">×</button></header>'+html;
        document.body.append(dialog);
        dialog.querySelector('[data-close-dispatch]').onclick=close;
        dialog.addEventListener('click',event=>{
            if(event.target.closest('[data-cancel-dispatch]')) close();
            if(event.target===dialog) {
                const bounds=dialog.getBoundingClientRect();
                if(event.clientX<bounds.left||event.clientX>bounds.right||event.clientY<bounds.top||event.clientY>bounds.bottom) close();
            }
        });
        dialog.addEventListener('close',()=>{
            if(restoreFocus?.isConnected) restoreFocus.focus({preventScroll:true});
            else document.querySelector('[data-act=assign],[data-give-task]')?.focus({preventScroll:true});
        },{once:true});
        dialog.showModal();
    }
    function options(values,selected) {
        return Object.entries(values).map(([id,label])=>'<option value="'+id+'"'+(id===selected?' selected':'')+'>'+label+'</option>').join('');
    }
    function archivePicker(root,onSelect) {
        root.innerHTML='<label>查找职工档案（姓名或工号）<div class="dispatch-archive-search"><input data-archive-search maxlength="100" placeholder="如：解小敏 或 105955"><button type="button" data-archive-search-button>查找</button></div></label>'+
            '<label data-archive-choices hidden>选择职工（请核对工号、单位）<select data-archive-select><option value="">请选择</option></select></label><p data-archive-search-status role="status"></p>';
        const input=root.querySelector('[data-archive-search]');
        const button=root.querySelector('[data-archive-search-button]');
        const select=root.querySelector('[data-archive-select]');
        const status=root.querySelector('[data-archive-search-status]');
        let generation=0,items=[];
        async function search() {
            const term=input.value.trim(),sequence=++generation;
            items=[];select.replaceChildren(new Option('请选择',''));root.querySelector('[data-archive-choices]').hidden=true;
            onSelect(null);
            if(!term) {status.textContent='请输入姓名或工号后查找。';return;}
            button.disabled=true;status.textContent='正在查找当前单位的授权档案…';
            try {
                const result=await service.searchArchives(term);
                if(sequence!==generation) return;
                items=result.items;
                for(const item of items) select.add(new Option(item.name+' · '+item.code+' · '+item.org,item.id));
                root.querySelector('[data-archive-choices]').hidden=!items.length;
                status.textContent=items.length?'找到 '+result.total+' 条，展示前20条，请明确选择后关联。':'未找到可访问的职工档案，请核对姓名或工号。';
            }catch(error){if(sequence===generation) status.textContent=error.message;}
            finally{if(sequence===generation) button.disabled=false;}
        }
        button.onclick=search;
        input.addEventListener('keydown',event=>{if(event.key==='Enter'){event.preventDefault();search();}});
        input.addEventListener('input',()=>{generation++;items=[];select.value='';root.querySelector('[data-archive-choices]').hidden=true;button.disabled=false;status.textContent='';onSelect(null);});
        select.onchange=()=>{const item=items.find(item=>item.id===select.value);onSelect(item||null);status.textContent=item?'已选择 '+item.name+'，工号 '+item.code+'。':'';};
    }
    function archiveSummary(archive) {
        if(!archive?.linked) return '<p class="dispatch-note">'+(archive?.unavailable?'关联档案已不可访问，请核对权限或重新关联。':'未关联职工档案。仅在任务标题写姓名不会读取档案，请先关联。')+'</p>';
        const labels={archived:'已归档',approved:'已审核',pending:'待审核',draft:'草稿',rejected:'审核未通过'};
        const report=archive.report;
        return '<div class="dispatch-archive-summary"><p><strong>'+escape(archive.name)+'</strong> · 工号 '+escape(archive.code)+' · '+escape(labels[archive.status]||archive.status)+'</p>'+
            '<p>系统完整度 '+escape(archive.systemCompleteness)+'% · 当前规则核对 '+escape(archive.checkedCompleteness)+'%</p>'+
            '<p>系统缺项：'+escape(archive.missing.length?archive.missing.join('、'):'无（按9项资料规则）')+'</p>'+
            (report?'<p>最新报告 V'+escape(report.revision)+' · '+escape(report.examDate||'日期待核定')+' · '+escape(report.institution||'机构待核对')+'</p><p>原件 '+escape(report.sourceFiles)+' 份 · '+escape(report.pageCount)+' 页 · 检查项目 '+escape(report.itemCount)+' 项</p>':'<p>当前没有报告记录。</p>')+
            '<p>读取时间 '+escape(timestamp(archive.capturedAt))+'；资料完整度不代表临床风险评估结果。</p></div>';
    }
    function assign(worker,onChange=()=>{}) {
        const employee=service.workers.find(item=>item.id===worker);
        if(!employee) return;
        let requestKey=requestId();
        show('给 <span>'+employee.name+'</span> 派活',
            '<form id="dw-assignment-form"><fieldset><label>任务类型<select name="type" required>'+options(types,'archive_completion')+
            '</select></label><label>任务标题<input name="title" required maxlength="120" placeholder="例如：核对本次体检报告并补全档案"></label>'+
            '<div data-task-profile></div>'+
            '<div class="dispatch-two"><label>会员编码（可空）<input name="memberCode" maxlength="100" placeholder="当前单位会员编码或职工工号"></label>'+
            '<label>优先级<select name="priority">'+options(priorities,'normal')+'</select></label></div>'+
            '<input type="hidden" name="recordId" value=""><div data-archive-picker></div>'+
            '<label>要 TA 注意什么（可空）<textarea name="instructions" rows="3" maxlength="2000" placeholder="补充任务背景、处理要求和完成标准"></textarea></label>'+
            '<label class="dispatch-ai-option"><input name="useAi" type="checkbox" disabled>交由 AI 处理，完成后提交医生审核</label>'+
            '<p class="dispatch-note" data-ai-config>正在检查数字员工处理服务…</p></fieldset>'+
            '<p data-error role="alert"></p><footer><button type="button" data-cancel-dispatch>取消</button><button type="submit">交给 TA</button></footer></form>');
        const current=dialog;
        const form=dialog.querySelector('form');
        let workerConfig;
        const settingsPromise=service.workerSettings().then(data=>{
            workerConfig=data.items.find(item=>item.worker===worker);
            if(!workerConfig)throw new Error('岗位配置暂不可用，请刷新重试');
            form.querySelector('[name=priority]').value=workerConfig.defaultPriority;
            const blocked=!workerConfig.acceptingTasks||workerConfig.activeTasks>=workerConfig.maxActiveTasks;
            form.dataset.settingsBlocked=String(blocked);
            form.querySelector('[type=submit]').disabled=blocked;
            if(blocked)form.querySelector('[data-error]').textContent=workerConfig.acceptingTasks?'活跃任务已达配置上限，请先处理既有任务或调整配置。':'该员工已暂停接收派活，请先调整岗位配置。';
            return workerConfig;
        });
        workflow.assignment(form,worker,settingsPromise);
        Promise.all([service.modelStatus(),settingsPromise]).then(([status,config])=>{
            const checkbox=form.querySelector('[name=useAi]');
            checkbox.disabled=!status.configured||!config.aiEnabled;
            checkbox.checked=Boolean(status.configured&&config.aiEnabled&&config.defaultUseAi);
            form.querySelector('[data-ai-config]').textContent=!config.aiEnabled?'该岗位已关闭AI处理，可保存任务后人工处理。':status.configured?
                '将按当前岗位配置处理；结果仍需医生审核，不会自动发消息或修改档案。':'数字员工处理服务暂不可用，可先保存任务后人工处理。';
        }).catch(error=>{form.querySelector('[data-ai-config]').textContent=error.message;});
        let selectedMember='';
        archivePicker(form.querySelector('[data-archive-picker]'),item=>{
            form.querySelector('[name=recordId]').value=item?.id||'';
            const member=form.querySelector('[name=memberCode]');
            if(item) member.value=item.code;
            else if(member.value===selectedMember) member.value='';
            selectedMember=item?.code||'';
            requestKey=requestId();
        });
        form.querySelector('[name=memberCode]').addEventListener('input',()=>{form.querySelector('[name=recordId]').value='';});
        form.addEventListener('input',()=>{requestKey=requestId();});
        form.onsubmit=async event=>{
            event.preventDefault();
            if(!form.reportValidity()) return;
            if(form.dataset.settingsBlocked==='true')return;
            if(form.dataset.profileReady!=='true'){form.querySelector('[data-error]').textContent='任务处理要求尚未载入，请稍候或刷新重试。';return;}
            const body={...Object.fromEntries(new FormData(form)),worker,requestKey};
            body.taskContext=workflow.collect(form.querySelector('[data-task-profile]'));
            const useAi=body.useAi==='on';
            delete body.useAi;
            form.querySelector('fieldset').disabled=true;
            form.querySelector('[type=submit]').disabled=true;
            form.querySelector('[data-error]').textContent='';
            try {
                const task=await service.assign(body);
                let aiError='';
                if(useAi) {
                    try {await service.generate(task.id,task.version);}
                    catch(error){aiError=error.message;}
                }
                if(current===dialog) {
                    current.close();
                    await detail(task.id,onChange);
                    if(aiError&&dialog?.open) {
                        const message=document.createElement('p');
                        message.textContent='任务已保存。'+aiError;
                        message.setAttribute('role','status');
                        dialog.querySelector('.dispatch-content')?.prepend(message);
                    }
                }
                onChange();
            } catch(error) {
                form.querySelector('[data-error]').textContent=error.message;
            } finally {
                form.querySelector('fieldset').disabled=false;
                form.querySelector('[type=submit]').disabled=false;
            }
        };
    }
    function originalLink(task,sourceId,page=1) {
        return document.body.dataset.base+'ehr/api/digital-worker-tasks/'+encodeURIComponent(task.id)+'/originals/'+encodeURIComponent(sourceId)+'#page='+encodeURIComponent(page);
    }
    function checkValue(value,unit,range,abnormal) {
        const text=String(value??'');
        const content=text.length>180?'<details><summary>'+escape(text.slice(0,100))+'…</summary><p class="check-long-value">'+escape(text)+'</p></details>':escape(text||'未记录');
        return content+([unit,range,abnormal].some(Boolean)?'<small>'+[unit?'单位 '+unit:'',range?'参考范围 '+range:'',abnormal?'标记 '+abnormal:''].filter(Boolean).map(escape).join(' · ')+'</small>':'');
    }
    function verificationHtml(task) {
        const result=task.ai?.verification;
        if(!result) return '';
        const sources=result.sources||[];
        return '<section class="original-verification" aria-label="原件核对结果"><p class="check-summary">'+escape(result.summary)+'</p>'+
            (result.state==='blocked'?'<p class="check-blocked">本次核对未完成，不能作为通过依据。</p>':
            '<dl class="check-counts"><div><dt>原文一致</dt><dd>'+escape(result.matched)+'</dd></div><div><dt>内容差异</dt><dd>'+escape(result.differences)+'</dd></div><div><dt>格式差异</dt><dd>'+escape(result.formatting||0)+'</dd></div><div><dt>无法核实</dt><dd>'+escape(result.unverified)+'</dd></div></dl>')+
            '<p class="check-meta">核对时间 '+escape(timestamp(result.checkedAt))+' · '+(result.state==='blocked'?'等待处理后重试':task.status==='completed'?'已审核记录':task.status==='cancelled'?'已取消 · 历史结果':'待医生审核')+(sources.length?' · <a href="'+escape(originalLink(task,sources[0].id))+'" target="_blank" rel="noopener">查看原版体检报告</a>':'')+'</p>'+
            (result.modelErrors?.length?'<p class="check-blocked">部分项目未取得完整识别结果，已列入“无法核实”，请按原件复核；未计入一致项目。</p>':'')+
            (sources.length?'<details class="check-source-list"><summary>核对范围与报告原件</summary><ul>'+sources.map((source,index)=>'<li><a href="'+escape(originalLink(task,source.id))+'" target="_blank" rel="noopener">报告原件 '+(index+1)+' · '+escape(source.pages)+' 页</a><span>文件指纹已校验</span></li>').join('')+'</ul><p>将档案录入值与原件逐项对照；仅有来源登记或完整度100%不代表原件核对通过。</p>'+(result.reusedComparison?'<p>原件与档案指纹未变，本次重新读取原件并校验证据后复用比对记录。</p>':'')+'</details>':'')+
            ((result.checks||[]).length?'<div class="check-filters" role="group" aria-label="筛选核对结果"><button type="button" data-check-filter="attention">差异与待复核</button><button type="button" data-check-filter="all">全部项目</button><button type="button" data-check-filter="matched">原文一致</button><button type="button" data-check-filter="different">发现差异</button><button type="button" data-check-filter="unverified">无法核实</button><button type="button" data-check-filter="formatting">格式差异</button><button type="button" data-check-filter="suspected">疑似漏录</button></div><div data-check-results aria-live="polite"></div>':'')+
            '<p class="check-footnote">核对结果不会自动更改档案。差异处理和审核仍由授权人员完成。</p></section>';
    }
    function mountVerification(root,task) {
        const result=task.ai?.verification,container=root.querySelector('[data-check-results]');if(!container)return;
        const statuses={matched:'原文一致',different:'内容差异',unverified:'无法核实',formatting:'格式差异',suspected:'疑似漏录'};
        const groups={identity:'身份与报告信息',item:'检查项目',conclusion:'体检结论',advice:'医师建议',extra:'疑似漏录项目'};
        function render(filter) {
            const rows=result.checks.filter(row=>filter==='all'||filter==='attention'&&row.status!=='matched'||row.status===filter);
            root.querySelectorAll('[data-check-filter]').forEach(button=>button.setAttribute('aria-pressed',String(button.dataset.checkFilter===filter)));
            container.innerHTML=rows.length?'<div class="check-table-scroll"><table class="check-table"><thead><tr><th scope="col">核对项目</th><th scope="col">档案录入值</th><th scope="col">原件值</th><th scope="col">结论与依据</th></tr></thead><tbody>'+rows.map(row=>'<tr><th scope="row">'+escape(row.label)+'<small>'+escape(groups[row.group]||'核对项目')+'</small></th><td>'+(row.group==='extra'?'<span>未匹配到同名项目</span><small>需核对是否使用其他名称</small>':checkValue(row.archiveValue,row.archiveUnit,row.archiveRange,row.archiveAbnormal))+'</td><td>'+(row.status==='unverified'?'<span>尚未核实</span>'+(row.originalValue?'<details><summary>识别候选值（待确认）</summary>'+checkValue(row.originalValue,row.originalUnit,row.originalRange,row.originalAbnormal)+'</details>':''):checkValue(row.originalValue,row.originalUnit,row.originalRange,row.originalAbnormal))+'</td><td><span class="check-status check-status-'+escape(row.status)+'">'+escape(statuses[row.status]||'待复核')+'</span><p>'+escape(row.reason)+'</p>'+(row.sourceId&&row.page?'<a href="'+escape(originalLink(task,row.sourceId,row.page))+'" target="_blank" rel="noopener">查看原件第 '+escape(row.page)+' 页</a>':'')+(row.quote?'<details><summary>'+(row.status==='unverified'?'识别片段（待核实）':'原文证据')+'</summary><blockquote>'+escape(row.quote)+'</blockquote></details>':'')+'</td></tr>').join('')+'</tbody></table></div>':'<p class="check-empty">该筛选下没有项目。</p>';
        }
        root.querySelectorAll('[data-check-filter]').forEach(button=>button.onclick=()=>render(button.dataset.checkFilter));
        render(result.differences||result.unverified||result.extraCount||result.formatting?'attention':'all');
    }
    async function detail(id,onChange=()=>{}) {
        show('派活任务详情','<p class="dispatch-content">正在读取任务…</p>');
        const current=dialog;
        try {
            const task=await service.assignment(id);
            if(current!==dialog||!current.open) return;
            if(task.automation){
                const auto=task.automation,link='/assets/report-center/index.html?automation=1&report='+encodeURIComponent(auto.reportId)+'&revision='+Number(auto.revision);
                show('档案小谷 · 自动处理任务','<div class="dispatch-content"><h3>'+escape(statuses[task.status]||'处理中')+'</h3><p>'+escape(task.resultNote||auto.reason||'正在跟进报告识别与整理')+'</p><p><a href="'+escape(link)+'">打开报告处理详情与审核入口 →</a></p><h3>自动执行记录</h3>'+task.events.map(e=>'<p>'+escape(timestamp(e.time))+' · '+escape(e.actor)+'<br>'+escape(e.note)+'</p>').join('')+'</div>');return;
            }
            const isOriginal=task.type==='archive_completion';
            current.classList.toggle('is-original-check',isOriginal);
            current.classList.toggle('is-task-result',!isOriginal);
            current.classList.toggle('has-verification',Boolean(task.ai?.verification));
            current.classList.toggle('has-task-result',Boolean(task.ai?.taskResult)&&task.ai.state==='succeeded');
            const available={queued:['start','cancel'],in_progress:['submit','cancel'],pending_review:['approve','return','cancel'],
                completed:[],cancelled:[]}[task.status]||[];
            if(task.ai?.verification?.state==='blocked') {const approve=available.indexOf('approve');if(approve!==-1)available.splice(approve,1);}
            if(task.ai?.taskResult&&(task.ai.taskResult.state!=='ready_for_review'||task.ai.outdated)){const approve=available.indexOf('approve');if(approve!==-1)available.splice(approve,1);}
            const employee=service.workers.find(item=>item.id===task.worker);
            const aiLabels={queued:isOriginal?'等待原件核对':'等待处理',running:isOriginal?'正在读取报告原件并逐项核对':'处理中',succeeded:task.ai?.verification?(task.ai.verification.state==='blocked'?'原件核对未完成':'核对记录已生成，待医生审核'):'处理结果已生成，待医生审核',failed:'处理失败',discarded:'任务已变化，结果未应用'};
            const aiBusy=task.ai&&['queued','running'].includes(task.ai.state);
            const canGenerate=task.canManage&&task.workerConfig?.aiEnabled!==false&&!aiBusy&&['queued','in_progress','pending_review'].includes(task.status);
            const canFollowUp=task.canManage&&['completed','cancelled'].includes(task.status)&&task.recordId;
            const legacy=isOriginal&&task.ai?.output&&!task.ai?.verification;
            const aiHtml=(task.ai?.taskResult?'':'<h3>'+(isOriginal?'原件核对结果':'处理结果')+'</h3>')+'<p data-ai-status role="status">'+escape(task.ai?aiLabels[task.ai.state]:'尚未开始处理')+'</p>'+
                (task.ai?.outdated?'<p class="check-blocked">任务资料或关联档案已变化，此结果已过期，请重新处理。</p>':'')+
                (task.ai?.errorCode?'<p>处理未完成，请稍后重试。错误编号 '+escape(task.ai.errorCode)+'</p>':'')+
                (task.ai?.verification?verificationHtml(task):task.ai?.taskResult?workflow.html(task):legacy?'<p class="check-blocked">历史结果仅采用档案摘要，未与报告原件比对。请点击“读取原件重新核对”。</p><details><summary>查看历史说明</summary><div class="dispatch-text">'+escape(task.ai.output)+'</div></details>':task.ai?.output?'<p class="check-blocked">此历史结果为通用说明，请重新生成分类处理结果。</p><details><summary>查看历史说明</summary><div class="dispatch-text">'+escape(task.ai.output)+'</div></details>':'')+
                (task.workerConfig?.aiEnabled===false?'<p class="dispatch-note">该岗位的AI处理已关闭；可在数字员工配置中调整，人工处理仍可使用。</p>':'')+'<div class="check-actions">'+(canGenerate?'<button type="button" data-ai-request>'+(isOriginal?'读取原件'+(task.ai?'重新核对':'并核对'):'读取业务资料并生成结果')+'</button>':'')+(canFollowUp?'<button type="button" data-original-followup>'+(isOriginal?'重新核对原件':'重新处理')+'（新任务）</button>':'')+'<button type="button" data-ai-refresh>刷新结果</button></div><p data-ai-error role="alert"></p>';

            current.querySelector('h2').textContent=task.title;
            current.querySelector('.dispatch-content').outerHTML='<div class="dispatch-content">'+'<p class="check-subject"><strong>'+escape(task.archive?.name||'未关联职工')+' · 工号 '+escape(task.memberCode||'—')+'</strong><span>'+escape(employee?.name)+' · '+statuses[task.status]+'</span></p><details class="check-task-context"><summary>任务信息与关联档案</summary>'+'<p><strong>'+escape(employee?.name)+
                '</strong> · '+escape(types[task.type])+' · '+statuses[task.status]+' · '+priorities[task.priority]+'</p>'+
                (task.memberCode?'<p>会员编码 '+escape(task.memberCode)+'</p>':'')+'<p class="dispatch-text">'+escape(task.instructions||'暂无补充要求')+'</p>'+
                (task.recordId?'<p><a href="'+escape(service.archiveLink(task.recordId))+'" target="_blank" rel="noopener">打开关联健康档案</a></p>':'')+
                '<details class="current-archive"><summary>关联档案：'+escape(task.archive?.name||'尚未关联')+' · '+escape(task.archive?.code||'')+'</summary>'+archiveSummary(task.archive)+'</details>'+
                (task.canManage&&!aiBusy&&!['completed','cancelled'].includes(task.status)?'<details data-link-archive><summary>'+(task.recordId?'更换关联档案':'关联职工档案')+'</summary><div data-archive-picker></div><button type="button" data-save-archive disabled>确认关联档案</button><p data-archive-error role="alert"></p></details>':'')+
                '</details>'+workflow.contextHtml(task,aiBusy)+aiHtml+'<details class="check-history"><summary>查看处理记录</summary><ol class="dispatch-events">'+task.events.map(item=>'<li><strong>'+escape(item.action==='context_saved'?'保存实际资料':actions[item.action]||item.action)+
                    '</strong> · '+escape(timestamp(item.time))+'<br><small>操作人 '+escape(item.actor)+'</small><p class="dispatch-text">'+escape(item.note)+'</p></li>').join('')+'</ol></details>'+
                (task.canManage&&available.length?'<form id="dw-assignment-action"><label>处理操作<select name="action">'+
                    available.map(action=>'<option value="'+action+'">'+actions[action]+'</option>').join('')+'</select></label>'+
                    '<label>处理说明 / 结果<textarea name="note" rows="3" maxlength="2000" required placeholder="填写处理结果、审核意见或取消原因"></textarea></label>'+
                    (task.ai?.verification?'<label class="dispatch-ai-option"><input type="checkbox" name="originalReviewed">已按原件复核差异和无法核实项（审核通过时必选）</label>':'')+(task.ai?.taskResult?'<label class="dispatch-ai-option"><input type="checkbox" name="workflowReviewed">已核验结果依据、资料缺口与实际执行状态（审核通过时必选）</label>':'')+'<p data-error role="alert"></p><footer><button type="submit">确认操作</button></footer></form>':'')+'</div>';
            if(task.ai?.verification) mountVerification(current,task);
            workflow.mount(current,task,()=>{onChange();if(current===dialog&&current.open)detail(id,onChange);});
            current.querySelector('[data-ai-refresh]').onclick=()=>detail(id,onChange);
            const followUp=current.querySelector('[data-original-followup]');
            if(followUp) followUp.onclick=async()=>{
                followUp.disabled=true;
                try {
                    const created=await service.assign({worker:task.worker,type:task.type,title:((isOriginal?'原件复核':'重新处理')+' · '+task.title).slice(0,120),memberCode:task.memberCode||'',recordId:task.recordId,priority:task.priority,instructions:task.instructions,taskContext:task.taskContext||{},requestKey:requestId()});
                    let errorMessage='';try{await service.generate(created.id,created.version);}catch(error){errorMessage=error.message;}
                    onChange();if(current===dialog&&current.open){await detail(created.id,onChange);if(errorMessage)dialog.querySelector('[data-ai-error]').textContent='新任务已保存。'+errorMessage;}
                }catch(error){current.querySelector('[data-ai-error]').textContent=error.message;followUp.disabled=false;}
            };
            const link=current.querySelector('[data-link-archive]');
            if(link) {
                let selected;
                const save=link.querySelector('[data-save-archive]');
                archivePicker(link.querySelector('[data-archive-picker]'),item=>{selected=item;save.disabled=!item;});
                save.onclick=async()=>{
                    if(!selected) return;
                    save.disabled=true;
                    try {
                        await service.linkArchive(id,{recordId:selected.id,version:task.version});
                        onChange();if(current===dialog&&current.open) detail(id,onChange);
                    }catch(error){link.querySelector('[data-archive-error]').textContent=error.message;save.disabled=false;}
                };
            }
            const requestAi=current.querySelector('[data-ai-request]');
            if(requestAi) requestAi.onclick=async()=>{
                requestAi.disabled=true;
                try {
                    await service.generate(id,task.version);
                    onChange();
                    if(current===dialog&&current.open) detail(id,onChange);
                } catch(error) {
                    current.querySelector('[data-ai-error]').textContent=error.message;
                    requestAi.disabled=false;
                }
            };
            const form=current.querySelector('#dw-assignment-action');
            if(aiBusy) {
                const poll=async()=>{
                    if(current!==dialog||!current.open) return;
                    try {
                        const next=await service.assignment(id);
                        if(current!==dialog||!current.open) return;
                        if(next.ai?.state!==task.ai.state||next.status!==task.status) {
                            onChange();
                            if(!form?.querySelector('[name=note]')?.value.trim()) {detail(id,onChange);return;}
                            current.querySelector('[data-ai-status]').textContent=(aiLabels[next.ai?.state]||'状态已更新')+'，点击“刷新 AI 结果”查看。';
                        }
                    }catch(error){current.querySelector('[data-ai-error]').textContent=error.message;}
                    setTimeout(poll,3000);
                };
                setTimeout(poll,3000);
            }

            if(form) form.onsubmit=async event=>{
                event.preventDefault();
                if(!form.reportValidity()) return;
                form.querySelector('[type=submit]').disabled=true;
                try {
                    const body={...Object.fromEntries(new FormData(form)),version:task.version};
                    body.originalReviewed=body.originalReviewed==='on';
                    body.workflowReviewed=body.workflowReviewed==='on';
                    await service.assignmentAction(id,body);
                    onChange();
                    if(current===dialog&&current.open) detail(id,onChange);
                } catch(error) {
                    form.querySelector('[data-error]').textContent=error.message;
                    form.querySelector('[type=submit]').disabled=false;
                }
            };
        } catch(error) {if(current===dialog&&current.open) current.querySelector('.dispatch-content').textContent=error.message;}
    }
    function list(root,{worker='',history=false}={}) {
        const model={worker,status:history?'finished':'',priority:'',page:1,generation:0};
        root.innerHTML='<div class="dw-filters"><label>数字员工<select data-assigned-worker><option value="">全部员工</option>'+
            service.workers.map(item=>'<option value="'+item.id+'"'+(worker===item.id?' selected':'')+'>'+item.name+'</option>').join('')+
            '</select></label><label>任务状态<select data-assigned-status><option value="'+(history?'finished':'')+'">'+(history?'已结束':'全部状态')+'</option>'+
            options(statuses,'')+'</select></label><label>优先级<select data-assigned-priority><option value="">全部优先级</option>'+
            options(priorities,'')+'</select></label><button class="btn" data-assigned-refresh>刷新派活记录</button></div>'+
            '<div data-assigned-results aria-live="polite"></div><div class="dw-filters"><button class="btn" data-assigned-prev>上一页</button>'+
            '<span data-assigned-count></span><button class="btn" data-assigned-next>下一页</button></div>';
        const results=root.querySelector('[data-assigned-results]');
        let busy=false;
        async function load() {
            const sequence=++model.generation;
            busy=true;
            try {
                const data=await service.assignments({worker:model.worker,status:model.status,priority:model.priority,page:model.page,limit:10});
                if(sequence!==model.generation) return;
                results.innerHTML=data.items.length?'<div class="dw-table-wrap"><table><thead><tr><th>任务</th><th>数字员工</th><th>优先级</th><th>状态</th><th>更新时间</th><th>操作</th></tr></thead><tbody>'+
                    data.items.map(task=>'<tr><td>'+escape(task.title)+'</td><td>'+escape(service.workers.find(item=>item.id===task.worker)?.name)+
                        '</td><td>'+priorities[task.priority]+'</td><td>'+statuses[task.status]+'</td><td>'+escape(timestamp(task.updatedAt))+
                        '</td><td><button class="btn" data-assigned-detail="'+escape(task.id)+'">查看 / 处理</button></td></tr>').join('')+
                    '</tbody></table></div>':'<p>暂无符合条件的派活任务。</p>';
                root.querySelector('[data-assigned-count]').textContent='第 '+model.page+' 页 · 共 '+data.total+' 条';
                root.querySelector('[data-assigned-prev]').disabled=model.page<=1;
                root.querySelector('[data-assigned-next]').disabled=model.page*10>=data.total;
            }catch(error){if(sequence===model.generation) results.textContent=error.message;}
            finally{if(sequence===model.generation) busy=false;}
        }
        root.addEventListener('change',event=>{
            const map={'data-assigned-worker':'worker','data-assigned-status':'status','data-assigned-priority':'priority'};
            const key=Object.keys(map).find(attribute=>event.target.hasAttribute(attribute));
            if(key){model[map[key]]=event.target.value;model.page=1;load();}
        });
        root.addEventListener('click',event=>{
            const button=event.target.closest('button');
            if(!button) return;
            if(button.dataset.assignedDetail) detail(button.dataset.assignedDetail,load);
            else if(button.hasAttribute('data-assigned-prev')){model.page--;load();}
            else if(button.hasAttribute('data-assigned-next')){model.page++;load();}
            else if(button.hasAttribute('data-assigned-refresh')) load();
        });
        service.watch(()=>{if(root.isConnected&&!busy) return load();});
        load();
        return load;
    }
    window.DigitalWorkerDispatch={assign,detail,list,statuses,types,priorities};
})();
