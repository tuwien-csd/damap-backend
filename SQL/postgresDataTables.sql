-- create database and role for postgres

CREATE ROLE damap WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';

CREATE DATABASE damap
    WITH
    OWNER = damap
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE SCHEMA damap
    AUTHORIZATION damap;

--------------------------------------------------------------
-- create basic data tables
--------------------------------------------------------------

CREATE TABLE damap.identifier_type
(
    type text NOT NULL,
    CONSTRAINT type PRIMARY KEY (type)
);

ALTER TABLE damap.identifier_type
    OWNER to damap;


insert into damap.identifier_type values ('ORCID');
insert into damap.identifier_type values ('ISNI');
insert into damap.identifier_type values ('OPENID');
insert into damap.identifier_type values ('OTHER');
insert into damap.identifier_type values ('HANDLE');
insert into damap.identifier_type values ('DOI');
insert into damap.identifier_type values ('ARK');
insert into damap.identifier_type values ('URL');
insert into damap.identifier_type values ('FUNDREF');

--------------------------------------------------------------

CREATE TABLE damap.identifier
(
    id bigserial NOT NULL,
    identifier text,
    type text,
    PRIMARY KEY (id),
    FOREIGN KEY (type)
        REFERENCES damap.identifier_type (type) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE damap.identifier
    OWNER to damap;

---------------------------------------------------------------

CREATE TABLE damap.funding_status
(
    status text NOT NULL,
    PRIMARY KEY (status)
);

ALTER TABLE damap.funding_status
    OWNER to damap;


insert into damap.funding_status values ('PLANNED');
insert into damap.funding_status values ('APPLIED');
insert into damap.funding_status values ('GRANTED');
insert into damap.funding_status values ('REJECTED');
insert into damap.funding_status values ('UNSPECIFIED');

---------------------------------------------------------------

CREATE TABLE damap.funding
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    funder_id bigint,
    funding_status text,
    grant_id bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (funder_id)
        REFERENCES damap.identifier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (grant_id)
        REFERENCES damap.identifier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
	FOREIGN KEY (funding_status)
        REFERENCES damap.funding_status (status) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.funding
    OWNER to damap;

---------------------------------------------------------------

CREATE TABLE damap.person
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    person_id bigint,
    university_id bigint,
    mbox text,
    first_name text,
    last_name text,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id)
        REFERENCES damap.identifier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.person
    OWNER to damap;

---------------------------------------------------------------

CREATE TABLE damap.project
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    title text,
    description text,
    funding bigint,
    start date,
    "end" date,
    leader bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (funding)
        REFERENCES damap.funding (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (leader)
        REFERENCES damap.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.project
    OWNER to damap;

---------------------------------------------------------------

CREATE TABLE damap.data_kind
(
    data_kind text NOT NULL,
    PRIMARY KEY (data_kind)
);

ALTER TABLE damap.data_kind
    OWNER to damap;


insert into damap.data_kind values ('UNKNOWN');
insert into damap.data_kind values ('NONE');
insert into damap.data_kind values ('SPECIFY');

---------------------------------------------------------------

CREATE TABLE damap.dmp
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    created date,
    modified date,
    title text,
    description text,
    project bigint,
    contact bigint,
    data_kind text,
    no_data_explanation text,
    metadata text,
    data_generation text,
    structure text,
    target_audience text,
    personal_information boolean,
    sensitive_data boolean,
    legal_restrictions boolean,
    ethical_issues_exist boolean,
    committee_approved boolean,
    ethics_report text,
    optional_statement text,
    PRIMARY KEY (id),
	FOREIGN KEY (contact)
        REFERENCES damap.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (data_kind)
        REFERENCES damap.data_kind (data_kind) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (project)
        REFERENCES damap.project (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.dmp
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.contributor_role
(
    role text NOT NULL,
    PRIMARY KEY (role)
);

ALTER TABLE damap.contributor_role
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.contributor
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    dmp_id bigint,
    person_id bigint,
	contributor_role text,
    PRIMARY KEY (id),
    FOREIGN KEY (dmp_id)
        REFERENCES damap.dmp (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (person_id)
        REFERENCES damap.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
	FOREIGN KEY (contributor_role)
        REFERENCES damap.contributor_role (role) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.contributor
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.host
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    dmp_id bigint,
    name text,
    date date,
    PRIMARY KEY (id),
    FOREIGN KEY (dmp_id)
        REFERENCES damap.dmp (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.host
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.dataset
(
    id bigserial NOT NULL,
	version integer NOT NULL,
	dmp_id bigint,
	host_id bigint,
    title text,
    type text,
    size text,
    comment text,
    publish boolean,
    license text,
    start date,
    reference_hash text,
    PRIMARY KEY (id),
    FOREIGN KEY (dmp_id)
        REFERENCES damap.dmp (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
	FOREIGN KEY (host_id)
        REFERENCES damap.host (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.dataset
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.function_role
(
    role text NOT NULL,
    PRIMARY KEY (role)
);

ALTER TABLE damap.function_role
    OWNER to damap;


insert into damap.function_role values ('ADMIN');
insert into damap.function_role values ('SUPPORT');
insert into damap.function_role values ('OWNER');
insert into damap.function_role values ('EDITOR');
insert into damap.function_role values ('GUEST');

--------------------------------------------------------------

CREATE TABLE damap.administration
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    university_id bigint,
    role text,
    "from" date,
    until date,
    PRIMARY KEY (id),
    FOREIGN KEY (role)
        REFERENCES damap.function_role (role) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.administration
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.access
(
    id bigserial NOT NULL,
	version integer NOT NULL,
    dmp_id bigint,
    university_id bigint,
    identifier_id bigint,
    role text,
    "from" date,
    until date,
    PRIMARY KEY (id),
    FOREIGN KEY (dmp_id)
        REFERENCES damap.dmp (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (identifier_id)
        REFERENCES damap.identifier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (role)
        REFERENCES damap.function_role (role) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.access
    OWNER to damap;

