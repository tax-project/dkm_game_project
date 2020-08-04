package com.dkm.goods.services


import com.dkm.goods.entities.vo.GoodsVo
import com.dkm.utils.bean.ResultVo

interface IGoodService {
    fun getAllGoods(): List<GoodsVo>
    fun addGoodsItem(goods: GoodsVo): ResultVo
    fun updateItemById(id: Long, goods: GoodsVo): ResultVo
    fun deleteItemById(id: String): ResultVo
}