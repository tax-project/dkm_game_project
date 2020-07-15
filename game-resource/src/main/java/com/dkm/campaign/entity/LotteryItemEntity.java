package com.dkm.campaign.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OpenE
 */
@Data
public class LotteryItemEntity {

    private Long id;
    private LocalDateTime expiration;
    private String name;
    private Long goodsId;
    private Long goodsMoney;
    private String itemSize;
    private String imageUrl;

}
