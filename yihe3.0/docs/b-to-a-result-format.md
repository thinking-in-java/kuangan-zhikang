# B → A OCR 结果回传格式（当前实现，2026-11-16）

这是现有 A 端 Worker 接口的对接说明，不是新接口。原始文件由 A 保存，B 下载、识别并回传 JSON；B 不直接写 MySQL。

## 1. 调用流程和地址

| 用途 | 方法及路径 |
|---|---|
| 查询环境、租约参数 | `GET /ehr/worker/environment` |
| 获取 67 个标准字段和 655 个项目字典 | `GET /ehr/worker/dictionary` |
| 领取任务 | `POST /ehr/worker/tasks/claim`，请求 `{"workerId":"ocr-pc-b-01","requestKey":"每次领取生成的UUID"}` |
| 获取任务上下文 | `GET /ehr/worker/tasks/{jobId}/input` |
| 下载原件 | 使用 input 中 `files[].downloadPath` |
| 心跳续约 | `POST /ehr/worker/tasks/{jobId}/heartbeat` |
| **回传完整结果** | **`POST /ehr/worker/tasks/{jobId}/result`** |
| 查询接收回执 | `GET /ehr/worker/tasks/{jobId}/receipt` |
| 报告识别失败 | `POST /ehr/worker/tasks/{jobId}/failure` |

正式环境：`http://192.168.1.105:18080`。隔离合成测试环境：`http://192.168.1.105:18082`。两套 Worker Token 不同，队列按 data_mode 隔离，不能交叉提交。

所有 Worker 请求携带 `Authorization: Bearer <对应环境的Worker Token>`。input、下载、心跳、结果、失败、回执请求额外携带 `X-OCR-Lease: <本次领取返回的leaseToken>`。

结果请求使用 `Content-Type: application/json`，请求体直接是报告对象，**不要套 `data`、`result` 或 `payload` 外壳，也不要把 JSON 当文件上传**。

当前租约 600 秒，建议每 200 秒心跳；B 应优先读取响应中的 `leaseSeconds` 和 `heartbeatSeconds`，不要写死旧值。心跳示例：`{"stage":"validation","percent":95}`。相同阶段/百分比可以重复续约，进度不能倒退；100% 由 A 入库成功后设置。

## 2. 顶层字段名称

“标准发送”表示新 B 端应始终携带；部分旧字段缺省在 A 端仍兼容，不建议依赖兼容行为。

| 字段 | 类型 | 来源及要求 |
|---|---|---|
| `contract_version` | string | 必填，`"2.0.0"` |
| `dictionary_version` | string | 标准发送，`"2.0.0"`；不能拼成 `dictionarversion` |
| `job_id` | string UUID | 标准发送，取本次 input.job_id，与 URL 的 jobId 相同 |
| `scope_code` | string | 必填，原样复制 input，不自行填 LOCAL_BENCH/DEMO_ONLY |
| `data_mode` | string | 必填，原样复制 input：`real` 或 `synthetic` |
| `report_id` | string UUID | 标准发送，原样复制 input.report_id；不是 job_id |
| `revision` | integer | 标准发送，原样复制 input.revision；不是字符串，不自行加 1 |
| `prior_revision` | integer/null | 原样复制 input.prior_revision |
| `record_id` | string UUID/null | 原样复制 input.record_id，不猜测或改绑职工 |
| `report_kind` | string | 标准发送；个人报告沿用 input 的 `individual`。确实不能判断可 `unknown`，A 将要求医生确认；不得把组织汇总回传为个人报告 |
| `institution_raw` | string/null | **体检机构原文**；未识别为 null |
| `exam_date_raw` | string/null | **报告级体检日期原文/候选**；不能用分项检查日期或报告打印日期代替 |
| `exam_date_verified` | null | B 统一发 null；由 A 人工核对保存后核定，A 不接受 B 直接核定 |
| `report_date_raw` | string/null | 报告日期原文，区别于体检日期 |
| `exam_type_raw` | string/null | 体检类型原文，例如在岗职业健康检查 |
| `exam_number_raw` | string/null | 报告号/体检编号原文 |
| `age_raw` | string/null | 年龄原文，不用企业资料补造 |
| `review_status` | string | 标准发送 `needs_review`；A 保存为待核对候选 |
| `source_files` | array | 标准发送，原样复制 input.source_files |
| `evidence` | array | 必填；实际证据列表，详见下文 |
| `field_values` | array | 必填；标准字段观察记录，详见下文 |
| `items` | array | 必填；医学检查项目结果，详见下文 |
| `conclusions` | array | 必填；**完整体检结论原文**，详见下文 |
| `advice` | array | 必填；**报告医师建议原文**，详见下文 |

