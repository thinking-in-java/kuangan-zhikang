ALTER TABLE a_chronic_risk_tasks
 ADD COLUMN report_format VARCHAR(64) NULL,
 ADD COLUMN result_json JSON NULL,
 ADD COLUMN suggested_risk_level VARCHAR(16) NOT NULL DEFAULT 'unknown',
 ADD COLUMN model_subject_ref VARCHAR(64) NULL,
 ADD COLUMN model_name VARCHAR(160) NULL,
 ADD COLUMN usage_json JSON NULL,
 ADD COLUMN duration_ms BIGINT NULL,
 ADD COLUMN claim_generation CHAR(36) NULL,
 MODIFY COLUMN review_state ENUM('pending','confirmed','rejected') NOT NULL DEFAULT 'pending';
CREATE TABLE a_chronic_health_context (
 scope_code VARCHAR(100) NOT NULL,
 data_mode VARCHAR(32) NOT NULL,
 record_id CHAR(36) NOT NULL,
 context_json JSON NOT NULL,
 row_version INT NOT NULL DEFAULT 1,
 updated_by VARCHAR(100) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode,record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_chronic_health_context_events (
 event_id CHAR(36) PRIMARY KEY,
 scope_code VARCHAR(100) NOT NULL,
 data_mode VARCHAR(32) NOT NULL,
 record_id CHAR(36) NOT NULL,
 before_json JSON NOT NULL,
 after_json JSON NOT NULL,
 actor VARCHAR(100) NOT NULL,
 created_at DATETIME(6) NOT NULL,
 INDEX(record_id,created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
DELIMITER $$
CREATE TRIGGER trg_chronic_claim_protocol BEFORE UPDATE ON a_chronic_risk_tasks FOR EACH ROW
BEGIN
 IF NEW.report_format='chronic-screening-v1' AND NEW.ai_state='running' AND OLD.ai_state<>'running'
 AND (NEW.claim_generation IS NULL OR NEW.claim_generation <=> OLD.claim_generation) THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Chronic screening worker protocol requires fresh generation';
 END IF;
END$$
DELIMITER ;
