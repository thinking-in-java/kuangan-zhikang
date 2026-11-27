CREATE TABLE IF NOT EXISTS a_archive_original_checks (
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 input_etag CHAR(64) NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 original_stamp CHAR(64) NOT NULL,
 check_status VARCHAR(24) NOT NULL DEFAULT 'queued',
 approved_item_ids JSON NOT NULL,
 checks_json JSON NULL,
 error_message VARCHAR(1000) NULL,
 attempt_count INT NOT NULL DEFAULT 0,
 retry_after DATETIME(6) NULL,
 started_at DATETIME(6) NULL,
 finished_at DATETIME(6) NULL,
 created_at DATETIME(6) NOT NULL,
 PRIMARY KEY(report_id,revision,input_etag),
 KEY ix_original_check_queue(data_mode,check_status,retry_after,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
