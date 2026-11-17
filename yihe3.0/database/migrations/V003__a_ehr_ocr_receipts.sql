CREATE TABLE a_ehr_ocr_receipts (
 job_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '识别任务ID，绑定该任务的报告版本',
 payload_sha256 BINARY(32) NOT NULL COMMENT '规范化回调JSON摘要，保护重复回调幂等性',
 response_json LONGTEXT NOT NULL COMMENT 'OCR候选响应快照，医学数值按原始字符串保存',
 received_at DATETIME(6) NOT NULL COMMENT 'UTC成功接收时间',
 PRIMARY KEY(job_id),
 CONSTRAINT fk_a_ocr_receipt_job FOREIGN KEY(job_id) REFERENCES a_extraction_jobs(job_id),
 CONSTRAINT ck_a_ocr_receipt_json CHECK(JSON_VALID(response_json))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='外部OCR成功回调原始候选快照；不代表审核或归档';
DELIMITER $$
CREATE TRIGGER a_ocr_receipt_no_update BEFORE UPDATE ON a_ehr_ocr_receipts FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only'; END$$
CREATE TRIGGER a_ocr_receipt_no_delete BEFORE DELETE ON a_ehr_ocr_receipts FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='OCR receipt is append only'; END$$
DELIMITER ;
