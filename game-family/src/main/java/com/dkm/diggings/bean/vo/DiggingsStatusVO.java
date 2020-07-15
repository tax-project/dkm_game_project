package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dragon
 */
@ApiModel("矿区的状态信息")
@Data
public class DiggingsStatusVO {
    @ApiModelProperty("剩余挖矿的次数")
    private Integer remaining;
    @ApiModelProperty("是否正在挖矿")
    private Boolean mining;
    @ApiModelProperty("结束挖矿的时间")
    private String stopDate;
    @ApiModelProperty("以获得的金钱")
    private Integer gold;
    @ApiModelProperty("以获得的积分")
    private Integer integral;
}
