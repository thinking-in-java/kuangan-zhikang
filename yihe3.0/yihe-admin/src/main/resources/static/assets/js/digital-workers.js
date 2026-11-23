(function () {
    'use strict';
    const service = window.DigitalWorkerService;
    const { escape, states, terminal, archiveLink } = service;
    const page = document.body.dataset.page;
    const titles = { dispatch: '智能任务调度', records: '任务执行记录', settings: '数字员工配置' };
    const formatCount = count => Number.isFinite(count) ? count.toLocaleString('zh-CN') : '—';

    function rowLink(id) {
        return '<a href="' + escape(archiveLink(id)) + '">打开健康档案</a>';
    }

    function renderQueue(root, { history = false, worker = '', alertId = '', initialTask = '', onWorker } = {}) {
        const filters = history ? terminal : ['active', 'queued', 'running', 'retry_wait', ...terminal, 'all'];
        const model = { filter: filters[0], worker: worker || 'archive', page: 1, sequence: 0 };
        const labels = { ...states, active: '全部活跃任务', all: '全部 OCR 任务', finished: '全部已结束任务' };
        if (history) filters.unshift('finished');
        model.filter = filters[0];
        root.innerHTML = '<div class="dw-filters"><label>数字员工<select data-worker>' +
            service.workers.map(item => '<option value="' + item.id + '"' + (item.id === model.worker ? ' selected' : '') +
                '>' + item.name + '</option>').join('') + '</select></label>' +
            '<label data-ocr-filter>OCR 状态<select data-state>' + filters.map(state =>
                '<option value="' + state + '">' + labels[state] + '</option>').join('') + '</select></label>' +
            '<button type="button" class="btn" data-refresh>刷新</button></div><div data-results aria-live="polite"></div>' +
            '<div class="dw-filters" data-pager hidden><button class="btn" data-prev>上一页</button><span data-count></span>' +
            '<button class="btn" data-next>下一页</button></div><div data-detail aria-live="polite"></div>';
        const result = root.querySelector('[data-results]');
        const pager = root.querySelector('[data-pager]');
        const detail = root.querySelector('[data-detail]');
        const limit = 10;

        async function load() {
            const sequence = ++model.sequence;
            root.querySelector('[data-ocr-filter]').hidden = model.worker !== 'archive';
            pager.hidden = true;
            detail.innerHTML = '';
            result.textContent = '正在读取数据…';
            try {
                if (model.worker === 'archive') {
                    const data = await service.tasks(model.filter, model.page, limit);
                    if (sequence !== model.sequence) return;
                    result.innerHTML = data.items.length ? '<div class="dw-table-wrap"><table><thead><tr><th>工号</th><th>状态</th><th>进度</th>' +
                        '<th>最后更新</th><th>操作</th></tr></thead><tbody>' + data.items.map(row =>
                            '<tr><td>' + escape(row.employeeNo || '—') + '</td><td>' + escape(states[row.state] || '未知') + '</td><td>' +
                            escape(row.percent ?? '—') + (row.percent == null ? '' : '%') + '</td><td>' + escape(service.timestamp(row.updatedAt)) +
                            '</td><td><button type="button" class="btn" data-task="' + escape(row.jobId) + '">查看执行详情</button> ' +
                            rowLink(row.recordId) + '</td></tr>').join('') + '</tbody></table></div>' : '<p>暂无符合条件的 OCR 任务。</p>';
                    pagination(data.total);
                } else if (model.worker === 'assess' && !history) {
                    const data = await service.alerts(alertId ? { alertId, page: 1, limit: 1 } : { page: model.page, limit });
                    if (sequence !== model.sequence) return;
                    result.innerHTML = '<p>以下为当前账号可查看的指标预警。预警复核和处置通过原业务页面办理。</p>' +
                        (data.items.length ? '<div class="dw-table-wrap"><table><thead><tr><th>编号</th><th>预警</th><th>来源状态</th><th>记录时间</th></tr></thead><tbody>' +
                            data.items.map(row => '<tr><td>' + escape(row.id) + '</td><td>' + escape(row.title) + '</td><td>' +
                                escape(row.status || '—') + '</td><td>' + escape(row.time || '—') + '</td></tr>').join('') +
                            '</tbody></table></div>' : '<p>当前账号暂无匹配的指标预警。</p>');
                    if (!alertId) pagination(data.total);
                } else {
                    result.textContent = '该岗位暂无接入的' + (history ? '真实执行记录。' : '正式任务队列。');
                }
            } catch (error) {
                if (sequence === model.sequence) result.textContent = error.message;
            }
        }

        function pagination(total) {
            pager.hidden = false;
            root.querySelector('[data-count]').textContent = '第 ' + model.page + ' 页 · 共 ' + formatCount(total) + ' 条';
            root.querySelector('[data-prev]').disabled = model.page <= 1;
            root.querySelector('[data-next]').disabled = model.page * limit >= total;
        }

        async function showTask(id) {
            const sequence = ++model.sequence;
            detail.textContent = '正在读取执行记录…';
            try {
                const data = await service.task(id);
                if (sequence !== model.sequence) return;
                const attempts = data.attempts || [];
                detail.innerHTML = '<h3>OCR 执行详情</h3><p>任务编号 ' + escape(id) + ' · ' + escape(states[data.state] || '未知') +
                    ' · 报告版本 ' + escape(data.revision) + '</p>' +
                    (attempts.length ? '<ol>' + attempts.map(attempt => '<li>第 ' + escape(attempt.attemptNumber) + ' 次 · ' +
                        escape(({ ...states, expired: '租约到期' })[attempt.outcome] || attempt.outcome || '处理中') + ' · ' + escape(service.timestamp(attempt.startedAt)) +
                        (attempt.errorCode ? ' · ' + escape(attempt.errorCode) : '') + '</li>').join('') + '</ol>' : '<p>暂无执行尝试。</p>');
            } catch (error) {
                if (sequence === model.sequence) detail.textContent = error.message;
            }
        }

        root.addEventListener('change', event => {
            if (event.target.matches('[data-worker]')) {
                if (onWorker) { onWorker(event.target.value); return; }
                model.worker = event.target.value;
            }
            else if (event.target.matches('[data-state]')) model.filter = event.target.value;
            else return;
            model.page = 1;
            load();
        });
        root.addEventListener('click', event => {
            const button = event.target.closest('button');
            if (!button) return;
            if (button.hasAttribute('data-task')) { showTask(button.dataset.task); return; }
            if (button.hasAttribute('data-prev')) model.page--;
            else if (button.hasAttribute('data-next')) model.page++;
            else if (!button.hasAttribute('data-refresh')) return;
            load();
        });
        load().then(()=>{if(initialTask&&model.worker==='archive'){showTask(initialTask).then(()=>detail.scrollIntoView({block:'start'}));}});
    }

    window.DigitalWorkerReadViews = { renderQueue };
    if (!Object.hasOwn(titles, page)) return;
    const root = document.getElementById('dw-app');
    const params = new URLSearchParams(location.search);
    const validWorker = service.workers.some(worker => worker.id === params.get('worker'));
    const alertId = params.get('source') === 'real' && /^\d{1,10}$/.test(params.get('alert') || '') ? params.get('alert') : '';
    const description = {
        dispatch: '派发和处理任务，查询 OCR 队列及指标预警。',
        records: '查询派活处理记录、审核结果和 OCR 执行尝试。',
        settings: '配置五位员工的接收规则、默认派活与AI处理要求。'
    };
    root.innerHTML = '<header class="topbar dw-header"><div><h1>' + titles[page] + '</h1><p>' + description[page] +
        '</p></div><span class="live-indicator">正式数据</span></header><section class="dw-panel"><p>' +
        (page === 'dispatch' ? '<a href="' + escape(service.uploadLink()) + '" target="_blank" rel="noopener">布置报告识别任务</a> · ' : '') +
        (page==='settings'?'岗位规则按当前单位保存；新派活与新的AI请求使用保存后的配置。':'识别重试、取消和报告审核请在原业务页面办理。 '+rowLink())+'</p><div id="dw-content"></div></section>';
    const content = document.getElementById('dw-content');
    if (page !== 'settings') {
        const workerId=validWorker ? params.get('worker') : 'archive';
        const assigned=document.createElement('section');
        assigned.className='dw-panel';
        assigned.innerHTML='<header><h2>派活任务'+(page==='records'?'处理记录':'队列')+'</h2><button class="btn" data-give-task>给 TA 派活</button></header><div data-assigned-list></div>';
        content.parentElement.before(assigned);
        const listRoot=assigned.querySelector('[data-assigned-list]');
        const refreshAssigned=window.DigitalWorkerDispatch.list(listRoot,{worker:workerId,history:page==='records'});
        assigned.querySelector('[data-give-task]').onclick=()=>window.DigitalWorkerDispatch.assign(
            listRoot.querySelector('[data-assigned-worker]').value || workerId,refreshAssigned);
    }

    if (page !== 'settings') {
        renderQueue(content, { history: page === 'records', worker: alertId ? 'assess' : validWorker ? params.get('worker') : 'archive', alertId, initialTask: /^[0-9a-f-]{36}$/.test(params.get('task')||'')?params.get('task'):'' });
    } else {
        window.DigitalWorkerSettingsPage.mount(content);
    }
})();
