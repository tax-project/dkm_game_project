package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.diggings.bean.vo.RenownVo;
import com.dkm.diggings.bean.vo.UserInfoBO;
import com.dkm.diggings.bean.vo.UserInfosVo;
import com.dkm.feign.fallback.FamilyUserFeignClientFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user", fallback = FamilyUserFeignClientFallback.class)
public interface FamilyUserFeignClient {

    @PostMapping("/v1/userInfo/increase")
    Result<String> update(UserInfoBO userInfoBO);

    @GetMapping("/v1/userInfo/query/user/section")
    Result<RenownVo> queryUserSection(@RequestParam(value = "userId") Long userId);

    @GetMapping("/v1/we/chat/queryUser/{id}")
    Result<UserInfosVo> queryUser(@PathVariable Long id);

}