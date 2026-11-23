# 中转站模型识别接入

当前本地环境默认识别提供方已切换为 `model`。旧 B 端 Worker 不再领取新任务；原件、旧任务、历史结果和人工审核流程保留。没有新建数据库表。

## 流程

后台/小程序原上传接口 → A 保存原件 → 创建 `model-vision-v1` 持久任务 → 本机模型适配进程领取 → 下载并核验 SHA256 → PDF 按原页渲染/图片解码 → 中转站模型返回逐页 JSON → A 端适配为 EHR 2.0.0 → 原事务校验/回执 → 待人工核对。

职工、报告、版本、数据范围和原件 ID 均来自 A，不交由模型分配。模型只提取可见报告原文，不直接访问数据库或调用业务写接口。

## 配置

私有配置文件由 `ehr.recognition.config-file` 指定，部署时应放在上传目录和版本控制之外。apiKey 只存于该私有文件，不写前端、源码、命令行或业务日志。上传原件目录现为 `D:/update`；旧 F 盘默认路径已替换。

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

## 本机上传恢复与存储迁移（2026-11-20）

发现上传停在 0% 的任务尚未被领取（attempt_count=0），并没有调用模型 API。原因是本机识别提供方为 model，但运行配置仍指向不存在的旧 F 盘配置；识别监督进程因此未启动。修正启动脚本中的 OCR 私有配置、模型与队列凭据文件、Python 运行时，并为子进程提供已验证的依赖路径。参数只传文件路径，不传凭据值。

该原任务恢复后首次尝试成功：12 页、81 个项目、189 条证据，模型提取约 164 秒；结果为候选，未审核、未发布。后续重启确认监督进程自动恢复，未再次领取已完成任务。

按用户要求把 `ehr.storage-root` 与 `yihe.profile` 都切换至 `D:/update`，并替换业务上传相关的旧 F 盘默认值。数据库保留相对 object_ref；将当前可访问原件复制到新目录，逐份核验 SHA256，旧副本保留用于回滚。识别配置和数字员工专用配置仍各自位于私有目录，凭据不复制到上传目录，也未合并。

`UploadStorageChecks.java` 在显式指定的新目录执行合成图片上传、下载、字节校验、幂等和跨单位访问检查；SQL 与合成源文件全部回滚，没有请求模型。浏览器确认真实报告进度 100%，且新目录原件下载指纹一致，未执行审核或归档。

## 上传临时目录失效修复（2026-11-21）

本次失败发生在 Servlet multipart 解析阶段：Windows Temp 下的 Tomcat 工作目录不存在，原件尚未进入业务保存和 OCR 入队。原件目录正确不代表请求临时目录也正确。旧异常处理甚至以 HTTP 200 返回业务 code=500，因此页面显示运行时错误。

为上传解析独立设置 `spring.servlet.multipart.location`，默认 `${ehr.storage-root}/.tmp/uploads`，可通过 `EHR_UPLOAD_TEMP` 指定；Tomcat basedir 默认位于 `${ehr.storage-root}/.runtime/tomcat`，可通过 `EHR_TOMCAT_BASEDIR` 指定。本机启动器明确使用 `D:/update` 下的对应目录，并把 JVM 临时目录放入 `.runtime/java-tmp`。原件仍使用原有相对 object_ref 和授权下载流程，不改变报告归属、审核和发布规则。

`UploadTempDirectoryFilter` 在启动时创建并验证 multipart 目录，在 multipart 请求解析前再次创建缺失目录。Tomcat 9.0.13 不会自行恢复该目录。磁盘或权限导致目录不可用时返回 HTTP 503 与明确 JSON 提示，客户端不接收服务器路径和底层异常堆栈。

验证：Maven 打包成功；真实嵌入式 Tomcat 的 11 项回归检查覆盖初次创建、字节一致性、运行中删除后恢复、阻塞路径返回 503、存储恢复和请求临时文件清理。另有 8 项隔离预览数据库上传检查，验证有效原件保存、下载、OCR 入队、幂等及权限，并回滚全部测试数据。8080 实测请求从原先的目录解析失败恢复到正常的文件内容校验；删除检查为空的 multipart 目录后再次请求自动重建。浏览器验证上传弹窗、422 文件内容提示、重试按钮和重新打开。保留 898 名职工、已有 3 个报告版本、账号与数字员工配置；没有向正式库插入测试报告，也未发起模型请求。

