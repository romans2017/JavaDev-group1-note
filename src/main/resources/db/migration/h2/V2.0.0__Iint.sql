
CREATE TABLE notes (id UUID DEFAULT RANDOM_UUID() NOT NULL,
name varchar(100),
text varchar(10000),
 type varchar(30),
 user_id UUID,
 primary key (id),
  FOREIGN KEY(user_id) REFERENCES users(id));


