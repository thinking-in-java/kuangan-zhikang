# 数字健康师：Responses API 与业务工具

已接入现有 Java 后端、完整工作台及悬浮对话。前端只访问同源 `/ehr/api/agent`，模型服务地址和密钥只由服务器读取。报告 OCR 流程独立运行。

## 能力及边界

| 工具 | 用途 |
| --- | --- |
| search_employees | 在当前账号单位范围按姓名、工号、班组分页查职工 |
| get_employee_record | 基本资料、职业信息、已确认风险、缺失材料 |
| list_exam_reports | 指定职工历次报告版本、机构、原始/核定日期、审核状态 |
| get_exam_report | 报告原文、结论、建议、检查结果及证据页码，每页80项 |
| get_health_statistics | 企业整体档案及已确认风险统计 |
| find_abnormal_results | 原始异常标记汇总及明细；当前报告版本，支持班组、项目、已审核过滤 |
| prepare_employee_delete | 生成单个职工删除预览，关联报告联动删除 |
| prepare_employee_update | 生成基础资料变更预览，不改写报告原文 |

后两项仅后台医生账号可用。模型没有执行删除、直接 SQL、浏览任意地址或执行命令的工具。用户明确要求操作、明确目标后，才能生成预览；页面显示目标姓名、工号及报告影响范围，用户手动输入工号再确认。操作票据限本人、当前会话和单位，10分钟有效，核对行版本与报告内容摘要；变更后需重新预览。重复确认只返回原执行回执。取消不会改数据。原系统职工资料保留，删除的是新健康档案及其报告。

分析只引用查询到的数据。未审核报告明确标记为候选；未评估不能算低风险。异常汇总基于已有原始异常标记，不自动推断疾病、不自动写入正式风险评估或发出医学预警。基础病关注建议需医生结合原报告核验。尚未接入随访任务创建、通知发送及自动归档工具。

## 配置

`ehr.agent.config-file` / `EHR_AGENT_CONFIG_FILE` 默认指向 `F:/upload/.config/health-agent.json`。未设置的字段沿用现有 `model-recognition.json`，不会修改 OCR 的配置。可通过 `EHR_AGENT_API_KEY` 覆盖密钥。

```json
{
  "enabled": true,
  "baseUrl": "https://api.openai.com/v1",
  "model": "填写账户实际可用且支持工具调用的模型名",
  "apiKey": "仅写入服务端私有配置",
  "reasoningEffort": "none"
}
```

兼容中转站 `/v1/responses`。`serviceTier` 可选，仅在该提供方支持时填写；请求档位不保证实际速度。`store:false`，续接时传回完整 `output`（含必要 reasoning 项）及 `function_call_output`，使用 `call_id` 对应，不依赖提供方保存会话。

## 网页接口

全部沿用 Shiro 登录态。修改请求须带 `/ehr/api/context` 返回的 `X-CSRF-Token`。单位、用户、real/synthetic 模式均来自服务端，客户端不能指定。

| 方法 | 路径（相对 /ehr/api） | 说明 |
| --- | --- | --- |
| GET | /agent/context | 配置状态、修改能力、当前单位统计；不返回密钥 |
| GET | /agent/conversations | 当前账号最近60个会话 |
| POST | /agent/conversations | 创建会话，返回id |
| GET | /agent/conversations/{id} | 会话历史及未过期待确认操作 |
| DELETE | /agent/conversations/{id} | 删除本人的整条对话、轮次、工具轨迹及操作票据；需 CSRF；处理中返回409 |
| POST | /agent/chat | 发送消息并执行白名单查询工具 |
| POST | /agent/actions/{id}/resolve | 用户确认或取消已生成的操作 |

对话请求：

```json
{
  "conversationId": "创建会话返回的id",
  "requestId": "前端生成的8至96位唯一请求编号",
  "message": "查找张三，并分析他的体检报告",
  "recordId": "可选：已选择职工档案id；不选传空字符串"
}
```

返回 `code:0,data:{conversationId,turnId,text,tools,sources,actions,elapsedMs,dataMode}`。`sources` 可打开档案详情和原件；`tools` 是实际执行轨迹；`actions` 是服务端操作预览，不能仅依据模型文字执行。

确认：`{"confirm":true,"employeeCode":"页面显示的目标工号"}`。取消：`{"confirm":false}`。缺少确认、错误工号、非本人操作、过期或资料变化均不能执行。

删除对话返回 `code:0,data:{id,deleted:true}`。删除前页面弹出确认框；删除后不会恢复，已执行的业务操作不会撤销，职工、报告和业务审计保持原样。不存在或非本人、不同单位/数据模式的会话返回404。后端在短事务中锁定会话并按外键顺序删除关联记录，与聊天发起共用会话锁，防止删除与新消息交错。

同一会话的同一 `requestId` 重试返回已保存结果；换内容返回409；处理中返回409。失败轮次保留错误，但后续消息不重用不完整工具上下文。每次最多7轮模型请求、12次工具调用，整体预算150秒，同时最多3次模型对话，超限返回429。不会因断线自动重试写操作。

## 表结构（V008，均为 a_）

| 表 | 关键字段及含义 |
| --- | --- |
| a_agent_conversations | conversation_id会话；scope_code单位；data_mode模式；owner_user本人账号；title标题；lease_turn_id/lease_until正在处理的轮次租约；created_at/updated_at UTC时间 |
| a_agent_turns | turn_id轮次；conversation_id会话；request_key幂等键；request_hash消息与对象摘要；user_message提问；selected_record_id选中对象；turn_status状态；response_json页面回执；transcript_json服务端Responses上下文；created_at/completed_at |
| a_agent_actions | action_id操作票据；conversation_id/turn_id来源；record_id目标；action_type删除或修改；action_status待确认/已执行/取消；payload_json变更；snapshot_json姓名工号行版本与报告指纹；result_json幂等回执；expires_at/created_at/resolved_at |
| a_agent_tool_logs | tool_log_id调用；turn_id轮次；tool_name白名单工具；arguments_json参数；outcome成功或错误；duration_ms耗时；created_at |

所有表、字段均有数据库中文注释。V008只新增表；应用前用已有迁移校验器验证并登记，不修改旧表及历史业务数据。

## 验证

- `python tests/ehr/test-health-agent.py`：真实MySQL上隔离scope事务验证，全部回滚；覆盖工具循环、权限/单位隔离、幂等、预览、错误工号、过期、取消、资料变化及联动删除。
- `tests/ehr/test-health-agent-live.py`：真实模型与HTTP联调，仅 synthetic 服务，创建独立测试职工及报告，验证查询、报告分析、操作确认及清理。
- `tests/ehr/test-health-agent-ui.cjs`：聊天输入、Markdown安全渲染、资料去重、工作台与悬浮页面的后端请求和人工确认验证。
- `tests/ehr/test-health-agent-layout.py`：独立无头Chrome，检查桌面/小屏四种布局，单一消息滚动、固定发送栏、长回复及多行输入，并保存截图。

实现参考：[OpenAI Function calling](https://developers.openai.com/api/docs/guides/function-calling)、[Responses API](https://developers.openai.com/api/reference/resources/responses/methods/create)。
