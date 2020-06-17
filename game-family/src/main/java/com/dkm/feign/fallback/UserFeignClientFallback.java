package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.UserFeignClient;
import com.dkm.goldMine.bean.ao.UserInfoBO;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@Component
public class UserFeignClientFallback implements UserFeignClient {


   @Override
   public Result<UserInfoBO> updateGold(UserInfoBO userInfoBO) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
