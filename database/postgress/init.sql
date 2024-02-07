/**
  Scripts de banco nos mo
 */

INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_DASHBOARD');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Distribuição de Cestas Resources', 'VIEW_DISTRIBUICAO_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_BENEFICIARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_VOLUNTARIO');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_CESTA');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_RELATORIOS');
INSERT INTO spg_auth_resource (id, create_date, create_user, last_modified_date, last_modified_user, description, name)
VALUES ((SELECT nextval('seq_spg_auth_resource')), current_timestamp, 'system', null, null, 'Dashboard Resources', 'VIEW_CONFIGURACAO');
