# 微信登录入口

未登录用户首先进入 pages/login/index。登录按钮先检查协议勾选，微信小程序环境调用 uni.login(provider:weixin) 获取 code，再调用统一 authApi.login。后端返回身份后，未绑定职工进入 pages/login/bind，绑定后进入首页；授权医生进入工作台，待审核医生进入审核状态页。有效会话恢复时按后端角色与绑定状态跳转，退出后返回登录入口。

不获取手机号、头像或昵称；不在前端保存微信 AppSecret、openid 或 session_key，也不记录 code。微信 code 应由后端换取身份，返回业务 token/profile，角色不能由入口授予。

当前 VITE_USE_MOCK=true 时，账号验证仍是演示服务，登录页明确提示。微信端仍调用原生登录获取临时 code；浏览器只在演示模式使用 browser-demo。真实认证需要实现 POST /api/v1/auth/wechat/login、POST /api/v1/auth/bind-worker、GET /api/v1/me/profile 并切换后端配置。2026-11-14 本次检查局域网后端超时，未验证这些接口或真实微信身份交换。上传专用 Token 不作为登录凭证。
