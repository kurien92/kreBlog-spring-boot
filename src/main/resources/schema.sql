drop table if exists account;
drop table if exists account_authority;
drop table if exists account_role;
drop table if exists authority;
drop table if exists content;
drop table if exists file;
drop table if exists role;
drop table if exists role_authority;
drop table if exists service_file;
drop table if exists category;

create table account
(
    account_id  bigint not null auto_increment,
    email       varchar(200),
    password    varchar(200),
    nickname    varchar(30),
    block       integer,
    signup_time datetime(6),
    signup_ip   varchar(15),
    primary key (account_id)
) engine = InnoDB;

create table account_authority
(
    account_authority_id bigint not null auto_increment,
    account_id           bigint,
    authority_id         bigint,
    create_time          datetime(6),
    primary key (account_authority_id)
) engine = InnoDB;

create table account_role
(
    account_role_id bigint not null auto_increment,
    account_id      bigint,
    role_id         bigint,
    create_at       datetime(6),
    primary key (account_role_id)
) engine = InnoDB;

create table authority
(
    authority_id bigint not null auto_increment,
    authority    varchar(10),
    primary key (authority_id)
) engine = InnoDB;

create table content
(
    id          bigint not null auto_increment,
    name        varchar(30),
    title       varchar(200),
    content     longtext,
    view        integer,
    create_time datetime(6),
    primary key (id)
) engine = InnoDB;

create table file
(
    file_id     bigint not null auto_increment,
    type        varchar(6),
    path        varchar(255),
    name        varchar(255),
    stored_name varchar(255),
    extension   varchar(100),
    mime        varchar(100),
    size        bigint,
    upload_time datetime(6),
    upload_ip   varchar(15),
    primary key (file_id)
) engine = InnoDB;

create table role
(
    role_id   bigint not null auto_increment,
    role_name varchar(20),
    primary key (role_id)
) engine = InnoDB;

create table role_authority
(
    role_authority_id bigint not null auto_increment,
    role_id           bigint,
    authority_id      bigint,
    create_at         datetime(6),
    primary key (role_authority_id)
) engine = InnoDB;

create table service_file
(
    service_file_id bigint not null auto_increment,
    service_name    varchar(255),
    service_id      bigint,
    file_id         bigint,
    create_time     datetime(6),
    create_ip       varchar(255),
    primary key (service_file_id)
) engine = InnoDB;

CREATE TABLE `category` (
   `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
   `category_parent_id` int(11) DEFAULT NULL,
   `category_depth` int(11) NOT NULL,
   `category_order` int(11) NOT NULL,
   `category_key` varchar(30) NOT NULL,
   `category_name` varchar(100) DEFAULT NULL,
   PRIMARY KEY (`category_id`),
   UNIQUE KEY `category_key` (`category_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4

# alter table content add constraint UK_rjsbh4mgvigalpmpuitjf7b7e unique (name);
# alter table account_authority add constraint test1 foreign key (account_id) references account (account_id);
# alter table account_authority add constraint FKr21r28dhxvddh22qps7xc6q2u foreign key (authority_id) references authority (authority_id);
# alter table account_role add constraint FK1f8y4iy71kb1arff79s71j0dh foreign key (account_id) references account (account_id);
# alter table account_role add constraint FKrs2s3m3039h0xt8d5yhwbuyam foreign key (role_id) references role (role_id);
# alter table role_authority add constraint FKqbri833f7xop13bvdje3xxtnw foreign key (authority_id) references authority (authority_id);
# alter table role_authority add constraint FK2052966dco7y9f97s1a824bj1 foreign key (role_id) references role (role_id);
# alter table service_file add constraint FKpyy6n5tvlggw97mfo90jpgfws foreign key (file_id) references file (file_id);
