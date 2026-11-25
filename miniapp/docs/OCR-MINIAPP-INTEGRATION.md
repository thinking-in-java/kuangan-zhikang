# 小程序 OCR 任务接口接入记录

日期：2026-11-15。依据用户提供的 ocr-worker-api(1).md，仅接入小程序业务接口；不实现电脑B识别引擎。

## 已接入

- POST /ehr/miniapp/reports：multipart file，使用当前微信用户登录会话 Authorization 和每文件唯一 UUID Idempotency-Key。失败重试沿用原UUID，已成功文件跳过；不提交其他职工recordId。支持PDF/PNG/JPEG，单文件20MiB（字节和PDF页数最终由服务器校验）。
- 解析原有 id、revision，以及新增 jobId、ocrTask（jobId、state、stage、percent、attemptCount及后台提供的错误字段）。缺失百分比不补0，不自行递增或伪造完成。
- GET /ehr/miniapp/reports/{id}/{revision}：每3秒读取当前完整报告快照。单文件上传成功后自动进入进度页；多文件各回执可分别打开。
- 界面区分原件传输百分比、OCR处理百分比、业务审核状态。queued/running/retry_wait 持续刷新；succeeded/failed/cancelled 停止。识别成功只表示候选入库，不等于医生审核或正式归档。
- 阶段展示queued/pdf_parse/ocr/field_extraction/item_matching/validation/finished，未知阶段显示后台原值。
- 失败、重试、停止展示后台错误码及原因；缺少原因时明确说明后台未返回。OCR任务重试和取消由后台授权人员操作，前端不重复上传代替重试。
- 离页清理定时器；忽略已离页或旧轮询的在途响应。网络或认证错误后停止，允许手动刷新；连续最多120次后暂停，用户可手动继续。
- 本地选择的原件使用临时文件预览；后台原件仍使用本人会话鉴权下载。完整报告和任务快照不持久化到本地。

## 没有执行的操作

未读取 F:\upload 下的工作服务密钥，未调用 /ehr/worker、后台重试/取消、医生Cookie/CSRF接口，也没有让小程序直接连接MySQL。没有新建真实绑定或上传报告，没有伪造后台OCR结果。

## 验证结果与边界

1. 真实GET /ehr/miniapp/config 返回200，wechatConfigured仍为false。
2. TypeScript检查通过。25项单元/契约测试通过，其中含旧的隔离Mock夹具测试；不代表25个真实后端接口已验收。
3. 浏览器流程使用合成响应验证排队、识别中、重试、完成、失败、取消、真实比例及错误信息、单文件自动进入进度、终态/离页停止轮询；并验证登录/绑定契约。未用合成响应绕过真实账号运行流程。
4. 微信目标编译通过，产物使用ES2018兼容语法；全部JS兼容性检查通过。

## 仍待实际联调（请务必跟进）

- 真实微信登录：后端配置本小程序的 EHR_WECHAT_APP_ID 和 EHR_WECHAT_APP_SECRET 后重启，确认 wechatConfigured=true，再使用真机新code验收。当前未完成。
- 电脑B真实领取、下载、持续心跳、识别、结果回传以及A端校验入库，仍需两端联合验收。前端合成状态测试不等同于实际OCR成功。
- 之后使用已绑定用户上传一份脱敏PDF，确认同一报告id/revision/jobId从queued到真实终态，医生核对归档后进入本人正式档案。

当前配置地址仍为 http://192.168.1.105:18080；更换地址须修改 .env.local 并重新打包。正式发布使用HTTPS与微信合法域名配置。现有登录仍不能回退到演示身份。
