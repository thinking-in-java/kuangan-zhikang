-- Scoped questionnaires, immutable releases and one response per assignment.
CREATE TABLE IF NOT EXISTS a_questionnaires (
 questionnaire_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 title VARCHAR(120) NOT NULL,category VARCHAR(30) NOT NULL,audience VARCHAR(255) NOT NULL,
 document_json JSON NOT NULL,questionnaire_status ENUM('draft','active','archived','deleted') NOT NULL DEFAULT 'draft',
 row_version INT NOT NULL DEFAULT 1,created_by VARCHAR(100) NOT NULL,updated_by VARCHAR(100) NOT NULL,
 created_at DATETIME(6) NOT NULL,updated_at DATETIME(6) NOT NULL,
 KEY ix_questionnaire_scope(scope_code,data_mode,questionnaire_status,updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_releases (
 release_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 questionnaire_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,document_json JSON NOT NULL,release_status ENUM('active','archived') NOT NULL,
 published_by VARCHAR(100) NOT NULL,published_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_questionnaire_release(questionnaire_id,revision)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_assignments (
 assignment_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 release_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 due_date DATE NULL,assigned_by VARCHAR(100) NOT NULL,assigned_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_questionnaire_recipient(release_id,record_id),
 KEY ix_questionnaire_assignment(scope_code,data_mode,record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_responses (
 response_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 assignment_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 answers_json JSON NOT NULL,total_score DECIMAL(12,2) NOT NULL,
 submitted_by VARCHAR(100) NOT NULL,submission_channel ENUM('self','proxy') NOT NULL,
 source_description VARCHAR(1000) NOT NULL,submitted_at DATETIME(6) NOT NULL,
 review_state ENUM('pending','confirmed','rejected') NOT NULL DEFAULT 'pending',
 risk_level ENUM('unknown','low','medium','high','critical') NOT NULL DEFAULT 'unknown',
 review_note TEXT NULL,reviewer VARCHAR(100) NULL,reviewed_at DATETIME(6) NULL,row_version INT NOT NULL DEFAULT 1,
 UNIQUE KEY uq_questionnaire_answer(assignment_id),KEY ix_questionnaire_response(scope_code,data_mode,submitted_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_assets (
 asset_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 asset_kind ENUM('question','scale') NOT NULL,title VARCHAR(120) NOT NULL,document_json JSON NOT NULL,
 row_version INT NOT NULL DEFAULT 1,deleted TINYINT NOT NULL DEFAULT 0,
 updated_by VARCHAR(100) NOT NULL,updated_at DATETIME(6) NOT NULL,
 KEY ix_questionnaire_asset(scope_code,data_mode,asset_kind,deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_mutations (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 request_key CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,payload_digest CHAR(64) NOT NULL,
 result_json JSON NULL,actor VARCHAR(100) NOT NULL,created_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,request_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
