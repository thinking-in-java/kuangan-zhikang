/* Decorative motion uses CSS and inline SVG; login controls stay independent. */
(function () {
const password = document.getElementById('login-password');
const toggle = document.getElementById('password-toggle');
const capsHint = document.getElementById('caps-hint');
toggle.addEventListener('click', () => {
    const show = password.type === 'password';
    password.type = show ? 'text' : 'password';
    toggle.setAttribute('aria-label', show ? '隐藏密码' : '显示密码');
    toggle.setAttribute('aria-pressed', String(show));
    toggle.firstElementChild.className = 'layui-icon ' + (show ? 'layui-icon-eye' : 'layui-icon-eye-invisible');
});
password.addEventListener('keyup', event => {
    capsHint.hidden = !(event.getModifierState && event.getModifierState('CapsLock'));
});
password.addEventListener('blur', () => { capsHint.hidden = true; });

})();
