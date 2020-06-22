package com.dkm.apparel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 记录表
 * @author: zhd
 * @create: 2020-06-22 14:37
 **/
@Data
@TableName("tb_apparel_order")
public class ApparelOrderEntity {

    @TableId
    private Long apparel_order_id;

    /**
     * 0金币1钻石
     */
    private Integer apparel_pay_type;

    /**
     * 支付金币或钻石
     */
    private Integer apparel_pay_money;

    /**
     * 类型0购买1卖出
     */
    private Integer apparel_order_type;

    private Long userId;

    private LocalDateTime apparel_pay_time;

    private Long apparelDetailId;
}
