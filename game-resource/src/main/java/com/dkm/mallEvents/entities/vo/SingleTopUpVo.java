package com.dkm.mallEvents.entities.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class SingleTopUpVo {
    /**
     * 累计充值、消耗的id
     */
    private Integer id;
    /**
     * 奖励物品
     */
    final private List<GoodsInfoVo> goods = new ArrayList<>();
    /**
     * 状态：0 表示无法领取，1 表示可领取，2表示领取完成
     */
    @ApiModelProperty("状态：0 表示无法领取，1 表示可领取，2表示领取完成")
    private Integer status;
    private String day;

}
