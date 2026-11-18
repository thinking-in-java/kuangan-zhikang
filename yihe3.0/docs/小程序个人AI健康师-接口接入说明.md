# 小程序个人 AI 健康师：接口接入说明

本期已实现 A 端接口，小程序页面由小程序开发者接入。复用现有微信登录 Token、职工绑定和 Responses 模型配置，不需要每人创建一个模型或 API Key。

开发联调地址：`http://192.168.1.105:18080`。部署时通过客户端配置替换 baseUrl。

## 1. 登录与身份

1. 沿用 `POST /ehr/miniapp/wechat/login`，获得业务 `token`。
2. 登录结果 `bindingState=BOUND` 才能进入个人健康师，否则走已有身份验证和确认流程。
3. 所有下列接口带 `Authorization: Bearer <token>`。
4. 后端从当前绑定确定本人。请求不要传员工ID、工号、单位、OpenID、模型名称或密钥。
5. 解除绑定、换绑、职工停用后，旧身份对话立即不可访问。后台管理员的对话与小程序个人对话分别隔离。

## 2. 接口总览

相对前缀：`/ehr/miniapp/agent`。

| 方法 | 地址 | 返回 |
| --- | --- | --- |
| GET | /context | 可用状态、本人展示信息、报告数量、建议问题 |
| POST | /conversations | 创建会话；请求体 `{}` 或不传 |
| GET | /conversations?page=1&pageSize=20 | 本人当前绑定下的会话列表 |
| GET | /conversations/{id}?page=1&pageSize=10 | 对话轮次，包含问题、状态、回复及来源 |
| DELETE | /conversations/{id} | 删除整条个人对话；排队/处理中返回409 |
| POST | /chat | 异步发送问题；处理中HTTP 202，已完成幂等重试HTTP 200 |
| GET | /messages/{messageId} | 查询某次问题的状态与完整结果 |

所有接口使用 `Cache-Control: private, no-store`。小程序不要跨账号复用本地聊天缓存；退出登录时清除本地 Token、会话列表及待恢复消息编号。

### 初始化

`GET /context`：

```json
{
  "code": 0,
  "data": {
    "enabled": true,
    "apiStyle": "responses",
    "bindingState": "BOUND",
    "assistantName": "我的AI健康师",
    "employee": { "name": "示例职工", "gender": "男", "position": "示例岗位" },
    "reportCounts": { "total": 2, "approved": 1 },
    "suggestions": ["帮我解释最近一次已审核体检报告", "我的报告有哪些异常指标需要关注？"],
    "personalOnly": true,
    "canModify": false,
    "responseMode": "async-polling"
  }
}
```

示例值用于说明格式，不代表实际员工数据。`gender` 沿用档案中的「男/女/未填写」文本；如选择男女医生形象，按该文本映射，未知显示默认形象。形象不参与鉴权。

### 创建会话

`POST /conversations`，请求 `{}`：

```json
{ "code": 0, "data": { "id": "服务端会话UUID", "title": "新的健康对话" } }
```

### 发送问题

`POST /chat`：

```json
{
  "conversationId": "创建会话返回的id",
  "requestId": "小程序生成的UUID",
  "message": "帮我解读这份体检报告，哪些项目需要关注？",
  "context": { "reportId": "本人报告ID", "revision": 1 }
}
```

- `requestId`：8至96位字母、数字、下划线或连字符，建议UUID。每个新问题一个新ID；网络重试必须保留同一个ID。
- `message`：1至3000字，不接受客户端历史消息、系统提示词或工具定义。
- `context`：可省略。若提供，只允许 `reportId` 与正整数 `revision`；报告必须属于本人。
- 默认只分析已审核报告。要核对未审核报告，必须从本人报告列表选中后带 `context`；回复和来源保留候选标签。已驳回报告不做候选解读。

受理成功（HTTP 202）：

```json
{
  "code": 0,
  "data": {
    "conversationId": "会话UUID",
    "messageId": "本轮消息UUID",
    "status": "queued",
    "pollAfterMs": 1500
  }
}
```

同一会话、相同 requestId 和相同问题/上下文重复提交，返回同一个 messageId；不会新建第二个模型任务。同一ID换问题返回409。模型失败后若明确重试，使用新 requestId。

### 轮询回复

`GET /messages/{messageId}`。`queued` 和 `running` 时按 `pollAfterMs` 继续查询；`completed` 或 `failed` 停止轮询。

成功（HTTP 200）：

```json
{
  "code": 0,
  "data": {
    "conversationId": "会话UUID",
    "messageId": "本轮消息UUID",
    "status": "completed",
    "answer": "根据你这份已审核报告中的原始结果……",
    "sources": [
      {
        "key": "报告ID:1",
        "reportId": "本人报告ID",
        "revision": 1,
        "reviewStatus": "confirmed",
        "published": false,
        "candidate": false,
        "examDate": "2026-11-15",
        "institution": "示例体检机构",
        "pageNumbers": [1, 2],
        "detailPath": "/ehr/miniapp/reports/本人报告ID/1"
      }
    ],
    "elapsedMs": 12345,
    "dataAsOf": "2026-11-17T12:00:00.000000Z"
  }
}
```

`confirmed` 表示报告已审核，`published` 表示是否已归档发布；不能合并成一个状态。`candidate=true` 时页面应突出提示「待审核，仅供核对」。一般功能说明或岗位问答可能没有报告来源，sources 可以为空。

处理失败仍以HTTP 200返回可查询的消息状态：

```json
{
  "code": 0,
  "data": {
    "conversationId": "会话UUID",
    "messageId": "本轮消息UUID",
    "status": "failed",
    "errorCode": "MODEL_TIMEOUT",
    "message": "大模型响应超时，请稍后重试"
  }
}
```

