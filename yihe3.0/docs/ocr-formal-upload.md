# 正式上传与OCR接入

正式后台地址：`http://192.168.1.105:18080/login`。

当前医生账号对应的任务上下文为：

```json
{"scope_code":"org:10000","data_mode":"real"}
```

scope_code由当前登录账号单位归属生成；其他单位应使用其实际scope，不能统一硬编码为org:10000。data_mode由A端正式运行配置生成。前端只传文件与上传幂等键，不能自行指定或覆盖任务上下文。

B端连接正式baseUrl `http://192.168.1.105:18080`，带现有worker密钥调用 `/ehr/worker/tasks/claim`。随后读取返回的inputPath，原样沿用scope_code、data_mode、报告ID和版本。不要用测试地址18081，不要根据文件名或SHA覆盖上下文。

后台上传：`POST /ehr/api/records/{recordId}/reports`（后台登录Cookie、X-CSRF-Token、Idempotency-Key，multipart的file字段）。

小程序上传：`POST /ehr/miniapp/reports`（微信登录后已确认绑定的Bearer令牌、Idempotency-Key，multipart的file字段）。正式微信参数仍需配置；不能使用模拟微信身份替代正式登录。

## 当前回传核对

任务 `9afedd74-559d-4db3-b6e0-52483ec520e5` 第二次尝试已成功，A端API状态succeeded、100%、无当前错误码；首次INPUT_CONTEXT_MISSING保留在尝试历史中。A端收据的scope_code和data_mode与上述正式上下文一致。

实际接收2个标准字段、1个检查项目、4条证据、1条结论及0条建议。此数量只证明本次回传与入库情况，不能据此认定整份体检报告已完整提取。报告状态needs_review，尚未归档发布，需医生对照原件核验。

重新打开进度或刷新数据查看当前状态。需核对候选结果时使用“核对报告结果”；不要因历史错误再次重跑已成功任务。

核验数据见 `database/formal-ocr-status-verification.json`。详细调用规范见 [ocr-worker-api.md](ocr-worker-api.md)。
