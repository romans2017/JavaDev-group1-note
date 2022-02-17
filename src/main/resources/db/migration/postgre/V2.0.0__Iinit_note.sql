CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE notes (id uuid DEFAULT uuid_generate_v4 () NOT NULL, description varchar(10000), name varchar(100), primary key (id));