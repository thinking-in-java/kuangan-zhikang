# 沟通与服务中心：职工会话

首期仅实现「职工会话」。后台入口 `/manage/communication/conversations`，位于数字员工工作中心之后。原聊天管理、数字健康师个人 AI 会话保留，历史私密 AI 对话不会自动进入人工服务收件箱。

## 部署

1. 使用项目当前数据库配置，运行 `python database/communication_migration.py --apply`。V016 新建五张 `a_service_*` 表并记录校验和，重复执行不重复创建。
2. 使用 JDK 8 构建 `mvn -pl yihe-admin -am verify`，停止旧进程后启动新的 WAR。
3. 聊天附件位于 `${ehr.storage-root}/communication/{UUID}.source`，当前部署根目录为 `F:/upload`；通过鉴权接口访问，不映射静态目录。
4. AI 草稿沿用数字健康师的模型配置。未配置或上游失败时，人工沟通仍可使用，不返回模拟 AI 回复。

## 会话与权限

- 同一单位、数据模式、职工仅有一个持续会话，结束服务后历史仍保留，再次咨询会重新开启。
- 后台使用现有登录会话与单位权限。修改请求需要 `/ehr/api/context` 返回的 `csrfToken`，放入 `X-CSRF-Token`。跨单位/跨模式/已删除职工不可访问。
- 后台必须先接管才能发送、上传附件或生成草稿；同事已接管时不能抢占，当前处理人先释放。医生/健康管理员身份由服务器确定。
- 未读游标按账号独立、单调递增，待回复按最后职工消息与最后人工回复顺序判断。系统服务记录不算回复。
- 每次发送提交唯一 `requestId`（8–96 位字母、数字、`_`、`-`）；同账号、同会话、同请求与同内容重复提交只返回原消息，内容变化则 409。
- 消息只支持文本和附件，不解释 HTML。文本最多 4000 字，每条最多五个附件，附件限 PDF/JPG/PNG/UTF-8 TXT，单个 20 MB，校验实际格式与图片尺寸。
- 未发送附件仅上传者可见，可移除；发送后的附件由会话参与者按权限下载，不能移除。浏览器系统通知需权限及安全上下文，页面未读提示不依赖系统通知。

## 后台接口

统一响应：成功 `{ "code": 0, "data": ... }`；失败对应 HTTP 状态并返回 `{ "code": 409, "msg": "说明" }`。

前缀 `/ehr/api/communication`：

| 方法 | 路径 | 参数 / 说明 |
| --- | --- | --- |
| GET | `/context` | 当前账号、角色、进行中/待回复/未读数、AI 配置状态 |
| GET | `/conversations` | `page`、`limit`（默认30，最多50）、`search`、`filter=open/all/pending/unread/mine/closed`；搜索姓名、工号、消息关键词 |
| POST | `/conversations` | `{ "recordId": "档案UUID" }`，已有则返回原会话 |
| GET | `/conversations/{cid}/messages` | 最新50条；`before=seq` 向前翻页；`after=seq` 增量拉取，两者不能同时传 |
| POST | `/conversations/{cid}/messages` | `{ "requestId":"UUID", "text":"回复", "attachmentIds":[], "draftId":"可选草稿UUID" }` |
| POST | `/conversations/{cid}/read` | `{ "seq": 12 }`，已查看到的消息序号 |
| POST | `/conversations/{cid}/actions` | `{ "action":"takeover/release/close/reopen", "version": 3 }`，版本取会话返回值 |
| GET | `/conversations/{cid}/health` | 同职工真实档案、已确认风险、报告原值、历史评估、随访、服务记录；缺失信息明确为空 |
| GET | `/conversations/{cid}/summary` | 真实消息数量、最早/最近时间，当前不生成 AI 会话总结 |
| POST | `/conversations/{cid}/drafts` | `{ "purpose":"general/report/exercise/diet/review/care" }`；返回 `id/text/safetyNote/basedOnSeq`，不会自动发送 |
| POST | `/conversations/{cid}/attachments` | multipart 字段 `file`，返回 `id/name/mime/size` |
| GET | `/conversations/{cid}/attachments/{fid}` | 鉴权下载；图片可预览，其他类型附件下载 |
| DELETE | `/conversations/{cid}/attachments/{fid}` | 移除自己尚未发送的附件 |

`messages` 返回 `{conversation,items,hasEarlier,lastReturnedSeq,hasMore}`。`items` 按序号升序，消息包含 `id/seq/role/sender/senderName/text/createdAt/attachments`。时间为 UTC；客户端按本地时区展示。角色：`employee`、`doctor`、`manager`、`ai_assisted`、`system`。

接管等操作使用 `version` 防止旧页面覆盖新状态；AI 草稿生成期间出现新消息，或旧草稿发送时会话已变化，返回 409，需要重新生成或人工核对后移除草稿标记。AI 仅接收最近12条已作基础身份隐藏的文本，不发送原始附件或完整档案，不提供业务写工具；这不等于所有自由文本都已完全脱敏。

## 提供给其他小程序开发者

前缀 `/ehr/miniapp/communication`；鉴权沿用现有小程序 `Authorization: Bearer <token>`，服务端校验令牌所属 AppID，须已经确认职工身份绑定。每次重新检查绑定与职工状态，不能由客户端指定他人职工编号。

