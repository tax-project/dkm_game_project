package com.dkm.diggings.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 矿区的详情信息
 *
 * @author OpenE
 */
@Data
@ApiModel("矿区的详情信息")
public class MineDetailVo {
    @ApiModelProperty("矿区的详情信息")
    private MineInfoVo info;
    @ApiModelProperty("产出的物品名称")
    private final List<String> outputItems = new ArrayList<>();
    @ApiModelProperty("获胜的成功率")
    private double successRate;
    @ApiModelProperty("技能名称")
    private String skillName;
    @ApiModelProperty("对手的姓名")
    private String herName;
    @ApiModelProperty("对手的技能等级")
    private String herSkillLevel;
    @ApiModelProperty("自己的的技能等级")
    private String skillLevel;
}
