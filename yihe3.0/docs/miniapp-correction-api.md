# 绑定前职工资料更正申请 API V1

适用场景：微信用户已经通过姓名、工号和个人专属验证码核验，在个人信息确认页发现班组、岗位、电话等不准确。小程序由其他开发者编写，本项目提供以下后端接口。复用 [微信登录和绑定](miniapp-wechat-binding-api.md) 的 Bearer 会话、返回格式及错误状态。

## 页面与状态处理

确认页显示“信息正确，确认绑定”和“信息有误，申请更正”。姓名、工号不匹配、显示的人并非本人，应停止绑定并联系企业管理员；不能用更正申请冒领另一人的身份。申请页填写更正字段和原因，提交后显示等待审核；审核通过或驳回后，重新验证并展示当前后台资料，用户再次勾选确认才能绑定。

`GET /ehr/miniapp/me` 增加状态：`bindingState=CORRECTION_PENDING`、`nextAction=WAIT_FOR_REVIEW`。等待页可调用申请列表查询处理结果。申请未处理时，原确认凭据失效，相关职工不能绑定，申请人不能改绑其他职工。申请通过**不自动绑定**。

## 1. 提交更正申请

`POST /ehr/miniapp/binding/correction-requests`

```json
{
  "confirmationToken":"同一微信用户刚才身份验证得到的有效确认凭据",
  "requestKey":"每次申请生成的UUID，网络重试沿用同一个值",
  "reason":"已调整到掘进二队，后台班组与岗位尚未更新",
  "changes":{
    "team":"掘进二队",
    "position":"掘进工",
    "phone":"13900001111"
  }
}
```

服务端根据确认凭据确定职工，不接受客户端指定目标。`requestKey` 使用UUID格式，原因1–600字符；只提交需要更正的字段。白名单如下，其他字段返回422：

| changes字段 | 含义 | 校验 |
|---|---|---|
| organization | 单位显示名称 | 必填，最多255字符；不会更改企业授权范围scope_code |
| team | 班组 | 字符串，最多100字符，可明确清空 |
| position | 岗位 / 工种 | 字符串，最多100字符，可明确清空 |
| phone | 联系电话 | 最多30字符，数字及常见电话符号，可明确清空 |
| gender | 性别 | 男、女、未填写 |
| birthDate | 出生日期 | yyyy-MM-dd，不晚于今天 |

姓名、工号、员工UUID、档案UUID、微信身份、单位授权编码不在白名单中。无变化的申请会返回422。原确认凭据须属于当前用户、未消费、未过期，原个人验证码仍有效且未撤销；后台资料版本须与所核对的一致。

成功HTTP200，`code=0`，`data`包含：

```json
{
  "id":"更正申请UUID",
  "recordId":"服务端确认的档案UUID",
  "status":"pending",
  "rowVersion":1,
  "profileRowVersion":3,
  "before":{"name":"企业登记姓名","employeeNo":"职工工号","team":"原班组","phoneMasked":"138****0000"},
  "changes":{"team":"掘进二队","position":"掘进工","phone":"13900001111"},
  "reason":"更正原因",
  "createdAt":"UTC时间",
  "reviewedAt":null,
  "reviewNote":null,
  "after":null,
  "nextAction":"WAIT_FOR_REVIEW"
}
```

`before`实际还包括原确认页的性别、出生日期、单位、岗位、employeeId、recordId和rowVersion，原手机号只保留脱敏快照。`changes.phone`是用户自己填写的新号码。相同用户、相同幂等键及相同内容重试返回同一申请；用同一个键提交不同内容返回409。一用户、一职工最多一份待审申请，防止重复和争抢。提交只保存申请，不改正式名册。

## 2. 查询本人的申请

`GET /ehr/miniapp/binding/correction-requests?page=1&limit=10`

需要当前微信Bearer令牌。返回 `items,total,page,limit`，每项结构同上。只返回该微信用户提交过的申请，不提供按他人工号或UUID查询入口。limit为1–100。

`status`为pending、approved、rejected。处理完成时返回审核时间、审核说明，`nextAction=VERIFY_EMPLOYEE_AGAIN`；通过时`after`为更新后的脱敏资料快照，驳回时为null。再次使用仍有效的个人验证码验证身份；如果验证码已过期或被撤销，需要后台重新签发。

## 3. 后台查看申请队列

`GET /ehr/api/correction-requests?status=pending&page=1&limit=10&recordId=可选档案UUID`

后台医生Cookie鉴权；status可为pending、approved、rejected、all。不传status时查询全部。按当前企业档案池和数据模式隔离，返回 `items,total,page,limit`。页面在“档案信息管理”和“审核与归档”的操作栏增加“资料更正申请”；每位职工行内也可查看该职工的更正记录。

## 4. 后台人工审核

`POST /ehr/api/correction-requests/{id}/review`

需要医生Cookie、`X-CSRF-Token`，不能由小程序用户调用。

```json
{
  "decision":"approve",
  "note":"已向企业核实该职工的调岗信息",
  "requestKey":"本次审核UUID，重试沿用",
  "rowVersion":1
}
```

decision为approve或reject；note为1–600字符。审核通过把**申请中的白名单变更**合并到企业名册，保留姓名、工号、授权范围及旧账号关联；更新名册版本和完整度，记录申请前后快照与审核日志。不会更改原体检报告快照、医生医学结论或健康风险。

申请后企业名册若又被后台编辑，approve返回409，不覆盖新资料。医生核对后可reject，告知职工重新验证和申请。驳回只记录原因，不更新名册。重复审核只有同一医生、同一审核键、相同结论与说明才幂等返回结果，不能覆盖已处理申请。所有写入在同一事务完成，失败整体回滚。

有待审申请的职工暂不能删除，需先审核处理，避免申请失去处理对象。

## 数据库与联调

新增V005表 `a_miniapp_correction_requests`，保存原资料快照、变更值、原因、申请人、审核人、状态、前后版本及幂等键；所有时间按UTC约定。无明文微信令牌或确认凭据字段。完整字段见 [逐字段字典](ehr-data-dictionary.html)。

接口错误：401会话无效；403非医生/CSRF或未绑定业务访问；404队列记录不存在或无权限；409已有待审申请、版本冲突、已处理或原验证码撤销；422字段或确认凭据不合法；429更正申请每用户每15分钟最多8次，失败也计数。

真实微信参数仍待配置；自动验证仅使用synthetic隔离职工和本机微信契约服务，不代替真实小程序登录验收。
