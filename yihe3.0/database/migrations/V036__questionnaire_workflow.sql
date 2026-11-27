-- Questionnaire configuration, durable analysis queue, clinical review and intervention outbox.
CREATE TABLE IF NOT EXISTS a_questionnaire_center_state (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL, document_json JSON NOT NULL,
 row_version INT NOT NULL DEFAULT 1, updated_by VARCHAR(100) NOT NULL, updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_processing (
 response_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL, data_mode ENUM('real','synthetic') NOT NULL,
 score_json JSON NOT NULL, ai_state ENUM('queued','running','completed','failed','disabled') NOT NULL,
 ai_output TEXT NULL, ai_error VARCHAR(120) NULL, attempts INT NOT NULL DEFAULT 0,
 claim_token CHAR(36) NULL, started_at DATETIME(6) NULL, finished_at DATETIME(6) NULL,
 alert_state VARCHAR(30) NOT NULL DEFAULT 'none', intervention_json JSON NULL,
 archived TINYINT NOT NULL DEFAULT 0, row_version INT NOT NULL DEFAULT 1,
 created_at DATETIME(6) NOT NULL, updated_at DATETIME(6) NOT NULL,
 KEY ix_questionnaire_ai(ai_state,updated_at), KEY ix_questionnaire_processing_scope(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS a_questionnaire_deliveries (
 delivery_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 assignment_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,data_mode ENUM('real','synthetic') NOT NULL,
 delivery_kind VARCHAR(30) NOT NULL, delivery_state VARCHAR(30) NOT NULL,
 created_at DATETIME(6) NOT NULL, KEY ix_questionnaire_delivery(assignment_id,delivery_kind)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
