package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.*;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
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
   public Result<List<TbEquipmentVo>> selectByBoxIdTwo(String boxId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<Map<String, Object>> personalCenterAll(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result<List<FoodInfoVo>> getFoodsFegin(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result plant(SeedPlantVo seedPlantVo) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

   @Override
   public Result addBackpackGoods(AddGoodsInfo addGoodsInfo) {
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
   public Result<Map<String, Object>> queryUserIdMaster(Long userId) {
      return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
   }

}
