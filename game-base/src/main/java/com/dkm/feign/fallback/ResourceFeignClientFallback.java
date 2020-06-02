package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.SkillBo;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.AttendantGoods;
import com.dkm.feign.entity.SeedUnlockVo;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.personalCenter.domain.Seed;
import com.dkm.personalCenter.domain.vo.TbBlackHouseVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/28
 * @vesion 1.0
 **/
@Component
public class ResourceFeignClientFallback implements ResourceFeignClient {
   @Override
   public Result updateIsva(Long tekId, Integer foodNumber) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<TbEquipmentKnapsackVo>> selectUserIdAndFoodId(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<com.dkm.personalCenter.domain.vo.TbEquipmentKnapsackVo>> userCenterTwo(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<TbBlackHouseVo> selectIsBlackTwo(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<SeedUnlockVo>> queryAreUnlocked(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<TbEquipmentVo>> selectByBoxIdTwo(List<Long> boxId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<AttendantGoods>> queryJoinOutPutGoods(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<SkillBo>> queryAllSkillByUserId(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }
}
