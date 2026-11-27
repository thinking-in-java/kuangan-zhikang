-- Keep protocol generations enforced for every version of the anonymized assessment workflow.
DROP TRIGGER IF EXISTS trg_ai_assessment_claim_protocol;
DELIMITER $$
CREATE TRIGGER trg_ai_assessment_claim_protocol BEFORE UPDATE ON a_ai_exam_tasks FOR EACH ROW
BEGIN
 IF NEW.report_format LIKE 'ai-health-assessment-v%' AND NEW.ai_state='running' AND OLD.ai_state<>'running'
    AND (NEW.claim_generation IS NULL OR NEW.claim_generation <=> OLD.claim_generation) THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Assessment worker protocol requires a fresh claim generation';
 END IF;
END$$
DELIMITER ;
