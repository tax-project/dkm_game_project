package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.FriendFeignClient;
import com.dkm.feign.entity.FriendNotOnlineVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/14
 * @vesion 1.0
 **/
public class FriendFeignClientFallback implements FriendFeignClient {


   @Override
   public Result<List<FriendNotOnlineVo>> queryNotOnline(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result updateLookStatus(List<Long> list) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }


}
