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
    id bigint NOT NULL,
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
    id bigint NOT NULL,
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
    id bigint NOT NULL,
	version integer NOT NULL,
    person_id bigint,
    university_id text,
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
    id bigint NOT NULL,
	version integer NOT NULL,
    title text,
    description text,
    funding bigint,
    project_start date,
    project_end date,
    PRIMARY KEY (id),
    FOREIGN KEY (funding)
        REFERENCES damap.funding (id) MATCH SIMPLE
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
    id bigint NOT NULL,
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
    tools text,
    restricted_data_access text,
    personal_information boolean,
    sensitive_data boolean,
    sensitive_data_security text,
    legal_restrictions boolean,
    ethical_issues_exist boolean,
    committee_approved boolean,
    ethics_report text,
    ethical_compliance_statement text,
    external_storage_info text,
    restricted_access_info text,
    closed_access_info text,
    costs_exist boolean,
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

insert into damap.contributor_role values ('DataCollector');
insert into damap.contributor_role values ('DataCurator');
insert into damap.contributor_role values ('DataManager');
insert into damap.contributor_role values ('Distributor');
insert into damap.contributor_role values ('Editor');
insert into damap.contributor_role values ('HostingInstitution');
insert into damap.contributor_role values ('Producer');
insert into damap.contributor_role values ('ProjectLeader');
insert into damap.contributor_role values ('ProjectManager');
insert into damap.contributor_role values ('ProjectMember');
insert into damap.contributor_role values ('RegistrationAgency');
insert into damap.contributor_role values ('RegistrationAuthority');
insert into damap.contributor_role values ('RelatedPerson');
insert into damap.contributor_role values ('Researcher');
insert into damap.contributor_role values ('ResearchGroup');
insert into damap.contributor_role values ('RightsHolder');
insert into damap.contributor_role values ('Sponsor');
insert into damap.contributor_role values ('Supervisor');
insert into damap.contributor_role values ('WorkPackageLeader');
insert into damap.contributor_role values ('Other');

--------------------------------------------------------------

CREATE TABLE damap.contributor
(
    id bigint NOT NULL,
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

CREATE TABLE damap.cost_type
(
    type text NOT NULL,
    PRIMARY KEY (type)
);

ALTER TABLE damap.cost_type
    OWNER to damap;

insert into damap.cost_type values ('dataAcquisition');
insert into damap.cost_type values ('database');
insert into damap.cost_type values ('filebasedStorage');
insert into damap.cost_type values ('hardwareAndInfrastructure');
insert into damap.cost_type values ('legalAdvice');
insert into damap.cost_type values ('personnel');
insert into damap.cost_type values ('repository');
insert into damap.cost_type values ('sofwareLicense');
insert into damap.cost_type values ('training');
insert into damap.cost_type values ('other');

--------------------------------------------------------------

CREATE TABLE damap.cost
(
    id bigint NOT NULL,
	version integer NOT NULL,
    dmp_id bigint,
    title text,
    value bigint,
    currency_code text,
    description text,
    cost_type text,
    custom_type text,
    PRIMARY KEY (id),
    FOREIGN KEY (dmp_id)
        REFERENCES damap.dmp (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (cost_type)
         REFERENCES damap.cost_type (type) MATCH SIMPLE
         ON UPDATE NO ACTION
         ON DELETE NO ACTION
         NOT VALID
);

ALTER TABLE damap.cost
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.host
(
    id bigint NOT NULL,
	version integer NOT NULL,
	host_id text,
    dmp_id bigint,
    title text,
    retrieval_date date,
    discriminator text,
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

CREATE TABLE damap.external_storage
(
    id bigint NOT NULL,
	url text,
	backup_frequency text,
	storage_location text,
	backup_location text,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
        REFERENCES damap.host (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.external_storage
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.repository
(
    id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
        REFERENCES damap.host (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.repository
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.storage
(
    id bigint NOT NULL,
	url text,
	backup_frequency text,
	storage_location text,
	backup_location text,
    PRIMARY KEY (id),
    FOREIGN KEY (id)
        REFERENCES damap.host (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE damap.storage
    OWNER to damap;

--------------------------------------------------------------

CREATE TABLE damap.data_access
(
    access_type text NOT NULL,
    PRIMARY KEY (access_type)
);

ALTER TABLE damap.data_access
    OWNER to damap;


insert into damap.data_access values ('open');
insert into damap.data_access values ('restricted');
insert into damap.data_access values ('closed');

--------------------------------------------------------------

CREATE TABLE damap.dataset
(
    id bigint NOT NULL,
	version integer NOT NULL,
	dmp_id bigint,
	host_id bigint,
    title text,
    type text,
    data_size text,
    dataset_comment text,
    publish boolean,
    license text,
    start_date date,
    reference_hash text,
    data_access text,
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
        NOT VALID,
    FOREIGN KEY (data_access)
         REFERENCES damap.data_access (access_type) MATCH SIMPLE
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
    id bigint NOT NULL,
	version integer NOT NULL,
    university_id text,
    role text,
    start_date date,
    until_date date,
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

CREATE TABLE damap.access_management
(
    id bigint NOT NULL,
	version integer NOT NULL,
    dmp_id bigint,
    university_id text,
    identifier_id bigint,
    role text,
    start_date date,
    until_date date,
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

ALTER TABLE damap.access_management
    OWNER to damap;


--------------------------------------------------------------
-- create id sequence for hibernate
--------------------------------------------------------------

CREATE SEQUENCE damap.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;
