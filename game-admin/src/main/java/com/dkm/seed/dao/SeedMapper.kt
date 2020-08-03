package com.dkm.seed.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.dkm.seed.mapper.bo.SeedEntity
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface SeedMapper : BaseMapper<SeedEntity>{
    @Select("select * from tb_seed")
    fun selectAll():List<SeedEntity>

}
