-- Additive collaboration ledger. Existing business queues are not rewritten.
CREATE TABLE IF NOT EXISTS a_worker_orders (
 order_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 order_no VARCHAR(48) CHARACTER SET ascii COLLATE ascii_bin NOT NULL UNIQUE,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 parent_id CHAR(36) NULL, record_id CHAR(36) NOT NULL, report_id CHAR(36) NULL,
 source_worker VARCHAR(16) NOT NULL, target_worker VARCHAR(16) NOT NULL,
 order_kind VARCHAR(40) NOT NULL, title VARCHAR(120) NOT NULL, instructions VARCHAR(2000) NOT NULL,
 priority VARCHAR(16) NOT NULL, due_at DATETIME(6) NOT NULL,
 order_state VARCHAR(24) NOT NULL DEFAULT 'draft', payload_json JSON NOT NULL,
 result_json JSON NULL, created_by VARCHAR(100) NOT NULL, claimed_by VARCHAR(100) NULL,
 accepted_by VARCHAR(100) NULL, emergency TINYINT NOT NULL DEFAULT 0,
 emergency_ack_by VARCHAR(100) NULL, row_version INT NOT NULL DEFAULT 1,
 created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 KEY ix_worker_order_scope(scope_code,data_mode,target_worker,order_state),
 KEY ix_worker_order_sent(scope_code,data_mode,source_worker,created_at),
 KEY ix_worker_order_record(scope_code,data_mode,record_id), KEY ix_worker_order_due(order_state,due_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_worker_order_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 order_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 action VARCHAR(32) NOT NULL, actor VARCHAR(100) NOT NULL, acting_worker VARCHAR(16) NOT NULL,
 from_state VARCHAR(24) NULL, to_state VARCHAR(24) NOT NULL, detail_json JSON NOT NULL,
 created_at DATETIME(6) NOT NULL, KEY ix_worker_order_event(order_id,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