没识别到的标量字段发 null，没识别到的集合发 `[]`。不能用“无异常”“正常”等默认值伪装成识别结果。67 是字典字段种类数，不要求每份报告凑齐 67 条；重复观察保留证据，不能把各科 `section_conclusion` 冒充完整结论。

## 3. source_files[]：原件关联

| 字段 | 类型 | 要求 |
|---|---|---|
| `source_file_id` | string UUID | A input 中的原件 ID |
| `sha256` | string | A input 中的 64 位十六进制哈希，文件内容应核验一致 |
| `page_count` | integer | A input 中的页数；不是 B 自行重算的另一个版本 |

数量、ID、哈希、页数须与本次 input 一致。所有 evidence 的 source_file_id 必须属于这里的原件。

## 4. evidence[]：页码和原文证据

| 字段 | 类型 | 要求 |
|---|---|---|
| `evidence_id` | string UUID | B 生成，当前报告版本内唯一 |
| `source_file_id` | string UUID | A 原件 ID |
| `page_number` | integer | 从 1 开始，不能超过原件页数 |
| `bbox` | number[4]/null | `[x0,y0,x1,y1]`，非负，x1>x0、y1>y0；坐标不可编造 |
| `coordinate_system` | string | `pdf_points_unrotated` / `image_pixels_top_left` / `object_only` |
| `page_rotation` | integer | 0、90、180、270 |
| `render_dpi` | integer/null | 图片像素坐标必须填实际正数 DPI；PDF 点坐标可 null |
| `original_label` | string | 原文标签，例如姓名、体检日期、血压 |
| `raw_fragment` | string/null | 原始片段，保留上下文 |
| `extraction_method` | string | `native_text` / `ocr` / `pdf_object`；B 不使用 manual_transcription |
| `extractor_version` | string | 实际解析/OCR 程序版本，不编造 |
| `ocr_confidence` | number/null | OCR 可填 0–1；非 OCR 必须 null |
| `review_status` | string | `needs_review` |

`object_only` 时 bbox 必须 null；其他坐标系必须有实际四元 bbox。一个证据可以被多个相关字段引用。

## 5. field_values[]：标准字段观察记录

| 字段 | 类型 | 要求 |
|---|---|---|
| `field_key` | string | 必须在 A standardFields 字典内；例如 full_name、sex、exam_date、institution、check_date |
| `original_field_name` | string | 报告上的原标签 |
| `value_raw` | string | 识别原值，数值也先保留原始字符串 |
| `value_normalized` | null | **当前必须 null，空字符串也不允许** |
| `normalization_rule_version` | null | **当前必须 null** |
| `evidence_ids` | string[] | 非空；每个 UUID 必须存在于本报文 evidence 中 |
| `review_status` | string | `needs_review` |

全量 67 个 field_key 以随附字典及 `GET /ehr/worker/dictionary` 为准。报告级日期使用 exam_date；分科检查日期使用 check_date。日期、机构若同时放在顶层和 field_values，内容须一致。顶层 exam_date_raw 用于回填，exam_date_verified 仍保持 null。

## 6. items[]：医学检查项目

