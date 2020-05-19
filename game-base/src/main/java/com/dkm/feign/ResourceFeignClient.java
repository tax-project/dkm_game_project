package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.personalCenter.domain.vo.TbBlackHouseVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: game_project
 * @description: 调用resource服务
 * @author: zhd
 * @create: 2020-05-16 15:25
 **/
@Component
@FeignClient(value = "service-resource",fallback = ResourceFeignClientFallback.class)
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
     * 根据当前用户查询装备
     * @return
     */
    @GetMapping("/dkm/tbEquipmentKnapsack/userCenter")
    Result<List<com.dkm.personalCenter.domain.vo.TbEquipmentKnapsackVo>> userCenter();

    @PostMapping("/dkm/tbBlackHouse/selectIsBlackTwo")
    Result<TbBlackHouseVo> selectIsBlackTwo();
}
