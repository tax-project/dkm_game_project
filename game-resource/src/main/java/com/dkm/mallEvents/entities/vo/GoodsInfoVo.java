package com.dkm.mallEvents.entities.vo;

import lombok.Data;

@Data
public class GoodsInfoVo {
    private Integer id;
    private Long goodsId;
    private String name;
    private String imageUrl;
    private Integer size;
}