切后台或断网时停止定时器，保留 conversationId/messageId；回来后查询相同消息，不要自动重新 POST 问题。服务中断的旧 running 消息到处理截止时间后标记失败；不会自动重新调用模型计费。排队最长10分钟，开始执行后的截止时间为3分钟，模型循环内部预算150秒。

### 历史会话与分页

列表返回：`data:{items:[{id,title,createdAt,updatedAt}],page,pageSize,total,hasMore}`。会话按更新时间倒序。

详情返回：`data:{id,title,items,page,pageSize,total,hasMore}`。每个 item 是一轮对话：`{conversationId,messageId,question,status,answer?,sources?,errorCode?,message?}`。

详情 page=1 是最新一页轮次，页内按时间正序，向上加载旧历史时取 page=2、3并前插。`pageSize` 按「轮次」计，不是按气泡计；每轮可渲染一条用户气泡及一条助手回复/处理状态。列表 pageSize 最大50，详情最大30。

### 删除对话

`DELETE /conversations/{id}`：

```json
{ "code": 0, "data": { "id": "会话UUID", "deleted": true } }
```

页面先提示确认；删除后清理相关消息和工具轨迹，不影响本人档案和体检报告。处理中返回409，等待完成后再删。

## 3. 错误与页面行为

接口错误使用HTTP错误状态，JSON形如 `{"code":403,"errorCode":"EMPLOYEE_BINDING_REQUIRED","msg":"请先验证并确认职工身份"}`。`msg` 是可展示描述；流程判断优先看HTTP状态与 errorCode。

| 情况 | HTTP / errorCode | 页面处理 |
| --- | --- | --- |
| Token缺失、过期或账号停用 | 401 / LOGIN_REQUIRED | 重新微信登录 |
| 未绑定 | 403 / EMPLOYEE_BINDING_REQUIRED | 进入已有身份认证流程 |
| 绑定变化 | 403 / EMPLOYEE_BINDING_CHANGED | 清理当前对话页面，重新加载本人状态 |
| 对话/消息/报告不属于本人或不存在 | 404 / NOT_FOUND | 不展示他人信息，不反复重试 |
| 已有问题处理中 | 409 / MESSAGE_IN_PROGRESS | 继续查询原消息 |
| 同requestId换内容 | 409 / REQUEST_ID_CONFLICT | 新问题生成新ID |
| 参数错误/额外身份字段 | 422 / INVALID_REQUEST | 修正请求参数 |
| 请求频率/每日额度/队列繁忙 | 429 / RATE_LIMITED、DAILY_LIMIT、QUEUE_FULL | 提示并等待 |
| 模型未配置或暂不可用 | 503 / AGENT_UNAVAILABLE | 暂停发送，稍后刷新状态 |

`failed` 消息里的 `MODEL_TIMEOUT`、`MODEL_BUSY`、`AGENT_FAILED`、`TASK_INTERRUPTED` 为异步处理结果，不能按HTTP 200当成回答成功。

## 4. 报告与附件仍用原接口

- 本人报告：`GET /ehr/miniapp/reports`，或 `/reports/approved`、`/reports/pending`。
- 本人报告详情：`GET /ehr/miniapp/reports/{reportId}/{revision}`。
- 原件下载：`GET /ehr/miniapp/files/{fileId}`，下载请求同样携带 Bearer Token，不使用后台管理员文件地址。
- 上传：`POST /ehr/miniapp/reports`，file 表单及 `Idempotency-Key`。上传完成后先识别、再审核，聊天不会绕过该流程。

## 5. 服务配置与本期边界

模型沿用服务端 `health-agent.json`；不把配置、key或微信标识发送给小程序。个人端配置：

```yaml
ehr:
  miniapp-agent:
    enabled: true
    workers: 2
    per-minute: 6
    per-day: 100
    queue-limit: 100
```

分别对应 `EHR_MINIAPP_AGENT_ENABLED`、`EHR_MINIAPP_AGENT_WORKERS`、`EHR_MINIAPP_AGENT_PER_MINUTE`、`EHR_MINIAPP_AGENT_PER_DAY`、`EHR_MINIAPP_AGENT_QUEUE_LIMIT`。同一用户同时只处理一个问题；个人端和后台共同受模型客户端最多3个并发请求限制。额度可按试运行情况调整。分钟额度按自然分钟、每日额度按北京时间自然日计算；独立用量计数不会随删除对话或换绑重置，幂等重试不重复扣次。

首版6个只读工具：本人职业背景、本人报告目录、本人报告解读、本人指标变化、本人异常项目、本人已确认风险。没有自动修改身份、删除职工、开药、发送通知、创建随访或正式诊断功能。不会把对话中的自述自动写入企业正式档案。

## 6. 验证记录

- 迁移：V009，在克隆结构验证后扩展现有 `a_agent_conversations` 与 `a_agent_turns`，原有行保留，没有按职工重复建表。
- 双账号真实MySQL测试：45项检查，覆盖本人报告、他人报告拦截、候选标签、消息幂等、超时、解绑及换绑、删除后报告保留、删除不重置额度，隔离数据回滚。
- 独立 synthetic 服务通过31项真实HTTP和模型联调检查，两位合成职工分别获得自己的岗位信息；互查返回404。没有使用正式职工数据，临时账号与会话已清理。
- 后台原有智能体37项检查通过。

Postman集合位于同目录 `miniapp-personal-agent.postman_collection.json`，只需填 base_url 和现有微信登录返回的 miniapp_token。
