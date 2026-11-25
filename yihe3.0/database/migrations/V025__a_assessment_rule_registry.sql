-- Tenant-scoped rule drafts and immutable content versions.
CREATE TABLE IF NOT EXISTS a_assessment_rule_heads (
 rule_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 rule_kind VARCHAR(30) NOT NULL,title VARCHAR(150) NOT NULL,indicator_key VARCHAR(100) NOT NULL,
 category VARCHAR(100) NOT NULL,active_revision INT NULL,draft_revision INT NULL,
 enabled TINYINT NOT NULL DEFAULT 0,row_version INT NOT NULL DEFAULT 1,
 created_by VARCHAR(100) NOT NULL,updated_by VARCHAR(100) NOT NULL,
 created_at DATETIME(6) NOT NULL,updated_at DATETIME(6) NOT NULL,
 KEY ix_assessment_rule_scope(scope_code,data_mode,rule_kind,enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_assessment_rule_versions (
 rule_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,revision INT NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,document_json JSON NOT NULL,
 created_by VARCHAR(100) NOT NULL,created_at DATETIME(6) NOT NULL,
 published_by VARCHAR(100) NULL,published_at DATETIME(6) NULL,publication_note TEXT NULL,
 PRIMARY KEY(rule_id,revision),KEY ix_assessment_rule_version(scope_code,data_mode,published_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_assessment_rule_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 rule_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,revision INT NOT NULL,
 action VARCHAR(30) NOT NULL,actor VARCHAR(100) NOT NULL,reason TEXT NOT NULL,created_at DATETIME(6) NOT NULL,
 KEY ix_assessment_rule_event(scope_code,data_mode,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_assessment_rule_mutations (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,request_key CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 payload_digest CHAR(64) NOT NULL,result_json JSON NULL,actor VARCHAR(100) NOT NULL,created_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,request_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS a_assessment_rule_locks (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,guard INT NOT NULL DEFAULT 0,
 PRIMARY KEY(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
