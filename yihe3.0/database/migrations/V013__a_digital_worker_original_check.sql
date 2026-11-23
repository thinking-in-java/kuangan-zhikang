ALTER TABLE a_digital_worker_ai_attempts
 ADD COLUMN verification_json JSON NULL COMMENT '原件核对结果、逐项差异、页码、原文证据及覆盖范围';
