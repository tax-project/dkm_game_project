package com.dkm.admin.services.impl


import com.alibaba.fastjson.JSONObject
import com.auth0.jwt.JWT
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.dkm.admin.entities.AdminUserEntity
import com.dkm.admin.entities.vo.UserLoginResultVo
import com.dkm.admin.entities.vo.UserLoginVo
import com.dkm.admin.mappers.AdminUserMapper
import com.dkm.admin.services.IAdminUserLoginService
import com.dkm.constanct.CodeType
import com.dkm.exception.ApplicationException
import com.dkm.feign.AnotherUserFeignClient
import com.dkm.jwt.contain.LocalUser
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

/**
 * @author OpenE
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class UserLoginServiceImpl : IAdminUserLoginService {
    private val logger = LoggerFactory.getLogger(javaClass)
    @Resource
    private lateinit var localUser: LocalUser

    @Resource
    private lateinit var anotherUserFeignClient: AnotherUserFeignClient

    @Resource
    private lateinit var adminUserMapper: AdminUserMapper
    override fun login(loginVo: UserLoginVo): UserLoginResultVo {
        val result = UserLoginResultVo()
        val login = anotherUserFeignClient.login(loginVo)
        logger.debug(JSONObject.toJSONString(login))
        if (login.code == 1006) {
            throw ApplicationException(CodeType.AUTHENTICATION_ERROR, "密码错误")
        }
        val loginStatus = login
                .data ?: throw ApplicationException(CodeType.FEIGN_CONNECT_ERROR)
        result.loginStatus = true
        result.loginToken = loginStatus.token!!
        return result
    }


    override fun checkToken(token: String): Boolean {
        val userId = try {
            JWT.decode(token).getClaim("id").asLong()
        }catch (_:Exception){
            throw ApplicationException(CodeType.TOKENNULL_ERROR,"token无效！")
        }
        val list = adminUserMapper.selectList(QueryWrapper<AdminUserEntity>().eq("user_id", userId))
        return !(list == null || list.isEmpty())
    }
}