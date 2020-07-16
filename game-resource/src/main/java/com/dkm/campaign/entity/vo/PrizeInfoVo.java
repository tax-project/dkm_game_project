package com.dkm.campaign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author fkl
 */
@ApiModel("商品信息")
@Data
public class PrizeInfoVo {
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品 URL")
    private String imageUrl;
}
