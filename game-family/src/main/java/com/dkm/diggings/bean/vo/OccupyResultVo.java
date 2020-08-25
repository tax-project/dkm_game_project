package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fkl
 */
@ApiModel("占领矿山结果")
@Data
public class  OccupyResultVo {
    @ApiModelProperty("是否占领成功")
    private Boolean status;
    @ApiModelProperty("占领矿山的信息")
    private MineInfoVo info;
}
