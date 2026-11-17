> OCR接入已升级为持久队列主动领取模式，详见 [A端上传和工作服务接入](ocr-worker-api.md)。原文中的submit-url主动推送配置不再作为新上传入队的前提；新B端统一使用`/ehr/worker`，旧`/ehr/integration`仅保留兼容。

# 健康档案业务 API 与接入说明

本次实现沿用当前项目的 Spring Boot、Shiro、JDBC、MySQL 和现有页面。数据库为 `192.168.1.109:3306/shayu_3.0`；数据库密码继续从项目环境配置读取，不在接口文档中复制。

**微信小程序新接入请使用 [微信登录与职工确认绑定 API](miniapp-wechat-binding-api.md)**，附 [Postman 集合](miniapp-wechat-binding.postman_collection.json)。下文 `/ehr/mobile` 是早期旧账号密码接入方式，继续保留兼容，不能代替新微信身份校验流程。

## 已实现的业务范围

- 智能档案页：授权职工资料、已发布的个人报告、原始指标、原件、职业资料与处理历史。新修订草稿不会遮掉之前的归档报告。
- 档案统计分析：按当前组织和数据模式实时汇总人数、业务状态、已归档占比、风险、来源、班组、年龄和建档趋势。未核定风险明确显示“未评估”，不按血压等阈值擅自生成风险诊断。
- 档案信息管理：新增、修改、软删除、组合查询、分页、CSV 导入导出、报告上传、人工补录与报告版本维护。
- 审核与归档：提交、医生确认、退回、驳回、正式归档及批量操作；每个动作留记录。
- 小程序：独立登录会话、本人档案、本人报告上传、报告查询、原件下载与退出登录。
- OCR：仅实现外部服务提交、受保护原件读取、候选回调接收与幂等校验，不包含 OCR 引擎。

小程序端应用源码未在本次范围中修改；下文给出了可供小程序调用的服务端接口和代码示例。OCR 真实服务地址、鉴权和既有报文未提供，因此当前使用下文的数据契约；收到既有服务契约后只需适配接入层。

## 访问与返回格式

后台入口：`http://localhost:18080/login`。健康档案静态入口在 `/assets/health-archive/`，包括 `index.html`、`statistics.html`、`management.html`、`review.html`、`detail.html?id=档案UUID`。

成功返回 HTTP 200 和 `{"code":0,"data":...}`。错误返回对应 HTTP 状态与 `{"code":状态码,"msg":"具体原因"}`。401 未登录或令牌失效；403 账号权限或 CSRF 校验失败；404 不存在或无权访问；409 内容、版本或重复操作冲突；413 文件/候选过大；422 输入不合法；503 工作服务鉴权尚未配置或队列暂忙。不会把失败伪装为成功。

后台沿用登录 Cookie。先 `GET /ehr/api/context` 获取 `csrfToken`，所有写操作增加 `X-CSRF-Token`。服务端从登录账号确定 `org:单位编码`，同时过滤 `real` / `synthetic`。后台仍按新档案池的组织编码隔离；旧名册可按企业已授权组织树汇入本企业档案池，原单位保留为来源，不开放其他档案池的跨单位访问。个人类型账号不能使用后台接口；医生类型 4 才能确认、退回、驳回与归档。没有从前端传参扩大单位范围的入口。

## 后台接口清单

