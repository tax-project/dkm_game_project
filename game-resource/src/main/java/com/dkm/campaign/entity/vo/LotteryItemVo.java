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
    /**
     * 奖池的代号
     */
    @ApiModelProperty("奖池的代号")
    private long id;
    /**
     * 价值
     */
    @ApiModelProperty("价值")
    private int marketPrice;
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String goodsName;
    /**
     * 商品 URL
     */
    @ApiModelProperty("商品 URL")
    private String goodsImageUrl;
    /**
     * 商品数目
     */
    @ApiModelProperty("商品数目")
    private int prizeSize;
    /**
     * 参与次数
     */
    @ApiModelProperty("参与次数")
    private int participation;
    /**
     * 夺宝总数
     */
    @ApiModelProperty("夺宝总数")
    private int total;
    /**
     * 用户参与的次数
     */
    @ApiModelProperty("用户参与的次数")
    private int userParticipation;
}
