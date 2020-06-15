package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 15:37
 */
@Data
public class GoldOrMoneyVo {
    /**
     * 掉落的金币
     */
    private Integer Gold=0;
    /**
     *  掉落的红包
     */
    private Double Money;

    /**
     *  掉落的花
     */
    private Integer dropFallingFlowers;

}
