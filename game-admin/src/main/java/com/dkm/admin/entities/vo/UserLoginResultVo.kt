package com.dkm.admin.entities.vo


data class UserLoginResultVo(
        var loginStatus: Boolean = false,
        var loginToken: String = ""
)