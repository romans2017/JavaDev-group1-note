CREATE TABLE roles (
  id varchar(36) NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id));

CREATE TABLE users (
  id varchar(36) NOT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id));

CREATE TABLE user_role (
  user_id varchar(36) NOT NULL,
  role_id varchar(36) NOT NULL,
  KEY FKt7e7djp752sqn6w22i6ocqy6q (role_id),
  KEY FKj345gk1bovqvfame88rcx7yyx (user_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id));

CREATE TABLE notes (
  id varchar(36) NOT NULL,
  type varchar(255) DEFAULT NULL,
  name varchar(100) DEFAULT NULL,
  text varchar(10000) DEFAULT NULL,
  user_id varchar(36) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  PRIMARY KEY (id));