INSERT INTO tb_users (id_user, name, password, username) VALUES (0, 'master', '$2a$10$zp6OkVki9PDMqKCEpDbvj.F/nhZT2Uj/qmLMNsEfgK3FJ4UwH9m.m', 'adm');
INSERT INTO tb_roles (id_role, name) VALUES (0, 'ROLE_ADMINISTRATOR');
INSERT INTO user_roles (id_role, id_user) VALUES (0, 0);