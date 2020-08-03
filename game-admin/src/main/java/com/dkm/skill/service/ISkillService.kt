package com.dkm.skill.service


import com.dkm.skill.mapper.vo.SkillVo
import com.dkm.utils.bean.ResultVo

interface ISkillService {

    fun getAll(): List<SkillVo>

    fun insert(skillVo: SkillVo): ResultVo

    fun update(id: Long, skillVo: SkillVo): ResultVo

    fun delete(id: Long): ResultVo


}
