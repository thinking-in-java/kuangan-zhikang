# 慢性病风险评估

入口：健康评估中心 → 慢性病风险评估；页面 `/assets/assessment/chronic-risk.html`。沿参考图实现矿山横幅、五项统计、五类风险概览、人员列表、风险分布、12个月趋势、TOP5和评估依据；原有平台导航及其他页面保留。

## 功能与数据
- 高血压、糖尿病、血脂异常、肥胖、心脑血管五类。分项总人数、高/中风险及综合风险只统计来源、规则仍有效的医生确认结果；未评估不自动当作低风险。
- 复用授权档案、原始报告指标、单位、参考范围、页码、年龄性别、岗位及有来源病史。列表显示原报告异常标记，不将单次异常当确诊。真实单位和班组分别筛选，单位列展示单位字段。
- 支持姓名/工号、单位、班组、岗位、风险类型、等级、状态、12/24个月或全部时间；分类卡片与分布图可下钻，支持跨页选择、页码跳转、每页条数及URL筛选恢复。快速切换条件时只应用最后一次请求响应，避免旧数据覆盖新筛选。
- 默认展示每人最新任务，可来自前一评估年度。历史曲线按每个月末最后一条医生确认记录统计高及极高风险人数；历史快照不冒充当前规则结果。没有上月可比基数时不制造涨跌百分比。
- “查看趋势”读取真实报告日期、同名同单位原值；空缺不插值、不把不同单位混为同一指标。支持血压、血糖、BMI、总胆固醇选择和原文链接。
- 每批1至20人，独立2线程持久化队列；复用独立数字员工AI配置，使用慢病专用提示规则，未调用OCR模型。模型不能自动确认风险或生成确定发病概率/诊断。
- 医生审核须完成身份、原件及病史依据核验，所有关联报告须先归档，每个已核定分项需明确依据。确认后写现有风险台账与报告来源，不改原始体检报告。
- 保存来源快照、规则、历史和审核事件；来源或规则变化、权限撤销、任务取消后的晚到结果不得回写。历史可追溯，任务可查看与取消。
- 支持单人/批量HTML报告和当前查询CSV导出，保留未审核标识及出处。
- “创建干预方案”复用数字员工现有派活接口，为方案小谷创建待处理的方案准备任务；需明确提交，不自动执行干预、改档或发送通知。

## 代码与接口
- `yihe-admin/src/main/resources/templates/index.html`：健康评估中心增加子菜单。
- `yihe-admin/src/main/resources/static/assets/assessment/chronic-risk.html`
- `yihe-admin/src/main/resources/static/assets/assessment/chronic-risk.css`
- `yihe-admin/src/main/resources/static/assets/assessment/chronic-risk.js`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/ChronicRiskService.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/ChronicRiskWorker.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/ChronicRiskController.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/DigitalWorkerModelClient.java`：新增慢病专用任务分支，其他分支保留。
- `database/migrations/V022__a_chronic_risk_assessment.sql`：独立规则、任务、事件3张表，已创建于本地业务库及隔离预览库。
- `tests/ehr/java/ChronicRiskChecks.java`：隔离预览库事务回滚检查。

基路径 `/ehr/api/assessment/chronic`，沿用登录、CSRF、单位及数据域权限。
- GET 空路径：列表、五类概览、统计、历史月份快照、TOP5、筛选项、执行器状态。
- GET/POST `/rules`：指标关联与分析要求及版本。
- GET `/tasks`：当前单位最近100个任务。
- POST `/batches`：明确年度与职工标识，创建分析队列。
- GET `/records/{record}?year=2026&task=可选历史任务`：当前来源、任务与同年度历史。
- POST `/tasks/{task}`：带版本和幂等键的医生审核或取消。
- 干预方案准备沿用 POST `/ehr/api/digital-worker-tasks`，不自动请求模型。

## 验证（2026-11-24）
- Java 8定向编译、JS语法及git diff --check通过。当前环境无Maven命令，未声称完成全仓Maven构建。
- 本模块44项后端检查通过；职业风险36项、综合评估29项、AI体检33项回归通过，共142项。使用隔离预览库夹具和替身模型，事务全部回滚。
- Chrome/Playwright验证真实898人数据、五项统计与五类卡片、12个月趋势、筛选下钻、分页跳转、原始指标趋势、任务、批量表单、干预派活表单、分项医生审核、历史、HTML/CSV导出及取消。
- 单独的浏览器内夹具覆盖已审核高风险类别和非空TOP5渲染；不存入数据库。所有业务POST拦截验证，未实际调用外部模型或创建真实干预任务。
- 1920/1366/390宽度及侧栏集成验证通过，无页面JS异常；修复了图表销毁后窗口缩放的异常。
- 新WAR已部署8080，打包校验未替换条目字节保持原样。生产档案、报告、文件、账号、任务、配置摘要与原有匹配审核发布数量均未变化；新增3张业务表记录数均为0。
- 设计静态检测器因本地HTML解析依赖缺失降级，不能据此声称完成计算对比度检测。

## 待实际使用确认
独立AI配置检测为可用，执行器就绪。本次未调用外部付费模型；实际分析需由医生明确发起。当前新模块尚无真实医生确认结果，五类分项和高风险分布为0，待评估898人。未引入未经验证的自动慢病概率或分级算法，所有风险结论需医生核定。
