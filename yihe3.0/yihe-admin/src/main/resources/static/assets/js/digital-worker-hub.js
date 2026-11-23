(function(){
    'use strict';
    const api=window.DigitalWorkerService,e=api.escape;
    const statuses={all:'全部任务',active:'活跃任务',queued:'待处理',in_progress:'处理中',pending_review:'待审核',completed:'已完成',failed:'失败',cancelled:'已取消',unknown:'待核实'};
    const sources={assignment:'派活任务',ocr:'OCR 任务',alert:'指标预警'};
    const types={...window.DigitalWorkerDispatch.types,report_ocr:'报告 OCR 识别',indicator_alert:'指标预警'};
    const workers=Object.fromEntries(api.workers.map(w=>[w.id,w.name]));
    function count(value){return Number.isFinite(value)?value.toLocaleString('zh-CN'):'—';}
    function state(row){return row.source==='ocr'?api.states[row.nativeStatus]||'状态待核实':statuses[row.status]||'状态待核实';}
    function taskLink(row,label){if(row.source==='ocr'&&row.canOpen===0)return '<span class="hub-unavailable" title="关联档案已删除或不可访问；仅保留本单位任务记录">'+e(label?'关联档案不可访问':row.title)+'</span>';return '<button type="button" class="hub-task-link" data-hub-task="'+e(row.id)+'" data-hub-source="'+e(row.source)+'">'+e(label||row.title)+'</button>';}
    function openTask(row,changed){if(row.source==='ocr'&&row.canOpen===0)return;if(row.source==='assignment')window.DigitalWorkerDispatch.detail(row.id,changed);else if(row.source==='ocr')api.open('records',{worker:'archive',task:row.id});else if(row.source==='alert')api.open('dispatch',{worker:'assess',source:'real',alert:row.id});}
    function create(root,changed){
        const query=new URLSearchParams(location.search);
        const model={worker:'',status:'all',source:'',priority:'',type:'',search:'',sort:'updated',page:1};
        for(const key of Object.keys(model))if(query.has(key))model[key]=query.get(key);
        model.worker=Object.hasOwn(workers,model.worker)?model.worker:'';model.source=Object.hasOwn(sources,model.source)?model.source:'';
        model.status=Object.hasOwn(statuses,model.status)||['active','finished'].includes(model.status)?model.status:'all';
        model.type=Object.hasOwn(types,model.type)?model.type:'';model.priority=['normal','high','urgent'].includes(model.priority)?model.priority:'';
        model.sort=['updated','created','priority'].includes(model.sort)?model.sort:'updated';model.page=/^[1-9]\d{0,4}$/.test(model.page)?Number(model.page):1;model.search=String(model.search).slice(0,100);
        let generation=0,current=[],loading=false;
        const options=(values,selected,placeholder)=>'<option value="">'+placeholder+'</option>'+Object.entries(values).map(([key,label])=>'<option value="'+e(key)+'"'+(key===selected?' selected':'')+'>'+e(label)+'</option>').join('');
        root.innerHTML='<header class="hub-heading"><div><h2 id="taskHubTitle" tabindex="-1">五位数字员工 · 全部任务</h2><p>汇总派活、OCR 与指标预警，按来源保留原有任务和处理记录。</p></div><button type="button" class="hub-button" data-hub-close>收起总览</button></header><div class="hub-statuses" role="group" aria-label="任务状态筛选">'+Object.entries(statuses).filter(([key])=>key!=='unknown').map(([key,label])=>'<button type="button" data-hub-status="'+key+'" aria-pressed="false">'+label+' <b data-hub-count="'+key+'">—</b></button>').join('')+'</div><form class="hub-filters" data-hub-filters><label>数字员工<select name="worker">'+options(workers,model.worker,'全部员工')+'</select></label><label>任务来源<select name="source">'+options(sources,model.source,'全部来源')+'</select></label><label>任务类型<select name="type">'+options(types,model.type,'全部类型')+'</select></label><label>优先级<select name="priority">'+options(window.DigitalWorkerDispatch.priorities,model.priority,'全部优先级')+'</select></label><label>排序<select name="sort">'+Object.entries({updated:'最近更新',created:'最近创建',priority:'优先级优先'}).map(([key,label])=>'<option value="'+key+'"'+(model.sort===key?' selected':'')+'>'+label+'</option>').join('')+'</select></label><label class="hub-search">搜索任务或职工<input name="search" type="search" maxlength="100" placeholder="任务标题、姓名、工号或编号" value="'+e(model.search)+'"></label><button class="hub-button hub-primary" type="submit">查询</button><button class="hub-button" type="button" data-hub-reset>重置</button></form><p class="hub-scope" data-hub-scope></p><div data-hub-results aria-live="polite"></div><nav class="hub-pagination" aria-label="全部任务分页"><button class="hub-button" type="button" data-hub-prev>上一页</button><span data-hub-page></span><button class="hub-button" type="button" data-hub-next>下一页</button></nav><p class="hub-footnote">已完成表示各来源任务的实际状态：OCR 识别完成不等于档案审核通过。随访、通知与干预仍按原业务权限和审核流程处理。</p>';
        const form=root.querySelector('form'),results=root.querySelector('[data-hub-results]'),prev=root.querySelector('[data-hub-prev]'),next=root.querySelector('[data-hub-next]');
        function save(){
            const url=new URL(location.href);for(const key of ['hub',...Object.keys(model)])url.searchParams.delete(key);
            if(!root.hidden){url.searchParams.set('hub','1');for(const [key,value]of Object.entries(model))if(value!==''&&!(key==='status'&&value==='all')&&!(key==='sort'&&value==='updated')&&!(key==='page'&&value===1))url.searchParams.set(key,value);}
            history.replaceState(null,'',url.pathname+url.search+url.hash);
            try{window.parent!==window&&window.parent.saveDigitalWorkerHubQuery?.(url.searchParams);}catch(error){/* Standalone route remains usable. */}
        }
        function sync(){for(const key of ['worker','source','type','priority','sort','search'])form.elements[key].value=model[key];root.querySelectorAll('[data-hub-status]').forEach(button=>button.setAttribute('aria-pressed',String(button.dataset.hubStatus===model.status)));}
        async function reload(){
            if(root.hidden)return;const sequence=++generation;loading=true;root.setAttribute('aria-busy','true');prev.disabled=next.disabled=true;results.classList.add('hub-loading');
            if(!current.length)results.innerHTML='<p class="hub-empty">正在读取五位员工的授权任务…</p>';
            try{
                const data=await api.hubTasks({...model,limit:12});if(sequence!==generation)return;
                if(model.page>1&&!data.items.length){model.page=Math.max(1,Math.ceil(data.total/12));save();return reload();}
                current=data.items;
                for(const key of Object.keys(statuses)){const countElement=root.querySelector('[data-hub-count="'+key+'"]');if(countElement)countElement.textContent=count(key==='all'?data.summary.total:data.summary[key]);}
                root.querySelector('[data-hub-scope]').textContent='当前筛选：'+(workers[model.worker]||'全部员工')+' · '+(statuses[model.status]||({active:'活跃任务',finished:'已结束任务'})[model.status])+' · 派活 '+count(data.bySource.assignment)+' / OCR '+count(data.bySource.ocr)+' / 指标预警 '+count(data.bySource.alert)+' · 最近同步 '+api.timestamp(data.capturedAt);
                results.innerHTML=current.length?'<div class="hub-table-scroll" tabindex="0" aria-label="汇总任务表格，可横向滚动"><table class="hub-table"><thead><tr><th scope="col">任务与类型</th><th scope="col">数字员工</th><th scope="col">关联职工</th><th scope="col">来源</th><th scope="col">状态</th><th scope="col">更新时间</th><th scope="col">操作</th></tr></thead><tbody>'+current.map(row=>'<tr><td>'+taskLink(row)+'<small>'+e(types[row.type]||row.type)+(row.priority!=='normal'?' · '+e(window.DigitalWorkerDispatch.priorities[row.priority]):'')+'</small></td><td>'+e(workers[row.worker])+'</td><td>'+e(row.memberName||(row.source==='ocr'&&row.canOpen===0?'资料不可访问':'未关联职工'))+'<small>'+e(row.memberCode||'无职工编码')+'</small></td><td>'+e(sources[row.source])+'</td><td><span class="hub-state hub-state-'+e(row.status)+'">'+e(state(row))+'</span>'+(row.source==='ocr'&&row.percent!=null?'<small>进度 '+e(row.percent)+'%</small>':'')+'</td><td>'+e(api.timestamp(row.updatedAt))+'</td><td>'+taskLink(row,row.source==='assignment'?'查看 / 处理':row.source==='ocr'?'查看 OCR':'查看预警')+'</td></tr>').join('')+'</tbody></table></div>':'<div class="hub-empty"><strong>暂无符合条件的任务</strong><p>可重置筛选查看全部；新派发任务及已接入业务队列会汇总到这里。</p><button type="button" class="hub-button" data-hub-reset>重置筛选</button></div>';
                root.querySelector('[data-hub-page]').textContent='第 '+model.page+' / '+Math.max(1,Math.ceil(data.total/12))+' 页 · 共 '+count(data.total)+' 条';prev.disabled=model.page<=1;next.disabled=model.page*12>=data.total;
            }catch(error){if(sequence!==generation)return;current=[];results.innerHTML='<div class="hub-empty" role="alert"><strong>任务总览暂不可用</strong><p>'+e(error.message)+'</p><button type="button" class="hub-button" data-hub-retry>重试</button></div>';root.querySelector('[data-hub-page]').textContent='';root.querySelector('[data-hub-scope]').textContent='本次读取失败，筛选条件已保留。';root.querySelectorAll('[data-hub-count]').forEach(el=>el.textContent='—');}
            finally{if(sequence===generation){loading=false;root.removeAttribute('aria-busy');results.classList.remove('hub-loading');}}
        }
        function focusPanel(){root.querySelector('h2').focus({preventScroll:true});window.scrollTo({top:root.getBoundingClientRect().top+window.scrollY-16,behavior:'instant'});}
        function show(values={},focus=true){Object.assign(model,values);root.hidden=false;document.querySelector('#engineButton').setAttribute('aria-expanded','true');sync();save();reload();if(focus)focusPanel();}
        form.onsubmit=event=>{event.preventDefault();if(!form.reportValidity())return;Object.assign(model,Object.fromEntries(new FormData(form)),{page:1});sync();save();reload();};
        form.addEventListener('change',event=>{if(event.target.tagName==='SELECT')form.requestSubmit();});
        root.addEventListener('click',event=>{
            const button=event.target.closest('button');if(!button)return;
            if(button.dataset.hubTask){const row=current.find(item=>item.id===button.dataset.hubTask&&item.source===button.dataset.hubSource);if(row)openTask(row,changed);}
            else if(button.hasAttribute('data-hub-close')){root.hidden=true;generation++;document.querySelector('#engineButton').setAttribute('aria-expanded','false');save();const engine=document.querySelector('#engineButton');engine.focus({preventScroll:true});window.scrollTo({top:engine.getBoundingClientRect().top+window.scrollY-20,behavior:'instant'});}
            else if(button.hasAttribute('data-hub-status'))show({status:button.dataset.hubStatus,page:1},false);
            else if(button.hasAttribute('data-hub-reset'))show({worker:'',source:'',priority:'',type:'',search:'',status:'all',sort:'updated',page:1},false);
            else if(button.hasAttribute('data-hub-retry'))reload();
            else if(button.hasAttribute('data-hub-prev')&&!loading&&model.page>1)show({page:model.page-1},false);
            else if(button.hasAttribute('data-hub-next')&&!loading)show({page:model.page+1},false);
        });
        const initiallyOpen=query.get('hub')==='1';sync();if(initiallyOpen)show({},false);
        return {show,reload,taskLink,openTask,model,focus:focusPanel};
    }
    window.DigitalWorkerHub={create,taskLink,openTask,state,count};
})();
