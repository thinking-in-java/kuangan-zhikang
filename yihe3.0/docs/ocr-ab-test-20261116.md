# A/B OCR 联调交接（2026-11-16）

## 当前状态及约定

2026-11-16 已确认本机地址为 **192.168.1.105**，MySQL `192.168.1.109` 连接恢复正常。
正式服务 `18080` 和独立测试服务 `18082` 均已启动；两个登录页及 Worker 环境、字典接口均返回 HTTP 200。正式环境的 11 项只读就绪检查通过。
测试环境已确认 `data_mode=synthetic`、`queuePartition=synthetic`、`candidateOnly=true`，字典包含 67 个标准字段及 655 个检查项目。

**本次新联调尚未上传文件、尚未创建 job_id/report_id。必须等 B 端明确回复「B 端已开始监听」后，A 端才上传 1 份合成测试 PDF 并入队。**

## 环境配置

| 项目 | 本次约定 |
|---|---|
| 测试地址 | `http://192.168.1.105:18082`，以 A 最终确认的 IP 为准 |
| 正式服务 | 18080；本次合成联调禁止连接该端口 |
| 队列分区 | `synthetic` |
| scope_code | `org:10000`（配合 synthetic 模式构成本次测试范围） |
| data_mode | `synthetic` |
| 个人报告 report_kind | `individual`，回传沿用 input 值 |
| contract_version / dictionary_version | `2.0.0` |
| revision | 读取本次 input，不能硬编码 1 |
| 原件存储 | A 本机 `F:\upload-test`，B 通过鉴权 HTTP 下载 |
| 专用 Worker Token | A 私有文件 `F:\upload\.config\ocr-worker.synthetic.token` |
| B 私有环境文件 | A 私有文件 `F:\upload\.config\ocr-ab-test-worker.env`，负责人私下传给 B |
| 审核及发布开关 | `ehr.candidate-only=true`，允许候选接收/补录/提交审核，禁止审核通过和正式归档 |

隔离方式为**独立服务端口、独立 Worker Token、独立原件目录，以及同一 MySQL 中按 data_mode 分隔的逻辑队列**，不是另建一套物理数据库。任务领取、续期、下载、回传均校验运行模式，正式队列不会被测试服务领取；测试 Token 不应配置到正式服务。

本次保持现有 B 主动拉取任务协议。不要改为浏览器直接调用 B，也不要把测试 PDF 上传到正式 18080。

启动 A 测试服务：

```powershell
cd F:\workstation\background\yihe3.0
.\scripts\start-ocr-ab-test.ps1
```

启动脚本不会上传或领取任务。服务日志为 `F:\workstation\.tools\runtime\ocr-ab-test.log`，PID 文件为同目录 `ocr-ab-test.pid`。项目后台测试页面在该服务 `/login` 登录后进入档案信息管理。

## B 请求头

所有请求：`Authorization: Bearer <专用测试Worker Token>`。
领取任务后，除环境和字典外再带：`X-OCR-Lease: <本次返回的leaseToken>`。

Token、leaseToken 不放聊天、公开文档或导出的 Postman 集合中；B 不需要数据库账号。

## 接口顺序

| 步骤 | 方法及路径 | 关键检查 |
|---|---|---|
| 环境自检 | `GET /ehr/worker/environment` | data_mode=synthetic、candidateOnly=true |
| 字典 | `GET /ehr/worker/dictionary` | 2.0.0、67 标准字段、655 检查项目 |
| 领取 | `POST /ehr/worker/tasks/claim` | workerId、每个领取循环的新 requestKey UUID |
| 输入 | `GET /ehr/worker/tasks/{jobId}/input` | scope_code、data_mode、report_kind、revision 均使用 A 返回值 |
| 下载 | `GET /ehr/worker/tasks/{jobId}/files/{fileId}` | 与 input.source_files 的 SHA-256、页数对应 |
| 心跳 | `POST /ehr/worker/tasks/{jobId}/heartbeat` | 每 40 秒以内，默认租约 120 秒；阶段和百分比不倒退 |
| 候选回传 | `POST /ehr/worker/tasks/{jobId}/result` | 完整 EHR 2.0.0 对象 |
| 接收回执 | `GET /ehr/worker/tasks/{jobId}/receipt` | accepted=true、state=succeeded、reviewStatus=needs_review、published=false |
| 识别失败 | `POST /ehr/worker/tasks/{jobId}/failure` | errorCode、message、retryable |

