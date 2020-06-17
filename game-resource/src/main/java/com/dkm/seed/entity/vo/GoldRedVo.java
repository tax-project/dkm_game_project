package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/17 10:19
 */
@Data
public class GoldRedVo {
    /**
     * 金币
     */
    private Integer goldCoin=0;

    /**
     * 红包
     */
    private Double redEnvelopes=0.0;
}
