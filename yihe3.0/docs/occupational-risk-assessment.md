# 职业健康风险评估

入口：健康评估中心 → 职业健康风险评估；页面 `/assets/assessment/occupational-risk.html`。

## 实现范围
- 按参考图组织六张运行统计卡、五类职业危害接触分布、风险等级环图、重点人群、筛选表格、批量操作及分页。保留原有系统导航，不添加重复页面横幅。
- 授权范围中的全部职工按年度展示；支持单位、岗位、风险、评估状态、姓名/工号筛选、接触因素与多重危害人群下钻、跨页勾选、每页条数及页码跳转。URL保留筛选状态，每15秒在可见且未打开对话框时刷新。
- 每批1至20名职工，持久化队列，独立2线程并发处理；排队任务可查看、取消；20分钟租约超时记为失败，不虚构完成状态。
- 输入包含岗位接触史、职业健康资料、结构化报告原值、参考范围、报告日期和原件页码。使用现有独立数字员工模型配置；新增职业风险专用提示，不读取OCR模型配置，不影响OCR并发。
- AI仅输出分析线索，分项及综合风险由医生核验身份、原始报告和接触资料后明确确认。所有关联报告须先完成审核归档；风险写入现有风险台账并关联报告证据。不会自动诊断、修改原报告、发送通知或执行干预。
- 规则配置管理五类接触关键词及辅助分析要求，按单位和数据域保存，带乐观版本控制。关键词只用于接触人群筛选，不能代替暴露剂量、检测浓度或临床风险模型。
- 保存来源快照、报告版本清单、规则版本和审核事件。来源或规则变化后旧结果保留追溯、不再计入当前已确认风险；已取消、来源变化、权限撤销的晚到模型结果不得回写。
- 支持单人/批量HTML评估报告和当前查询全量CSV导出（上限5000条），含审核状态与来源；没有实际评估任务时不生成伪报告。

## 文件
- `yihe-admin/src/main/resources/static/assets/assessment/occupational-risk.html`
- `yihe-admin/src/main/resources/static/assets/assessment/occupational-risk.css`
- `yihe-admin/src/main/resources/static/assets/assessment/occupational-risk.js`
- `yihe-admin/src/main/resources/templates/index.html`：新增独立健康评估中心子菜单。
- `yihe-framework/src/main/java/com/yihe/framework/ehr/OccupationalRiskService.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/OccupationalRiskWorker.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/OccupationalRiskController.java`
- `yihe-framework/src/main/java/com/yihe/framework/ehr/DigitalWorkerModelClient.java`：新增专用任务策略，原任务分支保留。
- `database/migrations/V021__a_occupational_risk_assessment.sql`：规则、任务、审核事件3张表，已在本地业务库及预览库创建。
- `tests/ehr/java/OccupationalRiskChecks.java`：隔离预览库事务回滚检查。

## 接口
基路径 `/ehr/api/assessment/occupational`，沿用登录、单位/数据域权限与CSRF校验。
- GET 空路径：年度职工列表、统计、接触分布、筛选项、执行器状态。
- GET/POST `/rules`：读取/保存规则及版本。
- GET `/tasks`：当前单位最近100个任务。
- POST `/batches`：创建职业风险辅助分析批次。
- GET `/records/{record}?year=2026&task=可选历史任务`：当前来源、指定任务、来源变化及历史。
- POST `/tasks/{task}`：医生审核或取消任务，要求行版本及操作幂等键。

## 验证（2026-11-24）
- Java 8定向编译、JS语法检查、git diff --check通过。环境无Maven命令，未运行全仓Maven构建。
- 新模块36项后端检查通过；综合健康评估29项和AI体检评估33项回归检查通过，共98项。预览库夹具与替身模型验证，事务全部回滚。
- Chrome/Playwright验证：真实898人授权数据、六项统计、五类分布、重点人群、搜索重置、分页和跳页、人群下钻、规则/模型配置、任务、批量表单、医生审核分项依据、来源链接、历史版本、报告及CSV导出、取消任务。
- 1920/1366/390宽度及现有侧栏集成验证通过，无页面JS异常。浏览器所有业务POST被拦截，未实际调用外部模型。
- WAR已部署到8080（监听所有网卡），打包校验其他条目字节保持。生产业务资料摘要及原有匹配、审核、发布数量未变化；新模块任务、配置和事件记录均为0，未为测试创建真实业务结果。
- 设计检测器因本地HTML解析依赖缺失降级，仅作辅助；未据此声称完成计算对比度检查。

## 使用边界
独立模型配置检测为可用，执行器就绪。本次未实际调用外部付费模型；实际分析由医生明确发起。当前898人无该新模块的医生确认风险结果，页面显示未评估。接触人群计数来自现有接触史，不代表已测定高剂量暴露，也不代表已确诊职业病。未接入经验证的自动风险分级算法，始终保留医生确认流程。
