-- Sql to be executed from favorite db client (pgadmin for example)
-- Schema creation
CREATE SCHEMA IF NOT EXISTS application AUTHORIZATION test;
GRANT ALL ON SCHEMA application TO test;

--reset everything
--recreate dbs
DROP TABLE IF EXISTS application.user_tracking_behaviour CASCADE;
DROP TABLE IF EXISTS application.product_popularity CASCADE;
DROP TABLE IF EXISTS application.product CASCADE;
DROP TABLE IF EXISTS application.user CASCADE;

--recreate sequences
DROP SEQUENCE IF EXISTS application.seq_user_tracking_behaviour;
DROP SEQUENCE IF EXISTS application.seq_product_popularity;
DROP SEQUENCE IF EXISTS application.seq_product;
DROP SEQUENCE IF EXISTS application.seq_user;

--Create table user
CREATE TABLE application.user (
id bigint NOT NULL,
version integer NOT NULL DEFAULT 0,
creation_date timestamp without time zone NOT NULL,
activity_time character varying(15) NOT NULL,
credit_card_type character varying(15) NOT NULL, 
user_device character varying(15) NOT NULL,
user_group character varying(15) NOT NULL,
authorisation character varying(5) NOT NULL,
country_iso character varying(2) NOT NULL,
gender character varying(20) NOT NULL,
age int NOT NULL,
CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE application.user OWNER TO test;
GRANT ALL ON TABLE application.user TO test;

CREATE SEQUENCE application.seq_user START 1000;
ALTER TABLE application.seq_user OWNER TO test;

-- Create table product
CREATE TABLE application.product (
id bigint NOT NULL,
version integer NOT NULL DEFAULT 0,
creation_date timestamp without time zone NOT NULL,
name character varying(20) NOT NULL,
star_rating integer NOT NULL DEFAULT 0,
description character varying(50) NOT NULL,
status character varying(20) NOT NULL,
CONSTRAINT product_pkey PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE application.product OWNER TO test;
GRANT ALL ON TABLE application.product TO test;

CREATE SEQUENCE application.seq_product START 1000;
ALTER TABLE application.seq_product OWNER TO test;

-- Create table product_popularity
CREATE TABLE application.product_popularity (
id bigint NOT NULL,
version integer NOT NULL DEFAULT 0,
creation_date timestamp without time zone NOT NULL,
product_id bigint NOT NULL,
popularity_rating character varying(20) NOT NULL,
year integer NOT NULL DEFAULT 0,
month integer NOT NULL DEFAULT 0,
CONSTRAINT product_popularity_pkey PRIMARY KEY (id),
CONSTRAINT product_fkey FOREIGN KEY (product_id)
REFERENCES application.product (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE application.product_popularity OWNER TO test;
GRANT ALL ON TABLE application.product_popularity TO test;

CREATE SEQUENCE application.seq_product_popularity START 1000;
ALTER TABLE application.seq_product_popularity OWNER TO test;

-- Create table user_tracking_behaviour
CREATE TABLE application.user_tracking_behaviour (
id bigint NOT NULL,
version integer NOT NULL DEFAULT 0,
creation_date timestamp without time zone NOT NULL,
product_id bigint NOT NULL,
user_id bigint NOT NULL,
product_interaction character varying(20) NOT NULL,
CONSTRAINT user_tracking_behaviour_pkey PRIMARY KEY (id),
CONSTRAINT product_fkey FOREIGN KEY (product_id)
REFERENCES application.product (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT user_fkey FOREIGN KEY (user_id)
REFERENCES application.user (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE application.user_tracking_behaviour OWNER TO test;
GRANT ALL ON TABLE application.user_tracking_behaviour TO test;

CREATE SEQUENCE application.seq_user_tracking_behaviour START 1000;
ALTER TABLE application.seq_user_tracking_behaviour OWNER TO test;
