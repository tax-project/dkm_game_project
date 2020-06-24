package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.SkillBo;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.*;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.personalCenter.domain.Seed;
import com.dkm.personalCenter.domain.vo.TbBlackHouseVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
   public Result<List<TbEquipmentVo>> selectByBoxIdTwo(String boxId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<Map<String, Object>> personalCenterAll(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result plant(SeedPlantVo seedPlantVo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<SeedPlantUnlock>> queryUserIdSeed(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<Map<String, Object>> queryAidMaster() {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<Map<String, Object>> queryAid(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }


}
