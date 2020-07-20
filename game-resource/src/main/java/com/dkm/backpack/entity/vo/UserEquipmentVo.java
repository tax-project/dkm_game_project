package com.dkm.backpack.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-20 11:39
 **/
@Data
@ApiModel("装备信息")
public class UserEquipmentVo {
    @ApiModelProperty("装备图片")
    private String url;
    @ApiModelProperty("装备图片")
    private String name;
    @ApiModelProperty("装备等级")
    private Integer grade;
    @ApiModelProperty("背包id")
    private Long backpackId;
    @ApiModelProperty("物品id")
    private Long goodId;

    @JsonIgnore
    private Integer eqType;
}
