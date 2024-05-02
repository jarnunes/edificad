/**
  Scripts de banco nos mo
 */

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Dashboard', 'VIEW_DASHBOARD');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Voluntário', 'VIEW_VOLUNTARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Voluntário > Criar', 'CREATE_VOLUNTARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Voluntário > Editar', 'EDIT_VOLUNTARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Voluntário > Excluir', 'DELETE_VOLUNTARIO');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Beneficiario', 'VIEW_BENEFICIARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Beneficiário > Criar', 'CREATE_BENEFICIARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Beneficiário > Editar', 'EDIT_BENEFICIARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Beneficiário > Excluir', 'DELETE_BENEFICIARIO');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Distribuição Cesta', 'VIEW_DISTRIBUICAO_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Distribuição Cesta > Editar', 'EDIT_DISTRIBUICAO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Distribuição Cesta > Cancelar', 'ACTION_CANCEL_DISTRIBUICAO');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Cesta', 'VIEW_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Cesta > Criar', 'CREATE_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Cesta > Editar', 'EDIT_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Cesta > Excluir', 'DELETE_CESTA');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Relatórios', 'VIEW_RELATORIOS');

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Menu: Configuração', 'VIEW_CONFIGURACAO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Configuração > Parâmetro', 'VIEW_PARAMETRO');