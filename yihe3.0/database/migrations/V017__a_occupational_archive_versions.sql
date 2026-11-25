-- Append-only occupational archive documents; no changes to source reports or roster.
CREATE TABLE IF NOT EXISTS a_occupational_archive_versions (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT UNSIGNED NOT NULL,
 document_json JSON NOT NULL,
 source_snapshot_json JSON NOT NULL,
 source_digest CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 document_status ENUM('draft','employee_confirmed','reviewed') NOT NULL,
 action_name VARCHAR(24) NOT NULL,
 actor_ref VARCHAR(100) NOT NULL,
 action_note VARCHAR(1000) NOT NULL,
 confirmation_source_id CHAR(36) NULL,
 request_key CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 payload_digest CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 created_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,record_id,revision),
 UNIQUE KEY uq_occ_request(scope_code,data_mode,request_key),
 KEY ix_occ_record(scope_code,data_mode,record_id,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
