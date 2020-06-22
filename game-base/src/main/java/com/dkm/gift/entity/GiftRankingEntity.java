package com.dkm.gift.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: game_project
 * @description: 送礼排行
 * @author: zhd
 * @create: 2020-06-18 14:33
 **/
@Data
@TableName("tb_gift_rangking")
@NoArgsConstructor
@AllArgsConstructor
public class GiftRankingEntity {
    private Long rankingId;
    private Long userId;
    private Integer send;
    private Integer accept;
    private Integer sendFlower;
    private Integer acceptFlower;
}
