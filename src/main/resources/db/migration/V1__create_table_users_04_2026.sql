DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE USERS (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email varchar(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username varchar(100) NOT NULL
);
