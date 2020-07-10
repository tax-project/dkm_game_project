package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.ParamBo;
import com.dkm.entity.bo.UserHeardUrlBo;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.entity.IncreaseUserInfoBO;
import com.dkm.feign.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/13
 * @vesion 1.0
 **/
@FeignClient(value = "user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

   /**
    *  查询所有用户信息
    * @param id
    * @return
    */
   @GetMapping("/v1/we/chat/queryUser/{id}")
   Result<UserInfoQueryBo> queryUser (@PathVariable("id") Long id);

   /**
    *  修改用户的每日抢红包的总次数
    * @param much 次数
    * @param userId 用户Id
    * @param userInfoDiamonds 钻石
    * @return 返回结果
    */
   @GetMapping("/v1/userInfo/updateMuch/{much}/{userId}/{userInfoDiamonds}")
   Result updateUserInfo (@PathVariable("much") Integer much,
                          @PathVariable("userId") Long userId,
                          @PathVariable("userInfoDiamonds") Integer userInfoDiamonds);

   /**
    *  根据用户Id集合查询所有用户头像
    * @param bo
    * @return
    */
   @PostMapping("/v1/we/chat/queryAllHeardByUserId")
   Result<List<UserHeardUrlBo>> queryAllHeardByUserId (@RequestBody ParamBo bo);

   /**
    * 增加减少金币经验等
    * @param increaseUserInfoBO
    * @return
    */
   @PostMapping("/v1/userInfo/increase")
   Result increaseUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO);
}
