CREATE TABLE IF NOT EXISTS a_digital_worker_settings (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 worker_id VARCHAR(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 settings_json JSON NOT NULL,
 row_version INT NOT NULL DEFAULT 0,
 updated_by VARCHAR(64) NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,worker_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_digital_worker_settings_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 worker_id VARCHAR(16) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 actor VARCHAR(64) NOT NULL,
 before_json JSON NOT NULL,
 after_json JSON NOT NULL,
 created_at DATETIME(6) NOT NULL,
 KEY ix_worker_settings_events(scope_code,data_mode,worker_id,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE a_digital_worker_ai_attempts ADD COLUMN worker_settings_json JSON NULL;
