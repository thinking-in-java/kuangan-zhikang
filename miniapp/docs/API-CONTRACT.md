# 前后端接口契约 v1

所有接口为 HTTPS，前缀 `/api/v1`。生产请求封装在 `src/api/request.ts`，演示适配器为 `src/mock/server.ts`。JSON 响应统一为：

```json
{"code":0,"message":"ok","data":{},"requestId":"server-generated-id"}
```

错误返回非 2xx HTTP 状态及 `{code,message,requestId}`；401 清理登录状态，403 明确拒绝访问。`data` 的结构与 `src/types/index.ts` 中的类型一致。列表当前返回数组；若未来分页，需要同步扩展前端列表适配。

登录以外的接口带 `Authorization: Bearer <token>`。后端从 token 推导主体，不接受前端传入的“本人ID”作为授权依据。不得在日志中记录原始报告或问答健康内容。

## 认证与身份

| 接口 | 请求/响应 |
| --- | --- |
| POST `/auth/wechat/login` | `{code,roleIntent,consentVersion}` → `{token,profile}` |
| POST `/auth/bind-worker` | `{employeeNo,name,verificationCode}` → `Profile` |
| GET `/me/profile` | `Profile` |

`roleIntent` 只是入口意向，不授予权限。后端使用 `code` 换取微信身份，核验账号后返回 `worker`、`doctor` 或 `pending`。首次职工可返回 `bound:false`；验证码由企业发放并在后端验证，应限流和防重放。未核验医生仅能查询本人审核状态。登录 token 最长前端缓存 8 小时，后端仍应实现自身过期与吊销；过期返回 401。

## 上传、报告与文件

| 接口 | 请求/响应 |
| --- | --- |
| POST `/reports/upload` | multipart 单文件字段 `file` → `{fileId}` |
| POST `/reports` | `{type,date,institution,fileIds,idempotencyKey,followupId?}` → `Report` |
| GET `/me/reports` | `Report[]`，只返回本人 |
| GET `/reports/{id}` | `Report`，校验本人或授权医生 |
| GET `/reports/{id}/status` | `{status}` |

采用“先上传文件，再提交整份报告”的两步接口，支持多页原件。文件最大 20 MB，单次最多 9 个，后端必须再次验证数量、MIME、文件内容与归属，不可仅凭扩展名。上传后暂存的未关联文件应过期清理。

提交接口按账号及 `idempotencyKey` 去重，避免网络重试创建重复报告。`followupId` 必须属于当前职工。报告与随访状态变更应在事务内完成。原始文件不可被提取修正或医生审核覆盖。

状态：`processing` → `pending` → `approved` 或 `returned`，异常为 `failed`。前端只在详情页显示期间轮询，最多 24 次，离开页面停止，可手动刷新。

`Report.files[].url` 应为按当前请求人授权后签发的短期 HTTPS 预览地址，允许微信图片组件及文档预览。不得使用长期公开 URL。后端应保证文档链接的有效期和撤销策略，前端不将原件保存到持久存储。

## 个人健康服务

| 接口 | 数据类型 |
| --- | --- |
| GET `/me/health-archive` | `Archive` |
| GET `/me/advice` | `Advice[]` |
| GET `/me/followups` | `Followup[]` |
| GET `/me/notifications` | `Notice[]` |
| GET `/knowledge/articles` | `Article[]`，专业审核后的内容 |

指标单位、日期、趋势值、建议依据与来源均由后端提供。不要将模拟数值或未审核科普用于生产。

## AI 助手

POST `/assistant/chat`：

```json
{"sessionId":"可选现有会话ID","message":"用户问题","reportId":"可选且由用户主动选择","requestId":"客户端幂等ID"}
```

返回 `{sessionId,message:Message}`。GET `/assistant/sessions` 返回当前用户 `Session[]`。此版本使用完整 JSON 响应，不要求 SSE。

后端必须校验会话与报告所有权；未传 `reportId` 时不得默认检索完整个人档案。响应 `sources` 由后端返回可追溯知识来源说明；不得编造引用。失败重试使用相同 `requestId`，后端去重同一轮对话。AI 输出不作正式诊断或上岗资格判定。

## 医生服务

| 接口 | 数据 |
| --- | --- |
| GET `/doctor/dashboard` | `Dashboard`，统计限定授权范围 |
| GET `/doctor/reviews` | `Report[]`，全部授权报告，前端筛选状态 |
| GET `/doctor/workers` | `Profile[]`，已授权职工 |
| GET `/doctor/workers/{id}` | `Archive`，二次校验授权 |
| POST `/doctor/reviews/{id}/approve` | `ReviewDecision` → 更新的 `Report` |
| POST `/doctor/reviews/{id}/reject` | `ReviewDecision` → 更新的 `Report` |
| GET `/doctor/followups` | `Followup[]`，授权范围 |
| POST `/doctor/followups` | `{workerId,title,dueDate,content}` → `Followup` |

`ReviewDecision`：

```json
{"note":"审核意见","corrections":[{"key":"hearing","value":"核对后的提取值"}],"confirmedFields":["hearing"]}
```

只允许对后端白名单提取字段纠正，不允许通过该接口修改体检机构正式结论或文件。后端应验证所有需确认字段、字段值、报告状态与访问授权，并以事务和并发控制提交审核。记录操作者、时间、原提取值、修正值、确认字段、审核状态与备注的审计记录。

本前端 Mock 会保留 `sourceValue` 和备注用于演示，但不替代生产审计系统。授权职工管理范围不能由前端查询参数拓宽。后端应校验随访计划日期并限制重复提交。
