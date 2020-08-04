package com.dkm.problem.mappers

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.dkm.problem.entities.bo.ProblemEntity
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface ProblemMapper : BaseMapper<ProblemEntity>{
    @Select("select * from tb_problem")
    fun selectAll():List<ProblemEntity>

}
