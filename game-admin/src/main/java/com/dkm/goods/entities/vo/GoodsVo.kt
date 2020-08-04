package com.dkm.goods.entities.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("物品ID")
data class GoodsVo(
        @ApiModelProperty("物品ID")
        val id: String?,
        @ApiModelProperty("物品名称")
        val name: String?,
        @ApiModelProperty("图片地址")
        val url: String?,
        @ApiModelProperty("物品类型[0:金币、2：道具、3：食物]")
        val goodType: Int = -1,
        @ApiModelProperty("物品描述")
        val goodContent: String?,
        @ApiModelProperty("物品价格")
        val goodMoney: Int = -1,
        @ApiModelProperty("前端使用跳转路径")
        val tabUrl: String?
)