SELECT t1.user_id user_id
FROM tb_mine_history t1
         INNER JOIN tb_mine_user_info t2
         INNER JOIN tb_mine_history_goods t3
                    ON t1.user_id = t2.user_id
                        AND t1.id = t3.mine_history_id;