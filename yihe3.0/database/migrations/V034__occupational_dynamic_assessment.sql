-- Separate automated screening suggestions from signed medical risk assessments.
ALTER TABLE a_occupational_risk_tasks
 ADD COLUMN report_format VARCHAR(64) NULL,
 ADD COLUMN result_json JSON NULL,
 ADD COLUMN suggested_risk_level VARCHAR(16) NOT NULL DEFAULT 'unknown',
 ADD COLUMN screening_score INT NULL,
 ADD COLUMN completeness_score INT NULL,
 ADD COLUMN model_subject_ref VARCHAR(64) NULL,
 ADD COLUMN model_name VARCHAR(160) NULL,
 ADD COLUMN usage_json JSON NULL,
 ADD COLUMN duration_ms BIGINT NULL,
 ADD COLUMN claim_generation CHAR(36) NULL;
DELIMITER $$
CREATE TRIGGER trg_occupational_claim_protocol BEFORE UPDATE ON a_occupational_risk_tasks FOR EACH ROW
BEGIN
 IF NEW.report_format='occupational-screening-v1' AND NEW.ai_state='running' AND OLD.ai_state<>'running'
 AND (NEW.claim_generation IS NULL OR NEW.claim_generation <=> OLD.claim_generation) THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Occupational screening worker protocol requires fresh generation';
 END IF;
END$$
DELIMITER ;
