create table member_groups (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    name varchar(255)
);

create table permission (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    name varchar(20)
);

create table admin (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    email varchar(255),
    name varchar(100),
    password varchar(255),
    member_groups_id bigint,
    foreign key (member_groups_id) references member_groups(id)
);

create table member (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    account varchar(50),
    account_bank varchar(50),
    depositor_name varchar(20),
    email varchar(255),
    member_status smallint,
    name varchar(100),
    point int,
    provider smallint,
    member_groups_id bigint,
    foreign key (member_groups_id) references member_groups(id)
);

create table item (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    category_type varchar(50),
    is_suggested boolean,
    price int,
    rating double,
    redirect_url longtext,
    refund int,
    thumbnail_url longtext,
    title longtext
);

create table item_image (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    url longtext,
    item_id bigint,
    foreign key (item_id) references item(id)
);

create table item_video (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    url longtext,
    item_id bigint,
    foreign key (item_id) references item(id)
);

create table buy (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    approved_time datetime,
    cert_image_url longtext,
    points_after_refund int,
    points_before_refund int,
    purchase int,
    redirect_url longtext,
    refund int,
    refund_status smallint,
    reject_reason varchar(255),
    upload_time datetime,
    item_id bigint,
    member_id bigint,
    foreign key (item_id) references item(id),
    foreign key (member_id) references member(id)
);

create table dib (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    item_id bigint,
    member_id bigint,
    foreign key (item_id) references item(id) on delete cascade,
    foreign key (member_id) references member(id) on delete cascade
);

create table group_permission (
    id bigint auto_increment primary key,
    created_at datetime,
    deleted boolean,
    updated_at datetime,
    member_groups_id bigint,
    permission_id bigint,
    foreign key (member_groups_id) references member_groups(id),
    foreign key (permission_id) references permission(id)
);
