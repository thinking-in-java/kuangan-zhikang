CREATE TABLE a_service_conversation_details (
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 category ENUM('health','report','review','plan','other') NOT NULL DEFAULT 'health',
 satisfaction_score TINYINT NULL,
 rated_by VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NULL,
 rated_at DATETIME(6) NULL,
 updated_at DATETIME(6) NOT NULL,
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id),
 CHECK(satisfaction_score IS NULL OR satisfaction_score BETWEEN 1 AND 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
