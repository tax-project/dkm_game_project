package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/5/13
 * @vesion 1.0
 **/
@Component
public class UserFeignClientFallback implements UserFeignClient {


   @Override
   public Result<UserInfoQueryBo> queryUser(Long id) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   /**
    * feign调用失败的回调
    * @param much 次数
    * @param userId 用户Id
    */
   @Override
   public Result updateUserInfo(Integer much, Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
