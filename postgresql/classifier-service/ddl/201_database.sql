CREATE USER calssifier_app_user PASSWORD '13245';

CREATE DATABASE classifier
    WITH
    OWNER = calssifier_app_user
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;