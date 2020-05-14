package com.dkm.housekeeper.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhd
 * @date 2020/5/8 16:53
 */
@Data
@TableName("tb_housekeeper")
public class HousekeeperEntity {
    @TableId
    private  Long housekeeperId;

    /**
     * 用户id
     * */
    private Long userId;

    /**
     * 管家金额
     * */
    private BigDecimal housekeeperMoney;

    /**
     * 是否有效
     * */
    private Integer isEffective;

    /**
     * 下单日期
     * */
    private LocalDateTime orderTime;

    /**
     * 到期时间
     */
    private LocalDateTime expireTime;

    /**
     * 开工结束时间
     */
    private LocalDateTime endWorkTime;

    /**
     * 下次开工时间
     */
    private LocalDateTime startWorkTime;
}
