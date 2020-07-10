package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.UserSkillVo;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2020/7/7 10:21
 * @Version 1.0
 */
@FeignClient(value = "resource", fallback = ResourceFeignClientFallback.class)
public interface ResourceFeignClient {

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/SkillController/querySkillByUserId")
    Result<List<UserSkillVo>> querySkillByUserId(@RequestParam("userId") Long userId);
}
