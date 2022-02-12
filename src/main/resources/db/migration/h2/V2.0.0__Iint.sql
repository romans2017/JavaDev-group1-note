
CREATE TABLE notes (id UUID DEFAULT RANDOM_UUID() NOT NULL, name varchar(100),description varchar(10000), type varchar(30), primary key (id));
INSERT INTO notes(name,description,type) VALUES('My note','Это тестовая заметка.','PRIVATE');

