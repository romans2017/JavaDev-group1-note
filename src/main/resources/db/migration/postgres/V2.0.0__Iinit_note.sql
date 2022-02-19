CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE notes (id varchar(50) NOT NULL,
name varchar(100),
text varchar(10000),
 type varchar(30),
 user_id UUID,
 primary key (id),
  FOREIGN KEY(user_id) REFERENCES users(id));
