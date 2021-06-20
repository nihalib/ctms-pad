--liquibase formatted sql
CREATE SCHEMA ctms_pad;

-- create tariff table
CREATE SEQUENCE ctms_pad.ctms_pad_tariff_sqc INCREMENT 10 START 1;

create table ctms_pad.tariff
(
    id                      bigint          not null    default     nextval(('ctms_pad.ctms_pad_tariff_sqc'::text)::regclass),
    station_id              bigint          not null,
    tariff_id               varchar(50)     not null,
    location                varchar(50),
    currency_code           varchar(50),
    price                   varchar(50)
);
ALTER SEQUENCE ctms_pad.ctms_pad_tariff_sqc OWNED BY ctms_pad.tariff.id;

ALTER TABLE ctms_pad.tariff ADD CONSTRAINT pk_tariff PRIMARY KEY (id);
ALTER TABLE ctms_pad.tariff ADD CONSTRAINT unique_station_tariff_id UNIQUE (station_id, tariff_id);

create index idx_tariff_station_id on ctms_pad.tariff (station_id);
create index idx_tariff_location on ctms_pad.tariff (location);

-- create station table
-- create table ctms.station
-- (
--     id                       bigint  default nextval(('ctms_station_sqc'::text)::regclass),
--     station_id               varchar(100)             not null,
--     provider_id              varchar(16)              not null,
--     supported_charging_modes varchar(3)[],
--     timezone                 varchar(50),
--     last_updated             timestamp with time zone not null,
--     is_public                boolean default true
-- );
--
-- ALTER TABLE ctms.station
--     ADD CONSTRAINT pk_station PRIMARY KEY (id);
-- ALTER TABLE ctms.station
--     ADD CONSTRAINT unique_station UNIQUE (station_id, provider_id);
--
-- comment on column ctms.station.supported_charging_modes is 'array with possible values ''AC1'', ''AC3'', ''DC''';
--
-- create index idx_station_id on ctms.station (station_id);
-- create index idx_provider_id on ctms.station (provider_id);
--
-- create sequence ctms.ctms_station_sqc increment by 10;
--
--
-- -- create job table
-- create table ctms.job
-- (
--     id           integer default nextval(('ctms_batch_job_sqc'::text)::regclass),
--     job_name     varchar(50) not null,
--     provider_id  varchar(16) not null,
--     last_job_run timestamp with time zone
-- );
--
-- ALTER TABLE ctms.job
--     ADD CONSTRAINT pk_batch_job PRIMARY KEY (id);
-- ALTER TABLE ctms.job
--     ADD CONSTRAINT unique_job_name_provider UNIQUE (job_name, provider_id);
--
-- comment on column ctms.job.job_name is 'station, tariff, ...';
--
-- create sequence ctms.ctms_batch_job_sqc increment by 10;
--
--
-- -- create tariff table
-- create table ctms.tariff
-- (
--     id            bigint default nextval(('ctms_tariff_sqc'::text)::regclass),
--     tariff_id     varchar(50)              not null,
--     currency      varchar(3)               not null,
--     charging_mode varchar(3),
--     last_updated  timestamp with time zone not null,
--     provider_id   varchar(16)              not null
-- );
--
-- ALTER TABLE ctms.tariff
--     ADD CONSTRAINT pk_tariff PRIMARY KEY (id);
-- ALTER TABLE ctms.tariff
--     ADD CONSTRAINT charging_mode_check
--         CHECK (((charging_mode)::text = 'AC1'::text) OR ((charging_mode)::text = 'AC3'::text) OR
--                ((charging_mode)::text = 'DC'::text));
-- ALTER TABLE ctms.tariff
--     ADD CONSTRAINT tariff_provider_id_check
--         CHECK (((provider_id)::text = 'CPI'::text) OR ((provider_id)::text = 'DCS'::text) OR
--                ((provider_id)::text = 'NAV'::text));
--
-- comment on column ctms.tariff.charging_mode is 'on of ''AC1'', ''AC3'', ''DC''';
--
-- create sequence ctms.ctms_tariff_sqc increment by 10;
--
--
-- -- create station_tariff_group table
-- create table ctms.station_tariff_group
-- (
--     id         bigint default nextval(('ctms_station_tariff_group_sqc'::text)::regclass),
--     station_id bigint not null,
--     tariff_id  bigint not null,
--     group_id   integer
-- );
--
-- ALTER TABLE ctms.station_tariff_group
--     ADD CONSTRAINT pk_station_tariff_group PRIMARY KEY (id);
-- ALTER TABLE ctms.station_tariff_group
--     ADD CONSTRAINT unique_station_tariff_group UNIQUE (station_id, tariff_id, group_id);
--
-- ALTER TABLE ctms.station_tariff_group
--     ADD CONSTRAINT fk_station_tariff_group_station
--         FOREIGN KEY (station_id) REFERENCES ctms.station (id);
-- ALTER TABLE ctms.station_tariff_group
--     ADD CONSTRAINT fk_station_tariff_group_tariff
--         FOREIGN KEY (tariff_id) REFERENCES ctms.tariff (id);
--
-- create index ixfk_station_tariff_group_station on ctms.station_tariff_group (station_id);
-- create index ixfk_station_tariff_group_tariff on ctms.station_tariff_group (tariff_id);
-- create index idx_group on ctms.station_tariff_group (group_id);
--
-- create sequence ctms.ctms_station_tariff_group_sqc increment by 10;
--
--
-- -- create tariff_element table
-- create table ctms.tariff_element
-- (
--     id        bigint default nextval(('ctms_tariff_element_sqc'::text)::regclass),
--     tariff_id bigint
-- );
--
-- ALTER TABLE ctms.tariff_element
--     ADD CONSTRAINT pk_tariff_element PRIMARY KEY (id);
--
-- ALTER TABLE ctms.tariff_element
--     ADD CONSTRAINT fk_tariff_element_tariff
--         FOREIGN KEY (tariff_id) REFERENCES ctms.tariff (id) on update cascade on delete cascade;
--
-- create sequence ctms.ctms_tariff_element_sqc increment by 10;
--
--
-- -- create price_component table
-- create table ctms.price_component
-- (
--     id                    bigint default nextval(('ctms_price_component_sqc'::text)::regclass),
--     tariff_dimension_type varchar(50) not null,
--     price                 numeric     not null,
--     step_size             integer     not null,
--     tariff_element_id     bigint
-- );
--
-- ALTER TABLE ctms.price_component
--     ADD CONSTRAINT pk_price_component PRIMARY KEY (id);
--
-- ALTER TABLE ctms.price_component
--     ADD CONSTRAINT fk_price_component_tariff_element
--         FOREIGN KEY (tariff_element_id) REFERENCES ctms.tariff_element (id) on update cascade on delete cascade;
--
-- ALTER TABLE ctms.price_component
--     ADD CONSTRAINT price_dimension_type_check
--         CHECK (((tariff_dimension_type)::text = 'ENERGY'::text) OR
--                ((tariff_dimension_type)::text = 'FLAT'::text) OR
--                ((tariff_dimension_type)::text = 'PARKING_DURING_CHARGING'::text) OR
--                ((tariff_dimension_type)::text = 'PARKING_AFTER_CHARGING'::text) OR
--                ((tariff_dimension_type)::text = 'TIME'::text) OR
--                ((tariff_dimension_type)::text = 'SERVICE'::text) OR
--                ((tariff_dimension_type)::text = 'MINIMUM_PRICE'::text) OR
--                ((tariff_dimension_type)::text = 'MAXIMUM_PRICE'::text) OR
--                ((tariff_dimension_type)::text = 'SESSION_TIME'::text));
--
-- comment on column ctms.price_component.tariff_dimension_type is
--     'one of ENERGY, FLAT, PARKING_DURING_CHARGING, PARKING_AFTER_CHARGING, TIME, SERVICE, MINIMUM_PRICE, MAXIMUM_PRICE, SESSION_TIME';
--
-- create index ixfk_price_component_tariff_element on ctms.price_component (tariff_element_id);
-- create index ixfk_tariff_element_tariff on ctms.tariff_element (tariff_id);
--
-- create sequence ctms.ctms_price_component_sqc increment by 10;
--
--
-- -- create tariff_restriction table
-- create table ctms.tariff_restriction
-- (
--     id                bigint default nextval(('ctms_tariff_restriction_sqc'::text)::regclass),
--     start_time        time,
--     end_time          time,
--     start_date        date,
--     end_date          date,
--     min_kwh           numeric,
--     max_kwh           numeric,
--     min_power         numeric,
--     max_power         numeric,
--     min_duration      integer,
--     max_duration      integer,
--     day_of_week       varchar(10)[],
--     tariff_element_id bigint
-- );
--
-- ALTER TABLE ctms.tariff_restriction
--     ADD CONSTRAINT pk_tariff_restriction PRIMARY KEY (id);
--
-- ALTER TABLE ctms.tariff_restriction
--     ADD CONSTRAINT fk_tariff_restriction_tariff_element
--         FOREIGN KEY (tariff_element_id) REFERENCES ctms.tariff_element (id) on update cascade on delete cascade;
--
-- comment on column ctms.tariff_restriction.day_of_week is
--     'array with possible values ''MONDAY'', ''TUESDAY'', ''WEDNESDAY'', ''THURSDAY'', ''FRIDAY'', ''SATURDAY'', ''SUNDAY''';
--
-- create index ixfk_tariff_restriction_tariff_element on ctms.tariff_restriction (tariff_element_id);
-- create index idx_tariff_id on ctms.tariff (tariff_id);
--
-- create sequence ctms.ctms_tariff_restriction_sqc increment by 10;
--
--
-- -- create tax table
-- create table ctms.tax
-- (
--     id         bigint default nextval(('ctms_tax_sqc'::text)::regclass),
--     name       varchar(255) not null,
--     amount     numeric      not null,
--     applied_on varchar(50)  not null,
--     station_id bigint       not null
-- );
--
-- ALTER TABLE ctms.tax
--     ADD CONSTRAINT pk_tax PRIMARY KEY (id);
--
-- ALTER TABLE ctms.tax
--     ADD CONSTRAINT fk_tax_station
--         FOREIGN KEY (station_id) REFERENCES ctms.station (id) on update cascade on delete cascade;
--
-- ALTER TABLE ctms.tax
--     ADD CONSTRAINT applied_on_check
--         CHECK (applied_on = 'ENERGY' OR applied_on = 'TIME' OR applied_on = 'TOTAL');
--
-- comment on column ctms.tax.applied_on is 'ENERGY, TIME, TOTAL';
--
-- create index ixfk_tax_station on ctms.tax (station_id);
--
-- create sequence ctms.ctms_tax_sqc increment by 10;



