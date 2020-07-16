# SELECT lottery.id              id,
#        lottery.expiration      expiration,
#        goods.name              name,
#        goods.id                goods_id,
#        goods.good_money        goods_money,
#        lottery_item.goods_size item_size
# FROM tb_lottery lottery,
#      tb_lottery_item lottery_item,
#      tb_goods goods
# WHERE lottery_item.goods_id = goods.id
#   AND lottery.id = lottery_item.lottery_id
#   AND lottery.expiration > now()
# ORDER BY lottery.id DESC;
#
# SELECT lottery.id                id,
#        lottery_status.expiration expiration,
#        goods.name                name,
#        goods.id                  goods_id,
#        goods.good_money          goods_money,
#        item.goods_size           item_size,
#        goods.url                 image_url
# FROM tb_lottery lottery,
#      tb_lottery_item item,
#      tb_lottery_status lottery_status,
#      tb_lottery_item_status item_status,
#      tb_goods goods
# WHERE lottery.id IN (
#     SELECT MIN(lottery.id)
#     FROM tb_lottery lottery
#     WHERE lottery.expiration > now()
# )
#   AND item.goods_id = goods.id
#   AND lottery.id = item.lottery_id
#   AND item_status.lottery_item_id = item.id
#   AND lottery_status.lottery_id = lottery.id;
#
# SELECT *
# FROM tb_diggings_history
# WHERE id IN (
#     SELECT MAX(id)
#     FROM tb_diggings_history
#     WHERE user_id = #{userId}
#       AND family_id = #{familyId}
# )


SELECT a.id                                                                              id,
       a.goods_size                                                                      goods_size,
       a.size                                                                            size,
       b.url                                                                             image_url,
       b.good_money                                                                      money,
       b.name                                                                            name,
       (SELECT COUNT(*) FROM tb_lottery_user WHERE tb_lottery_user.tb_lottery_id = a.id) usedSize
FROM tb_lottery a,
     tb_goods b
WHERE a.id = b.id;

INSERT INTO tb_game_options (option_key, option_value)
    VALUE ('LOTTERY_REFRESH_DATE', '86400');
INSERT INTO tb_game_options (option_key, option_value)
    VALUE ('LOTTERY_NEXT_UPDATE_DATE', '2020-08-10 00:00:00');
SELECT *
FROM tb_game_options;


select count(1) len
from tb_goods
WHERE name = '1';
