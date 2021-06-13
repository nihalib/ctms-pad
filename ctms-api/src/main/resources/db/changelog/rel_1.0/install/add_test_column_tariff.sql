SET SEARCH_PATH TO ctms_pad;

ALTER TABLE ctms_pad.tariff ADD COLUMN is_test_data boolean default false not null;

ALTER TABLE ctms_pad.tariff ADD CONSTRAINT fk_tariff_station FOREIGN KEY (station_id) REFERENCES ctms_pad.station
    ON UPDATE CASCADE ON DELETE CASCADE