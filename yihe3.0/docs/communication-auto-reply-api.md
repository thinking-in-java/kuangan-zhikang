# 沟通与服务中心：自动回复

本模块为职工服务会话提供固定回复、依据知识的 AI 回复、人工服务分流。原聊天管理、个人数字健康师会话均保留。本页不会自动改写健康档案、生成处方或发布体检结论。

## 页面与操作

入口：沟通与服务中心 → 自动回复；直接地址 `/manage/communication/auto-replies`。

1. 首次载入内置 24 条规则、6 组知识依据；此操作幂等，不覆盖已有草稿。
2. 编辑标题、触发关键词、分类、回复方式、匹配方式、优先级、判断边界、内容、变量及关联知识，保存为草稿。
3. 在测试窗口选择草稿或已发布版本。默认仅匹配规则；勾选“调用模型测试”才调用模型生成知识回答。所有测试不发送真实消息，不计入生产命中统计。
4. 发布配置生成不可变版本，新消息使用新版本；编辑草稿不影响线上版本。回滚会创建新的发布版本，保留历史。
5. 支持批量启停、删除、复制、JSON 导入及导出。导入强制成为草稿并分配新 ID，不覆盖现有规则。每次导入最多 100 条，总计最多 300 条。

默认内容覆盖报告查询、待审核状态、上传与识别失败、姓名日期错误、复查准备、健康方案、人工联系、小程序绑定、验证码、个人资料更正、隐私、附件、职业接触史、职业体检常识、异常与用药分流、通用活动饮食、随访及评价。企业实际预约方式、收费、联系方式、服务时间须自行核实，未编造此类信息。

知识来源：

