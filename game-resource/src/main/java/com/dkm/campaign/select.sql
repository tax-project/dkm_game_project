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

SELECT (a.size - COUNT(*)) remaining_size
FROM tb_lottery a,
     tb_lottery_user b
WHERE a.id = b.tb_lottery_id AND a.id = 1;

SELECT *
FROM tb_game_options;

SELECT row_count(), id, option_key, option_value
FROM tb_game_options
WHERE option_key = 'LOTTERY_REFRESH_DATE'
LIMIT 1;
SELECT option_value
FROM tb_game_options
WHERE option_key = 'LOTTERY_NEXT_UPDATE_DATE'
LIMIT 1;

select count(1) len
from tb_goods
WHERE name = '1';

# UPDATE tb_game_options SET option_value = #{date} WHERE option_key = 'LOTTERY_REFRESH_DATE'
# UPDATE tb_game_options SET option_value = #{date} WHERE option_key = 'LOTTERY_NEXT_UPDATE_DATE'


-- 获取上次的用户
SELECT user.we_chat_nick_name name, history.prize_text
FROM tb_lottery_last_history history,
     tb_user user
WHERE history.user_id = user.user_id;


SELECT a.id                           id,
       b.name                         name,
       b.url                          image_url,
       a.goods_size                   goods_size,
       a.goods_id                     goods_id,
       a.size                         size,
       b.good_money                   money,
       (SELECT COUNT(*)
        FROM tb_lottery_user c
        WHERE c.tb_lottery_id = a.id) usedSize,
       (SELECT COUNT(*)
        FROM tb_lottery_user tlu
        WHERE tlu.tb_lottery_id = a.id
          AND tlu.user_id = '')       userSize
FROM tb_lottery a,
     tb_goods b
WHERE a.id = b.id;


SELECT user.we_chat_nick_name `姓名`, info.user_info_diamonds `钻石`
FROM tb_user user,
     tb_user_info info
WHERE user.user_id = info.user_id
  AND user.we_chat_nick_name = 'asd';

UPDATE tb_user user,
    tb_user_info info
SET info.user_info_diamonds = 10000
WHERE user.user_id = info.user_id
  AND user.we_chat_nick_name = 'asd';
