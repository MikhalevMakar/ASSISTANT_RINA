create sequence category_menu_seq start with 1 increment by 50;
create sequence check_table_seq start with 1 increment by 50;
create sequence dish_seq start with 1 increment by 50;
create sequence order_table_seq start with 1 increment by 50;
create sequence restaurant_seq start with 1 increment by 50;
create sequence review_seq start with 1 increment by 50;

create table category_menu (
    id bigint not null,
    rest_id bigint,
    link_image varchar(1000),
    title varchar(1000),
    primary key (id)
);


create table check_table (
    number_table integer,
    session_status smallint check (session_status between 0 and 1),
    cost bigint,
    date_created timestamp(6),
    id bigint not null,
    rest_id bigint,
    primary key (id)
);

create table dish (
    is_stop_list boolean,
    weight float(53),
    category_id bigint,
    date_created timestamp(6),
    id bigint not null,
    price bigint,
    rest_id bigint,
    link_image varchar(1000),
    description text,
    title varchar(255),
    primary key (id)
);

create table order_table (
    number_table integer,
    check_table_id bigint,
    count bigint,
    dish_id bigint,
    id bigint not null,
    price bigint,
    primary key (id)
);

create table restaurant (
    count_table integer,
    id bigint not null,
    info_rest varchar(1000),
    name_restaurant varchar(1000),
    primary key (id)
);

create table review (
    score smallint,
    id bigint not null,
    restaurant_id bigint,
    text text,
    primary key (id)
);

alter table if exists category_menu add constraint FKkr05f61eb9fdurxnbh41n0rit foreign key (rest_id) references restaurant;
alter table if exists check_table add constraint FKo5ivlp4avf0yvb95sd1uj4p3c foreign key (rest_id) references restaurant;
alter table if exists dish add constraint FKku2c0syq8mtqg98rk69gj823k foreign key (category_id) references category_menu;
alter table if exists dish add constraint FKdtoxfjnb5qdsk523wl8a8569q foreign key (rest_id) references restaurant;
alter table if exists order_table add constraint FK8v925y2n3podelbd6n84xodpf foreign key (check_table_id) references check_table;
alter table if exists review add constraint FK70ry7cuti298yxet366rynxch foreign key (restaurant_id) references restaurant;