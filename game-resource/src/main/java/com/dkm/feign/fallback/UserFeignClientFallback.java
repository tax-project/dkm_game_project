package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.*;
import com.dkm.entity.vo.*;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.ReputationRankingBO;
import com.dkm.feign.entity.UserNameVo;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import org.springframework.stereotype.Component;

import java.util.List;

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

   @Override
   public Result increaseUserInfo(IncreaseUserInfoBO increaseUserInfoBO) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result cutUserInfo(IncreaseUserInfoBO increaseUserInfoBO) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<UserNameVo>> queryUserName(List<Long> userIds) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<UserPlunderBo>> listUserPlunder() {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result updateStrength(Long userId, Integer grade) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }


   @Override
   public Result updateInfo(UserInfoSkillBo bo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<AttendantWithUserVo>> listAttUser(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<UserHeardUrlBo>> queryAllHeardByUserId(ParamBo bo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<ReputationRankingBO>> reputationRanking() {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<OpponentVo>> listOpponent(ListVo listVo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<UserInfoAttVo>> queryUserInfoAtt(UserAttAllVo listVo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }


}
