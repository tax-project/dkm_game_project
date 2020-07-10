package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.UserSkillVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2020/7/7 10:24
 * @Version 1.0
 */
@Component
public class ResourceFeignClientFallback implements ResourceFeignClient {
    @Override
    public Result<List<UserSkillVo>> querySkillByUserId(Long userId) {
        return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
    }
}
