package com.dkm.admin.entities.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

/**
 * @author OpenE
 */
@Data
@TableName("tb_admin_user_token")
data class AdminUserTokenEntity(
        @TableId
        var id: Int = 0,
        var tokenId: Long = 0,
        var userId: Int = 0
)

