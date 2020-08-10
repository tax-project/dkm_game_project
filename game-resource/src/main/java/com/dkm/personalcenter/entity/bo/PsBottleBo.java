package com.dkm.personalcenter.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: game_project
 * @description: 体力瓶
 * @author: zhd
 * @create: 2020-08-10 10:28
 **/
@ApiModel("体力瓶信息")
@Data
public class PsBottleBo {
    @ApiModelProperty("背包id")
    private Long backpackId;
    @ApiModelProperty("体力瓶名称")
    private String name;
    @ApiModelProperty("图片")
    private String url;
    @ApiModelProperty("数量")
    private Integer number;
}
