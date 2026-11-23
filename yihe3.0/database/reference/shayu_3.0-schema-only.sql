-- Schema only, exported 2026-11-23. No employee rows, reports, credentials or original files.
-- Existing objects are not dropped. Apply to an empty database. Views/routines/triggers are excluded.
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `a_agent_actions` (
  `action_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作预览ID，不交给模型确认',
  `conversation_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作发起会话',
  `turn_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作发起轮次',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '目标健康档案ID，执行时再次检查单位和权限',
  `action_type` enum('delete_employee','update_employee') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '白名单操作类型',
  `action_status` enum('pending','confirmed','cancelled') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'pending' COMMENT '只有人工确认接口可以执行',
  `payload_json` json NOT NULL COMMENT '待执行变更及删除原因',
  `snapshot_json` json NOT NULL COMMENT '姓名工号、行版本、报告数量及内容摘要',
  `result_json` json DEFAULT NULL COMMENT '执行回执；重复确认返回同一回执',
  `expires_at` datetime(6) NOT NULL COMMENT 'UTC十分钟后失效，过期需重新预览',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC创建时间',
  `resolved_at` datetime(6) DEFAULT NULL COMMENT 'UTC确认或取消时间',
  PRIMARY KEY (`action_id`),
  KEY `ix_agent_actions` (`conversation_id`,`action_status`),
  KEY `fk_agent_action_turn` (`turn_id`),
  CONSTRAINT `fk_agent_action_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `a_agent_conversations` (`conversation_id`),
  CONSTRAINT `fk_agent_action_turn` FOREIGN KEY (`turn_id`) REFERENCES `a_agent_turns` (`turn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='人工确认操作票据；不把删除权限交给模型';

CREATE TABLE `a_agent_conversations` (
  `conversation_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端生成的智能体会话ID',
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '当前账号单位范围',
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '正式或隔离测试数据',
  `owner_user` varchar(96) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '后台登录账号编号',
  `title` varchar(100) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '第一条提问生成的本地标题',
  `lease_turn_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '正在执行的轮次，防止并发篡改上下文',
  `lease_until` datetime(6) DEFAULT NULL COMMENT 'UTC执行租约到期时间',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC创建时间',
  `updated_at` datetime(6) NOT NULL COMMENT 'UTC更新时间',
  `client_type` enum('admin','miniapp') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'admin' COMMENT '智能体访问渠道',
  `subject_record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '个人对话固定绑定的本人档案',
  `binding_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '创建对话时的有效身份绑定版本',
  PRIMARY KEY (`conversation_id`),
  KEY `ix_agent_owner` (`scope_code`,`data_mode`,`owner_user`,`updated_at`),
  KEY `ix_agent_personal` (`client_type`,`owner_user`,`data_mode`,`binding_id`,`updated_at`),
  CONSTRAINT `ck_agent_personal_subject` CHECK (((`client_type` = _utf8mb4'admin') or ((`subject_record_id` is not null) and (`binding_id` is not null))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='数字健康师私有会话，不与其他账号共享';

CREATE TABLE `a_agent_tool_logs` (
  `tool_log_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '工具调用记录ID',
  `turn_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '所属轮次',
  `tool_name` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '服务端注册工具名称',
  `arguments_json` json NOT NULL COMMENT '通过JSON参数校验的工具参数',
  `outcome` varchar(32) COLLATE utf8mb4_0900_bin NOT NULL COMMENT '成功或业务错误状态',
  `duration_ms` bigint NOT NULL COMMENT '工具执行耗时毫秒',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC调用时间',
  PRIMARY KEY (`tool_log_id`),
  KEY `ix_agent_tool_turn` (`turn_id`,`created_at`),
  CONSTRAINT `fk_agent_tool_turn` FOREIGN KEY (`turn_id`) REFERENCES `a_agent_turns` (`turn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='真实业务工具审计，不记录API密钥';

CREATE TABLE `a_agent_turns` (
  `turn_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端对话轮次ID',
  `conversation_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '所属会话',
  `request_key` varchar(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '前端重试使用同一幂等键',
  `request_hash` char(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '消息与选中职工摘要，阻止幂等键换内容',
  `user_message` text COLLATE utf8mb4_0900_bin NOT NULL COMMENT '用户本轮原始提问',
  `selected_record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '用户主动选中的职工，仅作为查询上下文',
  `turn_status` enum('queued','running','completed','failed') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '异步排队、执行、完成或失败',
  `response_json` json DEFAULT NULL COMMENT '返回给页面的文本、来源、工具轨迹和待确认操作',
  `transcript_json` json DEFAULT NULL COMMENT 'Responses输出及function_call_output，仅供服务端续接',
  `created_at` datetime(6) NOT NULL COMMENT 'UTC开始时间',
  `completed_at` datetime(6) DEFAULT NULL COMMENT 'UTC结束时间',
  `request_context_json` json DEFAULT NULL COMMENT '本人报告上下文；身份不接受客户端指定',
  `run_token` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL COMMENT '异步执行领取凭据，防止超时回写',
  `started_at` datetime(6) DEFAULT NULL COMMENT 'UTC异步开始时间',
  `deadline_at` datetime(6) DEFAULT NULL COMMENT 'UTC处理截止时间',
  PRIMARY KEY (`turn_id`),
  UNIQUE KEY `uq_agent_request` (`conversation_id`,`request_key`),
  KEY `ix_agent_turns` (`conversation_id`,`created_at`),
  KEY `ix_agent_queue` (`turn_status`,`created_at`),
  CONSTRAINT `fk_agent_turn_conversation` FOREIGN KEY (`conversation_id`) REFERENCES `a_agent_conversations` (`conversation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='服务端保管对话历史，浏览器不能伪造工具输出';

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

CREATE TABLE `a_digital_worker_ai_attempts` (
  `attempt_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `task_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `active_task_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL,
  `requested_by` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `task_version` int NOT NULL,
  `request_state` enum('queued','running','succeeded','failed','discarded') NOT NULL,
  `lease_until` datetime(6) DEFAULT NULL,
  `model_name` varchar(160) DEFAULT NULL,
  `output_text` mediumtext,
  `usage_json` json DEFAULT NULL,
  `error_code` varchar(64) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `archive_context_json` json DEFAULT NULL COMMENT '授权档案完整性摘要及数据来源；不包含报告原件或临床明细',
  `verification_json` json DEFAULT NULL COMMENT '原件核对结果、逐项差异、页码、原文证据及覆盖范围',
  `result_json` json DEFAULT NULL COMMENT '按任务类型生成的结构化工作结果及依据',
  `worker_settings_json` json DEFAULT NULL,
  PRIMARY KEY (`attempt_id`),
  UNIQUE KEY `uq_digital_worker_ai_active` (`active_task_id`),
  KEY `ix_digital_worker_ai_pending` (`request_state`,`created_at`),
  KEY `ix_digital_worker_ai_task` (`task_id`,`created_at`),
  CONSTRAINT `fk_digital_worker_ai_task` FOREIGN KEY (`task_id`) REFERENCES `a_digital_worker_tasks` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `a_digital_worker_settings` (
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `data_mode` enum('real','synthetic') NOT NULL,
  `worker_id` varchar(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `settings_json` json NOT NULL,
  `row_version` int NOT NULL DEFAULT '0',
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`scope_code`,`data_mode`,`worker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `a_digital_worker_settings_events` (
  `event_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `data_mode` enum('real','synthetic') NOT NULL,
  `worker_id` varchar(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `actor` varchar(64) NOT NULL,
  `before_json` json NOT NULL,
  `after_json` json NOT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `ix_worker_settings_events` (`scope_code`,`data_mode`,`worker_id`,`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `a_digital_worker_task_events` (
  `event_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `task_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `actor` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `action` varchar(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `from_status` varchar(24) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL,
  `to_status` varchar(24) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `note` varchar(2000) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `ix_digital_worker_events` (`task_id`,`created_at`,`event_id`),
  CONSTRAINT `fk_digital_worker_event_task` FOREIGN KEY (`task_id`) REFERENCES `a_digital_worker_tasks` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `a_digital_worker_tasks` (
  `task_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `data_mode` enum('real','synthetic') NOT NULL,
  `worker_id` varchar(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `task_type` varchar(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `title` varchar(120) NOT NULL,
  `member_code` varchar(100) NOT NULL DEFAULT '',
  `record_id` char(36) CHARACTER SET ascii COLLATE ascii_bin DEFAULT NULL,
  `priority` enum('normal','high','urgent') NOT NULL,
  `instructions` varchar(2000) NOT NULL DEFAULT '',
  `task_status` enum('queued','in_progress','pending_review','completed','cancelled') NOT NULL DEFAULT 'queued',
  `result_note` varchar(2000) NOT NULL DEFAULT '',
  `created_by` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `request_key` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `payload_sha256` binary(32) NOT NULL,
  `row_version` int NOT NULL DEFAULT '1',
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `task_context_json` json DEFAULT NULL COMMENT '医生补充的本次随访、方案或投诉事实',
  `context_saved_at` datetime(6) DEFAULT NULL,
  `context_saved_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `uq_digital_worker_request` (`scope_code`,`data_mode`,`created_by`,`request_key`),
  KEY `ix_digital_worker_queue` (`scope_code`,`data_mode`,`worker_id`,`task_status`,`created_at`),
  KEY `ix_digital_worker_updates` (`scope_code`,`data_mode`,`updated_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

CREATE TABLE `a_ehr_ocr_receipts` (
  `job_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '识别任务ID，绑定该任务的报告版本',
  `payload_sha256` binary(32) NOT NULL COMMENT '规范化回调JSON摘要，保护重复回调幂等性',
  `response_json` longtext COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'OCR候选响应快照，医学数值按原始字符串保存',
  `received_at` datetime(6) NOT NULL COMMENT 'UTC成功接收时间',
  PRIMARY KEY (`job_id`),
  CONSTRAINT `fk_a_ocr_receipt_job` FOREIGN KEY (`job_id`) REFERENCES `a_extraction_jobs` (`job_id`),
  CONSTRAINT `ck_a_ocr_receipt_json` CHECK (json_valid(`response_json`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='外部OCR成功回调原始候选快照；不代表审核或归档';

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

CREATE TABLE `a_health_schema_migration` (
  `version` varchar(64) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'version',
  `checksum` char(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'checksum',
  `source_sha256` char(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'source_sha256',
  `description` varchar(500) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'description',
  `applied_by` varchar(255) COLLATE utf8mb4_0900_bin NOT NULL COMMENT 'applied_by',
  `applied_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT 'UTC时间',
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='本模块结构迁移版本及输入校验摘要';

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

CREATE TABLE `a_miniapp_rate_limits` (
  `bucket_key` binary(32) NOT NULL COMMENT '操作、模式、用户或IP的摘要，不存明文IP',
  `window_start` datetime NOT NULL COMMENT 'UTC固定限流窗口起点',
  `attempt_count` int unsigned NOT NULL COMMENT '独立事务累加，错误请求也计数',
  PRIMARY KEY (`bucket_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信登录及身份验证限流，绑定事务回滚不清空计数';

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

CREATE TABLE `a_report_deletions` (
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL,
  `actor_ref` varbinary(128) NOT NULL,
  `reason` varchar(600) COLLATE utf8mb4_0900_bin NOT NULL,
  `source_ids` json NOT NULL,
  `job_ids` json NOT NULL,
  `deleted_counts` json NOT NULL,
  `state` enum('purging','deleted') COLLATE utf8mb4_0900_bin NOT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `completed_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  KEY `ix_report_deletions_scope` (`scope_code`,`data_mode`,`completed_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='受控报告删除凭证；不保存报告正文';

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

CREATE TABLE `a_report_file_cleanup_tasks` (
  `cleanup_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `scope_code` varchar(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `data_mode` enum('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL,
  `report_id` char(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `target_kind` enum('source','model_artifacts') COLLATE utf8mb4_0900_bin NOT NULL,
  `target_ref` varbinary(512) NOT NULL,
  `status` enum('pending','done','failed') COLLATE utf8mb4_0900_bin NOT NULL DEFAULT 'pending',
  `attempts` int NOT NULL DEFAULT '0',
  `last_error` varchar(100) COLLATE utf8mb4_0900_bin DEFAULT NULL,
  `created_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`cleanup_id`),
  KEY `ix_report_cleanup_queue` (`data_mode`,`status`,`updated_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='事务提交后的原件和模型候选清理队列';

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

CREATE TABLE `cs_doctor_chat_list` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '医生类型',
  `info` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='配置聊天医生列表';

CREATE TABLE `cs_org_doctor_chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '机构id',
  `doc_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '医生userid',
  `type` int DEFAULT NULL COMMENT '咨询类型id cs_doctor_chat_list主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='机构医生咨询绑定列表';

CREATE TABLE `food_writingfile` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名字',
  `content` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内容',
  `type` int DEFAULT NULL COMMENT '类型 1，高  2，底',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='膳食文案';

CREATE TABLE `integral_shopping_grouping` (
  `grouping_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '分类名',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '父分类ID',
  `sort_id` int DEFAULT NULL COMMENT '顺序id',
  `add_time` datetime DEFAULT NULL,
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构',
  PRIMARY KEY (`grouping_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='积分商城分组表';

CREATE TABLE `jiai_alarm` (
  `aid` int NOT NULL AUTO_INCREMENT,
  `terminalId` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '“1” sos 2跌倒 3脱手 4久坐 5 低电 6家庭医生 7房颤',
  `battery` int DEFAULT NULL COMMENT '如果是低电预警 则有值，其他如果是低电预警 则有值，其他如果是低电预警 则有值，其他如果是低电预警 则有值，其他如果是低电预警 则有值，其他无值',
  `address` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '如果是sos就有值，其他无值',
  `longitude` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'sos高德经度，sos才有值',
  `latitude` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'sos高德纬度，sos才有值',
  `establishTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `jiai_enclosure` (
  `eid` int NOT NULL AUTO_INCREMENT,
  `terminalId` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `longitude` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '经度',
  `latitude` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '纬度',
  `radius` int DEFAULT NULL COMMENT '半径',
  `establishTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `jiai_interface_list` (
  `jid` int NOT NULL AUTO_INCREMENT,
  `api` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '地址',
  `describe` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '地址说明',
  `key` varchar(222) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `cpid` varchar(222) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`jid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `jiai_locate` (
  `lid` int NOT NULL AUTO_INCREMENT,
  `imei` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `lat` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '纬度',
  `lon` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '经度',
  `establishTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`lid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `jiai_watch_info` (
  `id` int DEFAULT NULL,
  `terminalId` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '设备id',
  `gpsflag` int DEFAULT NULL COMMENT '0关闭1开启',
  `positInterval` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '定位间隔秒',
  `sosname1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'sos姓名1',
  `mobile1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'sos手机号1',
  `sosname2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `mobile2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sosname3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `mobile3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `heartInterval` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率间隔',
  `bloodPressureInterval` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压间隔',
  `temperaturePressureInterval` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体温间隔',
  PRIMARY KEY (`terminalId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `layim_big_group` (
  `id` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_at` bigint DEFAULT NULL,
  `avatar` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `group_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `uid` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKol8kn1ah7lj1olypbu7r40tsw` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `layim_friend_group` (
  `id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_at` bigint DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKawe6k1o9mujam11lxiwcwbqpu` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `layim_msg_record` (
  `id` bigint NOT NULL,
  `from_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `to_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `room_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `contents` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_at` bigint DEFAULT NULL,
  `mtype` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `layim_user` (
  `id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_at` bigint DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `sign` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `layim_user_big_group` (
  `uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  KEY `FKftgx1bft0t9o6xo74evcfhlsd` (`group_id`) USING BTREE,
  KEY `FKpyb8edgdcgjw5pd0g3mrmf3vt` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `layim_user_friend_group` (
  `uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  KEY `FKiio3b59cbsn1orj8ps2mlxk8j` (`group_id`) USING BTREE,
  KEY `FK3g6jqnmqu3lobq57235cawwq` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `monitor_insert_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usercode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `usercode` (`usercode`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_annual_occupational_health_promotion_training_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病防治法律、行政法规、规章、标准、文件',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_anxiety_disorder_intervention_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_commodity_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `merchandise_id` int DEFAULT NULL COMMENT '商品id',
  `inventory` int DEFAULT NULL COMMENT '积分',
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_coronary_heart_disease_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `height` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身高',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `HDLC` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'HDLC',
  `fasting_blood_glucose` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '空腹血糖',
  `cholesterol` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '胆固醇',
  `triglyceride` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '甘油三酯',
  `electrocardiogram_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心电图检查',
  `exercise_load_test` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动负荷实验',
  `myocardial_enzyme` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌酶',
  `coronary_angiography` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌酶',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `special_treatment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '特殊治疗',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `non_pharmacological_treatment_measures` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '非药物治疗措施',
  `non_pharmacological_treatment_measures1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '非药物治疗措施1',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_depression_intervention_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_dermatosis_patient_service_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `chief_complaint` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主诉',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_employee_personal_information_card` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '出生',
  `name_used_before` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '曾用名',
  `marital_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '婚姻状况',
  `degree_education` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '文化程度',
  `native_place` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '籍贯',
  `my_phone_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '本人电话',
  `hobby` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '嗜好',
  `id_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `occupational_history_exposure_history_to_occupational_hazards` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业史及职业病危害接触史',
  `text_json` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `menstrual_reproductive_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '月经史及生育史',
  `testing_results_occupational_hazards_in_workplace` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '工作场所职业病危害因素检测结果\r\n',
  `previous_occupational_health_examination_results` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '历次职业健康检查结果及处理情况',
  `sign` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1.已签字2.需要签字',
  `notice_occupational_health_examination_results` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业健康检查结果通知书',
  `occupational_health_examination1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业健康体检报告',
  `occupational_health_examination2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业健康体检报告',
  `occupational_health_examination3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业健康体检报告',
  `occupational_health_examination4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业健康体检报告',
  `examination_results` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '历次职业健康检查结果及处理情况',
  `examination_results_sign` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1.已签字2.需要签字',
  `examination_results_file` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '文件',
  `regarding_materials` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '需要存入职业健康监护档案的其它有关材料',
  `notes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '记事',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其它',
  `photo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '照片',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `reg_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_employee_psychological_counseling_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_affiliation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '所属客户',
  `customer_contact` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '联系人',
  `reg_id` int DEFAULT NULL COMMENT '服务人id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别',
  `age` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别',
  `company` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '单位',
  `departments` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '部门',
  `service_date` date DEFAULT NULL COMMENT '服务日期',
  `chief_complaint` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主诉',
  `objective_observation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客观观察',
  `assessment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评估',
  `plan` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '计划',
  `important_interaction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '重要互动',
  `family_social_background` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家庭和社会背景',
  `resources_strength` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '资源和力量',
  `consultation_effectiveness_feedback` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '咨询效果和反馈',
  `next_visit_date` date DEFAULT NULL COMMENT '下次来访日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_gout_patient_service_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `uric_acid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `uric_acid1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况（咸淡）',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `health_education` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康教育',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_impact_assessment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '管理id',
  `service_date` date DEFAULT NULL COMMENT '年份',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `file_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '文件地址',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_individual_occupational_health_monitoring_records_workers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `stage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阶段',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `entry_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '项目名称',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设项目职业卫生“三同时”审查登记表',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_insomnia_intervention_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_intervention_record_form_for_high_risk_population` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '编号id',
  `followup_date` date DEFAULT NULL COMMENT '随访日期',
  `follow_up_method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `disease` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `disease1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体 重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量2',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量（两）',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量（两）1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动1',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动2',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动3',
  `daily_alcohol_consumption2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量（两）2',
  `daily_alcohol_consumption3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量（两）3',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `auxiliary_detection` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance_nature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应1',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况1',
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `next_followup_date` datetime DEFAULT NULL COMMENT '下次随访日期',
  `psychological_counselor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '心理咨询师',
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_list_chronic_disease_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `json_data` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `isnert_time` datetime DEFAULT NULL,
  `doctor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_list_psychological_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_affiliation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '所属客户',
  `customer_contact` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '联系人',
  `reg_id` int DEFAULT NULL COMMENT '服务人id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `sex` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别',
  `age` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '年龄',
  `company` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '单位',
  `departments` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '部门',
  `service_date` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '病症',
  `chief_complaint` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '治疗情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_medical_examination_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT 'new_reg\r\n关联的id',
  `gender` int DEFAULT NULL COMMENT '记录时性别1.男2.女',
  `age` int DEFAULT NULL COMMENT '记录时年龄',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '记录时电话',
  `marriage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '婚姻',
  `occupation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '职业',
  `height` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'BMI',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `blood_oxygen` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血氧',
  `personal_medical_examination_report` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '个人体检报告',
  `stage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阶段1.上岗前2.在岗期间3.离岗后',
  `is_check` int DEFAULT NULL COMMENT '是否确认1.确认',
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `signature` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '签名图片',
  `v16` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `v17` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `v18` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_monitoring_occupational_hazard_factors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `stage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阶段',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `entry_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '项目名称',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病危害因素监测与检测评价档案',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `file2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file5` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file6` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file7` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file8` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file9` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_obesity_patient_service_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `waist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰围',
  `lower_limb_edema` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '下肢水肿',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `beverage_snack_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮料零食情况',
  `dietary_bias` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食偏颇',
  `health_education` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康教育',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation12` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation13` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '服务方式',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_obsessive_compulsive_disorder_intervention_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_occupational_health_management_archives` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病防治法律、行政法规、规章、标准、文件',
  `file2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病防治领导机构及职业卫生管理机构成立文件',
  `file3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病防治年度计划及实施方案',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `file4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file5` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file6` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file7` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file8` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file9` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file10` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file11` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file12` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file13` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_occupational_health_promotion_training_archive` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `stage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阶段',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `entry_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '项目名称',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业卫生宣传培训档案',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `file2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_other_psychological_disorders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_patients_with_abnormal_blood_lipids` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `waist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1',
  `waist1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '2',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `health_education` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康教育',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_pneumoconiosis_service_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL,
  `service_date` date DEFAULT NULL,
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation12` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `next_service_date` date DEFAULT NULL,
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_prostatitis_patient_service_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '症状',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_psychological_assessment_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '手机号',
  `number_evaluations` int DEFAULT NULL COMMENT '评估次数',
  `latest_evaluation_date` datetime DEFAULT NULL,
  `evaluation_results` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评估结果',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_record_form_for_chronic_gastritis_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `chief_complaint` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '上腹部压痛感',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_record_form_for_stroke_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `complication` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '并发症',
  `complication1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `waist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰围',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `blood_sugar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血糖',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `breath` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_reg` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '档案id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名字',
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户code',
  `sex` int DEFAULT NULL COMMENT '1.男2.女',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `work_unit` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '工作单位',
  `department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '部门',
  `rog_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构code',
  `id_card` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身份证信息',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `blood_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血型',
  `marriage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '婚姻',
  `history_drug_allergies` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物过敏史',
  `exposure_history` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '暴露史',
  `medical_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '疾病既往史',
  `surgical_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '手术既往史',
  `medical_history_trauma` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '外伤既往史',
  `blood_transfusion_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '输血既往史',
  `family_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '家族史',
  `genetic_history` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '遗传病史',
  `disability_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '残疾情况',
  `kitchen_exhaust_facilities` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '厨房排风设施',
  `fuel_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '燃料类型',
  `drinking_water` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮水',
  `toilet` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '厕所',
  `poultry_livestock_pens` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '禽畜栏',
  `responsible_health_manager_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健管师code',
  `archivist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '建档人code',
  `insert_date` datetime DEFAULT NULL COMMENT '建档时间',
  `age` int DEFAULT NULL COMMENT '年龄',
  `nation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '民族',
  `culture` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '文化程度',
  `v666` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `v777` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `intervention_time` datetime DEFAULT NULL COMMENT '干预时间',
  `v888` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '班组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_risk` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评估表单\r\n',
  `gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别',
  `risk_frequency` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评估次数',
  `risk_time` date DEFAULT NULL COMMENT '最新评估日期',
  `highrisk` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高风险',
  `be_ill` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '患病或异常',
  `inser_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `new_service_form_for_osteoporosis_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况（咸淡）',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_service_record_form_for_breast_hyperplasia_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮食情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_service_record_form_for_hypertensive_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况（咸淡）',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_service_record_form_for_osteoarthritis_patients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `position_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '位部',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `salt_intake_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况（咸淡）',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_stress_intervention_record_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '随访日期',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '随访方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状1',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压1',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重1',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数1',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `daily_smoking_volume1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量1',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `daily_alcohol_consumption1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量1',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `dietary_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '摄盐情况',
  `psychological_adjustment` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理调整',
  `compliance_behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用药情况',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `dietary_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '涉盐情况1',
  `health_guidance_opinions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康指导意见',
  `adverse_drug_reactions1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_three_simultaneous` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `stage` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阶段',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `entry_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '项目名称',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设项目职业卫生“三同时”审查登记表',
  `file2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设项目批准文件',
  `file3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病危害预评价委托书与预评价报告',
  `file4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设项目职业病防护设施设计专篇',
  `file5` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病危害控制效果评价委托书与控制效果评价报告',
  `file6` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设单位对职业病危害预评价报告、职业病防护设施设计专篇、职业病防护设施控制效果评价报告的评审意见',
  `file7` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '安全监管部门审核、审查、验收批文',
  `file8` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '建设项目职业病危害防治法律责任承诺书',
  `file9` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '全套竣工图纸、验收报告、竣工总结',
  `file10` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '工程改建、扩建及维修、使用中变更的图纸及有关材料',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_type_2_diabetes_patient_service_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reg_id` int DEFAULT NULL COMMENT '服务日期',
  `service_date` date DEFAULT NULL COMMENT '服务方式',
  `service_mode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  `symptom` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `symptom1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `blood_pressure` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `blood_pressure1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血压',
  `weight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `weight1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `body_mass_index` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `body_mass_index1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `heart_rate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `other` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `daily_smoking_volume` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日吸烟量（支）',
  `daily_alcohol_consumption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日饮酒量',
  `motion` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `motion3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动',
  `staple_food` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主食（克/天）',
  `other_inspections` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他检查*',
  `testing_date` date DEFAULT NULL COMMENT '检测日期',
  `fasting_blood_glucose` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '空腹血糖',
  `compliance_behavior_` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '遵医行为',
  `supplementary_examination` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '辅助检查',
  `medication_compliance` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服药依从性',
  `adverse_drug_reactions` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药物不良反应',
  `this_service_classification` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '此次服务分类',
  `medication_situation` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation10` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `medication_situation11` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原 因',
  `institution_department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构及科别',
  `next_service_date` date DEFAULT NULL COMMENT '下次服务日期',
  `health_management_specialist` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '健康管理师',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `new_unit_occupational_health_surveillance_management_archives` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '客户id',
  `staffing_personnel` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '编制人员',
  `preparation_time` date DEFAULT NULL COMMENT '编制时间',
  `file_names` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案名称',
  `archive_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案编号',
  `archive_year` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '档案年度',
  `archive_time` date DEFAULT NULL COMMENT '归档时间',
  `file1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '职业病防治法律、行政法规、规章、标准、文件',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `file2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file4` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file5` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file6` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `file7` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `nodelist` (
  `id` int DEFAULT NULL,
  `nodecontent` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `pid` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务blob触发器';

CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务日期';

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务表达式';

CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `fired_time` bigint NOT NULL,
  `sched_time` bigint NOT NULL,
  `priority` int NOT NULL,
  `state` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务发射触发';

CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务详情';

CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务锁';

CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务暂停触发';

CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `last_checkin_time` bigint NOT NULL,
  `checkin_interval` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务调度程序状态';

CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `repeat_count` bigint NOT NULL,
  `repeat_interval` bigint NOT NULL,
  `times_triggered` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务简单的触发器';

CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `int_prop_1` int DEFAULT NULL,
  `int_prop_2` int DEFAULT NULL,
  `long_prop_1` bigint DEFAULT NULL,
  `long_prop_2` bigint DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务simprop触发器';

CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `next_fire_time` bigint DEFAULT NULL,
  `prev_fire_time` bigint DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `start_time` bigint NOT NULL,
  `end_time` bigint DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `misfire_instr` smallint DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务触发器';

CREATE TABLE `sleep_remind` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户code',
  `remind_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户选的提醒时间',
  `qtime` timestamp NULL DEFAULT NULL COMMENT '记录时间',
  `timer_id` int DEFAULT NULL COMMENT '定时器ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠提醒';

CREATE TABLE `sleep_skip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '跳转人code',
  `qtime` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠评估点击跳过';

CREATE TABLE `sys_basics_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `self_registration` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自主注册''Y''开启''N''关闭',
  `reg_card_node` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注册卡节点',
  `login_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录页logo',
  `left_navigation_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '左侧导航logo',
  `browser_tab_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览器标签logo',
  `report_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报告logo',
  `applet_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '小程序名称',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签名（副标题）',
  `official_accounts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公众号二维码',
  `mini_program` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '小程序二维码',
  `applet_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '小程序logo',
  `applet_Login_background` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录页背景图',
  `initial_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '初始密码',
  `system_prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统前缀',
  `copyright` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '版权',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统基础信息表';

CREATE TABLE `sys_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT '0' COMMENT '文件类型',
  `url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `userId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='文件上传';

CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT='系统访问记录';

CREATE TABLE `t_onemachine_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户id',
  `checkno` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检编号',
  `name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `sex` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '性别（1 男；2 女）',
  `age` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '年龄',
  `card_num` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `city_id` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检地点编号',
  `mac` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'Mac地址',
  `gmt_create` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检时间',
  `height` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身高',
  `weight` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `bmi` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重指数',
  `temperature` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体温',
  `diastolic_pressure` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '舒张压',
  `systolic_pressure` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '收缩压',
  `pulse` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脉率',
  `blood_sugar` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血糖',
  `uric_acid` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿酸',
  `cholesterin` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '胆固醇',
  `oxygen_saturation` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血氧饱和度',
  `electrocardiogram_value` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `electrocardiogram_result` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心电图结论',
  `ffatp` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脂肪率',
  `fbmr` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '基础代谢',
  `ftbwp` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体内水率',
  `ffat` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脂肪重量',
  `fmuscle` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌肉量',
  `fbone` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨盐重量',
  `fproteinp` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蛋白质率',
  `fatgrade` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内脏脂肪等级',
  `bodyage` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身体年龄',
  `fmusclep` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌肉率',
  `ftbw` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体内水重量',
  `fffm` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '去脂体重重量',
  `ficw` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '细胞内液重',
  `fecw` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '细胞外液重',
  `fprotein` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蛋白质重',
  `fcholz` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总胆固醇',
  `ftrig` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '甘油三脂',
  `fldl` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '低密度脂蛋白胆固醇',
  `fhdl` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高密度脂蛋白胆固醇',
  `leye` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左眼视力',
  `reye` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '右眼视力',
  `eye_result` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '视力结果',
  `lastigmatism` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左眼是否散光',
  `rastigmatism` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '右眼是否散光',
  `colourblind` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '是否色盲',
  `anomalous` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '是否色弱',
  `fvc` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fev1` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `pef` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fev1_fvc` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fef25` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fef50` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fef75` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fef2575` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功',
  `fvcp` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `fev1p` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `pefp` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `fef25p` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `fef50p` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `fef75p` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `fef2575p` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比值',
  `counts` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吹气次数',
  `fvc_result` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺功能结论',
  `alcohol` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '酒精含量',
  `alcohol_result` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '酒精测量结果',
  `tscore` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'T值',
  `zscore` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'Z值',
  `sos` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `young_adult` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '成人比',
  `bqi` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `age_matched` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '同龄比',
  `rrf` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `boneage` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨龄(岁)',
  `bonemonth` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨龄(月)',
  `eoa` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `gmd_result` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨密度结果',
  `fev2` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '2秒钟用力肺活量',
  `fev3` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '3秒钟用力肺活量',
  `mvv1` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '最大通气量/1秒量',
  `bsa1` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `mvv_bsa` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体表面率',
  `v75` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸至75%肺活量时对应流速值',
  `v50` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸至50%肺活量时对应流速值',
  `v25` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸至25%肺活量时对应流速值',
  `v25_h` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'V25与身高之比',
  `mvv` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '最大通气量',
  `bsa` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体表面积',
  `vc` varchar(53) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '实测肺活量',
  `tv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '潮气量',
  `erv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '补呼气量',
  `ic` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '深吸气量',
  `rr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸频率',
  `fev2p` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '2秒率',
  `fev3p` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '3秒率',
  `mmf` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '最大呼气中段流速',
  `mvv1_bsa` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'MVV1与体表面积之比',
  `v50_v25` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼气至50%肺活量时对应流速值',
  `irv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '补吸气量',
  `mv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '静息通气量',
  `pr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脉率',
  `sv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '每搏心搏量',
  `co` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '每分钟心输出量',
  `si` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心搏指数',
  `ci` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心搏指数',
  `vpe` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左心室有效泵力',
  `ewk` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左心能量有效利用率',
  `hoi` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌耗氧指数',
  `lvwi` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左心搏功指数',
  `cmbr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌血液供耗率',
  `cmbv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌血液灌注量',
  `cmbn` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心肌血液需要量',
  `svr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体外反搏增搏量',
  `rsvr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体外反搏增搏率',
  `bv` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '有效血容量',
  `n` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '全血粘度',
  `nr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '还原全血粘度',
  `vo2max` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '最大摄氧量',
  `mets` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '梅脱值',
  `hrb` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心力储备',
  `cob` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心输出量储备',
  `pp` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动脉压差',
  `msp` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '平均收缩压',
  `mdp` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '平均舒张压',
  `map` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '平均动脉压',
  `ac` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血管顺应度',
  `ccp` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠状动脉灌注压',
  `blk` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主动脉排空系数',
  `fek` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血管弹性扩张系数',
  `tpr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总周阻',
  `spr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '标准周阻',
  `ver` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左室喷血阻抗',
  `pawp` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺动脉楔压',
  `par` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺血管阻力',
  `pap` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺动脉压',
  `mhr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '微循环半更新率',
  `mrt` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '微循环半更新时间',
  `mst` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '微循环平均滞留时间',
  `xxg_result` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心血管结果',
  `hb` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血红蛋白',
  `htc` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '百分比',
  `waist` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰围',
  `buttock` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '臀围',
  `whr` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰臀比',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='一体机检测';

CREATE TABLE `t_user` (
  `userId` int NOT NULL DEFAULT '0',
  `userName` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `userPassword` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `userEmail` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT;

CREATE TABLE `yh_assess_concent` (
  `id` int NOT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估名',
  `nameText` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估描述',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片',
  `falg` int DEFAULT NULL COMMENT '是否上架（1.上架 2下架）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='心理系列评估内容表';

CREATE TABLE `yh_assess_conclusion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score_min` int DEFAULT NULL COMMENT '最低分',
  `score_max` int DEFAULT NULL COMMENT '最高分',
  `state` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结果',
  `conclusion` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结论',
  `suggest` varchar(550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '建议',
  `guidance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '指导',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
  `leftwidth` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '宽度',
  `assess` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评估结论表(普通)';

CREATE TABLE `yh_assess_disease` (
  `id` int NOT NULL AUTO_INCREMENT,
  `disease_code` int NOT NULL DEFAULT '0',
  `disease_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `risk_group` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sex` int DEFAULT NULL,
  `synopsis` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `key_point` varchar(600) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `is_falg` int DEFAULT NULL COMMENT '状态 1、上线 2、下架 3、非自主评估',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疾病信息表';

CREATE TABLE `yh_assess_disease_level` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diseasId` int DEFAULT NULL COMMENT '疾病id',
  `risk_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '疾病等级',
  `risk_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '风险等级描述',
  `now_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '当前风险描述',
  `ideal_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '理想风险描述',
  `units` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='疾病等级表';

CREATE TABLE `yh_assess_imagetext` (
  `id` int NOT NULL AUTO_INCREMENT,
  `riskName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `improve` int DEFAULT NULL COMMENT '图文id',
  `image_text` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图文连接,多个以；分割',
  `image_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `target` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '改善目标',
  `method` varchar(700) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '改善方法',
  `sex` int DEFAULT NULL COMMENT '性别',
  `good_text` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='风险因素改善图文指南';

CREATE TABLE `yh_assess_introduce` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  `reference` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '参考文献',
  `introduce` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '介绍',
  `question_count` int DEFAULT NULL COMMENT '相关题目数',
  `required_time` int DEFAULT NULL COMMENT '答题大概需要时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康评估介绍内容';

CREATE TABLE `yh_assess_option` (
  `id` int NOT NULL AUTO_INCREMENT,
  `topic_id` int NOT NULL COMMENT '题目id',
  `option` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '选项',
  `score` int DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评估问卷选项表';

CREATE TABLE `yh_assess_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估用户',
  `score` int DEFAULT NULL COMMENT '评估分数',
  `scoreL` int DEFAULT NULL COMMENT 'L量表',
  `state` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估结果',
  `age` int DEFAULT NULL COMMENT '评估年龄',
  `sex` int DEFAULT NULL COMMENT '评估性别(0.男 1.女)',
  `qtime` datetime DEFAULT NULL COMMENT '评估时间',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估答案',
  `is_falg` int DEFAULT NULL COMMENT '评估状态（1.暂存 2.完成 3.逻辑删除）',
  `assess` int DEFAULT NULL COMMENT '1. 焦虑情绪评估 2.抑郁情绪评估 3.失眠指数评估 4.心理健康综合评估 5.职业倦怠评估 6.压力水平评估 7.A型性格评估 8.强迫症评估 9.躯体症状评估 15.老年抑郁评估',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评估结果表';

CREATE TABLE `yh_assess_risk_optimal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diseaseid` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病ID',
  `sno` int DEFAULT NULL,
  `qname` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `qcode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '字段名',
  `optimal` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '理想值',
  `units` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '单位',
  `value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `minv` double DEFAULT NULL COMMENT '最小值范围',
  `maxv` double DEFAULT NULL COMMENT '最大值范围',
  `qtype` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1男2女',
  `isRadar` int DEFAULT NULL COMMENT '1.雷达图；2.危险因素',
  `range_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '雷达图范围值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='危险因素字典表';

CREATE TABLE `yh_assess_risk_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `report_id` int DEFAULT NULL,
  `qtime` datetime DEFAULT NULL COMMENT '评估生成时间',
  `result_serialNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `diseas_id` int DEFAULT NULL,
  `risk_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '风险等级',
  `now_risk_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '当前风险值',
  `now_risk_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `now_risk_last_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上次风险值',
  `ideal_risk_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '理想风险',
  `last_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上次评估日期',
  `age_forecast` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age_op_forecast` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `improves` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '改善id ;',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评估报告结果';

CREATE TABLE `yh_assess_risk_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `report_id` int DEFAULT NULL COMMENT '请求序列号',
  `user_code` varchar(111) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户编码',
  `result_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `result_json` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估返回的json',
  `Ideal_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估结果',
  `qtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结果插入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='疾病风险因素表';

CREATE TABLE `yh_assess_risk_topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `topic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '题目内容',
  `question_item_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对应的字段',
  `choose_one` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '选项一',
  `value_one` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '答案',
  `choose_two` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value_two` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `choose_three` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value_three` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `choose_four` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value_four` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `choose_five` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value_five` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `choose_six` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `value_six` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `replenish` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '补充说明',
  `parent_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否根据其他题目来判断0-有子项；-1-没有子项；子项是为0的id',
  `gender` int DEFAULT NULL COMMENT '0-男性专有 1-女性专有 2- 共有',
  `disease_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属id(多个分号;区分)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='风险评估问卷表';

CREATE TABLE `yh_assess_topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '题目',
  `assess` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评估项目',
  `sort` int DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='评估问卷表（风险评估除外）';

CREATE TABLE `yh_assess_total_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编码',
  `qtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
  `gender` int DEFAULT NULL COMMENT '性别 1.男 ',
  `age` int DEFAULT NULL COMMENT '年龄',
  `times` int DEFAULT NULL COMMENT '评估次数',
  `df_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高风险疾病',
  `sk_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '患或异常疾病',
  `ld_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '中危疾病',
  `lr_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '低危疾病',
  `classify` int DEFAULT NULL COMMENT '1.一般人群 2.慢病患者 3.慢病高危人群',
  `bmi` double DEFAULT NULL,
  `waistline` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='评估报告_基本信息';

CREATE TABLE `yh_audit_check` (
  `checkid` int NOT NULL AUTO_INCREMENT,
  `audit_id` int DEFAULT NULL COMMENT '对应数据id',
  `audit_type` int DEFAULT '0' COMMENT '审核类型 1.膳食；2.运动',
  `audit_title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '审核标题',
  `audit_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '申请人',
  `audit_status` int DEFAULT '0' COMMENT '审核状态 3.同意；1.待审核；2.拒绝',
  `audit_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请日期',
  PRIMARY KEY (`checkid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=sjis ROW_FORMAT=COMPACT COMMENT='审核管理';

CREATE TABLE `yh_award_medal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `grade` int DEFAULT NULL COMMENT '等级',
  `hab_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '外键id',
  `me_mane` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='奖励勋章';

CREATE TABLE `yh_cct_callin_record` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `CALLIN_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `OPERATOR_NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `SEAT_OPERATOR_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '0 - 未处理\r\n            1 - 已处理',
  `INTENTION_TYPE` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INTENTION_DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='坐席呼叫呼入呼出记录表';

CREATE TABLE `yh_cct_customer` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `PHONE_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MOBILE_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ADDRESS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `GENDER` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0 - male\r\n            1 - female\r\n            2 - don''yh know',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `Index_phone_no` (`PHONE_NO`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='坐席呼叫客户信息表';

CREATE TABLE `yh_chat_docinfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '医师code',
  `deptid` bigint DEFAULT NULL COMMENT '分组',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `nickname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '昵称',
  `sex` int DEFAULT NULL COMMENT '性别（0女 1男）',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `remark` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '备注',
  `profilephoto` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人头像',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser` bigint DEFAULT NULL COMMENT '创建人',
  `updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `updateuser` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='聊天分组医师信息表';

CREATE TABLE `yh_chat_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '分组名称',
  `count` int DEFAULT NULL COMMENT '分组人数',
  `level` int DEFAULT NULL COMMENT '等级',
  `parentid` bigint DEFAULT NULL COMMENT '上级分组ID',
  `remark` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '备注',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `updateuser` bigint DEFAULT NULL COMMENT '修改人',
  `isdel` int DEFAULT NULL COMMENT '是否删除（0否1是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='聊天分组';

CREATE TABLE `yh_chat_message` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `senduser` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '发送人',
  `receiveuser` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '接收人',
  `groupid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '群ID',
  `isread` int DEFAULT NULL COMMENT '是否已读 0 未读  1 已读',
  `type` int DEFAULT NULL COMMENT '类型 0 单聊消息  1 群消息',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '消息内容',
  `createuser` bigint DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='聊天记录';

CREATE TABLE `yh_chinese_anwser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `User_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `chooselist` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '选择的答案',
  `A_score` int DEFAULT NULL COMMENT '平和质分数',
  `B_score` int DEFAULT NULL,
  `C_score` int DEFAULT NULL,
  `D_score` int DEFAULT NULL,
  `E_score` int DEFAULT NULL,
  `F_score` int DEFAULT NULL,
  `G_score` int DEFAULT NULL,
  `H_score` int DEFAULT NULL,
  `I_score` int DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '1.完成2.删除3.暂存',
  `A_istype` int DEFAULT NULL COMMENT '1.是2.倾向是3.不是',
  `B_istype` int DEFAULT NULL,
  `C_istype` int DEFAULT NULL,
  `D_istype` int DEFAULT NULL,
  `E_istype` int DEFAULT NULL,
  `F_istype` int DEFAULT NULL,
  `G_istype` int DEFAULT NULL,
  `H_istype` int DEFAULT NULL,
  `I_istype` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_chinese_physique` (
  `q_id` int NOT NULL AUTO_INCREMENT,
  `health_qstion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '题目',
  `answer_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `answer_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `answer_3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `answer_4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `answer_5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`q_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_commodity_brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '品牌名',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '修改人',
  `brand_falg` int DEFAULT NULL COMMENT '状态 1.正常 2.删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='商品品牌';

CREATE TABLE `yh_data_detection_category` (
  `categoryid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '大类标题',
  `applicable_gender` int NOT NULL DEFAULT '0' COMMENT '适用性别(0全部1男2女)',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `state` int NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`categoryid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《大类》';

CREATE TABLE `yh_data_detection_equipment` (
  `equipmentid` int NOT NULL,
  `eimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备分类头像',
  `ename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备分类名称',
  `tid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'yh_data_detection_type二级分类外键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《设备分类》';

CREATE TABLE `yh_data_detection_equipment_list` (
  `equipmentlistid` int NOT NULL,
  `listimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `typeid` int DEFAULT NULL COMMENT 'equipment外键',
  `listname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备名称',
  `upload_mode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1蓝牙上传2物联网上传',
  PRIMARY KEY (`equipmentlistid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《设备集合》';

CREATE TABLE `yh_data_detection_equipment_user` (
  `listid` int NOT NULL,
  `usercode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `terminalId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《设备绑定》';

CREATE TABLE `yh_data_detection_from` (
  `formid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '刻度尺、小数键盘',
  `jump_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '跳转路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`formid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《表单类型》';

CREATE TABLE `yh_data_detection_project` (
  `projectid` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL COMMENT '大类id',
  `projectname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
  `sign` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '意义',
  `fid` int DEFAULT NULL COMMENT '表单类型外键',
  `applicable_gender` int DEFAULT '0' COMMENT '适用性别0全部1男2女',
  `value_type` int DEFAULT NULL COMMENT '值类型',
  `query_last` int DEFAULT '1' COMMENT '0查询上次1不查询上次',
  `initial_value` double DEFAULT NULL COMMENT '初始值',
  `maximum` double DEFAULT NULL COMMENT '范围最大值',
  `minimum` double DEFAULT NULL COMMENT '范围最小值',
  `state` int NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `intelligent_tracking` int NOT NULL DEFAULT '1' COMMENT '智能跟踪（0是1否）',
  `tid` int NOT NULL COMMENT 'yh_data_detection_type外键',
  `grid` double DEFAULT NULL COMMENT '一格几厘米',
  `prominent` double DEFAULT NULL COMMENT '短分割',
  `division` double DEFAULT NULL COMMENT '分割',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `point` int DEFAULT NULL,
  PRIMARY KEY (`projectid`) USING BTREE,
  KEY `tid` (`tid`) USING BTREE,
  KEY `value_type` (`value_type`) USING BTREE,
  KEY `cid` (`cid`) USING BTREE,
  CONSTRAINT `yh_data_detection_project_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `yh_data_detection_type` (`typeid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `yh_data_detection_project_ibfk_4` FOREIGN KEY (`value_type`) REFERENCES `yh_data_detection_value_type` (`vid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `yh_data_detection_project_ibfk_5` FOREIGN KEY (`cid`) REFERENCES `yh_data_detection_category` (`categoryid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《项目》';

CREATE TABLE `yh_data_detection_project_result` (
  `resultid` int NOT NULL AUTO_INCREMENT,
  `sid` int DEFAULT NULL COMMENT 'yh_data_detection_project_standard外键',
  `content` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '解读内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`resultid`) USING BTREE,
  KEY `sid` (`sid`) USING BTREE,
  CONSTRAINT `yh_data_detection_project_result_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `yh_data_detection_project_standard` (`standardid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《解读结果》';

CREATE TABLE `yh_data_detection_project_standard` (
  `standardid` int NOT NULL AUTO_INCREMENT,
  `rid` int DEFAULT NULL COMMENT 'yh_data_detection_reference外键（参考值、单位）',
  `applicable_gender` int DEFAULT '0' COMMENT '使用性别0全部1男2女',
  `applicable_max_age` int DEFAULT '0' COMMENT '使用年龄  最大年龄',
  `applicable_min_age` int DEFAULT '0' COMMENT '使用年龄  最小年龄',
  `tag_color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '色值',
  `standard` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标准 多个区间去,号分割',
  `anomaly_level` int NOT NULL DEFAULT '0' COMMENT '异常等级(多标准时取异常等级更高的)',
  `state` int DEFAULT NULL COMMENT '0正常1异常',
  `evaluate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评价结果',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`standardid`) USING BTREE,
  KEY `rid` (`rid`) USING BTREE,
  CONSTRAINT `yh_data_detection_project_standard_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `yh_data_detection_reference` (`referenceid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《评价标准》';

CREATE TABLE `yh_data_detection_record` (
  `recordid` int NOT NULL AUTO_INCREMENT,
  `usercode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `unitid` int DEFAULT NULL COMMENT 'yh_data_detection_reference外键',
  `tid` int DEFAULT NULL COMMENT '二级分类id',
  `pid` int DEFAULT NULL COMMENT '项目id',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实际值',
  `standard_id` int DEFAULT NULL COMMENT '解读开关关闭的每个指标有单独的评价id',
  `state` int DEFAULT '0' COMMENT '0正常1异常',
  `image_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '影像描述',
  `image_prompt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '影像提示',
  `picture_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片地址,多图用,号分割',
  `data_sources` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '数据来源',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `deleted` int DEFAULT '0' COMMENT '0显示1删除',
  `batch` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '批次(同一时间新增)',
  PRIMARY KEY (`recordid`) USING BTREE,
  KEY `usercode` (`usercode`) USING BTREE,
  KEY `batch` (`batch`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《用户数据检测记录》';

CREATE TABLE `yh_data_detection_record_result` (
  `batchid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rid` int DEFAULT NULL COMMENT '结果解读',
  `sid` int DEFAULT NULL COMMENT '评价标准',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`batchid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《用户数据检测记录结果》';

CREATE TABLE `yh_data_detection_reference` (
  `referenceid` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL COMMENT '项目id',
  `self_equipment` int NOT NULL DEFAULT '1' COMMENT '自测设备对接 0是；1否；',
  `applicable_gender` int DEFAULT '0' COMMENT '使用性别0全部1男2女',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '单位',
  `reference_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示参考范围',
  `reference_range_judge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于判断的参考范围 (3.1~3.3] “( ”大于  ”[ ”大于等于',
  `decimal_digit` int NOT NULL DEFAULT '0' COMMENT '小数位数	0：无小数',
  `options` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '选项,号分割',
  `state` int NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`referenceid`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE,
  CONSTRAINT `yh_data_detection_reference_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `yh_data_detection_project` (`projectid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《参考值、单位》';

CREATE TABLE `yh_data_detection_type` (
  `typeid` int NOT NULL AUTO_INCREMENT,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `sign` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '意义',
  `note` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注意事项',
  `interpretation_switch` int DEFAULT '1' COMMENT '解读开关0关1开',
  `applicable_gender` int DEFAULT '0' COMMENT '使用性别0全部1男2女',
  `state` int DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `statistical_chart` int DEFAULT '0' COMMENT '统计图表（0显示 1不显示）',
  `fid` int DEFAULT NULL COMMENT '表单类型id',
  `cid` int DEFAULT NULL COMMENT 'yh_data_detection_category外键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `point` int DEFAULT NULL,
  `source` int DEFAULT '0' COMMENT '0手动录入1设备上传',
  PRIMARY KEY (`typeid`) USING BTREE,
  KEY `cid` (`cid`) USING BTREE,
  KEY `yh_data_detection_project_ibfk_3` (`fid`) USING BTREE,
  CONSTRAINT `yh_data_detection_project_ibfk_3` FOREIGN KEY (`fid`) REFERENCES `yh_data_detection_from` (`formid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `yh_data_detection_type_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `yh_data_detection_category` (`categoryid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《二级类》';

CREATE TABLE `yh_data_detection_value_type` (
  `vid` int NOT NULL AUTO_INCREMENT,
  `valueType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`vid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='4.6数据检测升级《值类型字典》';

CREATE TABLE `yh_device_terminal` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `DEVICE_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ORG_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `IME` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '唯一识别码',
  `BIND_MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `MOBILE_NO` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `SOS_NOS` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `NOTE` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ONLINE` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '0 - 离线\\\\\\\\r\\\\\\\\n            1 - 在线 (正常)\\\\\\\\r\\\\\\\\n            2 - 在线（未佩戴）\\\\\\\\r\\\\\\\\n            ',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='设备终端表';

CREATE TABLE `yh_dic_abo` (
  `code` int NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_no` int NOT NULL,
  `frozen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='字典表-血型';

CREATE TABLE `yh_dic_assessment_screen` (
  `sid` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(444) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '筛选标题',
  `explain` varchar(444) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '说明',
  `sex` int DEFAULT NULL COMMENT '性别',
  `ageStart` int DEFAULT NULL COMMENT '从多少岁',
  `ageEnd` int DEFAULT NULL COMMENT '到多少岁',
  `weight1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重选项1',
  `weight2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重选项2',
  `weight3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重选项3',
  `weight4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重选项4',
  `hypertension` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人高血压',
  `chronicBronchitis` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人慢性支气管炎',
  `dyslipidemia` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人血脂异常',
  `osteoporosis` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人骨质疏松',
  `lungCancer` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人肺癌',
  `emphysema` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人肺气肿',
  `coronaryDisease` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人冠心病',
  `hyperuricemia` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人高尿酸血症',
  `phthisis` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人肺结核',
  `fattyLiver` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人脂肪肝',
  `type2Diabetes` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人2型糖尿病',
  `apoplexy` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人脑卒中',
  `breastCancer` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人乳腺癌',
  `prostate` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人前列腺癌',
  `acuteGout` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人痛风急性',
  `goutIsNotAcute` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人痛风非急性',
  `previousFracture` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人既往骨折',
  `prostatitis` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人前列腺炎或肥大',
  `familialHypertension` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族高血压',
  `familialLungCancer` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族肺癌',
  `familialCoronaryDisease` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族冠心病',
  `familialApoplexy` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族脑卒中',
  `familialBreastCancer` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族乳腺癌',
  `familialProstate` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族前列腺癌',
  `familialDiabetes` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族糖尿病',
  `familialhipFracture` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族髋骨骨折',
  `familialgout` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族痛风',
  `smoke1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吸烟情况1',
  `smoke2` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吸烟情况2',
  `smoke3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吸烟情况3',
  `drink1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮酒情况',
  `drink2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮酒情况2不喝酒',
  `sport1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动情况1',
  `sport2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动情况2',
  `sport3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动情况3',
  `food1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食习惯1',
  `food2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食习惯2',
  `food3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食习惯3',
  `food4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食习惯4',
  `psychology1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理情况',
  `psychology2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理2',
  `psychology3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心理3',
  `sleep1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠情况',
  `sleep2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠2',
  `sleep3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠3',
  `coronaryDiseaseRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠心病高风险',
  `coronaryDiseaseRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠心病中风险',
  `coronaryDiseaseRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠心病低风险',
  `coronaryDiseaseRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hypertensionRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血压风险',
  `hypertensionRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血压中风险',
  `hypertensionRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血压低风险',
  `hypertensionRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `apoplexyRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脑卒中风险',
  `apoplexyRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脑卒中中风险',
  `apoplexyRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脑卒中低风险',
  `apoplexyRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `diabetesRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖尿病风险',
  `diabetesRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖尿病中风险',
  `diabetesRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖尿病低风险',
  `diabetesRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `lungCancerRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺癌风险',
  `lungCancerRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺癌中风险',
  `lungCancerRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺癌低风险',
  `lungCancerRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `prostateRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '前列腺癌风险',
  `prostateRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '前列腺癌中风险',
  `prostateRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '前列腺癌低风险',
  `prostateRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `breastCancerRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乳腺癌风险',
  `breastCancerRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乳腺癌中风险',
  `breastCancerRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乳腺癌低风险',
  `breastCancerRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `dyslipidemiaRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血脂异常风险',
  `dyslipidemiaRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血脂异常中风险',
  `dyslipidemiaRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血脂异常低风险',
  `dyslipidemiaRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `osteoporosisRisk1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨质疏松风险',
  `osteoporosisRisk2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨质疏松中风险',
  `osteoporosisRisk3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨质疏松低风险',
  `osteoporosisRisk4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `creationTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `screeningTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '筛选时间',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `riskofgout1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '痛风低风险',
  `riskofgout2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '痛风中风险',
  `riskofgout3` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '痛风高风险',
  `riskofgout4` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '痛风已患或异常',
  `foodVegetables1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蔬菜摄入不足',
  `foodVegetables2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蔬菜摄入充足',
  `foodFruits1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '水果摄入不足',
  `foodFruits2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '水果摄入充足',
  `foodairy1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '牛奶摄入不足',
  `foodairy2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '牛奶摄入充足',
  `foodPurine1` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高嘌呤饮食 是',
  `foodPurine2` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高嘌呤饮食 否',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `yh_dic_certtype` (
  `code` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_no` int NOT NULL,
  `frozen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典表-证件';

CREATE TABLE `yh_dic_education` (
  `code` int NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_no` int NOT NULL,
  `frozen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典表-学历';

CREATE TABLE `yh_dic_introduction` (
  `code` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `explain` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '解释信息',
  `information` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '检测详情&申明信息',
  `type` int DEFAULT NULL COMMENT '1数据检测&2小工具',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='相关功能说明';

CREATE TABLE `yh_dic_marital` (
  `code` int NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_no` int NOT NULL,
  `frozen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典表-婚姻状况';

CREATE TABLE `yh_dic_nationality` (
  `Code` int NOT NULL,
  `Name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Order_No` int NOT NULL,
  PRIMARY KEY (`Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典表-民族';

CREATE TABLE `yh_dic_occupation` (
  `code` int NOT NULL,
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `order_no` int NOT NULL,
  `frozen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典表-职业';

CREATE TABLE `yh_dic_physicalindicators` (
  `physicalindicators_Id` int NOT NULL AUTO_INCREMENT,
  `physical_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检名称',
  `physical_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检code',
  `physical_explain` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标说明',
  `physical_unit1` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标单位1',
  `physical_unit2` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标单位2',
  `physical_range1` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标标准参考范围1',
  `physical_range2` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标标准参考范围2',
  `physical_wrange` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标女参考范围',
  `physical_mrange` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标男参考范围',
  `physical_remark` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`physicalindicators_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='字典体检指标参数表 新';

CREATE TABLE `yh_dic_to_examine` (
  `state` int DEFAULT NULL COMMENT '审核状态0未通过1已通过'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_disease` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Disease` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疾病';

CREATE TABLE `yh_disease_assess_imagetext` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_text` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图文连接,多个以；分割',
  `image_url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='风险因素改善图文指南';

CREATE TABLE `yh_disease_assess_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户编码',
  `result_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '请求序列号',
  `qtime` datetime(6) DEFAULT NULL COMMENT '评估生成时间',
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  `diseasId` int DEFAULT NULL COMMENT '1:冠心病2:脑卒中3:高血压4:糖尿病5:痛风6:血脂异常7:骨质疏松性骨折8:肺癌9:前列腺癌10:乳腺癌11:肥胖症',
  `risk_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '风险等级',
  `risk_desc` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '风险描述',
  `now_risk_desc` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当前风险描述',
  `now_risk_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当前风险值',
  `now_risk_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `now_risk_last_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '上次风险值',
  `ideal_risk_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '理想风险',
  `ideal_risk_desc` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '理想风险描述',
  `last_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '上次评估日期',
  `age_forecast_r20` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '20岁-29的预测值',
  `age_forecast_r30` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '30岁-39的预测值',
  `age_forecast_r40` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '40岁-49的预测值',
  `age_forecast_r50` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '50岁-59的预测值',
  `age_forecast_r60` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '60岁-69的预测值',
  `age_forecast_r70` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '70岁-79的预测值',
  `age_forecast_r80` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '大于80岁的预测值',
  `age_op_forecast_r20_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '20岁-29的理想值',
  `age_op_forecast_r30_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '30岁-39的理想值',
  `age_op_forecast_r40_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '40岁-49的理想值',
  `age_op_forecast_r50_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '50岁-59的理想值',
  `age_op_forecast_r60_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '60岁-69的理想值',
  `age_op_forecast_r70_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '70岁-79的理想值',
  `age_op_forecast_r80_op` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '大于80岁的理想值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='评估报告结果';

CREATE TABLE `yh_disease_assess_resolve` (
  `id` int NOT NULL AUTO_INCREMENT,
  `result_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '序列号',
  `diseaseId` tinyint NOT NULL,
  `resolve_type` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '改善方式',
  `resolve_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '改善标题名',
  `resolve_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='风险因素改善指南';

CREATE TABLE `yh_disease_assess_total_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `result_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '返回结果序列号',
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编码',
  `qtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户名',
  `user_occupation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '职业',
  `user_jzd` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '居住地 1南方; 2北方',
  `user_jzhj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '居住环境 1城市; 2农村',
  `classify` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1.一般人群 2.慢病患者 3.慢病高危人群',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `request_serialNum` (`request_serialNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='评估报告_基本信息';

CREATE TABLE `yh_disease_assess_total_report2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编码',
  `qtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户名',
  `times` int DEFAULT NULL COMMENT '评估次数',
  `df_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '高风险疾病',
  `sk_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患或异常疾病',
  `ld_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '中危疾病',
  `lr_names` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '低危疾病',
  `classify` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '1.一般人群 2.慢病患者 3.慢病高危人群',
  `chd_his` int DEFAULT NULL COMMENT '冠心病  --1,是2，否',
  `stroke_his` int DEFAULT NULL COMMENT '脑卒中  --1,是2，否',
  `hbp_his` int DEFAULT NULL COMMENT '高血压  --1,是2，否',
  `diab2_his` int DEFAULT NULL COMMENT '2型糖尿病  --1,是2，否',
  `bf_his` int DEFAULT NULL COMMENT '血脂异常  --1,是2，否',
  `hua_his` int DEFAULT NULL COMMENT '高尿酸血症  --1,是2，否',
  `gae_his` int DEFAULT NULL COMMENT '痛风急性期  --1,是2，否',
  `gna_his` int DEFAULT NULL COMMENT '痛风非急性期  --1,是2，否',
  `op_his` int DEFAULT NULL COMMENT '骨质疏松症  --1,是2，否',
  `fl_his` int DEFAULT NULL COMMENT '脂肪肝  --1,是2，否',
  `bronch_his` int DEFAULT NULL COMMENT '慢性支气管炎  --1,是2，否',
  `emphy_his` int DEFAULT NULL COMMENT '肺气肿  --1,是2，否',
  `lc_his` int DEFAULT NULL COMMENT '肺癌  --1,是2，否',
  `decline_his` int DEFAULT NULL COMMENT '肺结核史  --1,是2，否',
  `fracture_his` int DEFAULT NULL COMMENT '既往骨折  --1,是2，否',
  `prostate_disease` int DEFAULT NULL COMMENT '前列腺炎或肥大  --1,是2，否',
  `pc_his` int DEFAULT NULL COMMENT '前列腺癌  --1,是2，否',
  `bc_his` int DEFAULT NULL COMMENT '乳腺癌  --1,是2，否',
  `family_diab` int DEFAULT NULL COMMENT '家族患糖尿病   --1,是2，否',
  `family_hbp` int DEFAULT NULL COMMENT '家族患高血压   --1,是2，否',
  `family_chd` int DEFAULT NULL COMMENT '家族患冠心病   --1,是2，否',
  `family_stroke` int DEFAULT NULL COMMENT '家族患脑卒中   --1,是2，否',
  `family_hf` int DEFAULT NULL COMMENT '家族髋部骨折   --1,是2，否',
  `family_lung` int DEFAULT NULL COMMENT '家族肺癌   --1,是2，否',
  `family_pro` int DEFAULT NULL COMMENT '家族前列腺癌    --1,是2，否',
  `family_breast` int DEFAULT NULL COMMENT '家族乳腺癌   --1,是2，否',
  `family_gout` int DEFAULT NULL COMMENT '家族痛风   --1,是2，否',
  `smoke_status` int DEFAULT NULL COMMENT '吸烟  --1,吸烟 ;2，不吸烟 ;3，已戒烟',
  `food_drink` int DEFAULT NULL COMMENT '喝酒  --1喝酒2不喝',
  `bmi` float DEFAULT NULL,
  `food_vegetables` int DEFAULT NULL COMMENT '蔬菜水果摄入 1.不足;2.充足',
  `food_purine` int DEFAULT NULL COMMENT '高嘌呤饮食  --1,是2，否',
  `food_dairy` int DEFAULT NULL COMMENT '牛奶摄入  1.不足;2.充足',
  `food_fruits` int DEFAULT NULL COMMENT '水果摄入 1.不足;2.充足',
  `B4` int DEFAULT NULL COMMENT '高脂饮食 --1,是2，否',
  `sport_in` int DEFAULT NULL COMMENT '运动  --1不足；2中等；3.充分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `request_serialNum` (`request_serialNum`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='评估报告_基本信息';

CREATE TABLE `yh_disease_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `disease_code` int NOT NULL DEFAULT '0',
  `disease_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `risk_group` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `isAssessment` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疾病信息表';

CREATE TABLE `yh_disease_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `drug_allergy` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '过敏史 1：青霉素；2：磺胺；3：链霉素；4：其他；',
  `exposure_history` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '暴露史 1：化学品；2：毒物；3：射线；',
  `previous_history` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '既往史 疾病 1：高血压；2：糖尿病；3：冠心病；4：慢性阻塞性肺疾病；5：恶性肿瘤；6：脑卒中；7：严重精神障碍；8：结核病；9：肝炎；10：其他法定传染病；11：职业病；12：其他；',
  `previous_history_time` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '确诊时间',
  `family_history_f` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '家族史-父亲 1：高血压；2：糖尿病；3：冠心病；4：慢性阻塞性肺疾病；5：恶性肿瘤；6：脑卒中；7：严重精神障碍；8：结核病；9：肝炎；10：先天畸形；11：其他；',
  `family_history_m` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '母亲 1：高血压；2：糖尿病；3：冠心病；4：慢性阻塞性肺疾病；5：恶性肿瘤；6：脑卒中；7：严重精神障碍；8：结核病；9：肝炎；10：先天畸形；11：其他；',
  `family_history_bs` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '兄弟姐妹 1：高血压；2：糖尿病；3：冠心病；4：慢性阻塞性肺疾病；5：恶性肿瘤；6：脑卒中；7：严重精神障碍；8：结核病；9：肝炎；10：先天畸形；11：其他；',
  `family_history_c` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '子女 1：高血压；2：糖尿病；3：冠心病；4：慢性阻塞性肺疾病；5：恶性肿瘤；6：脑卒中；7：严重精神障碍；8：结核病；9：肝炎；10：先天畸形；11：其他；',
  `disability` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病情况 1：视力残疾；2：听力残疾；3：言语残疾；4：肢体残疾；5：智力残疾；6：精神残疾；7：其他残疾',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建和修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='病史信息主表';

CREATE TABLE `yh_disease_information_sublist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `infor_id` int DEFAULT NULL COMMENT '主表id',
  `type_state` int DEFAULT NULL COMMENT '状态 1.手术 2.外伤 3.输血 4.遗传病史',
  `record_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  `record_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='病史信息从表';

CREATE TABLE `yh_disease_risk_factor_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factor_group` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '危险因素组合',
  `factor_segment` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分成的段数',
  `factor_segment_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分段区间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疾病危险因素组合';

CREATE TABLE `yh_disease_risk_optimal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_serial` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '请求序列号',
  `depart` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sno` int DEFAULT NULL,
  `qname` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `qcode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `optimal` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '理想值',
  `weight` float DEFAULT NULL,
  `remark` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '范围说明',
  `value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `minv` float DEFAULT NULL COMMENT '最小值范围',
  `maxv` float DEFAULT NULL COMMENT '最大值范围',
  `qtype` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1男2女',
  `diseaseid` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病ID',
  `xpath` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '字段名',
  `isRadar` int DEFAULT NULL COMMENT '1.雷达图；3.危险因素',
  `Radar_range_point` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '不可逆转1',
  `Radar_range_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '雷达图范围值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='危险因素字典表';

CREATE TABLE `yh_disease_risk_result2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diseaseId` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病ID',
  `user_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `request_serialize` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  `age` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '年龄对应的风险内容',
  `hdlc` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高密度脂蛋白胆固醇对应的风险内容',
  `ldlc` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '低密度脂蛋白胆固醇对应的风险内容',
  `food` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蔬菜水果对应的风险内容',
  `sport` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体力活动水平对应的风险内容',
  `b4` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高脂饮食对应的风险内容',
  `smoke` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吸烟对应的风险内容',
  `drink` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮酒情况对应的风险内容',
  `hw_check` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '红外检测报告对应的风险内容',
  `BMI` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重指数（BMI）对应的风险内容',
  `waist` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰围对应的风险内容',
  `diab_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖尿病史对应的风险内容',
  `hbp_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血压病史对应的风险内容',
  `FBG` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '空腹血糖对应的风险内容',
  `sbp` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '收缩压对应的风险内容',
  `cholesterol` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总胆固醇对应的风险内容',
  `TG` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '甘油三酯对应的风险内容',
  `BF` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血脂的风险内容',
  `dbp` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '舒张压对应的风险内容',
  `HBPS` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高血压家族史对应的风险内容',
  `HFS` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '父母髋部骨折史对应的风险内容',
  `hf_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '个人既往骨折史的风险内容',
  `C1` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '长期服用肾上腺皮质激素的风险内容',
  `milk` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '牛奶摄入对应的风险内容',
  `tfa` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '反式脂肪酸对应的风险内容',
  `chds` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠心病家族史对应的风险内容',
  `dms` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖尿病家族史对应的风险内容',
  `smoke_sencod` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '被动吸烟对应的风险内容',
  `lungs` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺癌家族史对应的风险内容',
  `emphy_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺气肿病史对应的风险内容',
  `asbestos_contact_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '粉尘接触史对应的风险内容',
  `decorate_house` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '近两年是否新房装修对应的风险内容',
  `psy_nervous` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '精神压力对应的风险内容',
  `bronch_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '慢性支气管炎病史对应的风险内容',
  `lampblack_contact` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '油烟接触史对应的风险内容',
  `gouts` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '痛风家族史对应的风险内容',
  `tea` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '饮茶情况对应的风险内容',
  `food_purine` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高嘌呤饮食对应的风险内容',
  `chd_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '冠心病史对应的风险内容',
  `bua` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸对应的风险内容',
  `med_use_pill` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '避孕药服用对应的风险内容',
  `breast_plastic_surgery` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '使用丰乳产品或做过乳房整形手术对应的风险内容',
  `BCS` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乳腺癌家族史对应的风险内容',
  `begind_breast_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '良性乳腺疾病史对应的风险内容',
  `female_menstruation` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '初潮年龄对应的风险内容',
  `female_tire` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '初产年龄对应的风险内容',
  `female_breast_time` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '哺乳喂养时间对应的风险内容',
  `female_menopause_age` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '绝经年龄对应的风险内容',
  `has_med_ert` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '雌激素替代疗法对应的风险内容',
  `CSS` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脑卒中家族史对应的风险内容',
  `card_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心血管病史对应的风险内容',
  `lvd_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '左心室肥大对应的风险内容',
  `af_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '房颤病史对应的风险内容',
  `PCS` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '前列腺癌家族史对应的风险内容',
  `prostate_disease` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '前列腺炎或肥大病史对应的风险内容',
  `urinary_tract_infection` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿路感染对应的风险内容',
  `urine_symptom` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿频、尿痛、尿不尽、排尿困难症状对应的风险内容',
  `t_score` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'T值对应的风险内容',
  `ra_his` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '类风湿关节炎对应的风险内容',
  `living_envir` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '居住环境对应的风险内容',
  `food_drink_beer` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '啤酒摄入对应的风险内容',
  `female_menstruation_regular` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '月经规律对应的风险内容',
  `chemical_occupation` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '工作环境对应的风险内容',
  `food_vegetables` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蔬菜水果对应的风险内容',
  `remark` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `qtime` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='疾病风险因素表';

CREATE TABLE `yh_doc_group` (
  `U_Docgroup_Id` int NOT NULL AUTO_INCREMENT COMMENT '系统自带12个分组，少了会报错',
  `Docid` int DEFAULT '0',
  `Gname` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `User_Count` int DEFAULT '0',
  `Sort` int DEFAULT NULL COMMENT '1为系统自带，不能删除。0为另外新增可以删除',
  `establishTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `disease_code` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '对应疾病',
  PRIMARY KEY (`U_Docgroup_Id`) USING BTREE,
  KEY `Docid` (`Docid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医生分组';

CREATE TABLE `yh_dv_device_type` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `DEVICE_NAME` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `FACTORY_NAME` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `VERSION` varchar(24) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `DEVICE_TYPE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '00 -- 手表',
  `PICTRUE` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='设备类型表';

CREATE TABLE `yh_event_class` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '三高一重班级id',
  `class_name` varchar(55) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '班级名称',
  `enroll_notice` varchar(6000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '报名须知',
  `event_rule` varchar(6000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '报名注意事项',
  `total` int DEFAULT NULL COMMENT '报名人数',
  `enroll_id` int DEFAULT NULL COMMENT '对应well_event_enroll活动id',
  `administrators` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '管理员微信二维码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_event_enroll` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '活动ID,',
  `event_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '活动名',
  `event_content` varchar(10000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '活动内容',
  `event_contentmd` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT 'Web渲染Md',
  `provider` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主办方',
  `start_date` datetime DEFAULT NULL COMMENT '活动报名开始日期',
  `register_end_date` datetime DEFAULT NULL COMMENT '活动报名结束日期',
  `total` int DEFAULT '0' COMMENT '总报名人数',
  `public_date` datetime DEFAULT NULL COMMENT '活动发布日期',
  `end_date` datetime DEFAULT NULL COMMENT '活动结束日期',
  `event_rule` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '活动注意事项及规则',
  `event_addr` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '活动举办地点',
  `event_img` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '活动图片',
  `event_type` int DEFAULT NULL COMMENT '状态（1，已发布 2，草稿）',
  `org_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '属于哪个机构发布的',
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='活动事件表';

CREATE TABLE `yh_event_register` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '用户编码',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '姓名',
  `age` int DEFAULT '0' COMMENT '年龄',
  `phone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '联系电话',
  `gender` tinyint DEFAULT '0' COMMENT '性别',
  `register_date` date DEFAULT NULL COMMENT '报名日期',
  `event_id` int DEFAULT NULL COMMENT '活动编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='活动报名人员表';

CREATE TABLE `yh_follow_up_service_inisetting` (
  `settingid` int NOT NULL,
  `type` int NOT NULL COMMENT '任务类型id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '子类型',
  `pid` int NOT NULL COMMENT '优先级id',
  `switch` int NOT NULL DEFAULT '0' COMMENT '0开1关',
  `mid` int NOT NULL COMMENT '随访方式id',
  `cid` int NOT NULL COMMENT '模板id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `days` int DEFAULT NULL COMMENT '定时天数',
  `sid` int NOT NULL COMMENT '来源id',
  PRIMARY KEY (`settingid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='《随访跟踪》初始任务设置';

CREATE TABLE `yh_follow_up_service_mode` (
  `modeid` int NOT NULL,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人工智能分类',
  `mname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '随访方式',
  PRIMARY KEY (`modeid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='《随访跟踪》随访方式';

CREATE TABLE `yh_follow_up_service_priority` (
  `priorityid` int NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_limit` double NOT NULL,
  PRIMARY KEY (`priorityid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='《随访跟踪》优先级';

CREATE TABLE `yh_follow_up_service_source` (
  `sourceid` int NOT NULL,
  `sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sourceid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='《随访跟踪》服务任务来源';

CREATE TABLE `yh_food_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `food_category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '食物分类名',
  `food_category_img` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '食物分类img',
  `displayOrder` int DEFAULT NULL COMMENT '显示顺序',
  `everydayMin` int DEFAULT NULL COMMENT '每日平均摄入最小值（克）',
  `everydayMax` int DEFAULT NULL COMMENT '每日平均摄入最大值（克）',
  `analysis` int DEFAULT NULL COMMENT '膳食宝塔分析(0:是；1：否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='食物分类';

CREATE TABLE `yh_food_classifenergy` (
  `F_ClassifEnergyId` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F_level` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '能量等级',
  `F_breakfast` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '早餐食物分类_能量',
  `F_lunch` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '午餐食物分类_能量',
  `F_dinner` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '晚餐食物分类_能量',
  `F_extrameal` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '加餐食物分类_能量',
  PRIMARY KEY (`F_ClassifEnergyId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='食物分类能量';

CREATE TABLE `yh_food_cookbook` (
  `F_C_ID` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F_C_NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  `Appraise` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评价',
  `F_C_IMG` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `Energy_Kcal` float DEFAULT NULL COMMENT '热量（大卡）',
  `Cho` float DEFAULT '0' COMMENT '碳水化合物（克）',
  `Fat` float DEFAULT '0' COMMENT '脂肪（克）',
  `Protein` float DEFAULT '0' COMMENT '蛋白质（克）',
  `Cellulose` float DEFAULT '0' COMMENT '纤维素（克）',
  `common` int DEFAULT '0' COMMENT '常见（被记录次数）',
  `oneself` int DEFAULT '1' COMMENT '(1，本公司添加的 2，加盟商添加食物 3,用户增加)',
  `is_del` int DEFAULT '2' COMMENT '逻辑删除(2显示，1删除)',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户上传的用户id',
  PRIMARY KEY (`F_C_ID`) USING BTREE,
  KEY `F_C_NAME` (`F_C_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='食材食谱表，营养成份都以100克为基准。';

CREATE TABLE `yh_food_cookbook2` (
  `F_C_ID` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F_C_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  `Appraise` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '评价',
  `F_C_IMG` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `Measuring_unit` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '度量单位',
  `F_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2' COMMENT '食物分类',
  `Cook_Person` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '使用人群',
  `Used_Food` int DEFAULT '0' COMMENT '常用食物',
  `Edible_Part` int DEFAULT '0' COMMENT '可食部分',
  `Moisture` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '水分',
  `Energy_Kcal` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '热量（大卡）',
  `Cho` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '碳水化合物（克）',
  `Fat` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '脂肪（克）',
  `Protein` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '蛋白质（克）',
  `Insoluble_Dietary_Fiber` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '不溶性膳食纤维',
  `Cholesterol` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '胆固醇(毫克)',
  `VitaminA` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '维生素A(微克)',
  `Cellulose` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '纤维素(克)',
  `VitaminC` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '维生素C(微克)',
  `VitaminE` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '维生素E(微克)',
  `Thiamine` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '硫胺素(毫克)',
  `Riboflavin` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '核黄素(毫克)',
  `Niacin` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '烟酸(毫克)',
  `Magnesium` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '镁(毫克)',
  `Calcium` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '钙(毫克)',
  `Rron` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '铁(毫克)',
  `Zinc` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '锌(毫克)',
  `Copper` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '铜(毫克)',
  `Manganese` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '锰(毫克)',
  `Potassium` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '钾(毫克)',
  `Phosphorus` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '磷(毫克)',
  `Sodium` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '钠(毫克)',
  `Selenium` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '硒(毫克)',
  `Saturated_Fat` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '饱和脂肪酸（毫克）',
  `Monounsaturated_Fatty_Acids` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '单不饱和脂肪酸（毫克）',
  `Polyunsaturated_Fatty_Acids` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '多不饱和脂肪酸（毫克）',
  `common` int DEFAULT '0' COMMENT '常见（被记录次数）',
  `oneself` int DEFAULT '1' COMMENT '(1，本公司添加的 2，加盟商添加食物 3,用户增加)',
  `is_del` int DEFAULT '2' COMMENT '逻辑删除(2显示，1删除)',
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户上传的用户id',
  `Not_Recommend_Food` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `F_B_NAME` varchar(222) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`F_C_ID`) USING BTREE,
  KEY `F_C_NAME` (`F_C_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='食材食谱表，营养成份都以100克为基准。';

CREATE TABLE `yh_food_cookbook_collection` (
  `cid` int NOT NULL AUTO_INCREMENT COMMENT '食材收藏表id',
  `foodid` int DEFAULT NULL COMMENT '食材id',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户id',
  `insertTime` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_food_cookbook_disease` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diseaseid` int DEFAULT '0' COMMENT '疾病id',
  `cookbook_ids` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '食谱套餐推荐。餐次_食谱ID构成',
  `food_elements_ids` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '食材套餐推荐 餐次_食材id构成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='根据不同的慢性病定制的菜谱';

CREATE TABLE `yh_food_cooked` (
  `F_CookedId` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `F_CookedElements` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '菜肴名称',
  `foodMethod` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '料理方法',
  PRIMARY KEY (`F_CookedId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='菜肴。';

CREATE TABLE `yh_food_energy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shape` int DEFAULT NULL COMMENT '体型1:＜18.5，消瘦;2:18.5~23.9，正常;3:24~27.9，超重;4:≥28，肥胖;',
  `activity` int DEFAULT NULL COMMENT '体力活动量（1:极轻;2:轻;3中:;4:重;）',
  `supply` int DEFAULT NULL COMMENT '每日能量供给量(kcal/kg标准体重)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_food_energy_level` (
  `id` int NOT NULL AUTO_INCREMENT,
  `energy_level` int NOT NULL,
  `total_exchange_sum` float NOT NULL COMMENT '总交换份',
  `cron` float NOT NULL COMMENT '谷薯组',
  `vegetable` float NOT NULL COMMENT '果蔬组',
  `eggs` float NOT NULL COMMENT '肉蛋组',
  `energy` float NOT NULL COMMENT '供热组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='能量等级';

CREATE TABLE `yh_food_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分组名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='食物分组';

CREATE TABLE `yh_food_hot_search` (
  `hid` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `foodname` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '食物主键',
  `heat` int DEFAULT '0' COMMENT '热度',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '搜索人id',
  `C_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '搜索时间',
  `spare` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备用',
  PRIMARY KEY (`hid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_food_srecipe` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `F_type` int NOT NULL DEFAULT '1' COMMENT '1:风险评估推荐；2：健康计划配套',
  `E_Level` int NOT NULL DEFAULT '0' COMMENT '每天的热量',
  `F_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '01 —对应推荐详情id',
  `Is_Del` int NOT NULL DEFAULT '2',
  `diseaseid` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '2' COMMENT '推荐的疾病',
  `oneself` int NOT NULL DEFAULT '1' COMMENT '1,本公司添加  2，加盟商添加',
  `status` int NOT NULL DEFAULT '1' COMMENT '0:已发布;1:待审核;2：草稿',
  `fs_describe` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐描述',
  `User_code` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '添加者',
  `creationTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '添加时间',
  `updateUsercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户推荐膳食处方';

CREATE TABLE `yh_food_srecipe2` (
  `Fs_Id` int NOT NULL AUTO_INCREMENT,
  `D_Type` int NOT NULL DEFAULT '1' COMMENT '口味：1.清淡 2.甜  3.咸 4.辣',
  `E_Level` int NOT NULL DEFAULT '0' COMMENT '每天的热量',
  `genter` int NOT NULL DEFAULT '0' COMMENT '1.男 2.女',
  `Is_Use` int NOT NULL DEFAULT '0' COMMENT '体力活动：1.轻 ；2.重',
  `F_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '01 —对应推荐详情id',
  `Package_number` int DEFAULT NULL COMMENT '男女平均身高体重套餐：1.165:155；2.175:165；3.185:175',
  `Is_Del` int NOT NULL DEFAULT '2',
  `diseaseid` int NOT NULL DEFAULT '2' COMMENT '推荐的疾病',
  `oneself` int NOT NULL DEFAULT '1' COMMENT '1,本公司添加  2，加盟商添加',
  `fs_describe` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐描述',
  PRIMARY KEY (`Fs_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户推荐膳食处方';

CREATE TABLE `yh_food_srecipe_record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `record_data` datetime DEFAULT CURRENT_TIMESTAMP,
  `record_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐名',
  `record_kay` int DEFAULT NULL COMMENT '千卡',
  `record_type` int DEFAULT '1' COMMENT '(1,执行中2，过期 3,挂起)',
  `record_mode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '推荐方式',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `meal_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐id',
  `illness_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病id',
  `standby` datetime DEFAULT NULL COMMENT '备用',
  `age` int DEFAULT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `strength` int DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `request_serialNum` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='膳食推荐记录';

CREATE TABLE `yh_food_type` (
  `Type_Code` int NOT NULL AUTO_INCREMENT COMMENT '食物大类id',
  `Type_Name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '大类名称',
  `Is_Del` int NOT NULL DEFAULT '2',
  PRIMARY KEY (`Type_Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='食物大类';

CREATE TABLE `yh_food_user_diary` (
  `U_Fd_Id` int NOT NULL AUTO_INCREMENT,
  `C_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Energy_Level` int NOT NULL DEFAULT '0' COMMENT '能量级别',
  `Data_Sources` int NOT NULL DEFAULT '3',
  `R_Id` int NOT NULL DEFAULT '0',
  `Fat` float NOT NULL DEFAULT '0' COMMENT '脂肪',
  `Cho` float NOT NULL DEFAULT '0' COMMENT '碳水化合物',
  `Cholesterol` float NOT NULL DEFAULT '0',
  `Energy_Kcal` float NOT NULL DEFAULT '0',
  `F_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `Ac_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `Is_Del` int NOT NULL DEFAULT '2',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Protein` float NOT NULL DEFAULT '0' COMMENT '蛋白质',
  `Alvol` float NOT NULL DEFAULT '0',
  `Cellulose` float NOT NULL DEFAULT '0' COMMENT '纤维素',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`U_Fd_Id`) USING BTREE,
  KEY `Ref9911` (`user_code`) USING BTREE,
  KEY `C_Time` (`C_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户膳食日志';

CREATE TABLE `yh_food_user_screcipe` (
  `U_Food_Erecipe_Id` int NOT NULL AUTO_INCREMENT,
  `C_Time` datetime NOT NULL COMMENT '处方生成的时间',
  `F_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '1_152115_25; 1代表早餐，2加餐，3中餐，4晚餐 152115代表食材编号要查询well_food_element，25代表该食材的重量，多个食材用分号隔开',
  `Start_Time` datetime NOT NULL,
  `State_Flag` int NOT NULL DEFAULT '2',
  `Srecipe_Id` int NOT NULL,
  `Is_Del` int NOT NULL DEFAULT '2',
  `E_Level` int DEFAULT NULL COMMENT '能量等级',
  `diseaseid` int DEFAULT NULL COMMENT '疾病id',
  `status` int DEFAULT NULL COMMENT '状态',
  `oneself` int DEFAULT NULL,
  PRIMARY KEY (`U_Food_Erecipe_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户膳食处方详细表';

CREATE TABLE `yh_food_user_screcipe2` (
  `U_Food_Erecipe_Id` int NOT NULL AUTO_INCREMENT,
  `C_Time` datetime NOT NULL COMMENT '处方生成的时间',
  `F_Elements` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '1_152115_25; 1代表早餐，2加餐，3中餐，4晚餐 152115代表食材编号要查询well_food_element，25代表该食材的重量，多个食材用分号隔开',
  `Start_Time` datetime NOT NULL,
  `State_Flag` int NOT NULL DEFAULT '2',
  `Srecipe_Id` int NOT NULL,
  `Is_Del` int NOT NULL DEFAULT '2',
  `E_Level` int DEFAULT NULL COMMENT '能量等级',
  `diseaseid` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '疾病id',
  `status` int DEFAULT NULL,
  `oneself` int DEFAULT NULL COMMENT '1为系统提供',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `updateUsercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`U_Food_Erecipe_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='用户膳食处方详细表';

CREATE TABLE `yh_giveup_smoking_directory` (
  `secret_Id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '分类名',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '父分类ID',
  `sort_id` int DEFAULT NULL COMMENT '顺序id',
  `tip_one` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '提示语',
  `watch` int DEFAULT NULL COMMENT '查看人数',
  PRIMARY KEY (`secret_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='戒烟秘笈目录表';

CREATE TABLE `yh_giveup_smoking_info` (
  `infoId` int NOT NULL AUTO_INCREMENT,
  `tips` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '关键词，多个用分号隔开',
  `category_id` int DEFAULT '0' COMMENT '类别ID',
  `typeid` int DEFAULT '0' COMMENT '类型ID,1,视频5，图文',
  `thumbnail_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '缩略图URL',
  `listbox_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '列表图',
  `detail_content_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '详情地址',
  `video_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '视频播放地址',
  `audio_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '语音播放地址',
  `update_date` datetime DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '标题',
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '作者',
  `instruction` varchar(18900) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '概要',
  `read_count` int unsigned DEFAULT '1' COMMENT '点击数',
  `addInfoTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `state` int DEFAULT NULL COMMENT '(1,发布2，草稿)',
  `speaker` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主讲人',
  PRIMARY KEY (`infoId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='戒烟秘笈内容表';

CREATE TABLE `yh_group_screen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_one` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '一级条件名称',
  `name_two` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '二级条件名称',
  `name_three` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '三级条件名称',
  `screen_sql` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '筛选时的sql',
  `is_falg` int DEFAULT NULL COMMENT '开启状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户分组筛选条件表';

CREATE TABLE `yh_habit_praise` (
  `pr_id` int NOT NULL AUTO_INCREMENT,
  `commentId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '评论id',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'user_code',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '增加时间',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`pr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='习惯点赞';

CREATE TABLE `yh_habit_remind` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ha_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '外键id',
  `ha_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='健康习惯提醒存储';

CREATE TABLE `yh_he_electronic_fence_alarm` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `CENTER_LONGITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '电池电量百分比 -- 暂时没有用',
  `CENTER_LATITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `RADIUS` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'UNIT : KM',
  `CURRENT_LONGTITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `CURRENT_LATITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `DISTANCE_TO_CENTER` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'UNIT : M',
  `DISTANCE_OVERFLOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'UNIT : M',
  `CONTENT` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `TIME` datetime NOT NULL,
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ACCEPT_TIME` datetime DEFAULT NULL,
  `ACCEPT_OPERATOR_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ARCHIVE_TIME` datetime DEFAULT NULL,
  `ACCEPT_DETAIL_DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='电子围栏告警表';

CREATE TABLE `yh_he_gps_location` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `LONGITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LATITUDE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HEIGHT` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `SPEED` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `DIRECTION` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `TIME` datetime NOT NULL,
  `SOURCE_TYPE` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '00 -- GPS (默认）\r\n            01 -- MULTICELL\r\n            02 -- SINGLECELL',
  `SOURCE_RECORD_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'SOURCE_TYPE 不为GPS 时, 数据为对应记录ID',
  `ADDRESS` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'SOURCE_TYPE 不为GPS 时, 数据为基站定位的地址信息',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='智能手表gps定位信息表';

CREATE TABLE `yh_he_multicell_location` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `VOLTAGE` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `NUM` int NOT NULL,
  `MNC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `MCC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `RESPONSE` int DEFAULT NULL,
  `CELL_INFO` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'lacX：基站X的小区编号，16进制\r\n            cellidX：基站X的基站编号，16进制\r\n            arfcnX：基站X的信号频点，10进制\r\n            rxlevX：基站X的信号强度值，10进制；服务器端需要减去110，一般为负值\r\n            ',
  `TIME` datetime NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='智能手表多基站定位表';

CREATE TABLE `yh_he_param_setting` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TYPE` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `INDEX_1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `PARAMS` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='智能手表个人信息参数';

CREATE TABLE `yh_he_personal_threshold` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT 'type for [pulse, temperature, blood_pressure, blood_sugar]',
  `LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='个人告警阀值';

CREATE TABLE `yh_he_powerlow_alarm` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `VALUE` int DEFAULT NULL COMMENT '电池电量百分比 -- 暂时没有用',
  `TIME` datetime NOT NULL,
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='低电量告警表';

CREATE TABLE `yh_he_singlecell_location` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MNC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LAC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `CELL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `TIME` datetime NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='智能手表单基站定位表';

CREATE TABLE `yh_he_sitlong_remind` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `VALUE` int DEFAULT NULL COMMENT '久坐时间 （单位 秒） -- 暂时没有用到',
  `TIME` datetime NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='久坐提醒表';

CREATE TABLE `yh_he_sos_alarm` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` char(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '10000',
  `NUM` int DEFAULT NULL,
  `MNC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `MCC` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `RESPONSE` int DEFAULT NULL,
  `CELL_INFO` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'CELL_INFO',
  `CONTENT` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `TIME` datetime NOT NULL,
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '0 -- 未处理\r\n            1 -- 已受理 (正在处理)\r\n            2 -- 已处理 ( 处理完毕)',
  `ACCEPT_TIME` datetime DEFAULT NULL,
  `ACCEPT_OPERATOR_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ARCHIVE_TIME` datetime DEFAULT NULL,
  `ACCEPT_DETAIL_DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='sos告警表';

CREATE TABLE `yh_he_threshold` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'type for [pulse, temperature, blood_pressure, blood_sugar]',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ISTRUE` int DEFAULT NULL,
  `Order` int DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='标准告警范围表';

CREATE TABLE `yh_he_threshold2` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `blood_pressure_sys_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '收缩压1',
  `blood_pressure_sys_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '收缩压_max',
  `temperature_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体温',
  `temperature_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体温_max',
  `pulse_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率',
  `pulse_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心率_max',
  `blood_sugar_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血糖',
  `blood_sugar_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血糖_max',
  `blood_pressure_dia_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '舒张压',
  `blood_pressure_dia_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '舒张压_max',
  `trig_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '甘油三酯',
  `trig_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '甘油三酯_max',
  `hdlc_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高密度蛋白胆固醇',
  `hdlc_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '高密度蛋白胆固醇_max',
  `ldlc_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '低密度蛋白胆固醇',
  `ldlc_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '低密度蛋白胆固醇_max',
  `boold_oxygen_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血氧',
  `boold_oxygen_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血氧_max',
  `BMI_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'BMI',
  `BMI_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'BMI_max',
  `blood_muscle_anhydride_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血肌酐',
  `blood_muscle_anhydride_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血肌酐_max',
  `blood_urea_nitrogen_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿素氮',
  `blood_urea_nitrogen_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿素氮_max',
  `blood_uric_acid_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸',
  `blood_uric_acid_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸_max',
  `alanine_transaminase_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷丙转氨酶',
  `alanine_transaminase_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷丙转氨酶_max',
  `transaminase_millet_grass_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷草转氨酶',
  `transaminase_millet_grass_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷草转氨酶_max',
  `serum_serum_protein_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清白蛋白',
  `serum_serum_protein_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清白蛋白_max',
  `serum_globulin_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清球蛋白',
  `serum_globulin_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清球蛋白_max',
  `urinary_microprotein_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿微量蛋白',
  `urinary_microprotein_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿微量蛋白_max',
  `Hba1c_and_protein_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖化血红蛋白',
  `Hba1c_and_protein_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖化血红蛋白_max',
  `homocysteine_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '同型半胱氨酸',
  `homocysteine_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '同型半胱氨酸_max',
  `urinalysis_erythrocyte` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿检红细胞',
  `urine_protein` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿蛋白',
  `the_ketone_body` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '酮体',
  `tc_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总胆固醇',
  `tc_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总胆固醇_max',
  `blood_sugar2_min` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '餐后2小时血糖min',
  `blood_sugar2_max` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '餐后2小时血糖max',
  `blood_muscle_anhydride_min_girl` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸 女 min',
  `blood_muscle_anhydride_max_girl` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸 女 max',
  `blood_urea_nitrogen_max_girl` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸 女 max',
  `blood_urea_nitrogen_min_girl` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血尿酸 女 min',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='医生端标准告警范围表';

CREATE TABLE `yh_he_threshold_alarm` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户code',
  `TERMINAL_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '设备id',
  `ORG_ID` char(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构id',
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '类型 [blood_pressure, blood_sugar]',
  `RECORD_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `CONTENT` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '告警内容',
  `TIME` datetime NOT NULL COMMENT '告警时间',
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '0 -- 未处理\r\n            1 -- 已受理 (正在处理)\r\n            2 -- 已处理 ( 处理完毕)',
  `ACCEPT_TIME` datetime DEFAULT NULL COMMENT '受理时间',
  `ACCEPT_OPERATOR_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '受理人code',
  `ARCHIVE_TIME` datetime DEFAULT NULL COMMENT '归档时间',
  `ACCEPT_DETAIL_DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '受理情况描述',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `index_time` (`TIME`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='数据告警日志表';

CREATE TABLE `yh_he_threshold_copy1` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TYPE` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'type for [pulse, temperature, blood_pressure, blood_sugar]',
  `NAME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `LOW2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `HIGH2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_LOW` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `INVALID_HIGH` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `ISTRUE` int DEFAULT NULL,
  `Order` int DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='标准告警范围表';

CREATE TABLE `yh_he_threshold_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '告警类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_health_headline` (
  `id` int NOT NULL AUTO_INCREMENT,
  `he_title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '标题',
  `he_content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '内容',
  `he_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '图片',
  `he_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '时间',
  `he_author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '作者',
  `he_count` int DEFAULT NULL COMMENT '观看人数',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '备用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康头条';

CREATE TABLE `yh_health_lecture_category` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '分类名',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '父分类ID',
  `sort_id` int DEFAULT NULL COMMENT '顺序id',
  PRIMARY KEY (`categoryId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康大课堂分类信息表';

CREATE TABLE `yh_health_lecture_comment` (
  `commentId` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `pid` int unsigned DEFAULT '0' COMMENT '回复楼中楼编号',
  `lectureInfoId` int unsigned NOT NULL COMMENT '文章编号',
  `usercode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户编号',
  `commentInfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `status` tinyint DEFAULT '1' COMMENT '类型(-1:健康课堂,2:健康习惯)',
  `commentTime` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '创建时间',
  `comImg` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评论图片',
  PRIMARY KEY (`commentId`) USING BTREE,
  KEY `yh_health_lecture_infoId` (`lectureInfoId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='完善健康大课堂评论表';

CREATE TABLE `yh_health_lecture_enshrine` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '收藏表主键id',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户id',
  `categoryId` int DEFAULT NULL COMMENT '健康大课堂id',
  `new_time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康大课堂 收藏表';

CREATE TABLE `yh_health_lecture_fabulous` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '点赞表主键id',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户id',
  `categoryId` int DEFAULT NULL COMMENT '健康大课堂id',
  `new_time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_health_lecture_info` (
  `infoId` int NOT NULL AUTO_INCREMENT,
  `tips` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '关键词，多个用分号隔开',
  `category_id` int NOT NULL DEFAULT '0' COMMENT '类别ID',
  `typeid` int NOT NULL DEFAULT '0' COMMENT '类型ID,1,视频5，图文',
  `thumbnail_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '缩略图URL',
  `listbox_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '列表图',
  `detail_content_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '详情地址',
  `video_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '视频播放地址',
  `audio_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '语音播放地址',
  `update_date` datetime DEFAULT NULL COMMENT '开播日期',
  `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '标题',
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '作者',
  `instruction` varchar(15000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '概要',
  `instructionMd` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT 'Web渲染Md',
  `read_count` int unsigned DEFAULT '1' COMMENT '点击数',
  `addInfoTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `state` int DEFAULT NULL COMMENT '(1,发布2，草稿)',
  `speaker` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主讲人',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`infoId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康大课堂';

CREATE TABLE `yh_health_lecture_type` (
  `typeId` int NOT NULL COMMENT '唯一ID',
  `type_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '类型名',
  `illustration` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='课堂展示类型表';

CREATE TABLE `yh_health_lecture_watch` (
  `watchId` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '用户编号',
  `class_id` int NOT NULL DEFAULT '0' COMMENT '课程编号',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '姓名',
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '电话号码',
  `age` int DEFAULT NULL COMMENT '年龄',
  `org_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '所属子机构',
  `watch_date` datetime DEFAULT NULL COMMENT '观看开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '观看结束时间',
  `watch_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '观看时间',
  `status` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1.完整看完视频 2.没有完整看完视频',
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`watchId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='课堂观看人数';

CREATE TABLE `yh_health_star` (
  `healthstar_ID` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_type` int DEFAULT NULL,
  `healthstars` int DEFAULT NULL,
  `healthstars_events` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `events_time` datetime DEFAULT NULL,
  `count_day` int DEFAULT NULL,
  PRIMARY KEY (`healthstar_ID`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_healthy_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康计划名称',
  `plan_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康计划封面',
  `plan_number` int NOT NULL DEFAULT '0' COMMENT '健康计划共有多少天',
  `plan_score` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康计划积分',
  `plan_watch` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康计划浏览数',
  `plan_description` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康计划简介',
  `plan_people` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `plan_allnumber` int DEFAULT NULL COMMENT '总任务个数',
  `plan_allintegral` int DEFAULT NULL COMMENT '总积分',
  `plan_isapply` int DEFAULT '0' COMMENT '是否需要申请，0不需要申请,1需要申请',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康计划';

CREATE TABLE `yh_healthy_plandays` (
  `id` int NOT NULL AUTO_INCREMENT,
  `planday` int NOT NULL DEFAULT '0' COMMENT '计划第几天',
  `plandetail_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '对应计划详情id',
  `plan_id` int NOT NULL DEFAULT '0' COMMENT '计划id',
  `plan_num` int DEFAULT NULL COMMENT '计划任务个数',
  `plan_isskip` int DEFAULT NULL COMMENT '计划是否可以跳过,0可以，1不行',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康计划天数详情';

CREATE TABLE `yh_healthy_plandetail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plandetail_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '计划内容标题',
  `plandetail_type` int DEFAULT NULL COMMENT '计划内容类型 1:纯文章完成任务;2:文章+记录健康数据; 3:选择题; 4：看视频5；听音频，6跳转地址,7步数打卡，8弹框选择题',
  `plandetail_url` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '计划内容记录健康数据连接',
  `plandetail_content` varchar(8000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '计划内容',
  `plandetail_total` int NOT NULL DEFAULT '0' COMMENT '计划完成次数',
  `plandetail_tip` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择提示内容',
  `plandetail_jumpuri` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '背景图片',
  `plandetail_finishportrait` varchar(8000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '最近8人完成 任务头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康计划内容';

CREATE TABLE `yh_homeinfo_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `info_type` int NOT NULL DEFAULT '0' COMMENT '1:轮播图 ；2:健康头条',
  `info_img` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '信息图片',
  `info_title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '信息标题',
  `info_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '添加时间',
  `info_status` int NOT NULL DEFAULT '1' COMMENT '1:已发布；2:草稿',
  `info_source` int NOT NULL DEFAULT '0' COMMENT '来源（1，健康课堂 2，精彩活动 3，健康素养）',
  `info_content` int NOT NULL DEFAULT '0' COMMENT '内容id',
  `info_tip` int DEFAULT NULL COMMENT '轮播标识(1-5)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='主页信息配置';

CREATE TABLE `yh_hospital` (
  `h_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_name` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_province` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_city` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_area` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_address` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_tel` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_unit_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_economy_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_managemnet_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_unit_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_org_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_org_class` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  KEY `h_name` (`h_name`(255)) USING BTREE,
  KEY `h_code` (`h_code`) USING BTREE,
  KEY `h_province` (`h_province`) USING BTREE,
  KEY `h_city` (`h_city`) USING BTREE,
  KEY `h_area` (`h_area`) USING BTREE,
  KEY `h_area_2` (`h_area`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院';

CREATE TABLE `yh_hospital_economy_type` (
  `h_economy_type_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_economy_type_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院经济类型';

CREATE TABLE `yh_hospital_managemnet_type` (
  `h_managemnet_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_managemnet_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院管理类型';

CREATE TABLE `yh_hospital_org_class` (
  `h_org_class` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_org_class`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院组织班级';

CREATE TABLE `yh_hospital_org_level` (
  `h_org_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_org_level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院组织等级';

CREATE TABLE `yh_hospital_records` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Hospitalization_Date` datetime DEFAULT NULL,
  `Discharge_Diagnosis` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Note_Information` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Entry_Time` datetime DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `E_User_Id` int NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Ref9939` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院记录';

CREATE TABLE `yh_hospital_unit_level` (
  `h_unit_level` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_unit_level`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院单位级';

CREATE TABLE `yh_hospital_unit_type` (
  `h_unit_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `h_content_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_unit_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院单位类型';

CREATE TABLE `yh_hospital_unit_type_code` (
  `h_content_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `h_content_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`h_content_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='医院单位类型代码';

CREATE TABLE `yh_instrument_record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户code',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小工具类型1体重计算 2腰臀比 3.日需能量 4运动能耗 5营养素',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='小工具记录表';

CREATE TABLE `yh_intervention_template` (
  `intervention_id` int NOT NULL AUTO_INCREMENT,
  `source_type` int DEFAULT NULL COMMENT '模板来源1.系统提供2.我创建的3.其他人创建的4.全部',
  `template_diseases` int DEFAULT NULL COMMENT '模板条件：',
  `template_risk` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '模板条件：',
  `template_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '模板名称：',
  `insert_time` datetime DEFAULT NULL COMMENT '创建时间',
  `plan_count` int DEFAULT NULL COMMENT '计划数',
  `apply_sex` int DEFAULT NULL COMMENT '性别',
  `apply_age_min` int DEFAULT NULL COMMENT '年龄',
  `apply_age_max` int DEFAULT NULL,
  `apply_bim_min` float(10,0) DEFAULT NULL COMMENT 'BMI',
  `apply_bim_max` float(10,0) DEFAULT NULL,
  `souce_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `souce_isdelete` int DEFAULT '0' COMMENT '0表示未删除，1表示删除',
  `operation` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '操作',
  `template_describe` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`intervention_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划模板表';

CREATE TABLE `yh_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务调度表';

CREATE TABLE `yh_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='定时任务调度日志表';

CREATE TABLE `yh_jz_coin_detail` (
  `ID` int NOT NULL,
  `COIN_EVENT` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '积分事件',
  `COIN_TIME` datetime DEFAULT NULL COMMENT '积分发生时间',
  `COIN` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '积分数量',
  `VOLUNTEER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '志愿者编号',
  `ORDER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '关联的工单ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='积分详情';

CREATE TABLE `yh_jz_oa_notify` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '类型',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内容',
  `files` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '附件',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '状态',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `oa_notify_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='通知通告';

CREATE TABLE `yh_jz_oa_notify_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `notify_id` bigint DEFAULT NULL COMMENT '通知通告ID',
  `user_id` bigint DEFAULT NULL COMMENT '接受人',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '阅读标记',
  `read_date` date DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `oa_notify_record_notify_id` (`notify_id`) USING BTREE,
  KEY `oa_notify_record_user_id` (`user_id`) USING BTREE,
  KEY `oa_notify_record_read_flag` (`is_read`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='通知通告发送记录';

CREATE TABLE `yh_jz_service_assessment_switch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `every_switch` int DEFAULT NULL COMMENT '每次完成评估',
  `long_time_no_switch` int DEFAULT NULL COMMENT '久未评估',
  `long_time_no_intervaltime` double(10,0) DEFAULT NULL COMMENT '久未检测间隔时间',
  `long_time_no_remindtime` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `first_switch` int DEFAULT NULL COMMENT '首次完成',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_bodydata_switch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '1开2关，后面一样',
  `first_switch` int DEFAULT NULL COMMENT '首次检测',
  `every_switch` int DEFAULT NULL COMMENT '每次检测',
  `long_time_no_switch` int DEFAULT NULL COMMENT '久未检测',
  `long_time_no_intervaltime` int DEFAULT NULL COMMENT '久未检测间隔时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_person` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '服务人员编号',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '姓名',
  `gender` int NOT NULL DEFAULT '0' COMMENT '性别',
  `end_service_time` datetime DEFAULT NULL COMMENT '服务结束时间',
  `phone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '电话',
  `age` int NOT NULL DEFAULT '0' COMMENT '年龄',
  `start_service_time` datetime DEFAULT NULL COMMENT '服务开始时间',
  `service_state` int NOT NULL DEFAULT '0' COMMENT '0：等待、1：正在服务、2：服务关闭、3：服务完成',
  `service_waiting_time` datetime DEFAULT NULL COMMENT '服务等待时间',
  `service_content` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务内容',
  `service_type` int NOT NULL,
  `service_record` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '风险评估次数',
  `isrede` int DEFAULT '1' COMMENT '1:未读，2已读',
  `returnvisit` int DEFAULT '1' COMMENT '回访类型id1.电话回访，2.微信回访3.小程序聊天回访4.上门回访5，短信回访6,小程序订阅通知回访7，公众号通知回访',
  `longitude` double(50,0) DEFAULT NULL COMMENT '经度',
  `dimension` double(50,0) DEFAULT NULL COMMENT '纬度',
  `insert_time` datetime DEFAULT NULL COMMENT '插入时间',
  `acceptsituation` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '受理情况',
  `childnodes` int DEFAULT NULL COMMENT '子节点对应id',
  `childtag` int DEFAULT NULL COMMENT '类型标记1.检测类型，2.评估类型',
  `satisfaction` int DEFAULT NULL COMMENT '配合程度0.不配合1.一半配合2.比较配合3.非常配合',
  `service_subtitle` varchar(55) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `finishi_doctor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `totaltype` int DEFAULT '1' COMMENT '总类型1.自主使用,2.久未使用，3.干预计划执行',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE,
  KEY `service_state` (`service_state`) USING BTREE,
  KEY `insert_time` (`insert_time`) USING BTREE,
  KEY `totaltype` (`totaltype`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='服务任务';

CREATE TABLE `yh_jz_service_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_returnway` (
  `id` int NOT NULL AUTO_INCREMENT,
  `returnway` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_switch` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '服务开关id',
  `service_type` int NOT NULL COMMENT '服务类型id',
  `service_date` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '任务期限/小时',
  `service_content` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '服务内容',
  `service_describe` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务描述',
  `isopen` int DEFAULT '0' COMMENT '0未打开',
  `return_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_total_person` (
  `tid` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `doctor_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`tid`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE,
  KEY `doctor_code` (`doctor_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_service_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_jz_sys_sms` (
  `sms_id` bigint NOT NULL AUTO_INCREMENT,
  `template_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '消息模本ID',
  `smsname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '短信名称',
  `signname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '短信签名',
  `accessKey` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'ak',
  `accessKeySecret` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'aks',
  `smscode_num` int DEFAULT NULL COMMENT '验证码位数',
  `sms_create` datetime DEFAULT NULL COMMENT '创建时间',
  `sms_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `template_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板分类',
  `template_text` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板内容',
  `template_genre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板类型',
  `type` int DEFAULT NULL COMMENT '(1,通用  2，服务任务模板)',
  PRIMARY KEY (`sms_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='短信管理';

CREATE TABLE `yh_jz_sys_sms_text` (
  `T_id` int NOT NULL AUTO_INCREMENT,
  `T_smsid` int DEFAULT NULL COMMENT '模板ID',
  `T_text` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '模板内容',
  PRIMARY KEY (`T_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='短信模板分类内容';

CREATE TABLE `yh_life_habit_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `habit_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '习惯名',
  `habit_type` int DEFAULT NULL COMMENT '所属分类',
  `habitDesc` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '习惯说明',
  `period_whether` int DEFAULT '1' COMMENT '是否允许用户修改周期（1,是 2,否）',
  `period` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '周期',
  `time_whether` int DEFAULT '1' COMMENT '是否允许用户修改打卡时间（1,是 2,否）',
  `begin_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '00:00:00' COMMENT '开始时间',
  `finish_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '23:59:00' COMMENT '结束时间',
  `life_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `life_time` date DEFAULT NULL COMMENT '新建时间',
  `one_rank` int DEFAULT '7' COMMENT '级别1',
  `two_rank` int DEFAULT '21' COMMENT '级别2',
  `three_rank` int DEFAULT '56' COMMENT '级别3',
  `four_rank` int DEFAULT '84' COMMENT '级别4',
  `five_rank` int DEFAULT '168' COMMENT '级别5',
  `remind_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '提醒时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '该习惯所属user_code,默认为0公有习惯',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2' COMMENT '(1，本公司加的运动 2，加盟商添加的运动)',
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT 'OR100' COMMENT '机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康习惯字典';

CREATE TABLE `yh_life_habit_dict_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '健康习惯分类名',
  `category_icon` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分类小图标URL',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='健康习惯分类';

CREATE TABLE `yh_life_habit_improvement_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '需改善项',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0',
  `risk` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '带来的危害',
  `assess_scores` int NOT NULL DEFAULT '0' COMMENT '评分最大值',
  `question_id` int NOT NULL DEFAULT '0' COMMENT '对应的问卷题目编号',
  `analyzename` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '分析报告',
  `result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评估结果（0~60分：较差 60~80分：良好 80~100分：很棒）',
  `Dict_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='生活习惯改善字典';

CREATE TABLE `yh_life_habit_person_dict` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0',
  `habit_id` int DEFAULT '0' COMMENT '生活习惯编号',
  `continue_day` int DEFAULT '0' COMMENT '最大持续天数',
  `missing_day` int DEFAULT NULL COMMENT '1（系统匹配）2（医生手动） 3（用户手动）',
  `total_day` int DEFAULT '0' COMMENT '总坚持天数',
  `begin_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '开始打卡时间',
  `finish_tim` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '结束打卡时间',
  `period` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '打卡周期',
  `begin_date` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '开始打卡日期',
  `finish_date` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '结束打卡日期',
  `accomplish_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3' COMMENT '状态(1,完成，2，已过期3，执行中4,已删除)',
  `increase_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加日期',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `standby1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `standby2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='我的生活习惯项目';

CREATE TABLE `yh_life_habit_question` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '题目',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选项1',
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '选项1 的分值',
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选项2',
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2' COMMENT '选项2 的分值',
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选项3',
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3' COMMENT '选项3 的分值',
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选项4',
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '4' COMMENT '选项4的分值',
  `choose5` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选项5',
  `choose5_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '5' COMMENT '选项5的分值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='生活习惯问卷';

CREATE TABLE `yh_life_habit_retroactive` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gain_time` date DEFAULT NULL COMMENT '获取时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `past_time` date DEFAULT NULL COMMENT '过期时间',
  `whether` int DEFAULT NULL COMMENT '是否使用（1,使用 0，未使用）',
  `mode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '获取方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='习惯补卡记录表';

CREATE TABLE `yh_life_habit_sign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `habit_id` int NOT NULL DEFAULT '0' COMMENT '打卡习惯编号',
  `signId_date` date NOT NULL COMMENT '打卡签到日期',
  `mark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '签到备注',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户编号',
  `life_add` int DEFAULT '0' COMMENT '是否是补卡（1，是 0，不是）',
  `sign_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `increase_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_falg` int DEFAULT NULL COMMENT '打卡状态 1 、未打卡 2、已打卡',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='生活习惯打卡记录';

CREATE TABLE `yh_life_habit_skip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `skip_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='习惯评估记录';

CREATE TABLE `yh_loginlog` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='系统访问记录';

CREATE TABLE `yh_me_notware_alarm` (
  `ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `MEMBER_ID` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ORG_ID` char(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `IME` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `TIME` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `CONTENT` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `STATUS` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '0 - 未处理\r\n            1 - 已处理',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `FK_Reference_35` (`MEMBER_ID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='智能手表未穿戴告警表';

CREATE TABLE `yh_medal` (
  `id` int NOT NULL,
  `img` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片',
  `type` int DEFAULT NULL COMMENT '类型',
  `he_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '外键id',
  `grade` int DEFAULT NULL COMMENT '等级',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '勋章名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='勋章';

CREATE TABLE `yh_medical_category` (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '检查项目分类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '分类名字',
  `category_parent_id` int NOT NULL DEFAULT '1' COMMENT '父级ID',
  `descrip` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='体检项目';

CREATE TABLE `yh_medical_item_modal` (
  `mem_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '检查项目指标id',
  `category_id` int DEFAULT NULL COMMENT 'well_pac_content_sort主键',
  `category_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '分类名字',
  `chinese_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '中文名',
  `english_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '英文名',
  `refer_value` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '参考值',
  `unit` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '单位',
  `meaning` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '意义',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mem_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='体检项目';

CREATE TABLE `yh_medical_records` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用药记录序号',
  `user_code` int DEFAULT NULL COMMENT '用户id',
  `drug_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药品名称',
  `img_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药品图片',
  `take_number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服用数量',
  `drug_unit` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用单位',
  `remind_time` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提醒时间',
  `drug_state` int DEFAULT NULL COMMENT '1.待服药、2.以服药、3.漏服药、4.已超时',
  `taking_state` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服药时差',
  `insert_date` date DEFAULT NULL COMMENT '服药时间',
  `remind_id` int DEFAULT NULL COMMENT '用药提醒主表id',
  `drug_id` int DEFAULT NULL COMMENT '药品id',
  `number_id` int DEFAULT NULL COMMENT '次数id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT='用药记录表';

CREATE TABLE `yh_medication_remind` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '提醒序号',
  `user_code` int DEFAULT NULL COMMENT '用户id',
  `remind_frequency` int DEFAULT NULL COMMENT '提醒频率',
  `remind_began` date DEFAULT NULL COMMENT '提醒开始日期',
  `remind_end` date DEFAULT NULL COMMENT '提醒结束日期',
  `insert_date` datetime DEFAULT NULL COMMENT '添加时间',
  `is_falg` int DEFAULT NULL COMMENT '提醒开启状态(1.开启2.关闭)',
  `last_date` date DEFAULT NULL COMMENT '上一次提醒时间',
  `remind_type` int DEFAULT NULL COMMENT '添加类型 1.用户 2.健管师',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT='用药提醒总表';

CREATE TABLE `yh_medicine` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `parent_id` bigint DEFAULT '-1' COMMENT '父级ID',
  `user_code` varbinary(255) DEFAULT NULL COMMENT '用户编号',
  `userFlag` tinyint(1) DEFAULT NULL COMMENT '1健管师，2用户',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '药品名称',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持有人及生产企业',
  `specification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规格',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '药品照片地址',
  `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频地址地址',
  `insert_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reminder_time` datetime DEFAULT NULL COMMENT '提醒时间',
  `drug_state` tinyint(1) DEFAULT NULL COMMENT '状态(1:待服药,4:已超时)',
  `taking_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服药状态 示例：今日提前32分钟服药',
  `drugUnit` varbinary(5) DEFAULT NULL COMMENT '单位 （包、mg、粒等等）',
  `number` double DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_member_grade` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vip_grade` int DEFAULT NULL COMMENT '会员等级',
  `min_energy` int DEFAULT NULL COMMENT '所需最小积分',
  `max_energy` int DEFAULT NULL COMMENT '所需最大积分(为null表示最大等级)',
  `img` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='会员等级字典';

CREATE TABLE `yh_my_life_habit_assess_result` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '用户编号',
  `dict_id` int NOT NULL COMMENT '评估id',
  `assess_scores` int NOT NULL DEFAULT '0' COMMENT '评估分值',
  `assess_improved` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '题目,多个用逗号隔开',
  `result_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='我的生活习惯评估问卷';

CREATE TABLE `yh_org_activity` (
  `activity_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `org_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='精彩活动与机构关联授权表';

CREATE TABLE `yh_org_class` (
  `org_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构id',
  `class_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='课堂与机构关联授权表';

CREATE TABLE `yh_org_doctor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `add_user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `add_date` datetime DEFAULT NULL,
  `link_type` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `org_code` (`org_code`,`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_org_droit_model` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `DtName` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Dt_Data` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `Org_Code` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Add_Date` datetime DEFAULT NULL,
  `Dt_Type` int DEFAULT NULL,
  `User_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `tree_length` int DEFAULT '0',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE,
  KEY `DtName` (`DtName`(255)) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='机构所有权模型';

CREATE TABLE `yh_org_facility` (
  `id` int NOT NULL AUTO_INCREMENT,
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '机构code',
  `deviceId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '设备id',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='设备绑定机构';

CREATE TABLE `yh_org_hospital_info` (
  `org_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `hospital_name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_city` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_address` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_zip_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_tel` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_fax` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_website` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_name` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_sex` int DEFAULT NULL,
  `contact_tel` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_departments` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `contact_post` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `add_date` datetime DEFAULT NULL,
  `hospital_class` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hospital_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`org_code`) USING BTREE,
  KEY `hospital_name` (`hospital_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_org_tree` (
  `Org_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Org_Name` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Org_Note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `Super_Org_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `Add_Date` date DEFAULT NULL,
  `Class_Number` int DEFAULT NULL,
  `User_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Docdor_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `org_tree_upload_id` int DEFAULT '0',
  `org_customer_code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '市',
  `county` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '区',
  `standby` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用',
  `oa_tree_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'oa机构编号',
  UNIQUE KEY `tree_Org_Code` (`Org_Code`) USING BTREE,
  KEY `Org_Name` (`Org_Name`(255)) USING BTREE,
  KEY `Super_Org_Code` (`Super_Org_Code`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE,
  KEY `Docdor_Code` (`Docdor_Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='机构管理层级树';

CREATE TABLE `yh_pain_resolve` (
  `resolve_id` int NOT NULL AUTO_INCREMENT,
  `pain_id` tinyint NOT NULL COMMENT '疼痛部位类目id',
  `resolve_type` int DEFAULT NULL COMMENT '评估结果类型1.差; 2.一般 ;3.良好',
  `resolve_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '评估总结',
  `resolve_harm` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '评估危害',
  `resolve_explain` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '解释',
  `resolve_solution` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '解决方案',
  `resolve_estimate` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '判定值',
  `teach_video` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '教学视频',
  PRIMARY KEY (`resolve_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疼痛建议';

CREATE TABLE `yh_pain_total_report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编码',
  `qtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户名',
  `times` int DEFAULT NULL COMMENT '评估次数',
  `pain_score` int DEFAULT NULL COMMENT '评估总分',
  `cervical_result` int DEFAULT NULL COMMENT '颈椎总结',
  `cervical_classify` int DEFAULT NULL COMMENT '颈椎等级 1.差；2.一般；3.良好',
  `shoulder_result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '肩关节总结',
  `shoulder_classify` int DEFAULT NULL COMMENT '肩关节等级1.差；2.一般；3.良好',
  `back_result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '上背部总结',
  `back_classify` int DEFAULT NULL COMMENT '上背部等级1.差；2.一般；3.良好',
  `waist_result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '腰部总结',
  `waist_classify` int DEFAULT NULL COMMENT '腰部等级1.差；2.一般；3.良好',
  `wrist_result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手腕总结',
  `wrist_classify` int DEFAULT NULL COMMENT '手腕等级1.差；2.一般；3.良好',
  PRIMARY KEY (`report_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='疼痛评估报告';

CREATE TABLE `yh_pain_total_report_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户编号',
  `report_id` int NOT NULL COMMENT '评估id',
  `assess_improved` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '题目,多个用逗号隔开',
  `result_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='症状评估问卷';

CREATE TABLE `yh_personality_assessment` (
  `Pa_Id` int NOT NULL AUTO_INCREMENT,
  `Pa_Que` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Pa_Del` int DEFAULT NULL,
  `Pa_Yes` int DEFAULT NULL,
  `Pa_No` int DEFAULT NULL,
  PRIMARY KEY (`Pa_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='A型性格评估问卷';

CREATE TABLE `yh_physical_data` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编号',
  `detection_date` datetime DEFAULT NULL COMMENT '检测日期',
  `physical_score` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '体适能得分',
  `physical_level` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '体适能等级',
  `weight` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '体重',
  `vertical_jump` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '纵跳',
  `vital_capacity` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '肺活量',
  `step_test` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '台阶测试',
  `balanced` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '平衡能力',
  `sit_reach` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '坐位体前屈',
  `reaction` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '反应速度',
  `grip` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '握力',
  `jump_rope` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '一分钟跳绳',
  `sit_up` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '一分钟仰卧起坐',
  `push_up` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '俯卧撑',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='体适能数据表';

CREATE TABLE `yh_pressure_assessment` (
  `Pa_Id` int NOT NULL AUTO_INCREMENT,
  `Pa_Que` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Pa_Del` int DEFAULT NULL,
  PRIMARY KEY (`Pa_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='PSTRI压力评估问卷';

CREATE TABLE `yh_pressure_evaluate` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Evaluate_Data` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Evaluate_Type` int DEFAULT NULL,
  `Evaluate_Score` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `User_Code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Evaluate_Date` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Do_Date` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `Evaluate_select` varchar(2500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='压力管理三大评估问卷数据';

CREATE TABLE `yh_programs_dictionary` (
  `plancount_id` int NOT NULL AUTO_INCREMENT,
  `plan_count` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`plancount_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_programs_template` (
  `programs_id` int NOT NULL AUTO_INCREMENT COMMENT '干预计划模板id',
  `Intervention_id` int DEFAULT NULL,
  `programs_weeks` int DEFAULT NULL COMMENT '第几周',
  `programs_days` int DEFAULT NULL COMMENT '第几天',
  `service_way` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务方式1.电话回访，2.微信回访3.小程序聊天回访4.上门回访,5.不限',
  `plan_content` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '计划内容',
  `content_prompt` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '内容提示',
  PRIMARY KEY (`programs_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划模板';

CREATE TABLE `yh_psychological_assessment` (
  `Pa_Id` int NOT NULL AUTO_INCREMENT,
  `Pa_Que` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Pa_Del` int DEFAULT NULL,
  `Pa_Often` int DEFAULT NULL,
  `Pa_Occasional` int DEFAULT NULL,
  `Pa_Rare` int DEFAULT NULL,
  `Pa_Never` int DEFAULT NULL,
  PRIMARY KEY (`Pa_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='心理健康问卷';

CREATE TABLE `yh_question_assess_title_front` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `question_item_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '对应的字段',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1',
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2',
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3',
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '4',
  `choose5` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose5_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '5',
  `choose6` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose6_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '是否根据其他题目来判断0-有子项；-1-没有子项；子项是为0的id',
  `gender` int DEFAULT '0' COMMENT '是否女性专属问题？0-共有 1-女性专有 2- 男性专有',
  `partId` int DEFAULT '0' COMMENT '问题所属分类1-居住环境及基本情况 2-个人疾病史3-家庭疾病史4-饮食习惯 5-吸烟及喝酒情况6-运动情况7-睡眠及精神压力 8-男性健康9-女性健康10-体检指标',
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷题目表';

CREATE TABLE `yh_question_assess_title_front2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `question_item_name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '对应的字段',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1',
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2',
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3',
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '4',
  `choose5` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose5_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '5',
  `choose6` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose6_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '是否根据其他题目来判断0-有子项；-1-没有子项；子项是为0的id',
  `gender` int DEFAULT '0' COMMENT '是否女性专属问题？0-共有 1-女性专有 2- 男性专有',
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷题目表';

CREATE TABLE `yh_question_base_userinfo` (
  `id` int NOT NULL,
  `diseaseIds` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '疾病ID',
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '请求序列号',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户CODE',
  `gender` int DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '真名',
  `user_occupation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '职业',
  `user_jzd` int DEFAULT NULL COMMENT '居住地：1南方;2北方',
  `user_jzhj` int DEFAULT NULL COMMENT '居住区域：1城市; 2农村',
  `air_status` int DEFAULT NULL COMMENT '所住城市空气质量1：优良:2：差',
  `user_envir` int DEFAULT NULL COMMENT '居住环境１:不寒冷不潮湿２:寒冷潮湿',
  `dust_exposure` int DEFAULT NULL COMMENT '是否有粉尘接触史（如：矿工、环卫工人等或长期接触过汽油等有害物质）1:有; 2:无',
  `dust_exposure_disYear` int DEFAULT NULL COMMENT '有粉尘接触史_年',
  `dust_exposure_disMonth` int DEFAULT NULL COMMENT '有粉尘接触史_月',
  `lampblack_contact` int DEFAULT NULL COMMENT '是否有油烟接触史（长期厨房做菜或为厨师职业）1:有; 2:无',
  `lampblack_contac_disYear` int DEFAULT NULL COMMENT '油烟接触史_年',
  `lampblack_contac_disMonth` int DEFAULT NULL COMMENT '油烟接触史_月',
  `asbestos_contact` int DEFAULT NULL COMMENT '是否有石棉接触史（长期接触过石棉）1:有; 2:无',
  `asbestos_contact_disYear` int DEFAULT NULL COMMENT '石棉接触史_年',
  `asbestos_contact_month` int DEFAULT NULL COMMENT '石棉接触史_月',
  `decorate_house` int DEFAULT NULL COMMENT '近期（2年）是否新房装修1:有; 2:无',
  `married_status` int DEFAULT NULL COMMENT '婚烟状况1:未婚2:已婚3:离婚或丧偶4:其他',
  `chemical_occupation` int DEFAULT NULL COMMENT '是否长期从事化工、染料、橡胶及印刷等职业 1,是   2,否',
  `education_levels` int DEFAULT NULL COMMENT '文化程度：1:小学及以下2:初中3:高中、中专或技校4:本科5:本科以上',
  `income` int DEFAULT NULL COMMENT '收入：1:小于1000元2:1000~2999元3:3000~4999元4:5000元以上',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='评估问卷_用户基础信息';

CREATE TABLE `yh_question_check_json` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0',
  `user_check_answer` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0',
  `user_check_index` int NOT NULL DEFAULT '0' COMMENT '暂存下标',
  `user_check_ts` int NOT NULL DEFAULT '0' COMMENT '评估 1 ；暂存 2',
  `user_check_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户问卷答案';

CREATE TABLE `yh_question_check_json2` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0',
  `user_check_answer` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `user_check_index` int NOT NULL DEFAULT '0' COMMENT '暂存下标',
  `user_check_ts` int NOT NULL DEFAULT '0' COMMENT '评估 1 ；暂存 2',
  `user_check_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '请求序列号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户问卷答案';

CREATE TABLE `yh_question_family_disease` (
  `id` int NOT NULL,
  `request_searialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `family_diab_f` int DEFAULT NULL COMMENT '父亲糖尿病',
  `family_diab_m` int DEFAULT NULL COMMENT '母亲糖尿病',
  `family_diab_bs` int DEFAULT NULL COMMENT '兄弟姐妹糖尿病',
  `family_diab_ch` int DEFAULT NULL COMMENT '子女糖尿病',
  `family_hbp_f` int DEFAULT NULL COMMENT '父亲高血压',
  `family_hbp_m` int DEFAULT NULL COMMENT '母亲高血压',
  `family_hbp_bs` int DEFAULT NULL COMMENT '兄弟姐妹高血压',
  `family_hbp_ch` int DEFAULT NULL COMMENT '子女高血压',
  `family_chd_f` int DEFAULT NULL COMMENT '您的家族中父亲是否患冠心病/心绞痛/心肌梗塞',
  `family_chd_m` int DEFAULT NULL COMMENT '您的家族中母亲是否患冠心病/心绞痛/心肌梗塞',
  `family_chd_bs` int DEFAULT NULL COMMENT '您的家族中兄弟姐妹是否患冠心病/心绞痛/心肌梗塞',
  `family_chd_ch` int DEFAULT NULL COMMENT '您的家族中子女是否患冠心病/心绞痛/心肌梗塞',
  `family_stroke_f` int DEFAULT NULL COMMENT '您的家族中父亲是否患脑卒中',
  `family_stroke_m` int DEFAULT NULL COMMENT '您的家族中母亲是否患脑卒中',
  `family_stroke_bs` int DEFAULT NULL COMMENT '您的家族中兄弟姐妹是否患脑卒中',
  `family_stroke_ch` int DEFAULT NULL COMMENT '您的家族中子女是否患脑卒中',
  `family_hf_f` int DEFAULT NULL COMMENT '您的家族中父亲是否患患髋部骨折',
  `family_hf_m` int DEFAULT NULL COMMENT '您的家族中母亲是否患患髋部骨折',
  `family_hf_bs` int DEFAULT NULL COMMENT '您的家族中兄弟姐妹是否患患髋部骨折',
  `family_hf_ch` int DEFAULT NULL COMMENT '您的家族中子女是否患患髋部骨折',
  `family_lung_f` int DEFAULT NULL COMMENT '您的家族中父亲是否患患肺癌',
  `family_lung_m` int DEFAULT NULL COMMENT '您的家族中母亲是否患患肺癌',
  `family_lung_bs` int DEFAULT NULL COMMENT '您的家族中兄弟姐妹是否患患肺癌',
  `family_lung_ch` int DEFAULT NULL COMMENT '您的家族中子女是否患患肺癌',
  `family_pro_f` int DEFAULT NULL COMMENT '您的家族中父亲是否患前列腺癌 ',
  `family_pro_bs` int DEFAULT NULL COMMENT '您的家族中兄弟是否患前列腺癌',
  `family_pro_ch` int DEFAULT NULL COMMENT '您的家族中儿子是否患前列腺癌',
  `family_breast_m` int DEFAULT NULL COMMENT '您的家族中母亲是否患乳腺癌',
  `family_breast_bs` int DEFAULT NULL COMMENT '您的家族中姐妹是否患乳腺癌',
  `family_breast_ch` int DEFAULT NULL COMMENT '您的家族中女儿是否患乳腺癌',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷_家族史';

CREATE TABLE `yh_question_female` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_serialNum` int DEFAULT NULL COMMENT '请求序列号',
  `female_menstruation` int DEFAULT NULL COMMENT '您第一次来月经的年龄是多大？1，<12 岁    2，≥12岁',
  `female_gestation` int DEFAULT NULL COMMENT '您是否处于妊娠期？1,是2，否',
  `female_birth` int DEFAULT NULL COMMENT '您是否生育过？1,是  2,是，产后超过3个月3,否',
  `female_tire` int DEFAULT NULL COMMENT '您生第一胎年龄是多大？1,<30岁   2,≥30岁',
  `female_tire_number` int DEFAULT NULL COMMENT '您生育过 几次？1,<2次    2,≥2次',
  `female_breast` int DEFAULT NULL COMMENT '您的孩子是母乳喂养吗？1,是   2,否',
  `female_breast_time` int DEFAULT NULL COMMENT '您的哺乳时间总共有多长时间1,<12个月   2,≥12个月',
  `female_menopause` int DEFAULT NULL COMMENT '您是否绝经1,是  2,否',
  `female_menopause_age` int DEFAULT NULL COMMENT '您的绝经年龄是 多大？1,<55岁   2,≥55岁',
  `has_med_ert` int DEFAULT NULL COMMENT '您绝经后是否使用过雌激素替代疗法?1,是2,否',
  `med_ert` int DEFAULT NULL COMMENT '您绝经后是否使用过雌激素替代疗法时长1,雌激素替代疗法<5年 2,雌激素替代疗法>=5年',
  `weight_up` int DEFAULT NULL COMMENT '绝经后是否体重上升超过5公斤（女）（前置条件：已绝经）1,是   2,否',
  `med_use_pill` int DEFAULT NULL COMMENT '是否长期口服避孕药（女）1,是   2,否',
  `breast_plastic_surgery` int DEFAULT NULL COMMENT '是否长期使用丰乳产品或做过乳房整形手术（女）1,是   2,否',
  `gynecology_operation` int DEFAULT NULL COMMENT '是否曾有有子宫、卵巢和输卵管等的手术（女）1,是   2,否',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `question_serialNum` (`question_serialNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='女性健康';

CREATE TABLE `yh_question_food` (
  `id` int NOT NULL,
  `question_serialNum` int DEFAULT NULL,
  `food_staple` int DEFAULT NULL COMMENT '您平均每天吃多少大米、面类主食（1,不吃2，少于200克3，200-400克4，400-500克5，500克以上',
  `food_coarse` int DEFAULT NULL COMMENT '您每天吃多少杂粮（如玉米、小米、高粱、燕麦、红小豆、红薯、山药等）1、不吃 2、少于200克 3、200-400克 4、400-500克 5、500克以上',
  `food_vegetables` int DEFAULT NULL COMMENT '您平均每天吃多少新鲜蔬菜？（1碗炒熟的青菜约等于300克1、不吃2、少于50克3、50-75克4、150-300克5、300克以上）',
  `food_fruits` int DEFAULT NULL COMMENT '您平均每天吃多少新鲜水果？（1个中等大小的苹果约等于300克1、不吃2、少于50克3、50-100克4、100-200克5、200克以上）',
  `food_meat` int DEFAULT NULL COMMENT '您平均每天吃多少肉类及肉制品？（包含内脏）1、不吃2、少于50克3、50-75克4、75-100克5、100克以上）',
  `food_meat_kinds` int DEFAULT NULL COMMENT '您通常喜欢吃哪种肉？（“肉类”选择“不吃”的，不用回答）注：如果肉类选择不吃，则不填 1、廋肉2、肥瘦肉3、肥肉4、动物内脏',
  `food_fish` int DEFAULT NULL COMMENT '您平均每天吃多少鱼类及水产品？1、不吃2、少于25克3、25-50克4、50-100克5、100克以上）',
  `food_egg` int DEFAULT NULL COMMENT '您平均每天吃多少蛋类及制品？（1个鸡蛋约等于50克）1,不吃  2、少于50 3、50-100 4、100-150 5、150 以上',
  `food_milk` int DEFAULT NULL COMMENT '您平均每天喝几杯牛奶或酸奶？（240毫升相当于1杯）1、不喝2、少于0.5杯3、0.5-1杯4、1-2杯5、2杯以上',
  `food_dairy` int DEFAULT NULL COMMENT '您平均每天吃多少奶制品(如奶酪、奶粉、奶片等)？（一片奶酪≈40克,一平搪瓷勺奶粉≈5克）1、不吃  2、少于10克 3、10-40克 4、40-80克 5、80克以上',
  `food_bean` int DEFAULT NULL COMMENT '您平均每天吃多少大豆及豆制品(如黄豆、黑豆、豆腐、豆干、腐竹等)1、不吃  2、少于15克 3、15-30克 4、30-50克 5、50克以上',
  `food_soybean` int DEFAULT NULL COMMENT '您平均每天喝几杯豆浆?（240毫升≈1杯）1、不喝2、少于0.5杯3、0.5-1杯4、1-2杯5、2杯以上',
  `food_fired` int DEFAULT NULL COMMENT '您通常每周吃几次油炸食品（如油饼、油条、炸糕等）1、不吃 2、1-4次 3、4-7次 4、7次以上',
  `food_sweet` int DEFAULT NULL COMMENT '您通常每周吃几次点心或甜食（如蛋糕、巧克力、饼干及各种烘培点心等）?1、不吃 2、1-4次 3、4-7次 4、7次以上',
  `food_sugary` int DEFAULT NULL COMMENT '您通常每周喝几次含糖饮料（如可乐、果汁饮料等）吗?1、不喝 2、1-4次 3、4-7次 4、7次以上',
  `food_pickled` int DEFAULT NULL COMMENT '您通常每周吃几次咸菜或腌渍食品（如咸鱼）吗？1、不吃 2、1-4次 3、4-7次 4、7次以上',
  `food_breakfast` int DEFAULT NULL COMMENT '您每周有几天吃早餐？1、多于5天 2、 4-5天 3、 2-3天 4、2天以下',
  `food_good` int DEFAULT NULL COMMENT '您每天哪一餐吃得最丰盛1,早餐 2,午餐 3,晚餐 4,加餐',
  `food_add_meal` int DEFAULT NULL COMMENT '如果加餐（三餐以外的餐次），您常选择吃什么1,从不加餐 2,水果、蔬菜、酸奶 3,坚果（瓜子、花生、核桃、杏仁等） 4,甜食及膨化食品（如蛋糕、巧克力、薯片等）',
  `food_dine` int DEFAULT NULL COMMENT '您每周在外就餐的次数是1， 每天1次及以上 2， 每周5-6次 3， 每周2-4次 4，每周少于2次',
  `food_cook` int DEFAULT NULL COMMENT '您经常自己炒菜做饭吗?1， 每周少于1次 2， 每周1-3次 3， 每周4-7次 4，每周7次以上',
  `food_heat` int DEFAULT NULL COMMENT '您炒菜时经常把油烧得很热后(如已冒烟)才开始烹调吗？1是2否',
  `food_drink` int DEFAULT NULL COMMENT '您喝酒吗？(这里喝酒是指平均每周饮酒1次以上）1喝酒2不喝',
  `food_drink_times` int DEFAULT NULL COMMENT '您每周喝几次酒？1，1-2次 2，3-5次 3，>5次',
  `food_drink_spirit` int DEFAULT NULL COMMENT '您平均每次喝多少酒？1， 少于1两 2，1-2两 3，2-3两 4，3两以上',
  `food_drink_beer` int DEFAULT NULL COMMENT '啤酒 1,少于300毫升 2,300-600毫升 3,600-900毫升 4,900毫升以上',
  `food_drink_wine` int DEFAULT NULL COMMENT '葡萄酒或黄酒1,少于100毫升 2,100-200毫升 3,200-300毫升',
  `seafood_cnt` int DEFAULT NULL COMMENT '您每周吃虾、蟹、蛤蜊等海鲜次数是1：几乎不吃:2：1~2次:3：2~4次4：4次以上',
  `drink_tea_cnt` int DEFAULT NULL COMMENT '您每天饮茶的次数是1：几乎不饮茶2：不超过1杯3：1杯以上',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `question_serialNum` (`question_serialNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷_饮食习惯';

CREATE TABLE `yh_question_male` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '请求序列号',
  `urinary_tract_infection` int NOT NULL DEFAULT '0' COMMENT '尿路感染（男）',
  `urine_symptom` int NOT NULL DEFAULT '0' COMMENT '尿频、尿痛、尿不尽、排尿困难等症状（男）',
  `hematuresis` int NOT NULL DEFAULT '0' COMMENT '尿血或尿路结石（男）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='男性健康问卷';

CREATE TABLE `yh_question_my_disease` (
  `id` int NOT NULL,
  `request_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `hw_check_xt_exception` int DEFAULT NULL COMMENT '红外检测红外检测血糖结果1:正常2:异常',
  `chd_his_disflag` int DEFAULT NULL COMMENT '冠心病1:患过2:没患过',
  `chd_his_disYear` int DEFAULT NULL,
  `chd_his_dis_Month` int DEFAULT NULL,
  `mi_his_disFlag` int DEFAULT NULL COMMENT '严重心律失常1:患过2:没患过',
  `mi_his_disYear` int DEFAULT NULL,
  `mi_his_disMonth` int DEFAULT NULL,
  `angina_his_disFlag` int DEFAULT NULL COMMENT '不稳定心绞痛1:患过2:没患过',
  `angina_his_disYear` int DEFAULT NULL,
  `angina_his_disMonth` int DEFAULT NULL,
  `arrhythmia_his_disFlag` int DEFAULT NULL COMMENT '严重心律失调',
  `arrhythmia_his_disYear` int DEFAULT NULL,
  `arrhythmia_his_disMonth` int DEFAULT NULL,
  `ushf_his_disFlag` int DEFAULT NULL COMMENT '未控制的有症状的',
  `ushf_his_disYear` int DEFAULT NULL,
  `ushf_his_disMonth` int DEFAULT NULL,
  `lvd_his_disFlag` int DEFAULT NULL COMMENT '左心室肥大',
  `lvd_his_disYear` int DEFAULT NULL,
  `lvd_his_disMonth` int DEFAULT NULL,
  `af_his_disFlag` int DEFAULT NULL COMMENT '房颤',
  `af_his_disYear3` int DEFAULT NULL,
  `af_his_disMonth3` int DEFAULT NULL,
  `ace_his_disFlag` int DEFAULT NULL COMMENT '其他急性心脏事件',
  `ace_his_disYear` int DEFAULT NULL,
  `ace_his_disMonth` int DEFAULT NULL,
  `stroke_his_disFlag` int DEFAULT NULL COMMENT '脑卒中',
  `stroke_his_disYear` int DEFAULT NULL,
  `stroke_his_disMonth` int DEFAULT NULL,
  `hbp_his_disFlag` int DEFAULT NULL COMMENT '高血压',
  `hbp_his_disYear` int DEFAULT NULL,
  `hbp_his_disMonth` int DEFAULT NULL,
  `diab1_his_disFlag` int DEFAULT NULL COMMENT '1型糖尿病',
  `diab1_his_disYear` int DEFAULT NULL,
  `diab1_his_disMonth` int DEFAULT NULL,
  `diab2_his_disFlag` int DEFAULT NULL COMMENT '2型糖尿病',
  `diab2_his_disYear` int DEFAULT NULL,
  `diab2_his_disMonth` int DEFAULT NULL,
  `bf_his_disFlag` int DEFAULT NULL COMMENT '血脂异常',
  `bf_his_disYear` int DEFAULT NULL,
  `bf_his_disMonth` int DEFAULT NULL,
  `hua_his_disFlag` int DEFAULT NULL COMMENT '高尿酸血症',
  `hua_his_disYear` int DEFAULT NULL,
  `hua_his_disMonth` int DEFAULT NULL,
  `gae_his_disFlag` int DEFAULT NULL COMMENT '痛风急性发作期',
  `gae_his_disYear` int DEFAULT NULL,
  `gae_his_disMonth` int DEFAULT NULL,
  `gna_his_disFlag` int DEFAULT NULL COMMENT '痛风非急性期',
  `gna_his_disYear` int DEFAULT NULL,
  `gna_his_disMonth` int DEFAULT NULL,
  `f1_his_disFlag` int DEFAULT NULL COMMENT '脂肪肝',
  `f1_his_disYear` int DEFAULT NULL,
  `f1_his_disMonth` int DEFAULT NULL,
  `bronch_his_disFlag` int DEFAULT NULL COMMENT '慢性支气管炎',
  `bronch_his_disYear` int DEFAULT NULL,
  `bronch_his_disMonth` int DEFAULT NULL,
  `emphy_his_disFlag` int DEFAULT NULL COMMENT '肺气肿',
  `emphy_his_disYear` int DEFAULT NULL,
  `emphy_his_disMonth` int DEFAULT NULL,
  `lc_his_disFlag` int DEFAULT NULL COMMENT '肺癌',
  `lc_his_disYear` int DEFAULT NULL,
  `lc_his_disMonth` int DEFAULT NULL,
  `pc_his_disFlag` int DEFAULT NULL COMMENT '前列腺癌',
  `pc_his_disYear` int DEFAULT NULL,
  `pc_his_disMonth` int DEFAULT NULL,
  `cancer_his_disFlag` int DEFAULT NULL COMMENT '其他癌症',
  `cancer_his_disYear` int DEFAULT NULL,
  `cancer_his_disMonth` int DEFAULT NULL,
  `ra_his_disFlag` int DEFAULT NULL COMMENT '类风湿性关节炎',
  `ra_his_disYear` int DEFAULT NULL,
  `ra_his_disMonth` int DEFAULT NULL,
  `sport_his_disFlag` int DEFAULT NULL COMMENT '其他限制运动的疾病（如关节炎、足部溃疡、肌肉韧带损伤等）',
  `sport_his_disYear` int DEFAULT NULL,
  `sport_his_disMonth` int DEFAULT NULL,
  `porstate_his_disFlag` int DEFAULT NULL COMMENT '前列腺炎或肥大',
  `porstate_disYear` int DEFAULT NULL,
  `porstate_disMonth` int DEFAULT NULL,
  `begind_his_disFlag` int DEFAULT NULL COMMENT '良性乳腺疾病',
  `begind_disYear` int DEFAULT NULL,
  `begind_disMonth` int DEFAULT NULL,
  `secondary_his_disFlag` int DEFAULT NULL COMMENT '引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等）',
  `secondary_disYear` int DEFAULT NULL,
  `secondary_disMonth` int DEFAULT NULL,
  `fracture_his_disFlag` int DEFAULT NULL COMMENT '既往骨折',
  `fracture_disYear` int DEFAULT NULL,
  `fracture_disMonth` int DEFAULT NULL,
  `op_his_disFlag` int DEFAULT NULL COMMENT '骨质疏松症',
  `op_disYear` int DEFAULT NULL,
  `op_disMonth` int DEFAULT NULL,
  `active_his_disFlag` int DEFAULT NULL COMMENT '疾病急性期或活动期（如发热、疼痛或其他不适等）',
  `active_disYear` int DEFAULT NULL,
  `active_disMonth` int DEFAULT NULL,
  `bc_his_disFlag` int DEFAULT NULL COMMENT '乳腺癌',
  `bc_his_disYear` int DEFAULT NULL,
  `bc_his_disMonth` int DEFAULT NULL,
  `other_med_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他药物',
  `arterial_status` int DEFAULT NULL COMMENT '是否患过动脉硬化/狭窄/闭塞 1,是   2,否',
  `decline_his_disFlag` int DEFAULT NULL COMMENT '是否曾患过肺结核1,是   2,否',
  `decline_his_disYear` int DEFAULT NULL COMMENT '曾患过肺结核_年',
  `decline_his_disMonth` int DEFAULT NULL COMMENT '曾患过肺结核_月',
  `other_med_flag` int DEFAULT NULL,
  `med_use_flag` int DEFAULT NULL,
  `med_hbp_flag` int DEFAULT NULL,
  `med_ach_flag` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人疾病问卷表';

CREATE TABLE `yh_question_pain_title` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `title_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '题目图片说明',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1',
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2',
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3',
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '4',
  `choose5` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose5_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '5',
  `choose6` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose6_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '是否根据其他题目来判断0-有子项；-1-没有子项；子项是为0的id',
  `pain_id` int DEFAULT '0' COMMENT '疼痛部位id ;为0代表共有题',
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷题目表';

CREATE TABLE `yh_question_risk_title_front` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `question_item_name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '对应的字段',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1',
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2',
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '3',
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '4',
  `choose5` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose5_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '5',
  `choose6` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose6_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0',
  `parent_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '是否根据其他题目来判断0-有子项；-1-没有子项；子项是为0的id',
  `diseaseId` int DEFAULT '0' COMMENT '疾病id',
  `del_flag` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='危险因素题目表';

CREATE TABLE `yh_question_sleep` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_type` int NOT NULL DEFAULT '0' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `question_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '问卷标题',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠问卷';

CREATE TABLE `yh_question_smoke` (
  `id` int NOT NULL,
  `question_serialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '请求序列号',
  `smoke_status_flag` int DEFAULT NULL COMMENT '是否吸烟1,吸烟2，不吸烟3，已戒烟',
  `smoke_begin_age` int DEFAULT NULL COMMENT '开始吸烟的年龄数字1~100，定位在10',
  `smoke_first_time` int DEFAULT NULL COMMENT '您早晨醒来后多长时间吸第一支烟？1,<6分钟2，6~30分钟3，31~60分钟4，>60分钟',
  `smoke_first_more` int DEFAULT NULL COMMENT '您早上醒来后第一个小时是否比其他时间吸烟多1是2否',
  `smoke_give` int DEFAULT NULL COMMENT '您认为哪支烟您最不愿意放弃1,早上第一支烟2,其他时间',
  `somke_sick_flag` int DEFAULT NULL COMMENT '您卧病在床是仍旧吸烟吗1是2否',
  `smoke_end_age` int DEFAULT NULL COMMENT '开始戒烟的年龄数字1~100，定位在10',
  `somke_second` int DEFAULT NULL COMMENT '无论在家还是工作场所，您经常吸二手烟吗1是2否',
  `add_time` datetime(6) DEFAULT NULL,
  `smoke_controller` int DEFAULT NULL COMMENT '您是否在许多不准吸烟的场所很难控制吸烟的需求1是2否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='吸烟相关问卷表';

CREATE TABLE `yh_question_smoking` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_type` int NOT NULL DEFAULT '0' COMMENT '题目类型(1-单选题2-填空题3-多选题)',
  `question_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '问卷标题',
  `choose1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose1_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose2_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose3_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `choose4_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='吸烟问卷';

CREATE TABLE `yh_question_spirit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `request_searialNum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `sleep_quality` int DEFAULT NULL COMMENT '睡眠状态1,好  2,一般  3,差',
  `sleep_time` int DEFAULT NULL COMMENT '您一般晚上几点入睡？1,22点前  2,22-23点  3,23-0点4,0点以后',
  `bedtime` int DEFAULT NULL COMMENT '睡眠时间1:每天8小时以上2:每天6~8小时3:每天不足6小时',
  `psy_nervous` int DEFAULT NULL COMMENT '最近两周，您感到精神紧张，很难放松吗？1,没有2,偶尔3,经常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `Index 2` (`request_searialNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='精神状态及睡眠';

CREATE TABLE `yh_question_sport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_in` int DEFAULT NULL,
  `question_serialNum` int DEFAULT NULL COMMENT '请求序列号',
  `sport_model` int DEFAULT NULL COMMENT '您常采用的运动锻炼方式（可多选）s01,散步s02，漫步s03，游泳s04，骑自行车s05，爬楼梯s06,球类运动',
  `sport_freg` int DEFAULT NULL COMMENT '一般情况下您参加运动锻炼的频率是？1，	每月少于1次2，	每月至少1次，但每周不足1次3，	每周1-2次4，	每周3-4次5，每周5次以上',
  `sport_time` int DEFAULT NULL COMMENT '一般情况下您每次锻炼的持续时间是？	1，	少于30分钟2，30-60分钟3，60分钟以上1，	少于30分钟  ',
  `sport_feel` int DEFAULT NULL COMMENT '一般情况下您每次运动锻炼时的身体感受？1，呼吸、心跳与不锻炼时比，变化不大 2，呼吸、心跳加快，微微出汗  3，呼吸急促，心跳明显加快，出汗较多',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `question_serialNum` (`question_serialNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='问卷_运动';

CREATE TABLE `yh_remind_drug_list` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '药品序号',
  `remind_id` int DEFAULT NULL COMMENT '提醒id',
  `drug_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药瓶名称',
  `img_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药品图片',
  `take_number` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服用数量',
  `drug_unit` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用单位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT='用药提醒药品表';

CREATE TABLE `yh_remind_number` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '次数序号',
  `remind_id` int DEFAULT NULL COMMENT '提醒id',
  `remind_time` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提醒时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT COMMENT='用药提醒次数表';

CREATE TABLE `yh_result_comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编号',
  `detection_date` datetime DEFAULT NULL COMMENT '检测时间',
  `head` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头部',
  `body` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '躯干',
  `shoulder` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '肩部',
  `hip` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '臀部',
  `leg` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '腿部',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='体态检测数据表';

CREATE TABLE `yh_return` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户手机号码',
  `gender` int DEFAULT NULL COMMENT '用户性别',
  `next_return` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '下次回访时间',
  `recently_return` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '近日回访时间',
  `satisfaction` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '满意度',
  `return_manner` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '回访方式',
  `return_content` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '回访内容',
  `return_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '回访人员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='随访记录表';

CREATE TABLE `yh_riskurl_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评估地址',
  `status` tinyint DEFAULT '1' COMMENT '状态   0：禁用   1：可用',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='客户评估地址维护表';

CREATE TABLE `yh_score_event_category` (
  `scoreEvent_ID` int NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '积分变动类型中文名',
  `score` int DEFAULT '0' COMMENT '积分变动分值，可以是加分也可以是减分',
  `event_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '积分类型标志',
  `type` int DEFAULT NULL COMMENT '1每日任务 2每月任务',
  `continuous_status` int DEFAULT NULL COMMENT '是否开启连续奖励 1开启 2 关闭',
  `putaway` int DEFAULT NULL COMMENT '上架1 下架2',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '操作人',
  `standby` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用字段',
  `standby2` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用字段',
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`scoreEvent_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='积分事件规则表';

CREATE TABLE `yh_score_event_category_copy2` (
  `scoreEvent_ID` int NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '积分变动类型中文名',
  `score` int DEFAULT '0' COMMENT '积分变动分值，可以是加分也可以是减分',
  `event_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '积分类型标志',
  `type` int DEFAULT NULL COMMENT '1每日任务 2每月任务',
  `continuous_status` int DEFAULT NULL COMMENT '是否开启连续奖励 1开启 2 关闭',
  `putaway` int DEFAULT NULL COMMENT '上架1 下架2',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `creator` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '操作人',
  `standby` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用字段',
  `standby2` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用字段',
  `org_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`scoreEvent_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='积分事件规则表';

CREATE TABLE `yh_setting_log` (
  `yh_logo_id` int NOT NULL AUTO_INCREMENT COMMENT 'logo_id',
  `yh_logo_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'logo名字',
  `yh_logo_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'logo地址',
  PRIMARY KEY (`yh_logo_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_sleep_music` (
  `music_id` int DEFAULT NULL,
  `music_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐标题',
  `music_video` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐链接',
  `music_img` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐封面',
  `music_listennum` int NOT NULL DEFAULT '0' COMMENT '收听次数',
  `music_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠音乐';

CREATE TABLE `yh_sleep_music_meal` (
  `meal_id` int DEFAULT NULL,
  `meal_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐标题',
  `music_ids` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐id多个以;分割',
  `meal_img` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '套餐封面',
  `meal_listennum` int NOT NULL DEFAULT '0' COMMENT '收听次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠套餐音乐';

CREATE TABLE `yh_sleep_report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '评估人code',
  `report_desc` varchar(5000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '报告描述',
  `report_score` int DEFAULT NULL,
  `report_level` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '报告等级',
  `report_queselect` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '问卷答案',
  `qtime` timestamp NULL DEFAULT NULL COMMENT '评估时间',
  PRIMARY KEY (`report_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='睡眠评估结果';

CREATE TABLE `yh_sleep_user_diary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户code',
  `sleep_time` timestamp NULL DEFAULT NULL COMMENT '入睡时间',
  `wake_time` timestamp NULL DEFAULT NULL COMMENT '醒来时间',
  `diary_desc` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠时间评估结果',
  `sleep_diarytime` date DEFAULT NULL COMMENT '睡眠记录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户睡眠记录';

CREATE TABLE `yh_sleep_user_musiclist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `music_id` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '音乐id',
  `time` timestamp NULL DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户睡眠音乐播放列表';

CREATE TABLE `yh_smoke_craving` (
  `craving_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `craving_Time` datetime DEFAULT NULL COMMENT '烟瘾新增时间',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`craving_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='烟瘾记录';

CREATE TABLE `yh_smoking_comment` (
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `pid` int unsigned DEFAULT '0' COMMENT '回复楼中楼编号',
  `smoking_diary_id` int unsigned NOT NULL COMMENT '戒烟日记id',
  `usercode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户编号',
  `comment_Info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `status` tinyint DEFAULT '1' COMMENT '类型(-1:健康课堂,2:健康习惯)',
  `comment_time` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '创建时间',
  `comment_img` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评论图片',
  `stop_smoking_day` int DEFAULT '0' COMMENT '戒烟的天数',
  `like_num` int DEFAULT '0' COMMENT '点赞数',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='戒烟日记评论表';

CREATE TABLE `yh_smoking_diary` (
  `diary_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `diary_title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '标题',
  `diary_user` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '发布人',
  `instruction` varchar(18900) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '概要',
  `read_count` int unsigned DEFAULT '1' COMMENT '点赞数',
  `diary_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `state` int DEFAULT NULL COMMENT '(1,发布2，下线)',
  PRIMARY KEY (`diary_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='戒烟日记';

CREATE TABLE `yh_smoking_diarylike` (
  `diary_like_id` int NOT NULL AUTO_INCREMENT,
  `usercode` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `likediary_id` int DEFAULT NULL,
  `is_like` int DEFAULT NULL,
  PRIMARY KEY (`diary_like_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_smoking_report` (
  `smoking_report_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `smoking_report_desc` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '报告描述',
  `smoking_report_score` int DEFAULT NULL COMMENT '评估分数',
  `smoking_report_level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '报告等级',
  `smoking_report_queselect` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '问卷答案',
  `qtime` datetime DEFAULT NULL COMMENT '评估时间',
  PRIMARY KEY (`smoking_report_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_smoking_secret` (
  `secret_id` int NOT NULL AUTO_INCREMENT,
  `smokinginfo_id` int unsigned DEFAULT NULL COMMENT '戒烟秘笈章节目录id',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `secret_Time` datetime DEFAULT NULL COMMENT '记录时间',
  `smokinginfo_progress` int NOT NULL DEFAULT '0' COMMENT '戒烟秘笈进度',
  `smokinginfochi_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '戒烟秘笈文章id',
  PRIMARY KEY (`secret_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='戒烟秘籍记录';

CREATE TABLE `yh_sport_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `cate_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `cate_times` int DEFAULT NULL COMMENT '推荐周运动次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='运动分类';

CREATE TABLE `yh_sport_collect` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `sport_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动收藏表';

CREATE TABLE `yh_sport_course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '锻炼部位',
  `course_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '课程名称',
  `course_strength` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '课程强度',
  `course_content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '课程描述',
  `course_totalDay` int DEFAULT '0' COMMENT '课程总天数',
  `course_composing` int DEFAULT '0' COMMENT '组成形式（0：动作间断休息；1：连续动作不休息）',
  `course_averageDaily` int DEFAULT '0' COMMENT '课程日均时长(分钟)',
  `course_kcal` float NOT NULL DEFAULT '0.083' COMMENT '课程总消耗卡路里',
  `course_img` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '课程封面图片',
  `course_number` int DEFAULT '0' COMMENT '课程参加人数',
  `is_del` int NOT NULL DEFAULT '2' COMMENT '逻辑删除(2显示，1删除)',
  `course_use` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '2' COMMENT '课程用途（0_1;1_1）第1个数值，0：懒人；1：普通 第2个数值，0:不限；1：女性；2：男性',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动课程表';

CREATE TABLE `yh_sport_course_detail` (
  `cdetail_id` int NOT NULL AUTO_INCREMENT,
  `cdetail_day` int DEFAULT '0' COMMENT '运动课程天数',
  `cdetail_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '课程详情名称',
  `cdetail_url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '连续动作不休息的视频地址',
  `course_id` int DEFAULT '0' COMMENT '课程id',
  `vicon_ids` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动课程动作库id（多个以";"分割,为空代表休息）',
  `cdetail_totals` int DEFAULT '0' COMMENT '课程动作总数',
  `cdetail_totalKal` int DEFAULT '0' COMMENT '共需消耗总kal',
  `cdetail_totalDuration` int DEFAULT '0' COMMENT '课程总时长(分钟)',
  PRIMARY KEY (`cdetail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动课程详情表';

CREATE TABLE `yh_sport_diary` (
  `u_sport_id` int NOT NULL AUTO_INCREMENT,
  `Entry_Time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '时间',
  `Uweight` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '总卡路里',
  `Tenergy` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '总数',
  `Diary_Details` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动其他记录',
  `Diary_Boutique` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '精品课程',
  `Diary_Recommend` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '推荐运动',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0',
  `remark` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '运动总时间',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '步数',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`u_sport_id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`,`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动记录';

CREATE TABLE `yh_sport_encourage` (
  `zw_id` int NOT NULL AUTO_INCREMENT,
  `ch_id` int DEFAULT NULL COMMENT '挑战赛id',
  `time_add` date DEFAULT NULL COMMENT '增加时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`zw_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='挑战赛助威表';

CREATE TABLE `yh_sport_frequency` (
  `fr_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fr_type` int DEFAULT NULL COMMENT '"推荐来源1“系统自动”3“医师手动”"',
  `fr_frequency` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '推荐频率(1:周二、四;2:周一、三、五;3:周一、二、四、五、七)',
  `fr_dete` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`fr_id`) USING BTREE,
  UNIQUE KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='运动频率表';

CREATE TABLE `yh_sport_goals` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goals_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='锻炼目标\r\n';

CREATE TABLE `yh_sport_motion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sport_type` int DEFAULT '1' COMMENT '（数据字典）动作类型：1 分钟  2 个数',
  `sport_show` int DEFAULT '1' COMMENT '逻辑删除(2显示，1删除)',
  `sport_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作名称',
  `sport_steps` varchar(1500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作步骤',
  `sport_fell` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作感觉',
  `sport_breath` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸节奏',
  `follow_second` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '练习次数或时间（1为次数，2为秒，1_5,2_20）',
  `sport_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作图片',
  `sport_gif` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'gif动图',
  `sport_video` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作视频',
  `sport_content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作要领说明',
  `sport_explain_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作要领图（多个;分割）',
  `is_del` int NOT NULL DEFAULT '2' COMMENT '是否显示在动作列表（0：显示；1：不显示）',
  `Kcal` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0.083',
  `hot` int DEFAULT '0' COMMENT '追加次数',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '(1，本公司加的运动 2，加盟商添加的运动)',
  `sport_classity` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '动作分类',
  `sport_place` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '锻炼部位',
  `sport_goals` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '锻炼目标',
  `sport_strength` int DEFAULT '1' COMMENT '动作强度',
  `sport_videotime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '视频时长（单位：秒）',
  `sport_totalkcal` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '课程完成该动作的总能耗（Kcal）',
  `sport_numberduration` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '1' COMMENT '个数时长',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='运动动作表';

CREATE TABLE `yh_sport_part` (
  `id` int NOT NULL AUTO_INCREMENT,
  `part_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='锻炼部位';

CREATE TABLE `yh_sport_recommend_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `record_data` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0',
  `diseaseid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '疾病id',
  `setmealid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '套餐id',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '天数（第几天）',
  `finishid` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作完成id',
  `record_type` int DEFAULT '1' COMMENT '（1,执行中 2，已完成 ）',
  `record_kay` int DEFAULT NULL COMMENT '千卡',
  `record_mode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '推荐方式',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总共多少天',
  `sport_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动套餐名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动推荐记录表';

CREATE TABLE `yh_sport_setmeal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `disease_code` int DEFAULT '1' COMMENT '所属疾病ID',
  `setmeal_mame` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '套餐名称:高血压康复训练',
  `subject_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '所属主题id',
  `setmeal_img` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主题图片',
  `oneself` int DEFAULT '1' COMMENT '(1，本公司增加的套餐  2，加盟商增加的套餐)',
  `describe` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '描述',
  `exercise_frequency` int DEFAULT NULL COMMENT '运动频率',
  `recommend_benchmarks` int DEFAULT NULL COMMENT '推荐标准',
  `setmeal_type` int DEFAULT NULL,
  `diseaseid` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动套餐表';

CREATE TABLE `yh_sport_strength` (
  `id` int NOT NULL AUTO_INCREMENT,
  `strength_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='动作强度\r\n';

CREATE TABLE `yh_sport_subject` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject_type` int DEFAULT '1' COMMENT '（数据字典）主题类型：1-健康康复，2-完美身材',
  `subject_img` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主题图片',
  `subject_motion` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '包含动作ID，逗号隔开',
  `subject_details` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '主题介绍',
  `subject_num` int DEFAULT NULL COMMENT '动作组成的数量',
  `remark` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当天消耗热量',
  `subject_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '8.5' COMMENT '运动时长（分钟）',
  `is_del` int NOT NULL DEFAULT '2' COMMENT '逻辑删除（2显示，1删除）',
  `subject_days` int DEFAULT NULL COMMENT '运动第几天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动主题表';

CREATE TABLE `yh_sport_vicon` (
  `vicon_id` int NOT NULL AUTO_INCREMENT,
  `vicon_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '锻炼部位',
  `vicon_video` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '动作视频',
  `vicon_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作名称',
  `vicon_img` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作封面图片',
  `vicon_strength` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作强度',
  `vicon_content` varchar(800) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作要领说明',
  `vicon_explain_img` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '动作要领图（多个;分割）',
  `vicon_muscle_img` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌肉示意图（多个;分割）',
  `is_del` int NOT NULL DEFAULT '2' COMMENT '逻辑删除(2显示，1删除)',
  PRIMARY KEY (`vicon_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动课程动作库';

CREATE TABLE `yh_sport_weekly` (
  `we_id` int NOT NULL AUTO_INCREMENT,
  `nut` int DEFAULT NULL COMMENT '年龄',
  `userName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '姓名',
  `age` int DEFAULT NULL COMMENT '性别',
  `userCode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `uweight` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '平均摄入推荐',
  `tenergy` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '平均摄入实际',
  `frequency` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '记录频率',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '达标天数',
  `array` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '能耗统计图',
  `walk` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '步行',
  `recommend` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '推荐',
  `boutique` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '精品',
  `rests` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '其他',
  `aerobic` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '有氧',
  `anaerobic` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '无氧',
  `pliable` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '柔韧',
  `arthrosis` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关节',
  `walkpercent` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `recommendpercent` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `boutiquepercent` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `restspercent` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `durationnum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '运动分钟',
  `startTime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '开始时间',
  `endTime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`we_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='运动周报存储';

CREATE TABLE `yh_sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'value',
  `status` tinyint DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `key` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='云存储配置信息表';

CREATE TABLE `yh_system_update` (
  `update_id` int NOT NULL AUTO_INCREMENT,
  `update_begintime` datetime DEFAULT NULL COMMENT '系统升级时间',
  `update_endtime` datetime DEFAULT NULL,
  `update_title` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `update_content` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '0-草稿，1-已发布，2-已过期',
  PRIMARY KEY (`update_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='代码生成业务表';

CREATE TABLE `yh_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='代码生成业务表字段';

CREATE TABLE `yh_template_detail` (
  `details_id` int NOT NULL AUTO_INCREMENT,
  `execute_date` date DEFAULT NULL COMMENT '执行时间',
  `service_way` int DEFAULT NULL COMMENT '服务方式',
  `plan_count` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务内容',
  `count_prompt` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '内容提示',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '计划执行的人',
  `var_count` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增服务类型',
  PRIMARY KEY (`details_id`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE,
  KEY `execute_date` (`execute_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划执行详情表';

CREATE TABLE `yh_template_detail_copy1` (
  `details_id` int NOT NULL AUTO_INCREMENT,
  `execute_date` date DEFAULT NULL COMMENT '执行时间',
  `service_way` int DEFAULT NULL COMMENT '服务方式',
  `plan_count` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务内容',
  `count_prompt` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '内容提示',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '计划执行的人',
  `var_count` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增服务类型',
  PRIMARY KEY (`details_id`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE,
  KEY `execute_date` (`execute_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划执行详情表';

CREATE TABLE `yh_template_execution` (
  `execution_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `group_id` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `plan_count` int DEFAULT NULL COMMENT '剩余计划',
  `last_plan_time` datetime DEFAULT NULL COMMENT '最后计划日期',
  PRIMARY KEY (`execution_id`) USING BTREE,
  KEY `user_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划执行总表';

CREATE TABLE `yh_template_message` (
  `T_M_ID` int NOT NULL AUTO_INCREMENT,
  `T_M_Content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '对应内容',
  PRIMARY KEY (`T_M_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='消息模板对应内容';

CREATE TABLE `yh_template_training` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Template_Id` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Training_Id` int NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `RefWell_Training_Methods67` (`Training_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='运动方案模版与周期对应表';

CREATE TABLE `yh_template_type` (
  `T_ID` int NOT NULL AUTO_INCREMENT,
  `T_Type` int NOT NULL COMMENT '类型',
  `T_Classify` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板分类',
  `T_TemContent` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板内容',
  `T_Template_Id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`T_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='消息模板示列内容';

CREATE TABLE `yh_temporary_programs_template` (
  `programs_id` int NOT NULL AUTO_INCREMENT COMMENT '干预计划模板id',
  `Intervention_id` int DEFAULT NULL,
  `programs_weeks` int DEFAULT NULL COMMENT '第几周',
  `programs_days` int DEFAULT NULL COMMENT '第几天',
  `service_way` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务方式1.电话回访，2.微信回访3.小程序聊天回访4.上门回访,5.不限',
  `plan_content` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '计划内容',
  `content_prompt` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '内容提示',
  `remark` int DEFAULT NULL,
  `usercode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`programs_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='干预计划模板';

CREATE TABLE `yh_temproary_template` (
  `temporary_id` int NOT NULL AUTO_INCREMENT,
  `start_time` datetime DEFAULT NULL COMMENT '执行时间',
  `service_way` int DEFAULT NULL COMMENT '服务方式',
  `plan_content` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '计划内容',
  `content_prompt` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '服务提示',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '哪个医生正在操作',
  `programs_weeks` int DEFAULT NULL,
  `programs_days` int DEFAULT NULL,
  `Intervention_id` int DEFAULT NULL,
  `LAY_CHECKED` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'true',
  PRIMARY KEY (`temporary_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='临时计划表';

CREATE TABLE `yh_threshold_dic` (
  `dic_id` int NOT NULL AUTO_INCREMENT COMMENT '检测异常id',
  `threshold_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '检测异常名称',
  PRIMARY KEY (`dic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_tjhk_arteriosclerosis` (
  `aId` int NOT NULL AUTO_INCREMENT,
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisInfo` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断描述',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`aId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='动脉硬化检测';

CREATE TABLE `yh_tjhk_blood_lipid_test` (
  `blood_lipid_id` int NOT NULL AUTO_INCREMENT,
  `T_CHOL` float DEFAULT NULL COMMENT '总胆固醇',
  `tchol_normalor_abnormal` int DEFAULT '0' COMMENT '参考值',
  `tchol_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tchol_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT ' 0正常1异常',
  `TG` float DEFAULT NULL COMMENT '甘油三脂',
  `tg_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `tg_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tg_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `HDL` float DEFAULT NULL COMMENT '高密度脂蛋白',
  `hdl_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `hdl_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `hdl_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `LDL` float DEFAULT NULL COMMENT '低密度脂蛋白',
  `ldl_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ldl_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ldl_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `apoa1` float DEFAULT NULL COMMENT '载脂蛋白A1',
  `apoa1_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `apoa1_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `apoa1_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `apob` float DEFAULT NULL COMMENT '载脂蛋白B',
  `apob_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `apob_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `apob_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `alp` float DEFAULT NULL COMMENT '脂蛋白aLP',
  `alp_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `alp_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `alp_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`blood_lipid_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='血脂检测';

CREATE TABLE `yh_tjhk_blood_sugar` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `glycos` float DEFAULT NULL COMMENT '糖化血红蛋白',
  `glycosAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `glycosCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `glycosReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `glucose` float DEFAULT NULL COMMENT '葡萄糖',
  `glucoseAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `glucoseCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `glucoseReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `tolerance` float DEFAULT NULL COMMENT '葡萄糖耐量试验',
  `toleranceAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `toleranceCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `toleranceReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='血糖';

CREATE TABLE `yh_tjhk_blood_test` (
  `rid` int NOT NULL AUTO_INCREMENT COMMENT '血常规',
  `white` float DEFAULT NULL COMMENT '白细胞',
  `whiteAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `whiteCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `whiteReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `neut` float DEFAULT NULL COMMENT '中性粒细胞',
  `neutAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `neutCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `neutReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `lymp` float DEFAULT NULL COMMENT '淋巴细胞',
  `lympAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `lympCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `lympReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `mono` float DEFAULT NULL COMMENT '单核细胞',
  `monoAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `monoCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `monoReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `eosi` float DEFAULT NULL COMMENT '嗜 酸 性粒细胞',
  `eosiAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `eosiCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `eosiReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `baso` float DEFAULT NULL COMMENT '嗜 碱 性粒细胞',
  `basoAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `basoCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `basoReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `red` float DEFAULT NULL COMMENT '红细胞',
  `redAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `redCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `redReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `hem` float DEFAULT NULL COMMENT '血红蛋白',
  `hemAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `hemCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `hemReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `hema` float DEFAULT NULL COMMENT '红细胞比积',
  `hemaAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `hemaCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `hemaReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `pla` float DEFAULT NULL COMMENT '血小板',
  `plaAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `plaCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `plaReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `mean` float DEFAULT NULL COMMENT '平均红细胞体积',
  `meanAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `meanCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `meanReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `meancor` float DEFAULT NULL COMMENT '平均红细胞血红蛋白含量',
  `meancorAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `meancorCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `meancorReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  `concen` float DEFAULT NULL COMMENT '平均红细胞血红蛋白浓度',
  `concenAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `concenCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '单位',
  `concenReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '参考值',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='血常规';

CREATE TABLE `yh_tjhk_ct` (
  `ct_Id` int NOT NULL AUTO_INCREMENT,
  `typeid` int DEFAULT NULL COMMENT '1.CT胸部平扫2.头部CT',
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisinfo` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断描述',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `Is_Del` int DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`ct_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='CT检查 新';

CREATE TABLE `yh_tjhk_dr` (
  `dr_Id` int NOT NULL AUTO_INCREMENT,
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisInfo` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断描述',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`dr_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='胸 片 新';

CREATE TABLE `yh_tjhk_gastric_function` (
  `gastric_id` int NOT NULL AUTO_INCREMENT,
  `GAS` float DEFAULT NULL COMMENT '胃泌素',
  `gas_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `gas_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `gas_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `breathtest` float DEFAULT NULL COMMENT 'C14/C13呼气试验',
  `breathtest_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `breathtest_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `breathtest_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`gastric_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='胃部功能检查';

CREATE TABLE `yh_tjhk_gynecology_department` (
  `gynecology_id` int NOT NULL AUTO_INCREMENT,
  `TCT` float DEFAULT NULL COMMENT '液基超薄细胞检测（已婚）',
  `tct_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `tct_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tct_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `HPV_DNA` float DEFAULT NULL COMMENT '人乳头状瘤病毒检查 （已婚）',
  `hpv_dna_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `hpv_dna_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `hpv_dna_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`gynecology_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='妇科检查';

CREATE TABLE `yh_tjhk_liver_function3` (
  `lid` int NOT NULL AUTO_INCREMENT,
  `gluta` float DEFAULT NULL COMMENT '谷氨酰转肽酶',
  `glutaAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `glutaCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `glutaReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `alkali` float DEFAULT NULL COMMENT '碱性磷酸酶',
  `alkaliAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `alkaliCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `alkaliReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `total` float DEFAULT NULL COMMENT '总蛋白',
  `totalAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `totalCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `totalReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `albumin` float DEFAULT NULL COMMENT '白蛋白',
  `albuminAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `albuminCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `albuminReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `globulin` float DEFAULT NULL COMMENT '球蛋白',
  `globulinAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `globulinCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `globulinReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `AlbAndglob` float DEFAULT NULL COMMENT '白蛋白/球蛋白',
  `AlbAndglobAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `AlbAndglobCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `AlbAndglobReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `bilirubin` float DEFAULT NULL COMMENT '总胆红素',
  `bilirubinAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `bilirubinCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `bilirubinReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `direct` float DEFAULT NULL COMMENT '直接胆红素',
  `directAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `directCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `directReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Indirect` float DEFAULT NULL COMMENT '间接胆红素',
  `IndirectAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `IndirectCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `IndirectReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `alanine` float DEFAULT NULL COMMENT '丙氨酸氨基转氨酶',
  `alanineAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `alanineCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `alanineReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `aspar` float DEFAULT NULL COMMENT '天门冬氨酸氨基转移酶',
  `asparAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `asparCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `asparReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`lid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE `yh_tjhk_ovarian_function` (
  `ovarianfunction_id` int NOT NULL AUTO_INCREMENT,
  `AMH` float DEFAULT NULL COMMENT '抗苗勒氏管激素',
  `amh_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `amh_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `amh_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`ovarianfunction_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='卵巢功能检测';

CREATE TABLE `yh_tjhk_renal_function3` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `nitrogen` float DEFAULT NULL COMMENT '血尿素氮',
  `nitrogenAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `nitrogenCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `nitrogenReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `creatinine` float DEFAULT NULL COMMENT '中性粒细胞',
  `creatinineAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `creatinineCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `creatinineReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `cystatinc` float DEFAULT NULL COMMENT '血清胱抑素C',
  `cystatincAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `cystatincCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `cystatincReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `acid` float DEFAULT NULL COMMENT '尿酸',
  `acidAberrant` int DEFAULT '0' COMMENT '0正常1异常',
  `acidCompany` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `acidReference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='肾功能';

CREATE TABLE `yh_tjhk_sex_hormone` (
  `sexhormone_id` int NOT NULL AUTO_INCREMENT,
  `PRL` float DEFAULT NULL COMMENT '泌乳素',
  `prl_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `prl_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `prl_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `FSH` float DEFAULT NULL COMMENT '促卵泡刺激素',
  `fsh_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `fsh_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `fsh_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `LH` float DEFAULT NULL COMMENT '促黄体生成素',
  `lh_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `lh_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `lh_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `TTE` float DEFAULT NULL COMMENT '睾酮',
  `tte_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `tte_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tte_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `PGN` float DEFAULT NULL COMMENT '孕酮',
  `pgn_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `pgn_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `pgn_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `E2` float DEFAULT NULL COMMENT '雌二醇',
  `e2_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `e2_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `e2_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`sexhormone_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='性激素六项';

CREATE TABLE `yh_tjhk_thyroid` (
  `thyroid_id` int NOT NULL AUTO_INCREMENT,
  `T3` float DEFAULT NULL COMMENT '三碘甲状原氨酸',
  `t3_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `t3_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `t3_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `T4` float DEFAULT NULL COMMENT '甲状腺素',
  `t4_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `t4_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `t4_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `FT3` float DEFAULT NULL COMMENT '游离三碘甲状原氨酸',
  `ft3_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ft3_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ft3_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `FT4` float DEFAULT NULL COMMENT '游离甲状腺素',
  `ft4_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ft4_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ft4_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `TSH` float DEFAULT NULL COMMENT '促甲状腺激素',
  `tsh_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `tsh_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tsh_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`thyroid_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='甲状腺功能五项';

CREATE TABLE `yh_tjhk_tumor_marker` (
  `tumor_marker_id` int NOT NULL AUTO_INCREMENT,
  `CA15-3` float DEFAULT NULL COMMENT '糖链抗原15-3',
  `ca153_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca153_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca153_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CA125` float DEFAULT NULL COMMENT '糖链抗原125',
  `ca125_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca125_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca125_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `AFP` float DEFAULT NULL COMMENT '甲胎蛋白定量',
  `afp_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `afp_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `afp_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CEA` float DEFAULT NULL COMMENT '癌胚抗原定量',
  `cea_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `cea_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `cea_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CA19-9` float DEFAULT NULL COMMENT '癌抗原19-9',
  `ca199_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca199_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca199_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CA-724` float DEFAULT NULL COMMENT '糖链抗原724',
  `ca724_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca724_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca724_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `NSE` float DEFAULT NULL COMMENT '神经元特异性烯醇化酶',
  `nse_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `nse_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `nse_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `SF` float DEFAULT NULL COMMENT '血清铁蛋白',
  `sf_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `sf_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `sf_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CA50` float DEFAULT NULL COMMENT '糖类抗原50',
  `ca50_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca50_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca50_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `CA242` float DEFAULT NULL COMMENT '糖类抗原242',
  `ca242_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `ca242_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `ca242_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `SCC` float DEFAULT NULL COMMENT '鳞状细胞癌抗原',
  `scc_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `scc_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `scc_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `VCA-IgA` float DEFAULT NULL COMMENT '抗EB病毒抗体VCA-IgA',
  `vca_iga_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `vca_iga_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `vca_iga_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `EAIgA` float DEFAULT NULL COMMENT '抗EB病毒抗体EAIgA',
  `eaiga_iga_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `eaiga_iga_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `eaiga_iga_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `T-PSA` float DEFAULT NULL COMMENT '前列腺特异性抗原',
  `tpsa_iga_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `tpsa_iga_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `tpsa_iga_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `F-PSA` float DEFAULT NULL COMMENT '游离前列腺特异性抗原',
  `fpsa_iga_normalor_abnormal` int DEFAULT '0' COMMENT '0正常1异常',
  `fpsa_iga_unit` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '单位',
  `fpsa_iga_reference` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '参考值',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `test_hj` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  PRIMARY KEY (`tumor_marker_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='肿瘤标志物';

CREATE TABLE `yh_tjhk_ultrasonictesting` (
  `ultrasonictesting_Id` int NOT NULL AUTO_INCREMENT,
  `typeid` int DEFAULT NULL COMMENT '1.肝、胆、脾、胰、肾（彩色）2.甲状腺彩超3.心脏彩超4.颈动脉彩超5.子宫及附件彩超 （未婚）6.阴道超声（已婚检测）7.乳腺彩超（双侧）',
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisInfo` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断录入信息',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Entry_user` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '新增人员',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`ultrasonictesting_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='超声检查 新';

CREATE TABLE `yh_user_admin_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `resume` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `regtime` datetime(6) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `user_type` int DEFAULT NULL,
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `user_group` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='管理人员信息表';

CREATE TABLE `yh_user_artery` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sbp` int DEFAULT NULL COMMENT '最大血压  收缩压',
  `dbp` int DEFAULT NULL COMMENT '最小血压 舒张压',
  `Pulse` int DEFAULT NULL COMMENT '脉博，心率',
  `avi` int DEFAULT NULL COMMENT '中心动脉硬度指标',
  `api` int DEFAULT NULL COMMENT '上臂动脉硬度指标',
  `csbp` int DEFAULT NULL COMMENT '中心动脉血压值',
  `capp` int DEFAULT NULL COMMENT '中心动脉脉压值',
  `usesr_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `Is_Del` int DEFAULT '2' COMMENT '1删除2显示',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='动脉硬化（仪器上传）';

CREATE TABLE `yh_user_award_medal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片',
  `grade` int DEFAULT NULL COMMENT '勋章等级',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '勋章名称',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `hab_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '外键id',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '增加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户奖励勋章';

CREATE TABLE `yh_user_base_info` (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `user_type` int DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `birth_date` datetime(6) DEFAULT NULL,
  `real_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `enrol_card_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `last_login_time` datetime(6) DEFAULT NULL,
  `doc_userid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户基础信息表';

CREATE TABLE `yh_user_bloodfat` (
  `U_Bloodfat_Id` int NOT NULL AUTO_INCREMENT,
  `Cholesterin` float NOT NULL DEFAULT '0' COMMENT '总胆固醇',
  `Tring` float NOT NULL DEFAULT '0' COMMENT '甘油三酯',
  `Hdl` float NOT NULL DEFAULT '0' COMMENT '高密度脂蛋白胆固醇',
  `Ldl` float NOT NULL DEFAULT '0' COMMENT '低密度脂蛋白胆固醇',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '录入时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否删除',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_Bloodfat_Id`) USING BTREE,
  KEY `Ref994` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血脂四项';

CREATE TABLE `yh_user_bloodlipid2` (
  `bloodlipid_Id` int NOT NULL AUTO_INCREMENT,
  `T-CHOL` float DEFAULT NULL COMMENT '总胆固醇',
  `TG` float DEFAULT NULL COMMENT '甘油三脂',
  `HDL` float DEFAULT NULL COMMENT '高密度脂蛋白',
  `LDL-C` float DEFAULT NULL COMMENT '低密度脂蛋白胆固醇',
  `Apo-A1` float DEFAULT NULL COMMENT '载脂蛋白A1',
  `Apo-B` float DEFAULT NULL COMMENT '载脂蛋白B',
  `LPa` float DEFAULT NULL COMMENT '脂蛋白aLP',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0 正常 1 异常',
  PRIMARY KEY (`bloodlipid_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血脂检测 新-杏海堂';

CREATE TABLE `yh_user_bloodpressure` (
  `U_Bloodpressure_Id` int NOT NULL AUTO_INCREMENT,
  `Sbp` int DEFAULT '0' COMMENT '收缩压',
  `Dbp` int DEFAULT '0' COMMENT '舒张压',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `xinlv` int DEFAULT '0' COMMENT '心率',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '常规测量' COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_Bloodpressure_Id`) USING BTREE,
  KEY `Ref996` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血压';

CREATE TABLE `yh_user_bloodroutine_examination` (
  `U_bloodroutine_Id` int NOT NULL AUTO_INCREMENT,
  `WBC` float DEFAULT NULL COMMENT '白细胞',
  `NEUTP` float DEFAULT NULL COMMENT '中性粒细胞',
  `LYMPHP` float DEFAULT NULL COMMENT '淋巴细胞',
  `MONOP` float DEFAULT NULL COMMENT '单核细胞',
  `EOP` float DEFAULT NULL COMMENT '嗜酸性粒细胞',
  `BASOP` float DEFAULT NULL COMMENT '嗜碱性粒细胞',
  `RBC` float DEFAULT NULL COMMENT '红细胞',
  `HGB` float DEFAULT NULL COMMENT '血红蛋白',
  `PCV` float DEFAULT NULL COMMENT '红细胞比积',
  `PLT` float DEFAULT NULL COMMENT '血小板',
  `MCV` float DEFAULT NULL COMMENT '平均红细胞体积',
  `MCH` float DEFAULT NULL COMMENT '平均红细胞血红蛋白含量',
  `MCHC` float DEFAULT NULL COMMENT '平均红细胞血红蛋白浓度',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `xinlv` int DEFAULT NULL COMMENT '心率',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`U_bloodroutine_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血常规 新';

CREATE TABLE `yh_user_bloodsuger` (
  `U_Bloodsuger_Id` int NOT NULL AUTO_INCREMENT,
  `Bs` float NOT NULL DEFAULT '0' COMMENT '血糖值',
  `Entry_Time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户',
  `Env` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_Bloodsuger_Id`) USING BTREE,
  KEY `Ref995` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血糖';

CREATE TABLE `yh_user_bloodsuger2` (
  `U_Bloodsuger_Id` int NOT NULL AUTO_INCREMENT,
  `FBG` float DEFAULT '0' COMMENT '血糖值',
  `HbA1C` float DEFAULT '0' COMMENT '糖化血红蛋白',
  `Entry_Time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` int NOT NULL COMMENT '所属用户',
  `Env` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `unit` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '单位',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`U_Bloodsuger_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血糖';

CREATE TABLE `yh_user_bmi` (
  `U_Bmi_Id` int NOT NULL AUTO_INCREMENT,
  `U_Height` float(8,2) DEFAULT '0.00' COMMENT '身高',
  `U_Weight` float(8,2) DEFAULT '0.00' COMMENT '体重',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户',
  `Env` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_Bmi_Id`) USING BTREE,
  KEY `Ref993` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='bmi';

CREATE TABLE `yh_user_body_fat` (
  `wubf_id` int NOT NULL AUTO_INCREMENT,
  `weightScale` float DEFAULT '0' COMMENT '体重',
  `idealWeight` float DEFAULT NULL COMMENT '理想体重',
  `withoutFatForWeightScale` float unsigned zerofill DEFAULT NULL COMMENT '去脂体重',
  `weightmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重说明',
  `weightState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重评分',
  `fat_rate` float DEFAULT '0' COMMENT '脂肪率',
  `idealBodyFat` float DEFAULT NULL COMMENT '理想脂肪',
  `bodyFatScale` float DEFAULT NULL COMMENT '脂肪重量',
  `fatmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脂肪率说明',
  `fatState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脂肪评分',
  `muscle` float unsigned DEFAULT '0' COMMENT '肌肉',
  `muscleScale` float DEFAULT NULL COMMENT '肌肉重量',
  `muscleControlScale` float DEFAULT NULL COMMENT '肌肉控制量',
  `musclemessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌肉说明',
  `muscleState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌肉评分',
  `bones` float DEFAULT '0' COMMENT '骨骼',
  `bomesmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨骼说明',
  `bonesState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '骨骼评分',
  `visceral_level` float DEFAULT '0' COMMENT '内脏水平',
  `levelmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内脏说明',
  `levelState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内脏评分',
  `water` float DEFAULT '0' COMMENT '水分',
  `watermessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '水分说明',
  `waterState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '水分评分',
  `heat` float DEFAULT '0' COMMENT '热量',
  `protein` float DEFAULT '0' COMMENT '蛋白质',
  `porteinmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蛋白质说明',
  `proteinState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '蛋白质评分',
  `basal_met` float DEFAULT '0' COMMENT '基础代谢',
  `standardBasicMetabolism` float DEFAULT NULL COMMENT '标准基础代谢',
  `budgetBasicMetabolism` float DEFAULT NULL COMMENT '预算热量（推荐摄入量）',
  `basalmessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '基础代谢说明',
  `basalState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '基础代谢评分',
  `storageMuscleLevel` float DEFAULT '0' COMMENT '储肌能力',
  `storagemessages` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '储肌能力说明',
  `storageState` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '储肌能力评分',
  `bodyAge` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '身体年龄',
  `bodyscore` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '身体得分',
  `bodyType` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身体类型',
  `Entry_Time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '录入时间',
  `Is_Del` int DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '所属客户',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `source` int DEFAULT '1' COMMENT '(1,其他数据来源 2,有品体脂称)',
  PRIMARY KEY (`wubf_id`) USING BTREE,
  KEY `E_User_Id` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户体脂信息表（对应于一体机的数据上传）';

CREATE TABLE `yh_user_body_fat_scale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `json_data` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `wubf_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_celldetection` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `img` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `ctime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isDel` int NOT NULL DEFAULT '2',
  `resurtText` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `dataSource` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '健管师上传',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_chat_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '聊天记录表主键',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '发送人id',
  `to_user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '接收人id',
  `message` varchar(505) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `addTimeInfo` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '发送时间',
  `read` int DEFAULT '0' COMMENT '0未读1已读',
  `roomid` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '房间号',
  `mtype` int DEFAULT NULL COMMENT '消息类型1私聊2群聊',
  `messageType` int DEFAULT NULL COMMENT '1文字2图片3文件',
  `filesize` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '文件大小',
  `filename` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '文件名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_commodity_data` (
  `merchandise_id` int NOT NULL AUTO_INCREMENT,
  `merchandise_type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '商品类型(1实物商品 2优惠礼卷)',
  `merchandise_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '商品名',
  `grouping_id` int DEFAULT NULL COMMENT '分组id',
  `image_text` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '图文详情',
  `cover_img` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '封面图',
  `pzk_integral` int DEFAULT NULL COMMENT '普通会员所需积分',
  `azk_integral` int DEFAULT NULL COMMENT '超级会员所需积分',
  `inventory` int DEFAULT NULL COMMENT '库存',
  `putaway` int DEFAULT NULL COMMENT '是否上架(1上架2未上架)',
  `creator` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '创建人',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '备用',
  `add_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `org_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`merchandise_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='商品信息表';

CREATE TABLE `yh_user_ct` (
  `ct_Id` int NOT NULL AUTO_INCREMENT,
  `typeid` int NOT NULL COMMENT '1.CT胸部平扫2.头部CT',
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisinfo` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断描述',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`ct_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='CT检查 新';

CREATE TABLE `yh_user_device` (
  `tid` int NOT NULL AUTO_INCREMENT,
  `terminalId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '设备ID',
  `userCode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户ID',
  `onLine` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '是否在线：0-在线，1-未穿戴，2-已穿戴',
  `sos_nos` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '紧急呼叫手机卡号',
  `note` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `terminalType` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '设备类型：1-血压计，2-血糖仪',
  `terminalName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '设备名',
  `mobile_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `bindingTime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '绑定时间',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='用户绑定设备';

CREATE TABLE `yh_user_dialogue_list` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '列表名称',
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '哪个人',
  `to_user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '属于谁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_dr` (
  `dr_Id` int NOT NULL AUTO_INCREMENT,
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisInfo` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断描述',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`dr_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='胸 片 新';

CREATE TABLE `yh_user_ear_thermometer` (
  `U_ear_Id` int NOT NULL AUTO_INCREMENT,
  `ear_wd` float NOT NULL DEFAULT '0' COMMENT '测量温度',
  `Entry_Time` datetime NOT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` int NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `Body_Part` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量部位',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_ear_Id`) USING BTREE,
  KEY `Ref995` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='耳温';

CREATE TABLE `yh_user_ecg` (
  `U_ECG_Id` int NOT NULL AUTO_INCREMENT,
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `sr` int NOT NULL DEFAULT '0' COMMENT '采样率，每秒钟采集多少次数据',
  `tr` int NOT NULL DEFAULT '0' COMMENT '采集节点总数',
  `Pulse` int NOT NULL DEFAULT '0' COMMENT '心率',
  `ResultCode` int DEFAULT '-1' COMMENT '测量结果编码',
  `xd_Value` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心电文件位置',
  `Entry_Time` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `pig_path` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址',
  `resurt_text` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量结果说明',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_ECG_Id`) USING BTREE,
  KEY `Ref995` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='心电图';

CREATE TABLE `yh_user_electrolyte` (
  `electrolyte_Id` int NOT NULL AUTO_INCREMENT,
  `Potassium` float DEFAULT NULL COMMENT '钾',
  `Sodium` float DEFAULT NULL COMMENT '钠',
  `Chlorine` float DEFAULT NULL COMMENT '氯',
  `IonizedCalcium` float DEFAULT NULL COMMENT '离子钙',
  `TotalCalcium` float DEFAULT NULL COMMENT '总钙',
  `DeterminationMagnesium` float DEFAULT NULL COMMENT '镁测定',
  `DeterminationIron` float DEFAULT NULL COMMENT '铁测定',
  `DeterminationSerumTotalIron` float DEFAULT NULL COMMENT '血清总铁结合力测定',
  `DeterminationInorganicPhosphorus` float DEFAULT NULL COMMENT '无机磷测定',
  `SerumBicarbonateDetermined` float DEFAULT NULL COMMENT '血清碳酸氢盐(HCO3)测定',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0 正常 1异常',
  PRIMARY KEY (`electrolyte_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='电解质 新';

CREATE TABLE `yh_user_enrolinfo` (
  `E_User_Id` int NOT NULL AUTO_INCREMENT,
  `Seq_Instrument` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '仪器序列号',
  `Doc_Ruserid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `Flag_Dgroup` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '干预分组',
  `Sex_Code` int NOT NULL DEFAULT '1',
  `Height` float NOT NULL DEFAULT '0',
  `Weight` float NOT NULL DEFAULT '0',
  `Hw_Uptime` datetime DEFAULT NULL COMMENT '正常运行时间',
  `Waistc` float NOT NULL DEFAULT '0' COMMENT '腰围',
  `W_Uptime` datetime DEFAULT NULL COMMENT 'W正常运行时间',
  `Hipc` float NOT NULL DEFAULT '0',
  `H_Uptime` datetime DEFAULT NULL,
  `Sbp` int NOT NULL DEFAULT '0' COMMENT '收缩压',
  `Dbp` int NOT NULL DEFAULT '0' COMMENT '舒张压',
  `Sd_Uptime` datetime DEFAULT NULL,
  `Bs` float NOT NULL DEFAULT '0',
  `Bs_Uptime` datetime DEFAULT NULL,
  `Cholesterin` float NOT NULL DEFAULT '0',
  `Tring` float NOT NULL DEFAULT '0',
  `Hdl` float NOT NULL DEFAULT '0',
  `Ldl` float NOT NULL DEFAULT '0',
  `Bf_Uptime` datetime DEFAULT NULL,
  `Fooddiary_Uptime` datetime DEFAULT NULL,
  `sportdiary_uptime` datetime DEFAULT NULL,
  `Foodmon_Uptime` datetime DEFAULT NULL,
  `Energy_Uptime` datetime DEFAULT NULL,
  `Lifestyle_Uptime` datetime DEFAULT NULL,
  `Health_Monitoring` datetime DEFAULT NULL,
  `Is_Del` int NOT NULL DEFAULT '2',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `evaluateFlag` int NOT NULL DEFAULT '0' COMMENT '是否干预',
  `L_Ventricular_Hype` int(11) unsigned zerofill DEFAULT '00000000000',
  `measure_card` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '健康管理包ID',
  `evaluateFlag_Uptime` datetime DEFAULT NULL COMMENT '干预周期',
  PRIMARY KEY (`E_User_Id`) USING BTREE,
  KEY `Ref932` (`user_code`) USING BTREE,
  KEY `Seq_Instrument` (`Seq_Instrument`) USING BTREE,
  KEY `Doc_Ruserid` (`Doc_Ruserid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户信息扩展';

CREATE TABLE `yh_user_file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `file_type` int DEFAULT '0' COMMENT '文件类型 1文件  2图片',
  `file_name` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件名称',
  `file_size` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件大小',
  `file_parent_folder` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件夹id',
  `file_url` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '上传地址',
  `file_create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='用户文件存储';

CREATE TABLE `yh_user_folder` (
  `folder_id` bigint NOT NULL AUTO_INCREMENT,
  `folder_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '文件夹名称',
  `folder_parent` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件夹父节点',
  `folder_constraint` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文件约束条件',
  `folder_creation_date` datetime DEFAULT NULL COMMENT '文件夹创建时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建文件夹用户id',
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='用户文件夹';

CREATE TABLE `yh_user_food_guide` (
  `foodguide_Id` int NOT NULL AUTO_INCREMENT,
  `Gtype` int NOT NULL DEFAULT '1' COMMENT '类型：1为7天报告',
  `Start_Time` date DEFAULT NULL COMMENT '开始时间',
  `End_Time` date DEFAULT NULL COMMENT '结束时间',
  `Energy_Level` int DEFAULT NULL COMMENT '能量等级',
  `user_code` int NOT NULL COMMENT '用户code',
  `newly_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`foodguide_Id`) USING BTREE,
  KEY `Ref9920` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户膳食分析报告';

CREATE TABLE `yh_user_food_weekly` (
  `wid` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `startTime` varchar(111) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始时间',
  `endTime` varchar(111) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结束时间',
  `ecordDays` int DEFAULT NULL COMMENT '记录天数',
  `exceedingDays` int DEFAULT NULL COMMENT '超标天数',
  `noRepetition` int DEFAULT NULL COMMENT '不重复食材数',
  `averageIntake7` float DEFAULT '0' COMMENT '一周天数平均摄入',
  `averageIntake` float DEFAULT '0' COMMENT '实际记录天数平均摄入',
  `protein` float DEFAULT '0' COMMENT '蛋白质',
  `fat` float DEFAULT '0' COMMENT '脂肪',
  `carbohydrate` float DEFAULT NULL COMMENT '碳水化合物',
  `zao` float DEFAULT '0' COMMENT '早',
  `wu` float DEFAULT '0',
  `wan` float DEFAULT '0' COMMENT '晚',
  `jia` float DEFAULT '0' COMMENT '加',
  `foodType` varchar(111) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '平衡膳食宝塔分析',
  `type1` float DEFAULT '0',
  `type2` float DEFAULT '0',
  `type3` float DEFAULT '0',
  `type4` float DEFAULT '0',
  `type5` float DEFAULT '0',
  `type6` float DEFAULT '0',
  `type7` float DEFAULT '0',
  `type8` float DEFAULT '0',
  `type9` float DEFAULT '0',
  `type10` float DEFAULT '0',
  `type11` float DEFAULT '0',
  `type12` float DEFAULT '0',
  `type13` float DEFAULT '0',
  `type14` float DEFAULT '0',
  `type15` float DEFAULT '0',
  `recommend` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '推荐摄入',
  `actual` varchar(111) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实际摄入',
  PRIMARY KEY (`wid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_gastricfunction` (
  `gastricfunction_Id` int NOT NULL AUTO_INCREMENT,
  `PG_I` float DEFAULT NULL COMMENT '胃蛋白酶原Ⅰ',
  `PG_II` float DEFAULT NULL COMMENT '胃蛋白酶原Ⅱ',
  `PG` float DEFAULT NULL COMMENT 'PGⅠ/ PGⅡ比值',
  `G_17` float DEFAULT NULL COMMENT '胃泌素-17',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1 异常',
  PRIMARY KEY (`gastricfunction_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='胃部功能检查 新';

CREATE TABLE `yh_user_gift_exchange` (
  `gift_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `org_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '机构code',
  `gift_title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  `gift_img` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址',
  `pzk` int DEFAULT NULL COMMENT '普通会员所需积分',
  `azk` int DEFAULT NULL COMMENT '超级会员所需积分',
  `gift_content` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '介绍',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`gift_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='礼品兑换';

CREATE TABLE `yh_user_gift_redemption` (
  `redemption_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户code',
  `redemption_address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '地址',
  `tracking_number` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '快递单号',
  `gift_id` int DEFAULT NULL COMMENT '积分礼品编号',
  `integift` int DEFAULT NULL COMMENT '消耗积分',
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`redemption_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='换购积分礼品';

CREATE TABLE `yh_user_group_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int DEFAULT NULL COMMENT '用户分组id',
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `group_level` int DEFAULT NULL COMMENT '管理级别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户与分组绑定表';

CREATE TABLE `yh_user_group_next` (
  `next_id` int NOT NULL AUTO_INCREMENT,
  `group_id` int DEFAULT NULL COMMENT '分组主表id',
  `level` int DEFAULT NULL COMMENT '等级',
  `condition_id` int DEFAULT NULL COMMENT '筛选条件id',
  `condition_type` int DEFAULT NULL COMMENT '筛选类型 1、and 2、or',
  `condition_count` int DEFAULT NULL COMMENT '筛选 1、和  2、或',
  PRIMARY KEY (`next_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户分组规则分表';

CREATE TABLE `yh_user_grouping` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户分组名',
  `describe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `level_count` int DEFAULT NULL COMMENT '管理等级',
  `group_type` int DEFAULT NULL COMMENT '分组类型 （1、智能分组 2、手动分组）',
  `group_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者id',
  `group_intime` datetime DEFAULT NULL COMMENT '创建时间',
  `group_uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户分组规则主表';

CREATE TABLE `yh_user_gynecologicexamination` (
  `gynecologicexamination_Id` int NOT NULL AUTO_INCREMENT,
  `TCT` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '液基超薄细胞检测（已婚）',
  `HPV-DNA` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '人乳头状瘤病毒检查（已婚）',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`gynecologicexamination_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='妇科检查 新';

CREATE TABLE `yh_user_habit_guide` (
  `habit_guide_id` int NOT NULL AUTO_INCREMENT,
  `Start_Time` date DEFAULT NULL COMMENT '开始时间',
  `End_Time` date DEFAULT NULL COMMENT '结束时间',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `newly_Time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`habit_guide_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='习惯分析报告';

CREATE TABLE `yh_user_healthy_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户code',
  `courseseq` int DEFAULT NULL COMMENT '当前计划完成个数',
  `plan_day` int DEFAULT '0' COMMENT '计划执行天数',
  `plandetail_id` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当前完成计划详情id',
  `plan_id` int DEFAULT NULL COMMENT '计划id',
  `plan_startdate` datetime DEFAULT NULL COMMENT '计划开始时间',
  `plan_enddate` datetime DEFAULT NULL COMMENT '计划结束时间',
  `plan_state` int DEFAULT NULL COMMENT '0未开始；1已开始；2已结束；3已取消;4审核中',
  `plan_choose` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择题选了哪个',
  `plan_unique` int DEFAULT NULL COMMENT '计划次数唯一标识',
  `plan_finishtime` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户健康计划日志';

CREATE TABLE `yh_user_heart_rate` (
  `rid` int NOT NULL AUTO_INCREMENT COMMENT '心率自增id',
  `heart_rate` int DEFAULT NULL COMMENT '心率',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '录入时间',
  `Is_Del` int DEFAULT '2' COMMENT '是否隐藏',
  `Env` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`rid`) USING BTREE,
  KEY `E_User_Id` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_hemoglobin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `he_hb` float DEFAULT NULL COMMENT '血红蛋白值',
  `he_tyep` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '来源',
  `he_data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='血红蛋白';

CREATE TABLE `yh_user_hepatitisquantitative` (
  `hepatitisquantitative_Id` int NOT NULL AUTO_INCREMENT,
  `HBSAg` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乙肝表面抗原',
  `HBSAb` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乙肝表面抗体',
  `HBeAg` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'e抗原',
  `HBeAb` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'e抗体',
  `HBcAb` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '核心抗体',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`hepatitisquantitative_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='乙肝五项定量 新';

CREATE TABLE `yh_user_integral` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户',
  `aggregate_score` int DEFAULT NULL COMMENT '总积分',
  `current_integral` int DEFAULT NULL COMMENT '当前积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户积分表';

CREATE TABLE `yh_user_life_diary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `begin_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '开始时间',
  `finish_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '结束时间',
  `type` int DEFAULT NULL COMMENT '类型（1，月2，季3，年）',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `food_practical_num` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食实际情况',
  `food_recommend_heat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食推荐热量',
  `food_practical_heat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '膳食实际总热量',
  `food_rational_nutrient` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '膳食营养素合理内容',
  `food_unreasonable_nutrient` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '膳食营养素不合理内',
  `food_rational_guide` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '膳食指南合理',
  `food_unreasonable_guide` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '膳食指南不合理',
  `food_language` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '膳食文案',
  `sport_practical_num` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动实际情况',
  `sport_recommend_heat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动推荐耗量',
  `sport_practical_heat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动实际总耗量',
  `sport_recommend_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '运动推荐内容',
  `sport_language` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '运动文案',
  `sleep_week` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠记录天数',
  `sleep_duration` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '睡眠时长',
  `sleep_duration_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '睡眠时长内容',
  `sleep_time_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '睡眠入睡内容',
  `sleep_language` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '睡眠文案',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='运动，膳食，睡眠生活日记统计报告（月，季，年）';

CREATE TABLE `yh_user_liverfunction` (
  `liverid` int NOT NULL AUTO_INCREMENT COMMENT '肝功能id',
  `gu_Bing` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷丙转氨酶',
  `millet_straw` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '谷草转氨酶',
  `serum_albumin` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清白蛋白',
  `serum_globulin` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血清球蛋白',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `insertTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Is_Del` int DEFAULT '2' COMMENT '是否删除',
  `Data_Source` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`liverid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='肝功能';

CREATE TABLE `yh_user_liverfunction2` (
  `liverfunction_Id` int NOT NULL AUTO_INCREMENT,
  `GGT` float DEFAULT NULL COMMENT '谷氨酰转肽酶',
  `ALP` float DEFAULT NULL COMMENT '碱性磷酸酶',
  `TPO` float DEFAULT NULL COMMENT '总蛋白',
  `ALB` float DEFAULT NULL COMMENT '白蛋白',
  `GLO` float DEFAULT NULL COMMENT '球蛋白',
  `AG` float DEFAULT NULL COMMENT '白蛋白/球蛋白',
  `TBil` float DEFAULT NULL COMMENT '总胆红素',
  `DBILI` float DEFAULT NULL COMMENT '直接胆红素',
  `IBILI` float DEFAULT NULL COMMENT '间接胆红素',
  `ALT` float DEFAULT NULL COMMENT '丙氨酸氨基转氨酶',
  `AST` float DEFAULT NULL COMMENT '天门冬氨酸氨基转移酶',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `unit` varchar(55) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '总胆红素单位',
  `aberrant` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常，1异常',
  PRIMARY KEY (`liverfunction_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='肝功能表 新';

CREATE TABLE `yh_user_local_auth` (
  `auth_id` int NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `uer_role` int DEFAULT NULL,
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='本地身份验证';

CREATE TABLE `yh_user_log` (
  `log_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户编号',
  `description` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '操作描述',
  `ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '操作IP地址',
  `parameter` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '参数值',
  `result` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '结果值',
  `spend_time` int DEFAULT NULL COMMENT '消耗时间',
  `base_path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '根路径',
  `method` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '请求类型',
  `uri` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '请求路径',
  `user_agent` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用户标识',
  `permissions` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '权限值',
  `start_time` bigint DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户操作日志表';

CREATE TABLE `yh_user_make_card` (
  `Enrol_Card_Id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Card_Code` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Time` datetime NOT NULL COMMENT '登记时间',
  `Enrol_User_Id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '登记用户id',
  `Card_Type` int DEFAULT NULL COMMENT '卡类型',
  `Begin_Time` datetime DEFAULT NULL COMMENT '生效时间',
  `End_Time` date DEFAULT NULL COMMENT '结束时间',
  `Org_Id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `User_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `User_Type` int DEFAULT NULL,
  `doctor` int DEFAULT NULL COMMENT '根据yh_user_admin_info表中的id对应属于哪个医生',
  `ev_amount` int DEFAULT NULL,
  `number` int DEFAULT NULL,
  `key` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`Enrol_Card_Id`) USING BTREE,
  UNIQUE KEY `Enrol_Card_Id` (`Enrol_Card_Id`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE,
  KEY `doctor` (`doctor`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户卡制卡';

CREATE TABLE `yh_user_make_card_20240509` (
  `Enrol_Card_Id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Card_Code` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Time` datetime NOT NULL COMMENT '登记时间',
  `Enrol_User_Id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '登记用户id',
  `Card_Type` int DEFAULT NULL COMMENT '卡类型',
  `Begin_Time` datetime DEFAULT NULL COMMENT '生效时间',
  `End_Time` date DEFAULT NULL COMMENT '结束时间',
  `Org_Id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `User_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `User_Type` int DEFAULT NULL,
  `doctor` int DEFAULT NULL COMMENT '根据yh_user_admin_info表中的id对应属于哪个医生',
  `ev_amount` int DEFAULT NULL,
  `number` int DEFAULT NULL,
  `key` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`Enrol_Card_Id`) USING BTREE,
  UNIQUE KEY `Enrol_Card_Id` (`Enrol_Card_Id`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE,
  KEY `doctor` (`doctor`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户卡制卡';

CREATE TABLE `yh_user_make_card_log` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Marke_Ttime` datetime DEFAULT NULL,
  `Marke_Amount` int DEFAULT NULL,
  `Begin_Number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `End_Number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Card_Type` int DEFAULT NULL,
  `Marke_Note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Org_Code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Log_Type` int DEFAULT '0',
  `old_file_name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `upload_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Org_Code` (`Org_Code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='开卡操作日志表';

CREATE TABLE `yh_user_make_card_old` (
  `Enrol_Card_Id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Card_Code` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `Enrol_Time` datetime NOT NULL COMMENT '登记时间',
  `Enrol_User_Id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '登记用户id',
  `Card_Type` int DEFAULT NULL COMMENT '卡类型',
  `Begin_Time` datetime DEFAULT NULL COMMENT '生效时间',
  `End_Time` date DEFAULT NULL COMMENT '结束时间',
  `Org_Id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `User_Code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `User_Type` int DEFAULT NULL,
  `doctor` int DEFAULT NULL COMMENT '根据yh_user_admin_info表中的id对应属于哪个医生',
  `ev_amount` int DEFAULT NULL,
  `number` int DEFAULT NULL,
  `key` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`Enrol_Card_Id`) USING BTREE,
  UNIQUE KEY `Enrol_Card_Id` (`Enrol_Card_Id`) USING BTREE,
  KEY `User_Code` (`User_Code`) USING BTREE,
  KEY `doctor` (`doctor`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户卡制卡';

CREATE TABLE `yh_user_make_card_sort` (
  `wumcs_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sort_flag` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '分类标识',
  `sort_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '分类名称',
  `entry_time` datetime DEFAULT NULL COMMENT '录入时间',
  PRIMARY KEY (`wumcs_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_markerscardiovascular` (
  `markerscardiovascular_Id` int NOT NULL AUTO_INCREMENT,
  `HCP` float DEFAULT NULL COMMENT '超敏C反应蛋白',
  `Interleukin-6` float DEFAULT NULL COMMENT '白介素-6',
  `TNF-a` float DEFAULT NULL COMMENT '肿瘤坏死因子',
  `Fibrinogen` float DEFAULT NULL COMMENT '纤维蛋白原',
  `HCY` float DEFAULT NULL COMMENT '同型半胱氨酸',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0 正常 1 异常',
  PRIMARY KEY (`markerscardiovascular_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='心血管损伤标志物 新';

CREATE TABLE `yh_user_medal` (
  `medal_id` int NOT NULL AUTO_INCREMENT,
  `medal_name` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `medal_type` int DEFAULT NULL COMMENT '1.健康计划，2.健康习惯',
  `medal_stype` int DEFAULT NULL COMMENT '子id(1.高血压，2.高血糖，3.高血脂，4.减肥，5.高尿酸)',
  `medal_img` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `medal_num` int DEFAULT NULL,
  `medal_level` int DEFAULT NULL,
  `medal_num1` int DEFAULT NULL,
  `medal_num2` int DEFAULT NULL,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `meal_time` datetime DEFAULT NULL,
  PRIMARY KEY (`medal_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_menu` (
  `id` int NOT NULL,
  `menu_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `menu_url` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `parentid` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='菜单层级树表';

CREATE TABLE `yh_user_myocardialenzyme` (
  `myocardialenzyme_Id` int NOT NULL AUTO_INCREMENT,
  `SCKWD` float DEFAULT NULL COMMENT '血清肌酸激酶测定',
  `DOLD` float DEFAULT NULL COMMENT '乳酸脱氢酶测定',
  `DOSCK` float DEFAULT NULL COMMENT '血清肌酸激酶—MB同工酶测定',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`myocardialenzyme_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='心肌酶 新';

CREATE TABLE `yh_user_notify` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '内容',
  `user_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '接受人',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '阅读标记 0-未读 1-已读',
  `send_date` datetime DEFAULT NULL COMMENT '推送时间',
  `read_date` datetime DEFAULT NULL COMMENT '阅读时间',
  `type` tinyint(1) DEFAULT '1' COMMENT '消息类型1-报告；2-互动;3-系统;4.站内信',
  `to_service_id` int DEFAULT NULL COMMENT '1-运动  2-膳食 3-睡眠 4-习惯 5.站内信',
  `news_id` int DEFAULT NULL COMMENT '外键id',
  `lectureInfoId` int DEFAULT NULL COMMENT '文章编号',
  `standby1` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用字段1',
  `standby2` int DEFAULT NULL COMMENT '备用字段2',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `standby1` (`standby1`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='通知通告发送记录';

CREATE TABLE `yh_user_online` (
  `sessionId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int DEFAULT '0' COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='在线用户记录';

CREATE TABLE `yh_user_order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `order_number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '订单编号',
  `site_id` int DEFAULT NULL COMMENT '地址id',
  `merchandise_id` int DEFAULT NULL COMMENT '商品id',
  `order_ status` int DEFAULT NULL COMMENT '订单状态  1.已发货 2未发货',
  `consume_ integral` int DEFAULT NULL COMMENT '消耗积分',
  `money` int DEFAULT NULL COMMENT '金额',
  `logistics_company` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '物流公司',
  `logistics_odd` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '物流单号',
  `ssm` int DEFAULT NULL COMMENT '是否发送短信 1.发送 2不发送',
  `creator` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '创建人',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '数量',
  `add_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='订单记录表';

CREATE TABLE `yh_user_org_tree` (
  `id` int NOT NULL,
  `org_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `org_note` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci,
  `parent_org_id` int DEFAULT NULL,
  `addDate` datetime(6) DEFAULT NULL,
  `isdel` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_otherfunction` (
  `otherid` int NOT NULL AUTO_INCREMENT COMMENT '其他功能id',
  `urine_microprotein` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿微量蛋白',
  `glycosylated_hemoglobin` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '糖化血红蛋白',
  `hcy` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '同型半胱氨酸',
  `hemoglobin` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血红蛋白',
  `user_id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编号id',
  `insertTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Is_Del` int DEFAULT '2' COMMENT '是否删除',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`otherid`) USING BTREE,
  KEY `index_name` (`insertTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户其他医疗指标表';

CREATE TABLE `yh_user_oxygen` (
  `U_oxygen_Id` int NOT NULL AUTO_INCREMENT,
  `oxygen` float DEFAULT '0' COMMENT '血氧值',
  `heart_rate` int DEFAULT '0' COMMENT '心率',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`U_oxygen_Id`) USING BTREE,
  KEY `Ref995` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户血氧信息表';

CREATE TABLE `yh_user_pain_type` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `plan_assessment` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '疼痛部位',
  `plan_parentid` int DEFAULT NULL COMMENT '疼痛父分类ID',
  `sort_id` int DEFAULT NULL COMMENT '顺序id',
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疼痛部位类目表';

CREATE TABLE `yh_user_permission` (
  `permission_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `system_id` int unsigned NOT NULL COMMENT '权限所属角色1.管理员，2.健管师，3.用户，4.管理员跟健管师',
  `pid` int DEFAULT NULL COMMENT '所属上级',
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '名称',
  `type` tinyint DEFAULT NULL COMMENT '类型(1:目录,2:菜单,3:按钮)',
  `permission_value` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限值',
  `uri` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '路径',
  `icon` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '图标',
  `status` tinyint DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `ctime` bigint DEFAULT NULL COMMENT '创建时间',
  `orders` bigint DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='权限表';

CREATE TABLE `yh_user_personage_condition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `waist` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰臀比例',
  `habitas` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体质指数',
  `breathe` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '呼吸频率',
  `stature` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身高',
  `weight` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体重',
  `waistline` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '腰围',
  `hipline` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '臀围',
  `heat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体温',
  `palse` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脉搏',
  `dbp` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '舒张压',
  `sbp` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '收缩压',
  `hrrest` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '安静心率',
  `addtime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `updatetime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人状况';

CREATE TABLE `yh_user_personage_constitution` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `peace_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '和平质',
  `deficiency_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '气虚',
  `sun_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '阳虚',
  `cloudy_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '阴虚',
  `phlegm_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '痰湿',
  `damp_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '湿热',
  `blood_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血瘀',
  `gas_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '气郁',
  `special_quality` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '特秉质',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，中医体质';

CREATE TABLE `yh_user_personage_examination` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `fbg` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '空腹血糖',
  `hemoglobin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血红蛋白',
  `hemocyte` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血细胞',
  `blood` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血小板',
  `bloodelse` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血常规其他',
  `urine` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿蛋白',
  `uriensugar` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿糖',
  `urineacetone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿酮体',
  `urineblood` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿替血',
  `urineelse` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿常规其他',
  `urinarymicro` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿微量蛋白',
  `excrement` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '大便替血',
  `cerealthird` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '谷丙',
  `milletstraw` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '谷草',
  `albumin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '白蛋白',
  `sweetpotato` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '红素',
  `combine` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '结合',
  `serum` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血清',
  `hematuresis` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血尿',
  `potassium` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血钾',
  `sodium` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '血钠',
  `tc` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '总胆固醇',
  `tg` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '甘油三酯',
  `tctall` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '高总胆固醇',
  `tcbottom` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '底总胆固醇',
  `saccharify` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '糖化血',
  `hepatitis` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '乙型',
  `chest` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '胸部',
  `cervix` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '宫颈',
  `ecg` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '心电图',
  `fundus` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '眼底',
  `bultrasound` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT 'b超',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，辅助检查';

CREATE TABLE `yh_user_personage_existing` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `bloodvessel` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '脑血管',
  `kidney` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肾脏',
  `heart` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '心脏',
  `blooddisease` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '血管疾病',
  `ocular` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '眼部',
  `rests` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '其他',
  `nervoussystem` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '神经系统疾病',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，现存问题';

CREATE TABLE `yh_user_personage_hospitalized` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `timedeta` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '入院时间',
  `cause` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '原因',
  `Dischargetime` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '出院时间',
  `yiyuanname` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '医院名称',
  `disease` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '病案号',
  `hospitalizedtype` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '(1,个人  2,家庭)',
  `member` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '成员',
  `standby` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '备用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，住院情况';

CREATE TABLE `yh_user_personage_live` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usercode` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `exercise` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '是否运动（1,每天2,偶尔3，不运动）',
  `days` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '每天运动量',
  `sinsist` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '坚持运动时间',
  `have` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '锻炼时间',
  `dietsup` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '饮食方式',
  `smoke` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '吸烟',
  `quitsmokingtopic` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '戒烟年龄',
  `average` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '平均吸烟',
  `age` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '吸烟年龄',
  `harm` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '职业病危害',
  `specificsj` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '具体时间',
  `obtain` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '从业工作',
  `harmelement` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '危害因素',
  `defend` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '防护措施',
  `addtime` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `updatetime` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，生活方式';

CREATE TABLE `yh_user_personage_nonimmune` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `immunename` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '名称',
  `organization` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '机构',
  `timedate` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0' COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，非免疫规划预防接种史';

CREATE TABLE `yh_user_personage_pharmacy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `drugname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '药名',
  `usagefa` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用法',
  `dosage` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '用量',
  `timedata` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '时间',
  `comply` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '依从性',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，主要用药';

CREATE TABLE `yh_user_personage_physical` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '0',
  `skin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '皮肤',
  `sclera` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '巩膜',
  `lymph` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '淋巴结',
  `heartrate` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '心率',
  `rhthm` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '心律',
  `noise` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '杂音',
  `tenderness` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '压痛',
  `enclosedbao` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '包块',
  `hepatomegalg` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '肝大',
  `selenaaxe` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '脾大',
  `movethe` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '移动',
  `lower` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '下肢',
  `acrotarsium` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '足背',
  `anas` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '肛门',
  `breast` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '乳腺',
  `gynecology` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '外阴',
  `vagina` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '阴道',
  `cervix` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '宫颈',
  `corpus` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '宫体',
  `fucheck` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '妇检',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，查体';

CREATE TABLE `yh_user_personage_symptom` (
  `id` int NOT NULL AUTO_INCREMENT,
  `personage_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '症状',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备用',
  `addtime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '添加时间',
  `updatetime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='体检信息，个人症状';

CREATE TABLE `yh_user_personage_viscera` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `lips` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '口唇',
  `tooth` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '齿列',
  `throat` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '咽喉',
  `visionleft` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '视力左',
  `visionright` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '视力右',
  `correctleft` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '矫正左',
  `correctright` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '矫正右',
  `audiometry` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '听力情况',
  `sportssituation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '运动功能情况',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人中心，脏器功能';

CREATE TABLE `yh_user_physical_fitness` (
  `Id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '对应体适能设备传送过来的ID',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `age` tinyint DEFAULT NULL COMMENT '年龄',
  `complete` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '是否全部完成， 直接存中文的是或否',
  `height` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '身高',
  `weight` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '体重',
  `bmi_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'bmi评价（比如：肥胖）',
  `bmi_score` int DEFAULT NULL COMMENT 'bmi评分，总分5分，如果未测量初始值为0分，1-很差，2-较差，3-正常，4-良好，5优秀',
  `bmi_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'bmi评分标准',
  `lung_capacity` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺活量',
  `lung_capacity_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺活量评价',
  `lung_capacity_score` int DEFAULT NULL COMMENT '肺活量评分，总分5分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `lung_capacity_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肺活量评分标准',
  `step_index` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '台阶指数',
  `step_index_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '台阶指数评价',
  `step_index_score` int DEFAULT NULL COMMENT '台阶指数评分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `step_index_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '台阶指数评分标准',
  `grip_power` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '握力',
  `grip_power_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '握力评价',
  `grip_power_score` int DEFAULT NULL COMMENT '握力评分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `grip_power_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '握力评分标准',
  `crowd_jump` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '纵跳',
  `crowd_jump_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '纵跳评价',
  `crowd_jump_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '纵跳评分标准',
  `crowd_jump_score` int DEFAULT NULL COMMENT '众跳评分,如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `push_up` int DEFAULT NULL COMMENT '俯卧撑',
  `push_up_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '俯卧撑评价',
  `push_up_score` int DEFAULT NULL COMMENT '俯卧撑评分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `push_up_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '俯卧撑评分标准',
  `forward_flexion` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '坐立体前屈',
  `forward_flexion_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '坐立体前屈评价',
  `forward_flexion_score` int DEFAULT NULL COMMENT '坐立体前屈评分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `forward_flexion_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '坐立体前屈评分标准',
  `close_eyes_stand` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '闭眼单脚立',
  `close_eyes_stand_evaluate` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '闭眼单脚立评价',
  `close_eyes_stand_score` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '闭眼单脚立评分，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `close_eyes_stand_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '闭眼单脚立评分标准',
  `choice_reaction` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择反应时',
  `choice_reaction_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择反应时评价',
  `choice_reaction_sore` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择反应时评分吗，如果未测量初始值为0分，1-很差，2-较差，3-合格，4-良好，5优秀',
  `choice_reaction_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '选择反应时评分标准',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '会员唯一识别码',
  `measure_time` datetime DEFAULT NULL COMMENT '测量时间',
  `sit_up` int DEFAULT NULL COMMENT '仰卧起坐（针对女性）',
  `sit_up_score_evaluate` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '仰卧起坐评价（针对女性）',
  `sit_up_rule` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '仰卧起坐评分规则',
  `sit_up_score` int DEFAULT NULL COMMENT '仰卧起坐评分（针对女性）',
  `total_score` int DEFAULT NULL COMMENT '总评分：30分以下合格，31-40合格，41-45良好，46以上优秀',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='第三方体适能设备接口数据';

CREATE TABLE `yh_user_physical_info` (
  `upi_id` int NOT NULL AUTO_INCREMENT COMMENT '用户体检指标信息ID',
  `pir_id` int NOT NULL COMMENT '指标信息记录well_physical_info_record ID',
  `mem_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '体检指标ID',
  `mem_value` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '体检指标值',
  `chinese_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '指标名称',
  `excep_statue` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '异常状态 0正常 1异常',
  `user_ident` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户标识',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`upi_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户体检指标信息表';

CREATE TABLE `yh_user_physical_text_info` (
  `upti_id` int NOT NULL AUTO_INCREMENT COMMENT '用户体检指标文本信息ID',
  `pir_id` int NOT NULL COMMENT '指标信息记录well_physical_info_record ID',
  `mem_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '体检指标ID',
  `mem_text_value` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '体检指标文本值',
  `chinese_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '指标名称',
  `user_ident` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户标识',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`upti_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户体检指标文本信息表';

CREATE TABLE `yh_user_power` (
  `id` int NOT NULL,
  `powerType` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_power_menu` (
  `id` int NOT NULL,
  `powerId` int DEFAULT NULL,
  `menuId` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_quantumdetection` (
  `qid` int NOT NULL AUTO_INCREMENT,
  `img` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `qtime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `usercode` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `isDel` int NOT NULL DEFAULT '2',
  `resurtText` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `dataSource` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT '健管师录入',
  PRIMARY KEY (`qid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_user_reginfo` (
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户code',
  `User_Type` int NOT NULL DEFAULT '0' COMMENT '用户类型',
  `user_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '用户姓名',
  `user_pwd` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户密码',
  `gender` int NOT NULL DEFAULT '1' COMMENT '用户性别（1.男；2.女）',
  `birth_date` datetime DEFAULT NULL COMMENT '出生年月',
  `nation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '民族',
  `email` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `cardidentity` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '身份证',
  `province_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_mysql500_ci DEFAULT NULL COMMENT '省',
  `city_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '市',
  `area_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '区',
  `reg_time` datetime NOT NULL COMMENT '注册时间',
  `Login_times` int DEFAULT '0' COMMENT '登录次数',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '户籍地址',
  `permanentaddress` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '常住地址',
  `post_code` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '邮编',
  `relation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '紧急联系人手机号',
  `mobile` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手机号码',
  `occupation` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '职业',
  `ed_background` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '文化程度',
  `marital_status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '婚姻状况',
  `lock_flag` int NOT NULL DEFAULT '2' COMMENT '锁标记',
  `reg_source` int NOT NULL DEFAULT '1' COMMENT '注册来源',
  `Cert_Type` int DEFAULT NULL COMMENT '证书类型',
  `Cert_NO` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '证书编号',
  `Org_Code` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '组织code',
  `Blood_Type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '血型',
  `rhfeminine` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'rh阴性',
  `Enrol_Card_Id` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '注册卡Id',
  `login_time` datetime DEFAULT NULL COMMENT '登录次数',
  `unit` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '工作单位',
  `is_association` int DEFAULT '0' COMMENT '是否社团（0是1否）',
  `association_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '社团名称',
  `is_alone` int DEFAULT '0' COMMENT '是否独居（0是1否）',
  `is_allergy` int DEFAULT '4' COMMENT '是否过敏（0是1否）',
  `allergy_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '过敏源',
  `nickname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '昵称',
  `medicalCare` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `classifyId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `relation_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `qyuser_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Introduction` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `changePwdLastTime` datetime DEFAULT NULL,
  PRIMARY KEY (`user_code`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `Ref10000` (`user_code`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `province_id` (`province_id`) USING BTREE,
  KEY `city_id` (`city_id`) USING BTREE,
  KEY `area_id` (`area_id`) USING BTREE,
  KEY `Enrol_Card_Id` (`Enrol_Card_Id`) USING BTREE,
  KEY `Org_Code` (`Org_Code`) USING BTREE,
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='注册用户';

CREATE TABLE `yh_user_remind` (
  `U_Remind_Id` int NOT NULL AUTO_INCREMENT,
  `Add_Date` date NOT NULL,
  `Day_Type` int DEFAULT NULL,
  `Day_Log` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `E_User_Id` int NOT NULL,
  PRIMARY KEY (`U_Remind_Id`) USING BTREE,
  KEY `Ref9919` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户提醒';

CREATE TABLE `yh_user_reminder_settings` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `usercode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID',
  `consulting` int DEFAULT '0' COMMENT '健康咨询回复',
  `food` int DEFAULT '0' COMMENT '膳食任务',
  `sports` int DEFAULT '0' COMMENT '运动任务',
  `sleep` int DEFAULT '0' COMMENT '睡眠任务',
  `drug` int DEFAULT '0' COMMENT '用药提醒',
  `signin` int DEFAULT '0' COMMENT '签到任务',
  `breakfast` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '08:30' COMMENT '早餐',
  `lunch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '12:30' COMMENT '午餐',
  `dinner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '18:30' COMMENT '晚餐',
  `extra_meal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '10:00' COMMENT '加餐',
  `sports_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '08:00' COMMENT '运动时间',
  `nap_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '12:45' COMMENT '小憩',
  `long_sleep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '22:30' COMMENT '久睡提醒',
  `signin_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '08:00' COMMENT '签到时间设置',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='小程序设置提醒时间';

CREATE TABLE `yh_user_renalfunction` (
  `renalid` int NOT NULL AUTO_INCREMENT COMMENT '肾功能编号id',
  `creatinine` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '肌酐',
  `urea_nitrogen` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿素氮',
  `uric_acid` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '尿酸',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `inertTime` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `Is_Del` int DEFAULT '2' COMMENT '是否删除',
  PRIMARY KEY (`renalid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户肾功能';

CREATE TABLE `yh_user_renalfunction2` (
  `renalfunction_Id` int NOT NULL AUTO_INCREMENT,
  `BUN` float DEFAULT NULL COMMENT '血尿素氮',
  `CREA` float DEFAULT NULL COMMENT '血肌酸酐',
  `SCC` float DEFAULT NULL COMMENT '血清胱抑素C',
  `UA` float DEFAULT NULL COMMENT '尿酸',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`renalfunction_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='肾功能表 新';

CREATE TABLE `yh_user_role` (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_description` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色描述',
  `role_ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `orders` bigint DEFAULT NULL COMMENT '排序',
  `pid` int DEFAULT NULL COMMENT '父id',
  `role_type` int DEFAULT NULL COMMENT '1.管理员2.健管师3.用户',
  `manage_roleid` varchar(2000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '管理员可分配权限的id',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户角色表';

CREATE TABLE `yh_user_role_permission` (
  `role_permission_id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int NOT NULL COMMENT '角色编号',
  `permission_id` int NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`role_permission_id`) USING BTREE,
  KEY `FK_Reference_23` (`role_id`) USING BTREE,
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`role_id`) REFERENCES `yh_user_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='角色权限关联表';

CREATE TABLE `yh_user_role_power` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roleId` int DEFAULT NULL COMMENT '角色编号',
  `userId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户角色关联表';

CREATE TABLE `yh_user_role_relation` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户编号',
  `user_role` int DEFAULT NULL COMMENT '权限编号',
  `type` int DEFAULT NULL COMMENT '权限类型(-1:减权限,1:增权限)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户权限关联表';

CREATE TABLE `yh_user_servicestatistic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `genre` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '类型',
  `record` int DEFAULT NULL COMMENT '记录次数',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户服务统计';

CREATE TABLE `yh_user_shipping_address` (
  `site_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `consignee_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '姓名',
  `consignee_phone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '电话',
  `site` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '地址',
  `detailed_site` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '详细地址',
  `whether_ default` int DEFAULT NULL COMMENT '是否开启默认地址 1开启2关闭',
  `creator` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '创建人',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '备用',
  `add_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`site_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='收货地址信息表';

CREATE TABLE `yh_user_sleep_guide` (
  `Sleep_Guide_Id` int NOT NULL AUTO_INCREMENT,
  `Start_Time` date NOT NULL COMMENT '开始时间',
  `End_Time` date NOT NULL COMMENT '结束时间',
  `SType` int NOT NULL DEFAULT '0' COMMENT '类型',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户code',
  `newly_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`Sleep_Guide_Id`) USING BTREE,
  KEY `Ref9914` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户睡眠分析报告';

CREATE TABLE `yh_user_smoking` (
  `smoking_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '用户编码',
  `smoking_age` int DEFAULT NULL COMMENT '烟龄',
  `smoking_amount` int DEFAULT NULL COMMENT '吸烟量',
  `smoking_prices` decimal(10,2) DEFAULT NULL COMMENT '烟价',
  `smoking_tar_content` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '焦油含量',
  `smoking_amount_time` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '吸一支烟的时间（分钟）',
  `smoking_quit_times` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '曾尝试几次戒烟',
  `smoking_date` datetime DEFAULT NULL COMMENT '记录时间',
  `smoking_days` datetime DEFAULT NULL COMMENT '最后吸烟时间',
  `smoking_remark` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '备注',
  `log_state` int DEFAULT NULL COMMENT '记录状态',
  PRIMARY KEY (`smoking_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='吸烟情况';

CREATE TABLE `yh_user_sport_ch_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ch_id` int DEFAULT NULL COMMENT '总表id',
  `upload_tome` date DEFAULT NULL COMMENT '当前时间',
  `step` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当天步数',
  `whether` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '当天是否完成（1,完成  2,未完成）',
  `reach` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '达标率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户挑战赛记录表';

CREATE TABLE `yh_user_sport_challenge` (
  `ch_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ch_begin_time` date DEFAULT NULL COMMENT '开始时间',
  `ch_fiaish_time` date DEFAULT NULL COMMENT '结束时间',
  `ch_average_step` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '日均步数',
  `ch_whether` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2' COMMENT '是否完成(1,完成 2，未完成)',
  `ch_reach` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '达标率',
  `backups` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `backups1` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `backups2` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='个人挑战赛总记录表';

CREATE TABLE `yh_user_sport_course_add` (
  `add_id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `course_id` int DEFAULT NULL COMMENT '课程id',
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '增加时间',
  `finishid` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '动作完成序号',
  `add_type` int DEFAULT '1' COMMENT '1,执行中 2，已完成',
  `standby` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '总天数',
  `add_num` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '1' COMMENT '第几天',
  PRIMARY KEY (`add_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='精选课程记录';

CREATE TABLE `yh_user_sport_step` (
  `U_SportStep_Id` int NOT NULL AUTO_INCREMENT COMMENT '自动增长的主键',
  `step` int DEFAULT '0' COMMENT '步数',
  `calorie` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '消耗的卡路里',
  `distance` int DEFAULT '0' COMMENT '步行的距离',
  `E_User_Id` int DEFAULT '0' COMMENT 'ç”¨æˆ·id',
  `Is_Del` int DEFAULT '2' COMMENT '是否隐藏，默认为2',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '常规测量' COMMENT '测量的状态',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '0' COMMENT '测量的类型，有手动输入，评估，设备测量',
  `Entry_Time` datetime DEFAULT NULL,
  PRIMARY KEY (`U_SportStep_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='与设备对接的计步';

CREATE TABLE `yh_user_sports_guide` (
  `Sports_Guide_Id` int NOT NULL AUTO_INCREMENT,
  `Start_Time` date NOT NULL COMMENT '开始时间',
  `End_Time` date NOT NULL COMMENT '结束时间',
  `Total_Energy` float NOT NULL DEFAULT '0' COMMENT '总能耗',
  `SType` int NOT NULL DEFAULT '0' COMMENT '类型',
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户code',
  `newly_Time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`Sports_Guide_Id`) USING BTREE,
  KEY `Ref9914` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='用户运动分析报告';

CREATE TABLE `yh_user_step` (
  `step_ID` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `user_type` int DEFAULT NULL,
  `healthstars` int DEFAULT '0',
  `healthlevelstars` int DEFAULT '0',
  `gold_coins` int DEFAULT '0',
  `silver_star` int DEFAULT '0',
  `gold_star` int DEFAULT '0',
  `gold_crown` int DEFAULT '0',
  `step_time` datetime DEFAULT NULL,
  `Long_time` int DEFAULT '0',
  `Stay_time` datetime DEFAULT NULL,
  PRIMARY KEY (`step_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户步数';

CREATE TABLE `yh_user_temperature` (
  `tid` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `temperature` float DEFAULT NULL COMMENT '体温',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '新增时间',
  `Is_Del` int DEFAULT NULL COMMENT '是否删除',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户',
  `Data_Source` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  PRIMARY KEY (`tid`) USING BTREE,
  KEY `E_User_Id` (`E_User_Id`) USING BTREE,
  KEY `Entry_Time` (`Entry_Time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='用户体温';

CREATE TABLE `yh_user_third_auth` (
  `id` int NOT NULL,
  `oauth_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `user_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `access_token` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `expire_in` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `oauth_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='第三方认证登录表，如：qq,微信认证登录';

CREATE TABLE `yh_user_tumourmarker` (
  `tumourmarker_Id` int NOT NULL AUTO_INCREMENT,
  `AFP` float DEFAULT NULL COMMENT '甲胎蛋白',
  `CEA` float DEFAULT NULL COMMENT '癌胚抗原',
  `CA19-9` float DEFAULT NULL COMMENT '癌抗原19-9',
  `CA-724` float DEFAULT NULL COMMENT '癌抗原724',
  `CA50` float DEFAULT NULL COMMENT '糖类抗原50',
  `CA242` float DEFAULT NULL COMMENT '糖类抗原242',
  `CA125` float DEFAULT NULL COMMENT '癌抗原125',
  `CA15-3` float DEFAULT NULL COMMENT '癌抗原15-3',
  `NSE` float DEFAULT NULL COMMENT '神经元特异性烯醇化酶',
  `CYFRA21-1` float DEFAULT NULL COMMENT '细胞角蛋白19片段测定',
  `proGRP` float DEFAULT NULL COMMENT '血清胃泌素释放肽前体测定',
  `SF` float DEFAULT NULL COMMENT '血清铁蛋白',
  `HCG` float DEFAULT NULL COMMENT '特异β人绒毛膜促性腺激素',
  `SCC` float DEFAULT NULL COMMENT '鳞状细胞癌抗原',
  `AEBVCA-IgA` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '抗EB病毒抗体VCA-IgA',
  `AEBEAI-IgA` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '抗EB病毒抗体EAIgA',
  `HE4` float DEFAULT NULL COMMENT '人附睾蛋白',
  `PAP` float DEFAULT NULL COMMENT '前列腺酸性磷酸酶测定',
  `PSA` float DEFAULT NULL COMMENT '总前列腺特异性抗原测定',
  `fPSA` float DEFAULT NULL COMMENT '游离前列腺特异性抗原测定',
  `T-PSA` float DEFAULT NULL COMMENT '前列腺特异性抗原',
  `F-PSA` float DEFAULT NULL COMMENT '游离前列腺特异性抗原',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`tumourmarker_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='血清肿瘤标志物 新';

CREATE TABLE `yh_user_uba` (
  `Uba_Id` int NOT NULL AUTO_INCREMENT,
  `addDate` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量日期',
  `env` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `glucose` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '葡萄糖',
  `bilirubin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '胆红素',
  `ketone` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '酮体',
  `proportion` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '比重',
  `redBloodCell` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '红细胞',
  `ph` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'ph值',
  `urineProtein` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿蛋白',
  `urobilinogen` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '尿胆原',
  `nitrite` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '亚硝酸盐',
  `whiteBloodCell` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '' COMMENT '白细胞',
  `E_User_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '关联用户',
  `Data_Source` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `creatinine` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '肌酐',
  `Microalbumin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '微白蛋白',
  `calcium_ion` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '钙离子',
  `vitamin` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '维生素',
  `Result` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '结果',
  `Is_Del` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '2' COMMENT '是否删除',
  PRIMARY KEY (`Uba_Id`) USING BTREE,
  KEY `Ref999` (`E_User_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='尿检信息表（对应一体机或随诊箱的上传数据）';

CREATE TABLE `yh_user_ultrasonictesting` (
  `ultrasonictesting_Id` int NOT NULL AUTO_INCREMENT,
  `typeid` int DEFAULT NULL COMMENT '1.肝、胆、脾、胰、肾（彩色）2.甲状腺彩超3.心脏彩超4.颈动脉彩超5.子宫及附件彩超 （未婚）6.阴道超声（已婚检测）7.乳腺彩超（双侧）',
  `imgAddress` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '图片地址;号分割',
  `diagnosisInfo` varchar(555) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '诊断录入信息',
  `Entry_Time` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量时间',
  `Is_Del` int NOT NULL DEFAULT '2' COMMENT '是否隐藏',
  `E_User_Id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属客户',
  `test_hj` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '测量环境',
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '数据来源',
  `aberrant` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '0正常 1异常',
  PRIMARY KEY (`ultrasonictesting_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='超声检查 新';

CREATE TABLE `yh_user_waistline` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Waistline` float(8,0) DEFAULT NULL,
  `Entry_Time` datetime DEFAULT NULL,
  `Is_Del` int DEFAULT NULL,
  `E_User_Id` int NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Refwell_enrol_userinfo68` (`E_User_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='腰围';

CREATE TABLE `yh_weeks` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Weeks` int DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT;

CREATE TABLE `yh_weeks_disease` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `The_Weeks` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Disease_Patterns` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `Intensity_Default` int DEFAULT NULL,
  `Intensity_Range_Min` int DEFAULT NULL,
  `Intensity_Range_Max` int DEFAULT NULL,
  `Energy_Min` float DEFAULT NULL,
  `Energy_Max` float DEFAULT NULL,
  `Sports_Time` int DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='疾病推荐训练';

CREATE TABLE `yh_weeks_training` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Weeks_Id` int NOT NULL,
  `Training_Id` int NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `RefWell_Weeks_Disease53` (`Weeks_Id`) USING BTREE,
  KEY `RefWell_Training_Methods54` (`Training_Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='一周的培训';

CREATE TABLE `yh_wx_information` (
  `wx_id` int NOT NULL AUTO_INCREMENT,
  `gz_appid` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '公众号appid',
  `gz_appsecret` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '公众号appsecret',
  `xc_appid` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '小程序appid',
  `xc_appsecret` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '小程序appsecret',
  `board_id` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '模板id',
  `variate1` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '变量1',
  `variate2` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '变量2',
  `variate3` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '变量3',
  `variate4` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '变量4',
  `variate5` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '变量5',
  `wx_type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '类型',
  `mb_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '模板名称',
  `info_type` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '(1,小程序模板  2，公告号模板)',
  PRIMARY KEY (`wx_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='微信消息模板';

CREATE TABLE `yh_wx_notify` (
  `wx_notify_id` int NOT NULL AUTO_INCREMENT,
  `wx_notify_content` varchar(10000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `wx_notify_file` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `wx_notify_backup` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '标题',
  `wx_notify_backup2` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`wx_notify_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE `yh_wx_setting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `appid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `appsecret` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `partner` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `partnerkey` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `link` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '域名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='微信配置';

CREATE TABLE `yh_wx_user` (
  `user_id` varchar(111) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `oppen_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '账号手机号',
  `realname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `add_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `area_id` int DEFAULT NULL,
  `area_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `member_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci ROW_FORMAT=COMPACT COMMENT='绑定微信用户';

SET FOREIGN_KEY_CHECKS=1;
