--======================================================================================================--
insert into roles(role_name)
values('admin');
insert into roles(role_name)
values('user');
--======================================================================================================--
-- login: admin, pass: super_secret_password
insert into users(user_name, password)
values('admin', '{bcrypt}$2y$10$W7a7gyWBWWxEOwzbzoo2seTUmMuJXdLZfn8J1Vt2UDWXHKwOrEjUu');
--======================================================================================================--