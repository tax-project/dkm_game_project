package com.dkm.feign.fallback;

import com.dkm.feign.ResourceFeignClient;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2020/7/7 10:24
 * @Version 1.0
 */
public class ResourceFeignClientFallback implements ResourceFeignClient {
    @Override
    public List<UserSkill> querySkillByUserId(Long userId) {
        return null;
    }
}
