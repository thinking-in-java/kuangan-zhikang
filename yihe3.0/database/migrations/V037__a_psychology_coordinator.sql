-- Psychology digital employee: orchestration only; questionnaire releases remain authoritative.
CREATE TABLE IF NOT EXISTS a_psych_worker_policies (
 policy_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, title VARCHAR(120) NOT NULL,
 document_json JSON NOT NULL, enabled TINYINT NOT NULL DEFAULT 0,
 next_run_at DATETIME(6) NOT NULL, last_run_at DATETIME(6) NULL,
 last_error VARCHAR(200) NULL, approved_by VARCHAR(100) NOT NULL,
 row_version INT NOT NULL DEFAULT 1, created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 KEY ix_psych_policy_due(enabled,next_run_at), KEY ix_psych_policy_scope(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_psych_worker_items (
 item_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 source_key VARCHAR(150) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 response_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 item_kind VARCHAR(32) NOT NULL, title VARCHAR(120) NOT NULL,
 task_status VARCHAR(24) NOT NULL, priority VARCHAR(16) NOT NULL DEFAULT 'normal',
 emergency TINYINT NOT NULL DEFAULT 0, emergency_ack_by VARCHAR(100) NULL, emergency_ack_at DATETIME(6) NULL,
 owner_id VARCHAR(100) NULL, result_json JSON NULL, row_version INT NOT NULL DEFAULT 1,
 created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_psych_source(scope_code,data_mode,source_key),
 KEY ix_psych_item_scope(scope_code,data_mode,task_status,updated_at), KEY ix_psych_item_record(record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_psych_worker_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, item_id CHAR(36) NULL, record_id CHAR(36) NULL,
 action VARCHAR(60) NOT NULL, actor VARCHAR(100) NOT NULL, detail_json JSON NOT NULL,
 created_at DATETIME(6) NOT NULL, KEY ix_psych_event_scope(scope_code,data_mode,created_at),
 KEY ix_psych_event_item(item_id,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_psych_worker_handoffs (
 handoff_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 item_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, record_id CHAR(36) NOT NULL,
 worker_task_id CHAR(36) NOT NULL, source_worker VARCHAR(16) NOT NULL DEFAULT 'care',
 target_worker VARCHAR(16) NOT NULL DEFAULT 'plan', assessment_snapshot JSON NOT NULL,
 handoff_state VARCHAR(24) NOT NULL DEFAULT 'submitted', feedback_json JSON NULL,
 confirmed_by VARCHAR(100) NOT NULL, received_by VARCHAR(100) NULL, received_at DATETIME(6) NULL,
 row_version INT NOT NULL DEFAULT 1, created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_psych_handoff_item(item_id), KEY ix_psych_handoff_task(worker_task_id),
 KEY ix_psych_handoff_scope(scope_code,data_mode,record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_psych_worker_suggestions (
 suggestion_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, record_id CHAR(36) NOT NULL,
 item_id CHAR(36) NOT NULL, content TEXT NOT NULL, suggestion_state VARCHAR(20) NOT NULL DEFAULT 'reference',
 created_by VARCHAR(100) NOT NULL,
 row_version INT NOT NULL DEFAULT 1, created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 KEY ix_psych_suggestion_scope(scope_code,data_mode,record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
