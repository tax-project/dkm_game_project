package com.dkm.problem.services


import com.dkm.problem.entities.vo.ProblemVo
import com.dkm.utils.bean.ResultVo

interface IProblemService {
    /**
     * 得到所有的問答列表
     * @return List<ProblemVo> 所有的問答列表，如果不存在問答則返回 NULL
     */
    fun getAllProblems(): List<ProblemVo>

    /**
     * 添加一个问答选项
     *
     * @param problemVo ProblemVo
     * @return ResultVo
     */
    fun insert(problemVo: ProblemVo): ResultVo

    /**
     * 根据 ID 来更新一个数据
     * @param id String
     * @param problemVo ProblemVo
     * @return ResultVo
     */
    fun update(id: Long, problemVo: ProblemVo): ResultVo

    /**
     * 根据一个 ID 来删除问答
     *
     * @param id Long
     * @return ResultVo
     */
    fun delete(id: Long): ResultVo


}
