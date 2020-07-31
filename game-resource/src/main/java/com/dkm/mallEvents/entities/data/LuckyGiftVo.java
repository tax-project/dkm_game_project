package com.dkm.mallEvents.entities.data;

import lombok.Data;

import java.util.List;

/**
 * Module  "Lucky Gift"
 *
 * @date 2020/7/30
 */
@Data
public class LuckyGiftVo {
    /**
     * lucky goods items
     */
    private List<GoodsVo> luckyItems;
    /**
     * last lucky goods;
     */
    private GoodsVo lastLuckyItem;
    /**
     * next refresh date
     */
    private String nextRefreshDate;

}
