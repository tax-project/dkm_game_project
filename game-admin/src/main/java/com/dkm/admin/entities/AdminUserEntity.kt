package com.dkm.admin.entities

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tb_admin_users")
data class AdminUserEntity(
        @TableId
        val id: Int,
        val userId: Long
)