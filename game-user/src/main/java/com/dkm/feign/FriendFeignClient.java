package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.FriendNotOnlineVo;
import com.dkm.feign.fallback.FriendFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/14
 * @vesion 1.0
 **/
@FeignClient(value = "service-chat", fallback = FriendFeignClientFallback.class)
public interface FriendFeignClient {


   /**
    *  查询该用户所有离线信息
    * @param userId 用户id
    * @return 返回数据
    */
   @GetMapping("/v1/notOnline/queryNotInfo/{userId}")
   Result<List<FriendNotOnlineVo>> queryNotOnline(@PathVariable("userId") Long userId);


   /**
    * 更改未读的状态
    * @param list id集合
    * @return 返回结果
    */
   @PostMapping("/v1/notOnline/updateLookStatus")
   Result updateLookStatus(@RequestBody List<Long> list);
}
