package com.dkm.bill.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:44
 */
@Data
public class BillVo {

    /**
     * 1(金币) 2(钻石) 3(财富卷)
     */
    public Integer bType;

    /**
     * 1收入 2支出
     */
    public Integer bIncomeExpenditure;
}
