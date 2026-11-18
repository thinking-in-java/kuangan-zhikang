-- Agent state is owned by the authenticated backend account; never trust model-supplied scope.
CREATE TABLE a_agent_conversations (
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端生成的智能体会话ID',
 scope_code VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '当前账号单位范围',
 data_mode ENUM('real','synthetic') NOT NULL COMMENT '正式或隔离测试数据',
 owner_user VARCHAR(96) NOT NULL COMMENT '后台登录账号编号',
 title VARCHAR(100) NOT NULL COMMENT '第一条提问生成的本地标题',
 lease_turn_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '正在执行的轮次，防止并发篡改上下文',
 lease_until DATETIME(6) NULL COMMENT 'UTC执行租约到期时间',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC创建时间',
 updated_at DATETIME(6) NOT NULL COMMENT 'UTC更新时间',
 PRIMARY KEY(conversation_id), KEY ix_agent_owner(scope_code,data_mode,owner_user,updated_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='数字健康师私有会话，不与其他账号共享';

CREATE TABLE a_agent_turns (
 turn_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '服务端对话轮次ID',
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '所属会话',
 request_key VARCHAR(96) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '前端重试使用同一幂等键',
 request_hash CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '消息与选中职工摘要，阻止幂等键换内容',
 user_message TEXT NOT NULL COMMENT '用户本轮原始提问',
 selected_record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NULL COMMENT '用户主动选中的职工，仅作为查询上下文',
 turn_status ENUM('running','completed','failed') NOT NULL COMMENT '执行状态',
 response_json JSON NULL COMMENT '返回给页面的文本、来源、工具轨迹和待确认操作',
 transcript_json JSON NULL COMMENT 'Responses输出及function_call_output，仅供服务端续接',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC开始时间',
 completed_at DATETIME(6) NULL COMMENT 'UTC结束时间',
 PRIMARY KEY(turn_id), UNIQUE KEY uq_agent_request(conversation_id,request_key),
 KEY ix_agent_turns(conversation_id,created_at),
 CONSTRAINT fk_agent_turn_conversation FOREIGN KEY(conversation_id) REFERENCES a_agent_conversations(conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='服务端保管对话历史，浏览器不能伪造工具输出';

CREATE TABLE a_agent_actions (
 action_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作预览ID，不交给模型确认',
 conversation_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作发起会话',
 turn_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '操作发起轮次',
 record_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '目标健康档案ID，执行时再次检查单位和权限',
 action_type ENUM('delete_employee','update_employee') NOT NULL COMMENT '白名单操作类型',
 action_status ENUM('pending','confirmed','cancelled') NOT NULL DEFAULT 'pending' COMMENT '只有人工确认接口可以执行',
 payload_json JSON NOT NULL COMMENT '待执行变更及删除原因',
 snapshot_json JSON NOT NULL COMMENT '姓名工号、行版本、报告数量及内容摘要',
 result_json JSON NULL COMMENT '执行回执；重复确认返回同一回执',
 expires_at DATETIME(6) NOT NULL COMMENT 'UTC十分钟后失效，过期需重新预览',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC创建时间',
 resolved_at DATETIME(6) NULL COMMENT 'UTC确认或取消时间',
 PRIMARY KEY(action_id), KEY ix_agent_actions(conversation_id,action_status),
 CONSTRAINT fk_agent_action_conversation FOREIGN KEY(conversation_id) REFERENCES a_agent_conversations(conversation_id),
 CONSTRAINT fk_agent_action_turn FOREIGN KEY(turn_id) REFERENCES a_agent_turns(turn_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='人工确认操作票据；不把删除权限交给模型';

CREATE TABLE a_agent_tool_logs (
 tool_log_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '工具调用记录ID',
 turn_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL COMMENT '所属轮次',
 tool_name VARCHAR(64) NOT NULL COMMENT '服务端注册工具名称',
 arguments_json JSON NOT NULL COMMENT '通过JSON参数校验的工具参数',
 outcome VARCHAR(32) NOT NULL COMMENT '成功或业务错误状态',
 duration_ms BIGINT NOT NULL COMMENT '工具执行耗时毫秒',
 created_at DATETIME(6) NOT NULL COMMENT 'UTC调用时间',
 PRIMARY KEY(tool_log_id), KEY ix_agent_tool_turn(turn_id,created_at),
 CONSTRAINT fk_agent_tool_turn FOREIGN KEY(turn_id) REFERENCES a_agent_turns(turn_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_bin COMMENT='真实业务工具审计，不记录API密钥';
