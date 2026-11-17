# 中转站模型识别接入

当前本地环境默认识别提供方已切换为 `model`。旧 B 端 Worker 不再领取新任务；原件、旧任务、历史结果和人工审核流程保留。没有新建数据库表。

## 流程

后台/小程序原上传接口 → A 保存原件 → 创建 `model-vision-v1` 持久任务 → 本机模型适配进程领取 → 下载并核验 SHA256 → PDF 按原页渲染/图片解码 → 中转站模型返回逐页 JSON → A 端适配为 EHR 2.0.0 → 原事务校验/回执 → 待人工核对。

职工、报告、版本、数据范围和原件 ID 均来自 A，不交由模型分配。模型只提取可见报告原文，不直接访问数据库或调用业务写接口。

## 配置

私有配置文件：`F:/upload/.config/model-recognition.json`。其中 apiKey 只存于该私有文件，不写前端、源码、命令行或业务日志。

```json
{
  "baseUrl": "https://xyyapi.com/v1",
  "apiKey": "在本机私有文件配置",
  "model": "gpt-6.1-sol",
  "apiStyle": "responses",
  "enabled": true,
  "maxPages": 40,
  "maxResponseTokens": 10000,
  "readTimeoutSeconds": 240,
  "parallelRequests": 12,
  "compactOutput": true,
  "prioritizeDensePages": true,
  "reasoningEffort": "none",
  "serviceTier": "ultrafast",
  "preferPdfText": true,
  "nativeTextMinChars": 180,
  "renderDpi": 130,
  "imageMaxLongEdge": 1800
}
```

这里的模型名称是该中转站 `/models` 实际返回并经请求验证的模型标识，不代表对中转站底层模型来源的独立验证。`gpt-5.4-mini` 当前实测上游权限错误，因此未启用。

`enabled=false` 会暂停本机模型适配进程；若需恢复旧 B 接入，修改 `EHR_RECOGNITION_PROVIDER=worker` 后重启服务。旧失败任务只有在用户明确点击重试时才改为模型任务，旧成功结果不会自动重跑。

运行依赖：现有 Java 后端及 `scripts/model-recognition-requirements.txt` 中的 Python 依赖。开发机已验证可用。可用 `EHR_MODEL_PYTHON` 指定 Python 可执行文件，`EHR_MODEL_CONFIG_FILE` 指定私有配置文件。Java 监督适配进程；父进程退出后适配进程自动退出。

正式实例 `18080`、隔离测试实例 `18082` 各有本机适配进程，均只能领取自身 data_mode 的模型任务。隔离实例仍启用 candidate-only，不能审核通过或归档。

## 接口与前端

- 原上传接口、报告详情、补录、审核与归档接口不变，小程序上传自动使用同一新流程。
- `GET /ehr/api/recognition`：需后台登录，只返回 provider、enabled、model、maxPages、automaticRedaction、reviewRequired 和 legacyWorkerPaused，不返回密钥或本地配置路径。
- `POST /ehr/model-worker/tasks/claim`：仅本机适配服务专用密钥可领取；旧 B Worker Token 无权调用。
- 旧 `POST /ehr/worker/tasks/claim` 在模型模式返回 task=null、paused=true，不派发旧 B 新任务。
- 心跳、下载、回传和回执继续使用既有带租约的 Worker 接口，默认租约600秒，适配进程每30秒续约。
- 网页上传弹窗说明使用模型识别，并显示原件页面会发送至模型服务；识别进度、结果核对和历史记录沿用现有页面。

## 数据规则

