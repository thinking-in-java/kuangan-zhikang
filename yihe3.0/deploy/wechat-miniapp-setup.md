# 微信小程序部署配置

AppID：`wx23dee6836f09f2f5`。

AppID 已保存于同目录 `wechat-miniapp.json`，AppSecret 留空。部署时填写私有副本，或通过环境变量注入；不要把实际密钥提交到Git。
此文件仅供后端部署读取，不能复制到小程序客户端或静态资源目录。

启动后端前设置 `EHR_WECHAT_CREDENTIALS_FILE` 为该 JSON 文件的绝对路径，然后重启后端。
使用成对环境变量 `EHR_WECHAT_APP_ID`、`EHR_WECHAT_APP_SECRET` 时，环境变量优先。
验证 `GET /ehr/miniapp/config` 返回 `wechatConfigured=true`，然后使用小程序微信登录进行实际验证。

小程序源码位于仓库根目录 `miniapp/`。复制 `.env.example` 为 `.env.local`，填写自己的后端地址。
本机开发后端地址为 `http://192.168.1.122:18081`，管理员网页为 `http://localhost:18081/register`。
执行 `pnpm install --frozen-lockfile` 后执行 `pnpm build:mp-weixin`，微信开发者工具导入 `miniapp/`。
微信身份绑定需后台为正确职工签发验证码，由本人在小程序核验确认。
