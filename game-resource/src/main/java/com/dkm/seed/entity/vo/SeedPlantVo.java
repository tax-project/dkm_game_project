package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/29 13:43
 */
@Data
public class SeedPlantVo {
    private Long seedId;
    private Integer seedGrade;

    private Integer seedGold;
    /**
     * 用来做判断
     */
    private Integer status;
}
