create sequence seq_cesta start with 1 increment by 1;
create sequence seq_configuracao start with 1 increment by 1;
create sequence seq_dist_cesta start with 1 increment by 1;
create sequence seq_endereco start with 1 increment by 1;
create sequence seq_generator start with 1 increment by 50;



create table beneficiario
(
    id bigint not null,
    primary key (id)
);

create table cesta
(
    quantidade_estoque integer     not null,
    create_date        timestamp(6),
    id                 bigint      not null,
    last_modified_date timestamp(6),
    nome               varchar(50) not null unique,
    create_user        varchar(255),
    last_modified_user varchar(255),
    descricao          oid,
    primary key (id)
);

create table configuracao
(
    token_expires_at   integer,
    create_date        timestamp(6),
    id                 bigint not null,
    last_modified_date timestamp(6),
    create_user        varchar(255),
    last_modified_user varchar(255),
    token_secret_key   varchar(255),
    primary key (id)
);

create table dependente
(
    id             bigint not null,
    responsavel_fk bigint not null,
    primary key (id)
);

create table distribuicao_cesta
(
    beneficiario_fk    bigint not null,
    cesta_fk           bigint not null,
    create_date        timestamp(6),
    data_hora          timestamp(6),
    id                 bigint not null,
    last_modified_date timestamp(6),
    voluntario_fk      bigint not null,
    create_user        varchar(255),
    last_modified_user varchar(255),
    primary key (id)
);


create table endereco
(
    estado             varchar(2)   not null check (estado in ('MG', 'SP', 'RJ')),
    create_date        timestamp(6),
    id                 bigint       not null,
    last_modified_date timestamp(6),
    cep                varchar(14),
    cidade             varchar(50),
    bairro             varchar(100),
    numero             varchar(100) not null,
    create_user        varchar(255),
    last_modified_user varchar(255),
    logradouro         varchar(255) not null,
    primary key (id)
);

create table pessoa
(
    data_nascimento    date,
    create_date        timestamp(6),
    endereco_fk        bigint unique,
    id                 bigint       not null,
    last_modified_date timestamp(6),
    cpf                varchar(14) unique,
    telefone           varchar(15) unique,
    email              varchar(100) unique,
    nome               varchar(100) not null,
    create_user        varchar(255),
    last_modified_user varchar(255),
    primary key (id)
);

create table voluntario
(
    numero_projetos_participados integer,
    id                           bigint not null,
    primary key (id)
);

create table eds_role_user
(
    create_date        timestamp(6),
    id                 bigint       not null,
    last_modified_date timestamp(6),
    user_id            bigint,
    create_user        varchar(255),
    last_modified_user varchar(255),
    role               varchar(255) not null check (role in ('ADMIN', 'OPERATOR', 'WEBSERVICES')),
    primary key (id)
);

create table eds_user
(
    enabled            boolean      not null,
    locked             boolean      not null,
    create_date        timestamp(6),
    id                 bigint       not null,
    last_modified_date timestamp(6),
    create_user        varchar(255),
    email              varchar(255) not null unique,
    full_name          varchar(255) not null,
    last_modified_user varchar(255),
    password           varchar(255) not null,
    username           varchar(255) not null unique,
    primary key (id)
);


alter table if exists beneficiario add constraint FKg6pwuq5mu87n2l6vsmiducalw foreign key (id) references pessoa;
alter table if exists dependente add constraint FKhuoc5cn2xgdjre0p7wa1cr1gn foreign key (responsavel_fk) references beneficiario;
alter table if exists dependente add constraint FK6xy74durf67qwoe11j37lsih0 foreign key (id) references pessoa;
alter table if exists distribuicao_cesta add constraint FKn6ghikh9qjr73tqmx7dtxwei5 foreign key (beneficiario_fk) references beneficiario;
alter table if exists distribuicao_cesta add constraint FKihhjsmi1k3367by7wb556mtfl foreign key (cesta_fk) references cesta;
alter table if exists distribuicao_cesta add constraint FKclrnnaphrrbf973bkngc1nx5i foreign key (voluntario_fk) references voluntario;
alter table if exists pessoa add constraint FKg2aal0p0ich7gudkwm6vomdke foreign key (endereco_fk) references endereco;
alter table if exists voluntario add constraint FK2ag8fj8brxi11f01mur9klmw foreign key (id) references pessoa;
