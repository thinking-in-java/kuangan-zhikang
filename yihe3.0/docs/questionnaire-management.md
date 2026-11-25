# 问卷管理与分析

入口：健康评估中心 → 问卷管理与分析；管理页面 `/assets/assessment/questionnaires.html`，本人填写页面 `/assets/assessment/questionnaire-answer.html`。

## 已实现
- 沿参考图组织矿山横幅、六项统计、问卷/题库/量表/统计/历史五个标签、问卷表格及批量操作、右侧结果总览、风险检出率趋势和分类TOP5。保留现有平台导航和菜单。
- 问卷草稿创建与版本校验编辑；单选、多选、文本、数字题，必答、选项配置分值、增删及上下移动；从题库加入时复制快照。草稿删除、已发布问卷复制、版本预览和原问卷归档均有实际接口。
- 题库独立维护；量表管理维护名称、来源、版本和说明，不伪装为已验证的临床算法。关联元数据随发布版本冻结。
- 每批1至20份问卷，发放至1至1000名当前单位可访问职工，支持截止日期。发布冻结题目、选项分值和量表资料；发布过的内容不可直接覆盖，需复制为新草稿。重复发布同一版本给同一职工不重复创建记录。
- 发布只创建系统内发放记录，不发送短信、微信或其他通知。本人填写链接必须本人账号登录；个人接口仅能读取和提交自己的发放记录，支持列出本人收到的问卷。
- 医生可在核对原始答卷及授权后代录。服务器校验必答、题目标识、选项归属和格式，从冻结版本汇总单/多选配置分值，忽略客户端传入的分数；文本/数字原值不自动计分。每条发放记录只能提交一份原答卷，不覆盖原回答。
- 答卷专业确认/退回、风险等级及审核依据保存于独立问卷记录，不修改身体档案或健康风险台账。原始分数不自动转为临床风险或诊断。
- 批量归档使用事务，任一版本或状态不符合时整批不部分提交。历史发布内容、已提交答卷和审核记录保留。
- 问卷清单、题库/量表资料、回收记录CSV导出；问卷预览、发放对象、答卷及逐题分析。逐题统计明确只使用当前展示的最近200条发放中的有效回收，不将其冒充全量样本；历史版本列表限最近200个。
- 心理健康评估页的问卷完成率已读取心理/压力/睡眠类别的真实发放与有效回收，没有分母时显示“— 暂无发放”。不会把普通问卷结果自动登记为心理测评或确定心理疾病。

## 统计口径
- 问卷总数为未删除问卷；进行中为已发布状态，已完成为已归档状态；累计填写为已提交答卷数。
- 有效回收率 = 已提交且未被标记无效的答卷 / 实际系统内发放数。未审核但通过结构校验的答卷可计有效回收，风险统计仍不计未审核答卷。
- 风险检出率 = 专业确认中/高/极高风险答卷 / 已专业确认答卷；按提交月份汇总。无已审核分母时显示“—”，不生成示例比例、趋势或临床阈值。
- 右侧图表为当前单位汇总，列表搜索只筛选列表。结果总览为近6个月已审核答卷，趋势可切换6/12个月。
- 当前业务库没有实际创建问卷，新增模块六张表记录均为0；页面保持真实空状态。

## 文件
- `yihe-admin/src/main/resources/templates/index.html`：新增入口及页面辅助层兼容。
- `yihe-admin/src/main/resources/static/assets/assessment/questionnaires.html`
- `yihe-admin/src/main/resources/static/assets/assessment/questionnaires.css`
- `yihe-admin/src/main/resources/static/assets/assessment/questionnaires.js`
- `yihe-admin/src/main/resources/static/assets/assessment/questionnaire-answer.html`
- `yihe-admin/src/main/resources/static/assets/assessment/questionnaire-answer.js`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/QuestionnaireService.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/QuestionnaireController.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/QuestionnaireMobileController.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/PsychologicalAssessmentService.java`：接入真实问卷完成率。
- `yihe-admin/src/main/resources/static/assets/assessment/psychological.{html,js}`：完成率数据源说明与缓存版本。
- `database/migrations/V024__a_questionnaire_management.sql`：问卷、发布版本、发放、答卷、资料库、请求幂等六张表，已在本地业务库及隔离预览库创建。
- `tests/ehr/java/QuestionnaireChecks.java`：预览库事务回滚检查。

## 接口
管理端基路径 `/ehr/api/questionnaires`，沿用登录、CSRF、单位/数据域限制，并要求授权医生。
- GET/POST 空路径：问卷列表与全单位统计 / 保存草稿。
- GET/POST `/assets`：题库或量表资料读取/维护。
- GET `/history`：冻结发布版本。
- POST `/publish`：带问卷版本和对象列表的发放。
- POST `/batch-actions`：批量归档。
- GET `/{id}`：问卷、发布版本、最近200条发放与答卷。
- POST `/{id}/actions`：复制、归档或删除未发布草稿。
- GET `/assignments/{id}`、POST `/assignments/{id}/submit`：授权医生核对后代录。
- POST `/responses/{id}/review`：专业审核，需版本及明确依据。

个人端使用现有 `/ehr/mobile/session` 本人登录，令牌只保存在当前浏览器会话内；服务端使用现有本人档案绑定校验。
- GET `/ehr/mobile/questionnaires`：本人收到的问卷。
- GET `/ehr/mobile/questionnaires/{assignment}`：本人发放内容及提交状态。
- POST `/ehr/mobile/questionnaires/{assignment}/submit`：明确知悉用途后提交本人答卷。

## 验证（2026-11-24）
- Java 8定向编译、JS语法和git diff --check通过。环境没有Maven命令，未声称完成全仓Maven构建。
- 新模块39项后端检查通过，覆盖权限隔离、冻结版本、重复发放、本人边界、代录来源、必答/选项校验、服务器计分、不覆盖原答卷、专业审核、真实回收分母、心理完成率联动、归档保留及事务回滚。
- 心理27项、慢病44项、职业36项、综合29项、AI体检33项回归通过；共208项后端及回归检查。均使用隔离预览库夹具并事务回滚。
- Chrome/Playwright验证六项统计、五标签、草稿题型/重排、题库和量表表单、带版本的发布、批量归档、原件代录、专业审核、逐题分析、CSV导出；独立本人页面登录/填写也用浏览器内API夹具验证。所有业务POST被拦截，不保存实际问卷、答卷或发放记录。
- 1920/1366/390宽度、本人手机填写页及现有侧栏集成通过，无页面JS异常；心理评估页面联动回归通过。
- WAR已部署8080，未替换的WAR条目字节保持原样。业务档案、体检报告、文件、账号、任务和配置摘要未变，原有匹配审核发布数量未变。新增六表均为0条实际业务记录。
- 静态设计检测器因HTML解析依赖缺失降级，只作辅助，不据此声称完成计算对比度检测。

## 实际使用边界
没有默认填入SCL-90、PSQI、PHQ-9等受验证量表的题目或临床阈值。使用正式量表时需提供合法题目、适用版本及准确评分规则，由专业人员核实。当前配置计分提供可追溯的原始汇总，风险等级须专业审核。本人正式账号的真实问卷回收尚未在本次验证中执行；不会自动通知职工填写。本模块不依赖外部AI完成计分。
