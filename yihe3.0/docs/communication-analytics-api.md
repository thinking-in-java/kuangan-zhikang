# 沟通统计分析（页面五）

页面：`GET /manage/communication/analytics`，导航：沟通与服务中心 → 沟通统计分析。

本模块只读，复用现有 `a_service_conversations`、`a_service_messages`、`a_service_conversation_details`、`a_service_reply_events`、`a_health_records` 和 `a_employee_roster_profiles`。不新增表、不修改消息或健康档案，不写入示例统计。

## API

- `GET /ehr/api/communication/analytics`：完整仪表盘数据与分页明细。
- `GET /ehr/api/communication/analytics/export`：导出全部筛选线程的 UTF-8 BOM CSV，最多10000条线程。字段包括姓名、工号、单位、会话编号、服务分类、区间首末时间、自动/人工回复数、首次/平均响应秒、接管次数、当前状态和评分。CSV单元格防公式注入。

需要现有后台登录态；小程序身份不能访问企业统计。范围及 real/synthetic 数据模式来自服务端身份，不接受客户端传入扩大权限。响应 `Cache-Control: private, no-store`。

### 查询参数（两个 API 相同）

| 参数 | 说明 |
| --- | --- |
| start / end | 北京时间日期 yyyy-MM-dd；默认近30天（含今天），最长93天，不允许未来日期 |
| unit | 单位显示名称精确匹配；只在当前授权范围内筛选；为空选所有授权单位 |
| category | 空 / health / report / review / plan / other |
| origin | 空 / miniapp / admin / unknown；依据线程首条消息角色推定，未专门采集渠道 |
| human | 空 / yes / no；筛选区间内是否有真实人工接管操作 |
| page / limit | 页码；每页默认20、最多100，导出忽略分页 |

响应结构：`{code:0,data:{start,end,previousStart,previousEnd,summary,previous,changes,days,weeks,origins,topics,hours,ratings,quality,issues,items,total,page,limit,units,categories,todayConversations,generatedAt}}`。

- `summary`：conversations、aiReplies、humanReplies、averageSeconds、responseSamples、pending、takeovers、handoffs、closed、rated、satisfied、satisfaction。
- `changes`：与前一个等长区间的百分比变化，前期为0或无样本返回null；满意度变化同样为相对百分比，不是百分点。
- `days`：每天活跃线程去重数、自动回复数、人工回复数、完整问答响应样本和平均秒数。
- `weeks`：按区间首条消息所在的7天分段，每个线程只归类一次，优先级：当前待回复、人工、仅自动、其他。
- `origins`：线程第一条消息 employee 归 miniapp，doctor/manager/ai_assisted/auto_reply 归 admin，否则 unknown。是入口代理指标，不伪造独立 Web/随访回执渠道。
- `topics`：职工消息中预设关键词的命中次数，最多10个主题；一条消息允许命中多个主题；不调用模型推断疾病。
- `hours`：24个北京时间时段，每小时去重线程；不同小时可重复。
- `ratings`：1至5星各自的真实线程数，无评价不计入。
- `quality`：resolved、within24h、handoffCompleted、handoffSamples、autoSuccess、autoSamples、aiAccuracy。aiAccuracy固定null，未采集人工准确性标注。
- `items`：当前分页线程，包含 id/recordId/name/code/unit/category/categoryName/status/pending/firstAt/latestAt/aiReplies/humanReplies/takeovers/firstResponse/responseSeconds/responses/score 等。不会返回原始消息正文。

## 统计口径

1. 当前系统每位职工一个会话线程。区间会话数是区间内有非系统消息的去重线程，不是轮次数；纯系统通知不计为会话。按日活跃数不可相加替代区间去重总数。今日卡是当前筛选中的今日活跃线程数。
2. `auto_reply` 作为自动回复；doctor/manager/ai_assisted（人工确认后发送）归人工回复，不重复计入AI。
3. 连续职工消息为一轮提问；从该轮首条职工消息到下一条非系统服务回复计算耗时。系统通知忽略。只统计区间内完整问答，跨边界和未回复轮次不记零秒。
4. 待回复及已关闭是查询时线程当前状态。满意度使用选中线程已有当前评分，4–5星占有效评分比例，不按消息重复评分。
5. 人工接管次数取角色system且包含系统接管文案的操作消息。自动转人工请求取 outcome=handoff 的执行事件，单列，避免混称。
6. 24小时内完结率：分母是当前已关闭线程；分子要求区间内可见最后一次结束服务操作，与线程创建时间间隔不超过24小时。这不是多次服务轮次的SLA，因为系统尚未采集独立服务轮次。
7. 转人工跟进率按区间内自动转人工事件之后可见人工回复的线程占比计算；自动回复成功率仅统计 replied/(replied+failed) 的执行事件，不等于内容正确率。
8. null表示没有可计算样本，不补0、不造满意度。前期比较也使用相同单位、分类、来源、接管筛选。数据查询为一致性只读事务。单个区间超过20万消息或事件时明确拒绝，要求缩小范围，不截断后给出错误总数。

明细点击查看详情，会把 conversationId、start、end、category 传给聊天记录页并直接选中该线程；原始消息仍按聊天记录页完整历史展示。

## 验证

- `python tests/ehr/test-analytics.py`：真实MySQL、独立synthetic范围、全部写入回滚；验证计数、连续消息响应、角色分类、评分分母、手工/自动转人工、权限/模式隔离、各项筛选、分页/导出/CSV保护等。
- `python tests/ehr/test-analytics-layout.py`：真实无头Chrome、独立浏览器fixture、四种屏宽；验证布局、筛选、分页、空数据、错误状态、导出、口径弹窗和钻取地址。fixture不进入业务数据库。
