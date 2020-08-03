package com.dkm.problem.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.dkm.problem.mapper.bo.ProblemEntity
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface ProblemMapper : BaseMapper<ProblemEntity>{
    @Select("select * from tb_problem")
    fun selectAll():List<ProblemEntity>

}
