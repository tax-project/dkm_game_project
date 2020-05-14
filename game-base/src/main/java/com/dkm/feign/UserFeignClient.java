package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.feign.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qf
 * @date 2020/5/13
 * @vesion 1.0
 **/
@FeignClient(value = "service-user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

   /**
    *  查询所有用户信息
    * @param id
    * @return
    */
   @GetMapping("/v1/we/chat/queryUser/{id}")
   Result<UserInfoBo> queryUser (@PathVariable("id") Long id);

   /**
    *  修改用户的每日抢红包的总次数
    * @param much 次数
    * @param userId 用户Id
    * @return 返回结果
    */
   @GetMapping("/v1/userInfo/updateMuch/{much}/{userId}")
   Result updateUserInfo (@PathVariable("much") Integer much, @PathVariable("userId") Long userId);
}