## 最新报告同步姓名和出生年份（2026-11-21）

> 历史实现说明：以下自动回写逻辑已于 2026-11-22 取消，以本文“取消报告自动改写职工资料”一节为准。原有审计记录保留。

按本机后台明确指定的业务规则，成功接收 OCR 候选结果时，使用最新上传个人报告的 `full_name` 更新当前所选职工姓名；从 `age` 原文字段和 `age_raw` 中取一致、明确的整数年龄，按上海时区当前年份减年龄计算出生年份，保留原出生月日。例如 2026 年报告年龄 30 岁、原生日 1982-06-18，更新后为 1996-06-18。页面年龄按年份差展示，同步成功后识别进度页会刷新职工列表和统计。

`OcrRosterIdentitySync` 只接受后台文件和拍照上传；个人小程序、报告类型未知、同份报告多人姓名以及旧报告迟到回调不改企业身份资料。未识别到姓名或年龄时不清空原值；年龄冲突、无法解析、原生日缺失或推算年份无法容纳原月日时，保留相应原值并列出核查提示。原始 OCR 报告姓名、年龄、证据和医学项目保持原文；报告不会因此自动审核或发布。

姓名与生日同步和 OCR 回执处于同一事务，回执重放不会重复增加维护版本。报告加锁先锁职工，避免同一职工多份报告并发回写时反向占锁。审计动作 `ocr_roster_identity_synced` 保存报告、识别任务、来源文件、证据 ID、参考年份、变更前后值及原出生月日保留规则；已有的乐观锁阻止旧编辑表单覆盖同步结果。

隔离测试库的 `OcrRosterIdentityChecks` 覆盖 30 项解析、日期和真实回调检查；与既有健康师、个人权限、数字员工、原件核对、任务总览、配置、模型隔离和上传存储检查一起，共 266 项通过。全部测试数据回滚，不调用模型。另对已有成功识别的最新报告执行同一规则的本机维护，维护前保留完整数据库备份。

本机维护检查了 5 名有成功识别报告的职工，实际更新 1 名的姓名和出生年份，其他 4 名保留当前值（其中 1 名的报告年龄缺失或无效）。898 名职工总数及其他职业资料、账号、报告、识别任务和模型配置保持不变。浏览器核验全部 898 条年龄的年份差算法、更新后的编辑表单、出生月日、刷新保留，以及已完成 OCR 进度触发列表刷新；未新增测试报告或调用模型。

### 补充报告性别同步（2026-11-21）

同一事务增加报告标准字段 `sex` 到职工 `gender` 的同步，审计规则升为 `latest-report-roster-identity-v2`。接受明确的男、女、男性、女性以及 M/F、Male/Female 写法，保留报告原值与证据；不按姓名推测，不猜测 0/1 等未约定编码。多页性别冲突、无效或缺失时保留当前性别，并提示核对；其他有效身份字段仍可独立同步。沿用最新后台上传、租户、任务重放和个人端权限约束。

身份同步回调检查扩展到 41 项，验证性别回写、性别单独识别、冲突与未知值、旧报告晚到、个人上传限制以及审计和幂等；既有检查一起共 277 项通过，全部使用回滚的隔离数据，无模型请求。当前成功识别报告在完整备份后使用同一规则检查已有职工性别。

本次维护时，当前 3 名成功识别报告的职工性别已与报告一致，因此性别实际修改 0 条；同一同步规则修正了其中 1 名的姓名。另 1 名尚无成功 OCR 回执，保留当前资料。浏览器逐页核对 898 条档案与数据库性别值，验证编辑表单、刷新保留和上传提示；未新增测试报告。

## 多份报告并发识别（2026-11-21）

