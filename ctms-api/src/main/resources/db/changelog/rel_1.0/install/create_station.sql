SET SEARCH_PATH TO ctms_pad;

--create station table
CREATE SEQUENCE ctms_pad.ctms_pad_station_sqc INCREMENT by 10;

create table ctms_pad.station
(
    id                       bigint                   default       nextval(('ctms_pad.ctms_pad_station_sqc'::text)::regclass),
    station_id               bigint                   not null,
    provider_id              varchar(16)              not null,
    supported_charging_modes varchar(3)[],
    timezone                 varchar(50),
    last_updated             timestamp with time zone not null,
    is_public                boolean default true,
    is_test_data             boolean default false not null
);

ALTER TABLE ctms_pad.station ADD CONSTRAINT pk_station PRIMARY KEY (id);
ALTER TABLE ctms_pad.station ADD CONSTRAINT unique_station_service UNIQUE (station_id, provider_id);

comment on column ctms_pad.station.supported_charging_modes is 'array with possible values ''AC1'', ''AC3'', ''DC''';

create index idx_station_station_id on ctms_pad.station (station_id);
create index idx_station_provider_id on ctms_pad.station (provider_id);