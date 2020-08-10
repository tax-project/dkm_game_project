package com.dkm.skill.entities.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel("技能")
data class SkillVo(
        @ApiModelProperty("技能ID")
        var id:String?,
        @ApiModelProperty("技能名称")
        var skinName: String?,
        @ApiModelProperty("第一个技能效果")
        var firstSkinEffect: String?,
        @ApiModelProperty("第二个技能效果")
        var secondSkinEffect: String?
)
