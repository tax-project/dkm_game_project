package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.ResourceFeignClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-05-16 15:26
 **/
@Component
public class ResourceFeignClientFallback implements ResourceFeignClient {
    @Override
    public Result updateIsva(Long tekId, Integer foodNumber) {
        return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
    }

    @Override
    public Map<String, Object> selectUserId() {
        Map<String,Object> map = new HashMap<>();
        map.put("fail","调用失败");
        return map;
    }
}
