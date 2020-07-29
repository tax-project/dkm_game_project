SELECT a.id id, a.day as day, b.size as size, c.name as goods_name, c.url as goods_url
FROM tb_mall_single a,
     tb_mall_single_item b,
     tb_goods c
WHERE a.id = b.tb_mall_single_id
  AND c.id = b.goods_id;

SELECT a.id id, b.id goods_id, b.name name ,b.url  image_url ,a.size size
From tb_mall_single_item a
         join tb_goods b on a.goods_id = b.id
where tb_mall_single_id = 1;


SELECT  user_id ,tb_mall_single_id item_id FROM tb_mall_single_user WHERE user_id = '' AND tb_mall_single_id = '';
