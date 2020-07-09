package com.dkm.mine.bean.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author OpenE
 */
@ApiModel("矿山的基本信息")
@Data
public class BattleItemInfoVo {
    @ApiModelProperty("矿山的等级")
    private int level;
    @ApiModelProperty("默认对手的名称")
    private String npcName;
    @ApiModelProperty("默认对手的技能等级")
    private int npcSkillLevel;
    @ApiModelProperty("金币产出的数目")
    private long goldYield;
    @ApiModelProperty("经验产出的数目")
    private long integralYield;
}
