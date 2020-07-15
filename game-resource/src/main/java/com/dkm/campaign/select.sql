SELECT lottery.id              id,
       lottery.expiration      expiration,
       goods.name              name,
       goods.id                goods_id,
       goods.good_money        goods_money,
       lottery_item.goods_size item_size
FROM tb_lottery lottery,
     tb_lottery_item lottery_item,
     tb_goods goods
WHERE lottery_item.goods_id = goods.id
  AND lottery.id = lottery_item.lottery_id
  AND lottery.expiration > now()
ORDER BY lottery.id DESC;

SELECT lottery.id              id,
       lottery.expiration      expiration,
       goods.name              name,
       goods.id                goods_id,
       goods.good_money        goods_money,
       lottery_item.goods_size item_size,
       goods.url               image_url
FROM tb_lottery lottery,
     tb_lottery_item lottery_item,
     tb_goods goods
WHERE lottery.id IN (
    SELECT MIN(lottery.id)
    FROM tb_lottery lottery
    WHERE lottery.expiration > now()
)
  AND lottery_item.goods_id = goods.id
  AND lottery.id = lottery_item.lottery_id;



# SELECT *
# FROM tb_diggings_history
# WHERE id IN (
#     SELECT MAX(id)
#     FROM tb_diggings_history
#     WHERE user_id = #{userId}
#       AND family_id = #{familyId}
# )