# 2026-11-15：OCR输入缺少上下文修复与B端重跑

## 原因与修复

旧任务 `9afedd74-559d-4db3-b6e0-52483ec520e5` 原件已保存、B端已成功领取，首次失败码为 `INPUT_CONTEXT_MISSING`。数据库里已有 `scope_code=org:10000`、`data_mode=real`，但A的input接口未输出这两个字段，是A端适配遗漏，尚未进入OCR处理阶段。

A端现已从持久化任务补齐scope_code和data_mode，同时输出job_id、contract_version、report_id、record_id、report_kind、dictionary_version；保留原camelCase字段。Worker结果回传必须原样携带scope_code和data_mode，A端拒绝缺失或不匹配的数据，不允许按文件SHA自行切换环境。report_kind和job_id如回传也检查一致性。

原件的 `.source` 后缀是私有存储命名方式；原始字节、原名、类型、页码及哈希分别保存，文件没有因此损坏。B端应通过downloadPath下载，并按mimeType/真实文件头识别。

## 此次报告的隔离重跑

用户已确认此文件是合成测试报告。原失败记录及正式职工档案保持原状，不在18080重跑该合成样本；通过正常上传API将同一原始文件放入synthetic实例。

给B端开发者：

```text
测试baseUrl: http://192.168.1.105:18081
Authorization: 沿用已配置的worker密钥，不在文档中传播密钥
先 POST /ehr/worker/tasks/claim，以新的requestKey领取
再按返回inputPath读取，不复用旧jobId或旧leaseToken

本次测试任务: 937242d0-1162-49b7-8318-c985c605d9fa
报告ID: d5f8b247-e1bd-471f-ae74-9a5f4326d808
revision: 1
scope_code: org:10000
data_mode: synthetic
report_kind: individual

scope_code、data_mode以实际input为准，原样回传
不要通过SHA或本地默认值覆盖A端任务上下文
领取后input/下载/心跳/结果/失败均带X-OCR-Lease
```

同一文件SHA256：`673701f0ad96d735c56545e85b9f7216219e5d9549230190ba2a6eb026937d32`，字节数6971177。哈希只用于文件一致性校验，不决定数据模式。

测试后台：[http://192.168.1.105:18081/login](http://192.168.1.105:18081/login)。使用现有后台账号，档案列表中的 `合成报告联调职工`（工号OCR_SAMPLE_20261115）专供接口联调。其身份可能与报告姓名不同；如果质量检查提示姓名不一致，应保留提示并人工核验，不能绕过匹配校验。

正式业务继续使用18080。测试原件位于独立synthetic目录，不会写入正式健康档案。测试服务当前已启动，但不代表已设置Windows开机启动。

## 验证边界

- 修复后实际HTTP测试：OCR任务/契约45项、旧上传/回调19项、档案业务26项通过。
- 正式服务只读就绪检查11项通过，局域网测试登录页HTTP200；管理端和小程序API两个WAR构建成功。
- 未实现或代替B端OCR引擎；新测试任务仅已入队，实际识别与回传仍需B端连接测试地址完成。
- 识别结果仍是待审核候选，不自动批准、归档或发布。

完整调用规范见 [ocr-worker-api.md](ocr-worker-api.md)，Postman集合见 [ocr-worker.postman_collection.json](ocr-worker.postman_collection.json)。
