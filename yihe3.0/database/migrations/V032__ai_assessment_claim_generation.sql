-- New anonymized assessment jobs must be claimed by a worker that supplies a fresh generation.
-- Old background processes sharing the database cannot silently run the new protocol.
ALTER TABLE a_ai_exam_tasks ADD COLUMN claim_generation CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL;
DELIMITER $$
CREATE TRIGGER trg_ai_assessment_claim_protocol BEFORE UPDATE ON a_ai_exam_tasks FOR EACH ROW
BEGIN
 IF NEW.report_format='ai-health-assessment-v2' AND NEW.ai_state='running' AND OLD.ai_state<>'running'
    AND (NEW.claim_generation IS NULL OR NEW.claim_generation <=> OLD.claim_generation) THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Assessment worker protocol v2 requires a fresh claim generation';
 END IF;
END$$
DELIMITER ;
