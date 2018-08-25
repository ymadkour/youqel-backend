create schema sec;
CREATE TABLE sec.person (ID VARCHAR(255) NOT NULL, ACTIVATED BOOLEAN, created_timestamp TIMESTAMP, display_name VARCHAR(255), EMAIL VARCHAR(255), ENABLED BOOLEAN, lock_expiry_date TIMESTAMP, login_attempts INTEGER, PASSWORD VARCHAR(255), password_expiry_date TIMESTAMP, password_expiry_notification_sent BOOLEAN, password_set_token VARCHAR(255), updated_timestamp TIMESTAMP, VERSION BIGINT, PRIMARY KEY (ID));
CREATE TABLE password (ID VARCHAR(255) NOT NULL, created_timestamp TIMESTAMP, PASSWORD VARCHAR(255), updated_timestamp TIMESTAMP, VERSION BIGINT, fk_person_id VARCHAR(255), PRIMARY KEY (ID));
CREATE TABLE sec.person_role (Person_ID VARCHAR(255), role VARCHAR(255));
ALTER TABLE password ADD CONSTRAINT FK_password_fk_person_id FOREIGN KEY (fk_person_id) REFERENCES sec.person (ID);
ALTER TABLE sec.person_role ADD CONSTRAINT FK_person_role_Person_ID FOREIGN KEY (Person_ID) REFERENCES sec.person (ID);