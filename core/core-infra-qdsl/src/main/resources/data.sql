insert into permission(id, name) values(1, 'ROLE_ADMIN');
insert into permission(id, name) values(2, 'ROLE_USER');

insert into groups(id, name) values(1, 'ADMIN_GROUP');
insert into groups(id, name) values(2, 'USER_GROUP');

insert into group_permission(id, groups_id, permission_id) values(1, 1, 1);
insert into group_permission(id, groups_id, permission_id) values(2, 2, 2);

insert into item(id, detail, redirect_url, category_type, price, refund, rating, thumbnail_url)
values (1, 'test', 'https://s.zigzag.kr/3HSmoWF6Xa?af=1', 'BEAUTY', 10800, 1000, 5.0, 'https://cf.product-image.s.zigzag.kr/original/d/2023/9/21/21909_202309210952510497_81140.jpeg?width=720&height=720&quality=80&format=webp');
insert into item_video(id, url, item_id) values (1, 'https://youtu.be/TDmzXuma4Lw?si=9RFwafiFgTUOMsvE', 1);

insert into item(id, detail, redirect_url, category_type, price, refund, rating, thumbnail_url)
values (2, 'test', 'https://s.zigzag.kr/3HSmoWF6Xa?af=1', 'FOOD', 10800, 1000, 5.0, 'https://cf.product-image.s.zigzag.kr/original/d/2023/9/21/21909_202309210952510497_81140.jpeg?width=720&height=720&quality=80&format=webp');
insert into item_video(id, url, item_id) values (2, 'https://youtu.be/TDmzXuma4Lw?si=9RFwafiFgTUOMsvE', 2);

insert into item(id, detail, redirect_url, category_type, price, refund, rating, thumbnail_url)
values (3, 'test', 'https://s.zigzag.kr/3HSmoWF6Xa?af=1', 'LIFE', 10800, 1000, 5.0, 'https://cf.product-image.s.zigzag.kr/original/d/2023/9/21/21909_202309210952510497_81140.jpeg?width=720&height=720&quality=80&format=webp');
insert into item_video(id, url, item_id) values (3, 'https://youtu.be/TDmzXuma4Lw?si=9RFwafiFgTUOMsvE', 3);

insert into item(id, detail, redirect_url, category_type, price, refund, rating, thumbnail_url)
values (4, 'test', 'https://s.zigzag.kr/3HSmoWF6Xa?af=1', 'BEAUTY', 10800, 1000, 5.0, 'https://cf.product-image.s.zigzag.kr/original/d/2023/9/21/21909_202309210952510497_81140.jpeg?width=720&height=720&quality=80&format=webp');
insert into item_video(id, url, item_id) values (4, 'https://youtu.be/TDmzXuma4Lw?si=9RFwafiFgTUOMsvE', 4);
