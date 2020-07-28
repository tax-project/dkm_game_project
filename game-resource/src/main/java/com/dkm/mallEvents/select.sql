SELECT a.id id ,a.day as day, b.size as size, c.name as goods_name, c.url as goods_url
FROM tb_mall_single a,
     tb_mall_single_item b,
     tb_goods c
WHERE a.id = b.tb_mall_single_id
  AND c.id = b.goods_id;