package com.dkm.goods.mapper.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tb_goods")
data class GoodsEntity(
        @TableId
        val id: Long,
        var name: String?,
        var url: String?,
        var goodType: Int = 0,
        var goodContent: String?,
        var goodMoney: Int = 0,
        var tabUrl: String?
)

