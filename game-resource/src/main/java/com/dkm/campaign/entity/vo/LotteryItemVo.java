package com.dkm.campaign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dragon
 */
@Data
@ApiModel("神秘商店奖品")
public class LotteryItemVo {
    @ApiModelProperty("奖池的代号")
    private long id;
    @ApiModelProperty("价值")
    private int marketPrice;
    @ApiModelProperty("商品信息")
    private PrizeInfoVo prize;
    @ApiModelProperty("商品数目")
    private int prizeSize;
    @ApiModelProperty("参与次数")
    private int participation;
    @ApiModelProperty("夺宝总数")
    private int total;
    @ApiModelProperty("用户参与的次数")
    private int userParticipation;
}
