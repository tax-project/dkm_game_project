DROP TABLE if exists tb_shop_card;
CREATE TABLE IF NOT EXISTS tb_shop_card
(
    id       BIGINT(20) PRIMARY KEY NOT NULL,
    user_id  BIGINT(20)             NOT NULL COMMENT 'user ID',
    goods_id BIGINT(20)             NOT NULL COMMENT 'goods Item ID',
    size     INT                    NOT NULL COMMENT 'goods item size',
    checked  ENUM ('true','false')  NOT NULL COMMENT 'item checked status' DEFAULT 'false',

    FOREIGN KEY (goods_id) REFERENCES tb_goods (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES tb_user (user_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) COMMENT '购物车表'
    COLLATE = 'utf8_bin'
    ENGINE = InnoDB;

SELECT a.size                  goods_size,
       a.checked               checked,
       b.good_money            goods_price,
       b.url                   goods_image_url,
       b.name                  goods_name,
       (a.size * b.good_money) total_price
FROM tb_shop_card a
         JOIN tb_goods b ON a.goods_id = b.id
         JOIN tb_user c on a.user_id = c.user_id
WHERE c.user_id = '712030540001349632';

