-- 查询今天的家族积分
SELECT ifnull(SUM(integral), 0) size
FROM tb_diggings_history history,
     tb_family_details details
WHERE history.start_date > '2020-07-10 00:00:00.000'
  AND details.family_id = history.family_id
  AND details.family_id = '730784954585354240';

select sum(integral) len
from tb_diggings_history
WHERE family_id = 730825431837741056;



SELECT sum(integral) integral,
       f.family_name name,
       (SELECT u.we_chat_head_img_url url
        FROM tb_user u
        WHERE user_id IN
              (SELECT dl.user_id
               FROM tb_family_details dl
               WHERE family_id = '730825431837741056'
               ORDER BY dl.user_id
              )
        LIMIT 1)     userImage
FROM tb_family f
         left join
     tb_diggings_history h on f.family_id = h.family_id
where f.family_id = '730825431837741056'
GROUP BY h.family_id;



SELECT *
from tb_family_details
WHERE family_id = 730825431837741056;



select *
from tb_diggings_history h,
     tb_user u
WHERE family_id = 730825431837741056
  AND u.user_id = h.user_id;

-- 查询今天用户积分
SELECT details.user_id user_id, ifnull(sum(history.integral), 0) integral
FROM tb_diggings_history history
         RIGHT JOIN
     tb_family_details details ON
             details.user_id = history.user_id AND details.family_id = history.family_id AND
             history.family_id = 730825431837741056
GROUP BY details.user_id
ORDER BY integral DESC;

-- 查询用户积分
SELECT dl.user_id                       user_id,
       ifnull(sum(history.integral), 0) integral,
       user.we_chat_nick_name           name,
       user.we_chat_head_img_url        user_image
from tb_user user
         RIGHT JOIN tb_family_details dl ON
    user.user_id = dl.user_id
         LEFT JOIN tb_diggings_history history ON
        dl.family_id = history.family_id AND
        dl.user_id = history.user_id
        AND history.stop_date < NOW()
WHERE dl.family_id = 730825431837741056
GROUP BY dl.user_id
ORDER BY integral DESC;


SELECT sum(integral) integral

FROM tb_family f
         left join
     tb_diggings_history h on f.family_id = h.family_id
where f.family_id = '730825431837741056'
GROUP BY h.family_id;

SELECT @rowNum := @rowNum + 1 row, t.*
FROM (SELECT ifnull(SUM(h.integral), 0) integral,
             f.family_name              name,
             f.family_id                family_id
      FROM tb_family f
               LEFT JOIN
           tb_diggings_history h
           ON f.family_id = h.family_id
      GROUP BY f.family_id
      ORDER BY integral DESC) t,
     (SELECT @rowNum := 0) n
WHERE family_id = '730825431837741056';

SELECT @rowNum := @rowNum + 1 row
FROM (SELECT ifnull(SUM(h.integral), 0) integral
      FROM tb_family f
               LEFT JOIN
           tb_diggings_history h
           ON f.family_id = h.family_id
      WHERE f.family_id = '730825431837741056'
      GROUP BY f.family_id
      ORDER BY integral DESC) t,
     (SELECT @rowNum := 0) n;