| 方法 | 路径 | 用途 / 主要参数 |
|---|---|---|
| GET | `/ehr/api/context` | 当前账号、组织、模式、审核权限、CSRF、上传上限 |
| GET | `/ehr/api/records` | 分页查询；page、limit(1–100)、search、name、code、phone、org、team、job、status、risk、from、to |
| POST | `/ehr/api/records` | 新增授权职工及主档案，不创建旧系统登录账号 |
| GET | `/ehr/api/records/{id}` | 资料、当前候选 reports、各逻辑报告最新归档版本 publishedReports、缺项、历史 |
| PUT | `/ehr/api/records/{id}` | 编辑资料；必须带 rowVersion，旧版本冲突返回 409 |
| DELETE | `/ehr/api/records/{id}` | `{rowVersion,reason}`，软删除，保留报告和审计 |
| POST | `/ehr/api/records/import` | JSON 数组，每批 1–500 条；整批事务，任一失败全部回滚 |
| GET | `/ehr/api/records/export` | 同查询筛选，UTF-8 BOM CSV；超过 5000 条要求缩小范围；处理公式注入前缀 |
| GET | `/ehr/api/statistics` | 当前组织与模式实时统计 |
| POST | `/ehr/api/records/{id}/reports` | multipart 的 file、channel；必带 Idempotency-Key |
| GET | `/ehr/api/reports/{id}/{revision}` | 原值、文件、版本、证据关联、ETag、审核、发布状态 |
| PUT | `/ehr/api/reports/{id}/{revision}` | 补录候选；必须在 If-Match 中传最近读取到的 etag |
| POST | `/ehr/api/reports/{id}/{revision}/actions` | action、note、requestKey，医生确认还需 identityVerified=true、etag |
| POST | `/ehr/api/reports/{id}/{revision}/revisions` | `{note}` 新建修订，历史版本不覆盖 |
| POST | `/ehr/api/reports/batch-actions` | 1–100 个报告动作对象，整批事务 |
| GET | `/ehr/api/files/{fileId}` | 授权原件下载，不能直接拼静态文件 URL |
| POST | `/ehr/api/reports/{id}/{revision}/ocr` | 重新加入持久OCR队列，等待B端领取 |

职工新增/编辑请求示例：

```json
{
  "name":"示例职工", "code":"EMP-0001", "sex":"未填写",
  "birth":"1988-01-01", "phone":"", "org":"示例矿业",
  "team":"采煤一队", "job":"采煤工", "exposure":"企业授权填写的接触史",
  "legacyUserCode":"", "rowVersion":1
}
```

`legacyUserCode` 选填，须是当前企业授权组织树内的现有用户；报告 OCR 姓名不会自动覆盖授权花名册。软删除不会释放工号，以免重建造成历史分裂。接口返回 UUID、rowVersion 等数据。

上传接受 PDF、PNG、JPEG，每份最多 20 MiB；PDF 1–1000 页，图片最多 6000 万像素。依据实际文件头和可读性检查，不仅检查扩展名。文件写在私有目录，用随机名保存，并比对实际字节 SHA256。相同组织、模式和精确文件会去重；同文件不能错误绑定到另一职工。相同上传键重试不会创建重复逻辑报告。来源统计表示成功记录的原件上传来源，不把重复重试累计为新报告。

`channel` 为 `backend_file`、`phone_photo`；小程序端固定为 `miniapp`。拍照按钮使用移动浏览器原生相机选择器，电脑可选择图片。页面“小程序同步”刷新同一份数据库，不生成虚构同步记录，也不复制上传数据。

候选补录示例（所有医学值按字符串原文提交）：

```json
{
  "fullName":"示例职工", "examDate":"2026-11-14", "institution":"原文机构",
  "metadataPage":1, "conclusionPage":2,
  "formalConclusion":"原报告完整结论", "advice":"原报告建议",
  "items":[{"name":"血压", "value":"120/80", "unit":"mmHg", "referenceRange":"原文范围", "abnormal":"", "page":1, "itemCode":""}]
}
```

补录不会自动换算单位、推断正常、生成医生结论或修改独立风险评估。初期表单维护上述主要字段，保留未由此表单编辑的顶层 OCR 标准字段；所编辑的检查明细、结论和建议形成新的人工候选，原 OCR 回调收据与证据继续保留。已审核版本不能补录覆盖，须建立下一版本。

动作 `submit`、`approve`、`return`、`reject`、`archive`。送审前检查五项花名册资料（姓名、工号、单位、班组、岗位）及四项报告资料（原件、核定日期、检查项目原值、完整结论）；报告姓名须与花名册一致。初期完整度按这 9 项计算，不代表检查项目质量评分或临床健康评分。审核前仍须人工核对身份、原件、日期、单位、异常与结论。

医生确认保存不可变 JSON 快照与 SHA256；确认后内容冻结。归档重新计算内容摘要，必须与医生确认完全一致，并在同事务写发布记录与 outbox。退回保留决策并创建新修订；驳回保留原版本与记录。outbox 的外部消费程序、自动风险计算不在本轮实现范围；当前页面直接读取已发布事实和已有确认风险。

