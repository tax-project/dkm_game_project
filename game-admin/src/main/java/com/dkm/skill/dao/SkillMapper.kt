package com.dkm.skill.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.dkm.skill.mapper.bo.SkillEntity
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface SkillMapper : BaseMapper<SkillEntity>{
    @Select("select * from tb_skill")
    fun selectAll():List<SkillEntity>

}
