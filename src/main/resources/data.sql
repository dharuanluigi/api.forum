INSERT INTO tb_user(name, email, password) VALUES('Aluno', 'aluno@email.com', '{bcrypt}$2y$10$VM6WvTFWaobqnDelyWQg2Oa0Hecbs31vNs54kXzenGQ2dSJdv4plC');

INSERT INTO tb_course(name, category) VALUES('Spring Boot', 'Programação');
INSERT INTO tb_course(name, category) VALUES('HTML 5', 'Front-end');

INSERT INTO tb_topic(title, message, created_at, status, author_id, course_id) VALUES('Dúvida', 'Erro ao criar projeto', '2019-05-05T18:00:00Z', 'NOT_ANSWERED', 1, 1);
INSERT INTO tb_topic(title, message, created_at, status, author_id, course_id) VALUES('Dúvida 2', 'Projeto não compila', '2019-05-05T19:00:00Z', 'NOT_ANSWERED', 1, 1);
INSERT INTO tb_topic(title, message, created_at, status, author_id, course_id) VALUES('Dúvida 3', 'Tag HTML', '2019-05-05T20:00:00Z', 'NOT_ANSWERED', 1, 2);