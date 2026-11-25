-- Manual report identity matching state; original reports and review history are retained.
CREATE TABLE IF NOT EXISTS a_report_matching (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 matching_state ENUM('selection','confirmed','unmatched','flagged') NOT NULL,
 selected_record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 selected_profile_version INT NULL,
 report_digest CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 rule_version VARCHAR(40) NOT NULL,
 row_version INT NOT NULL DEFAULT 1,
 last_request_key VARCHAR(96) NOT NULL,
 last_payload_sha256 CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 updated_by VARCHAR(64) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,report_id,revision),
 KEY ix_report_matching_state(scope_code,data_mode,matching_state,updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
