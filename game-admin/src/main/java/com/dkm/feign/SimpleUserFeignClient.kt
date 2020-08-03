package com.dkm.feign

import com.dkm.admin.mapper.vo.UserLoginVo
import com.dkm.data.Result
import com.dkm.feign.entity.UserLoginStatusVo
import com.dkm.feign.fallback.SimpleUserFeignClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(value = "user", fallback = SimpleUserFeignClientFallback::class)
interface SimpleUserFeignClient {

    /**
     * user login feign
     *
     * - userName
     * - password
     *
     * @param userLoginVo UserLoginVo login info
     * @return Result<UserLoginStatusVo> user info result
     */
    @PostMapping("/v1/we/chat/loginUser" ,produces = ["application/json"])
    fun login(@RequestBody userLoginVo: UserLoginVo): Result<UserLoginStatusVo>
}