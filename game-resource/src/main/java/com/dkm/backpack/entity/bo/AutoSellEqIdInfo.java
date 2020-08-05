package com.dkm.backpack.entity.bo;

import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-08-05 10:27
 **/
@Data
public class AutoSellEqIdInfo {

    /**
     * 背包id
     */
    private Long backpackId;

    /**
     * 装备等级
     */
    private Integer grade;
}
