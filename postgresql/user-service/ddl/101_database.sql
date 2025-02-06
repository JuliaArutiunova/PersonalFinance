CREATE USER user_app_user PASSWORD '13245';

CREATE DATABASE users
    WITH
    OWNER = user_app_user
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;