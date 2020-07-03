package com.dkm.knapsack.domain.vo;

import lombok.Data;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/7/3 15:39
 */
@Data
public class TbEquipmentVoTwo {
    /**
     * 物品主键
     */
    private Long foodId;
    /**
     * 物品拥有数量
     */
    private Integer foodNumber;

    private Long userId;
}
