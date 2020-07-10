package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OpenE
 */
@ApiModel("被占领的信息")
@Data
public class OccupiedVo {
    @ApiModelProperty("占领的用户名称")
    private Long userName;
    @ApiModelProperty("占领的家族名称")
    private Long userFamilyName;
    @ApiModelProperty("结束挖矿的时间")
    private LocalDateTime stopDate;
    @ApiModelProperty("已获得的金币")
    private Long goldSize;
    @ApiModelProperty("已获得的积分")
    private Long integralSize;
}
