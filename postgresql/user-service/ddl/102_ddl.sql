\c users

CREATE SCHEMA user_data
    AUTHORIZATION user_app_user;



CREATE TABLE user_data."user"
(
    user_id uuid NOT NULL,
    fio character varying(255) NOT NULL,
    mail character varying(255) UNIQUE NOT NULL,
    password character varying(255) NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NULL,
    role character varying(50) NOT NULL,
    status character varying(50) NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT unique_col UNIQUE (mail)
);

ALTER TABLE IF EXISTS user_data."user"
    OWNER to user_app_user;



CREATE TABLE user_data.verification_info
(
    verification_id uuid NOT NULL,
    code character varying NOT NULL,
    user_id uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    PRIMARY KEY (verification_id),
    CONSTRAINT "user_id_FK" FOREIGN KEY (user_id)
        REFERENCES user_data."user" (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS user_data.verification_info
    OWNER to user_app_user;


INSERT INTO user_data."user"(
	user_id, fio, mail, password, role, status, dt_create, dt_update)
	VALUES ('33804f24-9353-4742-b9ec-858c681f5af1', 'admin', 'mail123@mail',
	'$2a$10$R0U0Ox/GacqsRWHWaOvspuKhfDi1lJqhOeOtc80/uw66tTcRQrnRa',
	'ADMIN', 'ACTIVATED', '2024-11-16 13:24:20.015', '2024-11-16 13:24:20.015');
