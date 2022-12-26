----------------------------------------- Insert data ------------------------------------------------------------------

--- User ---
INSERT INTO tb_user(id, name, email, password) VALUES('57470ac6-852b-11ed-a1eb-0242ac120002', 'user', 'user@email.com', '$2y$10$VM6WvTFWaobqnDelyWQg2Oa0Hecbs31vNs54kXzenGQ2dSJdv4plC');
INSERT INTO tb_user(id, name, email, password) VALUES('79f89eea-852b-11ed-a1eb-0242ac120002', 'mod', 'mod@email.com', '$2y$10$VM6WvTFWaobqnDelyWQg2Oa0Hecbs31vNs54kXzenGQ2dSJdv4plC');

--- Profiles ---
INSERT INTO tb_profile(id, name) VALUES('a22cfcd0-852b-11ed-a1eb-0242ac120002', 'ROLE_USER');
INSERT INTO tb_profile(id, name) VALUES('a7746f20-852b-11ed-a1eb-0242ac120002', 'ROLE_MODERATOR');

--- Categories ---
INSERT INTO tb_category(id, name) VALUES('01b09428-852c-11ed-a1eb-0242ac120002', 'Programacao');


---------------------------------------- Add relationship --------------------------------------------------------------

--- User into profiles ---
INSERT INTO tb_user_profiles(user_id, profiles_id) VALUES('57470ac6-852b-11ed-a1eb-0242ac120002', 'a22cfcd0-852b-11ed-a1eb-0242ac120002'); -- set user to role user
INSERT INTO tb_user_profiles(user_id, profiles_id) VALUES('79f89eea-852b-11ed-a1eb-0242ac120002', 'a22cfcd0-852b-11ed-a1eb-0242ac120002'); -- set mod to role user
INSERT INTO tb_user_profiles(user_id, profiles_id) VALUES('79f89eea-852b-11ed-a1eb-0242ac120002', 'a7746f20-852b-11ed-a1eb-0242ac120002'); -- set mod to role user

--- Course with categories ---
INSERT INTO tb_course(id, name, category_id) VALUES('b29c16fe-854a-11ed-a1eb-0242ac120002', 'Java', '01b09428-852c-11ed-a1eb-0242ac120002';
INSERT INTO tb_course(id, name, category_id) VALUES('bab3ca3a-854a-11ed-a1eb-0242ac120002', 'HTML 5', '01b09428-852c-11ed-a1eb-0242ac120002');

--- Category with courses
INSERT INTO tb_category_courses(categories_id, courses_id) VALUES('01b09428-852c-11ed-a1eb-0242ac120002', 'b29c16fe-854a-11ed-a1eb-0242ac120002');
INSERT INTO tb_category_courses(categories_id, courses_id) VALUES('01b09428-852c-11ed-a1eb-0242ac120002', 'bab3ca3a-854a-11ed-a1eb-0242ac120002');

--- Topics
INSERT INTO tb_topic(id, title, message, created_at, status, author_id, course_id) VALUES('728ecd08-854b-11ed-a1eb-0242ac120002', 'Dúvida', 'Erro ao criar projeto', '2019-05-05T18:00:00Z', 'NOT_ANSWERED', '57470ac6-852b-11ed-a1eb-0242ac120002', 'b29c16fe-854a-11ed-a1eb-0242ac120002');
INSERT INTO tb_topic(id, title, message, created_at, status, author_id, course_id) VALUES('7be7d930-854b-11ed-a1eb-0242ac120002', 'Dúvida 2', 'Projeto não compila', '2019-05-05T19:00:00Z', 'NOT_ANSWERED', '79f89eea-852b-11ed-a1eb-0242ac120002', 'b29c16fe-854a-11ed-a1eb-0242ac120002');
INSERT INTO tb_topic(id, title, message, created_at, status, author_id, course_id) VALUES('8343b9ec-854b-11ed-a1eb-0242ac120002', 'Dúvid a 3', 'Tag HTML', '2019-05-05T20:00:00Z', 'NOT_ANSWERED', '79f89eea-852b-11ed-a1eb-0242ac120002', 'bab3ca3a-854a-11ed-a1eb-0242ac120002');