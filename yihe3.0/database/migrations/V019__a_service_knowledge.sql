CREATE TABLE a_service_kb_categories (
 category_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 code VARCHAR(40) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 name VARCHAR(80) NOT NULL,
 sort_order INT NOT NULL DEFAULT 0,
 row_version INT NOT NULL DEFAULT 1,
 UNIQUE KEY uq_kb_category(scope_code,data_mode,code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_kb_documents (
 document_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 category_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 seed_key VARCHAR(100) CHARACTER SET ascii COLLATE ascii_bin NULL,
 title VARCHAR(180) NOT NULL,
 keywords JSON NOT NULL,
 body_text MEDIUMTEXT NOT NULL,
 sources_json JSON NOT NULL,
 content_kind ENUM('reference','template','platform') NOT NULL,
 document_status ENUM('draft','published','archived') NOT NULL DEFAULT 'draft',
 ai_enabled BOOLEAN NOT NULL DEFAULT FALSE,
 adopted BOOLEAN NOT NULL DEFAULT FALSE,
 row_version INT NOT NULL DEFAULT 1,
 published_json JSON NULL,
 expires_on DATE NULL,
 updated_by VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 created_at DATETIME(6) NOT NULL,
 updated_at DATETIME(6) NOT NULL,
 deleted_at DATETIME(6) NULL,
 UNIQUE KEY uq_kb_seed(scope_code,data_mode,seed_key),
 KEY ix_kb_list(scope_code,data_mode,deleted_at,category_id),
 FOREIGN KEY(category_id) REFERENCES a_service_kb_categories(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_kb_versions (
 version_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 document_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 revision INT NOT NULL,
 action_name VARCHAR(30) CHARACTER SET ascii NOT NULL,
 snapshot_json JSON NOT NULL,
 actor_user VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 created_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_kb_revision(document_id,revision),
 FOREIGN KEY(document_id) REFERENCES a_service_kb_documents(document_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_kb_settings (
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 enabled BOOLEAN NOT NULL DEFAULT TRUE,
 top_k INT NOT NULL DEFAULT 4,
 min_score INT NOT NULL DEFAULT 18,
 category_ids JSON NULL,
 row_version INT NOT NULL DEFAULT 1,
 updated_at DATETIME(6) NOT NULL,
 PRIMARY KEY(scope_code,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE a_service_kb_usage (
 usage_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin PRIMARY KEY,
 document_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 data_mode ENUM('real','synthetic') NOT NULL,
 request_key VARCHAR(100) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
 channel_name VARCHAR(30) CHARACTER SET ascii NOT NULL,
 revision INT NOT NULL,
 created_at DATETIME(6) NOT NULL,
 UNIQUE KEY uq_kb_use(document_id,request_key),
 KEY ix_kb_usage(scope_code,data_mode,created_at),
 FOREIGN KEY(document_id) REFERENCES a_service_kb_documents(document_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
