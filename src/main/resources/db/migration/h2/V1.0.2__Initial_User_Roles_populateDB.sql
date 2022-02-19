--======================================================================================================--
insert into roles(id, role_name)
values(random_uuid(), 'admin');
insert into roles(id, role_name)
values(random_uuid(), 'user');
--======================================================================================================--
-- login: admin, pass: super_secret_password
insert into users(id, name, password)
values(random_uuid(), 'admin', '{bcrypt}$2y$10$W7a7gyWBWWxEOwzbzoo2seTUmMuJXdLZfn8J1Vt2UDWXHKwOrEjUu');
--======================================================================================================--
