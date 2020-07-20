package com.dkm.backpack.entity.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-18 10:17
 **/
@Data
public class SellGoodsInfo {
    /**
     * userId
     */
    private Long userId;
    /**
     * 背包id
     */
    private Long backpackId;
    /**
     * 出售数量
     */
    private Integer number;
}
