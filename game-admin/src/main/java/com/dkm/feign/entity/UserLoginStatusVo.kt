package com.dkm.feign.entity

data class UserLoginStatusVo(
    val cid: String,
    val resultUser: ResultUser,
    val token: String
)

data class ResultUser(
    val userGold: Int,
    val userGrade: Int,
    val userId: String,
    val userIdCard: Any,
    val userIsEffective: Int,
    val userIsVip: Int,
    val userPacketBalance: Int,
    val userPhone: Any,
    val userRemark: String,
    val userRenown: Int,
    val weChatHeadImgUrl: Any,
    val weChatNickName: String,
    val weChatOpenId: String
)