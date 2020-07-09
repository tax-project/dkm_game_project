package com.dkm.mine.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OpenE
 */
@ApiModel("被占领的信息")
@Data
public class OccupiedInfoVo {
    @ApiModelProperty("占领的用户ID")
    private Long userId;
    @ApiModelProperty("占领的家族ID")
    private Long userFamily;
    @ApiModelProperty("结束挖矿的时间")
    private LocalDateTime stopDate;

}
