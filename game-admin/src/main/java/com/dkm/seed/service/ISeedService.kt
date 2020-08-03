package com.dkm.seed.service


import com.dkm.seed.mapper.vo.SeedVo
import com.dkm.utils.bean.ResultVo

interface ISeedService {

    fun getAll(): List<SeedVo>

    fun insert(seedVo: SeedVo): ResultVo

    fun update(id: Long, seedVo: SeedVo): ResultVo

    fun delete(id: Long): ResultVo


}
