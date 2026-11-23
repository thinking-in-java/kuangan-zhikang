CREATE TABLE IF NOT EXISTS a_digital_worker_ai_attempts (
 attempt_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 task_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 active_task_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 requested_by VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 task_version INT NOT NULL,
 request_state ENUM('queued','running','succeeded','failed','discarded') NOT NULL,
 lease_until DATETIME(6) NULL,
 model_name VARCHAR(160) NULL,
 output_text MEDIUMTEXT NULL,
 usage_json JSON NULL,
 error_code VARCHAR(64) NULL,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_digital_worker_ai_active(active_task_id),
 KEY ix_digital_worker_ai_pending(request_state,created_at),
 KEY ix_digital_worker_ai_task(task_id,created_at),
 CONSTRAINT fk_digital_worker_ai_task FOREIGN KEY(task_id) REFERENCES a_digital_worker_tasks(task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