档案状态为互斥的列表优先级：待审核 → 待归档 → 待完善 → 已归档 → 已驳回。一个职工可有旧归档报告及新待审核草稿，因此顶卡“已归档人数”表示当前列表状态为已归档的人数；已发布历史报告始终可查。风险是独立维度，可与待审核重叠。

## 小程序接口与示例

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/ehr/mobile/session` | `{username,password}`，使用现有个人或医生账号 |
| GET | `/ehr/mobile/me` | 本人档案；首次按同组织旧用户授权资料建立绑定 |
| POST | `/ehr/mobile/reports` | multipart file + Idempotency-Key；本人由令牌确定，不能传其他职工 id |
| GET | `/ehr/mobile/reports/{id}/{revision}` | 只能读本人报告 |
| GET | `/ehr/mobile/files/{id}` | 只能下载本人原件 |
| DELETE | `/ehr/mobile/session` | 注销当前 Bearer 令牌 |

会话返回 256 位随机 Bearer、expiresIn=7200。数据库只保存摘要，逐次检查用户锁定、组织、模式和有效期。旧系统字面工号 token 不接受。首次资料缺少班组或岗位不妨碍上传，但会出现在待完善项中。

```javascript
// baseUrl 设为手机可访问的后端地址，账号密码来自正常登录表单。
wx.request({
  url: `${baseUrl}/ehr/mobile/session`, method: 'POST',
  data: { username, password },
  success({data}) {
    if (data.code !== 0) return showError(data.msg);
    const token = data.data.token;
    // requestId 为客户端生成并保留的 UUID；同一上传重试复用，下一份文件换新键。
    wx.uploadFile({
      url: `${baseUrl}/ehr/mobile/reports`, filePath, name: 'file',
      header: { Authorization: `Bearer ${token}`, 'Idempotency-Key': requestId },
      success(res) {
        const body = JSON.parse(res.data);
        if (res.statusCode !== 200 || body.code !== 0) return showError(body.msg);
        showReport(body.data); // 此时仅原件入库，不能显示“已审核归档”。
      }
    });
  }
});
```

开发局域网可测试 HTTP；发布小程序时配置实际 HTTPS 域名及微信平台的合法域名，手机不能用电脑的 localhost。

## 外部 OCR 接入契约

当前新流程采用 [A端持久队列与Worker接口](ocr-worker-api.md)。上传或重新识别直接入队，不再主动POST到配置的submit-url。新B端使用工作服务密钥和任务租约，通过`/ehr/worker`领取、下载、心跳和回传。

以下保留旧回调兼容接口：只在服务端配置 `EHR_OCR_CALLBACK_TOKEN` 后启用。已经被新工作服务领取的任务必须走新租约结果接口，不能用旧回调覆盖。新旧接口共用下列2.0.0结果JSON契约。

OCR 服务使用回调专用 Bearer 访问：

- `GET /ehr/integration/jobs/{jobId}/input`：关联 reportId、revision、文件清单。
- `GET /ehr/integration/jobs/{jobId}/files/{fileId}`：下载该任务所关联的原件。
- `POST /ehr/integration/jobs/{jobId}/result`：提交识别候选。

回调顶层字段：`contract_version`、`report_id`、`revision`、原文元数据 `institution_raw`/`exam_date_raw`/`report_date_raw`/`exam_type_raw`/`exam_number_raw`/`age_raw`，以及数组 `evidence`、`field_values`、`items`、`conclusions`、`advice`。空集合传 `[]`。

```json
{
  "contract_version":"2.0.0", "report_id":"报告UUID", "revision":1,
  "institution_raw":"原文机构", "exam_date_raw":"原文日期",
  "evidence":[{
    "evidence_id":"证据UUID", "source_file_id":"任务中的文件UUID", "page_number":1,
    "bbox":[10,20,110,40], "coordinate_system":"image_pixels_top_left",
    "page_rotation":0, "render_dpi":150, "original_label":"体重指数",
    "raw_fragment":"原文片段", "extraction_method":"ocr",
    "extractor_version":"对方服务版本", "ocr_confidence":0.95
  }],
  "field_values":[{
    "field_key":"full_name", "original_field_name":"姓名", "value_raw":"原文姓名",
    "value_normalized":null, "normalization_rule_version":null,
    "evidence_ids":["证据UUID"], "review_status":"unreviewed"
  }],
  "items":[{
    "original_field_name":"体重指数", "item_code":"", "primary_evidence_id":"证据UUID",
    "evidence_ids":["证据UUID"], "result_raw":"26.40", "unit_raw":"kg/m²",
    "reference_range_raw":"原文参考范围", "abnormal_flag_raw":null, "field_values":[]
  }],
  "conclusions":[{
    "original_field_name":"完整体检结论", "original_text":"报告结论原文",
    "primary_evidence_id":"证据UUID", "evidence_ids":["证据UUID"],
    "completeness":"complete", "standard_category":null
  }],
  "advice":[]
}
```

UUID 必须替换为有效 UUID。67 个标准字段键和 655 个项目编码见 [原始映射](../database/reference/v2.0.0/standard-fields-physical-mapping.csv) 与种子字典。未匹配项目的 item_code 留空，保存原项目名称及原文；词汇级匹配不能自动变成医学确认。参考 [数据库字典](ehr-data-dictionary.html)。

坐标系支持 `pdf_points_unrotated`、`image_pixels_top_left`、`object_only`；后者 bbox 应为 null，不能伪造坐标。提取方式支持 native_text / ocr / pdf_object。页码必须属于关联原件；主证据必须在 evidence_ids 内。

结果、单位、参考范围和异常标记保留原字符串，禁止回调擅自提供 result_normalized/unit_normalized/unit_conversion。日期原文先保存，exam_date_verified 不由 OCR 自动核定。职业字段另存接触信息。结论 standard_category 必须 null，由医生确认。回调不会自动通过审核。

成功回调保存在不可变 `a_ehr_ocr_receipts`。同任务、相同候选可重复发送；不同内容返回409。旧任务、旧版本、已人工修改候选、已送审或已审核版本不能被迟到回调覆盖。失败回调不保存为成功收据，事务回滚可修正后重发。

## 配置、运行与测试

`ehr.data-mode` 默认 real；自动化联调使用独立 synthetic 模式。`ehr.storage-root` 为受限文件目录，默认当前操作系统用户下 `kuangan-ehr-private`，开发运行时已单独配置；必须与 MySQL 共同备份，不能只备份 SQL。原件不会被放入公开 static 目录。数据库迁移 V001/V002/V003 必须先应用，代码不会启动时删除或重建旧表。

构建：Java 8，执行 `mvn -pl yihe-admin,yihe-applet-api -am package -DskipTests`。两个模块均包含共享 EHR 控制器；新鉴权独立于旧小程序明文工号 token。

联调脚本见 `tests/ehr/`。测试仅允许 synthetic 上下文，使用独立的18081测试实例，不向 real 模式写入假档案。报告保存在 database/*verification.json。测试检查了 CRUD、版本冲突、原件字节、上传幂等、审核快照、历史归档、小程序本人隔离、回调鉴权、原值/单位保留与重复回调。通过回调契约测试不代表真实 OCR 提供方已经接入；真实提供方端到端验收须等服务地址和实际报文。


## 本次交付验证结果

65项隔离业务及页面检查通过（26项主流程、19项小程序/OCR契约、13项浏览器页面、7项导入导出及边界）。正式18080服务重启后又通过13项只读访问验证。初次部署验证时正式库为0条；后续已按授权同步2309条旧职工基础资料，当前来源与例外见 [旧名册同步说明](legacy-roster-sync.md)。原型模拟人数没有写入真实档案。

两个WAR包构建成功，打包的页面脚本与源代码一致。所有检查详情见 [交付验证报告](../database/ehr-delivery-verification.json)。OCR真实提供方尚未配置。


旧名册同步后，小程序登录会先解析本人已有的旧用户绑定：原单位仍属于档案所属企业组织树时复用原档案；迁出企业或出现重复绑定时拒绝并提示人工核对。同步不创建小程序账号、不修改旧密码，也不创建体检报告。

绑定前资料有误时使用 [信息更正申请 API](miniapp-correction-api.md)，已实现申请、本人进度、后台审核队列与审核通过/驳回；待审期间 `bindingState=CORRECTION_PENDING`、`nextAction=WAIT_FOR_REVIEW`。
