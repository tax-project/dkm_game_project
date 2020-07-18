package com.dkm.campaign.entity;

import lombok.Data;

/**
 * 是否
 *
 * @author OpenE
 */
@Data
public class LotteryItemEntity {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer goodsSize;
    private Long goodsId;
    private Integer size;
    private Long money;
    private Integer usedSize = 0;
    private Integer userSize = 0;

}
