INSERT INTO users (name) values ('user_1');
INSERT INTO users (name) values ('user_2');
INSERT INTO users (name) values ('user_3');
INSERT INTO users (name) values ('user_4');



INSERT INTO files (name,file_path) values ('application.json','C:/JavaProjects/module_2.444/src/main/resources/uploads/application.json');
INSERT INTO files (name,file_path) values ('application2.json','C:/JavaProjects/module_2.444/src/main/resources/uploads/application2.json');


INSERT INTO events (user_id,file_id) values (1,1);
INSERT INTO events (user_id,file_id) values (2,1);
INSERT INTO events (user_id,file_id) values (1,2);
INSERT INTO events (user_id,file_id) values (2,2);