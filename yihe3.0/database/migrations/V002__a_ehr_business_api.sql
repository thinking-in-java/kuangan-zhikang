-- Business metadata is kept separate from the 19 frozen EHR core tables.
CREATE TABLE a_employee_roster_profiles (
 employee_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT 'a_employees主键；授权花名册资料，不来自报告自动覆盖',
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端确定的组织范围',
 data_mode ENUM('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '真实与测试数据隔离',
 employee_name VARCHAR(100) NOT NULL COMMENT '授权职工姓名',
 employee_no VARCHAR(96) NOT NULL COMMENT '组织内工号',
 gender ENUM('男','女','未填写') NOT NULL DEFAULT '未填写' COMMENT '授权资料性别',
 birth_date DATE NULL COMMENT '授权出生日期',
 phone VARCHAR(30) NULL COMMENT '授权联系电话',
 organization_name VARCHAR(255) NOT NULL COMMENT '单位显示名称；不决定访问权限',
 team_name VARCHAR(100) NULL COMMENT '班组',
 position_name VARCHAR(100) NULL COMMENT '岗位或工种',
 exposure_history TEXT NULL COMMENT '授权填报的危害接触史，不是AI风险结论',
 legacy_user_code VARCHAR(50) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '已校验同组织的旧用户标识；小程序只能访问本人',
 row_version INT UNSIGNED NOT NULL DEFAULT 1 COMMENT '乐观锁；编辑时必须传回',
 deleted_at DATETIME(6) NULL COMMENT 'UTC软删除时间，保留报告和审核历史',
 delete_reason VARCHAR(600) NULL COMMENT '软删除原因',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC建档时间',
 updated_at DATETIME(6) NOT NULL COMMENT 'UTC最后维护时间',
 PRIMARY KEY(employee_id),
 UNIQUE KEY uq_roster_employee_no(scope_code,data_mode,employee_no),
 UNIQUE KEY uq_roster_legacy(scope_code,data_mode,legacy_user_code),
 KEY ix_roster_page(scope_code,data_mode,deleted_at,created_at),
 CONSTRAINT fk_a_roster_employee FOREIGN KEY(scope_code,data_mode,employee_id) REFERENCES a_employees(scope_code,data_mode,employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='EHR业务授权花名册和生命周期；不修改旧职工表';

CREATE TABLE a_report_review_snapshots (
 report_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '逻辑报告ID',
 revision INT UNSIGNED NOT NULL COMMENT '报告版本',
 reviewed_digest BINARY(32) NOT NULL COMMENT '服务端规范化快照SHA256，与质量/审核/发布摘要一致',
 snapshot_json LONGTEXT NOT NULL COMMENT '完整核对快照，原值和证据均保留',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC快照时间',
 PRIMARY KEY(report_id,revision),
 CONSTRAINT fk_a_review_snapshot_report FOREIGN KEY(report_id,revision) REFERENCES a_health_reports(report_id,revision),
 CONSTRAINT ck_a_snapshot_json CHECK(JSON_VALID(snapshot_json))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='医生审核时的不可变内容快照';

CREATE TABLE a_ehr_mobile_sessions (
 session_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '会话UUID',
 token_hash BINARY(32) NOT NULL COMMENT '随机Bearer令牌的SHA256；不保存明文令牌',
 legacy_user_code VARCHAR(50) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '已验证密码的旧用户标识，不能由上传参数指定',
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '登录时确定的组织范围',
 data_mode ENUM('real','synthetic') COLLATE utf8mb4_0900_bin NOT NULL COMMENT '会话数据模式由服务端配置确定',
 expires_at DATETIME(6) NOT NULL COMMENT 'UTC失效时间，默认2小时',
 revoked TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '1已注销',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC签发时间',
 PRIMARY KEY(session_id), UNIQUE KEY uq_mobile_token(token_hash),
 KEY ix_mobile_expire(expires_at),
 CONSTRAINT ck_a_mobile_revoked CHECK(revoked IN(0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='健康档案小程序专用短期令牌，不使用旧明文工号token';

DELIMITER $$
CREATE TRIGGER a_snapshot_no_update BEFORE UPDATE ON a_report_review_snapshots FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only'; END$$
CREATE TRIGGER a_snapshot_no_delete BEFORE DELETE ON a_report_review_snapshots FOR EACH ROW BEGIN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Reviewed snapshot is append only'; END$$
DELIMITER ;
