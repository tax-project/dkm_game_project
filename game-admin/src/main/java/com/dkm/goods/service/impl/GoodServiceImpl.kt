package com.dkm.goods.service.impl


import com.dkm.goods.dao.GoodsMapper
import com.dkm.goods.mapper.bo.GoodsEntity
import com.dkm.goods.mapper.vo.GoodsVo
import com.dkm.goods.service.IGoodService
import com.dkm.utils.IdGenerator
import com.dkm.utils.bean.ResultVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
@Transactional(rollbackFor = [Exception::class])
class GoodServiceImpl : IGoodService {
    @Resource
    private lateinit var goodsMapper: GoodsMapper
    @Resource
    private lateinit var idGenerator :IdGenerator
    override fun getAllGoods(): List<GoodsVo> {
        val result = mutableListOf<GoodsVo>()
        goodsMapper.selectAllGoods().forEach {
            it.run {
                result.add(GoodsVo(id.toString(), name, url, goodType, goodContent, goodMoney, tabUrl))
            }
        }
        return result
    }

    override fun addGoodsItem(goods: GoodsVo): ResultVo {
        val id = idGenerator.numberId
        goods.run {
            goodsMapper.insert(GoodsEntity(id, name, url, goodType, goodContent, goodMoney, tabUrl))
        }
        return ResultVo(true, id)
    }

    override fun updateItemById(id: Long, goods: GoodsVo): ResultVo {
        val entity = goodsMapper.selectById(id) ?: return ResultVo(false, "未找到此ID的数据")
        if (goods.name != null) {
            entity.name = goods.name
        }
        if (goods.url != null) {
            entity.url = goods.url
        }
        if (goods.goodType >= 0) {
            entity.goodType = goods.goodType
        }
        if (goods.goodContent != null) {
            entity.goodContent = goods.goodContent
        }
        if (goods.goodMoney >= 0) {
            entity.goodMoney = goods.goodMoney
        }
        if (goods.tabUrl != null) {
            entity.tabUrl = goods.tabUrl
        }
        goodsMapper.updateById(entity)
        return ResultVo(true, entity.id)
    }

    override fun deleteItemById(id: String): ResultVo {
        val entity = goodsMapper.selectById(id) ?: return ResultVo(false, "未找到此ID的数据")
        goodsMapper.deleteById(entity.id)
        return ResultVo(true, entity.id)
    }
}