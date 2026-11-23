CREATE TABLE IF NOT EXISTS a_digital_worker_tasks (
 task_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 worker_id VARCHAR(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 task_type VARCHAR(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 title VARCHAR(120) NOT NULL,
 member_code VARCHAR(100) NOT NULL DEFAULT '',
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 priority ENUM('normal','high','urgent') NOT NULL,
 instructions VARCHAR(2000) NOT NULL DEFAULT '',
 task_status ENUM('queued','in_progress','pending_review','completed','cancelled') NOT NULL DEFAULT 'queued',
 result_note VARCHAR(2000) NOT NULL DEFAULT '',
 created_by VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 request_key CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 payload_sha256 BINARY(32) NOT NULL,
 row_version INT NOT NULL DEFAULT 1,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_digital_worker_request(scope_code,data_mode,created_by,request_key),
 KEY ix_digital_worker_queue(scope_code,data_mode,worker_id,task_status,created_at),
 KEY ix_digital_worker_updates(scope_code,data_mode,updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS a_digital_worker_task_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 task_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 actor VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 action VARCHAR(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 from_status VARCHAR(24) CHARACTER SET ascii COLLATE ascii_bin NULL,
 to_status VARCHAR(24) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 note VARCHAR(2000) NOT NULL,
 created_at DATETIME(6) NOT NULL,
 KEY ix_digital_worker_events(task_id,created_at,event_id),
 CONSTRAINT fk_digital_worker_event_task FOREIGN KEY(task_id) REFERENCES a_digital_worker_tasks(task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
