--======================================================================================================--
insert into roles(id, role_name)
values(random_uuid(), 'ROLE_ADMIN');
insert into roles(id, role_name)
values(random_uuid(), 'ROLE_USER');
--======================================================================================================--
-- login: admin, pass: super_secret_password
insert into users(id, user_name, password)
values(random_uuid(), 'admin', '{bcrypt}$2y$10$W7a7gyWBWWxEOwzbzoo2seTUmMuJXdLZfn8J1Vt2UDWXHKwOrEjUu');
--======================================================================================================--
