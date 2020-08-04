package com.dkm.admin.entities.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * @author OpenE
 */
@TableName("tb_admin_user")
data class AdminUserEntity(
        @TableId
        var id: Int = 0,

        /**
         * 管理员账号
         */
        var userName: String? = null,
        var password: String? = null
)