1. `POST /conversation`，空 JSON `{}`，返回自己会话的 `id/status/unread/lastSeq` 等。
2. `POST /conversations/{cid}/messages`，发送结构同后台；服务器固定为 `employee`，无需接管。
3. `GET /conversations/{cid}/messages?after=<最后序号>`，建议前台每3–5秒轮询；首次不传游标，历史用 `before`。有 `hasMore=true` 则继续拉取，不跳过消息。
4. `POST /conversations/{cid}/read`，用户真正查看后提交最后可见序号。
5. 图片/文件发送：`wx.uploadFile` 调用 `POST /conversations/{cid}/attachments`（字段 `file`，携带鉴权头）；将返回 `id` 放入 `attachmentIds` 再发送消息。
6. 下载用 `wx.downloadFile` 调用 GET 附件接口并携带鉴权头；删除未发送附件用 DELETE 同路径。

未绑定返回对应认证错误；不提供其他职工会话列表、健康侧栏、接管、草稿生成等后台能力。需要小程序开发者把这些接口接入其人工咨询页面；现有个人 AI 问答接口不自动变成人工咨询。

## 表结构职责

| 表 | 内容 |
| --- | --- |
| `a_service_conversations` | 职工关联、单位/模式、接管人、服务状态、消息游标、并发版本 |
| `a_service_messages` | 顺序消息、角色、发送人、正文、幂等请求及内容摘要 |
| `a_service_reads` | 每位参与者的已读游标 |
| `a_service_attachments` | 私有原件元数据、哈希、上传者与消息关联 |
| `a_service_reply_drafts` | AI 待确认回复、生成所依据消息游标、发送状态 |

## 验证

- `python tests/ehr/test-communication.py`：真实 MySQL、随机隔离 synthetic 单位、外部模型使用确定性替身，全部测试数据与文件回滚。
- `python tests/ehr/test-communication-layout.py`：独立本机 HTTP 桩，真实无头 Chrome 验证三栏、发送、AI 草稿、窄屏及错误状态，不调用业务数据库或外部模型。

未实现历史数据迁移、微信推送订阅消息、自动回复规则、服务知识库和全模块统计；这些按后续页面分别接入。

## 页面二：聊天记录（2026-11-25）

入口 `/manage/communication/history`，菜单位于职工会话之后。执行 `python database/communication_history_migration.py --apply` 安装 V017，新增 `a_service_conversation_details` 保存分类和职工真实评分；不修改既有消息、旧聊天表和正式健康档案。

后台新增以下接口，沿用后台登录、单位权限与 CSRF：

| 方法 | 路径（前缀 `/ehr/api/communication`） | 说明 |
| --- | --- | --- |
| GET | `/history` | `search/start/end/category/source/page/limit`，列表与分页 |
| GET | `/history/statistics` | 同一筛选口径的六项统计及来源会话数 |
| GET | `/history/{cid}/detail` | 参与人、时间跨度、处理状态、分类、评分、主题关键词 |
| GET | `/history/{cid}/plans` | 只读查看已关联旧职工的原系统健康计划，不跨数据模式取数 |
| POST | `/history/{cid}/category` | `{ "category":"health/report/review/plan/other" }`，分类调整保留系统操作记录 |
| POST | `/history/{cid}/ai-summary` | 按需生成摘要，最多最近100条、24000字符；保留原始消息，不自动回复、不触发业务操作 |
| GET | `/history/export` | 同列表筛选，另可传 `conversationId`；UTF-8 BOM CSV，最多10000条，超限返回413 |

列表按姓名、工号、消息关键词检索；开始/结束日期均为北京时间自然日，结束日期包含当天。类型 `health=健康咨询、report=报告解读、review=复查咨询、plan=方案沟通、other=其他服务`。回复来源 `all/ai/human`；AI指人工核对后发送的 `ai_assisted`，人工为 `doctor/manager`。同时存在两类回复的会话会出现在两个来源筛选中，两个会话数不能相加作为总会话数。

统计对象为按职工保留的连续会话线程，不伪造单次会话编号或将每条消息计为会话。日期范围筛选命中该范围内有消息的线程并统计范围内消息；点击线程后可逐页回顾完整历史，页面明确提示完整历史不受左侧日期截断。会话详情展示的时间跨度是最早到最近消息，不代表连续在线服务时长。平均响应时间按职工消息后第一条紧接的非系统回复计算（连续职工消息取最后一条），无样本显示“—”。人工接管按系统真实接管记录计算。没有评价时不显示虚构的满意率。

小程序新增 `POST /ehr/miniapp/communication/conversations/{cid}/rating`，Bearer鉴权、本人绑定，JSON `{ "score": 1到5整数 }`；仅结束的会话可评，每个会话线程只能提交一次，不允许后台代填。满意度为4–5星评价占比，并展示评价样本数。

关键词在本机按消息主题词提取，不冒充AI生成。AI摘要不发送附件正文，作基础身份隐藏后调用现有模型配置；无配置或上游失败返回明确错误，仍可查看、筛选和导出原始记录。CSV字段进行公式注入防护，不包含可绕过鉴权的附件地址。

验证：`test-communication.py` 包含业务与隔离数据库测试（全部回滚）；`test-communication-history-layout.py` 使用独立本机桩与无头Chrome验证布局和交互，不调用真实模型。
