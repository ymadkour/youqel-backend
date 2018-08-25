CREATE TABLE contact_information (ID TEXT NOT NULL, COMPANY TEXT, created_timestamp TIMESTAMP, MOBILE TEXT, NAME TEXT, updated_timestamp TIMESTAMP, VERSION BIGINT, PRIMARY KEY (ID));
CREATE TABLE pickup_request (ID TEXT NOT NULL, created_timestamp TIMESTAMP, DETAILS TEXT, drop_off_location TEXT, pick_up_date TIMESTAMP, pick_up_location TEXT, PICKUPTRUCKTYPE TEXT, updated_timestamp TIMESTAMP, VERSION BIGINT, fk_receipient_id TEXT, fk_shipper_id TEXT, PRIMARY KEY (ID));
ALTER TABLE pickup_request ADD CONSTRAINT FK_pickup_request_fk_shipper_id FOREIGN KEY (fk_shipper_id) REFERENCES contact_information (ID);
ALTER TABLE pickup_request ADD CONSTRAINT FK_pickup_request_fk_receipient_id FOREIGN KEY (fk_receipient_id) REFERENCES contact_information (ID);
