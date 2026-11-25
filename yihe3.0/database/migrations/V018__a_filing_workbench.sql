-- Separate document streams; existing employee/report data is unchanged.
ALTER TABLE a_occupational_archive_versions MODIFY document_status ENUM('draft','pending_employee','employee_confirmed','reviewed','archived') NOT NULL;
CREATE TABLE IF NOT EXISTS a_general_archive_versions LIKE a_occupational_archive_versions;
