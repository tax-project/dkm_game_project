package com.dkm.vip.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author zhd
 * @date 2020/5/8 13:29
 */
@Data
@TableName("tb_vip")
public class VipEntity{
    @TableId
    private Long vipId;
    /**
     * 关联用户id*/
    private Long userId;

    /**
     * 会员名称*/
    private String vipName;

    /**
     * 会员金额*/
    private BigDecimal vipMoney;

    /**
     * 记录时间*/
    private Date vipCreateTime;
    private String exp1;
    private String exp2;
    private String exp3;

}
