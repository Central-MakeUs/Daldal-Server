insert into permission(id, name) values(1, 'ROLE_ADMIN');
insert into permission(id, name) values(2, 'ROLE_USER');

insert into groups(id, name) values(1, 'ADMIN_GROUP');
insert into groups(id, name) values(2, 'USER_GROUP');

insert into group_permission(id, groups_id, permission_id) values(1, 1, 1);
insert into group_permission(id, groups_id, permission_id) values(2, 2, 2);
