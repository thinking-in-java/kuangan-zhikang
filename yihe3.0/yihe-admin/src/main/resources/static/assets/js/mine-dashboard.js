/* Occupational health dashboard. Demo data is explicit and never written to the database. */
(function () {
    'use strict';
    var $ = function (id) { return document.getElementById(id); };
    var palette = ['#31eeb8', '#26c8f2', '#ffbd55', '#ff696d', '#a399f5', '#6484a0'];
    var icons = {
        people: '<circle cx="9" cy="8" r="3"/><path d="M3 21v-2a6 6 0 0 1 12 0v2M16 5a3 3 0 0 1 0 6M17 15a5 5 0 0 1 4 5"/>',
        helmet: '<path d="M4 16v-3a8 8 0 0 1 5-8M15 5a8 8 0 0 1 5 8v3M9 10V4h6v6M3 16h18v4H3Z"/><path d="M11 13h2"/>',
        pulse: '<path d="M2 12h5l3-8 4 16 3-8h5"/>',
        trend: '<path d="M3 3v18h18M6 15l5-6 4 4 6-8"/>',
        alarm: '<path d="M5 18h14l-2-3V9A5 5 0 0 0 7 9v6ZM10 22h4M12 3V1M3 5l2 2M21 5l-2 2"/>',
        shield: '<path d="m12 3 8 3v6c0 5-8 9-8 9s-8-4-8-9V6Z"/><path d="m8 12 3 3 5-6"/>',
        risk: '<path d="m12 3 10 18H2ZM12 9v5M12 17v.1"/>',
        heart: '<path d="M20 5c-4-4-8 1-8 1S8 1 4 5c-5 6 8 16 8 16S25 11 20 5Z"/><path d="M5 12h4l2-4 3 8 2-4h3"/>',
        drop: '<path d="M12 2S5 11 5 15a7 7 0 0 0 14 0c0-4-7-13-7-13Z"/><path d="M8 14c-1 3 1 5 4 5"/>',
        ear: '<path d="M6 9a6 6 0 0 1 12 0c0 5-5 5-5 9a3 3 0 0 1-6 0M9 9a3 3 0 0 1 6 0c0 3-3 3-3 5"/>',
        lungs: '<path d="M10 8C8 4 6 4 5 7L2 16c-1 5 7 5 8 1V8ZM14 8c2-4 4-4 5-1l3 9c1 5-7 5-8 1V8ZM12 2v9M9 13l3-2 3 2"/>',
        bone: '<path d="M5 8a3 3 0 1 1 3-3l11 11a3 3 0 1 1-3 3ZM4 4l2 2M18 18l2 2"/>',
        dust: '<circle cx="6" cy="6" r="1"/><circle cx="15" cy="4" r="1"/><circle cx="11" cy="12" r="1"/><circle cx="20" cy="11" r="1"/><circle cx="5" cy="17" r="1"/><circle cx="16" cy="20" r="1"/>',
        noise: '<path d="M3 9v6M7 5v14M11 8v8M15 3v18M19 7v10M23 10v4"/>',
        layers: '<path d="m12 2 10 5-10 5L2 7ZM2 12l10 5 10-5M2 17l10 5 10-5"/>',
        ai: '<rect x="4" y="6" width="16" height="14" rx="4"/><path d="M12 2v4M9 12v2M15 12v2M9 17h6M1 10v6M23 10v6"/><circle cx="12" cy="2" r="1"/>',
        clipboard: '<path d="M8 5H4v17h16V5h-4M8 2h8v6H8ZM8 14l3 3 5-6"/>',
        archive: '<path d="M3 8h18v13H3ZM2 4h20v4H2ZM9 12h6"/>',
        refresh: '<path d="M21 10a9 9 0 0 0-15-6L3 7M3 2v5h5M3 14a9 9 0 0 0 15 6l3-3M16 17h5v5"/>',
        expand: '<path d="M8 3H3v5M16 3h5v5M3 16v5h5M21 16v5h-5"/>',
        training: '<path d="m2 7 10-5 10 5-10 5ZM6 10v6c4 3 8 3 12 0v-6M22 7v9"/>'
    };
    function svg(name) { return '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.65" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">' + (icons[name] || icons.shield) + '</svg>'; }
    function setIcons(root) { (root || document).querySelectorAll('[data-icon]').forEach(function (node) { node.innerHTML = svg(node.getAttribute('data-icon')); }); }
    function escape(value) { var n = document.createElement('span'); n.textContent = value == null ? '' : String(value); return n.innerHTML; }
    function number(value) { return value == null || !isFinite(value) ? '—' : Number(value).toLocaleString('zh-CN', { maximumFractionDigits: 1 }); }
    function percent(part, total) { return total > 0 && part != null ? Math.round(part / total * 1000) / 10 : null; }
    function width(value) { return value == null ? 0 : Math.max(0, Math.min(100, value)); }
    function days(count) {
        var result = [];
        for (var i = count - 1; i >= 0; i--) { var d = new Date(); d.setDate(d.getDate() - i); result.push(d.getFullYear() + '-' + String(d.getMonth() + 1).padStart(2, '0') + '-' + String(d.getDate()).padStart(2, '0')); }
        return result;
    }
    function demoData() {
        return {
            total: 1842,
            kpis: [
                { label: '在管职工', value: 1842, unit: '人', icon: 'people', color: '#5be7fa' },
                { label: '今日预警', value: 184, unit: '条', icon: 'alarm', color: '#ffce68' },
                { label: '高风险关注', value: 381, unit: '人', icon: 'risk', color: '#ff827f' },
                { label: '体检覆盖率', value: 96.8, unit: '%', icon: 'heart', color: '#51f0c2' },
                { label: 'AI 随访任务', value: 312, unit: '项', icon: 'ai', color: '#c2a8ff' }
            ],
            monitor: [
                { name: '血压异常', value: 32, icon: 'heart', change: '+12%' },
                { name: '血糖异常', value: 18, icon: 'drop', change: '+6%' },
                { name: '听力异常', value: 46, icon: 'ear', change: '+9%' },
                { name: '肺功能关注', value: 28, icon: 'lungs', change: '+8%' },
                { name: '腰背劳损', value: 61, icon: 'bone', change: '+11%' }
            ],
            jobs: [ { name: '井下作业', value: 965 }, { name: '采掘支护', value: 344 }, { name: '运输提升', value: 223 }, { name: '机电维修', value: 177 }, { name: '地面辅助', value: 133 } ],
            age: { labels: ['＜30岁', '30–44岁', '45–59岁', '≥60岁'], values: [153, 785, 665, 239] },
            trend: { dates: days(30), labels: ['健康活跃', '风险事件'], one: [26, 38, 15, 12, 35, 28, 17, 14, 22, 91, 39, 28, 118, 31, 18, 14, 26, 13, 19, 24, 16, 20, 178, 42, 22, 17, 28, 23, 35, 27], two: [4, 9, 5, 3, 8, 5, 4, 7, 6, 14, 8, 3, 16, 8, 6, 4, 7, 5, 4, 9, 6, 7, 21, 10, 9, 5, 11, 6, 9, 8] },
            risks: [ { name: '低风险', value: 1131 }, { name: '中风险', value: 330 }, { name: '高风险', value: 217 }, { name: '极高风险', value: 164 } ],
            quality: [ { label: '企业体检完成率', value: 96.8, icon: 'clipboard' }, { label: '复查闭环率', value: 93.1, icon: 'refresh' }, { label: '健康档案建档率', value: 100, icon: 'archive' } ],
            exposures: [ { name: '粉尘暴露风险', value: 62.7, icon: 'dust' }, { name: '噪声暴露风险', value: 56.3, icon: 'noise' }, { name: '呼吸系统风险', value: 24.8, icon: 'lungs' }, { name: '听力损伤风险', value: 25, icon: 'ear' }, { name: '骨关节劳损风险', value: 33.6, icon: 'bone' } ],
            alerts: [
                { time: '09:32', level: '高风险', color: '#ff8585', text: '井下作业班组：血压指标持续偏高', detail: '演示工号 K-0186 的近期血压指标触发关注。此处展示预警分级和跟进流程，可由健管师核对原始记录后处理。' },
                { time: '09:15', level: '重点关注', color: '#ffcd74', text: '采掘工：肺功能指标变化，待复核', detail: '演示肺功能复核任务。由健管师核对体检报告、岗位信息及历史指标，再安排后续跟进。' },
                { time: '08:57', level: '重点关注', color: '#ffcd74', text: '运输岗位：听力变化进入关注清单', detail: '演示听力指标关注任务。可关联职业健康档案并记录复查及随访进度。' },
                { time: '08:31', level: '一般预警', color: '#54d7ff', text: '井下支护工：血糖记录待健管师确认', detail: '演示血糖记录确认任务。系统展示待核对记录，具体判断由健康管理人员完成。' }
            ],
            advice: [
                { title: '复查提醒', text: '26 名职工的复查任务待跟进，建议及时核对并安排随访。', icon: 'clipboard', color: '#58e5f3' },
                { title: '高危岗位关怀', text: '关注粉尘、噪声暴露岗位，关联职业档案开展持续健康服务。', icon: 'shield', color: '#4ce1c5' },
                { title: '班组健康培训', text: '结合岗位画像安排专题培训，跟踪参与情况与服务反馈。', icon: 'training', color: '#b09bf9' }
            ]
        };
    }
    var mode = 'demo';
    var data;
    var charts = {};
    var busy = false;
    var generation = 0;
    var refreshAt = Date.now() + 30000;
    var reducedMotion = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    var currentController;
    var errors = [];

    function chart(id, option) {
        if (!window.echarts) { $(id).innerHTML = '<div class="chart-empty">图表组件暂不可用</div>'; return; }
        try {
            if (!charts[id]) { charts[id] = window.echarts.init($(id), null, { renderer: 'svg' }); }
            option.animation = !reducedMotion;
            option.animationDuration = 900;
            option.textStyle = { fontFamily: 'Microsoft YaHei, Arial, sans-serif' };
            charts[id].setOption(option, true);
        } catch (e) { $(id).innerHTML = '<div class="chart-empty">图表加载失败，请刷新重试</div>'; }
    }
    function donut(id, values, title, subtitle, colors) {
        var total = values.reduce(function (sum, v) { return sum + v.value; }, 0);
        chart(id, {
            color: colors || palette,
            tooltip: { trigger: 'item', backgroundColor: '#082c44', borderColor: '#29728f', textStyle: { color: '#d5eefa', fontSize: 11 }, formatter: '{b}<br/>{c} 人 · {d}%' },
            title: [ { text: total > 0 ? title : '暂无数据', left: 'center', top: '39%', textStyle: { color: '#d8f7ff', fontSize: id === 'risk-chart' ? 17 : 21, fontWeight: 600, fontFamily: 'Consolas, Microsoft YaHei' } }, { text: subtitle, left: 'center', top: '54%', textStyle: { color: '#73a8c4', fontSize: 9, fontWeight: 400 } } ],
            series: [ { type: 'pie', radius: ['63%', '83%'], center: ['50%', '50%'], hoverOffset: 3, label: { show: false }, labelLine: { show: false }, itemStyle: { borderColor: '#09213b', borderWidth: 2 }, data: total ? values : [{ value: 1, name: '暂无记录', itemStyle: { color: '#12344d' }, tooltip: { show: false } }] } ]
        });
    }
    function render() {
        $('kpi-grid').innerHTML = data.kpis.map(function (k) { return '<div class="kpi-card" style="--kpi-color:' + k.color + '"><span class="kpi-icon" data-icon="' + k.icon + '"></span><div><p>' + escape(k.label) + '</p><strong>' + number(k.value) + '</strong><small>' + k.unit + '</small></div></div>'; }).join('');
        $('monitor-list').innerHTML = data.monitor.map(function (m) { return '<div class="monitor-row"><span data-icon="' + m.icon + '"></span><span>' + escape(m.name) + '</span><strong>' + number(m.value) + '<em>' + (mode === 'demo' ? '人' : '次') + '</em></strong><small>' + (m.change ? '▴ ' + m.change : '今日') + '</small></div>'; }).join('');
        $('population-title').textContent = mode === 'demo' ? '岗位分布' : '在管人员性别分布';
        var jobsTotal = data.jobs.reduce(function (sum, v) { return sum + v.value; }, 0);
        $('population-legend').innerHTML = data.jobs.map(function (v, i) { return '<div class="legend-item" style="--item-color:' + palette[i] + '"><i></i><span>' + escape(v.name) + '</span><b>' + number(percent(v.value, jobsTotal)) + '%</b></div>'; }).join('');
        donut('population-chart', data.jobs, number(data.total), '在管人数', ['#21d5f3', '#36efbb', '#ffcc65', '#a38df5', '#6889c4', '#476782']);
        chart('age-chart', {
            grid: { top: 25, left: 33, bottom: 25, right: 10 },
            tooltip: { trigger: 'axis', backgroundColor: '#082c44', textStyle: { color: '#d5eefa', fontSize: 10 }, formatter: function (items) { return escape(items[0].name) + '：' + items[0].value + ' 人'; } },
            xAxis: { type: 'category', data: data.age.labels, axisLine: { lineStyle: { color: '#315676' } }, axisTick: { show: false }, axisLabel: { color: '#91bbda', fontSize: data.age.labels.length > 4 ? 8 : 9, interval: 0 } },
            yAxis: { type: 'value', minInterval: 1, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#6894b5', fontSize: 8 }, splitLine: { lineStyle: { color: '#1b4b6c77', type: 'dashed' } } },
            series: [ { type: 'bar', barMaxWidth: 32, data: data.age.values.map(function (v, i) { return { value: v, itemStyle: { color: ['#21c8ef', '#2ae7b5', '#29cee9', '#ffbf59', '#9389ed', '#5c99d2'][i] } }; }), label: { show: true, position: 'top', color: '#c3e6fa', fontSize: 9 }, itemStyle: { barBorderRadius: [2, 2, 0, 0] } } ]
        });
        $('trend-title').textContent = mode === 'demo' ? '近30日健康活跃与风险趋势' : '近30日健康评估与计划记录';
        $('trend-key-one').textContent = data.trend.labels[0]; $('trend-key-two').textContent = data.trend.labels[1];
        $('trend-period').textContent = '近30天 · ' + (mode === 'demo' ? '演示数据' : '当前账号');
        $('trend-caption').textContent = mode === 'demo' ? '健康动态持续追踪，职业风险主动关注' : '统计当前账号关联人员的评估与计划记录次数';
        chart('trend-chart', {
            grid: { top: 27, right: 15, bottom: 25, left: 38 },
            tooltip: { trigger: 'axis', backgroundColor: '#082c44', borderColor: '#29728f', textStyle: { color: '#d5eefa', fontSize: 10 } },
            xAxis: { type: 'category', boundaryGap: false, data: data.trend.dates.map(function (d) { return d.slice(5); }), axisLine: { lineStyle: { color: '#2b5d7e' } }, axisLabel: { color: '#8cb4d0', fontSize: 8, interval: 'auto' }, axisTick: { show: false } },
            yAxis: { type: 'value', minInterval: 1, axisLine: { show: false }, axisTick: { show: false }, axisLabel: { color: '#86abc8', fontSize: 9 }, splitLine: { lineStyle: { color: '#26516d77', type: 'dashed' } } },
            series: [
                { name: data.trend.labels[0], type: 'line', smooth: .32, symbol: 'circle', symbolSize: 4, data: data.trend.one, itemStyle: { color: '#31edc3', borderColor: '#d0fff4', borderWidth: 1 }, lineStyle: { width: 2, shadowColor: '#2feec183', shadowBlur: 7 }, areaStyle: { color: { type: 'linear', x: 0, y: 0, x2: 0, y2: 1, colorStops: [{ offset: 0, color: '#1ed4b373' }, { offset: 1, color: '#1ed4b303' }] } } },
                { name: data.trend.labels[1], type: 'line', smooth: .32, symbol: 'circle', symbolSize: 3, data: data.trend.two, itemStyle: { color: '#ffc34e', borderColor: '#ffebbb', borderWidth: 1 }, lineStyle: { width: 1.7, shadowColor: '#ffc34e66', shadowBlur: 5 }, areaStyle: { color: '#eeac2010' } }
            ]
        });
        $('risk-title').textContent = mode === 'demo' ? '职业健康风险分布' : '健康评估关注分布';
        $('risk-scope').textContent = mode === 'demo' ? '全体职工' : '当前账号关联人员';
        var riskTotal = data.risks.reduce(function (sum, v) { return sum + v.value; }, 0);
        var riskColors = mode === 'demo' ? palette : ['#ff696d', '#ffbd55', '#31eeb8', '#6484a0'];
        donut('risk-chart', data.risks, mode === 'demo' ? '职业健康' : '健康评估', '风险关注分布', riskColors);
        $('risk-legend').innerHTML = data.risks.map(function (r, i) { var p = percent(r.value, riskTotal); return '<div class="risk-row" style="--item-color:' + riskColors[i] + '"><i></i><span>' + escape(r.name) + '</span><strong>' + number(p) + '%</strong><em>' + number(r.value) + ' 人</em><div class="risk-track"><span style="width:' + width(p) + '%"></span></div></div>'; }).join('');
        $('quality-grid').innerHTML = data.quality.map(function (q, i) { return '<div class="quality-card" style="--quality-color:' + ['#42efb9', '#3bd5f5', '#41edc5'][i] + '"><span class="kpi-icon" data-icon="' + q.icon + '"></span><p>' + escape(q.label) + '</p><strong>' + number(q.value) + '<small>%</small></strong><div class="quality-track"><span style="width:' + width(q.value) + '%"></span></div></div>'; }).join('');
        $('alerts-title').textContent = mode === 'demo' ? '今日预警动态' : '最新预警记录';
        $('alert-list').innerHTML = data.alerts.length ? data.alerts.slice(0, 4).map(function (a, i) { return '<button type="button" class="alert-row" data-alert="' + i + '" style="--alert-color:' + a.color + '"><i></i><time>' + escape(a.time) + '</time><span class="alert-severity">' + escape(a.level) + '</span><p>' + escape(a.text) + '</p><span>›</span></button>'; }).join('') : '<div class="empty-state"><strong>暂无预警记录</strong>当前账号暂未查询到相关预警</div>';
        $('alerts-summary').textContent = mode === 'demo' ? '预警分级关注 · 及时跟进处置' : '当前账号的最新记录，非全平台统计';
        $('exposure-title').textContent = mode === 'demo' ? '专项风险画像' : '健康服务参与画像';
        $('exposure-list').innerHTML = data.exposures.map(function (v) { return '<div class="exposure-row"><span data-icon="' + v.icon + '"></span><span>' + escape(v.name) + '</span><b>' + number(v.value) + '%</b><div class="exposure-track"><i style="width:' + width(v.value) + '%"></i></div></div>'; }).join('');
        $('advice-title').textContent = mode === 'demo' ? 'AI 健康建议' : '健康服务提示';
        $('advice-tag').textContent = mode === 'demo' ? '演示' : '统计提示';
        $('advice-list').innerHTML = data.advice.map(function (a, i) { return '<button type="button" class="advice-card" data-advice="' + i + '" style="--advice-color:' + a.color + '"><span data-icon="' + a.icon + '"></span><div><strong>' + escape(a.title) + '</strong><p>' + escape(a.text) + '</p></div><span>›</span></button>'; }).join('');
        $('monitor-note').textContent = mode === 'demo' ? '演示指标 · 非实时诊断' : '当前账号今日记录次数，不代表异常人数';
        $('exposure-note').textContent = mode === 'demo' ? '演示风险画像 · 不作为诊断依据' : '已评估/未评估人数 ÷ 在管人数';
        $('advice-note').textContent = mode === 'demo' ? '建议仅作服务流程演示，需由健管师审核。' : '由已有统计生成的服务提示，非 AI 诊断。';
        $('mode-label').textContent = mode === 'demo' ? '模拟数据 · 演示模式' : '真实数据 · 当前账号';
        $('data-disclaimer').textContent = mode === 'demo' ? '当前为模拟数据，仅用于产品演示' : '真实数据按账号范围查询，未接入项以 — 显示';
        $('source-toggle').textContent = mode === 'demo' ? '切换真实数据' : '返回演示模式';
        $('last-update').textContent = new Date().toLocaleTimeString('zh-CN', { hour12: false, timeZone: 'Asia/Shanghai' });
        setIcons();
    }

    function request(url, body, signal) {
        var options = { credentials: 'same-origin', signal: signal, headers: { 'X-Requested-With': 'XMLHttpRequest' } };
        if (body) { options.method = 'POST'; options.headers['Content-Type'] = 'application/x-www-form-urlencoded'; options.body = new URLSearchParams(body).toString(); }
        return fetch(url, options).then(function (r) {
            if (!r.ok || (r.headers.get('Content-Type') || '').indexOf('json') === -1) { throw new Error('请求失败或登录已失效'); }
            return r.json();
        }).then(function (r) { if (r.code !== 20000 || !r.data) { throw new Error(r.message || r.msg || '统计接口暂不可用'); } return r.data; });
    }
    function realData(signal) {
        errors = [];
        function optional(promise, fallback, name) { return promise.catch(function (e) { if (e.name === 'AbortError') { throw e; } errors.push(name); return fallback; }); }
        return Promise.all([
            request('/newRegController/findDoctorAllInfo', null, signal),
            optional(request('/YhUserMakeCard/regInfoByTimeType', { timetype: 'day', datatype: 2 }, signal), null, '评估趋势'),
            optional(request('/YhUserMakeCard/regInfoByTimeType', { timetype: 'day', datatype: 8 }, signal), null, '计划趋势'),
            optional(request('/YhHeThresholdAlarm/findThresholdList?page=1&limit=5', null, signal), { data: [] }, '预警记录')
        ]).then(function (results) {
            var b = results[0], first = results[1], second = results[2], alarm = results[3];
            function val(key) { return b[key] == null ? null : Number(b[key]); }
            var total = val('useCardNum'), assessed = val('assessment'), high = val('highRisk'), other = val('otherRisk');
            var coverage = percent(assessed, total);
            return {
                total: total,
                kpis: [
                    { label: '在管用户', value: total, unit: '人', icon: 'people', color: '#5be7fa' },
                    { label: '累计预警', value: val('healthAlarmNum'), unit: '条', icon: 'alarm', color: '#ffce68' },
                    { label: '高风险关注', value: high, unit: '人', icon: 'risk', color: '#ff827f' },
                    { label: '评估覆盖率', value: coverage, unit: '%', icon: 'heart', color: '#51f0c2' },
                    { label: 'AI 随访任务', value: null, unit: '项', icon: 'ai', color: '#c2a8ff' }
                ],
                monitor: [ { name: '健康评估', value: val('healthAssessmentNum'), icon: 'heart' }, { name: '健康监测', value: val('healthMonitoringNum'), icon: 'pulse' }, { name: '压力评估', value: val('pressureAssessmentNum'), icon: 'shield' }, { name: '习惯评估', value: val('habitAssessmentNum'), icon: 'clipboard' }, { name: '健康计划记录', value: val('healthPlanDiaryNum'), icon: 'archive' } ],
                jobs: [ { name: '男性', value: val('manNum') || 0 }, { name: '女性', value: val('womanNum') || 0 } ],
                age: { labels: ['≤25', '26–35', '36–45', '46–55', '56–60', '＞60'], values: [val('ageType1'), val('ageType2'), val('ageType3'), val('ageType4'), val('ageType5'), val('ageType6')] },
                trend: { dates: first ? first.regtimeList : days(30), labels: ['健康评估', '计划记录'], one: first ? first.regdataInfo : new Array(30).fill(null), two: second ? second.regdataInfo : new Array(30).fill(null) },
                risks: [ { name: '高风险关注', value: high || 0 }, { name: '其他风险关注', value: other || 0 }, { name: '已评估其他', value: Math.max(0, assessed - high - other) }, { name: '待评估', value: val('notassessment') || 0 } ],
                quality: [ { label: '健康评估覆盖率', value: coverage, icon: 'clipboard' }, { label: '复查闭环率', value: null, icon: 'refresh' }, { label: '职业档案建档率', value: null, icon: 'archive' } ],
                exposures: [ { name: '已评估覆盖', value: coverage, icon: 'clipboard' }, { name: '待评估占比', value: percent(val('notassessment'), total), icon: 'people' }, { name: '粉尘暴露风险', value: null, icon: 'dust' }, { name: '噪声暴露风险', value: null, icon: 'noise' }, { name: '职业健康风险', value: null, icon: 'shield' } ],
                alerts: (alarm.data || []).slice(0, 5).map(function (a) { var name = a.name ? a.name.charAt(0) + '**' : '关联用户'; return { time: String(a.time || '').slice(-8, -3) || '--:--', level: a.status || '待核对', color: '#ffcd74', text: name + '：' + (a.typename || a.type || '指标预警'), detail: name + '：' + (a.content || a.typename || '请进入原有预警管理页面核对记录与处置状态。') }; }),
                advice: [
                    { title: '评估覆盖跟进', text: number(val('notassessment')) + ' 名在管用户尚待评估，可在健康评估菜单中核对。', icon: 'clipboard', color: '#58e5f3' },
                    { title: '风险关注清单', text: '已查询到 ' + number(high) + ' 名高风险关注用户，建议健管师核对具体记录。', icon: 'shield', color: '#4ce1c5' },
                    { title: '职业数据接入', text: '岗位暴露、职业体检与 AI 随访数据尚未接入，不展示模拟结果。', icon: 'archive', color: '#b09bf9' }
                ]
            };
        });
    }
    function message(text) { $('screen-message').textContent = text; $('screen-message').hidden = !text; }
    function refresh() {
        if (busy) { return; }
        if (mode === 'demo') { data = demoData(); render(); message(''); refreshAt = Date.now() + 30000; return; }
        busy = true;
        var run = ++generation;
        currentController = new AbortController();
        var timeout = setTimeout(function () { currentController.abort(); }, 20000);
        $('refresh-screen').disabled = true;
        $('source-toggle').disabled = true;
        $('mode-label').textContent = '正在查询真实数据…';
        realData(currentController.signal).then(function (result) {
            if (run !== generation) { return; }
            data = result; render();
            message(errors.length ? '部分统计暂不可用：' + errors.join('、') + '。缺失内容未使用模拟数据补齐。' : '');
        }).catch(function () {
            // Keep the previous source truthful if the mandatory summary request fails.
            mode = 'demo'; data = demoData(); render();
            message('真实统计暂时无法读取，已返回明确标注的演示模式。请检查登录状态或稍后重试。');
        }).finally(function () {
            clearTimeout(timeout); currentController = null; busy = false;
            $('refresh-screen').disabled = false; $('source-toggle').disabled = false;
            refreshAt = Date.now() + 30000;
        });
    }
    function openDetail(title, body, label) {
        $('detail-title').textContent = title;
        $('detail-content').innerHTML = '<p class="detail-label">' + escape(label || (mode === 'demo' ? '模拟场景 · 不对应真实职工记录' : '当前账号关联数据')) + '</p><p>' + escape(body) + '</p>';
        if ($('detail-dialog').showModal) { $('detail-dialog').showModal(); }
        else { $('detail-dialog').setAttribute('open', ''); }
    }
    function closeDetail() { if ($('detail-dialog').close) { $('detail-dialog').close(); } else { $('detail-dialog').removeAttribute('open'); } }
    document.addEventListener('click', function (event) {
        var alert = event.target.closest('[data-alert]');
        var advice = event.target.closest('[data-advice]');
        if (alert) { var a = data.alerts[Number(alert.getAttribute('data-alert'))]; openDetail(a.level + ' · 健康关注', a.detail); }
        if (advice) { var tip = data.advice[Number(advice.getAttribute('data-advice'))]; openDetail(tip.title, tip.text + (mode === 'demo' ? ' 当前为产品演示，不会下发随访或修改职工数据。' : ' 请通过现有业务菜单核对并处理。')); }
    });
    $('view-alerts').addEventListener('click', function () {
        openDetail(mode === 'demo' ? '预警动态 · 演示清单' : '预警记录', data.alerts.length ? data.alerts.map(function (a) { return a.time + ' ' + a.text; }).join('；') : '当前账号暂无预警记录。');
    });
    $('close-detail').addEventListener('click', closeDetail); $('confirm-detail').addEventListener('click', closeDetail);
    $('detail-dialog').addEventListener('click', function (e) { if (e.target === this) { var r = this.getBoundingClientRect(); if (e.clientX < r.left || e.clientX > r.right || e.clientY < r.top || e.clientY > r.bottom) { closeDetail(); } } });
    $('source-toggle').addEventListener('click', function () { if (!busy) { mode = mode === 'demo' ? 'real' : 'demo'; refresh(); } });
    $('refresh-screen').addEventListener('click', refresh);
    $('fullscreen-screen').addEventListener('click', function () {
        var result;
        try { result = document.fullscreenElement ? document.exitFullscreen() : $('health-dashboard').requestFullscreen(); }
        catch (e) { message('当前浏览器不支持页面全屏，可使用浏览器全屏功能。'); return; }
        if (result && result.catch) { result.catch(function () { message('全屏暂不可用，请在独立大屏页中使用全屏按钮。'); }); }
    });
    function resize() { Object.keys(charts).forEach(function (id) { charts[id].resize(); }); }
    window.addEventListener('resize', resize);
    if (window.ResizeObserver) { new ResizeObserver(resize).observe($('health-dashboard')); }
    function tick() {
        var now = new Date();
        $('screen-clock').textContent = now.toLocaleTimeString('zh-CN', { hour12: false, timeZone: 'Asia/Shanghai' });
        $('screen-date').textContent = now.toLocaleDateString('zh-CN', { timeZone: 'Asia/Shanghai', year: 'numeric', month: '2-digit', day: '2-digit' }) + '\n' + now.toLocaleDateString('zh-CN', { timeZone: 'Asia/Shanghai', weekday: 'long' });
        var remaining = Math.max(0, Math.ceil((refreshAt - Date.now()) / 1000));
        $('refresh-countdown').textContent = busy ? '统计加载中' : remaining + ' 秒自动刷新';
        if (!document.hidden && remaining === 0 && !busy) { refresh(); }
    }
    var timer = setInterval(tick, 1000);
    window.addEventListener('pagehide', function () { clearInterval(timer); if (currentController) { currentController.abort(); } Object.keys(charts).forEach(function (id) { charts[id].dispose(); }); });
    refresh(); tick();
})();
