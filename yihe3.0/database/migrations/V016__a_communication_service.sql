-- Employee service conversations are independent of the legacy chat and AI-agent history.
CREATE TABLE a_service_conversations (
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 service_status ENUM('open','closed') NOT NULL DEFAULT 'open',
 assignee_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NULL,
 last_seq BIGINT NOT NULL DEFAULT 0,
 last_employee_seq BIGINT NOT NULL DEFAULT 0,
 last_staff_seq BIGINT NOT NULL DEFAULT 0,
 last_preview VARCHAR(300) NOT NULL DEFAULT '',
 row_version INT NOT NULL DEFAULT 1,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_service_employee(scope_code,data_mode,record_id),
 KEY ix_service_inbox(scope_code,data_mode,updated_at,conversation_id),
 FOREIGN KEY(record_id) REFERENCES a_health_records(record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_messages (
 message_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 seq BIGINT NOT NULL,
 sender_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 sender_role ENUM('employee','doctor','manager','ai_assisted','system') NOT NULL,
 body_text TEXT NOT NULL,
 request_key VARCHAR(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 payload_sha256 CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 draft_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 created_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_service_seq(conversation_id,seq),
 UNIQUE KEY uq_service_request(conversation_id,sender_user,request_key),
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_reads (
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 reader_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 last_read_seq BIGINT NOT NULL DEFAULT 0,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(conversation_id,reader_user),
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_attachments (
 attachment_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 uploader_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 message_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 original_name VARCHAR(200) NOT NULL,
 mime_type VARCHAR(80) CHARACTER SET ascii NOT NULL,
 byte_size BIGINT NOT NULL,
 sha256 CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 created_at DATETIME(6) NOT NULL,
 KEY ix_service_attachments(conversation_id,message_id),
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id),
 FOREIGN KEY(message_id) REFERENCES a_service_messages(message_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_reply_drafts (
 draft_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 author_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 based_on_seq BIGINT NOT NULL,
 draft_state ENUM('ready','sent') NOT NULL DEFAULT 'ready',
 body_text TEXT NOT NULL,
 safety_note VARCHAR(300) NOT NULL,
 created_at DATETIME(6) NOT NULL,
 KEY ix_service_drafts(conversation_id,author_user,created_at),
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
