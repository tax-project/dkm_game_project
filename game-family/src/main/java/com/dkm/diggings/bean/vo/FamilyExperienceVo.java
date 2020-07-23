package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("积分")
@Data
public class FamilyExperienceVo {
    @ApiModelProperty("总积分数目")
    private int integral = 0;
    @ApiModelProperty("家族名称")
    private String name;
    @ApiModelProperty("图片ID")
    private String image;
}
