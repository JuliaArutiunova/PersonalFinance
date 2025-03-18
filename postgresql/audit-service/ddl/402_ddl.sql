\c audit

CREATE SCHEMA audit_data
    AUTHORIZATION audit_app_user;

CREATE TABLE audit_data.user_info
(
    user_id uuid NOT NULL,
    fio character varying(255) NOT NULL,
    mail character varying(255) NOT NULL,
    role character varying(50) NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE IF EXISTS audit_data.user_info
    OWNER to audit_app_user;

INSERT INTO audit_data.user_info (
    user_id, fio, mail, role)
    VALUES ('33804f24-9353-4742-b9ec-858c681f5af1', 'admin', 'mail123@mail','ADMIN');



CREATE TABLE IF NOT EXISTS audit_data.audit
(
    audit_id uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    user_id uuid NOT NULL,
    text character varying NOT NULL,
    essence_type character varying(50) NOT NULL,
    entity_id uuid NOT NULL,
    CONSTRAINT audit_pkey PRIMARY KEY (audit_id),
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES audit_data.user_info (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS audit_data.audit
    OWNER to audit_app_user;