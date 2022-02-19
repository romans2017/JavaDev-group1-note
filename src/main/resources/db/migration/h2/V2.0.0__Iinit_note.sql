--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE notes (
    id varchar(50) NOT NULL,
    description varchar(10000),
    name varchar(100),
    primary key (id)
    );