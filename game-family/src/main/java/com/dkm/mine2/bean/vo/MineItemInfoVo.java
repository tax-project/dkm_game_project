package com.dkm.mine2.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author OpenE
 */
@ApiModel("矿山的信息")
@Data
public class MineItemInfoVo {
    @ApiModelProperty("矿山排序ID")
    private long index;
    @ApiModelProperty("矿山唯一ID")
    private long id;
    @ApiModelProperty("是否处于被占领状态")
    private Boolean occupied;
    @ApiModelProperty("如果被占领那么占领者的信息")
    private OccupiedInfoVo occupiedInfo = null;
    @ApiModelProperty("矿山的基本信息")
    final private BattleItemPropVo prop = new BattleItemPropVo();

}
