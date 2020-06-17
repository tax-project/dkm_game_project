package com.dkm.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:26
 */
@Data
@TableName("tb_withdrawal_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WithdrawalRecord extends Model<WithdrawalRecord> {

    /**
     * 提现主键
     */
    private Long withdrawalId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 提现金额
     */
    private BigDecimal withdrawalMoney;

    /**
     * 提现时间
     */
    private LocalDateTime withdrawalTime;

    /**
     * 提现描述
     */
    private String withdrawalDescribe;

    private String time;


}
