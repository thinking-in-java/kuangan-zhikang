# 数字员工分类任务结果

在既有矿安智康数字员工模块内实现，不另建项目。V014 增加本次任务事实和结构化结果字段；V010–V013 保留。业务数据、权限和原件下载均复用 EHR 服务。

## 九类工作流

| 类型 | 结果内容 | 必要事实与完成边界 |
| --- | --- | --- |
| 随访问询（发会员） | 接收对象、可复制问询内容、问题清单与发送准备 | 没有真实发送回执始终显示未发送；复制文本不等于发送 |
| 常规随访执行 | 联系事实、原系统随访记录、实际反馈、下次安排 | 未联系或没有实际反馈，结果不能审核完成 |
| 异常预警处置 | 原报告标记、原件页码、实际处置与待办 | 标记不等于危急值或确诊；没有实际处置记录不能审核完成 |
| 异常随访 | 关联异常、实际联系、复查资料、未闭环事项 | 复用人工联系事实，不伪造复查或转诊回执 |
| 慢病方案调整 | 原方案、本人目标、执行困难、调整对照及复核安排 | 没有原方案不能形成可审核结果；选项不自动应用、不改药 |
| 报告解读 | 完整录入项目、原文结论和建议、原件、历史可比性、辅助说明 | 读取并校验原件；未审核报告或原件不可读时明确资料不足 |
| 档案补全 | 原件逐页读取、逐项对照、一致/差异/无法核实/疑似漏录 | 保留 V013 工作流，不再把九项完整度当成原件核对结果 |
| 综合评估 | 已确认评估、报告线索、职业背景、资料缺口和评估待办 | 无确认记录不默认低风险，不产生虚构分数 |
| 会员投诉跟进 | 投诉原话、诉求、负责人、实际答复、约定时间和跟进清单 | 无实际答复或未联系时不能审核完成；不自动判定满意或解决 |

人工资料有保存人、保存时间、版本和事件记录。保存资料后保留历史结果，待审核任务退回待处理并需重新生成。审核确认勾选不能替代原业务权限。任务审核完成仅确认本次结果记录，不会自动发送通知、执行干预或修改健康档案。

## 数据与模型

- 按当前医生单位、real/synthetic 模式读取实际职工、报告当前版本、原系统随访任务和已确认风险评估。
- 通过既有授权原件读取器校验文件指纹、读取原文，附原件页码链接。扫描件走相同图片读取路径；无法读取不冒充已核实。
- 报告类辅助说明优先选取最多 30 项（先选原报告标记项），完整录入结果仍全部展示。模型说明属于待审核辅助内容，不宣称逐项核对通过。
- 报告历史对照只展示可比性；未审核历史、机构、单位或参考范围不一致时禁止直接判断趋势，仍需核验检测方法。
- 使用数字员工专用配置；原 OCR 参数复制初始化后独立维护，密钥只保留服务端。模型仅返回允许结构，项目解释须指向本次实际项目 ID；虚构引用、明确声称发送通知或改药的内容被丢弃。失败仍保留后台事实和工作清单，并说明无可用模型解读。
- 资料不足或结果过期不可通过审核。审核核对任务资料摘要和报告摘要，变更后需要重新处理。
- 随访问询、随访记录、投诉记录属于本模块任务事实；尚未对接会员发送回执、电话服务或干预执行接口，不写入不存在的外部执行状态。

## 公开流程参考（查阅于 2026-11-19）

以下用于组织工作步骤，没有将国外流程视为中国法律或套用固定时限，没有植入诊疗阈值或药物方案。

