package com.dkm.goods.service


import com.dkm.goods.mapper.vo.GoodsVo
import com.dkm.utils.bean.ResultVo

interface IGoodService {
    fun getAllGoods(): List<GoodsVo>
    fun addGoodsItem(goods: GoodsVo): ResultVo
    fun updateItemById(id: Long, goods: GoodsVo): ResultVo
    fun deleteItemById(id: String): ResultVo
}