/* The server remains the source of menu permissions and destinations. */
(function (window) {
    'use strict';
    var paths = {
        home: '<path d="m3 10 9-7 9 7v10a1 1 0 0 1-1 1h-5v-7H9v7H4a1 1 0 0 1-1-1Z"/>',
        archive: '<rect x="4" y="5" width="16" height="16" rx="2"/><path d="M9 5V3h6v2M8 10h8M8 14h5M8 18h7"/>',
        people: '<circle cx="9" cy="8" r="3"/><path d="M3 21v-2a6 6 0 0 1 12 0v2M16 5a3 3 0 0 1 0 6M17 15a5 5 0 0 1 4 5"/>',
        assess: '<path d="M9 4H5v17h14V4h-4M9 3h6v4H9ZM8 12l2 2 5-5M8 18h7"/>',
        shield: '<path d="m12 3 8 3v6c0 5-8 9-8 9s-8-4-8-9V6Z"/><path d="M12 8v8M8 12h8"/>',
        chat: '<path d="M21 11a8 8 0 0 1-8 8H8l-5 3 1-6a8 8 0 1 1 17-5Z"/><path d="M8 11h.01M12 11h.01M16 11h.01"/>',
        bell: '<path d="M5 17h14l-2-3V9a5 5 0 0 0-10 0v5ZM10 21h4M12 3V2"/>',
        medicine: '<path d="m9 5-5 5a5 5 0 0 0 7 7l5-5a5 5 0 0 0-7-7ZM7 8l7 7"/>',
        activity: '<path d="M2 12h5l3-8 4 16 3-8h5"/>',
        chart: '<rect x="3" y="3" width="18" height="18" rx="3"/><path d="M7 16v-4M12 16V7M17 16v-7"/>',
        book: '<path d="M12 5v16M12 5C8 2 3 4 3 4v15s5-2 9 2c4-4 9-2 9-2V4s-5-2-9 1Z"/>',
        layers: '<path d="m12 3 10 5-10 5L2 8ZM2 12l10 5 10-5M2 16l10 5 10-5"/>',
        star: '<path d="m12 3 3 6 6 1-4.5 4.5 1 6.5-5.5-3-5.5 3 1-6.5L3 10l6-1Z"/>',
        flag: '<path d="M5 22V3h14l-3 5 3 5H5"/>',
        setting: '<path d="M4 7h16M4 17h16"/><circle cx="9" cy="7" r="3" fill="currentColor" fill-opacity=".12"/><circle cx="15" cy="17" r="3" fill="currentColor" fill-opacity=".12"/>'
    };
    function icon(name) {
        var key = /控制|工作|首页/.test(name) ? 'home' : /职工档案|健康档案|档案/.test(name) ? 'archive' : /评估|问卷|筛查/.test(name) ? 'assess' : /干预|方案|随访/.test(name) ? 'shield' : /聊天|沟通|互动/.test(name) ? 'chat' : /消息|告警|通知/.test(name) ? 'bell' : /用药/.test(name) ? 'medicine' : /饮食|运动|日记/.test(name) ? 'activity' : /会员|积分/.test(name) ? 'star' : /促进/.test(name) ? 'flag' : /内容|课堂/.test(name) ? 'book' : /数据/.test(name) ? 'chart' : /运营|服务/.test(name) ? 'layers' : /用户|客户|团队|注册|跟踪/.test(name) ? 'people' : 'setting';
        var span = document.createElement('span');
        span.className = 'mine-menu-icon layui-icon';
        span.setAttribute('aria-hidden', 'true');
        span.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.65" stroke-linecap="round" stroke-linejoin="round">' + paths[key] + '</svg>';
        return span;
    }
    window.MineNavigation = {
        mount: function (options) {
            var container = document.getElementById('side-menu');
            var search = document.getElementById('mine-menu-search');
            var clear = document.getElementById('mine-search-clear');
            var empty = document.getElementById('mine-menu-empty');
            var status = document.getElementById('mine-search-status');
            var records = [];
            var savedExpansion = null;
            var sequence = 0;

            function build(item, depth) {
                var children = (item.children || []).filter(function (child) { return child.type != 3; });
                var name = String(item.name || '未命名菜单').trim();
                var node = document.createElement(depth === 0 ? 'li' : 'dd');
                node.className = depth === 0 ? 'layui-nav-item mine-menu-node' : 'mine-menu-node';
                var link = document.createElement('a');
                link.href = 'javascript:;';
                link.title = name;
                link.className = 'mine-menu-link';
                if (depth === 0) { link.appendChild(icon(name)); }
                var label = document.createElement('cite');
                label.textContent = name;
                link.appendChild(label);
                var record = { item: item, node: node, link: link, name: name.toLowerCase(), children: [] };
                records.push(record);
                node.appendChild(link);
                if (children.length) {
                    var list = document.createElement('dl');
                    list.className = 'layui-nav-child';
                    list.id = 'mine-submenu-' + (++sequence);
                    link.setAttribute('aria-controls', list.id);
                    link.setAttribute('aria-expanded', 'false');
                    children.forEach(function (child) {
                        var result = build(child, depth + 1);
                        record.children.push(result);
                        list.appendChild(result.node);
                    });
                    node.appendChild(list);
                } else if (item.permissionValue === 'cmd:single:signon') {
                    link.addEventListener('click', function () { options.onSingleSignOn(); });
                } else if (item.uri && item.uri !== '#') {
                    link.setAttribute('lay-href', item.uri);
                }
                link.addEventListener('keydown', function (event) {
                    if (event.key === ' ') { event.preventDefault(); link.click(); }
                });
                return record;
            }
            var roots = (options.menus || []).filter(function (item) { return item.type != 3; }).map(function (item) { return build(item, 0); });
            container.textContent = '';
            roots.forEach(function (record) { container.appendChild(record.node); });
            // Expand the current page's ancestors before Layui attaches its navigation handlers.
            records.forEach(function (record) {
                if (!record.children.length && record.item.uri === options.homePath) {
                    record.node.classList.add('layui-this');
                    var parent = record.node.parentElement;
                    while (parent && parent !== container) {
                        if (parent.classList.contains('mine-menu-node')) { parent.classList.add('layui-nav-itemed'); }
                        parent = parent.parentElement;
                    }
                }
            });
            window.layui.element.render('nav', 'admin-side-nav');

            function syncState() {
                records.forEach(function (record) {
                    if (record.children.length) {
                        record.link.setAttribute('aria-expanded', String(record.node.classList.contains('layui-nav-itemed')));
                        record.node.classList.toggle('mine-parent-current', !!record.node.querySelector('.layui-this'));
                    } else {
                        if (record.node.classList.contains('layui-this')) { record.link.setAttribute('aria-current', 'page'); }
                        else { record.link.removeAttribute('aria-current'); }
                    }
                });
            }
            var observer = new MutationObserver(syncState);
            observer.observe(container, { subtree: true, attributes: true, attributeFilter: ['class'] });
            syncState();

            function filter() {
                var query = search.value.trim().toLowerCase();
                clear.hidden = !query;
                if (query && !savedExpansion) { savedExpansion = records.map(function (r) { return r.node.classList.contains('layui-nav-itemed'); }); }
                var count = 0;
                function visit(record, ancestorMatched) {
                    var own = !query || ancestorMatched || record.name.indexOf(query) !== -1;
                    var childVisible = false;
                    record.children.forEach(function (child) { if (visit(child, own)) { childVisible = true; } });
                    var visible = own || childVisible;
                    record.node.hidden = !visible;
                    if (query && visible && record.children.length) { record.node.classList.add('layui-nav-itemed'); }
                    if (query && visible && !record.children.length) { count++; }
                    return visible;
                }
                roots.forEach(function (record) { visit(record, false); });
                if (!query && savedExpansion) {
                    records.forEach(function (r, i) { r.node.classList.toggle('layui-nav-itemed', savedExpansion[i]); });
                    savedExpansion = null;
                }
                empty.hidden = !query || count > 0;
                status.textContent = query ? (count ? '找到 ' + count + ' 个菜单' : '试试其他关键词') : '';
                syncState();
            }
            search.addEventListener('input', filter);
            clear.addEventListener('click', function () { search.value = ''; filter(); search.focus(); });
            search.addEventListener('keydown', function (event) {
                if (event.key === 'Escape') { search.value = ''; filter(); }
            });
        }
    };
})(window);
