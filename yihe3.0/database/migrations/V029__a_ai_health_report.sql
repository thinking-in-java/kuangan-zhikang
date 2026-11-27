-- AI suggestions are separate from signed clinical risk assessments.
ALTER TABLE a_ai_exam_tasks
 ADD COLUMN report_format VARCHAR(40) NOT NULL DEFAULT 'legacy-text-v1',
 ADD COLUMN result_json JSON NULL,
 ADD COLUMN suggested_risk_level ENUM('unknown','low','medium','high','critical') NOT NULL DEFAULT 'unknown',
 ADD COLUMN model_name VARCHAR(120) NULL,
 ADD COLUMN usage_json JSON NULL;
