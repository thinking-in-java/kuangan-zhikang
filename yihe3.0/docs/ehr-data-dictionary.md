# 矿安智康健康档案：逐表逐字段说明

实际数据库：`192.168.1.109 / shayu_3.0`。生成时间：2026-11-15T10:55:55.653744+08:00。

共 **44 张 a_ 表、449 个物理列**。其中V001为先前31张（19核心+12支撑），V002补充3张业务表，V003补充OCR回调收据，V004补充6张微信用户、会话和个人验证码绑定表，V005增加绑定前资料更正申请，V006增加持久OCR调度和处理尝试记录。下列内容从实际库读取类型、空值、默认值、主键、索引和外键，仅查询结构，不读取患者记录。

## 数据主线与关键边界

授权职工 → 一人一份主档案 → 原件/上传渠道 → 逻辑报告 → 多版本候选 → 原页证据/动态项目/职业信息/正式结论/建议 → 质量核验 → 医生审核 → 发布凭据与同步事件 → 个人档案和企业统计。

- 授权身份与报告快照分开，报告姓名不能自动覆盖职工姓名。
- 原始医学值、单位、参考范围保留字符串；无批准规则不进行换算或派生。
- 高风险与待审核可重叠；未评估不会被当作低风险。
- 当前前端已使用真实API；空库显示空状态，不再生成2436条演示档案。
- 已审核版本不可覆盖；退回保留处理记录并建立下一版本。
- 表中所有时间按UTC约定处理，前端可转换为北京时间。

## 表目录

