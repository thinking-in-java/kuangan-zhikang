ALTER TABLE a_digital_worker_tasks
 ADD COLUMN task_context_json JSON NULL COMMENT '医生补充的本次随访、方案或投诉事实',
 ADD COLUMN context_saved_at DATETIME(6) NULL,
 ADD COLUMN context_saved_by VARCHAR(64) NULL;
ALTER TABLE a_digital_worker_ai_attempts
 ADD COLUMN result_json JSON NULL COMMENT '按任务类型生成的结构化工作结果及依据';
