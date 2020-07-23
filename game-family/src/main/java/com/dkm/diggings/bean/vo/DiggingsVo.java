package com.dkm.diggings.bean.vo;

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
public class DiggingsVo {
    @ApiModelProperty("家族ID")
    private Long familyId;
    @ApiModelProperty("家族名称")
    private String familyName;
    @ApiModelProperty("家族等级")
    private Integer familyLevel;
    @ApiModelProperty("家族排名")
    private Integer familyRanking;
    @ApiModelProperty("用户排名")
    private Integer userRanking = 0;
    @ApiModelProperty("剩余攻击次数")
    private int occupationSize;
    @ApiModelProperty("地图上左上角的家族名称")
    private String topLeftFamilyName;
    @ApiModelProperty("地图上又上角的家族名称")
    private String topRightFamilyName;
    @ApiModelProperty("地图上右下角的家族名称")
    private String bottomRightFamilyName;
    @ApiModelProperty("公共地图的矿山")
    final private List<MineVo> publicItem = new ArrayList<>();
    @ApiModelProperty("私有地图的矿山")
    final private List<MineVo> privateItem = new ArrayList<>();

}
