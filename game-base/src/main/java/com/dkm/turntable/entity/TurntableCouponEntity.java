package com.dkm.turntable.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 用户抽奖券
 * @author: zhd
 * @create: 2020-06-11 11:55
 **/
@Data
@TableName("tb_turntable_coupon")
public class TurntableCouponEntity {

    @TableId
    private Long turntableCouponId;

    /**
     * userId
     */
    private Long userId;
    /**
     * 蓝券
     */
    private Integer couponBlue;
    /**
     * 绿券
     */
    private Integer couponGreen;
    /**
     * 紫券
     */
    private Integer couponPurple;

    /**
     * 获取时间
     */
    private LocalDateTime couponTime;
}
