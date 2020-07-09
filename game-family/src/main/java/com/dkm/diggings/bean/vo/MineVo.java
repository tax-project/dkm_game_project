package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author OpenE
 */
@ApiModel("矿山的信息")
@Data
public class MineVo {
    @ApiModelProperty("矿山排序ID")
    private long index;
    @ApiModelProperty("矿山唯一ID")
    private long id;
    @ApiModelProperty("等级")
    private Integer level;
    @ApiModelProperty("是否处于被占领状态")
    private boolean occupied = false;
    @ApiModelProperty("如果被占领那么将显示占领者的信息")
    private OccupiedVo occupiedInfo = null;

}