- [a_dictionary_source_rows](#a_dictionary_source_rows)：Excel十二张表的逐行原样快照；校验规则及待确认记录可追溯（6列）
- [a_ehr_mobile_sessions](#a_ehr_mobile_sessions)：健康档案小程序专用短期令牌，不使用旧明文工号token（8列）
- [a_ehr_ocr_receipts](#a_ehr_ocr_receipts)：外部OCR成功回调原始候选快照；不代表审核或归档（4列）
- [a_employee_binding_codes](#a_employee_binding_codes)：后台逐人生成的企业身份验证码，确认后一次性消费（14列）
- [a_employee_roster_profiles](#a_employee_roster_profiles)：EHR业务授权花名册和生命周期；不修改旧职工表（18列）
- [a_employees](#a_employees)：授权职工身份索引；不存储报告抽取的身份覆盖值（7列）
- [a_extraction_jobs](#a_extraction_jobs)：PDF解析及OCR任务；与审核状态独立（10列）
- [a_followup_tasks](#a_followup_tasks)：由医生建议产生的随访任务（7列）
- [a_health_advice](#a_health_advice)：报告原文建议或医生确认建议（10列）
- [a_health_archive_audit_logs](#a_health_archive_audit_logs)：档案业务审计补充；不替换旧平台日志，避免记录明文隐私（12列）
- [a_health_conclusions](#a_health_conclusions)：医生正式结论；区别于平台风险等级（10列）
- [a_health_items](#a_health_items)：动态检查项目结果；保留原值及原单位（23列）
- [a_health_record_completeness](#a_health_record_completeness)：完整度规则计算快照；不是样本PDF字段覆盖率（11列）
- [a_health_records](#a_health_records)：个人健康档案主记录；每个授权职工一份（5列）
- [a_health_reports](#a_health_reports)：报告版本；报告中的身份信息为原始快照（19列）
- [a_health_risk_assessments](#a_health_risk_assessments)：平台风险评估；不替代职业健康正式医学结论（13列）
- [a_health_risk_factors](#a_health_risk_factors)：可解释的风险因素；追溯已发布报告、指标和原页证据（10列）
- [a_health_risk_report_sources](#a_health_risk_report_sources)：风险评估所依据的已发布报告版本；保证同一职工与档案（6列）
- [a_health_schema_migration](#a_health_schema_migration)：本模块结构迁移版本及输入校验摘要（6列）
- [a_medical_field_mapping](#a_medical_field_mapping)：机构字段及检查项目别名；允许同名多候选（13列）
- [a_medical_item_dictionary](#a_medical_item_dictionary)：655种检查项目；词汇映射不等于医学语义确认（6列）
- [a_medical_unit_mapping](#a_medical_unit_mapping)：单位键目录；未批准转换不得生成标准值（8列）
- [a_medical_unit_observations](#a_medical_unit_observations)：390条项目与原单位观察；81个重复单位键无损关联，不是批准规则（7列）
- [a_miniapp_binding_challenges](#a_miniapp_binding_challenges)：先校验后预览，再显式确认；不接受客户端回传资料覆盖（10列）
- [a_miniapp_correction_requests](#a_miniapp_correction_requests)：小程序绑定前的信息更正申请；人工审核通过才更新名册，保留前后快照（21列）
- [a_miniapp_employee_bindings](#a_miniapp_employee_bindings)：正式职工绑定及历史；只后台可解除，不能自助换成他人身份（13列）
- [a_miniapp_rate_limits](#a_miniapp_rate_limits)：微信登录及身份验证限流，绑定事务回滚不清空计数（3列）
- [a_miniapp_sessions](#a_miniapp_sessions)：微信小程序独立登录态，未绑定也能持有但不能访问健康档案（7列）
- [a_miniapp_users](#a_miniapp_users)：微信登录身份；不自动生成职工档案、不保存session_key（10列）
- [a_occupational_profiles](#a_occupational_profiles)：职业健康字段观察及授权职业资料（14列）
- [a_ocr_dispatch_attempts](#a_ocr_dispatch_attempts)：OCR每次领取、心跳及成功失败超时历史，不覆盖旧尝试（14列）
- [a_ocr_dispatch_tasks](#a_ocr_dispatch_tasks)：持久OCR领取队列、租约、心跳、超时重试和任务进度（18列）
- [a_report_evidence](#a_report_evidence)：版本内原页、坐标及提取证据（15列）
- [a_report_publications](#a_report_publications)：正式归档凭据；仅允许同摘要的质量通过与医生确认版本（14列）
- [a_report_quality_checks](#a_report_quality_checks)：报告版本质量校验结果；按快照摘要和规则版本留痕（12列）
- [a_report_review_snapshots](#a_report_review_snapshots)：医生审核时的不可变内容快照（5列）
- [a_report_revision_sources](#a_report_revision_sources)：报告版本与多份原文件的关联（5列）
- [a_report_series](#a_report_series)：逻辑报告；current_revision不是正式发布标记（6列）
- [a_report_sources](#a_report_sources)：PDF或照片原始文件；按组织和模式去重（7列）
- [a_report_sync_outbox](#a_report_sync_outbox)：正式发布后的事务消息；消费者须幂等处理（6列）
- [a_report_template_mapping](#a_report_template_mapping)：模板映射；没有原始模板包则不编造种子（7列）
- [a_report_upload_metadata](#a_report_upload_metadata)：每次上传的渠道及元数据；同一原件可有多次上传（11列）
- [a_review_records](#a_review_records)：医生审核决策历史；退回与驳回分别记录（9列）
- [a_standard_field_dictionary](#a_standard_field_dictionary)：01表67个字段的版本化定义；必填键不等于报告必有非空值（9列）

## a_dictionary_source_rows

**用途：** Excel十二张表的逐行原样快照；校验规则及待确认记录可追溯。

Excel十二张表的逐行原样快照；校验规则及待确认记录可追溯

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| workbook_sha256 | binary(32) | 不可空；默认 NULL/未设置 | 原始Excel字节摘要，防止混用不同输入版本。 | 索引:PRIMARY |
| sheet_index | tinyint unsigned | 不可空；默认 NULL/未设置 | 工作表索引，从0开始，对应00至11十二张表。 | 索引:PRIMARY |
| source_row | smallint unsigned | 不可空；默认 NULL/未设置 | 输入工作簿中的原始行号，用于复核本行字典来源。 | 索引:PRIMARY |
| sheet_name | varchar(100) | 不可空；默认 NULL/未设置 | 工作表原名称，用于展示和追溯。 | 无独立索引/外键；通过所属记录关联 |
| payload | longtext | 不可空；默认 NULL/未设置 | 源工作表一行的完整JSON文本。使用LONGTEXT避免JSON数字规范化改变样本统计原始表示。 | 无独立索引/外键；通过所属记录关联 |
| payload_sha256 | binary(32) | 不可空；默认 NULL/未设置 | 该行规范序列化文本的SHA256，与源行逐项复核。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_dictionary_source_rows` (
  `workbook_sha256` binary(32) NOT NULL COMMENT 'workbook_sha256',
  `sheet_index` tinyint unsigned NOT NULL COMMENT 'sheet_index',
  `source_row` smallint unsigned NOT NULL COMMENT 'source_row',
  `sheet_name` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'sheet_name',
  `payload` longtext COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'payload',
  `payload_sha256` binary(32) NOT NULL COMMENT 'payload_sha256',
  PRIMARY KEY (`workbook_sha256`,`sheet_index`,`source_row`),
  CONSTRAINT `ck_a_dictionary_source_rows_1` CHECK (json_valid(`payload`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='Excel十二张表的逐行原样快照；校验规则及待确认记录可追溯';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_ehr_mobile_sessions

**用途：** 健康档案小程序专用短期令牌，不使用旧明文工号token。

新EHR独立短期随机令牌，不能用旧工号token直接访问此模块。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| session_id | char(36) | 不可空；默认 NULL/未设置 | 健康档案小程序专用会话UUID，不使用可猜测的工号作为令牌。 | 索引:PRIMARY |
| token_hash | binary(32) | 不可空；默认 NULL/未设置 | 32字节随机Bearer令牌的SHA256。数据库不存放明文令牌。 | 索引:uq_mobile_token |
| legacy_user_code | varchar(50) | 不可空；默认 NULL/未设置 | 同组织旧系统用户编码，用于小程序本人绑定。新建档案不会自动创建登录账号。 | 无独立索引/外键；通过所属记录关联 |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 无独立索引/外键；通过所属记录关联 |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 无独立索引/外键；通过所属记录关联 |
| expires_at | datetime(6) | 不可空；默认 NULL/未设置 | 短期会话的UTC失效时间，默认签发后两小时。 | 索引:ix_mobile_expire |
| revoked | tinyint unsigned | 不可空；默认 0 | 0有效、1已注销；还会检查用户锁定状态与当前组织归属。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_ehr_mobile_sessions` (
  `session_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '会话UUID',
  `token_hash` binary(32) NOT NULL COMMENT '随机Bearer令牌的SHA256；不保存明文令牌',
  `legacy_user_code` varchar(50) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '已验证密码的旧用户标识，不能由上传参数指定',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '登录时确定的组织范围',
  `data_mode` enum('real','synthetic') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL COMMENT '会话数据模式由服务端配置确定',
  `expires_at` datetime(6) NOT NULL COMMENT 'UTC失效时间，默认2小时',
  `revoked` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '1已注销',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC签发时间',
  PRIMARY KEY (`session_id`),
  UNIQUE KEY `uq_mobile_token` (`token_hash`),
  KEY `ix_mobile_expire` (`expires_at`),
  CONSTRAINT `ck_a_mobile_revoked` CHECK ((`revoked` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='健康档案小程序专用短期令牌，不使用旧明文工号token';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_ehr_ocr_receipts

**用途：** 外部OCR成功回调原始候选快照；不代表审核或归档。

外部OCR成功回调原始候选快照；不代表审核或归档

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| job_id | char(36) | 不可空；默认 NULL/未设置 | 外部OCR/PDF识别任务UUID，绑定逻辑报告和候选版本；回调必须关联该任务。 | 外键→a_extraction_jobs.job_id；索引:PRIMARY |
| payload_sha256 | binary(32) | 不可空；默认 NULL/未设置 | 对外部OCR候选JSON规范序列化后的SHA256，用于相同job_id的重复回调比对；不是文件SHA256。 | 无独立索引/外键；通过所属记录关联 |
| response_json | longtext | 不可空；默认 NULL/未设置 | 外部OCR成功回调的候选JSON快照，未审核，不代表正式健康档案。 | 无独立索引/外键；通过所属记录关联 |
| received_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC识别结果成功接收时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_ehr_ocr_receipts` (
  `job_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '识别任务ID，绑定该任务的报告版本',
  `payload_sha256` binary(32) NOT NULL COMMENT '规范化回调JSON摘要，保护重复回调幂等性',
  `response_json` longtext COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'OCR候选响应快照，医学数值按原始字符串保存',
  `received_at` datetime(6) NOT NULL COMMENT 'UTC成功接收时间',
  PRIMARY KEY (`job_id`),
  CONSTRAINT `fk_a_ocr_receipt_job` FOREIGN KEY (`job_id`) REFERENCES `a_extraction_jobs` (`job_id`),
  CONSTRAINT `ck_a_ocr_receipt_json` CHECK (json_valid(`response_json`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='外部OCR成功回调原始候选快照；不代表审核或归档';
```

写入保护触发器：
- a_ocr_receipt_no_delete：BEFORE DELETE

```sql
BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only'; END
```
- a_ocr_receipt_no_update：BEFORE UPDATE

```sql
BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only'; END
```

## a_employee_binding_codes

**用途：** 后台逐人生成的企业身份验证码，确认后一次性消费。

后台逐人生成的企业身份验证码，确认后一次性消费

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| binding_code_id | char(36) | 不可空；默认 NULL/未设置 | 个人企业身份验证码签发记录UUID；后续预览、确认和绑定均精确关联此签发。 | 索引:PRIMARY |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_records.scope_code；索引:fk_code_record；索引:uq_binding_issue |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_miniapp_users.data_mode；外键→a_health_records.data_mode；索引:fk_code_consumer；索引:fk_code_record；索引:uq_binding_active_code；索引:uq_binding_issue；索引:uq_binding_secret |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:fk_code_record |
| code_hash | binary(32) | 不可空；默认 NULL/未设置 | 规范化个人验证码的SHA256；随机16字符约80位熵，不保存可重复查询的明文。 | 索引:uq_binding_secret |
| code_status | enum('issued','consumed','revoked') | 不可空；默认 issued | issued已签发、consumed已确认消费、revoked已撤销；过期还须结合expires_at判断。 | 无独立索引/外键；通过所属记录关联 |
| issue_request_key | varchar(96) | 不可空；默认 NULL/未设置 | 后台签发幂等键，同企业和模式内唯一；重复不旋转验证码，也不重返明文。 | 索引:uq_binding_issue |
| issued_by_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 后台签发人标识的二进制引用，不能作为职工身份验证码。 | 无独立索引/外键；通过所属记录关联 |
| issued_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC个人验证码签发时间。 | 无独立索引/外键；通过所属记录关联 |
| expires_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC失效时间。会话默认2小时、确认凭据最多10分钟、个人码默认24小时，具体以本记录为准。 | 无独立索引/外键；通过所属记录关联 |
| consumed_by | char(36) | 可空；默认 NULL/未设置 | 实际勾选确认并完成绑定的小程序用户UUID；仅预览不会填此列。 | 外键→a_miniapp_users.mini_user_id；索引:fk_code_consumer |
| consumed_at | datetime(6) | 可空；默认 NULL/未设置 | UTC确认成功后消费验证码的时间，未消费为NULL。 | 无独立索引/外键；通过所属记录关联 |
| revoked_at | datetime(6) | 可空；默认 NULL/未设置 | UTC验证码撤销或微信绑定解除时间；保留原记录以便追溯。 | 无独立索引/外键；通过所属记录关联 |
| active_record_id | char(36) | 可空；默认 NULL/未设置 | 自动生成列：仅在验证码为issued或绑定为active时保留record_id，用唯一索引限制一职工一个当前状态；不能由客户端写入。 | 索引:uq_binding_active_code |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_employee_binding_codes` (
  `binding_code_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '个人专属验证码签发UUID',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '签发医生所属档案池',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '数据模式',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '专属绑定目标职工档案',
  `code_hash` binary(32) NOT NULL COMMENT '高强度随机验证码摘要，不保存明文',
  `code_status` enum('issued','consumed','revoked') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'issued' COMMENT '过期由expires_at另行判断',
  `issue_request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '签发幂等键；重复请求不再返回明文',
  `issued_by_ref` varbinary(256) NOT NULL COMMENT '后台签发人',
  `issued_at` datetime(6) NOT NULL COMMENT 'UTC签发时间',
  `expires_at` datetime(6) NOT NULL COMMENT 'UTC失效时间',
  `consumed_by` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '确认绑定时的小程序用户',
  `consumed_at` datetime(6) DEFAULT NULL COMMENT '真正确认时才消费验证码',
  `revoked_at` datetime(6) DEFAULT NULL COMMENT '重发或后台撤销时间',
  `active_record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS ((case when (`code_status` = _utf8mb4'issued') then `record_id` else NULL end)) STORED COMMENT '单人单个在用验证码约束',
  PRIMARY KEY (`binding_code_id`),
  UNIQUE KEY `uq_binding_secret` (`data_mode`,`code_hash`),
  UNIQUE KEY `uq_binding_issue` (`scope_code`,`data_mode`,`issue_request_key`),
  UNIQUE KEY `uq_binding_active_code` (`data_mode`,`active_record_id`),
  KEY `fk_code_record` (`scope_code`,`data_mode`,`record_id`),
  KEY `fk_code_consumer` (`consumed_by`,`data_mode`),
  CONSTRAINT `fk_code_consumer` FOREIGN KEY (`consumed_by`, `data_mode`) REFERENCES `a_miniapp_users` (`mini_user_id`, `data_mode`),
  CONSTRAINT `fk_code_record` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='后台逐人生成的企业身份验证码，确认后一次性消费';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_employee_roster_profiles

**用途：** EHR业务授权花名册和生命周期；不修改旧职工表。

为尚未创建登录账号的职工提供授权档案维护，并支持与旧用户绑定；不改旧职工表，也不接收OCR覆盖。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| employee_id | char(36) | 不可空；默认 NULL/未设置 | 职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。 | 外键→a_employees.employee_id；索引:fk_a_roster_employee；索引:PRIMARY |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_employees.scope_code；索引:fk_a_roster_employee；索引:ix_roster_page；索引:uq_roster_employee_no；索引:uq_roster_legacy |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_employees.data_mode；索引:fk_a_roster_employee；索引:ix_roster_page；索引:uq_roster_employee_no；索引:uq_roster_legacy |
| employee_name | varchar(100) | 不可空；默认 NULL/未设置 | 企业授权资料中的姓名；报告姓名保存在版本快照，不能自动覆盖本列。 | 无独立索引/外键；通过所属记录关联 |
| employee_no | varchar(96) | 不可空；默认 NULL/未设置 | 组织内唯一工号；软删除仍占用编号，避免重新建档割裂历史。 | 索引:uq_roster_employee_no |
| gender | enum('男','女','未填写') | 不可空；默认 未填写 | 授权身份性别：男、女、未填写。 | 无独立索引/外键；通过所属记录关联 |
| birth_date | date | 可空；默认 NULL/未设置 | 授权出生日期，用于当前年龄统计；报告原年龄仍独立保留。 | 无独立索引/外键；通过所属记录关联 |
| phone | varchar(30) | 可空；默认 NULL/未设置 | 授权联系电话，权限与导出均按组织边界控制。 | 无独立索引/外键；通过所属记录关联 |
| organization_name | varchar(255) | 不可空；默认 NULL/未设置 | 单位显示名称，仅用于业务展示；实际访问边界是scope_code。 | 无独立索引/外键；通过所属记录关联 |
| team_name | varchar(100) | 可空；默认 NULL/未设置 | 职工班组，供检索和班组统计。 | 无独立索引/外键；通过所属记录关联 |
| position_name | varchar(100) | 可空；默认 NULL/未设置 | 岗位或工种名称，供授权职业资料展示，不替代报告中的job_type原文。 | 无独立索引/外键；通过所属记录关联 |
| exposure_history | text | 可空；默认 NULL/未设置 | 企业授权填报的职业危害与接触史；不自动转成健康风险诊断。 | 无独立索引/外键；通过所属记录关联 |
| legacy_user_code | varchar(50) | 可空；默认 NULL/未设置 | 同组织旧系统用户编码，用于小程序本人绑定。新建档案不会自动创建登录账号。 | 索引:uq_roster_legacy |
| row_version | int unsigned | 不可空；默认 1 | 授权档案的乐观锁版本；编辑/删除必须携带旧版本，冲突返回409。 | 无独立索引/外键；通过所属记录关联 |
| deleted_at | datetime(6) | 可空；默认 NULL/未设置 | UTC软删除时间，NULL表示在档；不删除原件、报告、审核及历史。 | 索引:ix_roster_page |
| delete_reason | varchar(600) | 可空；默认 NULL/未设置 | 软删除必填原因，留作审计。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_roster_page |
| updated_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录最后更新时间，任务更新由接口显式写UTC。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_employee_roster_profiles` (
  `employee_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'a_employees主键；授权花名册资料，不来自报告自动覆盖',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端确定的组织范围',
  `data_mode` enum('real','synthetic') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实与测试数据隔离',
  `employee_name` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '授权职工姓名',
  `employee_no` varchar(96) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '组织内工号',
  `gender` enum('男','女','未填写') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT '未填写' COMMENT '授权资料性别',
  `birth_date` date DEFAULT NULL COMMENT '授权出生日期',
  `phone` varchar(30) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '授权联系电话',
  `organization_name` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '单位显示名称；不决定访问权限',
  `team_name` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '班组',
  `position_name` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '岗位或工种',
  `exposure_history` text COLLATE utf8mb4_0900_bin COMMENT '授权填报的危害接触史，不是AI风险结论',
  `legacy_user_code` varchar(50) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '已校验同组织的旧用户标识；小程序只能访问本人',
  `row_version` int unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁；编辑时必须传回',
  `deleted_at` datetime(6) DEFAULT NULL COMMENT 'UTC软删除时间，保留报告和审核历史',
  `delete_reason` varchar(600) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '软删除原因',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC建档时间',
  `updated_at` datetime(6) NOT NULL COMMENT 'UTC最后维护时间',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `uq_roster_employee_no` (`scope_code`,`data_mode`,`employee_no`),
  UNIQUE KEY `uq_roster_legacy` (`scope_code`,`data_mode`,`legacy_user_code`),
  KEY `ix_roster_page` (`scope_code`,`data_mode`,`deleted_at`,`created_at`),
  KEY `fk_a_roster_employee` (`scope_code`,`data_mode`,`employee_id`),
  CONSTRAINT `fk_a_roster_employee` FOREIGN KEY (`scope_code`, `data_mode`, `employee_id`) REFERENCES `a_employees` (`scope_code`, `data_mode`, `employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='EHR业务授权花名册和生命周期；不修改旧职工表';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_employees

**用途：** 授权职工身份索引；不存储报告抽取的身份覆盖值。

身份根索引；授权花名册资料由旧系统关联及a_employee_roster_profiles承接。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| employee_id | char(36) | 不可空；默认 NULL/未设置 | 职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。 | 索引:PRIMARY；索引:uq_employees_scope_id |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 索引:uq_employees_identity；索引:uq_employees_roster；索引:uq_employees_scope_id |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 索引:uq_employees_identity；索引:uq_employees_roster；索引:uq_employees_scope_id |
| roster_employee_key | varchar(96) | 不可空；默认 NULL/未设置 | 授权花名册中的稳定标识。已关联旧用户时使用旧user_code；后台新建职工使用ehr:UUID。 | 索引:uq_employees_roster |
| identity_ref | varbinary(128) | 不可空；默认 NULL/未设置 | 受限身份标识/令牌的二进制引用；本期使用授权标识摘要，不直接存放报告中的身份证号。 | 索引:uq_employees_identity |
| roster_source_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 授权身份资料的来源引用。用于说明是谁、基于什么授权资料建档；不是报告抽取值。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_employees` (
  `employee_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '员工标识',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `roster_employee_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `identity_ref` varbinary(128) NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `roster_source_ref` varbinary(256) NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `uq_employees_scope_id` (`scope_code`,`data_mode`,`employee_id`),
  UNIQUE KEY `uq_employees_roster` (`scope_code`,`data_mode`,`roster_employee_key`),
  UNIQUE KEY `uq_employees_identity` (`scope_code`,`data_mode`,`identity_ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='授权职工身份索引；不存储报告抽取的身份覆盖值';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_extraction_jobs

**用途：** PDF解析及OCR任务；与审核状态独立。

uploaded→ocr_complete/extracted→standardized/identity_checked/quality_checked→pending_review→published，异常为failed。状态表示所完成的阶段，不能伪造OCR成功。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| job_id | char(36) | 不可空；默认 NULL/未设置 | 外部OCR/PDF识别任务UUID，绑定逻辑报告和候选版本；回调必须关联该任务。 | 索引:PRIMARY |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_series.report_id；外键→a_health_reports.report_id；索引:fk_a_661c456d50683b60a1；索引:fk_a_6cb4f7969632d384bb |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_series.scope_code；索引:fk_a_661c456d50683b60a1；索引:ix_jobs_queue；索引:uq_jobs_request |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_series.data_mode；索引:fk_a_661c456d50683b60a1；索引:ix_jobs_queue；索引:uq_jobs_request |
| request_key | varchar(96) | 不可空；默认 NULL/未设置 | 客户端或任务幂等键。相同键应对应同一身份/原件/业务操作，不允许重用为其他内容。 | 索引:uq_jobs_request |
| pipeline_version | varchar(64) | 不可空；默认 NULL/未设置 | 识别处理管线/适配器版本，不代表模型一定运行成功。 | 无独立索引/外键；通过所属记录关联 |
| status | enum('uploaded','ocr_complete','extracted','standardized','identity_checked','quality_checked','pending_review','failed','published') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 索引:ix_jobs_queue |
| revision | int unsigned | 可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:fk_a_6cb4f7969632d384bb |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_jobs_queue |
| updated_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录最后更新时间，任务更新由接口显式写UTC。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_extraction_jobs` (
  `job_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `pipeline_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('uploaded','ocr_complete','extracted','standardized','identity_checked','quality_checked','pending_review','failed','published') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `revision` int unsigned DEFAULT NULL COMMENT '报告版本号',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '按冻结 DDL 列定义使用',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `uq_jobs_request` (`scope_code`,`data_mode`,`request_key`),
  KEY `ix_jobs_queue` (`scope_code`,`data_mode`,`status`,`created_at`),
  KEY `fk_a_661c456d50683b60a1` (`scope_code`,`data_mode`,`report_id`),
  KEY `fk_a_6cb4f7969632d384bb` (`report_id`,`revision`),
  CONSTRAINT `fk_a_661c456d50683b60a1` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`) REFERENCES `a_report_series` (`scope_code`, `data_mode`, `report_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_6cb4f7969632d384bb` FOREIGN KEY (`report_id`, `revision`) REFERENCES `a_health_reports` (`report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='PDF解析及OCR任务；与审核状态独立';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_followup_tasks

**用途：** 由医生建议产生的随访任务。

由医生建议产生的随访任务

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| task_id | char(36) | 不可空；默认 NULL/未设置 | 随访任务UUID，来自一个确定的报告版本和建议；不能代替识别任务job_id。 | 索引:PRIMARY |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_advice.report_id；索引:uq_tasks_origin |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_advice.revision；索引:uq_tasks_origin |
| advice_id | char(36) | 不可空；默认 NULL/未设置 | 一条报告原文建议或医生建议的UUID，用于后续任务追溯。 | 外键→a_health_advice.advice_id；索引:uq_tasks_origin |
| status | enum('pending','completed','cancelled') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 索引:ix_tasks_due |
| due_at | datetime(6) | 可空；默认 NULL/未设置 | 任务计划到期UTC时间；无明确计划时为NULL。 | 索引:ix_tasks_due |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_followup_tasks` (
  `task_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `advice_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('pending','completed','cancelled') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `due_at` datetime(6) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `uq_tasks_origin` (`report_id`,`revision`,`advice_id`),
  KEY `ix_tasks_due` (`status`,`due_at`),
  CONSTRAINT `fk_a_39caa637d84cd8a29a` FOREIGN KEY (`report_id`, `revision`, `advice_id`) REFERENCES `a_health_advice` (`report_id`, `revision`, `advice_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='由医生建议产生的随访任务';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_advice

**用途：** 报告原文建议或医生确认建议。

报告原文建议或医生确认建议

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_evidence.report_id；外键→a_review_records.report_id；索引:fk_a_2c85cc18608a9db27c；索引:fk_a_349979c79aa1064b44；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_evidence.revision；外键→a_review_records.revision；索引:fk_a_2c85cc18608a9db27c；索引:fk_a_349979c79aa1064b44；索引:PRIMARY |
| advice_id | char(36) | 不可空；默认 NULL/未设置 | 一条报告原文建议或医生建议的UUID，用于后续任务追溯。 | 索引:PRIMARY |
| source_kind | enum('pdf','clinician') | 不可空；默认 NULL/未设置 | 建议来源：pdf为报告原文，clinician为医生补充，后者必须有医生作者与审核记录。 | 无独立索引/外键；通过所属记录关联 |
| source_evidence_id | char(36) | 可空；默认 NULL/未设置 | 建议原文对应的证据ID；来自PDF时必须能够定位原页。 | 外键→a_report_evidence.evidence_id；索引:fk_a_2c85cc18608a9db27c |
| review_id | char(36) | 可空；默认 NULL/未设置 | 一条人工审核决策UUID，关联精确报告版本和已核对摘要。 | 外键→a_review_records.review_id；索引:fk_a_349979c79aa1064b44 |
| author_ref | varbinary(256) | 可空；默认 NULL/未设置 | 医生补充建议的作者标识，不能把模型当作持证医生。 | 无独立索引/外键；通过所属记录关联 |
| original_field_name | text | 可空；默认 NULL/未设置 | 报告原始字段/项目名称，例如原文检查名；应与标准名称分别保留。 | 无独立索引/外键；通过所属记录关联 |
| text_raw | mediumtext | 不可空；默认 NULL/未设置 | 原始建议/处理意见文字，保留来源，不自动生成用药或诊疗方案。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_advice` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `advice_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `source_kind` enum('pdf','clinician') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `source_evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `review_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `author_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_field_name` text COLLATE utf8mb4_0900_bin COMMENT '原字段名',
  `text_raw` mediumtext COLLATE utf8mb4_0900_bin NOT NULL COMMENT '建议原文',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  PRIMARY KEY (`report_id`,`revision`,`advice_id`),
  KEY `fk_a_2c85cc18608a9db27c` (`report_id`,`revision`,`source_evidence_id`),
  KEY `fk_a_349979c79aa1064b44` (`report_id`,`revision`,`review_id`),
  CONSTRAINT `fk_a_2c85cc18608a9db27c` FOREIGN KEY (`report_id`, `revision`, `source_evidence_id`) REFERENCES `a_report_evidence` (`report_id`, `revision`, `evidence_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_349979c79aa1064b44` FOREIGN KEY (`report_id`, `revision`, `review_id`) REFERENCES `a_review_records` (`report_id`, `revision`, `review_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_advice_1` CHECK ((((`source_kind` = _utf8mb4'pdf') and (`source_evidence_id` is not null)) or ((`source_kind` = _utf8mb4'clinician') and (`review_id` is not null) and (`author_ref` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='报告原文建议或医生确认建议';
```

写入保护触发器：
- a_ehr_3809de636b1c681974：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_e54f9dffd5c13d82fc：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_fbab3fca2b396e2938：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_health_archive_audit_logs

**用途：** 档案业务审计补充；不替换旧平台日志，避免记录明文隐私。

档案业务审计补充；不替换旧平台日志，避免记录明文隐私

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| audit_id | char(36) | 不可空；默认 NULL/未设置 | 一条不可混淆的档案业务审计UUID。 | 索引:PRIMARY；索引:uq_health_archive_audit_logs_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_reports.scope_code；外键→a_health_records.scope_code；索引:fk_a_27189a48c21ca37871；索引:fk_a_db59e30cae7af9858c；索引:uq_audit_request；索引:uq_health_archive_audit_logs_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_reports.data_mode；外键→a_health_records.data_mode；索引:fk_a_27189a48c21ca37871；索引:fk_a_db59e30cae7af9858c；索引:uq_audit_request；索引:uq_health_archive_audit_logs_scope |
| record_id | char(36) | 可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:fk_a_db59e30cae7af9858c |
| report_id | char(36) | 可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:fk_a_27189a48c21ca37871 |
| revision | int unsigned | 可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:fk_a_27189a48c21ca37871 |
| actor_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 实际执行操作的账号/服务受限标识，不使用前端传入的任意操作者。 | 无独立索引/外键；通过所属记录关联 |
| action | varchar(64) | 不可空；默认 NULL/未设置 | 审计动作代码，例如profile_saved、report_uploaded、report_approve、report_archive。 | 索引:uq_audit_request |
| request_key | varchar(96) | 不可空；默认 NULL/未设置 | 客户端或任务幂等键。相同键应对应同一身份/原件/业务操作，不允许重用为其他内容。 | 索引:uq_audit_request |
| reason | text | 可空；默认 NULL/未设置 | 业务处理或风险判定的具体理由；内容应与原件、规则和权限一致。 | 无独立索引/外键；通过所属记录关联 |
| redacted_diff | json | 可空；默认 NULL/未设置 | 经过脱敏的变更差异JSON，不能把完整隐私、密码、令牌写进日志。 | 无独立索引/外键；通过所属记录关联 |
| occurred_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC业务动作发生时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_archive_audit_logs` (
  `audit_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'audit_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT 'record_id',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT 'report_id',
  `revision` int unsigned DEFAULT NULL COMMENT 'revision',
  `actor_ref` varbinary(256) NOT NULL COMMENT 'actor_ref',
  `action` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'action',
  `request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'request_key',
  `reason` text COLLATE utf8mb4_0900_bin COMMENT 'reason',
  `redacted_diff` json DEFAULT NULL COMMENT 'redacted_diff',
  `occurred_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`audit_id`),
  UNIQUE KEY `uq_health_archive_audit_logs_scope` (`scope_code`,`data_mode`,`audit_id`),
  UNIQUE KEY `uq_audit_request` (`scope_code`,`data_mode`,`request_key`,`action`),
  KEY `fk_a_db59e30cae7af9858c` (`scope_code`,`data_mode`,`record_id`),
  KEY `fk_a_27189a48c21ca37871` (`scope_code`,`data_mode`,`report_id`,`revision`),
  CONSTRAINT `fk_a_27189a48c21ca37871` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`) REFERENCES `a_health_reports` (`scope_code`, `data_mode`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_db59e30cae7af9858c` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_archive_audit_logs_1` CHECK (((`report_id` is null) = (`revision` is null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='档案业务审计补充；不替换旧平台日志，避免记录明文隐私';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_conclusions

**用途：** 医生正式结论；区别于平台风险等级。

医学正式结论独立存储，不与平台风险等级混为一列。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_evidence.report_id；索引:fk_a_ebe08790e996a74b80；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_evidence.revision；索引:fk_a_ebe08790e996a74b80；索引:PRIMARY |
| conclusion_id | char(36) | 不可空；默认 NULL/未设置 | 一段医生正式结论的版本内UUID，支持一份报告多段结论。 | 索引:PRIMARY |
| primary_evidence_id | char(36) | 不可空；默认 NULL/未设置 | 该条项目/结论的主要原页证据ID，必须包含在evidence_ids数组中。 | 外键→a_report_evidence.evidence_id；索引:fk_a_ebe08790e996a74b80 |
| evidence_ids | json | 不可空；默认 NULL/未设置 | 关联本报告版本的证据UUID数组；用于多行、跨页或多模型证据并列。 | 无独立索引/外键；通过所属记录关联 |
| original_field_name | text | 不可空；默认 NULL/未设置 | 报告原始字段/项目名称，例如原文检查名；应与标准名称分别保留。 | 无独立索引/外键；通过所属记录关联 |
| original_text | mediumtext | 可空；默认 NULL/未设置 | 完整医生正式结论原文，不能拿分项小结或签名栏代替完整结论。 | 无独立索引/外键；通过所属记录关联 |
| completeness | enum('complete','partial','unknown') | 不可空；默认 NULL/未设置 | 这一段结论的完整性：complete、partial、unknown；不是个人主档案完整度百分比。 | 无独立索引/外键；通过所属记录关联 |
| standard_category | enum('目前未见异常','疑似职业病','职业禁忌证','其他疾病或异常') | 可空；默认 NULL/未设置 | 医生核对后对正式职业健康结论的分类：目前未见异常、疑似职业病、职业禁忌证、其他疾病或异常。不是平台高/中/低风险。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_conclusions` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `conclusion_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `primary_evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `evidence_ids` json NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_field_name` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '原字段名',
  `original_text` mediumtext COLLATE utf8mb4_0900_bin COMMENT '医生正式结论原文',
  `completeness` enum('complete','partial','unknown') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `standard_category` enum('目前未见异常','疑似职业病','职业禁忌证','其他疾病或异常') COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  PRIMARY KEY (`report_id`,`revision`,`conclusion_id`),
  KEY `fk_a_ebe08790e996a74b80` (`report_id`,`revision`,`primary_evidence_id`),
  CONSTRAINT `fk_a_ebe08790e996a74b80` FOREIGN KEY (`report_id`, `revision`, `primary_evidence_id`) REFERENCES `a_report_evidence` (`report_id`, `revision`, `evidence_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_conclusions_1` CHECK (((json_type(`evidence_ids`) = _utf8mb4'ARRAY') and (json_length(`evidence_ids`) > 0))),
  CONSTRAINT `ck_a_health_conclusions_2` CHECK (json_contains(`evidence_ids`,json_quote(`primary_evidence_id`))),
  CONSTRAINT `ck_a_health_conclusions_3` CHECK (((`standard_category` is null) or ((`completeness` = _utf8mb4'complete') and (`review_status` = _utf8mb4'confirmed') and (`original_text` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='医生正式结论；区别于平台风险等级';
```

写入保护触发器：
- a_ehr_09f03f5e1c43272088：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_5e6ac349fd6efed3e7：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_83e7a75c44671fbf22：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_health_items

**用途：** 动态检查项目结果；保留原值及原单位。

每行一个实际检查项目。655个项目是字典记录，不是655个固定数据库结果列。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_evidence.report_id；索引:fk_a_d076f8cec598e24700；索引:PRIMARY；索引:uq_item_report_ordinal |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_evidence.revision；索引:fk_a_d076f8cec598e24700；索引:PRIMARY；索引:uq_item_report_ordinal |
| item_id | char(36) | 不可空；默认 NULL/未设置 | 报告版本内一条检查结果的UUID；同项目多次测量可有多行，不能按item_code覆盖。 | 索引:PRIMARY |
| ordinal | int unsigned | 不可空；默认 NULL/未设置 | 该版本项目明细的行顺序，从1开始；同版本唯一，用于还原报告阅读顺序。 | 索引:uq_item_report_ordinal |
| primary_evidence_id | char(36) | 不可空；默认 NULL/未设置 | 该条项目/结论的主要原页证据ID，必须包含在evidence_ids数组中。 | 外键→a_report_evidence.evidence_id；索引:fk_a_d076f8cec598e24700 |
| evidence_ids | json | 不可空；默认 NULL/未设置 | 关联本报告版本的证据UUID数组；用于多行、跨页或多模型证据并列。 | 无独立索引/外键；通过所属记录关联 |
| original_field_name | text | 不可空；默认 NULL/未设置 | 报告原始字段/项目名称，例如原文检查名；应与标准名称分别保留。 | 无独立索引/外键；通过所属记录关联 |
| item_code | varchar(64) | 可空；默认 NULL/未设置 | 医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。 | 外键→a_medical_item_dictionary.item_code；索引:ix_item_dictionary |
| dictionary_version | varchar(64) | 可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 外键→a_medical_item_dictionary.dictionary_version；索引:ix_item_dictionary |
| standard_field_name | text | 可空；默认 NULL/未设置 | 匹配到的标准项目名，原项目名仍保留；未匹配时可以为空。 | 无独立索引/外键；通过所属记录关联 |
| abbreviation_raw | text | 可空；默认 NULL/未设置 | 项目英文缩写原文，例如报告提供的缩写，不从中文名称自行生成。 | 无独立索引/外键；通过所属记录关联 |
| result_raw | mediumtext | 可空；默认 NULL/未设置 | 检查结果原文，可为复合值、定性文字、滴度、半定量或数值字符串；不得强制转Double。 | 无独立索引/外键；通过所属记录关联 |
| result_normalized | json | 可空；默认 NULL/未设置 | 有批准规则和版本依据后的结构化标准结果JSON，当前OCR接收层拒绝无批准的派生结果。 | 无独立索引/外键；通过所属记录关联 |
| unit_raw | text | 可空；默认 NULL/未设置 | 报告上的原始单位字符串，保留大小写、指数、符号和空格；不能擅自转换。 | 无独立索引/外键；通过所属记录关联 |
| unit_normalized | text | 可空；默认 NULL/未设置 | 经批准规则转换后的单位。当前批准数为0，业务入库应为NULL。 | 无独立索引/外键；通过所属记录关联 |
| unit_mapping_key | varchar(64) | 可空；默认 NULL/未设置 | 单位映射目录编码。04表390行只有81个不同原单位键，具体项目关系保存在observations。 | 外键→a_medical_unit_mapping.unit_mapping_key；索引:fk_a_6d717733abe1dd49de |
| unit_mapping_version | varchar(64) | 可空；默认 NULL/未设置 | 该结果采用的单位映射发布版本，与unit_mapping_key配对。 | 外键→a_medical_unit_mapping.mapping_version；索引:fk_a_6d717733abe1dd49de |
| normalization_rule_version | varchar(64) | 可空；默认 NULL/未设置 | 归一化/单位转换规则版本；不能仅填一个字符串就宣称已取得医学批准。 | 无独立索引/外键；通过所属记录关联 |
| reference_range_raw | mediumtext | 可空；默认 NULL/未设置 | 原报告参考范围字符串，保留分组和单位上下文；不得套用一套通用正常值。 | 无独立索引/外键；通过所属记录关联 |
| abnormal_flag_raw | text | 可空；默认 NULL/未设置 | 原报告异常标记，例如箭头或文字；不直接把它当作平台风险等级。 | 无独立索引/外键；通过所属记录关联 |
| field_values | json | 不可空；默认 NULL/未设置 | 版本内字段观察JSON数组。同一field_key可重复出现；每项保存原字段名、原值、证据ID和复核状态。 | 无独立索引/外键；通过所属记录关联 |
| mapping_status | enum('unmapped','lexical_only','needs_confirmation','confirmed') | 不可空；默认 NULL/未设置 | unmapped未匹配、lexical_only词汇级、needs_confirmation待确认、confirmed已验证。医生确认原值不等于自动确认字典语义。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_items` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `item_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `ordinal` int unsigned NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `primary_evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `evidence_ids` json NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_field_name` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '原字段名',
  `item_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `standard_field_name` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `abbreviation_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `result_raw` mediumtext COLLATE utf8mb4_0900_bin COMMENT '检查结果原文',
  `result_normalized` json DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `unit_raw` text COLLATE utf8mb4_0900_bin COMMENT '原单位',
  `unit_normalized` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `unit_mapping_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `unit_mapping_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `normalization_rule_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `reference_range_raw` mediumtext COLLATE utf8mb4_0900_bin COMMENT '原参考范围',
  `abnormal_flag_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `field_values` json NOT NULL COMMENT '版本内字段观察 JSON',
  `mapping_status` enum('unmapped','lexical_only','needs_confirmation','confirmed') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  PRIMARY KEY (`report_id`,`revision`,`item_id`),
  UNIQUE KEY `uq_item_report_ordinal` (`report_id`,`revision`,`ordinal`),
  KEY `ix_item_dictionary` (`item_code`,`dictionary_version`),
  KEY `fk_a_d076f8cec598e24700` (`report_id`,`revision`,`primary_evidence_id`),
  KEY `fk_a_6d717733abe1dd49de` (`unit_mapping_key`,`unit_mapping_version`),
  CONSTRAINT `fk_a_67ba5611843606af77` FOREIGN KEY (`item_code`, `dictionary_version`) REFERENCES `a_medical_item_dictionary` (`item_code`, `dictionary_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_6d717733abe1dd49de` FOREIGN KEY (`unit_mapping_key`, `unit_mapping_version`) REFERENCES `a_medical_unit_mapping` (`unit_mapping_key`, `mapping_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_d076f8cec598e24700` FOREIGN KEY (`report_id`, `revision`, `primary_evidence_id`) REFERENCES `a_report_evidence` (`report_id`, `revision`, `evidence_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_items_1` CHECK (((json_type(`evidence_ids`) = _utf8mb4'ARRAY') and (json_length(`evidence_ids`) > 0))),
  CONSTRAINT `ck_a_health_items_2` CHECK (json_contains(`evidence_ids`,json_quote(`primary_evidence_id`))),
  CONSTRAINT `ck_a_health_items_3` CHECK ((`ordinal` > 0)),
  CONSTRAINT `ck_a_health_items_4` CHECK ((json_type(`field_values`) = _utf8mb4'ARRAY')),
  CONSTRAINT `ck_a_health_items_5` CHECK (((`item_code` is null) = (`dictionary_version` is null))),
  CONSTRAINT `ck_a_health_items_6` CHECK (((`mapping_status` <> _utf8mb4'confirmed') or (`item_code` is not null))),
  CONSTRAINT `ck_a_health_items_7` CHECK (((`unit_mapping_key` is null) = (`unit_mapping_version` is null))),
  CONSTRAINT `ck_a_health_items_8` CHECK (((`unit_normalized` is null) or ((`unit_mapping_key` is not null) and (`normalization_rule_version` is not null)))),
  CONSTRAINT `ck_a_health_items_9` CHECK (((`result_normalized` is null) or (`normalization_rule_version` is not null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='动态检查项目结果；保留原值及原单位';
```

写入保护触发器：
- a_ehr_79aeb614d3934d4ba5：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
IF NEW.unit_normalized IS NOT NULL AND NOT EXISTS (SELECT 1 FROM a_medical_unit_mapping m WHERE m.unit_mapping_key=NEW.unit_mapping_key AND m.mapping_version=NEW.unit_mapping_version AND m.status='confirmed' AND m.item_code=NEW.item_code AND m.dictionary_version=NEW.dictionary_version) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR unit conversion requires approved item-specific mapping';
END IF;
END
```
- a_ehr_da2f3b1733e2bb039a：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
IF NEW.unit_normalized IS NOT NULL AND NOT EXISTS (SELECT 1 FROM a_medical_unit_mapping m WHERE m.unit_mapping_key=NEW.unit_mapping_key AND m.mapping_version=NEW.unit_mapping_version AND m.status='confirmed' AND m.item_code=NEW.item_code AND m.dictionary_version=NEW.dictionary_version) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR unit conversion requires approved item-specific mapping';
END IF;
END
```
- a_ehr_ff7e5135de0f9fb5f7：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_health_record_completeness

**用途：** 完整度规则计算快照；不是样本PDF字段覆盖率。

完整度规则计算快照；不是样本PDF字段覆盖率

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| snapshot_id | char(36) | 不可空；默认 NULL/未设置 | 一次档案完整度快照UUID，允许按时间保留历史计算结果。 | 索引:PRIMARY；索引:uq_health_record_completeness_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_records.scope_code；索引:ix_completeness_latest；索引:uq_health_record_completeness_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_records.data_mode；索引:ix_completeness_latest；索引:uq_health_record_completeness_scope |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:ix_completeness_latest |
| required_count | int unsigned | 不可空；默认 NULL/未设置 | 当前完整度规则要求的材料/信息项数，不是655个项目数或样本字段出现频次。 | 无独立索引/外键；通过所属记录关联 |
| completed_count | int unsigned | 不可空；默认 NULL/未设置 | 该规则下实际已完成的项数，应不大于required_count。 | 无独立索引/外键；通过所属记录关联 |
| missing_items | json | 不可空；默认 NULL/未设置 | 缺失材料JSON数组，长度须等于应有项数减已完成项数。 | 无独立索引/外键；通过所属记录关联 |
| rule_version | varchar(64) | 不可空；默认 NULL/未设置 | 质量、风险或完整度规则的明确版本。不同业务的规则含义独立，不要混用。 | 无独立索引/外键；通过所属记录关联 |
| basis | json | 不可空；默认 NULL/未设置 | 计算完整度使用的报告版本及输入上下文JSON，供审计复算。 | 无独立索引/外键；通过所属记录关联 |
| completeness_rate | decimal(7,4) | 可空；默认 NULL/未设置 | 0至1的完整度比率，页面再乘100显示；分母为0时NULL，不能默认100%。 | 无独立索引/外键；通过所属记录关联 |
| calculated_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC完整度计算时间。 | 索引:ix_completeness_latest |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_record_completeness` (
  `snapshot_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'snapshot_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'record_id',
  `required_count` int unsigned NOT NULL COMMENT 'required_count',
  `completed_count` int unsigned NOT NULL COMMENT 'completed_count',
  `missing_items` json NOT NULL COMMENT 'missing_items',
  `rule_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'rule_version',
  `basis` json NOT NULL COMMENT 'basis',
  `completeness_rate` decimal(7,4) DEFAULT NULL COMMENT '0至1；required_count为0时NULL',
  `calculated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`snapshot_id`),
  UNIQUE KEY `uq_health_record_completeness_scope` (`scope_code`,`data_mode`,`snapshot_id`),
  KEY `ix_completeness_latest` (`scope_code`,`data_mode`,`record_id`,`calculated_at`),
  CONSTRAINT `fk_a_4521703e29e96c6f6d` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_record_completeness_1` CHECK ((`completed_count` <= `required_count`)),
  CONSTRAINT `ck_a_health_record_completeness_2` CHECK ((((`required_count` = 0) and (`completeness_rate` is null)) or ((`required_count` > 0) and (`completeness_rate` is not null) and (`completeness_rate` = round((`completed_count` / `required_count`),4))))),
  CONSTRAINT `ck_a_health_record_completeness_3` CHECK (((json_type(`missing_items`) = _utf8mb4'ARRAY') and (json_length(`missing_items`) = (`required_count` - `completed_count`))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='完整度规则计算快照；不是样本PDF字段覆盖率';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_records

**用途：** 个人健康档案主记录；每个授权职工一份。

个人健康档案主记录；每个授权职工一份

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 索引:ix_records_history；索引:PRIMARY；索引:uq_record_employee_id；索引:uq_records_scope_id |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_employees.scope_code；索引:ix_records_history；索引:uq_record_employee；索引:uq_record_employee_id；索引:uq_records_scope_id |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_employees.data_mode；索引:ix_records_history；索引:uq_record_employee；索引:uq_record_employee_id；索引:uq_records_scope_id |
| employee_id | char(36) | 不可空；默认 NULL/未设置 | 职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。 | 外键→a_employees.employee_id；索引:ix_records_history；索引:uq_record_employee；索引:uq_record_employee_id |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_records_history |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_records` (
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '档案标识',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `employee_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '员工标识',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`record_id`),
  UNIQUE KEY `uq_records_scope_id` (`scope_code`,`data_mode`,`record_id`),
  UNIQUE KEY `uq_record_employee` (`scope_code`,`data_mode`,`employee_id`),
  UNIQUE KEY `uq_record_employee_id` (`scope_code`,`data_mode`,`record_id`,`employee_id`),
  KEY `ix_records_history` (`scope_code`,`data_mode`,`employee_id`,`created_at`,`record_id`),
  CONSTRAINT `fk_a_127fbd8fb407b86d59` FOREIGN KEY (`scope_code`, `data_mode`, `employee_id`) REFERENCES `a_employees` (`scope_code`, `data_mode`, `employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='个人健康档案主记录；每个授权职工一份';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_reports

**用途：** 报告版本；报告中的身份信息为原始快照。

主键(report_id,revision)；身份字段为报告快照。已审核版本冻结，修改须追加下一revision。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_series.report_id；外键→a_health_reports.report_id；索引:fk_a_f0ff16240dea439d74；索引:PRIMARY；索引:uq_report_record_review；索引:uq_report_scope_rev |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 索引:PRIMARY；索引:uq_report_record_review；索引:uq_report_scope_rev |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_series.scope_code；外键→a_health_records.scope_code；索引:ix_report_review；索引:ix_report_timeline；索引:uq_report_record_review；索引:uq_report_scope_rev |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_series.data_mode；外键→a_health_records.data_mode；索引:ix_report_review；索引:ix_report_timeline；索引:uq_report_record_review；索引:uq_report_scope_rev |
| record_id | char(36) | 可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:ix_report_timeline；索引:uq_report_record_review |
| prior_revision | int unsigned | 可空；默认 NULL/未设置 | 上一版本号；首版为NULL。历史版本不能覆盖，退回和修订会建立下一版本。 | 外键→a_health_reports.revision；索引:fk_a_f0ff16240dea439d74 |
| report_kind | enum('individual','organization','unknown') | 不可空；默认 NULL/未设置 | individual个人报告、organization组织报告或unknown。个人归档需要明确职工主档案。 | 无独立索引/外键；通过所属记录关联 |
| institution_raw | text | 可空；默认 NULL/未设置 | 报告原文中的体检机构；区别于企业所属单位，保留报告当时的原始记录。 | 无独立索引/外键；通过所属记录关联 |
| exam_date_raw | text | 可空；默认 NULL/未设置 | 报告原文体检日期字符串，可含中文年月日或不完整日期，不应直接强制转换。 | 无独立索引/外键；通过所属记录关联 |
| exam_date_verified | date | 可空；默认 NULL/未设置 | 经人工核定的有效公历体检日期。OCR回调不会自动核定此字段，个人报告发布前必需。 | 索引:ix_report_timeline |
| report_date_raw | text | 可空；默认 NULL/未设置 | 报告出具日期的原始字符串，与体检日期、检验日期和上传日期分别保存。 | 无独立索引/外键；通过所属记录关联 |
| exam_type_raw | text | 可空；默认 NULL/未设置 | 原文体检类别/检查类型，不直接代替职业健康分类或风险等级。 | 无独立索引/外键；通过所属记录关联 |
| exam_number_raw | text | 可空；默认 NULL/未设置 | 机构给出的体检号原文，可能含前导零和字母；不是系统report_id。 | 无独立索引/外键；通过所属记录关联 |
| age_raw | text | 可空；默认 NULL/未设置 | 报告上的年龄原文镜像，不回写企业出生日期，也不默认为当前年龄。 | 无独立索引/外键；通过所属记录关联 |
| field_values | json | 不可空；默认 NULL/未设置 | 版本内字段观察JSON数组。同一field_key可重复出现；每项保存原字段名、原值、证据ID和复核状态。 | 无独立索引/外键；通过所属记录关联 |
| dictionary_version | varchar(64) | 不可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 索引:ix_report_review；索引:uq_report_record_review |
| reviewed_snapshot_ref | varbinary(512) | 可空；默认 NULL/未设置 | 医生已审核内容的受限快照引用。本期指向review_snapshots表的报告ID和版本。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_report_review |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_reports` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '档案标识',
  `prior_revision` int unsigned DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_kind` enum('individual','organization','unknown') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `institution_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `exam_date_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `exam_date_verified` date DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_date_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `exam_type_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `exam_number_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `age_raw` text COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `field_values` json NOT NULL COMMENT '版本内字段观察 JSON',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  `reviewed_snapshot_ref` varbinary(512) DEFAULT NULL COMMENT '已审核快照受限引用',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`report_id`,`revision`),
  UNIQUE KEY `uq_report_scope_rev` (`scope_code`,`data_mode`,`report_id`,`revision`),
  UNIQUE KEY `uq_report_record_review` (`scope_code`,`data_mode`,`report_id`,`revision`,`record_id`,`review_status`),
  KEY `ix_report_timeline` (`scope_code`,`data_mode`,`record_id`,`exam_date_verified`),
  KEY `ix_report_review` (`scope_code`,`data_mode`,`review_status`,`created_at`),
  KEY `fk_a_f0ff16240dea439d74` (`report_id`,`prior_revision`),
  CONSTRAINT `fk_a_05b1eda8b9da69a036` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`) REFERENCES `a_report_series` (`scope_code`, `data_mode`, `report_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_636997cf227567f8e0` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_f0ff16240dea439d74` FOREIGN KEY (`report_id`, `prior_revision`) REFERENCES `a_health_reports` (`report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_reports_1` CHECK ((json_type(`field_values`) = _utf8mb4'ARRAY')),
  CONSTRAINT `ck_a_health_reports_2` CHECK ((((`revision` = 1) and (`prior_revision` is null)) or ((`revision` > 1) and (`prior_revision` = (`revision` - 1))))),
  CONSTRAINT `ck_a_health_reports_3` CHECK (((`review_status` <> _utf8mb4'confirmed') or ((`reviewed_snapshot_ref` is not null) and ((`report_kind` <> _utf8mb4'individual') or ((`record_id` is not null) and (`exam_date_verified` is not null))))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='报告版本；报告中的身份信息为原始快照';
```

写入保护触发器：
- a_ehr_a1ef09b94f1cd8ac9b：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_ca13845f03ea3ca462：BEFORE UPDATE

```sql
BEGIN
IF OLD.review_status IN ('confirmed','rejected') OR (EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) AND (NOT (NEW.report_id <=> OLD.report_id) OR NOT (NEW.revision <=> OLD.revision) OR NOT (NEW.scope_code <=> OLD.scope_code) OR NOT (NEW.data_mode <=> OLD.data_mode) OR NOT (NEW.record_id <=> OLD.record_id) OR NOT (NEW.prior_revision <=> OLD.prior_revision) OR NOT (NEW.report_kind <=> OLD.report_kind) OR NOT (NEW.institution_raw <=> OLD.institution_raw) OR NOT (NEW.exam_date_raw <=> OLD.exam_date_raw) OR NOT (NEW.exam_date_verified <=> OLD.exam_date_verified) OR NOT (NEW.report_date_raw <=> OLD.report_date_raw) OR NOT (NEW.exam_type_raw <=> OLD.exam_type_raw) OR NOT (NEW.exam_number_raw <=> OLD.exam_number_raw) OR NOT (NEW.age_raw <=> OLD.age_raw) OR NOT (NEW.field_values <=> OLD.field_values) OR NOT (NEW.dictionary_version <=> OLD.dictionary_version) OR NOT (NEW.created_at <=> OLD.created_at))) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_health_risk_assessments

**用途：** 平台风险评估；不替代职业健康正式医学结论。

平台风险评估；不替代职业健康正式医学结论

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| assessment_id | char(36) | 不可空；默认 NULL/未设置 | 独立的平台风险评估UUID；与原报告医生正式结论分开。 | 索引:PRIMARY；索引:uq_assessment_record；索引:uq_health_risk_assessments_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_records.scope_code；外键→a_health_records.scope_code；索引:fk_a_67fd881a7ef17bfa95；索引:ix_risk_latest；索引:uq_assessment_record；索引:uq_health_risk_assessments_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_records.data_mode；外键→a_health_records.data_mode；索引:fk_a_67fd881a7ef17bfa95；索引:ix_risk_latest；索引:uq_assessment_record；索引:uq_health_risk_assessments_scope |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；外键→a_health_records.record_id；索引:fk_a_67fd881a7ef17bfa95；索引:ix_risk_latest；索引:uq_assessment_record |
| employee_id | char(36) | 不可空；默认 NULL/未设置 | 职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。 | 外键→a_health_records.employee_id；索引:fk_a_67fd881a7ef17bfa95 |
| assessment_time | datetime(6) | 不可空；默认 NULL/未设置 | 本次风险评估所对应的UTC时间，不是报告上传时间。 | 索引:ix_risk_latest |
| risk_score | decimal(12,4) | 可空；默认 NULL/未设置 | 按已批准规则得到的风险评分；没有规则时为空，不编造默认分数。 | 无独立索引/外键；通过所属记录关联 |
| risk_level | enum('unknown','low','medium','high','critical') | 不可空；默认 NULL/未设置 | unknown未评估，low低、medium中、high高、critical极高风险；未评估不得默认低风险。 | 无独立索引/外键；通过所属记录关联 |
| assessment_type | enum('rule','clinician','ai_assisted') | 不可空；默认 NULL/未设置 | rule规则评估、clinician人工评估、ai_assisted模型辅助；均需保留规则和复核来源。 | 无独立索引/外键；通过所属记录关联 |
| rule_version | varchar(64) | 不可空；默认 NULL/未设置 | 质量、风险或完整度规则的明确版本。不同业务的规则含义独立，不要混用。 | 无独立索引/外键；通过所属记录关联 |
| assessment_status | enum('draft','needs_review','confirmed','superseded') | 不可空；默认 NULL/未设置 | draft草稿、needs_review待核对、confirmed已确认、superseded已被后续评估替代。 | 索引:ix_risk_latest |
| reviewer_ref | varbinary(256) | 可空；默认 NULL/未设置 | 审核人员受限标识，当前由登录医生账号确定，不从前端提交审核人ID。 | 无独立索引/外键；通过所属记录关联 |
| reviewed_at | datetime(6) | 可空；默认 NULL/未设置 | UTC人工审核更正申请的时间；待审为NULL。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_risk_assessments` (
  `assessment_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'assessment_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'record_id',
  `employee_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'employee_id',
  `assessment_time` datetime(6) NOT NULL COMMENT 'assessment_time',
  `risk_score` decimal(12,4) DEFAULT NULL COMMENT 'risk_score',
  `risk_level` enum('unknown','low','medium','high','critical') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'risk_level',
  `assessment_type` enum('rule','clinician','ai_assisted') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'assessment_type',
  `rule_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'rule_version',
  `assessment_status` enum('draft','needs_review','confirmed','superseded') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'assessment_status',
  `reviewer_ref` varbinary(256) DEFAULT NULL COMMENT 'reviewer_ref',
  `reviewed_at` datetime(6) DEFAULT NULL COMMENT 'reviewed_at',
  PRIMARY KEY (`assessment_id`),
  UNIQUE KEY `uq_health_risk_assessments_scope` (`scope_code`,`data_mode`,`assessment_id`),
  UNIQUE KEY `uq_assessment_record` (`scope_code`,`data_mode`,`record_id`,`assessment_id`),
  KEY `ix_risk_latest` (`scope_code`,`data_mode`,`record_id`,`assessment_status`,`assessment_time`),
  KEY `fk_a_67fd881a7ef17bfa95` (`scope_code`,`data_mode`,`record_id`,`employee_id`),
  CONSTRAINT `fk_a_67fd881a7ef17bfa95` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`, `employee_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`, `employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_e132bedb907dfe817d` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_health_risk_assessments_1` CHECK (((`assessment_status` <> _utf8mb4'confirmed') or ((`reviewer_ref` is not null) and (`reviewed_at` is not null) and (`risk_level` <> _utf8mb4'unknown'))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='平台风险评估；不替代职业健康正式医学结论';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_risk_factors

**用途：** 可解释的风险因素；追溯已发布报告、指标和原页证据。

可解释的风险因素；追溯已发布报告、指标和原页证据

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| factor_id | char(36) | 不可空；默认 NULL/未设置 | 一条可解释风险因素UUID，与具体评估关联。 | 索引:PRIMARY |
| assessment_id | char(36) | 不可空；默认 NULL/未设置 | 独立的平台风险评估UUID；与原报告医生正式结论分开。 | 外键→a_health_risk_report_sources.assessment_id；索引:fk_a_bd6ef0fda894883506 |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_evidence.report_id；外键→a_health_risk_report_sources.report_id；外键→a_health_items.report_id；索引:fk_a_6407e7a8866fc6d854；索引:fk_a_bd6ef0fda894883506；索引:fk_a_c380966684225f66bb |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_evidence.revision；外键→a_health_risk_report_sources.revision；外键→a_health_items.revision；索引:fk_a_6407e7a8866fc6d854；索引:fk_a_bd6ef0fda894883506；索引:fk_a_c380966684225f66bb |
| item_id | char(36) | 可空；默认 NULL/未设置 | 报告版本内一条检查结果的UUID；同项目多次测量可有多行，不能按item_code覆盖。 | 外键→a_health_items.item_id；索引:fk_a_c380966684225f66bb |
| evidence_id | char(36) | 不可空；默认 NULL/未设置 | 报告版本内的证据UUID，用于连接原页、字段观察和检查项目；不得跨版本引用。 | 外键→a_report_evidence.evidence_id；索引:fk_a_6407e7a8866fc6d854 |
| factor_name | varchar(255) | 不可空；默认 NULL/未设置 | 该风险因素的名称，不是直接从异常标记推断出的诊断。 | 无独立索引/外键；通过所属记录关联 |
| result_raw | mediumtext | 可空；默认 NULL/未设置 | 检查结果原文，可为复合值、定性文字、滴度、半定量或数值字符串；不得强制转Double。 | 无独立索引/外键；通过所属记录关联 |
| reason | text | 不可空；默认 NULL/未设置 | 业务处理或风险判定的具体理由；内容应与原件、规则和权限一致。 | 无独立索引/外键；通过所属记录关联 |
| contribution | decimal(12,4) | 可空；默认 NULL/未设置 | 风险规则给定的贡献值；没有批准规则时保持NULL。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_risk_factors` (
  `factor_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'factor_id',
  `assessment_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'assessment_id',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'report_id',
  `revision` int unsigned NOT NULL COMMENT 'revision',
  `item_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT 'item_id',
  `evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'evidence_id',
  `factor_name` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'factor_name',
  `result_raw` mediumtext COLLATE utf8mb4_0900_bin COMMENT 'result_raw',
  `reason` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'reason',
  `contribution` decimal(12,4) DEFAULT NULL COMMENT 'contribution',
  PRIMARY KEY (`factor_id`),
  KEY `fk_a_bd6ef0fda894883506` (`assessment_id`,`report_id`,`revision`),
  KEY `fk_a_c380966684225f66bb` (`report_id`,`revision`,`item_id`),
  KEY `fk_a_6407e7a8866fc6d854` (`report_id`,`revision`,`evidence_id`),
  CONSTRAINT `fk_a_6407e7a8866fc6d854` FOREIGN KEY (`report_id`, `revision`, `evidence_id`) REFERENCES `a_report_evidence` (`report_id`, `revision`, `evidence_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_bd6ef0fda894883506` FOREIGN KEY (`assessment_id`, `report_id`, `revision`) REFERENCES `a_health_risk_report_sources` (`assessment_id`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_c380966684225f66bb` FOREIGN KEY (`report_id`, `revision`, `item_id`) REFERENCES `a_health_items` (`report_id`, `revision`, `item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='可解释的风险因素；追溯已发布报告、指标和原页证据';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_risk_report_sources

**用途：** 风险评估所依据的已发布报告版本；保证同一职工与档案。

风险评估所依据的已发布报告版本；保证同一职工与档案

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_risk_assessments.scope_code；外键→a_report_publications.scope_code；索引:fk_a_e32c6c517b6a7f41a1；索引:fk_a_e9df70e94c5934bb53 |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_risk_assessments.data_mode；外键→a_report_publications.data_mode；索引:fk_a_e32c6c517b6a7f41a1；索引:fk_a_e9df70e94c5934bb53 |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_risk_assessments.record_id；外键→a_report_publications.record_id；索引:fk_a_e32c6c517b6a7f41a1；索引:fk_a_e9df70e94c5934bb53 |
| assessment_id | char(36) | 不可空；默认 NULL/未设置 | 独立的平台风险评估UUID；与原报告医生正式结论分开。 | 外键→a_health_risk_assessments.assessment_id；索引:fk_a_e32c6c517b6a7f41a1；索引:PRIMARY |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_publications.report_id；索引:fk_a_e9df70e94c5934bb53；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_publications.revision；索引:fk_a_e9df70e94c5934bb53；索引:PRIMARY |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_risk_report_sources` (
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'record_id',
  `assessment_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'assessment_id',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'report_id',
  `revision` int unsigned NOT NULL COMMENT 'revision',
  PRIMARY KEY (`assessment_id`,`report_id`,`revision`),
  KEY `fk_a_e32c6c517b6a7f41a1` (`scope_code`,`data_mode`,`record_id`,`assessment_id`),
  KEY `fk_a_e9df70e94c5934bb53` (`scope_code`,`data_mode`,`record_id`,`report_id`,`revision`),
  CONSTRAINT `fk_a_e32c6c517b6a7f41a1` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`, `assessment_id`) REFERENCES `a_health_risk_assessments` (`scope_code`, `data_mode`, `record_id`, `assessment_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_e9df70e94c5934bb53` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`, `report_id`, `revision`) REFERENCES `a_report_publications` (`scope_code`, `data_mode`, `record_id`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='风险评估所依据的已发布报告版本；保证同一职工与档案';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_health_schema_migration

**用途：** 本模块结构迁移版本及输入校验摘要。

本模块结构迁移版本及输入校验摘要

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| version | varchar(64) | 不可空；默认 NULL/未设置 | 数据库迁移版本标识，与报告revision和字典版本分别管理。 | 索引:PRIMARY |
| checksum | char(64) | 不可空；默认 NULL/未设置 | 实际执行SQL文件的SHA256，重跑时必须一致，不允许无声替换。 | 无独立索引/外键；通过所属记录关联 |
| source_sha256 | char(64) | 不可空；默认 NULL/未设置 | 该迁移引用的原始字段工作簿SHA256。 | 无独立索引/外键；通过所属记录关联 |
| description | varchar(500) | 不可空；默认 NULL/未设置 | 迁移用途说明。 | 无独立索引/外键；通过所属记录关联 |
| applied_by | varchar(255) | 不可空；默认 NULL/未设置 | 执行数据库迁移的数据库账号，用于运维追溯。 | 无独立索引/外键；通过所属记录关联 |
| applied_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC迁移完成时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_health_schema_migration` (
  `version` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'version',
  `checksum` char(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'checksum',
  `source_sha256` char(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'source_sha256',
  `description` varchar(500) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'description',
  `applied_by` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'applied_by',
  `applied_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='本模块结构迁移版本及输入校验摘要';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_medical_field_mapping

**用途：** 机构字段及检查项目别名；允许同名多候选。

机构字段及检查项目别名；允许同名多候选

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| mapping_key | varchar(64) | 不可空；默认 NULL/未设置 | 机构字段/项目别名映射的稳定编码。用于保留来源、映射版本和多候选关系。 | 索引:PRIMARY |
| mapping_version | varchar(64) | 不可空；默认 NULL/未设置 | 映射目录的发布版本。不能把未经确认的旧规则直接修改成新语义；应新版本发布。 | 外键→a_standard_field_dictionary.dictionary_version；索引:fk_a_e359874004c96f25fe；索引:ix_field_mapping_lookup；索引:PRIMARY |
| entry_kind | enum('field','item') | 不可空；默认 NULL/未设置 | field对应67类标准报告字段，item对应动态检查项目；两种目标字段互斥。 | 索引:ix_field_mapping_lookup |
| institution_key | varchar(255) | 可空；默认 NULL/未设置 | 机构匹配上下文。相同原标签在不同机构可能含义不同，不应只按标签名自动确认。 | 无独立索引/外键；通过所属记录关联 |
| template_key | varchar(64) | 可空；默认 NULL/未设置 | 报告版式/模板稳定编码。用于机构布局识别，不代表一个检查项目或报告实例。 | 外键→a_report_template_mapping.template_key；索引:fk_a_fbe643e4a322ce9597 |
| template_version | varchar(64) | 可空；默认 NULL/未设置 | 字段映射所依赖的模板发布版本；与template_key同时为空或同时提供。 | 外键→a_report_template_mapping.mapping_version；索引:fk_a_fbe643e4a322ce9597 |
| original_label | varchar(255) | 不可空；默认 NULL/未设置 | 原件上看到的标签，不应把它改成标准名称后丢掉原文；签章标签也不是签章真实性证明。 | 无独立索引/外键；通过所属记录关联 |
| normalized_lookup | varchar(255) | 不可空；默认 NULL/未设置 | 仅用于检索匹配的标签形式（NFKC/空白整理），不得据此修改原医学结果或自动解决语义冲突。 | 索引:ix_field_mapping_lookup |
| field_key | varchar(64) | 可空；默认 NULL/未设置 | 标准报告字段编码，例如full_name、exam_date、hazard_factors。共享JSON数组依赖此键定位观察。 | 外键→a_standard_field_dictionary.field_key；索引:fk_a_e359874004c96f25fe |
| item_code | varchar(64) | 可空；默认 NULL/未设置 | 医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。 | 外键→a_medical_item_dictionary.item_code；索引:fk_a_bfaab89e64413517b1 |
| dictionary_version | varchar(64) | 可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 外键→a_medical_item_dictionary.dictionary_version；索引:fk_a_bfaab89e64413517b1 |
| status | enum('needs_confirmation','context_required','lexical_only','confirmed','retired') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 无独立索引/外键；通过所属记录关联 |
| validation_ref | varbinary(256) | 可空；默认 NULL/未设置 | 验证资料或来源行的受限引用。本次种子中的xlsx行引用不等于临床确认或原件验证。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_medical_field_mapping` (
  `mapping_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `mapping_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `entry_kind` enum('field','item') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `institution_key` varchar(255) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `template_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `template_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_label` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `normalized_lookup` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `field_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `item_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('needs_confirmation','context_required','lexical_only','confirmed','retired') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `validation_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  PRIMARY KEY (`mapping_key`,`mapping_version`),
  KEY `ix_field_mapping_lookup` (`mapping_version`,`entry_kind`,`normalized_lookup`),
  KEY `fk_a_fbe643e4a322ce9597` (`template_key`,`template_version`),
  KEY `fk_a_bfaab89e64413517b1` (`item_code`,`dictionary_version`),
  KEY `fk_a_e359874004c96f25fe` (`field_key`,`mapping_version`),
  CONSTRAINT `fk_a_bfaab89e64413517b1` FOREIGN KEY (`item_code`, `dictionary_version`) REFERENCES `a_medical_item_dictionary` (`item_code`, `dictionary_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_e359874004c96f25fe` FOREIGN KEY (`field_key`, `mapping_version`) REFERENCES `a_standard_field_dictionary` (`field_key`, `dictionary_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_fbe643e4a322ce9597` FOREIGN KEY (`template_key`, `template_version`) REFERENCES `a_report_template_mapping` (`template_key`, `mapping_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_medical_field_mapping_1` CHECK (((`item_code` is null) = (`dictionary_version` is null))),
  CONSTRAINT `ck_a_medical_field_mapping_2` CHECK (((`template_key` is null) = (`template_version` is null))),
  CONSTRAINT `ck_a_medical_field_mapping_3` CHECK ((((`entry_kind` = _utf8mb4'field') and (`field_key` is not null) and (`item_code` is null)) or ((`entry_kind` = _utf8mb4'item') and (`field_key` is null) and (`item_code` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='机构字段及检查项目别名；允许同名多候选';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_medical_item_dictionary

**用途：** 655种检查项目；词汇映射不等于医学语义确认。

655种检查项目；词汇映射不等于医学语义确认

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| item_code | varchar(64) | 不可空；默认 NULL/未设置 | 医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。 | 索引:PRIMARY |
| dictionary_version | varchar(64) | 不可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 索引:ix_dictionary_name；索引:PRIMARY |
| standard_name | varchar(255) | 不可空；默认 NULL/未设置 | 字典中的标准项目名称。当前大部分仅为词汇级整理，不表示医学语义已核定。 | 索引:ix_dictionary_name |
| semantic_scope | varchar(255) | 不可空；默认 NULL/未设置 | 项目语义适用范围与注意事项，标本、侧别、方法等仍可能需要原件复核。 | 无独立索引/外键；通过所属记录关联 |
| status | enum('lexical_only','needs_confirmation','confirmed','retired') | 不可空；默认 NULL/未设置 | lexical_only词汇级，needs_confirmation待确认，confirmed已验证，retired已退役；种子532条词汇级、123条待确认。 | 无独立索引/外键；通过所属记录关联 |
| validation_ref | varbinary(256) | 可空；默认 NULL/未设置 | 验证资料或来源行的受限引用。本次种子中的xlsx行引用不等于临床确认或原件验证。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_medical_item_dictionary` (
  `item_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `standard_name` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `semantic_scope` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('lexical_only','needs_confirmation','confirmed','retired') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `validation_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  PRIMARY KEY (`item_code`,`dictionary_version`),
  KEY `ix_dictionary_name` (`dictionary_version`,`standard_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='655种检查项目；词汇映射不等于医学语义确认';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_medical_unit_mapping

**用途：** 单位键目录；未批准转换不得生成标准值。

81个原单位键。当前无已批准转换；390条项目关联观察另见a_medical_unit_observations。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| unit_mapping_key | varchar(64) | 不可空；默认 NULL/未设置 | 单位映射目录编码。04表390行只有81个不同原单位键，具体项目关系保存在observations。 | 索引:PRIMARY |
| mapping_version | varchar(64) | 不可空；默认 NULL/未设置 | 映射目录的发布版本。不能把未经确认的旧规则直接修改成新语义；应新版本发布。 | 索引:ix_unit_mapping_lookup；索引:PRIMARY |
| unit_raw | varchar(255) | 不可空；默认 NULL/未设置 | 报告上的原始单位字符串，保留大小写、指数、符号和空格；不能擅自转换。 | 索引:ix_unit_mapping_lookup |
| unit_normalized | varchar(255) | 可空；默认 NULL/未设置 | 经批准规则转换后的单位。当前批准数为0，业务入库应为NULL。 | 无独立索引/外键；通过所属记录关联 |
| item_code | varchar(64) | 可空；默认 NULL/未设置 | 医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。 | 外键→a_medical_item_dictionary.item_code；索引:fk_a_ccfe14369aa094a7fb |
| dictionary_version | varchar(64) | 可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 外键→a_medical_item_dictionary.dictionary_version；索引:fk_a_ccfe14369aa094a7fb |
| rule_ref | varbinary(256) | 可空；默认 NULL/未设置 | 经过批准的项目级转换规则引用；只有observed_raw_only的单位观察没有此批准依据。 | 无独立索引/外键；通过所属记录关联 |
| status | enum('observed_raw_only','confirmed','retired') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_medical_unit_mapping` (
  `unit_mapping_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `mapping_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `unit_raw` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '原单位',
  `unit_normalized` varchar(255) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `item_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `rule_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('observed_raw_only','confirmed','retired') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  PRIMARY KEY (`unit_mapping_key`,`mapping_version`),
  KEY `ix_unit_mapping_lookup` (`mapping_version`,`unit_raw`),
  KEY `fk_a_ccfe14369aa094a7fb` (`item_code`,`dictionary_version`),
  CONSTRAINT `fk_a_ccfe14369aa094a7fb` FOREIGN KEY (`item_code`, `dictionary_version`) REFERENCES `a_medical_item_dictionary` (`item_code`, `dictionary_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_medical_unit_mapping_1` CHECK (((`item_code` is null) = (`dictionary_version` is null))),
  CONSTRAINT `ck_a_medical_unit_mapping_2` CHECK (((`status` <> _utf8mb4'confirmed') or ((`unit_normalized` is not null) and (`rule_ref` is not null) and (`item_code` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='单位键目录；未批准转换不得生成标准值';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_medical_unit_observations

**用途：** 390条项目与原单位观察；81个重复单位键无损关联，不是批准规则。

390条项目与原单位观察；81个重复单位键无损关联，不是批准规则

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| dictionary_version | varchar(64) | 不可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 外键→a_medical_item_dictionary.dictionary_version；索引:fk_a_b641ea56be45735f0a；索引:PRIMARY |
| source_row | smallint unsigned | 不可空；默认 NULL/未设置 | 输入工作簿中的原始行号，用于复核本行字典来源。 | 索引:PRIMARY |
| item_code | varchar(64) | 不可空；默认 NULL/未设置 | 医学检查项目字典的稳定编码。可以为NULL表示暂未匹配；不能仅凭同名宣称语义一致。 | 外键→a_medical_item_dictionary.item_code；索引:fk_a_b641ea56be45735f0a |
| unit_mapping_key | varchar(64) | 不可空；默认 NULL/未设置 | 单位映射目录编码。04表390行只有81个不同原单位键，具体项目关系保存在observations。 | 外键→a_medical_unit_mapping.unit_mapping_key；索引:fk_a_83b42c7f4638600144 |
| unit_mapping_version | varchar(64) | 不可空；默认 NULL/未设置 | 该结果采用的单位映射发布版本，与unit_mapping_key配对。 | 外键→a_medical_unit_mapping.mapping_version；索引:fk_a_83b42c7f4638600144 |
| unit_raw | varchar(255) | 不可空；默认 NULL/未设置 | 报告上的原始单位字符串，保留大小写、指数、符号和空格；不能擅自转换。 | 无独立索引/外键；通过所属记录关联 |
| approval_status | varchar(30) | 不可空；默认 NULL/未设置 | 单位观察的批准状态；目前390条均为未批准，不应据此做单位换算。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_medical_unit_observations` (
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'dictionary_version',
  `source_row` smallint unsigned NOT NULL COMMENT 'source_row',
  `item_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'item_code',
  `unit_mapping_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'unit_mapping_key',
  `unit_mapping_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'unit_mapping_version',
  `unit_raw` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'unit_raw',
  `approval_status` varchar(30) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'approval_status',
  PRIMARY KEY (`dictionary_version`,`source_row`),
  KEY `fk_a_b641ea56be45735f0a` (`item_code`,`dictionary_version`),
  KEY `fk_a_83b42c7f4638600144` (`unit_mapping_key`,`unit_mapping_version`),
  CONSTRAINT `fk_a_83b42c7f4638600144` FOREIGN KEY (`unit_mapping_key`, `unit_mapping_version`) REFERENCES `a_medical_unit_mapping` (`unit_mapping_key`, `mapping_version`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_b641ea56be45735f0a` FOREIGN KEY (`item_code`, `dictionary_version`) REFERENCES `a_medical_item_dictionary` (`item_code`, `dictionary_version`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='390条项目与原单位观察；81个重复单位键无损关联，不是批准规则';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_binding_challenges

**用途：** 先校验后预览，再显式确认；不接受客户端回传资料覆盖。

先校验后预览，再显式确认；不接受客户端回传资料覆盖

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| challenge_id | char(36) | 不可空；默认 NULL/未设置 | 已验证企业身份后生成的短期个人信息确认挑战UUID。 | 索引:PRIMARY |
| mini_user_id | char(36) | 不可空；默认 NULL/未设置 | 微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。 | 外键→a_miniapp_users.mini_user_id；索引:ix_binding_challenge_user |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_miniapp_users.data_mode；索引:ix_binding_challenge_user |
| binding_code_id | char(36) | 不可空；默认 NULL/未设置 | 个人企业身份验证码签发记录UUID；后续预览、确认和绑定均精确关联此签发。 | 外键→a_employee_binding_codes.binding_code_id；索引:fk_challenge_code |
| challenge_hash | binary(32) | 不可空；默认 NULL/未设置 | 随机确认凭据的SHA256，只允许同一小程序用户在有效期内确认。 | 索引:uq_binding_challenge |
| profile_row_version | int unsigned | 不可空；默认 NULL/未设置 | 身份核验返回资料时的花名册版本，后台修改后旧确认请求会被拒绝。 | 无独立索引/外键；通过所属记录关联 |
| profile_digest | binary(32) | 不可空；默认 NULL/未设置 | 实际展示给职工的资料摘要，包括脱敏手机号；防止确认与所见资料不一致。 | 无独立索引/外键；通过所属记录关联 |
| expires_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC确认凭据失效，最多10分钟且不晚于验证码。会话默认2小时、确认凭据最多10分钟、个人码默认24小时，具体以本记录为准。 | 索引:ix_binding_challenge_user |
| used_at | datetime(6) | 可空；默认 NULL/未设置 | UTC个人信息确认成功时间，NULL为尚未确认。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_binding_challenges` (
  `challenge_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '确认挑战UUID',
  `mini_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '只能由发起预览的同一小程序用户确认',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '数据模式',
  `binding_code_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '已校验的个人验证码',
  `challenge_hash` binary(32) NOT NULL COMMENT '随机确认凭据摘要',
  `profile_row_version` int unsigned NOT NULL COMMENT '预览时的授权资料版本',
  `profile_digest` binary(32) NOT NULL COMMENT '预览资料摘要，防止确认过期信息',
  `expires_at` datetime(6) NOT NULL COMMENT 'UTC确认凭据失效，最多10分钟且不晚于验证码',
  `used_at` datetime(6) DEFAULT NULL COMMENT '确认成功后消费时间',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC预览时间',
  PRIMARY KEY (`challenge_id`),
  UNIQUE KEY `uq_binding_challenge` (`challenge_hash`),
  KEY `ix_binding_challenge_user` (`mini_user_id`,`data_mode`,`expires_at`),
  KEY `fk_challenge_code` (`binding_code_id`),
  CONSTRAINT `fk_challenge_code` FOREIGN KEY (`binding_code_id`) REFERENCES `a_employee_binding_codes` (`binding_code_id`),
  CONSTRAINT `fk_challenge_user` FOREIGN KEY (`mini_user_id`, `data_mode`) REFERENCES `a_miniapp_users` (`mini_user_id`, `data_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='先校验后预览，再显式确认；不接受客户端回传资料覆盖';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_correction_requests

**用途：** 小程序绑定前的信息更正申请；人工审核通过才更新名册，保留前后快照。

小程序绑定前的信息更正申请；人工审核通过才更新名册，保留前后快照

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| correction_id | char(36) | 不可空；默认 NULL/未设置 | 绑定前资料更正申请UUID；用于查询和医生审核，不是职工ID。 | 索引:PRIMARY |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_records.scope_code；索引:fk_correction_record；索引:ix_correction_queue；索引:uq_correction_review_request |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_records.data_mode；外键→a_miniapp_users.data_mode；索引:fk_correction_record；索引:ix_correction_owner；索引:ix_correction_queue；索引:uq_correction_pending_record；索引:uq_correction_pending_user；索引:uq_correction_request；索引:uq_correction_review_request |
| mini_user_id | char(36) | 不可空；默认 NULL/未设置 | 微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。 | 外键→a_miniapp_users.mini_user_id；索引:ix_correction_owner；索引:uq_correction_request |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:fk_correction_record |
| challenge_id | char(36) | 不可空；默认 NULL/未设置 | 已验证企业身份后生成的短期个人信息确认挑战UUID。 | 外键→a_miniapp_binding_challenges.challenge_id；索引:fk_correction_challenge |
| request_key | varchar(96) | 不可空；默认 NULL/未设置 | 更正申请按微信用户和模式唯一的幂等键，同键不能变更提交内容。 | 索引:uq_correction_request |
| profile_row_version | bigint unsigned | 不可空；默认 NULL/未设置 | 身份核验返回资料时的花名册版本，后台修改后旧确认请求会被拒绝。 | 无独立索引/外键；通过所属记录关联 |
| before_snapshot | json | 不可空；默认 NULL/未设置 | 申请时职工已核验的资料快照JSON，联系电话脱敏；保留原资料便于审核。 | 无独立索引/外键；通过所属记录关联 |
| requested_changes | json | 不可空；默认 NULL/未设置 | 仅保存白名单字段的申请值JSON，不会在提交时修改正式资料。 | 无独立索引/外键；通过所属记录关联 |
| request_reason | varchar(600) | 不可空；默认 NULL/未设置 | 职工说明资料错误及申请更正的原因，必填1至600字符。 | 无独立索引/外键；通过所属记录关联 |
| request_status | enum('pending','approved','rejected') | 不可空；默认 pending | pending待审核、approved已通过、rejected已驳回。通过后仍须重新验证并本人确认绑定。 | 索引:ix_correction_queue |
| row_version | bigint unsigned | 不可空；默认 1 | 更正申请处理版本；待审为1，审核后递增，防止重复或并发处理覆盖。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_correction_owner；索引:ix_correction_queue |
| reviewed_by_ref | varbinary(256) | 可空；默认 NULL/未设置 | 处理更正申请的后台医生账号引用，由服务端取当前登录人。 | 无独立索引/外键；通过所属记录关联 |
| reviewed_at | datetime(6) | 可空；默认 NULL/未设置 | UTC人工审核更正申请的时间；待审为NULL。 | 无独立索引/外键；通过所属记录关联 |
| review_note | varchar(600) | 可空；默认 NULL/未设置 | 医生核对依据或驳回原因，必填；不会作为医学结论。 | 无独立索引/外键；通过所属记录关联 |
| review_request_key | varchar(96) | 可空；默认 NULL/未设置 | 医生审核幂等键，同结论同审核人可重试，不能覆盖既有处理结果。 | 索引:uq_correction_review_request |
| after_snapshot | json | 可空；默认 NULL/未设置 | 审核通过后当前名册的脱敏资料快照JSON；驳回或待审时NULL。 | 无独立索引/外键；通过所属记录关联 |
| pending_user_id | char(36) | 可空；默认 NULL/未设置 | 生成列：待审时为mini_user_id，处理后NULL；唯一索引限制一用户一份待审申请。 | 索引:uq_correction_pending_user |
| pending_record_id | char(36) | 可空；默认 NULL/未设置 | 生成列：待审时为record_id，处理后NULL；唯一索引限制一职工一份待审申请。 | 索引:uq_correction_pending_record |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_correction_requests` (
  `correction_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '信息更正申请UUID',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '原职工所属企业档案池，申请不能改变授权范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '正式与隔离测试模式',
  `mini_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '提交申请的已核验但未绑定微信用户',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '确认凭据指向的职工档案，不接受客户端指定',
  `challenge_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '提交时有效的本人资料确认凭据记录',
  `request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '用户侧申请幂等键，同用户同键不可提交不同内容',
  `profile_row_version` bigint unsigned NOT NULL COMMENT '申请时企业名册版本，审核通过必须与当前一致',
  `before_snapshot` json NOT NULL COMMENT '申请时展示的后台资料快照，手机号脱敏',
  `requested_changes` json NOT NULL COMMENT '白名单字段的申请值，不直接修改正式名册',
  `request_reason` varchar(600) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '职工更正原因，不能留空',
  `request_status` enum('pending','approved','rejected') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'pending' COMMENT '待审核、已通过、已驳回',
  `row_version` bigint unsigned NOT NULL DEFAULT '1' COMMENT '申请处理版本，防止重复或并发审核覆盖',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC提交时间',
  `reviewed_by_ref` varbinary(256) DEFAULT NULL COMMENT '实际审核医生账号引用，不由小程序提交',
  `reviewed_at` datetime(6) DEFAULT NULL COMMENT 'UTC人工审核时间',
  `review_note` varchar(600) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '医生核对依据或驳回原因',
  `review_request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '后台审核幂等键，不允许用新键覆盖既有结论',
  `after_snapshot` json DEFAULT NULL COMMENT '通过后资料快照，手机号脱敏；驳回为空',
  `pending_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS ((case when (`request_status` = _utf8mb4'pending') then `mini_user_id` else NULL end)) STORED COMMENT '一微信用户最多一份待审申请',
  `pending_record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS ((case when (`request_status` = _utf8mb4'pending') then `record_id` else NULL end)) STORED COMMENT '一职工最多一份待审申请，期间不能确认绑定',
  PRIMARY KEY (`correction_id`),
  UNIQUE KEY `uq_correction_request` (`data_mode`,`mini_user_id`,`request_key`),
  UNIQUE KEY `uq_correction_pending_user` (`data_mode`,`pending_user_id`),
  UNIQUE KEY `uq_correction_pending_record` (`data_mode`,`pending_record_id`),
  UNIQUE KEY `uq_correction_review_request` (`scope_code`,`data_mode`,`review_request_key`),
  KEY `ix_correction_queue` (`scope_code`,`data_mode`,`request_status`,`created_at`),
  KEY `ix_correction_owner` (`mini_user_id`,`data_mode`,`created_at`),
  KEY `fk_correction_record` (`scope_code`,`data_mode`,`record_id`),
  KEY `fk_correction_challenge` (`challenge_id`),
  CONSTRAINT `fk_correction_challenge` FOREIGN KEY (`challenge_id`) REFERENCES `a_miniapp_binding_challenges` (`challenge_id`),
  CONSTRAINT `fk_correction_record` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`),
  CONSTRAINT `fk_correction_user` FOREIGN KEY (`mini_user_id`, `data_mode`) REFERENCES `a_miniapp_users` (`mini_user_id`, `data_mode`),
  CONSTRAINT `ck_correction_payload` CHECK (((json_type(`before_snapshot`) = _utf8mb4'OBJECT') and (json_type(`requested_changes`) = _utf8mb4'OBJECT'))),
  CONSTRAINT `ck_correction_result` CHECK ((((`request_status` = _utf8mb4'approved') and (`after_snapshot` is not null)) or ((`request_status` in (_utf8mb4'pending',_utf8mb4'rejected')) and (`after_snapshot` is null)))),
  CONSTRAINT `ck_correction_review` CHECK ((((`request_status` = _utf8mb4'pending') and (`reviewed_at` is null) and (`reviewed_by_ref` is null) and (`review_note` is null) and (`review_request_key` is null) and (`after_snapshot` is null)) or ((`request_status` in (_utf8mb4'approved',_utf8mb4'rejected')) and (`reviewed_at` is not null) and (`reviewed_by_ref` is not null) and (`review_note` is not null) and (`review_request_key` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='小程序绑定前的信息更正申请；人工审核通过才更新名册，保留前后快照';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_employee_bindings

**用途：** 正式职工绑定及历史；只后台可解除，不能自助换成他人身份。

正式职工绑定及历史；只后台可解除，不能自助换成他人身份

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| binding_id | char(36) | 不可空；默认 NULL/未设置 | 正式微信用户与职工档案关联的UUID；解除后保留历史。 | 索引:PRIMARY |
| mini_user_id | char(36) | 不可空；默认 NULL/未设置 | 微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。 | 外键→a_miniapp_users.mini_user_id；索引:fk_binding_user |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_records.scope_code；索引:fk_binding_record |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_records.data_mode；外键→a_miniapp_users.data_mode；索引:fk_binding_record；索引:fk_binding_user；索引:uq_wx_active_employee；索引:uq_wx_active_user |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_records.record_id；索引:fk_binding_record |
| binding_code_id | char(36) | 不可空；默认 NULL/未设置 | 个人企业身份验证码签发记录UUID；后续预览、确认和绑定均精确关联此签发。 | 外键→a_employee_binding_codes.binding_code_id；索引:fk_binding_code |
| binding_status | enum('active','revoked') | 不可空；默认 active | active为有效绑定、revoked为后台核对后解除。 | 无独立索引/外键；通过所属记录关联 |
| confirmed_digest | binary(32) | 不可空；默认 NULL/未设置 | 职工明确确认的后台资料摘要，不是自动计算的健康风险。 | 无独立索引/外键；通过所属记录关联 |
| confirmed_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC职工确认本人信息并完成绑定的时间。 | 无独立索引/外键；通过所属记录关联 |
| revoked_at | datetime(6) | 可空；默认 NULL/未设置 | UTC验证码撤销或微信绑定解除时间；保留原记录以便追溯。 | 无独立索引/外键；通过所属记录关联 |
| revoke_reason | varchar(600) | 可空；默认 NULL/未设置 | 后台人工解除微信绑定的必填核对说明。 | 无独立索引/外键；通过所属记录关联 |
| active_user_id | char(36) | 可空；默认 NULL/未设置 | 自动生成列：只有有效绑定才填mini_user_id，唯一索引限制一个微信用户只能有一个当前职工身份。 | 索引:uq_wx_active_user |
| active_record_id | char(36) | 可空；默认 NULL/未设置 | 自动生成列：仅在验证码为issued或绑定为active时保留record_id，用唯一索引限制一职工一个当前状态；不能由客户端写入。 | 索引:uq_wx_active_employee |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_employee_bindings` (
  `binding_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '绑定记录UUID',
  `mini_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '微信小程序用户',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '实际绑定的企业档案池',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '数据模式',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '复用原职工主档案，绝不另建重复档案',
  `binding_code_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '本次消费的验证码',
  `binding_status` enum('active','revoked') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'active' COMMENT '后台人工解除后保留历史',
  `confirmed_digest` binary(32) NOT NULL COMMENT '职工确认的资料摘要',
  `confirmed_at` datetime(6) NOT NULL COMMENT 'UTC确认时间',
  `revoked_at` datetime(6) DEFAULT NULL COMMENT 'UTC人工解除时间',
  `revoke_reason` varchar(600) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '人工解除必填说明',
  `active_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS ((case when (`binding_status` = _utf8mb4'active') then `mini_user_id` else NULL end)) STORED COMMENT '一微信用户一个有效职工绑定',
  `active_record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS ((case when (`binding_status` = _utf8mb4'active') then `record_id` else NULL end)) STORED COMMENT '一职工一个有效微信绑定',
  PRIMARY KEY (`binding_id`),
  UNIQUE KEY `uq_wx_active_user` (`data_mode`,`active_user_id`),
  UNIQUE KEY `uq_wx_active_employee` (`data_mode`,`active_record_id`),
  KEY `fk_binding_user` (`mini_user_id`,`data_mode`),
  KEY `fk_binding_record` (`scope_code`,`data_mode`,`record_id`),
  KEY `fk_binding_code` (`binding_code_id`),
  CONSTRAINT `fk_binding_code` FOREIGN KEY (`binding_code_id`) REFERENCES `a_employee_binding_codes` (`binding_code_id`),
  CONSTRAINT `fk_binding_record` FOREIGN KEY (`scope_code`, `data_mode`, `record_id`) REFERENCES `a_health_records` (`scope_code`, `data_mode`, `record_id`),
  CONSTRAINT `fk_binding_user` FOREIGN KEY (`mini_user_id`, `data_mode`) REFERENCES `a_miniapp_users` (`mini_user_id`, `data_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='正式职工绑定及历史；只后台可解除，不能自助换成他人身份';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_rate_limits

**用途：** 微信登录及身份验证限流，绑定事务回滚不清空计数。

微信登录及身份验证限流，绑定事务回滚不清空计数

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| bucket_key | binary(32) | 不可空；默认 NULL/未设置 | 操作、模式和用户/IP的摘要，存储限流桶键，不保存明文IP。 | 索引:PRIMARY |
| window_start | datetime | 不可空；默认 NULL/未设置 | UTC固定限流窗口起点；新窗口首次请求重置计数。 | 无独立索引/外键；通过所属记录关联 |
| attempt_count | int unsigned | 不可空；默认 NULL/未设置 | 当前窗口尝试次数，独立事务累加；验证失败回滚不能撤销计数。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_rate_limits` (
  `bucket_key` binary(32) NOT NULL COMMENT '操作、模式、用户或IP的摘要，不存明文IP',
  `window_start` datetime NOT NULL COMMENT 'UTC固定限流窗口起点',
  `attempt_count` int unsigned NOT NULL COMMENT '独立事务累加，错误请求也计数',
  PRIMARY KEY (`bucket_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信登录及身份验证限流，绑定事务回滚不清空计数';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_sessions

**用途：** 微信小程序独立登录态，未绑定也能持有但不能访问健康档案。

微信小程序独立登录态，未绑定也能持有但不能访问健康档案

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| mini_session_id | char(36) | 不可空；默认 NULL/未设置 | 小程序独立会话UUID；令牌仅保存摘要，未绑定会话不授予健康数据权限。 | 索引:PRIMARY |
| mini_user_id | char(36) | 不可空；默认 NULL/未设置 | 微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。 | 外键→a_miniapp_users.mini_user_id；索引:ix_wx_session_user |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_miniapp_users.data_mode；索引:ix_wx_session_user |
| token_hash | binary(32) | 不可空；默认 NULL/未设置 | 32字节随机Bearer令牌的SHA256。数据库不存放明文令牌。 | 索引:uq_wx_token |
| expires_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC过期时间。会话默认2小时、确认凭据最多10分钟、个人码默认24小时，具体以本记录为准。 | 无独立索引/外键；通过所属记录关联 |
| revoked | tinyint | 不可空；默认 0 | 0有效、1已注销；还会检查用户锁定状态与当前组织归属。 | 索引:ix_wx_session_user |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_sessions` (
  `mini_session_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '小程序会话UUID',
  `mini_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '小程序用户UUID',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '会话数据模式',
  `token_hash` binary(32) NOT NULL COMMENT '随机Bearer令牌的SHA256，仅返回一次明文',
  `expires_at` datetime(6) NOT NULL COMMENT 'UTC过期时间',
  `revoked` tinyint NOT NULL DEFAULT '0' COMMENT '0有效，1已注销',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC签发时间',
  PRIMARY KEY (`mini_session_id`),
  UNIQUE KEY `uq_wx_token` (`token_hash`),
  KEY `ix_wx_session_user` (`mini_user_id`,`data_mode`,`revoked`),
  CONSTRAINT `fk_wx_session_user` FOREIGN KEY (`mini_user_id`, `data_mode`) REFERENCES `a_miniapp_users` (`mini_user_id`, `data_mode`),
  CONSTRAINT `ck_wx_revoked` CHECK ((`revoked` in (0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信小程序独立登录态，未绑定也能持有但不能访问健康档案';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_miniapp_users

**用途：** 微信登录身份；不自动生成职工档案、不保存session_key。

微信登录身份；不自动生成职工档案、不保存session_key

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| mini_user_id | char(36) | 不可空；默认 NULL/未设置 | 微信小程序独立用户UUID，不是职工ID。未绑定时也可存在，不能据此读取职工档案。 | 索引:PRIMARY；索引:uq_wx_user_mode |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 索引:uq_wx_identity；索引:uq_wx_user_mode |
| app_id | varchar(64) | 不可空；默认 NULL/未设置 | 服务端配置的微信小程序AppID；与OpenID、数据模式组成唯一身份。 | 索引:uq_wx_identity |
| open_id | varchar(128) | 不可空；默认 NULL/未设置 | 服务端从微信code2Session核验得到的OpenID；客户端不能指定，不在登录响应中下发。 | 索引:uq_wx_identity |
| union_id | varchar(128) | 可空；默认 NULL/未设置 | 微信实际返回时保存的UnionID，可空；不据客户端值跨用户合并。 | 无独立索引/外键；通过所属记录关联 |
| user_status | enum('active','disabled') | 不可空；默认 active | active有效、disabled停用；所有会话请求重新检查此状态。 | 无独立索引/外键；通过所属记录关联 |
| consent_version | varchar(64) | 不可空；默认 NULL/未设置 | 用户明确接受的用户协议/隐私政策版本，必须等于服务端当前版本。 | 无独立索引/外键；通过所属记录关联 |
| consent_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC用户最近明确接受该协议版本的时间。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |
| last_login_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC小程序用户最近成功微信登录时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_miniapp_users` (
  `mini_user_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '独立小程序用户UUID，与职工身份分开',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '正式与隔离测试',
  `app_id` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端配置的小程序AppID',
  `open_id` varchar(128) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '微信服务端核验的OpenID，不由客户端指定',
  `union_id` varchar(128) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '微信返回时才保存，不作为本期绑定凭据',
  `user_status` enum('active','disabled') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'active' COMMENT '小程序账号状态',
  `consent_version` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '用户明确勾选接受的协议版本',
  `consent_at` datetime(6) NOT NULL COMMENT 'UTC协议接受时间',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC用户首次登录',
  `last_login_at` datetime(6) NOT NULL COMMENT 'UTC最近登录',
  PRIMARY KEY (`mini_user_id`),
  UNIQUE KEY `uq_wx_identity` (`data_mode`,`app_id`,`open_id`),
  UNIQUE KEY `uq_wx_user_mode` (`mini_user_id`,`data_mode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信登录身份；不自动生成职工档案、不保存session_key';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_occupational_profiles

**用途：** 职业健康字段观察及授权职业资料。

职业健康字段观察及授权职业资料

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| profile_id | char(36) | 不可空；默认 NULL/未设置 | 一条职业健康字段观察的UUID，不是个人主档案record_id。 | 索引:PRIMARY |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_reports.scope_code；外键→a_employees.scope_code；索引:fk_a_088249500b416416ab；索引:fk_a_cbbdace0adaf26c74d |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_reports.data_mode；外键→a_employees.data_mode；索引:fk_a_088249500b416416ab；索引:fk_a_cbbdace0adaf26c74d |
| report_id | char(36) | 可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；外键→a_report_evidence.report_id；索引:fk_a_088249500b416416ab；索引:fk_a_92d8e6480d42ae202a |
| revision | int unsigned | 可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；外键→a_report_evidence.revision；索引:fk_a_088249500b416416ab；索引:fk_a_92d8e6480d42ae202a |
| employee_id | char(36) | 可空；默认 NULL/未设置 | 职工的内部UUID。与旧系统user_code不同；授权身份、主档案和业务花名册通过它关联。 | 外键→a_employees.employee_id；索引:fk_a_cbbdace0adaf26c74d；索引:ix_occupation_lookup |
| evidence_id | char(36) | 可空；默认 NULL/未设置 | 报告版本内的证据UUID，用于连接原页、字段观察和检查项目；不得跨版本引用。 | 外键→a_report_evidence.evidence_id；索引:fk_a_92d8e6480d42ae202a |
| authorized_source_ref | varbinary(256) | 可空；默认 NULL/未设置 | 职业资料来自企业授权来源时的引用；报告来源则必须有报告版本和原页证据。 | 无独立索引/外键；通过所属记录关联 |
| field_key | varchar(64) | 不可空；默认 NULL/未设置 | 标准报告字段编码，例如full_name、exam_date、hazard_factors。共享JSON数组依赖此键定位观察。 | 索引:ix_occupation_lookup |
| original_field_name | text | 不可空；默认 NULL/未设置 | 报告原始字段/项目名称，例如原文检查名；应与标准名称分别保留。 | 无独立索引/外键；通过所属记录关联 |
| value_raw | mediumtext | 可空；默认 NULL/未设置 | 标准字段或职业字段的原始字符串。缺失保留NULL，禁止通过空值补零或改成“正常”。 | 无独立索引/外键；通过所属记录关联 |
| value_normalized | json | 可空；默认 NULL/未设置 | 经批准规则得到的标准化值。没有规则版本和批准依据时不能填写。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_occupation_lookup |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_occupational_profiles` (
  `profile_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned DEFAULT NULL COMMENT '报告版本号',
  `employee_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '员工标识',
  `evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `authorized_source_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `field_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_field_name` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '原字段名',
  `value_raw` mediumtext COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `value_normalized` json DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`profile_id`),
  KEY `ix_occupation_lookup` (`employee_id`,`field_key`,`created_at`),
  KEY `fk_a_cbbdace0adaf26c74d` (`scope_code`,`data_mode`,`employee_id`),
  KEY `fk_a_088249500b416416ab` (`scope_code`,`data_mode`,`report_id`,`revision`),
  KEY `fk_a_92d8e6480d42ae202a` (`report_id`,`revision`,`evidence_id`),
  CONSTRAINT `fk_a_088249500b416416ab` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`) REFERENCES `a_health_reports` (`scope_code`, `data_mode`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_92d8e6480d42ae202a` FOREIGN KEY (`report_id`, `revision`, `evidence_id`) REFERENCES `a_report_evidence` (`report_id`, `revision`, `evidence_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_cbbdace0adaf26c74d` FOREIGN KEY (`scope_code`, `data_mode`, `employee_id`) REFERENCES `a_employees` (`scope_code`, `data_mode`, `employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_occupational_profiles_1` CHECK (((`report_id` is null) = (`revision` is null))),
  CONSTRAINT `ck_a_occupational_profiles_2` CHECK ((((`report_id` is not null) and (`evidence_id` is not null)) or ((`employee_id` is not null) and (`authorized_source_ref` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='职业健康字段观察及授权职业资料';
```

写入保护触发器：
- a_ehr_58655c2a4aa4f61b50：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_87702aa2686db69097：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_f6a534bad95733b7fc：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_ocr_dispatch_attempts

**用途：** OCR每次领取、心跳及成功失败超时历史，不覆盖旧尝试。

OCR每次领取、心跳及成功失败超时历史，不覆盖旧尝试

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| attempt_id | char(36) | 不可空；默认 NULL/未设置 | 一次实际领取处理尝试UUID；与业务job_id分开。 | 索引:PRIMARY |
| job_id | char(36) | 不可空；默认 NULL/未设置 | 外部OCR/PDF识别任务UUID，绑定逻辑报告和候选版本；回调必须关联该任务。 | 外键→a_ocr_dispatch_tasks.job_id；索引:fk_attempt_dispatch；索引:uq_dispatch_attempt |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_ocr_dispatch_tasks.data_mode；索引:fk_attempt_dispatch；索引:uq_dispatch_claim_request |
| attempt_number | int unsigned | 不可空；默认 NULL/未设置 | 同任务内的递增领取序号，超时后再次领取另增一条记录。 | 索引:uq_dispatch_attempt |
| claim_request_key | char(36) | 不可空；默认 NULL/未设置 | B端领取请求UUID，网络重试沿用；当前租约有效时返回原领取结果。 | 索引:uq_dispatch_claim_request |
| worker_id | varchar(80) | 不可空；默认 NULL/未设置 | OCR工作服务实例标识，仅表示处理程序，不是职工或医生身份。 | 索引:uq_dispatch_claim_request |
| outcome | enum('running','succeeded','failed','expired','cancelled') | 不可空；默认 running | 本次尝试结果：running、succeeded、failed、expired、cancelled。超时历史不会覆盖。 | 无独立索引/外键；通过所属记录关联 |
| progress_stage | varchar(32) | 不可空；默认 queued | B端报告的解析、OCR、提取、项目匹配或校验阶段；finished只由A端接收成功后写入。 | 无独立索引/外键；通过所属记录关联 |
| progress_percent | tinyint unsigned | 不可空；默认 0 | 任务进度0至100；B只能上报0至95，100由A端完成事务后设置。 | 无独立索引/外键；通过所属记录关联 |
| started_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC本次OCR领取处理尝试开始时间。 | 无独立索引/外键；通过所属记录关联 |
| heartbeat_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC本次尝试最近一次心跳时间，B处理长报告时也须续期。 | 无独立索引/外键；通过所属记录关联 |
| finished_at | datetime(6) | 可空；默认 NULL/未设置 | UTC本次尝试成功、失败、超时或取消时间；处理中为空。 | 无独立索引/外键；通过所属记录关联 |
| error_code | varchar(64) | 可空；默认 NULL/未设置 | 本次OCR尝试的脱敏错误代码。 | 无独立索引/外键；通过所属记录关联 |
| error_message | varchar(600) | 可空；默认 NULL/未设置 | 本次OCR尝试的脱敏错误说明。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_ocr_dispatch_attempts` (
  `attempt_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '一次领取处理尝试UUID',
  `job_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '所属OCR调度任务',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '与任务相同的数据模式',
  `attempt_number` int unsigned NOT NULL COMMENT '任务内递增尝试序号',
  `claim_request_key` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '工作服务领取幂等键，网络重试沿用',
  `worker_id` varchar(80) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '实际领取实例标识',
  `outcome` enum('running','succeeded','failed','expired','cancelled') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'running' COMMENT '本次处理结果，超时保留expired历史',
  `progress_stage` varchar(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL DEFAULT 'queued' COMMENT '本次最后上报的处理阶段',
  `progress_percent` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '本次最后进度0至100',
  `started_at` datetime(6) NOT NULL COMMENT 'UTC领取时间',
  `heartbeat_at` datetime(6) NOT NULL COMMENT 'UTC最后心跳时间',
  `finished_at` datetime(6) DEFAULT NULL COMMENT 'UTC处理结束、失败、超时或取消时间',
  `error_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '本次脱敏错误类型',
  `error_message` varchar(600) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '本次脱敏错误说明',
  PRIMARY KEY (`attempt_id`),
  UNIQUE KEY `uq_dispatch_attempt` (`job_id`,`attempt_number`),
  UNIQUE KEY `uq_dispatch_claim_request` (`data_mode`,`worker_id`,`claim_request_key`),
  KEY `fk_attempt_dispatch` (`job_id`,`data_mode`),
  CONSTRAINT `fk_attempt_dispatch` FOREIGN KEY (`job_id`, `data_mode`) REFERENCES `a_ocr_dispatch_tasks` (`job_id`, `data_mode`),
  CONSTRAINT `ck_attempt_finished` CHECK ((((`outcome` = _utf8mb4'running') and (`finished_at` is null)) or ((`outcome` <> _utf8mb4'running') and (`finished_at` is not null)))),
  CONSTRAINT `ck_attempt_progress` CHECK ((`progress_percent` <= 100))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='OCR每次领取、心跳及成功失败超时历史，不覆盖旧尝试';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_ocr_dispatch_tasks

**用途：** 持久OCR领取队列、租约、心跳、超时重试和任务进度。

持久OCR领取队列、租约、心跳、超时重试和任务进度

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| job_id | char(36) | 不可空；默认 NULL/未设置 | 外部OCR/PDF识别任务UUID，绑定逻辑报告和候选版本；回调必须关联该任务。 | 外键→a_extraction_jobs.job_id；索引:PRIMARY；索引:uq_dispatch_mode |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 索引:ix_dispatch_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 索引:ix_dispatch_claim；索引:ix_dispatch_scope；索引:uq_dispatch_mode |
| dispatch_state | enum('queued','running','retry_wait','succeeded','failed','cancelled') | 不可空；默认 queued | OCR调度状态：queued排队、running租约处理中、retry_wait等待重试、succeeded已收到候选、failed停止重试、cancelled人工或版本变更停止。 | 索引:ix_dispatch_claim |
| attempt_count | int unsigned | 不可空；默认 0 | 已实际领取的处理次数，不因手动重试归零；历史保留在a_ocr_dispatch_attempts。 | 无独立索引/外键；通过所属记录关联 |
| max_attempts | int unsigned | 不可空；默认 3 | 允许累计领取的次数预算，默认3；后台重试增加3次，原有尝试历史保留。 | 无独立索引/外键；通过所属记录关联 |
| available_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC任务下一次可领取时间；失败退避期间不能立即领取。 | 索引:ix_dispatch_claim |
| current_attempt_id | char(36) | 可空；默认 NULL/未设置 | 当前或最近一次领取尝试UUID；接管后旧租约失效。 | 无独立索引/外键；通过所属记录关联 |
| worker_id | varchar(80) | 可空；默认 NULL/未设置 | OCR工作服务实例标识，仅表示处理程序，不是职工或医生身份。 | 无独立索引/外键；通过所属记录关联 |
| lease_token_hash | binary(32) | 可空；默认 NULL/未设置 | 当前任务租约凭据的SHA256摘要，不在后台页面显示。 | 无独立索引/外键；通过所属记录关联 |
| lease_until | datetime(6) | 可空；默认 NULL/未设置 | UTC租约到期时间，B端心跳续期，过期后回收或失败。 | 索引:ix_dispatch_claim |
| progress_stage | enum('queued','pdf_parse','ocr','field_extraction','item_matching','validation','finished') | 不可空；默认 queued | B端报告的解析、OCR、提取、项目匹配或校验阶段；finished只由A端接收成功后写入。 | 无独立索引/外键；通过所属记录关联 |
| progress_percent | tinyint unsigned | 不可空；默认 0 | 任务进度0至100；B只能上报0至95，100由A端完成事务后设置。 | 无独立索引/外键；通过所属记录关联 |
| last_error_code | varchar(64) | 可空；默认 NULL/未设置 | 最近失败、超时或人工停止的错误码，用于页面提示和排查。 | 无独立索引/外键；通过所属记录关联 |
| last_error_message | varchar(600) | 可空；默认 NULL/未设置 | 脱敏错误说明，不应包含密钥、完整报告文本等。 | 无独立索引/外键；通过所属记录关联 |
| last_retry_key | char(36) | 可空；默认 NULL/未设置 | 后台手动重试UUID幂等键，同键重试不增加额外预算。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_dispatch_scope |
| updated_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录最后更新时间，任务更新由接口显式写UTC。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_ocr_dispatch_tasks` (
  `job_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '关联识别业务任务UUID，一任务一调度记录',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '企业档案池',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '正式与隔离数据模式',
  `dispatch_state` enum('queued','running','retry_wait','succeeded','failed','cancelled') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'queued' COMMENT '调度状态，与extraction_jobs业务识别阶段分开',
  `attempt_count` int unsigned NOT NULL DEFAULT '0' COMMENT '已领取次数，手动重试不抹去历史',
  `max_attempts` int unsigned NOT NULL DEFAULT '3' COMMENT '自动领取次数上限，人工重试增加预算',
  `available_at` datetime(6) NOT NULL COMMENT 'UTC允许领取或重试时间',
  `current_attempt_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '当前处理尝试UUID，旧尝试不能回传覆盖',
  `worker_id` varchar(80) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '工作服务的实例标识，不是职工身份',
  `lease_token_hash` binary(32) DEFAULT NULL COMMENT '当前租约凭据摘要，不保存明文凭据',
  `lease_until` datetime(6) DEFAULT NULL COMMENT 'UTC租约到期时间，心跳可续期',
  `progress_stage` enum('queued','pdf_parse','ocr','field_extraction','item_matching','validation','finished') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'queued' COMMENT '工作服务报告的进度，finished只由A端入库成功设置',
  `progress_percent` tinyint unsigned NOT NULL DEFAULT '0' COMMENT 'B端上报0至95，A端接收成功后为100',
  `last_error_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '最近错误类型，不包含密钥',
  `last_error_message` varchar(600) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '脱敏错误说明，不写入完整报告正文',
  `last_retry_key` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '后台手动重试幂等键',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC入队时间',
  `updated_at` datetime(6) NOT NULL COMMENT 'UTC最近调度变更时间',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `uq_dispatch_mode` (`job_id`,`data_mode`),
  KEY `ix_dispatch_claim` (`data_mode`,`dispatch_state`,`available_at`,`lease_until`),
  KEY `ix_dispatch_scope` (`scope_code`,`data_mode`,`created_at`),
  CONSTRAINT `fk_dispatch_job` FOREIGN KEY (`job_id`) REFERENCES `a_extraction_jobs` (`job_id`),
  CONSTRAINT `ck_dispatch_attempts` CHECK (((`max_attempts` >= 1) and (`attempt_count` <= `max_attempts`))),
  CONSTRAINT `ck_dispatch_progress` CHECK ((`progress_percent` <= 100)),
  CONSTRAINT `ck_dispatch_running` CHECK (((`dispatch_state` <> _utf8mb4'running') or ((`current_attempt_id` is not null) and (`worker_id` is not null) and (`lease_token_hash` is not null) and (`lease_until` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='持久OCR领取队列、租约、心跳、超时重试和任务进度';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_report_evidence

**用途：** 版本内原页、坐标及提取证据。

来源、页码、坐标、提取方式和复核状态共同组成证据，不可只保留一段AI文字。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_revision_sources.report_id；索引:fk_a_4fffcd0ffcb2935e30；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_revision_sources.revision；索引:fk_a_4fffcd0ffcb2935e30；索引:PRIMARY |
| evidence_id | char(36) | 不可空；默认 NULL/未设置 | 报告版本内的证据UUID，用于连接原页、字段观察和检查项目；不得跨版本引用。 | 索引:PRIMARY |
| source_file_id | char(36) | 不可空；默认 NULL/未设置 | 原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。 | 外键→a_report_revision_sources.source_file_id；索引:fk_a_4fffcd0ffcb2935e30；索引:ix_evidence_source_page |
| page_number | int unsigned | 不可空；默认 NULL/未设置 | 原件页码，从1开始。必须核对具体source_file_id和该文件实际页数。 | 索引:ix_evidence_source_page |
| bbox | json | 可空；默认 NULL/未设置 | 原页定位框JSON，顺序[x1,y1,x2,y2]。PDF点、图像像素和对象级证据不能混用。 | 无独立索引/外键；通过所属记录关联 |
| coordinate_system | enum('pdf_points_unrotated','image_pixels_top_left','object_only') | 不可空；默认 NULL/未设置 | pdf_points_unrotated为未旋转PDF点；image_pixels_top_left为左上原点像素；object_only仅指向对象/页且bbox为NULL。 | 无独立索引/外键；通过所属记录关联 |
| page_rotation | smallint unsigned | 不可空；默认 NULL/未设置 | 原页旋转角度，只允许0、90、180、270；用于正确展示定位框。 | 无独立索引/外键；通过所属记录关联 |
| render_dpi | smallint unsigned | 可空；默认 NULL/未设置 | PDF渲染成图像后的分辨率。像素坐标证据必须记录DPI，不能与PDF点坐标直接比较。 | 无独立索引/外键；通过所属记录关联 |
| original_label | text | 不可空；默认 NULL/未设置 | 原件上看到的标签，不应把它改成标准名称后丢掉原文；签章标签也不是签章真实性证明。 | 无独立索引/外键；通过所属记录关联 |
| raw_fragment | mediumtext | 可空；默认 NULL/未设置 | 对应原页的原始片段/抄录原文。补录为manual_transcription，不伪称OCR自动识别。 | 无独立索引/外键；通过所属记录关联 |
| extraction_method | enum('native_text','ocr','pdf_object','manual_transcription') | 不可空；默认 NULL/未设置 | native_text原生文本、ocr图像识别、pdf_object原对象、manual_transcription人工抄录。 | 无独立索引/外键；通过所属记录关联 |
| extractor_version | varchar(120) | 不可空；默认 NULL/未设置 | 外部识别器或人工补录适配器的版本标识，用于追溯识别来源。 | 无独立索引/外键；通过所属记录关联 |
| ocr_confidence | decimal(8,7) | 可空；默认 NULL/未设置 | OCR模型输出的0至1置信度。原生文本不得编造OCR置信度，未提供时保留NULL。 | 无独立索引/外键；通过所属记录关联 |
| review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | unreviewed未核对、needs_review待复核、confirmed医生已确认、rejected已驳回。与识别任务状态分开。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_evidence` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `evidence_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `source_file_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '受限原文件标识',
  `page_number` int unsigned NOT NULL COMMENT '原件页码，从 1 开始',
  `bbox` json DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `coordinate_system` enum('pdf_points_unrotated','image_pixels_top_left','object_only') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `page_rotation` smallint unsigned NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `render_dpi` smallint unsigned DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `original_label` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `raw_fragment` mediumtext COLLATE utf8mb4_0900_bin COMMENT '按冻结 DDL 列定义使用',
  `extraction_method` enum('native_text','ocr','pdf_object','manual_transcription') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `extractor_version` varchar(120) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `ocr_confidence` decimal(8,7) DEFAULT NULL COMMENT 'OCR 置信度；原生文本留空',
  `review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '复核状态',
  PRIMARY KEY (`report_id`,`revision`,`evidence_id`),
  KEY `ix_evidence_source_page` (`source_file_id`,`page_number`),
  KEY `fk_a_4fffcd0ffcb2935e30` (`report_id`,`revision`,`source_file_id`),
  CONSTRAINT `fk_a_4fffcd0ffcb2935e30` FOREIGN KEY (`report_id`, `revision`, `source_file_id`) REFERENCES `a_report_revision_sources` (`report_id`, `revision`, `source_file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_report_evidence_1` CHECK ((`page_number` > 0)),
  CONSTRAINT `ck_a_report_evidence_2` CHECK ((`page_rotation` in (0,90,180,270))),
  CONSTRAINT `ck_a_report_evidence_3` CHECK ((((`coordinate_system` = _utf8mb4'object_only') and (`bbox` is null)) or ((`coordinate_system` <> _utf8mb4'object_only') and (`bbox` is not null) and (json_type(`bbox`) = _utf8mb4'ARRAY') and (json_length(`bbox`) = 4)))),
  CONSTRAINT `ck_a_report_evidence_4` CHECK (((`ocr_confidence` is null) or ((`ocr_confidence` >= 0) and (`ocr_confidence` <= 1)))),
  CONSTRAINT `ck_a_report_evidence_5` CHECK (((`extraction_method` = _utf8mb4'ocr') or (`ocr_confidence` is null))),
  CONSTRAINT `ck_a_report_evidence_6` CHECK (((`coordinate_system` <> _utf8mb4'image_pixels_top_left') or ((`render_dpi` is not null) and (`render_dpi` > 0))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='版本内原页、坐标及提取证据';
```

写入保护触发器：
- a_ehr_078e26e8f26470a796：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_5112c6a40dd4e28de5：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_97cc6755ac8ed0dd60：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_report_publications

**用途：** 正式归档凭据；仅允许同摘要的质量通过与医生确认版本。

正式归档凭据；仅允许同摘要的质量通过与医生确认版本

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| publication_id | char(36) | 不可空；默认 NULL/未设置 | 一条正式归档/发布凭据UUID。个人正式档案读取已发布版本，而不是任意候选。 | 索引:PRIMARY；索引:uq_report_publications_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_quality_checks.scope_code；外键→a_health_reports.scope_code；索引:fk_a_209b53c0a63650bdb1；索引:fk_a_3b2fb2dcb78ff01f82；索引:uq_publication_record；索引:uq_report_publications_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_quality_checks.data_mode；外键→a_health_reports.data_mode；索引:fk_a_209b53c0a63650bdb1；索引:fk_a_3b2fb2dcb78ff01f82；索引:uq_publication_record；索引:uq_report_publications_scope |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_quality_checks.report_id；外键→a_health_reports.report_id；外键→a_review_records.report_id；索引:fk_a_209b53c0a63650bdb1；索引:fk_a_3b2fb2dcb78ff01f82；索引:fk_a_ca348748ccf340de8b；索引:uq_publication_record；索引:uq_publication_version |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_quality_checks.revision；外键→a_health_reports.revision；外键→a_review_records.revision；索引:fk_a_209b53c0a63650bdb1；索引:fk_a_3b2fb2dcb78ff01f82；索引:fk_a_ca348748ccf340de8b；索引:uq_publication_record；索引:uq_publication_version |
| record_id | char(36) | 不可空；默认 NULL/未设置 | 个人健康档案主记录UUID。同一组织和数据模式内，一个employee_id仅有一份主档案。 | 外键→a_health_reports.record_id；索引:fk_a_3b2fb2dcb78ff01f82；索引:uq_publication_record |
| review_id | char(36) | 不可空；默认 NULL/未设置 | 一条人工审核决策UUID，关联精确报告版本和已核对摘要。 | 外键→a_review_records.review_id；索引:fk_a_ca348748ccf340de8b |
| review_decision | enum('return','reject','confirm') | 不可空；默认 NULL/未设置 | 发布关联的人工审核决策，数据库限制必须为confirm。 | 外键→a_review_records.decision；索引:fk_a_ca348748ccf340de8b |
| report_review_status | enum('unreviewed','needs_review','confirmed','rejected') | 不可空；默认 NULL/未设置 | 发布关联版本的审核状态，数据库限制必须为confirmed。 | 外键→a_health_reports.review_status；索引:fk_a_3b2fb2dcb78ff01f82 |
| quality_check_id | char(36) | 不可空；默认 NULL/未设置 | 一次报告版本质量核验UUID，包括身份、规则版本和快照摘要。 | 外键→a_report_quality_checks.quality_check_id；索引:fk_a_209b53c0a63650bdb1 |
| quality_status | enum('passed','failed','needs_review') | 不可空；默认 NULL/未设置 | 发布关联质量核验状态，数据库限制必须为passed。 | 外键→a_report_quality_checks.status；索引:fk_a_209b53c0a63650bdb1 |
| reviewed_digest | binary(32) | 不可空；默认 NULL/未设置 | 规范化已核对内容的SHA256（32字节），质量校验、医生审核和发布必须相同。 | 外键→a_report_quality_checks.report_digest；外键→a_review_records.reviewed_digest；索引:fk_a_209b53c0a63650bdb1；索引:fk_a_ca348748ccf340de8b |
| published_by_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 执行归档的已授权账号标识。 | 无独立索引/外键；通过所属记录关联 |
| published_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC正式归档时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_publications` (
  `publication_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'publication_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'report_id',
  `revision` int unsigned NOT NULL COMMENT 'revision',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'record_id',
  `review_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'review_id',
  `review_decision` enum('return','reject','confirm') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'review_decision',
  `report_review_status` enum('unreviewed','needs_review','confirmed','rejected') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'report_review_status',
  `quality_check_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'quality_check_id',
  `quality_status` enum('passed','failed','needs_review') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'quality_status',
  `reviewed_digest` binary(32) NOT NULL COMMENT 'reviewed_digest',
  `published_by_ref` varbinary(256) NOT NULL COMMENT 'published_by_ref',
  `published_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`publication_id`),
  UNIQUE KEY `uq_report_publications_scope` (`scope_code`,`data_mode`,`publication_id`),
  UNIQUE KEY `uq_publication_version` (`report_id`,`revision`),
  UNIQUE KEY `uq_publication_record` (`scope_code`,`data_mode`,`record_id`,`report_id`,`revision`),
  KEY `fk_a_3b2fb2dcb78ff01f82` (`scope_code`,`data_mode`,`report_id`,`revision`,`record_id`,`report_review_status`),
  KEY `fk_a_ca348748ccf340de8b` (`report_id`,`revision`,`review_id`,`review_decision`,`reviewed_digest`),
  KEY `fk_a_209b53c0a63650bdb1` (`scope_code`,`data_mode`,`report_id`,`revision`,`quality_check_id`,`quality_status`,`reviewed_digest`),
  CONSTRAINT `fk_a_209b53c0a63650bdb1` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`, `quality_check_id`, `quality_status`, `reviewed_digest`) REFERENCES `a_report_quality_checks` (`scope_code`, `data_mode`, `report_id`, `revision`, `quality_check_id`, `status`, `report_digest`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_3b2fb2dcb78ff01f82` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`, `record_id`, `report_review_status`) REFERENCES `a_health_reports` (`scope_code`, `data_mode`, `report_id`, `revision`, `record_id`, `review_status`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_ca348748ccf340de8b` FOREIGN KEY (`report_id`, `revision`, `review_id`, `review_decision`, `reviewed_digest`) REFERENCES `a_review_records` (`report_id`, `revision`, `review_id`, `decision`, `reviewed_digest`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_report_publications_1` CHECK (((`review_decision` = _utf8mb4'confirm') and (`report_review_status` = _utf8mb4'confirmed') and (`quality_status` = _utf8mb4'passed')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='正式归档凭据；仅允许同摘要的质量通过与医生确认版本';
```

写入保护触发器：
- a_ehr_75d6bf8f4e1ae5c933：BEFORE UPDATE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```
- a_ehr_84056fdc6f762f6a99：BEFORE DELETE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```

## a_report_quality_checks

**用途：** 报告版本质量校验结果；按快照摘要和规则版本留痕。

报告版本质量校验结果；按快照摘要和规则版本留痕

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| quality_check_id | char(36) | 不可空；默认 NULL/未设置 | 一次报告版本质量核验UUID，包括身份、规则版本和快照摘要。 | 索引:PRIMARY；索引:uq_quality_release；索引:uq_report_quality_checks_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_health_reports.scope_code；索引:uq_quality_release；索引:uq_report_quality_checks_scope |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_health_reports.data_mode；索引:uq_quality_release；索引:uq_report_quality_checks_scope |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:uq_quality_release |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:uq_quality_release |
| status | enum('passed','failed','needs_review') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 索引:uq_quality_release |
| report_digest | binary(32) | 不可空；默认 NULL/未设置 | 被质量核验的报告内容摘要，发布时必须与医生核对摘要一致。 | 索引:uq_quality_release |
| identity_status | enum('matched','conflict','ambiguous','unmatched') | 不可空；默认 NULL/未设置 | matched已依据授权资料核定；conflict冲突、ambiguous多候选、unmatched未匹配。仅同名不能自动确认身份。 | 无独立索引/外键；通过所属记录关联 |
| issues | json | 不可空；默认 NULL/未设置 | JSON质量问题数组。原始冲突和缺失应保留，不能静默丢弃。 | 无独立索引/外键；通过所属记录关联 |
| rule_version | varchar(64) | 不可空；默认 NULL/未设置 | 质量、风险或完整度规则的明确版本。不同业务的规则含义独立，不要混用。 | 无独立索引/外键；通过所属记录关联 |
| checked_by_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 执行质量核验的人员或受控服务标识。 | 无独立索引/外键；通过所属记录关联 |
| checked_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC质量核验完成时间。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_quality_checks` (
  `quality_check_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'quality_check_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'report_id',
  `revision` int unsigned NOT NULL COMMENT 'revision',
  `status` enum('passed','failed','needs_review') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'status',
  `report_digest` binary(32) NOT NULL COMMENT 'report_digest',
  `identity_status` enum('matched','conflict','ambiguous','unmatched') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'identity_status',
  `issues` json NOT NULL COMMENT 'issues',
  `rule_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'rule_version',
  `checked_by_ref` varbinary(256) NOT NULL COMMENT 'checked_by_ref',
  `checked_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`quality_check_id`),
  UNIQUE KEY `uq_report_quality_checks_scope` (`scope_code`,`data_mode`,`quality_check_id`),
  UNIQUE KEY `uq_quality_release` (`scope_code`,`data_mode`,`report_id`,`revision`,`quality_check_id`,`status`,`report_digest`),
  CONSTRAINT `fk_a_8c35668a76d719f285` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`) REFERENCES `a_health_reports` (`scope_code`, `data_mode`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_report_quality_checks_1` CHECK (((`status` <> _utf8mb4'passed') or (`identity_status` = _utf8mb4'matched'))),
  CONSTRAINT `ck_a_report_quality_checks_2` CHECK ((json_type(`issues`) = _utf8mb4'ARRAY'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='报告版本质量校验结果；按快照摘要和规则版本留痕';
```

写入保护触发器：
- a_ehr_145987d2a22d967752：BEFORE UPDATE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```
- a_ehr_1c7bc32a5eb1bd56b7：BEFORE DELETE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```

## a_report_review_snapshots

**用途：** 医生审核时的不可变内容快照。

医生审核时的不可变内容快照

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:PRIMARY |
| reviewed_digest | binary(32) | 不可空；默认 NULL/未设置 | 规范化已核对内容的SHA256（32字节），质量校验、医生审核和发布必须相同。 | 无独立索引/外键；通过所属记录关联 |
| snapshot_json | longtext | 不可空；默认 NULL/未设置 | 完整审核内容的不可变JSON快照，后续归档会重新核对内容摘要。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 NULL/未设置 | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_review_snapshots` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告ID',
  `revision` int unsigned NOT NULL COMMENT '报告版本',
  `reviewed_digest` binary(32) NOT NULL COMMENT '服务端规范化快照SHA256，与质量/审核/发布摘要一致',
  `snapshot_json` longtext COLLATE utf8mb4_0900_bin NOT NULL COMMENT '完整核对快照，原值和证据均保留',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC快照时间',
  PRIMARY KEY (`report_id`,`revision`),
  CONSTRAINT `fk_a_review_snapshot_report` FOREIGN KEY (`report_id`, `revision`) REFERENCES `a_health_reports` (`report_id`, `revision`),
  CONSTRAINT `ck_a_snapshot_json` CHECK (json_valid(`snapshot_json`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='医生审核时的不可变内容快照';
```

写入保护触发器：
- a_snapshot_no_delete：BEFORE DELETE

```sql
BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only'; END
```
- a_snapshot_no_update：BEFORE UPDATE

```sql
BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only'; END
```

## a_report_revision_sources

**用途：** 报告版本与多份原文件的关联。

报告版本与多份原文件的关联

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:fk_a_2917f1f569ce6b2eb4；索引:PRIMARY |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:fk_a_2917f1f569ce6b2eb4；索引:PRIMARY |
| source_file_id | char(36) | 不可空；默认 NULL/未设置 | 原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。 | 外键→a_report_sources.source_file_id；索引:fk_a_1dec60391479d90b79；索引:PRIMARY |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_sources.scope_code；外键→a_health_reports.scope_code；索引:fk_a_1dec60391479d90b79；索引:fk_a_2917f1f569ce6b2eb4 |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_sources.data_mode；外键→a_health_reports.data_mode；索引:fk_a_1dec60391479d90b79；索引:fk_a_2917f1f569ce6b2eb4 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_revision_sources` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `source_file_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '受限原文件标识',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  PRIMARY KEY (`report_id`,`revision`,`source_file_id`),
  KEY `fk_a_2917f1f569ce6b2eb4` (`scope_code`,`data_mode`,`report_id`,`revision`),
  KEY `fk_a_1dec60391479d90b79` (`scope_code`,`data_mode`,`source_file_id`),
  CONSTRAINT `fk_a_1dec60391479d90b79` FOREIGN KEY (`scope_code`, `data_mode`, `source_file_id`) REFERENCES `a_report_sources` (`scope_code`, `data_mode`, `source_file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_2917f1f569ce6b2eb4` FOREIGN KEY (`scope_code`, `data_mode`, `report_id`, `revision`) REFERENCES `a_health_reports` (`scope_code`, `data_mode`, `report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='报告版本与多份原文件的关联';
```

写入保护触发器：
- a_ehr_44c0e748e0d574963d：BEFORE INSERT

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_aab71e7af6815c1dff：BEFORE UPDATE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=NEW.report_id AND r.revision=NEW.revision) OR EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```
- a_ehr_c9d2c5c6751095cbbc：BEFORE DELETE

```sql
BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END
```

## a_report_series

**用途：** 逻辑报告；current_revision不是正式发布标记。

逻辑报告与当前候选指针；正式发布记录与当前草稿可以同时存在。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:fk_a_6facccd9b7b3c0e11c；索引:PRIMARY；索引:uq_series_scope_id |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_sources.scope_code；索引:fk_a_f979a214c300ba572f；索引:uq_series_scope_id |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_sources.data_mode；索引:fk_a_f979a214c300ba572f；索引:uq_series_scope_id |
| source_file_id | char(36) | 不可空；默认 NULL/未设置 | 原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。 | 外键→a_report_sources.source_file_id；索引:fk_a_f979a214c300ba572f；索引:uq_series_primary_source |
| current_revision | int unsigned | 可空；默认 NULL/未设置 | 逻辑报告当前候选版本指针。它不是正式归档标记；发布事实以publications为准。 | 外键→a_health_reports.revision；索引:fk_a_6facccd9b7b3c0e11c |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_series` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `source_file_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '受限原文件标识',
  `current_revision` int unsigned DEFAULT NULL COMMENT '当前版本指针',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`report_id`),
  UNIQUE KEY `uq_series_scope_id` (`scope_code`,`data_mode`,`report_id`),
  UNIQUE KEY `uq_series_primary_source` (`source_file_id`),
  KEY `fk_a_6facccd9b7b3c0e11c` (`report_id`,`current_revision`),
  KEY `fk_a_f979a214c300ba572f` (`scope_code`,`data_mode`,`source_file_id`),
  CONSTRAINT `fk_a_6facccd9b7b3c0e11c` FOREIGN KEY (`report_id`, `current_revision`) REFERENCES `a_health_reports` (`report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_f979a214c300ba572f` FOREIGN KEY (`scope_code`, `data_mode`, `source_file_id`) REFERENCES `a_report_sources` (`scope_code`, `data_mode`, `source_file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='逻辑报告；current_revision不是正式发布标记';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_report_sources

**用途：** PDF或照片原始文件；按组织和模式去重。

PDF或照片原始文件；按组织和模式去重

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| source_file_id | char(36) | 不可空；默认 NULL/未设置 | 原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。 | 索引:PRIMARY；索引:uq_sources_scope_id |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 索引:uq_sources_repeat；索引:uq_sources_scope_id |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 索引:uq_sources_repeat；索引:uq_sources_scope_id |
| sha256 | binary(32) | 不可空；默认 NULL/未设置 | 原始文件字节的SHA256，32字节二进制；用于完整性和精确重复判断，不是文件名摘要。 | 索引:uq_sources_repeat |
| object_ref | varbinary(512) | 不可空；默认 NULL/未设置 | 原件的受限存储引用，当前为私有目录内随机文件名。不能作为未鉴权静态URL公开。 | 无独立索引/外键；通过所属记录关联 |
| page_count | int unsigned | 不可空；默认 NULL/未设置 | 实际读取的文件页数，PDF从文件解析，单张图片为1；证据页码必须落在此范围。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_sources` (
  `source_file_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '受限原文件标识',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '租户或组织范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实/模拟隔离',
  `sha256` binary(32) NOT NULL COMMENT '原始文件摘要',
  `object_ref` varbinary(512) NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `page_count` int unsigned NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`source_file_id`),
  UNIQUE KEY `uq_sources_scope_id` (`scope_code`,`data_mode`,`source_file_id`),
  UNIQUE KEY `uq_sources_repeat` (`scope_code`,`data_mode`,`sha256`),
  CONSTRAINT `ck_a_report_sources_1` CHECK ((`page_count` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='PDF或照片原始文件；按组织和模式去重';
```

写入保护触发器：
- a_ehr_428fa69ba1827bd547：BEFORE UPDATE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```
- a_ehr_ca603171f293464a9e：BEFORE DELETE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```

## a_report_sync_outbox

**用途：** 正式发布后的事务消息；消费者须幂等处理。

pending待投影/同步，sent已处理。发布与事件必须处于同一个数据库事务，外部消费需幂等。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| event_id | char(36) | 不可空；默认 NULL/未设置 | 一条发布后同步事件UUID；消费者按事件和版本幂等处理。 | 索引:PRIMARY |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_report_publications.report_id；外键→a_review_records.report_id；索引:fk_a_b707468b11812b079d；索引:uq_sync_publication |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_report_publications.revision；外键→a_review_records.revision；索引:fk_a_b707468b11812b079d；索引:uq_sync_publication |
| review_id | char(36) | 不可空；默认 NULL/未设置 | 一条人工审核决策UUID，关联精确报告版本和已核对摘要。 | 外键→a_review_records.review_id；索引:fk_a_b707468b11812b079d |
| status | enum('pending','sent') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 索引:ix_sync_queue |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 索引:ix_sync_queue |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_sync_outbox` (
  `event_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `review_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('pending','sent') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `uq_sync_publication` (`report_id`,`revision`),
  KEY `ix_sync_queue` (`status`,`created_at`),
  KEY `fk_a_b707468b11812b079d` (`report_id`,`revision`,`review_id`),
  CONSTRAINT `fk_a_98ed4f9fe35b58dd27` FOREIGN KEY (`report_id`, `revision`) REFERENCES `a_report_publications` (`report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_a_b707468b11812b079d` FOREIGN KEY (`report_id`, `revision`, `review_id`) REFERENCES `a_review_records` (`report_id`, `revision`, `review_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='正式发布后的事务消息；消费者须幂等处理';
```

写入保护触发器：
- a_ehr_3a6cf215f57e72a452：BEFORE INSERT

```sql
BEGIN
IF NOT EXISTS (SELECT 1 FROM a_report_publications p WHERE p.report_id=NEW.report_id AND p.revision=NEW.revision AND p.review_id=NEW.review_id) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR outbox must reference the confirming publication review';
END IF;
END
```

## a_report_template_mapping

**用途：** 模板映射；没有原始模板包则不编造种子。

模板映射；没有原始模板包则不编造种子

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| template_key | varchar(64) | 不可空；默认 NULL/未设置 | 报告版式/模板稳定编码。用于机构布局识别，不代表一个检查项目或报告实例。 | 索引:PRIMARY |
| mapping_version | varchar(64) | 不可空；默认 NULL/未设置 | 映射目录的发布版本。不能把未经确认的旧规则直接修改成新语义；应新版本发布。 | 索引:PRIMARY |
| template_kind | enum('text_layout','scan_appearance') | 不可空；默认 NULL/未设置 | text_layout为文本布局，scan_appearance为扫描外观模板。 | 无独立索引/外键；通过所属记录关联 |
| institution_key | varchar(255) | 可空；默认 NULL/未设置 | 机构匹配上下文。相同原标签在不同机构可能含义不同，不应只按标签名自动确认。 | 无独立索引/外键；通过所属记录关联 |
| observed_layout | text | 不可空；默认 NULL/未设置 | 观察到的报告布局描述；是模板证据，不是医疗诊断。 | 无独立索引/外键；通过所属记录关联 |
| status | enum('needs_confirmation','confirmed','retired') | 不可空；默认 NULL/未设置 | 当前实体自己的状态，具体枚举和含义见本表状态说明；不同表的status不能混用。 | 无独立索引/外键；通过所属记录关联 |
| validation_ref | varbinary(256) | 可空；默认 NULL/未设置 | 验证资料或来源行的受限引用。本次种子中的xlsx行引用不等于临床确认或原件验证。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_template_mapping` (
  `template_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `mapping_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `template_kind` enum('text_layout','scan_appearance') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `institution_key` varchar(255) COLLATE utf8mb4_0900_bin DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  `observed_layout` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `status` enum('needs_confirmation','confirmed','retired') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '本实体状态，按列枚举',
  `validation_ref` varbinary(256) DEFAULT NULL COMMENT '按冻结 DDL 列定义使用',
  PRIMARY KEY (`template_key`,`mapping_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='模板映射；没有原始模板包则不编造种子';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_report_upload_metadata

**用途：** 每次上传的渠道及元数据；同一原件可有多次上传。

每次上传的渠道及元数据；同一原件可有多次上传

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| upload_id | char(36) | 不可空；默认 NULL/未设置 | 一次实际上传的UUID，区别于去重后的source_file_id。 | 索引:PRIMARY；索引:uq_report_upload_metadata_scope |
| scope_code | varchar(64) | 不可空；默认 NULL/未设置 | 组织/租户访问边界，例如org:10000。由登录账号在服务端确定，客户端不能选择其他单位。 | 外键→a_report_sources.scope_code；索引:fk_a_0d2b6bcb3580e63632；索引:ix_upload_channel_time；索引:uq_report_upload_metadata_scope；索引:uq_upload_request |
| data_mode | enum('real','synthetic') | 不可空；默认 NULL/未设置 | real为正式业务，synthetic为隔离测试。所有档案查询、上传和统计均同时过滤组织与模式。 | 外键→a_report_sources.data_mode；索引:fk_a_0d2b6bcb3580e63632；索引:ix_upload_channel_time；索引:uq_report_upload_metadata_scope；索引:uq_upload_request |
| source_file_id | char(36) | 不可空；默认 NULL/未设置 | 原始PDF或照片UUID。相同组织和模式按文件SHA256去重；正文存于受限文件目录。 | 外键→a_report_sources.source_file_id；索引:fk_a_0d2b6bcb3580e63632 |
| request_key | varchar(96) | 不可空；默认 NULL/未设置 | 客户端或任务幂等键。相同键应对应同一身份/原件/业务操作，不允许重用为其他内容。 | 索引:uq_upload_request |
| upload_channel | enum('miniapp','phone_photo','backend_file','batch_import','legacy_import') | 不可空；默认 NULL/未设置 | miniapp小程序、phone_photo拍照、backend_file后台文件、batch_import批量、legacy_import历史导入；不是医学证据提取方式。 | 索引:ix_upload_channel_time |
| original_file_name | varchar(512) | 不可空；默认 NULL/未设置 | 客户端文件原名，仅作展示与下载名，不能用作服务器存储路径。 | 无独立索引/外键；通过所属记录关联 |
| mime_type | varchar(127) | 不可空；默认 NULL/未设置 | 经实际文件内容确认的类型，不只相信扩展名或客户端Content-Type。 | 无独立索引/外键；通过所属记录关联 |
| file_size | bigint unsigned | 不可空；默认 NULL/未设置 | 原文件字节数。当前接口限制单文件20 MB。 | 无独立索引/外键；通过所属记录关联 |
| uploader_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 真实上传人受限标识；后台取登录账号，小程序取专用令牌绑定身份。 | 无独立索引/外键；通过所属记录关联 |
| uploaded_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC实际上传时间。 | 索引:ix_upload_channel_time |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_report_upload_metadata` (
  `upload_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'upload_id',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'scope_code',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'data_mode',
  `source_file_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'source_file_id',
  `request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'request_key',
  `upload_channel` enum('miniapp','phone_photo','backend_file','batch_import','legacy_import') COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'upload_channel',
  `original_file_name` varchar(512) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'original_file_name',
  `mime_type` varchar(127) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'mime_type',
  `file_size` bigint unsigned NOT NULL COMMENT 'file_size',
  `uploader_ref` varbinary(256) NOT NULL COMMENT 'uploader_ref',
  `uploaded_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`upload_id`),
  UNIQUE KEY `uq_report_upload_metadata_scope` (`scope_code`,`data_mode`,`upload_id`),
  UNIQUE KEY `uq_upload_request` (`scope_code`,`data_mode`,`request_key`),
  KEY `ix_upload_channel_time` (`scope_code`,`data_mode`,`upload_channel`,`uploaded_at`),
  KEY `fk_a_0d2b6bcb3580e63632` (`scope_code`,`data_mode`,`source_file_id`),
  CONSTRAINT `fk_a_0d2b6bcb3580e63632` FOREIGN KEY (`scope_code`, `data_mode`, `source_file_id`) REFERENCES `a_report_sources` (`scope_code`, `data_mode`, `source_file_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='每次上传的渠道及元数据；同一原件可有多次上传';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。

## a_review_records

**用途：** 医生审核决策历史；退回与驳回分别记录。

决策return/reject/confirm分别留痕，审核摘要精确绑定版本；原记录只能追加。

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| review_id | char(36) | 不可空；默认 NULL/未设置 | 一条人工审核决策UUID，关联精确报告版本和已核对摘要。 | 索引:PRIMARY；索引:uq_review_decision_digest；索引:uq_review_revision |
| report_id | char(36) | 不可空；默认 NULL/未设置 | 逻辑体检报告UUID。一个原件对应的逻辑报告下可以有多次修订版本。 | 外键→a_health_reports.report_id；索引:uq_review_decision_digest；索引:uq_review_replay；索引:uq_review_revision |
| revision | int unsigned | 不可空；默认 NULL/未设置 | 报告版本号，从1开始；修订应为前一版本+1。指标、证据、结论和审核均绑定版本。 | 外键→a_health_reports.revision；索引:uq_review_decision_digest；索引:uq_review_replay；索引:uq_review_revision |
| reviewer_ref | varbinary(256) | 不可空；默认 NULL/未设置 | 审核人员受限标识，当前由登录医生账号确定，不从前端提交审核人ID。 | 无独立索引/外键；通过所属记录关联 |
| decision | enum('return','reject','confirm') | 不可空；默认 NULL/未设置 | return退回补充、reject驳回、confirm确认。三者分别留痕，不能互相冒充。 | 索引:uq_review_decision_digest |
| reviewed_digest | binary(32) | 不可空；默认 NULL/未设置 | 规范化已核对内容的SHA256（32字节），质量校验、医生审核和发布必须相同。 | 索引:uq_review_decision_digest |
| decision_key | varchar(96) | 不可空；默认 NULL/未设置 | 审核决策幂等键，同一报告版本内唯一，防止网络重试产生重复决策。 | 索引:uq_review_replay |
| issue_disposition | json | 不可空；默认 NULL/未设置 | JSON问题处置与说明，包括身份已人工核验、审核备注；不应存放访问令牌或密码。 | 无独立索引/外键；通过所属记录关联 |
| created_at | datetime(6) | 不可空；默认 CURRENT_TIMESTAMP(6) | UTC记录建立时间；不是体检日期、报告日期或事件发生日期。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_review_records` (
  `review_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告 UUID',
  `revision` int unsigned NOT NULL COMMENT '报告版本号',
  `reviewer_ref` varbinary(256) NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `decision` enum('return','reject','confirm') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `reviewed_digest` binary(32) NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `decision_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `issue_disposition` json NOT NULL COMMENT '按冻结 DDL 列定义使用',
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '记录创建时间 UTC',
  PRIMARY KEY (`review_id`),
  UNIQUE KEY `uq_review_revision` (`report_id`,`revision`,`review_id`),
  UNIQUE KEY `uq_review_replay` (`report_id`,`revision`,`decision_key`),
  UNIQUE KEY `uq_review_decision_digest` (`report_id`,`revision`,`review_id`,`decision`,`reviewed_digest`),
  CONSTRAINT `fk_a_768e4608daf47abeeb` FOREIGN KEY (`report_id`, `revision`) REFERENCES `a_health_reports` (`report_id`, `revision`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ck_a_review_records_1` CHECK ((json_type(`issue_disposition`) in (_utf8mb4'ARRAY',_utf8mb4'OBJECT')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='医生审核决策历史；退回与驳回分别记录';
```

写入保护触发器：
- a_ehr_9f6012fd9c8df0cc6f：BEFORE UPDATE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```
- a_ehr_b0681304f42d195e58：BEFORE DELETE

```sql
BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END
```

## a_standard_field_dictionary

**用途：** 01表67个字段的版本化定义；必填键不等于报告必有非空值。

01表67个字段的版本化定义；必填键不等于报告必有非空值

引擎：InnoDB；默认排序规则：utf8mb4_0900_bin。

| 字段 | 类型 | 可空 / 默认值 | 业务含义 | 关联与索引 |
|---|---|---|---|---|
| field_key | varchar(64) | 不可空；默认 NULL/未设置 | 标准报告字段编码，例如full_name、exam_date、hazard_factors。共享JSON数组依赖此键定位观察。 | 索引:PRIMARY |
| dictionary_version | varchar(64) | 不可空；默认 NULL/未设置 | 检查项目或字段字典的发布版本，例如2.0.0；结果应保留匹配时使用的版本。 | 索引:PRIMARY |
| field_name | varchar(255) | 不可空；默认 NULL/未设置 | 标准字段的中文名称，例如姓名、体检日期。不是职工实际姓名或实际检查值。 | 无独立索引/外键；通过所属记录关联 |
| field_category | varchar(100) | 不可空；默认 NULL/未设置 | 标准字段类别：职工基本、报告信息、正式结论、项目明细、原始证据、职业信息。 | 无独立索引/外键；通过所属记录关联 |
| target_table | varchar(64) | 不可空；默认 NULL/未设置 | 标准字段的a_物理目标表；不意味着字段值一定是固定列。 | 无独立索引/外键；通过所属记录关联 |
| target_column | varchar(64) | 不可空；默认 NULL/未设置 | 标准字段物理目标列，可能是共享field_values JSON列。 | 无独立索引/外键；通过所属记录关联 |
| json_path | varchar(255) | 不可空；默认 NULL/未设置 | 数据契约中的逻辑JSON路径；同一数组还需按field_key筛选定位。 | 无独立索引/外键；通过所属记录关联 |
| definition | json | 不可空；默认 NULL/未设置 | 01表该标准字段的完整元数据JSON，包括证据、敏感级别与来源等。 | 无独立索引/外键；通过所属记录关联 |
| source_row | smallint unsigned | 不可空；默认 NULL/未设置 | 输入工作簿中的原始行号，用于复核本行字典来源。 | 无独立索引/外键；通过所属记录关联 |

复合外键须作为一组理解，不能单独拿其中某一列跨组织、模式或版本关联。

实际DDL（包含CHECK约束与完整复合索引）：

```sql
CREATE TABLE `a_standard_field_dictionary` (
  `field_key` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'field_key',
  `dictionary_version` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'dictionary_version',
  `field_name` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'field_name',
  `field_category` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'field_category',
  `target_table` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'target_table',
  `target_column` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'target_column',
  `json_path` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'json_path',
  `definition` json NOT NULL COMMENT 'definition',
  `source_row` smallint unsigned NOT NULL COMMENT 'source_row',
  PRIMARY KEY (`field_key`,`dictionary_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='01表67个字段的版本化定义；必填键不等于报告必有非空值';
```

写入保护触发器：
无表级触发器，仍需遵守外键、CHECK及接口事务校验。
