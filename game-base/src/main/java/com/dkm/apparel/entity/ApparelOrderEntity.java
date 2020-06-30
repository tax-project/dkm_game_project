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
    private Long apparelOrderId;

    /**
     * 0金币1钻石
     */
    private Integer apparelPayType;

    /**
     * 支付金币或钻石
     */
    private Integer apparelPayMoney;

    /**
     * 类型0购买1卖出
     */
    private Integer apparelOrderType;

    private Long userId;

    private LocalDateTime apparelPayTime;

    private Long apparelDetailId;
}
