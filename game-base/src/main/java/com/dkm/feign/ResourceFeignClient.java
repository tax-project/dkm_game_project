package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.SeedPlantUnlock;
import com.dkm.feign.entity.SeedPlantVo;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: game_project
 * @description: 调用resource服务
 * @author: zhd
 * @create: 2020-05-16 15:25
 **/
@FeignClient(value = "resource",fallback = ResourceFeignClientFallback.class)
public interface ResourceFeignClient {
    /**
     * 更新背包
     * @param tekId
     * @param foodNumber
     * @return
     */
    @GetMapping("/dkm/tbEquipmentKnapsack/updateIsva")
    Result updateIsva(@RequestParam("tekId") Long tekId, @RequestParam("foodNumber") Integer foodNumber);

    /**
     * 获取背包食物
     * @param userId
     * @return
     */
    @GetMapping("/dkm/tbEquipmentKnapsack/selectUserIdAndFoodId")
    Result<List<TbEquipmentKnapsackVo>> selectUserIdAndFoodId(@RequestParam("userId") Long userId);

    /**
     * 管家收装备
     * @param boxId
     * @return
     */
    @GetMapping("/dkm/tbBox/selectByBoxIdTwo/{boxId}")
    Result<List<TbEquipmentVo>> selectByBoxIdTwo(@PathVariable("boxId") String boxId);

    /**
     * 查询多个接口 得到个人中心信息
     * @param userId
     * @return
     */
    @GetMapping("/center/PersonalCenterAll")
    Result<Map<String,Object>>  personalCenterAll(@RequestParam("userId") Long userId);

    /**
     * 收取、种植种子
     * @param seedPlantVo
     * @return
     */
    @PostMapping("/Seed/plants")
    Result plant(@RequestBody SeedPlantVo seedPlantVo);

    /**
     * 查询用户种子
     * @param userId
     * @return
     */
    @GetMapping("/Seed/queryUserIdSeeds")
    Result<List<SeedPlantUnlock>>  queryUserIdSeed(Long userId);

}
