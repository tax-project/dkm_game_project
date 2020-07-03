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
    private Long index;
    @ApiModelProperty("矿山唯一ID")
    private Long id;

}
