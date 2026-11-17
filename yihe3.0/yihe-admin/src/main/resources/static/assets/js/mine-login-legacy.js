/* Keep the existing Layui, slider verification and /login authentication contract. */
layui.use(['layer', 'form', 'notice', 'index', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var notice = layui.notice;
    var admin = layui.admin;
    var submitting = false;
    var verificationDialog;

    layui.index.clearTabCache();
    admin.changeTheme('theme-colorful');
    admin.putSetting('tabAutoRefresh', true);
    admin.putSetting('pageTabs', false);

    function getCookie(name) {
        var prefix = name + '=';
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].replace(/^\s+/, '');
            if (cookie.indexOf(prefix) === 0) {
                return unescape(cookie.substring(prefix.length));
            }
        }
    }

    // Preserve the application's existing seven-day login behavior.
    var username = getCookie('username');
    var password = getCookie('password');
    if (username != null && password != null) {
        $.ajax({
            type: 'post', url: '/login', timeout: 15000,
            data: { username: username, password: password, rememberMe: true, logintype: 'NOPASSWORD' },
            success: function (response) {
                if (response.code == 200) { window.location.href = '/register'; }
            }
        });
    }

    form.verify({
        name: function (value) { if (!value || !value.trim()) { return '账号不能为空'; } },
        password: function (value) { if (!value) { return '密码不能为空'; } }
    });

    form.on('submit(login-submit)', function (obj) {
        if (submitting || verificationDialog) { return false; }
        var fields = obj.field;
        // This value is part of the existing server contract, following slider verification.
        fields.code = '123465';
        $('#login-status').text('');
        verificationDialog = layer.open({
            type: 1, title: '请完成安全验证',
            area: ['290px', '250px'], shade: 0.65,
            id: 'LAY_layuipro', moveType: 1, content: $('#box3'),
            end: function () { verificationDialog = null; },
            success: function () {
                SliderBar('slideBar', {
                    dataList: ['0', '1'],
                    success: function () {
                        if (submitting) { return; }
                        submitting = true;
                        $('.login-submit').prop('disabled', true).attr('aria-busy', 'true');
                        $('#login-status').text('正在登录，请稍候…');
                        $.ajax({
                            type: 'post', url: '/login', data: fields, timeout: 15000,
                            success: function (response) {
                                if (response.code == 200) {
                                    $('#login-status').text('登录成功，正在进入工作台…');
                                    window.location.href = '/register';
                                } else {
                                    $('#login-status').text(response.msg || '登录失败，请重试。');
                                    notice.msg(response.msg || '登录失败，请重试。', { icon: 2 });
                                }
                            },
                            error: function () { $('#login-status').text('暂时无法连接服务，请稍后重试。'); },
                            complete: function () {
                                submitting = false;
                                $('.login-submit').prop('disabled', false).removeAttr('aria-busy');
                                layer.close(verificationDialog);
                            }
                        });
                    },
                    fail: function () {}
                });
            }
        });
        return false;
    });

    var prompt = $('#prompt').val();
    if (prompt) {
        $('#login-status').text(prompt);
        notice.msg(prompt, { icon: 2 });
    }
});
