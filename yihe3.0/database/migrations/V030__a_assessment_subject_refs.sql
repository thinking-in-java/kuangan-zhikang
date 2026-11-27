-- Temporary subject identifiers stay mapped to the employee only in this database.
ALTER TABLE a_ai_exam_tasks ADD COLUMN model_subject_ref VARCHAR(48) CHARACTER SET ascii COLLATE ascii_bin NULL, ADD UNIQUE KEY uq_ai_exam_subject_ref(model_subject_ref);
