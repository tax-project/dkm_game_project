package com.dkm.mallEvents.entities.data;

import lombok.Data;

/**
 * Recharge Item Info
 *
 * @date 2020/7/29
 */
@Data
public class GoodsVo {

    /**
     * item id
     */
    private Long id;
    /**
     * item name
     */
    private String name;
    /**
     * item size
     */
    private Integer size;
    /**
     * item image URL
     */
    private String url;
}
