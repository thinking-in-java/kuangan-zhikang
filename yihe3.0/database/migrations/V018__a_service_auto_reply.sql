CREATE TABLE a_service_reply_configs (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 draft_json JSON NOT NULL,
 draft_version INT NOT NULL DEFAULT 1,
 active_release_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_reply_releases (
 release_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 release_number INT NOT NULL,
 config_json JSON NOT NULL,
 actor_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 created_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_reply_release(scope_code,data_mode,release_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_reply_events (
 event_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 trigger_message_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 release_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 rule_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 outcome ENUM('queued','running','replied','handoff','skipped','failed') NOT NULL,
 reply_type VARCHAR(20) CHARACTER SET ascii NOT NULL,
 boundary VARCHAR(20) CHARACTER SET ascii NOT NULL,
 reply_message_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL,
 detail VARCHAR(300) NOT NULL,
 attempts INT NOT NULL DEFAULT 0,
 started_at DATETIME(6) NULL,
 created_at DATETIME(6) NOT NULL,
 completed_at DATETIME(6) NULL,
 UNIQUE KEY uq_reply_trigger(trigger_message_id),
 KEY ix_reply_queue(outcome,created_at),
 FOREIGN KEY(conversation_id) REFERENCES a_service_conversations(conversation_id),
 FOREIGN KEY(trigger_message_id) REFERENCES a_service_messages(message_id),
 FOREIGN KEY(release_id) REFERENCES a_service_reply_releases(release_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE a_service_messages MODIFY sender_role ENUM('employee','doctor','manager','ai_assisted','system','auto_reply') NOT NULL;
