package com.dkm.backpack.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: game_project
 * @description: 装备信息
 * @author: zhd
 * @create: 2020-07-20 11:13
 **/
@Data
@ApiModel("装备信息")
public class EquipmentVo {
    @ApiModelProperty("背包id")
    private Long backpackId;
    @ApiModelProperty("血量")
    private Integer blood;
    @ApiModelProperty("血量加成")
    private BigDecimal bloodAdd;
    @ApiModelProperty("声望")
    private Integer renown;
    @ApiModelProperty("装备等级")
    private Integer grade;
    @ApiModelProperty("才华")
    private Integer talent;
    @ApiModelProperty("才华加成")
    private BigDecimal talentAdd;
    @ApiModelProperty("经验掉落")
    private Integer exp;
    @ApiModelProperty("暴击率")
    private BigDecimal crit;
    @ApiModelProperty("装备掉落率")
    private BigDecimal eqDrop;
    @ApiModelProperty("需要等级")
    private Integer needGrade;
    @ApiModelProperty("装备类型 1 - 8 ")
    private Integer eqType;
    @ApiModelProperty("是否装备 0 为未装备 1为装备")
    private Integer isEquip;
    @ApiModelProperty("装备图片")
    private String url;
    @ApiModelProperty("装备名称")
    private String name;
}
