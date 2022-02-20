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

INSERT INTO roles VALUES
('564716bf-315e-4dd4-85dc-56e1d682b2e1','user'),
('683b9562-dc45-4e73-9ac5-ddb5cfd9c4d6','admin');

INSERT INTO users VALUES
('5f9f027c-68c3-49cc-b2ca-a8b0ecc3646a','admin','{bcrypt}$2a$10$9JJNeyuLyhFFwVkkHXxfZ.WMU2kzadeWJGBG8PnG1gCA1UgQ22vMO'),
('dfbcb1e5-fc35-48dc-af88-876f69a1743d','user1','{bcrypt}$2a$10$bEwyNwDae3vPOUCZ7wleiuJnWTKgQHpzBncR4mE1lm.giqXVpLxOq');

INSERT INTO user_role VALUES
('5f9f027c-68c3-49cc-b2ca-a8b0ecc3646a','683b9562-dc45-4e73-9ac5-ddb5cfd9c4d6'),
('dfbcb1e5-fc35-48dc-af88-876f69a1743d','564716bf-315e-4dd4-85dc-56e1d682b2e1');




