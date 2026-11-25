# 综合健康评估

入口：健康评估中心 → 综合健康评估。页面 `/assets/assessment/comprehensive.html`，可携带 `record`、`analysis` 参数；`view=list` 进入授权职工列表。

## 本次实现
- 沿参考图布局实现五块个人摘要、10 个栏目及基础资料、历史趋势、慢病风险、职业接触风险、AI 分析、医生审核和后续建议。
- 原有平台导航保留，不增加重复横幅；列表搜索、翻页、返回保留页码。
- 复用授权档案和当前版本历史报告；原始检查值、单位、参考范围、页码及原件链接可追溯。同名、同单位数值才比较，缺失年份不插值，默认按最近报告年份展示近三年。
- 独立数字员工 AI 配置复用现有服务层，不读取 OCR 配置；综合分析使用单独的任务提示规则；没有实际请求不会显示分析完成。
- 医生审核要求所有来源报告已归档、身份和原件核验、明确风险依据。健康分数为医生可选人工评分，必须附规则依据，不把完整度当健康评分。
- 确认风险写入原有风险台账及报告来源关联；分项风险、人工健康评分和建议另存。发布须显式确认，不修改报告原件，不发通知，不自动执行干预。
- 保留来源快照、历史版本及请求幂等；来源变化、撤权、取消的晚到结果不得回写。并发上限2份综合分析，与原有OCR并发配置独立。

## 修改文件
- `yihe-admin/src/main/resources/templates/index.html`：独立侧栏入口及页面辅助层兼容。
- `yihe-admin/src/main/resources/static/assets/assessment/comprehensive.{html,css,js}`：页面与交互。
- `yihe-framework/src/main/java/com/yihe/framework/ehr/ComprehensiveHealth{Service,Runner,Controller}.java`：数据聚合、分析流程、审核发布接口。
- `yihe-framework/src/main/java/com/yihe/framework/ehr/DigitalWorkerModelClient.java`：综合评估专用提示规则，原有任务规则保留。
- `database/migrations/V020__a_comprehensive_health.sql`：新增分析、审核两张表。
- `tests/ehr/java/ComprehensiveHealthChecks.java`：隔离预览库的事务回滚检查。

## 接口
- GET `/ehr/api/assessment/comprehensive/{record}`：授权资料、最新/指定分析、审核和历史。
- POST `/{record}/generate`：按 sourceDigest 发起独立AI辅助分析。
- POST `/{record}/review`：医生审核及明确分项依据，可选人工评分。
- POST `/{record}/publish/{review}`：明确发布当前最新已审核版本。
- POST `/{record}/cancel/{analysis}`：带版本和说明取消。

写操作沿用认证、单位/数据域权限和 CSRF 校验，不能匿名访问。

## 验证（2026-11-24）
- Java 8 定向编译通过；JS 语法检查及 git diff --check 通过。当前环境没有 Maven 命令，未声称完成全仓 Maven 构建。
- 新模块29项后端检查通过；原AI体检评估33项回归检查通过。测试使用预览库合成夹具及替身模型，全部事务回滚。
- Chrome/Playwright 验证真实授权资料读取、10个栏目、来源、时间范围、列表翻页及返回、导出、审核/发布表单、1920/1366/390宽度及侧栏集成，无页面JS异常。写入和模型请求在浏览器测试中拦截，不创建真实评估。
- 新WAR已部署到8080，其他WAR条目校验保持原样。生产档案、报告、文件、任务、账号、配置摘要及匹配审核发布计数均未变化；新增分析与审核表实际记录为0。
- 静态设计检测器因HTML解析依赖缺失降级，仅作辅助，未据此声称完成计算对比度检测。

## 数据边界
- 独立模型配置已检测可用；本次验证未实际调用外部付费模型，端到端输出质量需以用户明确发起的实际分析为准。
- 生活习惯、病史、家族史及分项风险没有来源时显示未提供/未评估。未实现自动诊断或未经验证的健康评分算法。
- 健康报告导出为包含来源与审核状态的独立HTML文档；发布后仅记录发布状态，不自动发送给职工。
