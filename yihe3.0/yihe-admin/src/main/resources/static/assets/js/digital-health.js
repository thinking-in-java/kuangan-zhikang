/* Digital health UI preview. No model calls, document uploads or business mutations. */
(function (window) {
    'use strict';
    var PAGE = '/assets/digital-health/index.html';
    var ASSET = '/assets/images/';
    var profile = { code: '', name: '健康管理师', gender: 0 };
    var sessions = [];
    var activeId;
    var uiListeners = [];
    var chats = [];
    var floating;
    var doctorModule;
    var counter = 0;
    var commands = [
        { key: 'report', name: '今日日报', icon: '▤', prompt: '查看今日健康服务日报' },
        { key: 'scan', name: '立即扫描', icon: '↗', prompt: '查看健康关注扫描入口' },
        { key: 'urgent', name: '紧急事项', icon: '♧', prompt: '查看需要关注的事项' },
        { key: 'overview', name: '数据概览', icon: '▦', prompt: '查看职业健康数据概览' },
        { key: 'plan', name: '自主规划', icon: '✧', prompt: '了解健康服务规划' },
        { key: 'schedule', name: '今日日程', icon: '□', prompt: '查看今日随访日程' }
    ];
    var starters = [
        { icon: '▤', title: '读懂体检报告', text: '梳理体检指标，关注健康变化', prompt: '帮我了解体检报告解读功能' },
        { icon: '♧', title: '职业健康画像', text: '连接岗位、暴露与健康档案', prompt: '介绍职业健康画像功能' },
        { icon: '♡', title: '制定随访关怀', text: '建立有温度的健康服务计划', prompt: '介绍随访关怀功能' },
        { icon: '✧', title: '整理服务日报', text: '汇总进度，跟踪待办与反馈', prompt: '查看今日健康服务日报' }
    ];
    function escape(value) { var n = document.createElement('span'); n.textContent = value == null ? '' : String(value); return n.innerHTML; }
    function avatar(gender) { return Number(gender) === 1 ? ASSET + 'digital-doctor-male.png' : Number(gender) === 2 ? ASSET + 'digital-doctor-female.png' : ASSET + 'kuangan-zhikang-mark.svg'; }
    function identity() { return { code: profile.code, name: profile.name, gender: profile.gender, avatar: avatar(profile.gender) }; }
    function timeLabel() { return new Date().toLocaleTimeString('zh-CN', { timeZone: 'Asia/Shanghai', hour: '2-digit', minute: '2-digit', hour12: false }); }
    function greeting() { var hour = Number(new Date().toLocaleString('en-US', { timeZone: 'Asia/Shanghai', hour: 'numeric', hour12: false })); return hour < 12 ? '上午好' : hour < 18 ? '下午好' : '晚上好'; }
    function welcome() { return greeting() + '，' + profile.name + '。\n我是数字健康师，很高兴陪你一起关注矿工职业健康。\n\n当前为界面预览：可以体验对话、快捷入口和材料选择。大模型与业务接口暂未接入，示例回复不会执行真实任务。'; }
    function current() { return sessions.filter(function (s) { return s.id === activeId; })[0]; }
    function notify() { uiListeners.forEach(function (listener) { listener(); }); }
    function newSession() {
        var s = { id: String(Date.now()) + '-' + (++counter), title: '新的健康对话', time: timeLabel(), messages: [] };
        sessions.unshift(s); activeId = s.id;
        chats.forEach(function (chat) { if (chat.clearMessages) { chat.clearMessages(); } });
        notify(); return s;
    }
    function ensureSession() { if (!current()) { newSession(); } }
    function previewReply(text) {
        var body = String(text || '');
        if (/日报|成果|汇总/.test(body)) { return '【日报界面演示】\n这里将汇总当天服务进度、风险关注、随访反馈与待办事项。\n\n后续接入账号范围内的数据后，可生成有来源依据的服务日报。当前没有读取统计数据，也没有生成真实日报。'; }
        if (/扫描|紧急|预警|异常/.test(body)) { return '【关注事项界面演示】\n这里将展示需要核对的指标变化、待评估记录和逾期随访，支持进入详情跟进。\n\n当前仅展示交互流程，没有扫描职工数据、判定风险或创建任务。'; }
        if (/体检|报告|材料/.test(body)) { return '【报告解读界面演示】\n后续可关联体检报告，整理检查日期、项目、结果、单位与参考范围，并连接职业健康档案。\n\n当前支持材料选择的本地界面预览；没有上传文件、识别报告或调用大模型。'; }
        if (/职业|画像|岗位|档案|数据|概览/.test(body)) { return '【职业健康画像界面演示】\n这里将连接岗位信息、职业暴露、历次体检与健康评估，形成持续更新的服务视图。\n\n可以从顶部选择演示职工体验上下文切换。本轮暂未接入真实职工数据。'; }
        if (/日程|随访|规划|计划/.test(body)) { return '【随访关怀界面演示】\n这里将帮助整理服务目标、随访时间、跟进方式和反馈记录，供健康管理人员审核与处理。\n\n当前不会安排日程、发送通知或下发干预；执行能力将在后续接入。'; }
        return '已收到你的消息：“' + body.slice(0, 160) + '”。\n\n当前为数字健康师的界面预览，回复为预设示例，暂未调用大模型。你可以试试“体检报告”“职业健康画像”“随访计划”或“今日日报”。';
    }
    function addMessage(role, text, source) {
        ensureSession();
        var session = current();
        session.messages.push({ role: role, text: text });
        if (role === 'user' && session.title === '新的健康对话') { session.title = text.slice(0, 18); }
        chats.forEach(function (chat) { if (chat !== source && chat.addMessage) { chat.addMessage({ role: role, text: text }); } });
        notify();
    }
    function createChat(host) {
        var chat = document.createElement('deep-chat');
        chat.className = 'dh-deep-chat';
        chat.style.cssText = 'width:100%;height:100%;border:0;border-radius:0;background:transparent;font-family:Microsoft YaHei,Arial,sans-serif';
        chat.chatStyle = { width: '100%', height: '100%', border: 'none', borderRadius: '0', backgroundColor: 'transparent' };
        chat.textInput = { placeholder: { text: '问问报告、档案或随访…（界面演示）', style: { color: '#92a2b1' } }, styles: { container: { borderRadius: '12px', border: '1px solid #dce9ee', backgroundColor: '#ffffff', margin: '8px 13px 10px', boxShadow: 'none' }, text: { fontSize: '12px', padding: '12px 36px 12px 12px', minHeight: '24px', maxHeight: '85px' }, focus: { border: '1px solid #47b7b1' } }, characterLimit: 1500 };
        chat.messageStyles = { default: { shared: { bubble: { fontSize: '12px', lineHeight: '1.8', padding: '11px 13px', borderRadius: '12px', maxWidth: '88%' }, outerContainer: { marginTop: '12px' } }, ai: { bubble: { backgroundColor: '#ffffff', color: '#40596a', border: '1px solid #e6eef2' } }, user: { bubble: { backgroundColor: '#dff5f0', color: '#205f61' } } }, error: { bubble: { backgroundColor: '#fff4e8', color: '#a36a2b', fontSize: '11px' } } };
        chat.submitButtonStyles = { submit: { container: { default: { borderRadius: '8px', backgroundColor: '#22aaa1', width: '28px', height: '28px', marginRight: '9px' }, hover: { backgroundColor: '#168f88' } }, svg: { styles: { default: { fill: '#fff', width: '17px', height: '17px' } } } } };
        chat.remarkable = { html: false, applyHTML: false, breaks: true };
        chat.browserStorage = false;
        chat.history = current() ? current().messages.slice() : [];
        chat.introMessage = { text: welcome() };
        chat.errorMessages = { overrides: { default: '对话组件暂不可用，请稍后刷新。' } };
        chat.requestBodyLimits = { maxMessages: 1 };
        chat.connect = { handler: function (body, signals) {
            var list = body.messages || [];
            var text = list.length ? list[list.length - 1].text || '' : '';
            addMessage('user', text, chat);
            var reply = previewReply(text);
            Promise.resolve(signals.onResponse({ text: reply })).then(function () { addMessage('ai', reply, chat); }).catch(function () { signals.onResponse({ error: '示例回复暂不可用，请重新尝试。' }); });
        } };
        host.appendChild(chat); chats.push(chat); return chat;
    }
    function submit(chat, prompt) { if (chat && typeof chat.submitUserMessage === 'function') { chat.submitUserMessage({ text: prompt }); } else { toast('对话组件正在准备，请稍后重试。'); } }
    function toast(text) {
        var node = document.getElementById('dh-toast');
        if (!node) { node = document.createElement('div'); node.id = 'dh-toast'; node.className = 'dh-toast'; node.setAttribute('role', 'status'); document.body.appendChild(node); }
        node.textContent = text; node.hidden = false;
        clearTimeout(node._hideTimer); node._hideTimer = setTimeout(function () { node.hidden = true; }, 4000);
    }
    function attachPreview(input, chat) {
        input.addEventListener('change', function () {
            var files = Array.prototype.slice.call(input.files || [], 0, 3);
            if (files.length && chat.addMessage) { var text = '已选择材料：' + files.map(function (f) { return f.name; }).join('、') + '\n仅本地界面预览，文件未上传或解析。'; chat.addMessage({ role: 'user', text: text }); addMessage('user', text, chat); }
            input.value = '';
        });
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
        var controls=document.createElement('div');controls.className='dh-motion-controls';controls.setAttribute('aria-label','医生动作');
        controls.innerHTML='<button type="button" data-motion="wave">挥手</button><button type="button" data-motion="nod">点头</button><button type="button" data-motion="listen">聆听</button><button type="button" data-motion="smile">微笑</button>';
        root.appendChild(controls);
        doctorModule=doctorModule||import('./digital-doctor-portrait.js?v=20261114g');
        doctorModule.then(async function(module){
            if(!stage.isConnected)return;
            var controller=await module.mountDoctor(stage,profile.gender);
            if(!stage.isConnected){controller.dispose();return;}
            launcher._doctor3d=controller;
            launcher.classList.add('dh-live-avatar-ready');
            launcher.title='数字健康师：保留原形象的分区动画，可按住拖动';
            root.dataset.avatarMode='portrait-rig';
        }).catch(function(){
            stage.remove();controls.hidden=true;root.dataset.avatarMode='fallback';
            launcher.title='当前显示原始医生形象；浏览器暂未启用角色动画';
            var fallback=document.createElement('small');fallback.className='dh-avatar-fallback-note';fallback.textContent='静态备用';launcher.appendChild(fallback);
        });
        controls.addEventListener('click',function(event){var b=event.target.closest('[data-motion]');if(!b)return;if(launcher._doctor3d){launcher._doctor3d.play(b.getAttribute('data-motion'));}else{toast('角色动画正在准备，请稍后重试。');}});
        var watch=new MutationObserver(function(){controls.hidden=!root.querySelector('.dh-floating-panel').hidden;});
        watch.observe(root.querySelector('.dh-floating-panel'),{attributes:true,attributeFilter:['hidden']});
        window.addEventListener('pagehide',function(){watch.disconnect();});
    }
    function makeInteractive(root, launcher, panel, open) {
        var bubble = document.createElement('div'); bubble.className = 'dh-doctor-bubble'; bubble.hidden = true;
        bubble.innerHTML = '<button class="dh-bubble-dismiss" type="button" aria-label="关闭提示">×</button><div class="dh-bubble-heading"><i></i> 数字健康师<span>陪伴提示</span></div><p class="dh-bubble-text" role="status" aria-live="polite"></p><div class="dh-bubble-actions"><button type="button" class="dh-bubble-chat">聊一聊 <span>↗</span></button><small>拖动娃娃，可调整位置</small></div>';
        root.appendChild(bubble);
        var badge = document.createElement('span'); badge.className = 'dh-doctor-badge'; badge.textContent = '1'; badge.hidden = true; launcher.appendChild(badge);
        var drag, suppressUntil = 0, hideTimer, greetTimer, interval, lastInteraction = Date.now(), tipIndex = 0;
        var tips = ['职业健康档案、体检报告、随访关怀，想从哪里开始？', '在“健康评估 → 数字健康师”里，可以打开完整工作台。', '想整理体检材料？可以先体验对话面板的“添加材料”入口。', '我在这里，随时陪你了解职业健康服务。'];
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
            if (read) { badge.hidden = true; launcher.setAttribute('aria-label', '打开数字健康师'); }
        }
        function greetMotion() {
            if (launcher._doctor3d) { launcher._doctor3d.play('wave'); }
            root.classList.remove('dh-is-greeting');
            void launcher.offsetWidth;
            root.classList.add('dh-is-greeting');
            clearTimeout(greetTimer); greetTimer = setTimeout(function () { root.classList.remove('dh-is-greeting'); }, 950);
        }
        function say(text, duration) {
            if (root.hidden || !panel.hidden || document.hidden || drag) { return; }
            bubble.querySelector('.dh-bubble-text').textContent = text;
            bubble.hidden = false; positionBubble();
            badge.hidden = false; launcher.setAttribute('aria-label', '数字健康师有一条提示，点击打开');
            greetMotion(); clearTimeout(hideTimer); hideTimer = setTimeout(function () { hideBubble(false); }, duration || 11000);
        }
        function activate() {
            if (Date.now() < suppressUntil) { return; }
            lastInteraction = Date.now(); hideBubble(true); open(); positionPanel();
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
        bubble.querySelector('.dh-bubble-dismiss').addEventListener('click', function () { lastInteraction = Date.now(); hideBubble(true); });
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
            if (root.hidden || !panel.hidden) { hideBubble(true); }
        }, 55000);
        var visibility = new MutationObserver(function () { if (root.hidden || !panel.hidden) { hideBubble(true); } });
        visibility.observe(root, { attributes: true, attributeFilter: ['hidden'] });
        visibility.observe(panel, { attributes: true, attributeFilter: ['hidden'] });
        window.addEventListener('pagehide', function () { clearTimeout(firstHello); clearTimeout(hideTimer); clearTimeout(greetTimer); clearInterval(interval); visibility.disconnect(); });
    }
    function mountFloating(options) {
        if (!window.customElements.get('deep-chat')) { window.customElements.whenDefined('deep-chat').then(function () { mountFloating(options); }); return; }
        if (floating) { return; }
        profile = { code: String(options.code || ''), name: String(options.name || '健康管理师'), gender: Number(options.gender) || 0 };
        ensureSession();
        var root = document.createElement('div'); root.className = 'dh-floating-root';
        root.innerHTML = '<button class="dh-launcher" type="button" title="数字健康师 · 点击打开对话" aria-label="打开数字健康师" aria-expanded="false" aria-controls="dh-floating-panel"><span class="dh-launcher-glow"></span><img src="' + avatar(profile.gender) + '" alt="' + (profile.gender === 1 ? '男医生' : profile.gender === 2 ? '女医生' : '数字健康师') + '"/><span class="dh-launcher-label"><i></i> 数字健康师 <b>＋</b></span></button><section class="dh-floating-panel" id="dh-floating-panel" role="dialog" aria-label="数字健康师对话面板" hidden><header class="dh-panel-header"><span class="dh-panel-avatar"><img src="' + avatar(profile.gender) + '" alt=""/></span><div class="dh-panel-name"><strong>数字健康师 <small>PREVIEW</small></strong><span>职业健康助手 · 界面预览</span></div><button type="button" class="dh-open-page" title="打开完整数字健康师页面">工作台 ↗</button><button class="dh-panel-minimize" type="button" aria-label="收起对话面板" title="收起">−</button></header><div class="dh-panel-banner"><i></i> 欢迎，<strong class="dh-account-name"></strong><span>暂未接入大模型</span></div><div class="dh-floating-chat-host"></div><div class="dh-quick-grid"></div><footer class="dh-panel-footer"><button type="button" class="dh-attach-button" title="添加材料，仅本地预览">＋ 材料</button><button type="button" class="dh-voice-button" title="语音入口预览，不会开启录音">♩ 语音</button><span>示例回复 · 不执行真实任务</span></footer><input class="dh-local-files" type="file" accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx" multiple hidden/></section>';
        document.body.appendChild(root);
        root.querySelector('.dh-account-name').textContent = profile.name;
        var launcher = root.querySelector('.dh-launcher'), panel = root.querySelector('.dh-floating-panel');
        var chat = createChat(root.querySelector('.dh-floating-chat-host'));
        floating = { root: root, launcher: launcher, panel: panel, chat: chat };
        addAnimatedDoctor(launcher, root);
        makeInteractive(root, launcher, panel, function () { panel.hidden = false; launcher.hidden = true; launcher.setAttribute('aria-expanded', 'true'); root.querySelector('.dh-panel-minimize').focus(); });
        root.querySelector('.dh-panel-minimize').addEventListener('click', function () { closeFloating(); launcher.focus(); });
        root.querySelector('.dh-open-page').addEventListener('click', openPage);
        root.querySelector('.dh-voice-button').addEventListener('click', function () { toast('语音入口已预留，本轮未启用录音。'); });
        var files = root.querySelector('.dh-local-files');
        root.querySelector('.dh-attach-button').addEventListener('click', function () { files.click(); });
        attachPreview(files, chat);
        var quick = root.querySelector('.dh-quick-grid');
        quick.innerHTML = commands.map(function (c) { return '<button type="button" data-command="' + c.key + '"><span>' + c.icon + '</span>' + c.name + '</button>'; }).join('');
        quick.addEventListener('click', function (event) { var b = event.target.closest('[data-command]'); if (b) { submit(chat, commands.filter(function (c) { return c.key === b.getAttribute('data-command'); })[0].prompt); } });
        document.addEventListener('keydown', function (event) { if (event.key === 'Escape' && !panel.hidden) { closeFloating(); launcher.focus(); } });
        // Keep a single assistant visible when its dedicated workbench is open.
        var frameObserver = new MutationObserver(function () {
            if (!root.isConnected) { return; }
            var frame = document.querySelector('.layui-body iframe.admin-iframe');
            root.hidden = !!(frame && frame.getAttribute('src') && frame.getAttribute('src').split('?')[0] === PAGE);
        });
        frameObserver.observe(document.querySelector('.layui-body') || document.body, { subtree: true, childList: true, attributes: true, attributeFilter: ['src'] });
        window.addEventListener('pagehide', function () { frameObserver.disconnect(); });
    }
    function mountPage() {
        if (!window.customElements.get('deep-chat')) { window.customElements.whenDefined('deep-chat').then(mountPage); return; }
        var parentApi;
        try { if (window.parent !== window && window.parent.DigitalHealth) { parentApi = window.parent.DigitalHealth; profile = parentApi.getIdentity(); } } catch (e) {}
        if (!profile.code) { profile.name = '健康管理师'; }
        ensureSession();
        document.getElementById('dh-page-avatar').src = avatar(profile.gender);
        document.getElementById('dh-side-avatar').src = avatar(profile.gender);
        document.getElementById('dh-welcome-title').textContent = greeting() + '，' + profile.name;
        var chat = createChat(document.getElementById('dh-page-chat-host'));
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
            document.getElementById('dh-session-list').innerHTML = filtered.length ? filtered.map(function (s) { return '<button type="button" class="dh-session' + (s.id === activeId ? ' is-active' : '') + '" data-session="' + s.id + '"><strong>' + escape(s.title) + '</strong><span>' + s.time + ' · ' + s.messages.length + ' 条消息</span></button>'; }).join('') : '<p class="dh-no-session">没有匹配的会话</p>';
            intro.hidden = current() && current().messages.length > 0;
            document.getElementById('dh-page-chat-host').classList.toggle('has-conversation', intro.hidden);
        }
        uiListeners.push(drawSessions); drawSessions();
        document.getElementById('dh-new-chat').addEventListener('click', function () { newSession(); });
        document.getElementById('dh-history-search').addEventListener('input', drawSessions);
        document.getElementById('dh-session-list').addEventListener('click', function (event) {
            var b = event.target.closest('[data-session]'); if (!b) { return; }
            activeId = b.getAttribute('data-session');
            var selected = current();
            if (chat.clearMessages) { chat.clearMessages(); }
            if (selected && chat.addMessage) { selected.messages.forEach(function (m) { chat.addMessage(m); }); }
            drawSessions();
        });
        document.getElementById('dh-patient-context').addEventListener('change', function (event) { var value = event.target.value; document.getElementById('dh-context-status').textContent = value ? '当前演示对象：' + value + ' · 不关联真实档案' : 'Enter 发送 · Shift + Enter 换行'; });
        document.getElementById('dh-page-voice').addEventListener('click', function () { toast('语音输入样式已预留，本轮未启用录音。'); });
        var files = document.getElementById('dh-page-files');
        document.getElementById('dh-page-attach').addEventListener('click', function () { files.click(); }); attachPreview(files, chat);
    }
    window.DigitalHealth = { mountFloating: mountFloating, mountPage: mountPage, getIdentity: identity, openPage: openPage, closeFloating: closeFloating };
})(window);