- [国家卫健委：2025年基本公共卫生服务工作通知](https://www.nhc.gov.cn/jws/c100073/202506/14a23782324542f59137bbf24a1c988f.shtml)：连续随访与转诊后追踪。
- [AHRQ：Health Literacy Universal Precautions Toolkit 第三版](https://www.ahrq.gov/sites/default/files/publications2/files/health-literacy-toolkit-third-edition.pdf)：随访、行动安排和确认理解的沟通方法。
- [MedlinePlus：How to Understand Your Lab Results](https://medlineplus.gov/lab-tests/how-to-understand-your-lab-results/)：以本报告单位和参考范围理解结果，区分标记与诊断。
- [NICE NG197：Shared decision making](https://www.nice.org.uk/guidance/ng197/chapter/recommendations)：本人目标、选项、风险和不确定性的共同讨论。
- [PHSO：NHS Complaint Standards](https://www.ombudsman.org.uk/handling-complaint/complaint-standards/nhs-complaint-standards)：投诉事实、诉求、调查依据、答复与反馈。

## 验证

先执行项目 Maven verify。构建后运行 `tests/ehr/test-digital-worker-workflows.py`：需通过环境变量提供隔离的 `shayu_3_preview` 连接及 JDK8 路径；执行原作者智能体、小程序、档案权限、原件证据与九类任务检查。全部合成写入回滚，不调用外部模型。

`tests/ehr/test-digital-worker-original-reader.py` 检查真实文件读取、页码、指纹和扫描件路径。浏览器验证桌面与手机端结果布局、筛选、复制、资料表单、菜单、原件预览及审核保护。真实 API 试调用应单独记录，禁止混同合成测试数据。

本次验证：Maven verify 成功；Java 五组检查共 163 项、原件读取器 6 项通过。浏览器验证九类表单、八类新增结果和已有原件核对，覆盖 1600×1000 / 390×844、分类筛选、分页、复制、资料保存交互、过期版本和审核确认保护。另对现有报告解读任务真实调用共享 OCR API，确认读取原件、114 个录入项目和 6 条有项目来源的辅助说明；保留历史尝试，未审核通过或修改健康档案。

## 主要实现文件

- `yihe-framework/src/main/java/com/yihe/framework/ehr/DigitalWorkerTaskProfiles.java`：九类任务定义、字段校验与流程来源。
- 同目录 `DigitalWorkerTaskResults.java`：授权数据读取、分类结果、状态与依据。
- 同目录 `DigitalWorkerModelClient.java`、`DigitalWorkerOriginalCheck.java`：独立模型服务的结构化辅助说明及原件读取。
- 同目录 `DigitalWorkerTaskService.java`、`DigitalWorkerTaskController.java`：事实资料保存、事件与版本、审核保护和接口。
- 同目录 `DigitalWorkerAiQueue.java`、`DigitalWorkerAiRunner.java`：分类处理与结构化结果持久化。
- `yihe-admin/src/main/resources/static/assets/js/digital-worker-task-results.js`：分类结果、事实表单、来源、复制、项目筛选与分页。
- 同目录 `digital-worker-dispatch.js`、`digital-worker-service.js`：派活、详情与 API 对接。
- `yihe-admin/src/main/resources/static/assets/css/digital-worker-dispatch.css`：沿用现有弹窗风格，完善结果排版和适配。
- `yihe-admin/src/main/resources/templates/digital-workers/index.html`、`workbench.html`：加载分类结果组件与缓存版本。
- `database/migrations/V014__a_digital_worker_task_results.sql`：任务事实、记录人和结果字段；已用于本地业务库及隔离验证库。
- `tests/ehr/java/DigitalWorkerWorkflowChecks.java`、`tests/ehr/test-digital-worker-workflows.py`：分类工作流与回归验证。

## 五位员工统一任务总览（2026-11-20）

总览中央的任务池现在是可点击、支持键盘操作的入口，展开页面内的汇总列表。每位员工卡片展示全部、活跃、待审核数量，可以直接切换到该员工及对应状态；原工作台入口继续保留。顶部任务统计、底部活跃任务和近期执行记录使用同一汇总来源。

数据来源保持独立：

- 派活任务来自 `a_digital_worker_tasks`，保留五位员工原有负责人及任务状态。
- OCR 来自 `a_ocr_dispatch_tasks`，归属档案小谷；识别重试状态映射到待处理，同时显示实际来源状态。
- 指标预警归属评估小谷，仅按原医生与会员的负责关系读取。没有 real/synthetic 字段的旧预警表不会进入 synthetic 数据模式。
- 关联档案已删除或不可访问时，仍保留本单位 OCR 历史任务的状态、进度和时间；隐藏不可访问的职工信息并关闭原件详情入口。发现现有 OCR 统计 9 条而原列表仅展示 3 条，此次保留其余 6 条历史任务，未恢复或放开原档案权限。

支持员工、状态、来源、类型、优先级、关键词、排序和服务端分页。关键词支持任务标题、姓名、工号及任务编号，通配符按字面量处理。筛选条件保存在总览 URL，并复用现有侧边栏页面恢复机制。计数在同一数据库快照中聚合，不新建影子任务，不重复累计执行尝试。每 15 秒按页面可见性刷新；失败显示不可用并允许重试。

派活行复用原任务详情与审核操作；OCR 行跳转现有执行记录并定位实际任务；预警行进入现有预警查询。没有增加通知、档案修改或干预执行权限。

设计参考 [Jira 的负责人、状态筛选](https://support.atlassian.com/jira-software-cloud/docs/filter-work-items/)和 [Asana 的进度汇总与筛选](https://help.asana.com/s/article/tracking-project-progress)，沿用当前上传 HTML 的页面配色与员工布局，没有复制其品牌或另建项目。

主要新增 `DigitalWorkerTaskHub.java`、`DigitalWorkerTaskHubController.java`、`digital-worker-hub.js`、`digital-worker-hub.css`，调整既有总览、API 服务层、OCR 详情定位及后台页面恢复代码，无新增数据库表或迁移。`DigitalWorkerHubChecks.java` 的 31 项检查验证汇总、五位员工、筛选分页、模式与单位隔离、历史不可访问记录和只读行为；与既有五组 Java 检查共 194 项，合成数据全部回滚。

## 可保存的岗位配置（2026-11-20）

原“数字员工配置”只有接入状态说明，没有编辑和保存能力。现在按单位、数据模式和员工保存以下配置：接收新派活、允许 AI、派活默认勾选 AI、可接收任务类型、默认任务类型、默认优先级、活跃派活上限（1–500）、岗位处理要求。

- 医生可修改本单位配置，其他后台账号仅查看；移动端不能访问配置接口。写请求沿用 CSRF 和版本检查。配置变更记录修改人、时间、前后值。
- 新派活在服务端检查接收开关、允许类型和上限，不能靠修改前端绕过。同一岗位的接收与配置更新使用行锁；派活和 AI 请求使用 READ COMMITTED，避免空请求键的间隙锁和陈旧读取。幂等重试不会因达到上限或暂停接收而重复建任务。
- 上限包含待处理、处理中、待审核的派活任务，不把 OCR 或指标预警算入该限额。降低上限或暂停接收不取消历史任务。
- AI 开关控制本岗位的新请求；还有排队或运行中的请求时不能直接关闭，避免遗留永远不再处理的请求。新请求保存岗位配置快照，进行中请求继续使用原版本；处理要求不改变证据和审核规则。
- 默认类型、优先级、可选类型及 AI 默认勾选实际应用到派活窗口。保存配置不会自行生成任务、发送通知、修改档案或应用干预；原件核对继续使用固定证据规则。
- 页面不展示模型 API 名称或具体型号；保留的 OCR 队列状态明确与数字员工处理服务独立。数字员工专用地址及密钥由服务器维护，岗位配置不写入凭据。
- 表单支持未保存修改保护、恢复默认值后再保存、放弃修改并重新读取、保存错误恢复和刷新后保持所选员工。

迁移 `V015__a_digital_worker_settings.sql` 增加岗位配置、配置审计两张表，以及 AI 请求的岗位配置快照字段。已应用到本地业务库和隔离验证库。主要实现为 `DigitalWorkerSettings.java`、`DigitalWorkerSettingsController.java`、`digital-worker-settings.js`、`digital-worker-settings.css`，并接入现有派活服务、AI 请求和模板。

`DigitalWorkerSettingsChecks.java` 新增 28 项检查，覆盖实际准入和 AI 限制、版本、权限、跨单位/数据模式隔离、快照、审计和幂等行为，全部回滚，无外部模型调用。页面验证使用相同配置值进行真实保存和刷新，配置值保持不变；另外以模拟响应验证前端暂停及 AI 关闭状态，不写入这些模拟规则。


## 专用模型配置与展示调整（2026-11-20）

按用户要求移除配置页的“共享 OCR 模型 API”、已配置的具体模型名称及复用说明，派活窗口不再展示型号。公开的数字员工服务状态接口只返回配置就绪与审核要求，不返回型号或凭据。

将原 OCR 私有参数复制到独立文件，并仅修改数字员工启动参数的文件指向。OCR 原文件及原有业务配置保持不变。复制初始化时的服务商、模型及凭据参数相同；这是独立配置维护，不表示创建了新的服务商账号、API密钥或远程模型实例。数字员工客户端没有读取或回退到 OCR 配置的路径；独立文件缺失或无效时明确不可用。

`DigitalWorkerModelIsolationChecks.java` 使用合成文件验证：缺失或损坏专用配置不回退 OCR、两份配置分别修改互不影响，测试不访问真实凭据或外部 API。
