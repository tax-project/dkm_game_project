DROP TABLE IF EXISTS tb_admin_user;
CREATE TABLE IF NOT EXISTS tb_admin_user
(
    id        INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(255)                   NOT NULL,
    password  VARCHAR(255)                   NOT NULL,
    token     VARCHAR(255)                   NOT NULL

);

INSERT INTO tb_admin_user (user_name, password,token) VALUE ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997','')


