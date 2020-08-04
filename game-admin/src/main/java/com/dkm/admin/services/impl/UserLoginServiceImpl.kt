package com.dkm.admin.services.impl


import com.dkm.admin.mappers.AdminUserMapper
import com.dkm.admin.mappers.AdminUserTokenMapper
import com.dkm.admin.entities.bo.AdminUserTokenEntity
import com.dkm.admin.entities.vo.UserLoginResultVo
import com.dkm.admin.entities.vo.UserLoginVo
import com.dkm.admin.services.IAdminUserLoginService
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