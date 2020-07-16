package com.dkm.campaign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;

/**
 * 神秘商店的基础信息
 *
 * @author fkl
 */
@ApiModel("神秘商店的基础信息")
@Data
public class LotteryInfoVo {
    @ApiModelProperty("下次刷新的时间")
    private String refreshDate;
    @ApiModelProperty("奖品")
    final private LinkedList<LotteryItemVo> items = new LinkedList<>();
}
