package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.UserSkillVo;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/1 14:43
 */
@FeignClient(value = "resource", fallback = ResourceFeignClientFallback.class)
@Component
public interface ResourceFeignClient {

    /**
     * 查询技能的名字和等级
     * @param userId
     * @return
     */
    @GetMapping("/SkillController/querySkillByUserId")
    Result<List<UserSkillVo>> querySkillByUserId(@RequestParam(value = "userId") Long userId);
}
