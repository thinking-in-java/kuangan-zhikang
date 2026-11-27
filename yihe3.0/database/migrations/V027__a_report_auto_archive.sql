-- Administrative archiving is separate from clinical approval/publication.
CREATE TABLE IF NOT EXISTS a_report_archive_entries (
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 archive_status VARCHAR(24) NOT NULL,
 identity_status VARCHAR(24) NOT NULL,
 ocr_status VARCHAR(24) NOT NULL,
 quality_status VARCHAR(24) NOT NULL,
 archive_time DATETIME(6) NOT NULL,
 archive_version INT NOT NULL,
 source_digest CHAR(64) NOT NULL,
 quality_json JSON NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(report_id,revision),
 KEY ix_auto_archive_record(scope_code,data_mode,record_id,archive_time),
 KEY ix_auto_archive_state(scope_code,data_mode,archive_status,quality_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_report_archive_versions (
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 archive_version INT NOT NULL,
 source_digest CHAR(64) NOT NULL,
 snapshot_json JSON NOT NULL,
 quality_json JSON NOT NULL,
 created_at DATETIME(6) NOT NULL,
 PRIMARY KEY(report_id,revision,archive_version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_archive_exception_tasks (
 exception_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 issue_key VARCHAR(160) NOT NULL,
 category VARCHAR(32) NOT NULL,
 severity VARCHAR(24) NOT NULL,
 item_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 message VARCHAR(1200) NOT NULL,
 details_json JSON NOT NULL,
 task_status VARCHAR(24) NOT NULL DEFAULT 'open',
 acknowledged_by VARCHAR(64) NULL,
 acknowledged_at DATETIME(6) NULL,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(exception_id),
 UNIQUE KEY uq_archive_exception(report_id,revision,issue_key),
 KEY ix_archive_exception_queue(scope_code,data_mode,task_status,severity,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
