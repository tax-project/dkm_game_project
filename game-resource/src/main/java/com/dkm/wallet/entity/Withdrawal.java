package com.dkm.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 13:57
 */
@Data
@TableName("tb_withdrawal")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Withdrawal extends Model<Withdrawal> {

    /**
     *提现主键
     */
    private Long id;

    /**
     * 人物等级
     */
    private Integer userGrade;

    /**
     * 提现金额
     */
    private BigDecimal withdrawalAmount;

    /**
     * 提现状态 （0没有提现 1已提现）
     */
    private Integer withdrawalStatus;

    /**
     * 用户id
     */
    private Long userId;
}
