package com.dkm.mine2.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fkl
 */
@ApiModel("矿区所有信息")
@Data
public class MineVo {
    @ApiModelProperty("家族ID")
    private Long familyId;
    @ApiModelProperty("家族等级")
    private Integer familyLevel;
    @ApiModelProperty("剩余攻击次数")
    private Long occupationSize;
    @ApiModelProperty("地图上左上角的家族ID，如果为0表示暂无家族")
    private Long topLeftFamilyId;
    @ApiModelProperty("地图上又上角的家族ID，如果为0表示暂无家族")
    private Long topRightFamilyId;
    @ApiModelProperty("地图上右下角的家族ID，如果为0表示暂无家族")
    private Long bottomRightFamilyId;
    @ApiModelProperty("公共地图的矿山")
    final private List<MineItemVo> publicItem = new ArrayList<>();
    @ApiModelProperty("私有地图的矿山")
    final private List<MineItemVo> privateItem = new ArrayList<>();

}
