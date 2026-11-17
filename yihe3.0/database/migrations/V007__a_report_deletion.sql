-- Explicit report erasure: retain only IDs/counts/reason in deletion receipts.
DELIMITER $$
CREATE TABLE IF NOT EXISTS a_report_deletions (
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 actor_ref VARBINARY(128) NOT NULL,
 reason VARCHAR(600) NOT NULL,
 source_ids JSON NOT NULL,
 job_ids JSON NOT NULL,
 deleted_counts JSON NOT NULL,
 state ENUM('purging','deleted') NOT NULL,
 created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 completed_at DATETIME(6) NULL,
 KEY ix_report_deletions_scope(scope_code,data_mode,completed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='受控报告删除凭证；不保存报告正文'$$
CREATE TABLE IF NOT EXISTS a_report_file_cleanup_tasks (
 cleanup_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 target_kind ENUM('source','model_artifacts') NOT NULL,
 target_ref VARBINARY(512) NOT NULL,
 status ENUM('pending','done','failed') NOT NULL DEFAULT 'pending',
 attempts INT NOT NULL DEFAULT 0,
 last_error VARCHAR(100) NULL,
 created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 KEY ix_report_cleanup_queue(data_mode,status,updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='事务提交后的原件和模型候选清理队列'$$
DROP PROCEDURE IF EXISTS a_delete_health_report$$
CREATE PROCEDURE a_delete_health_report(IN p_report CHAR(36),IN p_scope VARCHAR(64),IN p_mode VARCHAR(16),IN p_actor VARBINARY(128),IN p_reason VARCHAR(600))
main: BEGIN
 DECLARE v_scope VARCHAR(64) DEFAULT NULL;
 DECLARE v_mode VARCHAR(16) DEFAULT NULL;
 DECLARE v_sources JSON;
 DECLARE v_jobs JSON;
 DECLARE v_risks JSON;
 DECLARE v_counts JSON;
 DECLARE v_source CHAR(36);
 DECLARE v_object VARBINARY(512);
 DECLARE v_index INT DEFAULT 0;
 IF p_reason IS NULL OR CHAR_LENGTH(TRIM(p_reason))=0 OR p_actor IS NULL THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Report deletion requires actor and reason';
 END IF;
 IF EXISTS(SELECT 1 FROM a_report_deletions WHERE report_id=p_report AND scope_code=p_scope AND data_mode=p_mode AND state='deleted') THEN LEAVE main; END IF;
 SELECT scope_code,data_mode INTO v_scope,v_mode FROM a_report_series WHERE report_id=p_report FOR UPDATE;
 IF v_scope IS NULL OR BINARY v_scope<>BINARY p_scope OR BINARY v_mode<>BINARY p_mode THEN
  SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Report not found in authorized scope';
 END IF;
 SELECT COALESCE(JSON_ARRAYAGG(x.source_file_id),JSON_ARRAY()) INTO v_sources FROM
  (SELECT source_file_id FROM a_report_series WHERE report_id=p_report UNION SELECT source_file_id FROM a_report_revision_sources WHERE report_id=p_report) x;
 SELECT COALESCE(JSON_ARRAYAGG(job_id),JSON_ARRAY()) INTO v_jobs FROM a_extraction_jobs WHERE report_id=p_report;
 SELECT COALESCE(JSON_ARRAYAGG(x.assessment_id),JSON_ARRAY()) INTO v_risks FROM
  (SELECT DISTINCT assessment_id FROM a_health_risk_report_sources WHERE report_id=p_report) x;
 SET v_counts=JSON_OBJECT('versions',(SELECT COUNT(*) FROM a_health_reports WHERE report_id=p_report),'items',(SELECT COUNT(*) FROM a_health_items WHERE report_id=p_report),'evidence',(SELECT COUNT(*) FROM a_report_evidence WHERE report_id=p_report),'jobs',JSON_LENGTH(v_jobs),'sources',JSON_LENGTH(v_sources));
 INSERT INTO a_report_deletions(report_id,scope_code,data_mode,actor_ref,reason,source_ids,job_ids,deleted_counts,state)
 VALUES(p_report,p_scope,p_mode,p_actor,p_reason,v_sources,v_jobs,v_counts,'purging');
 UPDATE a_ocr_dispatch_attempts a JOIN a_extraction_jobs j ON j.job_id=a.job_id SET a.outcome='cancelled',a.finished_at=UTC_TIMESTAMP(6),a.error_code='REPORT_DELETED',a.error_message='Report deleted' WHERE j.report_id=p_report AND a.outcome='running';
 UPDATE a_ocr_dispatch_tasks q JOIN a_extraction_jobs j ON j.job_id=q.job_id SET q.dispatch_state='cancelled',q.lease_until=NULL,q.last_error_code='REPORT_DELETED',q.last_error_message='Report deleted',q.updated_at=UTC_TIMESTAMP(6) WHERE j.report_id=p_report AND q.dispatch_state IN ('queued','running','retry_wait');
 DELETE FROM a_health_risk_factors WHERE JSON_CONTAINS(v_risks,JSON_QUOTE(assessment_id));
 DELETE FROM a_health_risk_report_sources WHERE JSON_CONTAINS(v_risks,JSON_QUOTE(assessment_id));
 DELETE FROM a_health_risk_assessments WHERE JSON_CONTAINS(v_risks,JSON_QUOTE(assessment_id));
 DELETE FROM a_followup_tasks WHERE report_id=p_report;
 DELETE FROM a_report_sync_outbox WHERE report_id=p_report;
 DELETE FROM a_report_publications WHERE report_id=p_report;
 DELETE FROM a_health_advice WHERE report_id=p_report;
 DELETE FROM a_health_conclusions WHERE report_id=p_report;
 DELETE FROM a_health_items WHERE report_id=p_report;
 DELETE FROM a_occupational_profiles WHERE report_id=p_report;
 DELETE FROM a_report_review_snapshots WHERE report_id=p_report;
 DELETE FROM a_report_quality_checks WHERE report_id=p_report;
 DELETE FROM a_review_records WHERE report_id=p_report;
 DELETE FROM a_report_evidence WHERE report_id=p_report;
 DELETE FROM a_report_revision_sources WHERE report_id=p_report;
 DELETE r FROM a_ehr_ocr_receipts r JOIN a_extraction_jobs j ON j.job_id=r.job_id WHERE j.report_id=p_report;
 DELETE a FROM a_ocr_dispatch_attempts a JOIN a_extraction_jobs j ON j.job_id=a.job_id WHERE j.report_id=p_report;
 DELETE q FROM a_ocr_dispatch_tasks q JOIN a_extraction_jobs j ON j.job_id=q.job_id WHERE j.report_id=p_report;
 DELETE FROM a_extraction_jobs WHERE report_id=p_report;
 UPDATE a_health_archive_audit_logs SET report_id=NULL,revision=NULL,redacted_diff=JSON_OBJECT('deletedReportId',p_report) WHERE report_id=p_report;
 UPDATE a_report_series SET current_revision=NULL WHERE report_id=p_report;
 DELETE FROM a_health_reports WHERE report_id=p_report ORDER BY revision DESC;
 DELETE FROM a_report_series WHERE report_id=p_report;
 WHILE v_index<JSON_LENGTH(v_sources) DO
  SET v_source=JSON_UNQUOTE(JSON_EXTRACT(v_sources,CONCAT('$[',v_index,']')));
  IF NOT EXISTS(SELECT 1 FROM a_report_series WHERE source_file_id=v_source) AND NOT EXISTS(SELECT 1 FROM a_report_revision_sources WHERE source_file_id=v_source) THEN
   SELECT object_ref INTO v_object FROM a_report_sources WHERE source_file_id=v_source;
   INSERT INTO a_report_file_cleanup_tasks(cleanup_id,scope_code,data_mode,report_id,target_kind,target_ref) VALUES(UUID(),p_scope,p_mode,p_report,'source',v_object);
   DELETE FROM a_report_upload_metadata WHERE source_file_id=v_source;
   DELETE FROM a_report_sources WHERE source_file_id=v_source;
  END IF;
  SET v_index=v_index+1;
 END WHILE;
 SET v_index=0;
 WHILE v_index<JSON_LENGTH(v_jobs) DO
  INSERT INTO a_report_file_cleanup_tasks(cleanup_id,scope_code,data_mode,report_id,target_kind,target_ref) VALUES(UUID(),p_scope,p_mode,p_report,'model_artifacts',CONVERT(JSON_UNQUOTE(JSON_EXTRACT(v_jobs,CONCAT('$[',v_index,']'))) USING binary));
  SET v_index=v_index+1;
 END WHILE;
 UPDATE a_report_deletions SET state='deleted',completed_at=UTC_TIMESTAMP(6) WHERE report_id=p_report;
END$$

-- Scoped deletion trigger guards; normal reviewed-data immutability is retained.
DROP TRIGGER a_ehr_ca603171f293464a9e$$
CREATE TRIGGER a_ehr_ca603171f293464a9e BEFORE DELETE ON a_report_sources FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.scope_code=OLD.scope_code AND d.data_mode=OLD.data_mode AND JSON_CONTAINS(d.source_ids,JSON_QUOTE(OLD.source_file_id)))) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
DROP TRIGGER a_ehr_b0681304f42d195e58$$
CREATE TRIGGER a_ehr_b0681304f42d195e58 BEFORE DELETE ON a_review_records FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
DROP TRIGGER a_ehr_1c7bc32a5eb1bd56b7$$
CREATE TRIGGER a_ehr_1c7bc32a5eb1bd56b7 BEFORE DELETE ON a_report_quality_checks FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
DROP TRIGGER a_ehr_84056fdc6f762f6a99$$
CREATE TRIGGER a_ehr_84056fdc6f762f6a99 BEFORE DELETE ON a_report_publications FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
DROP TRIGGER a_ehr_97cc6755ac8ed0dd60$$
CREATE TRIGGER a_ehr_97cc6755ac8ed0dd60 BEFORE DELETE ON a_report_evidence FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_ff7e5135de0f9fb5f7$$
CREATE TRIGGER a_ehr_ff7e5135de0f9fb5f7 BEFORE DELETE ON a_health_items FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_09f03f5e1c43272088$$
CREATE TRIGGER a_ehr_09f03f5e1c43272088 BEFORE DELETE ON a_health_conclusions FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_fbab3fca2b396e2938$$
CREATE TRIGGER a_ehr_fbab3fca2b396e2938 BEFORE DELETE ON a_health_advice FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_87702aa2686db69097$$
CREATE TRIGGER a_ehr_87702aa2686db69097 BEFORE DELETE ON a_occupational_profiles FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_c9d2c5c6751095cbbc$$
CREATE TRIGGER a_ehr_c9d2c5c6751095cbbc BEFORE DELETE ON a_report_revision_sources FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_ehr_a1ef09b94f1cd8ac9b$$
CREATE TRIGGER a_ehr_a1ef09b94f1cd8ac9b BEFORE DELETE ON a_health_reports FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END IF;
END$$
DROP TRIGGER a_snapshot_no_delete$$
CREATE TRIGGER a_snapshot_no_delete BEFORE DELETE ON a_report_review_snapshots FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d WHERE d.state='purging' AND d.report_id=OLD.report_id)) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only';
END IF;
END$$
DROP TRIGGER a_ocr_receipt_no_delete$$
CREATE TRIGGER a_ocr_receipt_no_delete BEFORE DELETE ON a_ehr_ocr_receipts FOR EACH ROW BEGIN
IF NOT (EXISTS(SELECT 1 FROM a_report_deletions d JOIN a_extraction_jobs j ON j.report_id=d.report_id WHERE d.state='purging' AND j.job_id=OLD.job_id)) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only';
END IF;
END$$
