package com.dkm.campaign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ApiModel("购买后返回的数据")
@Data
public class LotteryBuyResultVo {
    @ApiModelProperty("购买的状态信息")
    private boolean status;
    @ApiModelProperty("状态信息")
    private String resultMsg;
}
