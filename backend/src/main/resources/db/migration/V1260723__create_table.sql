alter table if exists category_menu drop constraint if exists FKkr05f61eb9fdurxnbh41n0rit;
alter table if exists check_table drop constraint if exists FKo5ivlp4avf0yvb95sd1uj4p3c;
alter table if exists dish drop constraint if exists FKku2c0syq8mtqg98rk69gj823k;
alter table if exists dish drop constraint if exists FKdtoxfjnb5qdsk523wl8a8569q;
alter table if exists order_table drop constraint if exists FK8v925y2n3podelbd6n84xodpf;

drop table if exists category_menu cascade;
drop table if exists check_table cascade;
drop table if exists dish cascade;
drop table if exists order_table cascade;
drop table if exists restaurant cascade;

drop sequence if exists category_menu_seq;
drop sequence if exists check_table_seq;
drop sequence if exists dish_seq;
drop sequence if exists order_table_seq;
drop sequence if exists restaurant_seq;

create sequence category_menu_seq start with 1 increment by 50;
create sequence check_table_seq start with 1 increment by 50;
create sequence dish_seq start with 1 increment by 50;
create sequence order_table_seq start with 1 increment by 50;
create sequence restaurant_seq start with 1 increment by 50;

create table restaurant(
    count_table integer,
    id bigint not null,
    info_rest varchar(1000),
    name_restaurant varchar(1000),
    primary key (id)
);

create table category_menu (
    id bigint not null,
    rest_id bigint,
    link_image varchar(1000),
    title varchar(1000),
    primary key (id)

);

create table check_table (
    number_table integer,
    session_status smallint check(session_status between 0 and 1),
    cost bigint,
    date_created timestamp(6),
    id bigint not null,
    rest_id bigint,
    primary key (id)
);

create table dish (
    is_stop_list boolean,
    price integer,
    weight float(53),
    category_id bigint,
    date_created timestamp(6),
    id bigint not null,
    rest_id bigint,
    link_image varchar(1000),
    description text,
    title varchar(255),
    primary key (id)
);

create table order_table (
     count integer,
     number_table integer,
     check_table_id bigint,
     dish_id bigint,
     id bigint not null,
     price bigint,
     primary key (id)
);

alter table if exists category_menu add constraint FKkr05f61eb9fdurxnbh41n0rit foreign key (rest_id) references restaurant;
alter table if exists check_table add constraint FKo5ivlp4avf0yvb95sd1uj4p3c foreign key (rest_id) references restaurant;
alter table if exists dish add constraint FKku2c0syq8mtqg98rk69gj823k foreign key (category_id) references category_menu;
alter table if exists dish add constraint FKdtoxfjnb5qdsk523wl8a8569q foreign key (rest_id) references restaurant;
alter table if exists order_table add constraint FK8v925y2n3podelbd6n84xodpf foreign key (check_table_id) references check_table;
