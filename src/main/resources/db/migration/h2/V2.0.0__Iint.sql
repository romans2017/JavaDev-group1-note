
CREATE TABLE notes (id UUID DEFAULT RANDOM_UUID() NOT NULL, description varchar(10000), name varchar(100), primary key (id));
