# 心理健康评估

入口：健康评估中心 → 心理健康评估；页面 `/assets/assessment/psychological.html`。

## 页面与流程
按参考图组织矿山横幅、五项统计、项目/风险/单位/班组/时间筛选、评估对象与测评记录双列表、心理风险分布、项目构成、12个月趋势、随访提醒和AI说明。保留现有平台导航，支持独立双列表翻页、每页数量、职工联动、搜索及URL筛选恢复，页面可见且未打开表单时15秒刷新。

增加“登记测评”入口：由授权医生登记真实量表名称及版本、原始总分/满分（可空）、测评时间、结果说明及来源。必须核对来源，不能拿身体体检数据填心理结果。不实现未经验证的量表自动计分、阈值分级或诊断；总分仅校验数值有效且不超出登记的满分。

评估来源为独立心理测评记录，AI上下文仅传测评与当前规则，不传身体体检明细或个人基础资料。每批1至20名职工，独立2线程持久化队列。没有心理测评记录时拒绝分析，不自动标为正常。

专业审核核对身份、测评归属、原始量表及适用依据，明确综合及分项关注等级。心理结果存独立表，不写入身体健康风险台账，不修改原始档案。测评新增、来源或规则变化后旧分析保留追溯但不再作为当前已确认结果；取消及撤权后的晚到结果不得回写。

“生成建议”明确发起心理辅助分析；“发起随访”沿用现有数字员工派活接口，为心理小谷创建待处理关怀任务（worker=care，type=visit_invitation，标题前缀“心理随访:”）。不会自动联系职工、发送通知或执行干预。

支持测评记录CSV、单版分析HTML导出，保留真实来源、原始分数、审核状态和过期说明。心理信息接口限制为本单位授权医生，其他后台角色也不能读取。

## 统计口径与边界
- 参与人数、月完成次数、项目构成来自已登记原始测评，不来自全部在档人数。
- 风险分布与高关注提醒来自来源/规则仍有效的专业审核结果；“正常”仅指筛查关注等级，不代表临床排除心理问题。
- 历史曲线按每月末最后一条专业审核记录统计需关注人数，保留历史快照语义。
- 待随访统计为本模块标题前缀的心理小谷待处理关怀任务数量，单位为条。
- 尚未接入问卷发放、提交及完成率分母，完成率返回null并显示“— 待接入”，不显示虚构百分比。当前没有心理测评登记，参与与风险统计为0，左侧可管理898人在档对象。

## 文件
- `yihe-admin/src/main/resources/templates/index.html`：新增健康评估中心子菜单及页面辅助层兼容。
- `yihe-admin/src/main/resources/static/assets/assessment/psychological.html`
- `yihe-admin/src/main/resources/static/assets/assessment/psychological.css`
- `yihe-admin/src/main/resources/static/assets/assessment/psychological.js`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/PsychologicalAssessmentService.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/PsychologicalAssessmentWorker.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/PsychologicalAssessmentController.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/DigitalWorkerModelClient.java`：心理任务专用策略，其他任务策略保留。
- `database/migrations/V023__a_psychological_assessment.sql`：独立规则、任务、事件及原始测评记录4张表；已在本地业务库和预览库创建。
- `tests/ehr/java/PsychologicalAssessmentChecks.java`：预览库事务回滚检查。

## 接口
基路径 `/ehr/api/assessment/psychological`，沿用认证、CSRF、单位和数据域权限，额外限制授权医生。
- GET 空路径：双列表、统计、构成、历史趋势、提醒和执行器状态。
- POST `/measurements`：来源核实后的原始测评登记，带请求幂等键。
- GET/POST `/rules`：辅助规则与版本。
- GET `/tasks`：当前单位最近100个分析任务。
- POST `/batches`：分析排队，明确职工及评估年度。
- GET `/records/{id}?year=2026&task=可选历史任务`：来源快照、分析、审核和历史。
- POST `/tasks/{id}`：版本校验下的专业审核或取消。
- 随访复用 POST `/ehr/api/digital-worker-tasks`，只创建待处理任务。

## 验证（2026-11-24）
- Java 8定向编译、JS语法、git diff --check通过；环境无Maven命令，未声称完成全仓Maven构建。
- 新模块27项后端检查通过：独立测评来源、医生访问限制、跨单位隔离、真实来源确认、分数校验、幂等、专业审核、心理结果不污染身体风险台账、新来源过期、历史及取消。隔离预览库夹具与替身模型，事务全部回滚。
- 慢病44项、职业风险36项、综合评估29项、AI体检33项回归通过，合计169项后端/回归检查。
- Chrome/Playwright验证真实898人在档对象、未知问卷完成率、搜索筛选、左侧分页及双表联动、测评登记、批量分析、来源/审核表单、测评结果展示、HTML/CSV导出、关怀任务表单、1920/1366/390宽度及侧栏集成，无页面JS异常。
- 浏览器有结果状态使用仅浏览器内夹具，所有POST被拦截，没有真实登记心理资料、创建任务或调用外部AI。
- WAR已部署8080，其他WAR条目字节校验保留。业务资料摘要、原有匹配审核发布计数未变化，新增4张表均为0条真实业务记录。
- 静态设计检测器因HTML解析依赖缺失降级，只作辅助，不据此声称完成计算对比度检测。

## 后续对接
独立AI配置检测可用，但本次未实际调用外部付费模型。真实量表问卷发放、答题、逐题计分、量表版本及适用规则仍需专门接入与核验；当前采用授权人员登记已有量表原始结果并专业审核的流程。

## 问卷管理集成更新（V024）
问卷完成率已接入同单位心理/压力/睡眠类问卷的实际发放与有效回收；当前无发放时仍为空并显示“暂无发放”。不自动把自建问卷转成心理测评记录，不自动应用临床量表阈值。详见 questionnaire-management.md。
