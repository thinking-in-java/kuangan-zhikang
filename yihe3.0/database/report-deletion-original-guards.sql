DELIMITER $$
CREATE TRIGGER a_ehr_ca603171f293464a9e BEFORE DELETE ON a_report_sources FOR EACH ROW BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END$$
CREATE TRIGGER a_ehr_b0681304f42d195e58 BEFORE DELETE ON a_review_records FOR EACH ROW BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END$$
CREATE TRIGGER a_ehr_1c7bc32a5eb1bd56b7 BEFORE DELETE ON a_report_quality_checks FOR EACH ROW BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END$$
CREATE TRIGGER a_ehr_84056fdc6f762f6a99 BEFORE DELETE ON a_report_publications FOR EACH ROW BEGIN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END$$
CREATE TRIGGER a_ehr_97cc6755ac8ed0dd60 BEFORE DELETE ON a_report_evidence FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_ff7e5135de0f9fb5f7 BEFORE DELETE ON a_health_items FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_09f03f5e1c43272088 BEFORE DELETE ON a_health_conclusions FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_fbab3fca2b396e2938 BEFORE DELETE ON a_health_advice FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_87702aa2686db69097 BEFORE DELETE ON a_occupational_profiles FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_c9d2c5c6751095cbbc BEFORE DELETE ON a_report_revision_sources FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_ehr_a1ef09b94f1cd8ac9b BEFORE DELETE ON a_health_reports FOR EACH ROW BEGIN
IF EXISTS (SELECT 1 FROM a_review_records r WHERE r.report_id=OLD.report_id AND r.revision=OLD.revision) THEN
SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'EHR reviewed data is immutable; append a new revision';
END IF;
END$$
CREATE TRIGGER a_snapshot_no_delete BEFORE DELETE ON a_report_review_snapshots FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only'; END$$
CREATE TRIGGER a_ocr_receipt_no_delete BEFORE DELETE ON a_ehr_ocr_receipts FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only'; END$$
