package com.dkm.skill.service.impl


import com.dkm.skill.dao.SkillMapper
import com.dkm.skill.mapper.bo.SkillEntity
import com.dkm.skill.mapper.vo.SkillVo
import com.dkm.skill.service.ISkillService
import com.dkm.utils.IdGenerator
import com.dkm.utils.bean.ResultVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
@Transactional(rollbackFor = [Exception::class])
class SkillServiceImpl : ISkillService {
    @Resource
    lateinit var skillMapper: SkillMapper

    @Resource
    private lateinit var idGenerator: IdGenerator
    override fun getAll(): List<SkillVo> {
        val list = skillMapper.selectAll()
        val result = mutableListOf<SkillVo>()
        for (entity in list) {
            result.add(SkillVo(entity.id.toString(), entity.skName
                    , entity.skImg, entity.skEffectOne, entity.skEffectTwo))
        }
        return result
    }

    override fun insert(skillVo: SkillVo): ResultVo {
        val newId = idGenerator.numberId
        skillMapper.insert(SkillEntity(newId, skillVo.skinName,
                skillVo.skinImageUrl, skillVo.firstSkinEffect, skillVo.secondSkinEffect))
        return ResultVo(true, newId)
    }

    override fun update(id: Long, skillVo: SkillVo): ResultVo {
        val entity = SkillEntity(id, skillVo.skinName,
                skillVo.skinImageUrl, skillVo.firstSkinEffect, skillVo.secondSkinEffect)
        val res = skillMapper.updateById(entity)
        if (res == 0) {
            return ResultVo(res != 0, "数据行不存在")
        }
        return ResultVo(res != 0, entity.id)
    }


    override fun delete(id: Long): ResultVo {
        val result = skillMapper.deleteById(id)
        if (result == 0) {
            return ResultVo(result != 0, "数据行不存在")
        }
        return ResultVo(result != 0, id)
    }
}
