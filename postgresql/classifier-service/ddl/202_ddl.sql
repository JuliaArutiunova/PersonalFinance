\c classifier

CREATE SCHEMA classifier_data
    AUTHORIZATION calssifier_app_user;


CREATE TABLE classifier_data.currency
(
    currency_id uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    title character varying(50) NOT NULL,
    description character varying(255) NOT NULL,
    PRIMARY KEY (currency_id),
    CONSTRAINT unique_currency UNIQUE (title)
);

ALTER TABLE IF EXISTS classifier_data.currency
    OWNER to calssifier_app_user;



CREATE TABLE classifier_data.operation_category
(
    operation_category_id uuid NOT NULL,
    dt_create timestamp(3) without time zone NOT NULL,
    dt_update timestamp(3) without time zone NOT NULL,
    title character varying(255) NOT NULL,
    PRIMARY KEY (operation_category_id),
    CONSTRAINT unique_category UNIQUE (title)
);

ALTER TABLE IF EXISTS classifier_data.operation_category
    OWNER to calssifier_app_user;