
CREATE TABLE IF NOT EXISTS tb_admin_users
(
    id      INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id BIGINT(20)                     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES tb_user (user_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT '管理员用户表'
    COLLATE = 'utf8_bin'
    ENGINE = InnoDB;

INSERT INTO tb_admin_users(user_id) VALUE ((SELECT user_id FROM tb_user WHERE we_chat_nick_name = 'admin'));

SELECT * from tb_admin_users;
