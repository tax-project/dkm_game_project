package com.dkm.mallEvents.entities.data;

import com.dkm.good.entity.Goods;
import lombok.Data;

import java.util.List;

@Data
public class CancerGiftBoxInfoVo {
    private Integer yourCancerGiftSize = 0;
    private List<GoodsVo> giftItem;
}