- [中国疾控中心职业卫生所：2024 年职业病防治法宣传周发布会](https://niohp.chinacdc.cn/sndt/202404/t20240428_277186.htm)，用于职业健康检查常识、检查与诊断边界。
- [福建省卫健委：健康素养相关知识](https://wjw.fujian.gov.cn/ztzl/jkjy/jkzgxd/202411/t20241104_6557671.htm)，用于通用活动与膳食知识。
- 平台操作依据当前项目流程编写；企业服务规定占位依据默认停用，须填写并核实后启用。当前知识依据服务于本页规则，不冒充尚未开发的独立“服务知识库”管理模块。

## 管理 API

基础地址 `/ehr/api/communication/auto-replies`。后台登录 Cookie；非 GET 请求须携带 `/ehr/api/context` 返回的 `X-CSRF-Token`。所有查询和操作按登录单位及 `data_mode` 隔离，小程序账号无配置权限。响应沿用 `{ "code": 0, "data": ... }`；422 参数错误、409 草稿版本冲突、403 权限错误、503 模型不可用。

| 方法 | 路径 | 内容 |
|---|---|---|
| GET | 基础地址 | 草稿、草稿版本、当前发布版本、分类、真实处理统计 |
| POST | /initialize | 幂等初始化默认规则 |
| POST | /rules | `{version, rule}` 新增草稿规则 |
| PUT | /rules/{id} | `{version, rule}` 修改规则 |
| POST | /batch | `{version, ids:[], action:"enable/disable/delete/copy"}` |
| POST | /import | `{version, rules:[]}` 全部校验后统一导入 |
| PUT | /settings | `{version, enabled:boolean, knowledge:[]}` 更新总开关及知识依据草稿 |
| POST | /publish | `{version}` 发布快照 |
| GET | /releases | 最近 30 个发布版本 |
| POST | /rollback | `{version, releaseId}` 回滚并产生新版本 |
| POST | /test | `{question, mode:"draft/published", generate:false}` 无副作用测试 |

`version` 必须使用最新 GET 返回值，防止多人操作覆盖。`rule` 示例：

```json
{
  "title": "体检报告在哪里查看？",
  "keywords": ["报告在哪里", "报告查看"],
  "type": "fixed",
  "category": "report",
  "boundary": "general",
  "reply": "您好{{employee_name}}，请在本人小程序的体检报告页面查看。待审核内容须经人工核对。",
  "state": "enabled",
  "match": "any",
  "priority": 80,
  "knowledgeIds": ["platform"]
}
```

- `type`：`fixed` 固定、`ai` 知识生成、`handoff` 人工。
- `category`：`report/abnormal/review/plan/contact/miniapp/occupational/health`。
- `boundary`：`general/doctor/human`；后两项强制转人工。
- `state`：`draft/enabled/disabled`；只匹配已发布快照中启用的规则。
- `match`：`any` 包含任意关键词、`all` 包含全部、`exact` 与某个触发句完全匹配。关键词忽略英文大小写，每个 2～60 字，最多 20 个。
- 支持变量仅 `{{employee_name}}`、`{{employee_no}}`、`{{company_name}}`，由后台读取本人资料，不接受自定义执行表达式。
- `knowledge` 每项为 `{id,title,content,source,url,enabled}`，最多 30 项；出处链接只能 HTTPS。AI 规则至少关联一个启用的知识依据；不在线抓取外链，不执行知识文本中的指令。

## 职工会话接入

沿用现有小程序职工会话发送、消息拉取、附件接口。员工身份取已绑定登录会话，不接受前端传任意职工 ID。

发布且开启后：职工发消息 → 同一事务写入回复事件 → 后台工作器领取 → 匹配发布规则 → 固定回复或知识生成 → 再检查人工接管、会话状态、发布版本及最新消息序号 → 保存 `sender_role=auto_reply` 消息。原“职工会话”与“聊天记录”均显示此角色。

员工可主动 `POST /ehr/miniapp/communication/conversations/{id}/handoff` 请求人工服务，使用原小程序访问令牌与绑定身份，不需要传姓名或工号。继续用现有消息轮询获取回复。

医学问题或未命中可靠规则转人工，保留待回复；人工接管后不再自动发送。模型失败保留待回复，不伪造成功。新消息、人工接管或版本变化会使旧生成结果失效。自动回复不读取个人报告附件，不替代医生解读；AI 仅接收必要问题文本及关联知识，移除已知姓名、工号、手机号及基本敏感标识。不是对所有自由文本的无遗漏脱敏保证。

后台每 2 秒领取一条持久任务，不在上传或发送事务内调用模型；超过 3 分钟的运行任务转失败并留给人工，不无限重试。模型沿用数字健康师 Responses 配置；未配置时固定回复与转人工正常，AI 生成明确提示不可用。

## 数据与统计

增量迁移 `V018__a_service_auto_reply.sql`，通过 `python database/communication_auto_migration.py` 执行，带迁移校验和、数据库锁和消息表定义快照。新增表全部为 `a_` 前缀：

- `a_service_reply_configs`：单位/模式唯一草稿、版本号、当前生效版本指针。
- `a_service_reply_releases`：不可变发布配置、发布序号、操作账号与时间。
- `a_service_reply_events`：触发消息、命中规则、快照版本、回复类型、判断边界、处理状态、结果消息、失败原因和处理时间。触发消息唯一，发送重试不重复入队。

状态 `queued/running/replied/handoff/skipped/failed`。满意度来自真实会话反馈，无评价显示“暂无评价”；图表与统计无数据显示零或横线，不放虚构命中量。近 7 日命中率＝实际自动完成回复数÷（回复完成＋转人工＋失败），不计测试和过期跳过事件。单规则命中统计为事件数，会话满意度按相关事件关联展示，不代表该条回复独立获评。

## 验证

`python tests/ehr/test-auto-reply.py`：真实数据库、随机隔离 synthetic 单位，事务回滚全部测试数据，替身模型不请求外部服务。

`python tests/ehr/test-auto-reply-layout.py`：Chrome 四种屏宽，固定的独立假数据；检查布局、CRUD 相关操作、导入、发布回滚和测试窗口。截图位于工作目录之外的 `.tools/runtime/auto-reply-ui`。

`python tests/ehr/test-communication.py`：原会话与历史记录功能回归。
