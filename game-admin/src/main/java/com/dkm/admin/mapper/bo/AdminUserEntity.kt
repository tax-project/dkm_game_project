package com.dkm.admin.mapper.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import lombok.Data

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
