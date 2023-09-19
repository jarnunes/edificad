--pass: 123
insert into eds_user (id, create_date, create_user, last_modified_date, last_modified_user, email, enabled, full_name,
                      locked, password, username)
values (0, current_timestamp, 'system', current_timestamp, null, 'admin@gmail.com', true, 'System Admin', false,
        '{bcrypt}$2a$10$xuHWYCzrXFzHnrbTOHmxLu.dSNrK9VpE3ZE9NNAPfVNWyGWO7Bqzy', 'admin');

insert into eds_role_user (id, create_date, create_user, last_modified_date, last_modified_user, role, user_id)
values ((SELECT NEXTVAL('eds_role_user_seq')), current_timestamp, 'system', current_timestamp, null, 'ADMIN', 0);

insert into eds_role_user (id, create_date, create_user, last_modified_date, last_modified_user, role, user_id)
values ((SELECT NEXTVAL('eds_role_user_seq')), current_timestamp, 'system', current_timestamp, null, 'WEBSERVICES', 0);