1. 元数据 institution_raw、exam_date_raw 等保留原文；不同页出现冲突时保留各条证据，顶层值置空待核对。
2. exam_date_verified 始终 null，人工核对保存后再核定。不能用打印日期、报告签发日期或分项检查日期冒充体检日期。
3. full_name 等标准字段键必须属于当前67项字典。临床数值、单位、参考范围和标记只保留原文，不换算。
4. 检查项目只有名称在655项字典中精确且唯一匹配时才关联编码，其他保留unmapped，不做医学语义升级。
5. 所有归一化值、转换规则、标准医学分类均为空，避免之前的 value_normalized 校验失败。
6. 每条结果关联原文件、真实页码和模型提取片段。当前不声称有精确位置框，coordinate_system=object_only、bbox=null，禁止伪造坐标或置信度。
7. 完整结论、原报告建议分开保存；机构汇总报告不写入个人候选。类型不确定时要求医生额外确认。
8. 只在全部页面成功后统一回写。输出截断、非JSON、未知字段、失效租约或上下文冲突不能部分入库。

当前没有启用自动报告脱敏：模型接收原件页面图片。A 的内部任务/职工ID、配置密钥不放进模型提示词；这不等于已经去除了报告图片里的姓名或身份证。原件及候选仍在 A 保存，不能将此功能描述为自动脱敏。

启用 preferPdfText 后，文字层足够且没有大面积图片的 PDF 页面发送原生文本，扫描页、文字不足或图片占比高的页仍发送图片。不会仅因页中文字少就丢弃页面。并发请求上限受 parallelRequests 控制，允许1–12，本机配置12；每页使用独立HTTP会话，并按原文件、原页码顺序合并，任一页失败不部分回写。reasoningEffort 通过 Responses 的 reasoning.effort 发送，当前请求none（中转站实测可接受，不代表其内部完全没有推理token）；若换用不支持此参数的模型可将配置置空。

`compactOutput=true` 使用固定列数组减少模型输出中反复出现的键名：items每行依次为项目名、结果、单位、参考范围、标记、缩写、证据；fields每行为字段键、标签、值、证据。只允许省略末尾空列，不允许中间列移位。A端还原为原有完整EHR JSON，原值、单位、来源页码和人审规则保持原语义；不会以精简传输为由删除检查页。旧对象行仍兼容。记录每页input/output/reasoning tokens与实际服务档位便于测速。

2026-11-17并发优化验证：同一份15页原件，8并发+紧凑输出+请求none重放约56.17秒，上一轮3并发约235.86秒，本轮约快4.20倍。日期、机构、4–5页原文总检、第8页40项明细、派生值为空等检查通过；详见 database/model-parallel-speed-verification.json。此为单次局域网开发环境实测，非所有报告的30–60秒SLA；上游排队、限流、复杂扫描页和失败重试仍可能超时。无正式报告覆盖、审核或发布。

后续30秒目标优化：12并发并启用 prioritizeDensePages，根据文字长度及图片大小优先启动较重页面，输出仍按原页码合并。原生文字页不要求模型重复返回quote，由A查找同时含项目名和原值的唯一原文行；找不到或不唯一时不伪造定位，保持原页码与原值证据。明确总检正文已经从PDF按边界原文保留时，模型不再重复输出全文，最终仍由A补回正文和续页。

同一份15页PDF此轮实测30.797秒，相比56.172秒再快约1.82倍。日期、机构、第8页40项、4–5页原文结论和不自动核定/归档等检查通过，见 database/model-30s-speed-verification.json。准确表述是“约31秒”，不是已经承诺稳定低于30秒。此次只读重放没有覆盖正式报告。

11页报告的9轮并发对比见 [完整测速说明](model-performance-benchmark-20261117.md)：每档3次，本轮12并发平均26.651秒、最短24.203秒；8并发平均31.709秒；6并发平均88.568秒（包含502重试）。继续保留12并发。加上此前同配置的两次慢样本，12并发共5次平均36.178秒，不能只用本轮最快值承诺稳定30秒内。

