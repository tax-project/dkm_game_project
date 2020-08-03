package com.dkm.admin.mapper.vo


data class UserLoginResultVo(
        var loginStatus: Boolean = false,
        var loginToken: String = ""
)