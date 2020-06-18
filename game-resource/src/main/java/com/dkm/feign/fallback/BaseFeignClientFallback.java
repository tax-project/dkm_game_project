package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.GiftRankingDto;
import com.dkm.feign.entity.PetsDto;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/13
 * @vesion 1.0
 **/
@Component
public class BaseFeignClientFallback implements BaseFeignClient {


   @Override
   public Result<List<PetsDto>> getPetInfo(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<GiftRankingDto>> getGiftRanking(Integer type) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