`serviceTier` 对应请求的 `service_tier`，现配置 `priority`，不是虚构的 `speed:1.5`。2026-11-16 实测该中转站 HTTP200，但实际返回 `service_tier=default`；目前只能确认已请求优先档，不能宣称上游已开启或保证1.5倍。实际返回档位写入每页 timing，不记录密钥或原文。参见 [Fast mode](https://developers.openai.com/api/docs/guides/fast-mode)；中转站能力以响应为准。

日期处理优先使用原件中明确“体检日期/时间”标签，排除模型把“总检日期/检查日期”错标为体检日期的结果。同一原件多个明确体检日期仍保留冲突。封面明确体检日期并有唯一机构标题时，使用封面机构原文，不因附件医院简称而清空。上述均是候选值，exam_date_verified 仍为空。

前端同时读取字段证据、真实页码与结论标签，旧报告也可回填明确日期、封面机构。报告级结论按原页码排列，分科小结、影像检查意见、异常汇总单独保存为 section_conclusion，不混进完整结论栏；总检结论续页保留上下文。检查结果表要求输出全部正常和异常行，若整页只汇总为 fields 则进行一次定向复核；顺序明确的 item_name/item_result 原值组可补回 items，冲突组不猜测。

明确的总检正文及续页可从原生PDF文字按标题、总检签名及页脚边界保留原文，避免模型缩写；完整程度仍为候选 partial，待医生确认。“已检/未检”等科室完成状态保存为分项所见，不冒充医学结果。

15页原件只读复测见 database/model-quality-verification.json：旧尝试597秒，新模型重放236秒（约2.53倍，非优先档保证）。最终映射见 database/model-final-mapping-verification.json，核对了封面日期/机构、4–5页原文结论、第8页40条明细及目录状态分离。复测候选单独保存，没有覆盖原报告或执行审核发布；不能把条目数等同于医学准确性。

原始日期不改写为“核定日期”。日期比较和表单支持 YYYY-MM-DD、YYYY/MM/DD、中文年月日；同一天不同写法不再产生假冲突。总检日期、打印日期、分项检查日期不会作为体检日期来源。旧报告仅有 field_values 中的 exam_date 时也可回填，真实不同日期仍留待核对。

速度验证见 `database/model-date-speed-verification.json`：同一份用户提供的10页PDF，旧任务尝试约334秒，新配置只读重放约92秒，约3.64倍。两次提取数量有差异，不能据此认定医学识别完全一致或准确；原文、页面和人工审核继续保留。重放没有覆盖已接收报告或自动核定日期。每次新任务的逐页输入类型和耗时保存在私有 model-results/{job_id}.timing.json，方便区分上游延迟与本地处理耗时。

## 失败诊断

模型网络/上游错误使用 MODEL_NETWORK_ERROR、MODEL_HTTP_* 等脱敏错误码。JSON不合法、输出截断、报告类型和文件问题分别记录，不在公共日志输出病历全文或上游响应头。

单页请求遇到网络断开或429/502/503/504时短暂退避并最多重试一次（requestAttempts默认2，范围1–3），避免一次上游抖动导致整份报告重跑。字段契约错误不自动放宽，仍不会部分写入候选。

私有运行日志：`F:/upload/.config/model-logs/adapter-real.log`、`adapter-synthetic.log`；只记录任务ID、阶段结果和错误码。适配后的候选JSON在 `F:/upload/.config/model-results/{job_id}.candidate.json`，成功的完整候选仍可从数据库原回执查看。两个目录权限限制为当前用户、SYSTEM及管理员。

单份默认最多40页，页面长边最多2000像素；复杂小字、旋转、模糊或表格跨页仍可能影响识别，需对照原件审核。不得把接收成功视为临床准确性或正式归档。

## 验证记录

- `tests/ehr/test-model-recognition.py`：上下文不可被模型替换、证据关联、冲突日期、未知字段、派生值、截断JSON和文件解码等离线测试。
- `tests/ehr/java/ModelRoutingChecks.java`：旧Worker暂停、专用令牌校验、状态接口不泄露配置等离线测试。
- `tests/ehr/test-model-recognition-live.py`：使用实际中转站模型执行隔离合成图片联调；`EHR_TEST_PDF=1` 为两页合成PDF联调。结果见 database/model-recognition*-verification.json。测试结束后联动删除测试职工的报告、原件及模型缓存，不修改正式职工，不执行审核或发布。

请求结构参考：[Responses API](https://developers.openai.com/api/reference/resources/responses/methods/create)、[图片输入说明](https://developers.openai.com/api/docs/guides/images-vision?api-mode=responses)。中转站兼容能力以实测为准。

## 2026-11-17 GLM relay integration

The current relay key lists glm-5.1, glm-5.2, glm-5.3, glm-5.3-flash and glm-5.3-flashx. Real probes confirmed image requests succeed on glm-5.3-flash and flashx; image requests on the three non-flash IDs returned HTTP 400. The configured model is glm-5.3-flash with chat_completions. This identifies the relay route only, without independently verifying its underlying model. OpenAI-specific reasoningEffort/serviceTier options are empty and omitted from requests.

Some successful relay image responses declare ISO-8859-1 although their JSON bytes are UTF-8. The adapter now decodes JSON as UTF-8 before reading fields, preserving Chinese institution names, items and evidence. A regression fixture covers the incorrect charset.

Synthetic end-to-end verification passed for a PNG (18.297 seconds of extraction, 3 items, 1 conclusion, 1 advice) and a two-page scanned PDF (12.344 seconds, 6 items, 2 conclusions, 2 advice). Dates/institution, page associations, candidate-only storage and absence of automatic approval/publication were checked. These are small synthetic samples, not latency or accuracy guarantees for real reports. Verification summaries: database/model-recognition-verification.json and database/model-recognition-pdf-verification.json.

## 2026-11-17 current configuration: OpenAI Ultrafast

The relay group has switched back to GPT. The active configuration is gpt-6.1-sol, Responses API, serviceTier=ultrafast, reasoningEffort=none and parallelRequests=12. The adapter whitelist now explicitly permits ultrafast; it sends service_tier=ultrafast rather than an invented numeric speed multiplier. Small live probes returned HTTP 200, completed and service_tier=ultrafast. The two previously failed current-report tasks are requeued through the authenticated API; approval/publication remain manual.

The relay advertises this group as an eight-times tier. Response metadata confirms the requested Ultrafast tier, but does not establish an eightfold reduction in total PDF processing time. Timings vary with page count, queueing, content and retry behavior. Official request parameter documentation: https://developers.openai.com/api/docs/guides/ultrafast-mode . Previous GLM and priority benchmarks above are historical configurations.

Ultrafast verification: the previous 13-page MODEL_HTTP_404 task was accepted successfully in 27.735 seconds, with 152 items and all 13 page responses reporting ultrafast. Another previously failed report still fails MODEL_FIELD_INVALID before candidate submission; it has not been approved or published. See database/openai-ultrafast-verification.json.

## Raw institution spelling resolution

A report can use one hospital name on department pages and the same name plus a single exam-department suffix on its welcome page. The candidate aggregator now selects that existing, more complete literal spelling. Only exact common hospital bases and the fixed suffixes defined in resolve_institution qualify. Different hospitals and competing department spellings remain unresolved; no fuzzy aliases are inferred. All per-page institution values and evidence remain unchanged.

The report API also applies this display/form fallback to old unreviewed/needs_review candidates whose stored institution is empty, using their existing institution field_values. It does not change the original OCR receipt, approved/rejected snapshots or clinical results. When the doctor saves the candidate through the normal form, the selected value follows the existing audited save workflow. Future OCR candidates persist the selected institution in institution_raw.

Verified on the latest 13-page report: page 2 contains the exact selected spelling; other pages use the same hospital base. The preserved 152 medical items are unaffected. Tests: 47 offline adapter checks and 7 Java institution-resolution checks. Evidence summary: database/institution-fix-verification.json.
