-- create data base
-- application roles dictionary
create table roles (
	id        uuid DEFAULT RANDOM_UUID() PRIMARY KEY,
	role_name varchar(50) not null
);

-- application users dictionary
create table users (
	id         uuid DEFAULT RANDOM_UUID() PRIMARY KEY,
	user_name  varchar(100) not null,
	password   varchar(100) not null
);

-- user_role
create table user_role(
	user_id uuid not null,
	role_id uuid not null,
	CONSTRAINT fk_users_user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
	CONSTRAINT fk_roles_role_id FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE CASCADE
);