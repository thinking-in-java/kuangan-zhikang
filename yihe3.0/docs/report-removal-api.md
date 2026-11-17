# 后台移除误传体检报告

档案详情页的每份可移除报告右上角显示「×」。悬停提示「移除这份报告」，点击后展示文件名及版本，要求填写原因并确认。
已审核或归档报告、存在已审核历史版本的报告不显示入口，后端也拒绝移除。

```http
DELETE /ehr/api/reports/{reportId}/{revision}
Cookie: 后台登录会话
X-CSRF-Token: context返回的csrfToken
If-Match: 最新报告详情返回的etag
Content-Type: application/json
```

```json
{"requestKey":"本次操作UUID，网络重试沿用","reason":"选错职工，误传了他人的报告"}
```

成功：`{"code":0,"data":{"removed":true,"reportId":"报告UUID","recordId":"原职工档案UUID"}}`。

业务行为：

- 校验组织范围、当前版本、候选摘要和全部历史版本审核记录。
- 同事务停止该逻辑报告的活跃 OCR 任务，解除未审核报告与职工的关联，并清空逻辑报告当前指针。
- 保留原件、OCR 回传、证据、质量检查和操作审计，不删除职工基本资料，不影响其他报告。
- 更新原职工档案完整度；移除后报告不出现在本人或后台的当前报告列表。
- 同一个操作 UUID 的网络重试幂等。不同报告复用 UUID 返回 409。
- 同一原件重新上传时，在保留的逻辑报告中创建新版本和新 OCR 任务，旧候选结果不复制。
- 原件字节和数据库不可变保护继续保留；这属于业务移除，不是物理擦除。

状态码：未登录 401；个人端或跨站操作 403；范围外或已移除版本 404；版本/摘要变化、审核历史保护 409；缺少原因或 UUID 不合法 422；缺少 If-Match 400。

验证：`tests/ehr/test-report-removal.py` 16 项检查；`test-report-removal-ui.py` 验证真实浏览器按钮位置、确认弹窗和移除后刷新。测试仅使用 synthetic 数据。
