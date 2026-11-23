(function () {
    'use strict';
    const base = document.body.dataset.base;
    const workers = [
        { id: 'archive', name: '档案小谷', source: '健康档案与 OCR 队列' },
        { id: 'assess', name: '评估小谷', source: '当前医生的指标预警' },
        { id: 'follow', name: '随访小谷', source: '派活已接入 · 随访自动执行待接入' },
        { id: 'plan', name: '方案小谷', source: '派活已接入 · 方案自动生成待接入' },
        { id: 'care', name: '心理小谷', source: '派活已接入 · 心理服务自动执行待接入' }
    ];
    const states = {
        queued: '待领取', running: '识别中', retry_wait: '等待重试',
        succeeded: '识别成功', failed: '识别失败', cancelled: '已取消'
    };
    const terminal = ['succeeded', 'failed', 'cancelled'];
    const archive = 'assets/health-archive/review.html?v=digital-workers-live';
    const escape = value => String(value ?? '').replace(/[&<>"']/g, ch => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[ch]));
    function timestamp(value) {
        if (!value) return '—';
        const date = new Date(typeof value === 'string' ? value.replace(' ', 'T').replace(/(\.\d{3})\d+/, '$1') +
            (/(Z|[+-]\d\d:\d\d)$/.test(value) ? '' : 'Z') : value);
        return Number.isNaN(date.valueOf()) ? '—' : date.toLocaleString('zh-CN', { hour12: false });
    }
    async function request(path, code = 0) {
        const response = await fetch(base + path, {
            credentials: 'same-origin', cache: 'no-store', headers: { 'X-Requested-With': 'XMLHttpRequest' },
            signal: AbortSignal.timeout(12000)
        });
        const result = await response.json().catch(() => ({}));
        if (!response.ok || result.code !== code || !result.data) {
            throw new Error(response.status === 401 || response.status === 403 ? '登录已失效或当前账号没有访问权限。' : '数据暂不可用，请重试。');
        }
        return result.data;
    }
    let context;
    async function ehr(path) {
        context ||= request('ehr/api/context').catch(error => { context = null; throw error; });
        const account = await context;
        if (account.dataMode !== 'real') throw new Error('当前实例未开启真实档案数据模式。');
        return request('ehr/api/' + path);
    }
    async function alerts(params = {}) {
        const result = await request('YhHeThresholdAlarm/findThresholdList?' + new URLSearchParams({
            page: 1, limit: 5, ...params
        }), 20000);
        return { total: result.count, items: (result.data || []).map(item => ({
            id: String(item.id), title: item.typename || '指标预警',
            time: item.time, status: item.status
        })) };
    }
    function tasks(state = 'all', page = 1, limit = 10) {
        return ehr('ocr/tasks?' + new URLSearchParams({state, page, limit}));
    }
    function open(page, values = {}) {
        if (window.parent !== window && window.parent.openDigitalWorkerPage) {
            window.parent.openDigitalWorkerPage(page, new URLSearchParams(values));
        } else {
            location.href = base + 'manage/digital-workers/' + page + '?' + new URLSearchParams(values);
        }
    }
    function archiveLink(recordId) {
        return base + (recordId ? 'assets/health-archive/detail.html?' + new URLSearchParams({id: recordId}) : archive);
    }
    function uploadLink() {
        return base + 'assets/health-archive/management.html?action=upload&v=worker-ocr';
    }
    async function writeTask(path,body) {
        const account = await (context ||= request('ehr/api/context').catch(error => {context=null;throw error;}));
        if (account.dataMode !== 'real') throw new Error('当前未启用正式数据模式');
        const response = await fetch(base + 'ehr/api/digital-worker-tasks' + path, {
            method:'POST',credentials:'same-origin',cache:'no-store',
            headers:{'Content-Type':'application/json','X-CSRF-Token':account.csrfToken},
            body:JSON.stringify(body),signal:AbortSignal.timeout(15000)
        });
        const result = await response.json().catch(() => ({}));
        if (!response.ok || result.code !== 0) throw new Error(result.msg || '操作失败，请刷新后重试');
        return result.data;
    }
    function watch(refresh) {
        let busy = false;
        function visible() {
            if (document.hidden) return false;
            try { return !window.frameElement || window.frameElement.getClientRects().length > 0; }
            catch (error) { return true; }
        }
        async function update() {
            if (busy || !visible()) return;
            busy = true;
            try { await refresh(); }
            finally { busy = false; }
        }
        const timer = setInterval(update, 15000);
        const resumed = () => {
            if (visible()) update();
        };
        document.addEventListener('visibilitychange', resumed);
        window.addEventListener('focus', resumed);
        window.addEventListener('pageshow', resumed);
        let wasVisible = visible();
        let observer;
        try {
            const surface = window.frameElement?.closest('.layui-body');
            if (surface) {
                observer = new MutationObserver(() => {
                    const nowVisible = visible();
                    if (nowVisible && !wasVisible) update();
                    wasVisible = nowVisible;
                });
                observer.observe(surface, {subtree: true, attributes: true, attributeFilter: ['class', 'style']});
            }
        } catch (error) { /* Standalone pages use visibility and focus events. */ }
        window.addEventListener('pagehide', event => {
            if (event.persisted) return;
            clearInterval(timer);
            observer?.disconnect();
            document.removeEventListener('visibilitychange', resumed);
            window.removeEventListener('focus', resumed);
            window.removeEventListener('pageshow', resumed);
        }, {once:true});
    }
    window.DigitalWorkerService = {
        workers, states, terminal, escape, timestamp, open, archiveLink, uploadLink, alerts, tasks, watch,
        hubSummary: () => ehr('digital-worker-hub/summary'),
        hubTasks: filters => ehr('digital-worker-hub?' + new URLSearchParams(filters)),
        workerSettings: () => ehr('digital-worker-settings'),
        saveWorkerSettings: async (worker,body) => {
            const account=await (context ||= request('ehr/api/context').catch(error=>{context=null;throw error;}));
            const response=await fetch(base+'ehr/api/digital-worker-settings/'+encodeURIComponent(worker),{method:'POST',credentials:'same-origin',cache:'no-store',headers:{'Content-Type':'application/json','X-CSRF-Token':account.csrfToken},body:JSON.stringify(body),signal:AbortSignal.timeout(15000)});
            const result=await response.json().catch(()=>({}));if(!response.ok||result.code!==0)throw new Error(result.msg||'配置保存失败，请刷新后重试');return result.data;
        },
        assignments: filters => ehr('digital-worker-tasks?' + new URLSearchParams(filters)),
        assignment: id => ehr('digital-worker-tasks/' + encodeURIComponent(id)),
        assign: body => writeTask('',body),
        taskCatalog: () => ehr('digital-worker-tasks/catalog'),
        saveTaskContext: (id,body) => writeTask('/' + encodeURIComponent(id) + '/context',body),
        searchArchives: search => ehr('records?' + new URLSearchParams({search,page:1,limit:20})),
        linkArchive: (id,body) => writeTask('/' + encodeURIComponent(id) + '/archive',body),
        modelStatus: () => ehr('digital-worker-tasks/model-status'),
        generate: (id,version) => writeTask('/' + encodeURIComponent(id) + '/generate',{version}),
        assignmentAction: (id,body) => writeTask('/' + encodeURIComponent(id) + '/actions',body),
        summary: () => ehr('ocr/summary'),
        statistics: () => ehr('statistics'),
        recognition: () => ehr('recognition'),
        task: id => ehr('ocr/tasks/' + encodeURIComponent(id))
    };
})();
