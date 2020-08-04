package com.dkm.problem.services.impl


import com.dkm.problem.mappers.ProblemMapper
import com.dkm.problem.entities.bo.ProblemEntity
import com.dkm.problem.entities.vo.ProblemVo
import com.dkm.problem.services.IProblemService
import com.dkm.utils.IdGenerator
import com.dkm.utils.bean.ResultVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
@Transactional(rollbackFor = [Exception::class])
class ProblemServiceImpl : IProblemService {
    @Resource
    lateinit var problemMapper: ProblemMapper
    @Resource
    private lateinit var idGenerator:IdGenerator

    override fun getAllProblems(): List<ProblemVo> {
        val result = mutableListOf<ProblemVo>()
        problemMapper.selectAll().forEach {
            it.run {
                result.add(ProblemVo(id.toString(), problemSubject, problemAnswerA, problemAnswerB, problemAnswerC, problemAnswerD, problemAnswer))
            }
        }
        return result
    }

    override fun insert(problemVo: ProblemVo): ResultVo {
        val newId = idGenerator.numberId
        problemVo.run {
            problemMapper.insert(ProblemEntity(newId, problemSubject, problemAnswerA, problemAnswerB, problemAnswerC, problemAnswerD, problemAnswer))
        }
        return ResultVo(true, newId)
    }

    override fun update(id: Long, problemVo: ProblemVo): ResultVo {
        val entity = problemVo.run { ProblemEntity(id, problemSubject, problemAnswerA, problemAnswerB, problemAnswerC, problemAnswerD, problemAnswer) }
        val res = problemMapper.updateById(entity)
        if (res == 0) {
            return ResultVo(res != 0, "数据行不存在")
        }
        return ResultVo(res != 0, entity.id)
    }

    override fun delete(id: Long): ResultVo {
        val result = problemMapper.deleteById(id)
        if (result == 0) {
            return ResultVo(result != 0, "数据行不存在")
        }
        return ResultVo(result != 0, id)

    }
}