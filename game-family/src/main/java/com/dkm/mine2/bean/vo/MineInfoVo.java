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
public class MineInfoVo {
    @ApiModelProperty("家族ID")
    private Long familyId;
    @ApiModelProperty("家族等级")
    private Integer familyLevel;
    @ApiModelProperty("家族等级名称")
    private String familyLevelName;
    @ApiModelProperty("剩余攻击次数")
    private Long occupationSize;
    @ApiModelProperty("金币加成")
    private Double goldBonus;
    @ApiModelProperty("地图上左上角的家族ID，如果为0表示暂无家族")
    private Long topLeftFamilyId;
    @ApiModelProperty("地图上又上角的家族ID，如果为0表示暂无家族")
    private Long topRightFamilyId;
    @ApiModelProperty("地图上右下角的家族ID，如果为0表示暂无家族")
    private Long bottomRightFamilyId;
    @ApiModelProperty("公共地图的矿山")
    final private List<MineItemInfoVo> publicItem = new ArrayList<>();
    @ApiModelProperty("私有地图的矿山")
    final private List<MineItemInfoVo> privateItem = new ArrayList<>();

}
