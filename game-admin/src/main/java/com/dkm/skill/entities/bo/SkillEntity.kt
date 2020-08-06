package com.dkm.skill.entities.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tb_skill")
data class SkillEntity(
        @TableId
        var id: Long,
        var skName: String?,
        var skEffectOne: String?,
        var skEffectTwo: String?
)
