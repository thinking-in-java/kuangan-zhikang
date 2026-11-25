# 网页系统接口接入记录（2026-11-14）

## 必须记住：微信参数尚未配置

实际服务器 http://192.168.1.105:18080 的 GET /ehr/miniapp/config 返回 wechatConfigured=false。真实 POST /ehr/miniapp/wechat/login 返回 HTTP503、code503、msg“微信小程序AppID/AppSecret尚未配置”。这是当前真实微信登录的阻塞项，不能通过切换演示账号绕过。

请后端在服务器配置该微信小程序对应的 EHR_WECHAT_APP_ID 和 EHR_WECHAT_APP_SECRET，并重启服务。AppSecret 不放小程序源码、不发聊天。配置后先确认 /config 的 wechatConfigured=true，再用微信开发者工具/真机产生的新 code 做真实端到端验收。

## 已完成前端适配

1. GET /config：动态读取微信可用状态和协议版本；未配置时提示原因，禁止伪登录。
2. POST /wechat/login：仅提交 code、privacyAccepted=true 和后台协议版本，保存后台会话有效期。
3. GET /me：恢复账号和绑定状态；旧模拟会话被清除，会话按后端地址隔离。
4. POST /binding/verify：校验姓名、employeeNo 和16位个人验证码。只校验，不假装已绑定，不消费验证码。
5. POST /binding/confirm：只提交同一用户获得的 confirmationToken 与 confirmed=true。不提交行内修改、职工UUID或角色覆盖。确认凭据只在内存中保留，过期/409/丢失后重新验证。
6. DELETE /session：调用服务器注销后清除本机登录；不会解除长期企业绑定。
7. GET /health-record：读取真实正式档案报告，空集合显示空状态，无模拟值。
8. POST /reports：使用本人会话，file + UUID Idempotency-Key；返回 code0/data.id/data.revision。不再调用旧8000端口的 /api/v1/reports/upload，不使用固定本地测试 Token。
9. GET /reports/{reportId}/{revision}：按精确版本读取处理状态，候选未归档报告明确标注，已发布前不当作正式档案。
10. GET /files/{fileId}：使用本人认证下载临时原件，再调用微信预览；不使用医生Cookie或公开直链。

以上路径统一以 /ehr/miniapp 为前缀。前端已支持新接口的 code/data 成功包和 code/msg 错误包、401清会话、409重新验证、422输入错误、429限流和503配置错误。

## 与前一版界面的变化

新接口明确不允许从确认页覆盖企业身份资料，行内编辑已取消。信息错误时联系企业管理员修改，再重新验证。

新接口是职工微信身份流程，没有提供医生小程序登录。旧医生入口不再可通过模拟角色进入；后台 /ehr/api 的医生Cookie/CSRF签发、批量发码、撤销及解除绑定操作均未调用。

AI、健康建议、随访、科普、通知等没有在本次资料中给出可用小程序接口。相应功能显示未接入，不返回旧模拟内容。原Mock文件仅作为测试夹具保留，运行包不引用；编译包已检查无旧模拟用户服务。

## 当前配置

项目根目录 .env.local：

```dotenv
VITE_EHR_BASE_URL=http://192.168.1.105:18080
VITE_ALLOW_HTTP_LOCAL=true
```

原先 VITE_UPLOAD_TEST_TOKEN 等本地配置不再被运行代码读取或打包。不要把上传测试凭证当作微信账号Token。正式发布应使用实际HTTPS域名，VITE_ALLOW_HTTP_LOCAL=false，并在微信后台配置合法请求、上传和下载域名。每次变更地址后重新打包。

## 已执行验证

- 真实 GET /config：200，wechatConfigured=false。
- 真实未认证 GET /me 与 GET /health-record：401，“请先使用微信登录”。
- 在未配置参数条件下，用无效测试code发起一次登录校验请求：503，“微信小程序AppID/AppSecret尚未配置”。未取得真实微信会话。
- 19项单元/契约测试通过；其中保留了旧的隔离Mock夹具测试，不能把这个数量理解为19个真实业务接口已上线。
- 浏览器测试：读取真实未配置状态；正向流程使用拦截的合成响应，覆盖动态协议、登录请求字段、422、短期只读确认、刷新丢失凭据、真实路径/会话上传头、UUID幂等号、报告id/revision回执、候选进度与DELETE注销。没有新建真实微信账号、职工绑定、验证码或报告。
- TypeScript检查和微信目标编译通过；ES2018产物语法检查通过。

## 尚未完成的真实验收

1. 后端配置真实微信参数后，用真实wx.login完成“登录→三项校验→本人确认→档案→上传→进度→原件→退出”验收；当前尚未发生。
2. 当前文档没有 /health-record 和报告详情的完整JSON字段示例。前端严格要求已明确的 reports、id、revision、files，未给出的临床数据不编造；需要用真实合成响应再核对字段映射。
3. 提供给测试职工的逐人验证码由企业后台签发；不使用123456，不自动执行Postman集合的后台写操作。
4. 正式域名、微信隐私配置、运营主体与服务协议文本，以及Android/iPhone真机端到端验证仍需完成。

这次是依据现有真实契约完成前端接入，不代表后台未配置情况下已经能够真实微信登录或所有业务均已上线。
