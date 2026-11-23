-- Store the allow-listed archive snapshot used for each generated draft.
ALTER TABLE a_digital_worker_ai_attempts
 ADD COLUMN archive_context_json JSON NULL COMMENT '授权档案完整性摘要及数据来源；不包含报告原件或临床明细';
