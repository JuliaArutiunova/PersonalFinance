CREATE USER audit_app_user PASSWORD '13245';

CREATE DATABASE audit
    WITH
    OWNER = audit_app_user
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;