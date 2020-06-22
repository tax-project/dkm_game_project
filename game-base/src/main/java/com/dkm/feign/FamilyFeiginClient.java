package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.UserCenterFamilyVo;
import com.dkm.feign.fallback.FamilyFeiginClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 家族
 * @author: zhd
 * @create: 2020-06-22 16:27
 **/
@FeignClient(value = "family",fallback = FamilyFeiginClientFallback.class)
public interface FamilyFeiginClient {
    @GetMapping("/family/getUserCenterFamily")
    Result<UserCenterFamilyVo> getUserCenterFamily(@RequestParam("userId") Long userId);
}
