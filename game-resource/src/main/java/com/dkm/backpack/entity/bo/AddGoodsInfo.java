package com.dkm.backpack.entity.bo;

import lombok.Data;

/**
 * @program: game_project
 * @description: 添加物品信息
 * @author: zhd
 * @create: 2020-07-18 09:21
 **/
@Data
public class AddGoodsInfo {
    /**
     * userId
     */
    private Long userId;
    /**
     * 物品id
     */
    private Long goodId;
    /**
     * 物品增加减少数量
     */
    private Integer number;
}
