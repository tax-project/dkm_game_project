package com.dkm.skill.mapper.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tb_skill")
data class SkillEntity(
        @TableId
        var id: Long,
        var skName: String?,
        var skImg: String?,
        var skEffectOne: String?,
        var skEffectTwo: String?
)
