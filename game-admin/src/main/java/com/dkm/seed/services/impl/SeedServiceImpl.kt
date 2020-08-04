package com.dkm.seed.services.impl


import com.dkm.utils.IdGenerator
import com.dkm.seed.mappers.SeedMapper
import com.dkm.seed.entities.bo.SeedEntity
import com.dkm.seed.entities.vo.SeedVo
import com.dkm.seed.services.ISeedService
import com.dkm.utils.bean.ResultVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
@Transactional(rollbackFor = [Exception::class])
class SeedServiceImpl : ISeedService {
    @Resource
    lateinit var seedMapper: SeedMapper
    @Resource
    private lateinit var idGenerator :IdGenerator
    override fun getAll(): List<SeedVo> {
        val list = seedMapper.selectAll()
        val result = mutableListOf<SeedVo>()
        for (entity in list) {
            result.add(
                    SeedVo(
                            entity.seedId.toString(),
                            entity.seedName,
                            entity.seedGrade,
                            entity.seedProdgold,
                            entity.seedProdred,
                            entity.seedExperience,
                            entity.seedGold,
                            entity.seedImg
                    )
            )
        }
        return result
    }

    override fun insert(seedVo: SeedVo): ResultVo {
        val newId = idGenerator.numberId
        seedMapper.insert(
                SeedEntity(
                        newId,
                        seedVo.name,
                        seedVo.level,
                        seedVo.firstGold,
                        seedVo.firstPrize,
                        seedVo.xp,
                        seedVo.gold,
                        seedVo.imageUrl

                ))
        return ResultVo(true, newId)
    }

    override fun update(id: Long, seedVo: SeedVo): ResultVo {
        val entity = SeedEntity(
                id,
                seedVo.name,
                seedVo.level,
                seedVo.firstGold,
                seedVo.firstPrize,
                seedVo.xp,
                seedVo.gold,
                seedVo.imageUrl

        )
        val res = seedMapper.updateById(entity)
        if (res == 0) {
            return ResultVo(res != 0, "数据行不存在")
        }
        return ResultVo(res != 0, entity.seedId)
    }

    override fun delete(id: Long): ResultVo {
        val result = seedMapper.deleteById(id)
        if (result == 0) {
            return ResultVo(result != 0, "数据行不存在")
        }
        return ResultVo(result != 0, id)
    }

}
