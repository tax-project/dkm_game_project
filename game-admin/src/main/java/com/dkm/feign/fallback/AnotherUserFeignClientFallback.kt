package com.dkm.feign.fallback

import com.dkm.admin.entities.vo.UserLoginVo
import com.dkm.constanct.CodeType
import com.dkm.data.Result
import com.dkm.feign.AnotherUserFeignClient
import com.dkm.feign.entity.UserLoginStatusVo
import org.springframework.stereotype.Component


@Component
class AnotherUserFeignClientFallback :AnotherUserFeignClient  {

    override fun login(userLoginVo: UserLoginVo): Result<UserLoginStatusVo> {
        return Result.fail<UserLoginStatusVo>(CodeType.FEIGN_CONNECT_ERROR)
    }

}
