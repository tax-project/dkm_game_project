package com.dkm.admin.service.impl


import com.dkm.admin.dao.AdminUserMapper
import com.dkm.admin.dao.AdminUserTokenMapper
import com.dkm.admin.mapper.bo.AdminUserTokenEntity
import com.dkm.admin.mapper.vo.UserLoginResultVo
import com.dkm.admin.mapper.vo.UserLoginVo
import com.dkm.admin.service.IAdminUserLoginService
import com.dkm.jwt.Interceptor.AuthenticationInterceptor
import com.dkm.utils.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

/**
 * @author OpenE
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class UserLoginServiceImpl : IAdminUserLoginService {
    @Resource
    private lateinit var idGenerator:IdGenerator

    @Resource
    private lateinit var adminUserMapper: AdminUserMapper

    @Resource
    private lateinit var tokenMapper: AdminUserTokenMapper
    override fun login(loginVo: UserLoginVo): UserLoginResultVo {
        val result = UserLoginResultVo()
        val item = adminUserMapper.selectAllByUserName(loginVo.userName)
        if (item != null) {
            val loginStatus = loginVo.password == item.password
            result.loginStatus = loginStatus
            if (loginStatus) {
                val token = idGenerator.numberId
                result.loginToken = token.toString()
                val userTokenEntity = AdminUserTokenEntity()
                userTokenEntity.tokenId = token
                userTokenEntity.userId = item.id
                tokenMapper.insert(userTokenEntity)

            }
        }
        return result
    }

    override fun checkToken(token: String): Boolean {
        return tokenMapper.findItemByToken(token) != null
    }
}