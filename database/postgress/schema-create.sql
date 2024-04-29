create sequence seq_cesta start with 1 increment by 1;
create sequence seq_configuracao start with 1 increment by 1;
create sequence seq_dist_cesta start with 1 increment by 1;
create sequence seq_endereco start with 1 increment by 1;
create sequence seq_pessoa start with 1 increment by 1;
create sequence seq_parametro start with 1 increment by 1;
create sequence seq_valor_parametro_logico start with 1 increment by 1;

create table beneficiario
(
    id bigint not null,
    primary key (id)
);

create table cesta
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    quantidade_estoque integer     not null,
    nome               varchar(50) not null unique,
    descricao          oid,
    primary key (id)
);

create table configuracao
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
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
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    beneficiario_fk    bigint not null,
    cesta_fk           bigint not null,
    data_hora          timestamp(6),
    voluntario_fk      bigint not null,
    cancelamento         timestamp(6),
    motivo_cancelamento  varchar(255),
    usuario_cancelamento varchar(255),
    primary key (id)
);

create table endereco
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    estado             varchar(2)   not null check (estado in ('MG', 'SP', 'RJ')),
    cep                varchar(10),
    cidade             varchar(50),
    bairro             varchar(100),
    numero             varchar(100) not null,
    logradouro         varchar(255) not null,
    primary key (id)
);

create table parametro
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    d_type             varchar(255) not null,
    nome               varchar(255) not null,
    dominio            varchar(255) not null,
    primary key (id)
);

create table pessoa
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    data_nascimento    date,
    cpf                varchar(15) unique,
    telefone           varchar(15) unique,
    email              varchar(100) unique,
    nome               varchar(100) not null,
    primary key (id)
);

create table valor_parametro_logico
(
    id                 bigint not null,
    create_date        timestamp(6),
    create_user        varchar(255),
    last_modified_date timestamp(6),
    last_modified_user varchar(255),
    valor              boolean,
    parametro_fk       bigint not null,
    primary key (id)
);

create table voluntario
(
    numero_projetos_participados integer,
    id                           bigint not null,
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
alter table if exists valor_parametro_logico add constraint FKqsofb707v96k7lcq8kte6pcyf foreign key (parametro_fk) references parametro;
