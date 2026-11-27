(function () {
    'use strict';
    window.MinePageState = {
        connect: function (index, admin, user) {
            var key = 'mine.active-page.' + user, saved = null, started = false;
            function safe(value) {
                if (!value || typeof value.url !== 'string' || value.url.length > 4096) return null;
                try {
                    var url = new URL(value.url, location.href);
                    if (url.origin !== location.origin || !/^\/(?:manage\/[A-Za-z0-9_/-]+|assets\/[A-Za-z0-9_/-]+\.html)$/.test(url.pathname)) return null;
                    return {url: url.pathname + url.search + url.hash, title: String(value.title || '工作空间').slice(0, 100)};
                } catch (error) { return null; }
            }
            try { saved = safe(JSON.parse(sessionStorage.getItem(key))); } catch (error) { /* Storage is optional. */ }
            function frame() {
                return document.querySelector('.layui-body .layui-tab-item.layui-show iframe.admin-iframe, .layui-body > div > iframe.admin-iframe');
            }
            function remember(readLocation) {
                if (!started) return;
                var current = frame();
                if (!current) return;
                var url = current.getAttribute('src');
                if (readLocation) {
                    try { if (current.contentWindow.location.pathname !== 'about:blank') url = current.contentWindow.location.href; } catch (error) { /* Ignore external frames. */ }
                }
                var titleNode = document.querySelector('.layui-body .layui-tab-title > li.layui-this .title, .layui-body-header-title');
                var state = safe({url: url, title: current.title || titleNode?.textContent});
                if (state) try { sessionStorage.setItem(key, JSON.stringify(state)); } catch (error) { /* Navigation still works. */ }
            }
            return {
                saved: saved,
                restore: function () {
                    if (!saved) return;
                    var path = saved.url.split(/[?#]/)[0];
                    index.openTab({url: path, title: saved.title});
                    var current = frame();
                    if (current) { current.src = saved.url; current.title = saved.title; }
                    admin.activeNav(path);
                },
                start: function () {
                    started = true;
                    var body = document.querySelector('.layui-body');
                    var observer = new MutationObserver(function () { remember(false); });
                    observer.observe(body, {subtree: true, childList: true, attributes: true, attributeFilter: ['src', 'class']});
                    body.addEventListener('load', function (event) { if (event.target === frame()) remember(true); }, true);
                    window.addEventListener('beforeunload', function () { remember(true); });
                    window.addEventListener('pagehide', function () { remember(true); });
                }
            };
        }
    };
})();