| 字段 | 类型 | 要求 |
|---|---|---|
| `item_id` | string UUID | B 生成，数组内唯一 |
| `ordinal` | integer | 正整数、数组内不重复 |
| `primary_evidence_id` | string UUID | 主证据，必须列于 evidence_ids |
| `evidence_ids` | string[] | 非空，引用 evidence 中的 UUID |
| `original_field_name` | string | 项目原名称 |
| `result_raw` | string/null | 原始结果，可数值、复合值或定性文字 |
| `unit_raw` | string/null | 原始单位 |
| `reference_range_raw` | string/null | 原始参考范围，不能套统一范围 |
| `abnormal_flag_raw` | string/null | 报告原有 ↑、↓、H、L 等标记，无则 null |
| `abbreviation_raw` | string/null | 原文项目缩写 |
| `item_code` | string/null | A 医学项目字典的编码；不能确定则 null |
| `dictionary_version` | string/null | 匹配成功发 2.0.0；未匹配可 null |
| `standard_field_name` | string/null | 字典名称或 null；A 以 item_code 查询的字典名称为准 |
| `mapping_status` | string | 未匹配发 unmapped；A 按字典状态保存，不信任 B 自行升级状态 |
| `field_values` | array | 必须提供，可 `[]`。仅允许 check_date、lab_date、item_name、item_result、item_unit、item_reference、item_abnormal、item_abbreviation |
| `result_normalized` | null | 当前必须 null |
| `unit_normalized` | null | 当前必须 null |
| `unit_conversion` | null | 当前必须 null |
| `normalization_rule_version` | null | 当前必须 null |
| `review_status` | string | needs_review |

items/conclusions/advice 如果带 contract_version、scope_code、data_mode、report_id、revision，必须与顶层及 A input 完全相同。建议统一携带这些上下文字段。

## 7. conclusions[]：完整体检结论原文

| 字段 | 类型 | 要求 |
|---|---|---|
| `conclusion_id` | string UUID | B 生成，数组内唯一 |
| `primary_evidence_id` | string UUID | 主证据，必须列在 evidence_ids 内 |
| `evidence_ids` | string[] | 非空，有效证据引用 |
| `original_field_name` | string | 结论原标题 |
| `original_text` | string/null | **完整体检结论原文**，不能改写为 AI 总结 |
| `completeness` | string | complete / partial / unknown，按实际提取完整程度填写 |
| `standard_category` | null | 当前必须 null；不能由 OCR 认定正式医学分类 |
| `review_status` | string | needs_review |

结论跨页要保留全部文本及涉及的证据。若报告只写“暂无个体结论”，只能如实返回该原文，不能补写“正常”；仍需医生核对。

## 8. advice[]：报告医师建议原文

| 字段 | 类型 | 要求 |
|---|---|---|
| `advice_id` | string UUID | B 生成，数组内唯一 |
| `source_kind` | string | 当前 Worker 契约使用 pdf，表示来自报告原文；不能发 clinician |
| `source_evidence_id` | string UUID | 必须指向 evidence 中真实的建议原文证据 |
| `original_field_name` | string | 原标签，例如医师建议、健康建议 |
| `text_raw` | string | **报告医师建议原文**，不是模型新增建议 |
| `author_ref` | null | 必须 null，不能冒充后台医生 |
| `review_id` | null | 必须 null，不能伪造审核 |
| `review_status` | string | needs_review |

## 9. 当前禁止的派生值

在所有层级（包括 items[].field_values[]）保持：value_normalized、result_normalized、unit_normalized、unit_conversion、normalization_rule_version 均为 null；conclusions[].standard_category 为 null。

原文分别放在 value_raw、result_raw、unit_raw、reference_range_raw、original_text、text_raw。空字符串、0、空对象或空数组都不等于 null，会触发校验错误。不能仅为通过校验而删除原始识别内容。

## 10. 成功、失败和重传

成功：HTTP 200 且 `code=0`。随后 GET receipt 应为 `accepted=true`、`state=succeeded`，检查条目/证据计数；这仅代表候选结果接收成功，不代表医生审核或正式归档。

HTTP 422 / 413：保存返回的 msg 并定位字段；整次候选结果回滚，原件保留，任务记录 INVALID_RESULT。修正 B 后在后台重试，再领取有效租约后回传。

同一任务相同 JSON 可幂等重传；相同任务不同内容返回 409，不覆盖已经接收的结果。需要更正已成功结果时，通过 A 后台重新识别/修订获取新任务。

随附 b-to-a-result.example.json 是合成格式示例，UUID、哈希、医学文字和日期均为占位示例。实际发送必须用本次 input 绑定上下文和原件，并换成真实识别内容，不能把示例直接提交到正式任务。