环境自检预期：

```json
{"code":0,"data":{"data_mode":"synthetic","queuePartition":"synthetic","candidateOnly":true,"contract_version":"2.0.0","dictionary_version":"2.0.0","leaseSeconds":600,"heartbeatSeconds":200}}
```

领取请求：

```json
{"workerId":"ocr-pc-b-test-01","requestKey":"每次领取循环的新UUID，网络重试沿用"}
```

无任务 `data.task=null` 正常，间隔 `retryAfterSeconds` 后重试。B 保持轮询期间，A 等待负责人发出监听就绪消息。

心跳：

```json
{"stage":"pdf_parse","percent":10}
```

阶段：`queued → pdf_parse → ocr → field_extraction → item_matching → validation`；B 可上报 0–95，A 提交候选事务成功后才设 100。

## 输入及回传约束

input 同时包含冻结字段：`job_id`、`report_id`、`revision`、`prior_revision`、`record_id`、`scope_code`、`data_mode`、`report_kind`、`contract_version`、`dictionary_version`、`source_files`。B 不从文件名、历史样本 SHA 或本地默认值推导这些上下文。

`source_files` 包含 `source_file_id`、`sha256`、`page_count`；`files` 提供下载路径和 MIME。存储扩展名 `.source` 不代表文件格式，B 应按实际字节和 MIME 解析 PDF/图片。

回传时：

- 本次个人合成报告使用 `report_kind=individual`，沿用本次 input。
- 67 是字段字典规模，不要求编造 67 个字段值；655 是检查项目字典规模，不要求每份报告有 655 个项目。
- 姓名、机构、检查日期、结论和建议按原文提取并关联证据；未找到返回 null/空数组，不从名册补造。
- `exam_date_raw` 是原文候选日期，`exam_date_verified` 不由 OCR 核定；机构写 `institution_raw`。
- 保留原值、单位、参考范围及原始页码/证据 UUID。不擅自补临床结论或未经批准的归一化数值。
- 完整示例及离线结果绑定见 [B 端 JSON 对接](b-ocr-result-integration.md) 与 [Worker 主文档](ocr-worker-api.md)。

## 回执与幂等

`result` 成功返回 HTTP 200、code=0 和保存后的候选报告。若响应超时，B 保留同一 JSON、同一 jobId 和本次 leaseToken，查询 receipt 或重发相同结果。

receipt 返回 accepted、state、reviewStatus、published、received_at、payloadSha256 及字段/项目/证据/结论/建议数量，不重复传输原始医疗正文。

- 同一任务、相同 JSON 内容：幂等成功，不重复插入。对象键顺序由 A 排序后计算摘要；数组顺序和值变化视为不同内容。
- 同一任务、不同 JSON：409，已接收结果保持不变；需要重新识别时由后台创建新任务。
- 新领取循环新 requestKey；同一次领取网络重试沿用旧 requestKey。
- 错误 Token 或租约：401；运行模式或任务不存在：404；租约过期/取消/被替换：409，B 停止该尝试。
- 422/413：结构、上下文、证据或大小不满足契约；A 回滚整次候选保存，保留具体校验 msg，并记录 INVALID_RESULT。

## 这次联调交接步骤

1. A 放行数据库连接，启动测试服务，确认环境自检。
2. A 提供最终地址、专用测试 Token 的私有交接文件与测试 Postman 环境。
3. B 配置独立测试进程并回复「B 端已开始监听」。
4. A 上传 **1 份合成 PDF**，发出 `job_id`、`report_id`、scope_code、data_mode、report_kind、revision。
5. B 核对领取、下载、SHA、OCR、JSON 校验及回传。
6. A 核对回执、原件和证据可查看、字段入库及待审核候选状态；必要字段齐全后提交医生待审核，仍不执行审核通过或发布。
7. 失败时双方对照 `job_id`、attempt、errorCode、errorMessage 和 HTTP msg 中的失败字段；不交换密钥或整份未脱敏病历日志。

测试 Postman 集合：`ocr-ab-test.postman_collection.json`；公共环境模板：`ocr-ab-test.postman_environment.json`。公共文件不含真实 Token。
