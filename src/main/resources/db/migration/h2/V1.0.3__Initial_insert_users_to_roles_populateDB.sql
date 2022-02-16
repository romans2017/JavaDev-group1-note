insert into user_role(user_id, role_id)
values((select id from users where user_name = 'admin'), (select id from roles where role_name = 'ROLE_ADMIN'));