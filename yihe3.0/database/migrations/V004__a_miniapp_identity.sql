CREATE TABLE a_miniapp_users (
 mini_user_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '独立小程序用户UUID，与职工身份分开',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '正式与隔离测试',
 app_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端配置的小程序AppID',
 open_id VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '微信服务端核验的OpenID，不由客户端指定',
 union_id VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '微信返回时才保存，不作为本期绑定凭据',
 user_status ENUM('active','disabled') NOT NULL DEFAULT 'active' COMMENT '小程序账号状态',
 consent_version VARCHAR(64) NOT NULL COMMENT '用户明确勾选接受的协议版本',
 consent_at DATETIME(6) NOT NULL COMMENT 'UTC协议接受时间',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC用户首次登录',
 last_login_at DATETIME(6) NOT NULL COMMENT 'UTC最近登录',
 PRIMARY KEY(mini_user_id), UNIQUE KEY uq_wx_identity(data_mode,app_id,open_id), UNIQUE KEY uq_wx_user_mode(mini_user_id,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信登录身份；不自动生成职工档案、不保存session_key';

CREATE TABLE a_miniapp_sessions (
 mini_session_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '小程序会话UUID',
 mini_user_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '小程序用户UUID',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '会话数据模式',
 token_hash BINARY(32) NOT NULL COMMENT '随机Bearer令牌的SHA256，仅返回一次明文',
 expires_at DATETIME(6) NOT NULL COMMENT 'UTC过期时间',
 revoked TINYINT NOT NULL DEFAULT 0 COMMENT '0有效，1已注销',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC签发时间',
 PRIMARY KEY(mini_session_id), UNIQUE KEY uq_wx_token(token_hash), KEY ix_wx_session_user(mini_user_id,data_mode,revoked),
 CONSTRAINT fk_wx_session_user FOREIGN KEY(mini_user_id,data_mode) REFERENCES a_miniapp_users(mini_user_id,data_mode),
 CONSTRAINT ck_wx_revoked CHECK(revoked IN (0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信小程序独立登录态，未绑定也能持有但不能访问健康档案';

CREATE TABLE a_employee_binding_codes (
 binding_code_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '个人专属验证码签发UUID',
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '签发医生所属档案池',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '数据模式',
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '专属绑定目标职工档案',
 code_hash BINARY(32) NOT NULL COMMENT '高强度随机验证码摘要，不保存明文',
 code_status ENUM('issued','consumed','revoked') NOT NULL DEFAULT 'issued' COMMENT '过期由expires_at另行判断',
 issue_request_key VARCHAR(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '签发幂等键；重复请求不再返回明文',
 issued_by_ref VARBINARY(256) NOT NULL COMMENT '后台签发人',
 issued_at DATETIME(6) NOT NULL COMMENT 'UTC签发时间',
 expires_at DATETIME(6) NOT NULL COMMENT 'UTC失效时间',
 consumed_by CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '确认绑定时的小程序用户',
 consumed_at DATETIME(6) NULL COMMENT '真正确认时才消费验证码',
 revoked_at DATETIME(6) NULL COMMENT '重发或后台撤销时间',
 active_record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS (CASE WHEN code_status='issued' THEN record_id ELSE NULL END) STORED COMMENT '单人单个在用验证码约束',
 PRIMARY KEY(binding_code_id), UNIQUE KEY uq_binding_secret(data_mode,code_hash), UNIQUE KEY uq_binding_issue(scope_code,data_mode,issue_request_key), UNIQUE KEY uq_binding_active_code(data_mode,active_record_id),
 CONSTRAINT fk_code_record FOREIGN KEY(scope_code,data_mode,record_id) REFERENCES a_health_records(scope_code,data_mode,record_id),
 CONSTRAINT fk_code_consumer FOREIGN KEY(consumed_by,data_mode) REFERENCES a_miniapp_users(mini_user_id,data_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='后台逐人生成的企业身份验证码，确认后一次性消费';

CREATE TABLE a_miniapp_binding_challenges (
 challenge_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '确认挑战UUID',
 mini_user_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '只能由发起预览的同一小程序用户确认',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '数据模式',
 binding_code_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '已校验的个人验证码',
 challenge_hash BINARY(32) NOT NULL COMMENT '随机确认凭据摘要',
 profile_row_version INT UNSIGNED NOT NULL COMMENT '预览时的授权资料版本',
 profile_digest BINARY(32) NOT NULL COMMENT '预览资料摘要，防止确认过期信息',
 expires_at DATETIME(6) NOT NULL COMMENT 'UTC确认凭据失效，最多10分钟且不晚于验证码',
 used_at DATETIME(6) NULL COMMENT '确认成功后消费时间',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC预览时间',
 PRIMARY KEY(challenge_id), UNIQUE KEY uq_binding_challenge(challenge_hash), KEY ix_binding_challenge_user(mini_user_id,data_mode,expires_at),
 CONSTRAINT fk_challenge_user FOREIGN KEY(mini_user_id,data_mode) REFERENCES a_miniapp_users(mini_user_id,data_mode),
 CONSTRAINT fk_challenge_code FOREIGN KEY(binding_code_id) REFERENCES a_employee_binding_codes(binding_code_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='先校验后预览，再显式确认；不接受客户端回传资料覆盖';

CREATE TABLE a_miniapp_employee_bindings (
 binding_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '绑定记录UUID',
 mini_user_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '微信小程序用户',
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '实际绑定的企业档案池',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '数据模式',
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '复用原职工主档案，绝不另建重复档案',
 binding_code_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '本次消费的验证码',
 binding_status ENUM('active','revoked') NOT NULL DEFAULT 'active' COMMENT '后台人工解除后保留历史',
 confirmed_digest BINARY(32) NOT NULL COMMENT '职工确认的资料摘要',
 confirmed_at DATETIME(6) NOT NULL COMMENT 'UTC确认时间',
 revoked_at DATETIME(6) NULL COMMENT 'UTC人工解除时间',
 revoke_reason VARCHAR(600) NULL COMMENT '人工解除必填说明',
 active_user_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS (CASE WHEN binding_status='active' THEN mini_user_id ELSE NULL END) STORED COMMENT '一微信用户一个有效职工绑定',
 active_record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin GENERATED ALWAYS AS (CASE WHEN binding_status='active' THEN record_id ELSE NULL END) STORED COMMENT '一职工一个有效微信绑定',
 PRIMARY KEY(binding_id), UNIQUE KEY uq_wx_active_user(data_mode,active_user_id), UNIQUE KEY uq_wx_active_employee(data_mode,active_record_id),
 CONSTRAINT fk_binding_user FOREIGN KEY(mini_user_id,data_mode) REFERENCES a_miniapp_users(mini_user_id,data_mode),
 CONSTRAINT fk_binding_record FOREIGN KEY(scope_code,data_mode,record_id) REFERENCES a_health_records(scope_code,data_mode,record_id),
 CONSTRAINT fk_binding_code FOREIGN KEY(binding_code_id) REFERENCES a_employee_binding_codes(binding_code_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='正式职工绑定及历史；只后台可解除，不能自助换成他人身份';

CREATE TABLE a_miniapp_rate_limits (
 bucket_key BINARY(32) NOT NULL COMMENT '操作、模式、用户或IP的摘要，不存明文IP',
 window_start DATETIME NOT NULL COMMENT 'UTC固定限流窗口起点',
 attempt_count INT UNSIGNED NOT NULL COMMENT '独立事务累加，错误请求也计数',
 PRIMARY KEY(bucket_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='微信登录及身份验证限流，绑定事务回滚不清空计数';