原先 `parallelRequests` 只控制一份报告内的页面线程，工作服务仍逐份领取任务。现在 Java 监督进程启动报告协调器，协调器维护独立的报告工作进程，各工作进程仍使用页面线程池。PDF 解析放在不同进程，因为 [PyMuPDF 官方文档](https://pymupdf.readthedocs.io/en/latest/faq/index.html) 不支持跨线程并行使用解析库；原文解析不在页面请求线程中运行。

在现有 OCR 私有配置中合并以下并发字段，其他模型、地址、密钥、服务档位和识别参数保持原值：

```json
{
  "concurrentReports": 3,
  "maxTotalRequests": 12,
  "parallelRequests": 12
}
```

`concurrentReports` 默认 3，范围 1–8，表示同时处理的报告数；`maxTotalRequests` 默认 12，范围 1–24，表示全部报告共享的模型请求上限。`parallelRequests` 继续限制每份报告的页面线程。共享请求预算按等待顺序分配，只有一份报告时可使用全部可用名额，不把名额固定切成每份 4 个。模型请求重试和补充提取也受总体上限约束，等待名额时工作进程继续独立续租。

协调器每秒检查并发设置。增加报告容量时补齐工作进程；减少时已有报告处理完毕，退出的工作进程不再领取新任务。降低总请求容量不会中断当前 HTTP 调用，后续请求等待活动数降到新上限以内。工作进程退出后释放其共享预算并重新启动，失效任务仍由原有租约回收和重试机制处理。每个工作进程有唯一 worker ID、独立 HTTP 会话、心跳和候选文件；领取请求遇到网络歧义时保留同一个幂等键重试。Java、协调器、预算管理进程和报告进程有父进程存活监测，避免重启后遗留孤立进程。

公开识别状态增加 `concurrentReports`、`maxTotalRequests`，上传和任务列表显示报告处理容量及排队说明，不把并发配置伪装为识别完成。私有 `model-logs/pool-real.json` 仅记录协调器 PID、工作进程 PID、请求活动数和时间，不记录原件、临床内容或密钥。姓名、性别、出生年份同步仍由既有结果回执事务处理，旧报告不能覆盖新报告，不自动审核归档。

验证：47 项原有离线适配检查、11 项新增并发检查、11 项 Java 路由和状态检查通过。新增测试使用本机合成 API 和独立 PDF：3 份报告同时领取及处理，共享上限设为 2 时实际请求峰值为 2；逐份进度、来源、证据页序、失败隔离、容量回收、进程异常重启、增减容量和关闭均通过。未向正式库上传测试报告或请求外部模型。部署前等待原在途报告处理结束；运行环境核验 3 个独立报告进程受 8080 服务监督，模型总体上限 12，原 API 配置和数字员工独立模型未更改。浏览器核验上传页和任务列表的“最多同时识别 3 份”提示。

## 检查项目缺少原名称的页面恢复（2026-11-21）

`MODEL_FIELD_INVALID: 模型检查项目缺少原名称` 来自候选构建校验，表示模型某条结果没有提供项目名称。此前在全报告页面合并时才校验，一条异常会让整份报告停在 80%；同类错误早于报告并发调整出现。该错误不能用项目结果、缩写或“未知项目”占位绕过。

现在逐页执行候选构建校验，再将该页计入已完成进度。只兼容明确的项目原名键 `name`、`item_name`、`original_field_name`、`项目名称`；没有主键名称时，其他原名字段须只有一个明确值才使用。仅移除完全空的记录，不忽略有结果但无名称的行，不改写结果、单位及参考范围。格式校验失败时只重提取当前页，`pageRepairAttempts` 默认 2、范围 0–2，并遵守原共享模型请求上限及续租机制；HTTP 限流、网络和其他业务错误不被当成格式问题反复请求。重试后仍不合格时保留失败状态，并标明页码、行号和本页重试次数。

通过校验的页面写入私有 `<jobId>.pages.json` 缓存，绑定任务、报告版本、租户、数据模式、原件 SHA256、字典、模型与提取设置及提示版本，逐项校验缓存摘要。相同失败任务再次重试可复用成功页；换原件、模型或任务不会串用缓存。旧版本失败任务没有这些缓存，第一次使用新版本重试时仍需读取全份原件。异常页结构另存私有诊断文件，不向前端或日志输出临床内容和密钥。缓存与诊断沿用现有 `jobId.*` 文件清理机制，删除报告时一并处理。单页最终不合格不会提交残缺候选。

验证：47 项原有离线适配检查、11 项报告并发检查和 10 项页面恢复检查通过。新增检查覆盖原名键兼容、完全空行、有结果的无名行不得丢弃、页面重试边界、失败保留成功页、缓存防串用与摘要校验、并发写缓存、HTTP 错误不重复格式修复。切换时等待在途报告结束，只重载本机识别工作池，8080 网页服务未重启，8 份报告并发和总请求上限 12 保持不变。

对一份既有同类失败报告执行认证重试后，14 页、167 个项目、301 条证据成功接收，进度 100%，状态 `needs_review`、未发布。浏览器核验绿色完成进度和 167 行候选表单，所有项目有原始名称。该次重试没有再次触发格式错误，14 页均校验并缓存；问题页自动重试和部分页复用另由离线测试覆盖，不能据此承诺任何原件都能自动识别成功。

## 取消报告自动改写职工资料（2026-11-22）

按用户要求移除 `OcrRosterIdentitySync` 及 OCR 回执中的调用。新上传、识别成功、重复回调和提交报告均不再修改当前职工姓名、性别、出生日期、维护版本或其他原始资料。报告身份原文和证据仍然接收保存，姓名不一致继续标记 `identity_status=conflict` 并交人工核对；后台授权编辑继续可用。移除此行为不代表恢复以前的资料，已存在的人工及同步历史记录保留。

原有身份同步检查替换为24项真实回调与提交的资料保留检查，涵盖后台文件、拍照、小程序、原文字段、身份冲突、重复回调、候选补录、提交、授权人工编辑及无自动发布。与其他健康师、数字员工和上传存储检查合计260项通过，测试仅使用隔离数据库并全部回滚。构建检查确认运行包不再包含自动回写类。

本次发布前后核对898名职工及222个现有报告版本，数据库业务数据没有改变；没有生成或填入额外体检结果。8份报告并发、模型请求上限12、逐页恢复、分页返回和原件存储功能保持。浏览器验证上传提示、上传入口、人工编辑和第3页详情返回。

## 2026-11-17 current configuration: OpenAI Ultrafast

The relay group has switched back to GPT. The active configuration is gpt-6.1-sol, Responses API, serviceTier=ultrafast, reasoningEffort=none and parallelRequests=12. The adapter whitelist now explicitly permits ultrafast; it sends service_tier=ultrafast rather than an invented numeric speed multiplier. Small live probes returned HTTP 200, completed and service_tier=ultrafast. The two previously failed current-report tasks are requeued through the authenticated API; approval/publication remain manual.

The relay advertises this group as an eight-times tier. Response metadata confirms the requested Ultrafast tier, but does not establish an eightfold reduction in total PDF processing time. Timings vary with page count, queueing, content and retry behavior. Official request parameter documentation: https://developers.openai.com/api/docs/guides/ultrafast-mode . Previous GLM and priority benchmarks above are historical configurations.

Ultrafast verification: the previous 13-page MODEL_HTTP_404 task was accepted successfully in 27.735 seconds, with 152 items and all 13 page responses reporting ultrafast. Another previously failed report still fails MODEL_FIELD_INVALID before candidate submission; it has not been approved or published. See database/openai-ultrafast-verification.json.

## Raw institution spelling resolution

A report can use one hospital name on department pages and the same name plus a single exam-department suffix on its welcome page. The candidate aggregator now selects that existing, more complete literal spelling. Only exact common hospital bases and the fixed suffixes defined in resolve_institution qualify. Different hospitals and competing department spellings remain unresolved; no fuzzy aliases are inferred. All per-page institution values and evidence remain unchanged.

The report API also applies this display/form fallback to old unreviewed/needs_review candidates whose stored institution is empty, using their existing institution field_values. It does not change the original OCR receipt, approved/rejected snapshots or clinical results. When the doctor saves the candidate through the normal form, the selected value follows the existing audited save workflow. Future OCR candidates persist the selected institution in institution_raw.

Verified on the latest 13-page report: page 2 contains the exact selected spelling; other pages use the same hospital base. The preserved 152 medical items are unaffected. Tests: 47 offline adapter checks and 7 Java institution-resolution checks. Evidence summary: database/institution-fix-verification.json.
