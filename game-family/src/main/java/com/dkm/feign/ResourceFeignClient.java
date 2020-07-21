package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.UserSkillVo;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "resource", fallback = ResourceFeignClientFallback.class)
public interface ResourceFeignClient {

    /**
     * 查询技能的名字和等级
     * @param userId
     * @return
     */
    @GetMapping("/SkillController/querySkillByUserId")
    Result<List<UserSkillVo>> querySkillByUserId(@RequestParam(value = "userId") Long userId);
}
