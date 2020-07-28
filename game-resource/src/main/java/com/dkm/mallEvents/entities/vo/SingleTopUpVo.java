package com.dkm.mallEvents.entities.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SingleTopUpVo {
    private Integer id;
    final private List<GoodsInfoVo> goods = new ArrayList<>();
    private Boolean status;
    private Integer day;
}
