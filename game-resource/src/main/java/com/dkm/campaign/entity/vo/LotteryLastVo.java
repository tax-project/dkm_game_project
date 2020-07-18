package com.dkm.campaign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("上次中奖的信息")
@Data
public class LotteryLastVo {
    @ApiModelProperty("user Name")
    private String userName;
    @ApiModelProperty("user ID")
    private Long userId;
    @ApiModelProperty("lottery result message.")
    private String message;
    @ApiModelProperty("diamonds size")
    private long diamonds;
}
