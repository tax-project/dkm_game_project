package com.dkm.campaign.entity.dobj;


import lombok.Data;

/**
 * @author OpenE
 */
@Data
public class LotteryItemStatus {
    private long id;
    private String name;
    private long goodsId;
    private long goodsSize;
    private int usedSize;
    private int size;
}
