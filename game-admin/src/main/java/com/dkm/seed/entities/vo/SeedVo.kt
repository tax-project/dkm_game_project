package com.dkm.seed.entities.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal


@ApiModel("种子表")
data class SeedVo(
        @ApiModelProperty("种子ID")
        var id: String?,
        @ApiModelProperty("种子名称")
        var name: String?,
        @ApiModelProperty("种子等级")
        var level: Int?,
        @ApiModelProperty("首次种植时金币数目")
        var firstGold: Int?,
        @ApiModelProperty("首次种植时的红包大小")
        var firstPrize: BigDecimal?,
        @ApiModelProperty("种子产出的经验")
        var xp: Int?,
        @ApiModelProperty("种植的金币")
        var gold: Int?,
        @ApiModelProperty("图片地址")
        var imageUrl: String?
)
