package com.dkm.wallet.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:38
 */
@Data
public class WithdrawalRecordVo {

    /**
     * 提现金额
     */
    private Double withdrawalMoney;

    /**
     * 提现时间
     */
    private LocalDateTime withdrawalTime;

    /**
     * 提现描述
     */
    private String withdrawalDescribe;

    /**
     *本月累计体现总金额
     */
    private Double totalAmount;
}
