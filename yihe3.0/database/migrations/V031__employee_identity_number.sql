-- Optional roster identity; NULL uses the authorized legacy account fallback, empty string explicitly clears it.
ALTER TABLE a_employee_roster_profiles ADD COLUMN identity_number VARCHAR(18) NULL;
CREATE INDEX ix_roster_identity ON a_employee_roster_profiles(scope_code,data_mode,identity_number);
