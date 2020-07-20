package com.dkm.backpack.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: game_project
 * @description: 添加物品信息
 * @author: zhd
 * @create: 2020-07-18 09:21
 **/
@NoArgsConstructor
@AllArgsConstructor
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
