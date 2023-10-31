create sequence seq_role_user start with 1 increment by 1;

create table eds_role_user
(
    id                 bigint       not null,
    create_date        timestamp(6),
    last_modified_date timestamp(6),
    user_id            bigint,
    create_user        varchar(255),
    last_modified_user varchar(255),
    role               varchar(255) not null
        check (role in ('ADMIN', 'OPERATOR', 'WEBSERVICES')),

    primary key (id)
);

create table eds_user
(
    id                 bigint       not null,
    enabled            boolean      not null,
    locked             boolean      not null,
    create_date        timestamp(6),
    last_modified_date timestamp(6),
    create_user        varchar(255),
    email              varchar(255) not null unique,
    full_name          varchar(255) not null,
    last_modified_user varchar(255),
    password           varchar(255) not null,
    username           varchar(255) not null unique,

    primary key (id)
);


alter table if exists eds_role_user add constraint FK28p1hnn0s0jeylasl1gvm2s4v foreign key (user_id) references eds_user;