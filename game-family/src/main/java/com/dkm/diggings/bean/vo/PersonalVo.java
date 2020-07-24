package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;




@ApiModel("排名")
@Data
public class PersonalVo {
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("积分")
    private Integer integral;
    @ApiModelProperty("用户图片")
    private String userImage;
    @ApiModelProperty("剩余挖矿次数")
    private Integer minesRemaining = 2;
}
