package com.dkm.feign.fallback

import com.dkm.admin.mapper.vo.UserLoginVo
import com.dkm.constanct.CodeType
import com.dkm.data.Result
import com.dkm.feign.SimpleUserFeignClient
import com.dkm.feign.entity.UserLoginStatusVo

class SimpleUserFeignClientFallback :SimpleUserFeignClient  {
    override fun login(userLoginVo: UserLoginVo): Result<UserLoginStatusVo> {
        return Result.fail<UserLoginStatusVo>(CodeType.FEIGN_CONNECT_ERROR)
    }

}
