package com.dkm.seed.mapper.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.math.BigDecimal

@TableName("tb_seed")
data class SeedEntity(
        @TableId
        var seedId: Long,
        var seedName: String?,
        var seedGrade: Int?,
        var seedProdgold: Int?,
        var seedProdred: BigDecimal?,
        var seedExperience: Int?,
        var seedGold: Int?,
        var seedImg: String?
)
