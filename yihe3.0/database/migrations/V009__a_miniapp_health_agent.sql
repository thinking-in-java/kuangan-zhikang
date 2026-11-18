-- Additive migration; existing admin conversations keep their current ownership.
ALTER TABLE a_agent_conversations
 ADD COLUMN client_type ENUM('admin','miniapp') NOT NULL DEFAULT 'admin' COMMENT '智能体访问渠道',
 ADD COLUMN subject_record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '个人对话固定绑定的本人档案',
 ADD COLUMN binding_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '创建对话时的有效身份绑定版本',
 ADD KEY ix_agent_personal(client_type,owner_user,data_mode,binding_id,updated_at),
 ADD CONSTRAINT ck_agent_personal_subject CHECK (client_type='admin' OR (subject_record_id IS NOT NULL AND binding_id IS NOT NULL));

ALTER TABLE a_agent_turns
 MODIFY COLUMN turn_status ENUM('queued','running','completed','failed') NOT NULL COMMENT '异步排队、执行、完成或失败',
 ADD COLUMN request_context_json JSON NULL COMMENT '本人报告上下文；身份不接受客户端指定',
 ADD COLUMN run_token CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '异步执行领取凭据，防止超时回写',
 ADD COLUMN started_at DATETIME(6) NULL COMMENT 'UTC异步开始时间',
 ADD COLUMN deadline_at DATETIME(6) NULL COMMENT 'UTC处理截止时间',
 ADD KEY ix_agent_queue(turn_status,created_at);
