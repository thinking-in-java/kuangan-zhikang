(function () {
    'use strict';
    const service=window.DigitalWorkerService, e=service.escape;
    let catalogPromise;
    const catalog=()=>catalogPromise||=service.taskCatalog().then(data=>data.profiles).catch(error=>{catalogPromise=null;throw error;});
    function fields(profile,values={}) {
        return (profile?.fields||[]).map(field=>{
            const value=values[field.key]||'',attrs=' data-task-field="'+e(field.key)+'"';
            const input=field.type==='select'?'<select'+attrs+'><option value="">请选择</option>'+field.options.map(option=>{const [key,label]=option.split('|');return '<option value="'+e(key)+'"'+(key===value?' selected':'')+'>'+e(label)+'</option>';}).join('')+'</select>':field.type==='textarea'?'<textarea'+attrs+' rows="3" maxlength="2000">'+e(value)+'</textarea>':'<input'+attrs+' type="'+e(field.type)+'" maxlength="2000" value="'+e(value)+'">';
            return '<label>'+e(field.label)+input+'</label>';
        }).join('');
    }
    function collect(root){return Object.fromEntries([...root.querySelectorAll('[data-task-field]')].filter(input=>input.value.trim()).map(input=>[input.dataset.taskField,input.value.trim()]));}
    async function assignment(form,worker,settingsPromise) {
        const root=form.querySelector('[data-task-profile]'),select=form.querySelector('[name=type]');
        root.textContent='正在读取任务处理要求…';form.dataset.profileReady='false';
        try {
            const [profiles,config]=await Promise.all([catalog(),settingsPromise]);if(!root.isConnected)return;
            const defaults={archive:'archive_completion',assess:'report_interpretation',follow:'routine_followup',plan:'plan_adjustment',care:'visit_invitation'};
            select.innerHTML=profiles.filter(profile=>config.allowedTypes.includes(profile.type)).map(profile=>'<option value="'+e(profile.type)+'">'+e(profile.name)+'</option>').join('');
            select.value=config.defaultType||defaults[worker]||'archive_completion';
            const saved={};let prior;
            function render(){
                if(prior)saved[prior]=collect(root);prior=select.value;
                const profile=profiles.find(item=>item.type===prior),recommended=service.workers.find(item=>item.id===profile.recommendedWorker);
                root.innerHTML='<p class="workflow-purpose">'+e(profile.purpose)+'</p><p class="workflow-meta">结果包括：'+profile.outputs.map(e).join('、')+'。建议由'+e(recommended?.name||'授权人员')+'处理。</p>'+(profile.fields.length?'<details open><summary>本次任务资料（按实际情况填写）</summary><p class="workflow-meta">这些内容会保存为人工记录。尚未联系时请选择“尚未联系”，不要填写未发生的操作。</p>'+fields(profile,saved[prior])+'</details>':'');
            }
            select.addEventListener('change',render);render();form.dataset.profileReady='true';
        }catch(error){root.textContent=error.message+'，请刷新页面后再派活。';}
    }
    function contextHtml(task,busy) {
        if(!task.profile?.fields?.length)return '';
        const readOnly=!task.canManage||busy||['completed','cancelled'].includes(task.status);
        return '<details class="workflow-context"'+(!task.ai?.taskResult&&!Object.keys(task.taskContext||{}).length?' open':'')+'><summary>本次任务资料与实际处理记录</summary><p class="workflow-meta">'+(task.contextSavedAt?'人工记录保存于 '+e(service.timestamp(task.contextSavedAt))+' · 操作人 '+e(task.contextSavedBy):'尚无人工补充资料。请据实填写联系、反馈、原方案或投诉事实。')+'</p><form data-task-context-form><fieldset'+(readOnly?' disabled':'')+'>'+fields(task.profile,task.taskContext)+'</fieldset>'+(!readOnly?'<button type="submit">保存实际资料</button><p class="workflow-meta">保存后需重新生成结果；原处理记录保留。</p>':'')+'<p data-context-error role="alert"></p></form></details>';
    }
    const execution={not_sent:'未发送',not_contacted:'尚未联系',no_answer:'未接通',declined:'拒绝本次联系',contact_recorded:'联系与反馈已人工记录',not_disposed:'尚无实际处置记录',disposition_recorded:'处置已人工记录',not_applied:'方案未应用',interpretation_prepared:'报告资料已整理',assessment_prepared:'评估依据已整理',not_replied:'尚未答复',reply_recorded:'答复已人工记录'};
    function table(headers,rows){return '<div class="workflow-scroll" tabindex="0" aria-label="结果表格，可横向滚动"><table class="workflow-table"><thead><tr>'+headers.map(header=>'<th scope="col">'+e(header)+'</th>').join('')+'</tr></thead><tbody>'+rows.map(row=>'<tr>'+row.map(cell=>'<td>'+cell+'</td>').join('')+'</tr>').join('')+'</tbody></table></div>';}
    function sourceLink(task,row){return row.sourceId&&row.page?'<a href="'+e(document.body.dataset.base+'ehr/api/digital-worker-tasks/'+encodeURIComponent(task.id)+'/originals/'+encodeURIComponent(row.sourceId)+'#page='+encodeURIComponent(row.page))+'" target="_blank" rel="noopener">原件第 '+e(row.page)+' 页</a>':e(row.page?'第 '+row.page+' 页（未匹配原件链接）':'页码待核实');}
    function section(task,part,index){
        const rows=part.rows||[],empty='<p class="workflow-meta">当前未读取到此类记录，不代表不存在相关问题。</p>';
        let content='';
        switch(part.kind){
            case 'facts':content='<dl class="workflow-facts">'+rows.map(row=>'<div><dt>'+e(row.label)+'</dt><dd>'+e(row.value)+'</dd></div>').join('')+'</dl>';break;
            case 'message':content='<p class="workflow-meta">以下为待审核内容。复制不会发送给会员。</p><div class="workflow-message dispatch-text">'+e(rows)+'</div><button type="button" data-copy-message>复制问询内容</button><p data-copy-status role="status"></p>';break;
            case 'questions':content='<ul class="workflow-questions">'+rows.map(row=>'<li>'+e(row)+'</li>').join('')+'</ul>';break;
            case 'actions':content=table(['下一步事项','负责人','时间 / 触发条件','完成依据'],rows.map(row=>[row.action,row.owner,row.timing,row.completionEvidence].map(e)));break;
            case 'comparison':content=table(['事项','原有 / 上次情况','待讨论选项 / 本次情况','依据与状态'],rows.map(row=>[e(row.topic),e(row.before),e(row.proposal),e(row.basis)+'<p>'+e(row.status)+'</p>']));break;
            case 'evidence':content=rows.length?'<div class="check-filters"><button type="button" data-evidence-filter="all" data-section="'+index+'" aria-pressed="true">全部 '+rows.length+' 项</button><button type="button" data-evidence-filter="flagged" data-section="'+index+'" aria-pressed="false">有原报告标记</button></div>'+table(['项目','报告结果原文','单位 / 本报告参考范围','原报告标记','来源'],rows.map(row=>['<strong>'+e(row.name)+'</strong>',e(row.value||'未录入'),e(row.unit||'单位未录入')+'<br>'+e(row.referenceRange||'范围未录入'),e(row.abnormal||'未标记'),sourceLink(task,row)+'<small>'+(row.reviewed?'报告已审核':'报告未审核')+'</small>']))+'<nav class="workflow-pagination" aria-label="报告项目分页"><button type="button" data-evidence-prev>上一页</button><span data-evidence-count role="status"></span><button type="button" data-evidence-next>下一页</button></nav>':empty;break;
            case 'paragraphs':content=rows.length?rows.map(row=>'<details><summary>第 '+e(row.page||'—')+' 页 · '+e(String(row.text||row.originalText||'报告原文').slice(0,72))+'</summary><p class="dispatch-text">'+e(row.text||row.originalText)+'</p></details>').join(''):empty;break;
            case 'followups':content=rows.length?table(['随访任务','实际状态','原系统约定日期'],rows.map(row=>[e(row.id),e(({pending:'待跟进',sent:'已发送',completed:'已完成',cancelled:'已取消',overdue:'已逾期'})[row.status]||row.status),e(service.timestamp(row.dueAt))])):empty;break;
            case 'risks':content=rows.length?table(['已确认风险等级','记录分数','规则版本','评估时间'],rows.map(row=>[e(({low:'低',medium:'中',high:'高',critical:'极高',unknown:'未知'})[row.level]||row.level),e(row.score??'未记录'),e(row.ruleVersion),e(service.timestamp(row.assessedAt))])):empty;break;
            case 'explanations':content=rows.map(row=>{const item=task.ai.taskResult.sections.flatMap(s=>s.kind==='evidence'?s.rows:[]).find(item=>item.id===row.evidenceId);return '<div class="workflow-explanation"><strong>'+e(item?.name||'报告项目')+'</strong><p>'+e(row.text)+'</p>'+(item?sourceLink(task,item):'')+'</div>';}).join('');break;
            default:content=empty;
        }
        return '<section class="workflow-section" data-result-section="'+index+'"><h4>'+e(part.title)+'</h4>'+content+'</section>';
    }
    function html(task){
        const result=task.ai?.taskResult;if(!result)return '';
        const source=result.sourceContext||{},ready=result.state==='ready_for_review';
        return '<section class="workflow-result" aria-label="分类任务处理结果"><div class="workflow-outcome"><div><h4>'+e(result.title)+'</h4><p>'+e(result.summary)+'</p></div><span class="workflow-state">'+(task.status==='completed'?'已审核记录':task.status==='cancelled'?'已取消 · 历史结果':ready?'待人工核验':'资料不足 · 待补充')+'</span></div><p class="workflow-execution"><strong>实际执行状态：'+e(execution[result.executionState]||'未执行')+'</strong></p><details class="workflow-source"><summary>本次读取的数据与来源</summary><dl class="workflow-facts"><div><dt>关联职工</dt><dd>'+e(source.name||'未关联')+' · '+e(source.code||'—')+'</dd></div><div><dt>报告</dt><dd>'+e(source.examDate||'未读取')+' · '+e(source.institution||'机构待核实')+'</dd></div><div><dt>报告审核</dt><dd>'+(result.reportReviewed?'已审核':'未读取已审核报告')+'</dd></div><div><dt>原版报告</dt><dd>'+(result.originalRead?'已读取原件并校验文件指纹':e(result.originalNote||'本任务未读取原件'))+'</dd></div><div><dt>生成时间</dt><dd>'+e(service.timestamp(result.generatedAt))+'</dd></div></dl><p class="workflow-meta">'+(result.modelUsed?(['visit_invitation','routine_followup','complaint_followup'].includes(task.type)?'辅助内容根据本次人工资料整理；联系、答复和发送状态以实际记录为准。':'模型辅助说明优先覆盖最多30个项目（先看有原报告标记的项目）；报告解读同时展示完整录入结果，辅助说明仍需专业审核。'):'本次展示后台事实与任务工作清单，没有可用的模型辅助解读。')+'</p></details>'+result.sections.map((part,index)=>({part,index})).sort((a,b)=>(b.part.kind==='explanations')-(a.part.kind==='explanations')).map(({part,index})=>section(task,part,index)).join('')+'<details class="workflow-references"><summary>流程参考资料</summary><ul>'+(result.references||[]).map(ref=>'<li><a href="'+e(ref.url)+'" target="_blank" rel="noopener noreferrer">'+e(ref.title)+'</a></li>').join('')+'</ul><p class="workflow-meta">参考资料用于组织流程，不替代本机构业务规则或医生判断。</p></details><p class="check-footnote">结果生成和审核不会自动发送通知、修改健康档案或应用干预方案。人工填写的实际操作与系统回执分别保留。</p></section>';
    }
    function mount(root,task,onSaved){
        const form=root.querySelector('[data-task-context-form]');
        if(form&&form.querySelector('[type=submit]'))form.onsubmit=async event=>{event.preventDefault();if(!form.reportValidity())return;const button=form.querySelector('[type=submit]');button.disabled=true;try{await service.saveTaskContext(task.id,{taskContext:collect(form),version:task.version});onSaved();}catch(error){form.querySelector('[data-context-error]').textContent=error.message;button.disabled=false;}};
        const copy=root.querySelector('[data-copy-message]');if(copy)copy.onclick=async()=>{const status=root.querySelector('[data-copy-status]');try{if(navigator.clipboard&&window.isSecureContext)await navigator.clipboard.writeText(task.ai.taskResult.memberMessage);else{const range=document.createRange();range.selectNodeContents(root.querySelector('.workflow-message'));getSelection().removeAllRanges();getSelection().addRange(range);if(!document.execCommand('copy'))throw Error();getSelection().removeAllRanges();}status.textContent='问询内容已复制，尚未发送。';}catch(error){status.textContent='自动复制不可用，请选中上方内容后复制。';}};
        root.querySelectorAll('[data-result-section]').forEach(element=>{
            const part=task.ai?.taskResult?.sections[Number(element.dataset.resultSection)];if(part?.kind!=='evidence'||!part.rows.length)return;
            let filter=part.rows.some(row=>row.hasFlag)?'flagged':'all',page=1;
            function render(){
                const matched=part.rows.map((row,index)=>({row,index})).filter(({row})=>filter==='all'||row.hasFlag===true),visible=new Set(matched.slice((page-1)*12,page*12).map(({index})=>index));
                element.querySelectorAll('tbody tr').forEach((tr,index)=>tr.hidden=!visible.has(index));
                element.querySelectorAll('[data-evidence-filter]').forEach(button=>button.setAttribute('aria-pressed',String(button.dataset.evidenceFilter===filter)));
                const count=element.querySelector('[data-evidence-count]');if(count){count.textContent='第 '+page+' / '+Math.max(1,Math.ceil(matched.length/12))+' 页 · 共 '+matched.length+' 项';element.querySelector('[data-evidence-prev]').disabled=page===1;element.querySelector('[data-evidence-next]').disabled=page*12>=matched.length;}
            }
            element.querySelectorAll('[data-evidence-filter]').forEach(button=>button.onclick=()=>{filter=button.dataset.evidenceFilter;page=1;render();});
            const previous=element.querySelector('[data-evidence-prev]'),next=element.querySelector('[data-evidence-next]');if(previous)previous.onclick=()=>{page--;render();};if(next)next.onclick=()=>{page++;render();};render();
        });
    }
    window.DigitalWorkerTaskResults={assignment,collect,contextHtml,html,mount};
})();
