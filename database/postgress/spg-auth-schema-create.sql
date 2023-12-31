create sequence seq_spg_auth_user start with 1 increment by 1;
create sequence seq_spg_auth_config start with 1 increment by 1;
create sequence seq_spg_auth_pwd_reset_token start with 1 increment by 1;
create sequence seq_spg_auth_role_user start with 1 increment by 1;

create table spg_auth_configuration
(
    id                 bigint not null,
    token_expires_at   integer,
    create_date        timestamp(6),
    last_modified_date timestamp(6),
    create_user        varchar(255),
    last_modified_user varchar(255),
    token_secret_key   varchar(255),
    primary key (id)
);

create table spg_auth_pass_reset_token
(
    id                 bigint       not null,
    create_date        timestamp(6),
    expiry_date        timestamp(6) not null,
    last_modified_date timestamp(6),
    user_id            bigint       not null unique,
    create_user        varchar(255),
    last_modified_user varchar(255),
    token              varchar(255) not null unique,
    primary key (id)
);

create table spg_auth_role_user
(
    id                 bigint       not null,
    create_date        timestamp(6),
    last_modified_date timestamp(6),
    user_id            bigint,
    create_user        varchar(255),
    last_modified_user varchar(255),
    role               varchar(255) not null check (role in ('ADMIN', 'OPERATOR', 'WEBSERVICES')),
    primary key (id)
);
create table spg_auth_user
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

alter table if exists spg_auth_pass_reset_token
    add constraint FKti8st4wg08j8jdwjtgb4jj623 foreign key (user_id) references spg_auth_user;
alter table if exists spg_auth_role_user
    add constraint FK2hmko6b9f7yt1yuaofysct77t foreign key (user_id) references spg_auth_user;
