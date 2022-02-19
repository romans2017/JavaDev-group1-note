-- create data base
-- application roles dictionary
create table roles (
	--id        uuid DEFAULT gen_random_uuid() PRIMARY KEY,
	id        varchar(50) PRIMARY KEY not null,
	role_name varchar(50) not null
);

-- application users dictionary
create table users (
	id    varchar(50) PRIMARY KEY not null,
	name  varchar(100) not null,
	password   varchar(100) not null
);

-- user_role
create table user_role(
	user_id varchar(50) not null,
	role_id varchar(50) not null,
	CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id),
	CONSTRAINT fk_users_user_id FOREIGN KEY(user_id) REFERENCES users(id),
	CONSTRAINT fk_roles_role_id FOREIGN KEY(role_id) REFERENCES roles(id)
);