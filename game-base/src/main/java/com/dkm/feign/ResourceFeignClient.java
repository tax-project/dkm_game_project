package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.SkillBo;
import com.dkm.feign.entity.AttendantGoods;
import com.dkm.feign.entity.AttendantUserVo;
import com.dkm.feign.entity.SeedUnlockVo;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.personalCenter.domain.Seed;
import com.dkm.personalCenter.domain.vo.TbBlackHouseVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
     * 黑屋的用户信息对象
     * @return
     */
    @GetMapping("/dkm/tbBlackHouse/selectIsBlackThree/{userId}")
    Result<TbBlackHouseVo> selectIsBlackTwo(@PathVariable("userId") Long userId);



    /**
     * 管家收装备
     * @param boxId
     * @return
     */
    @GetMapping("/dkm/tbBox/selectByBoxIdTwo")
    Result<List<TbEquipmentVo>> selectByBoxIdTwo(@RequestBody List<Long> boxId);









    /**
     * 查询多个接口 得到个人中心信息
     * @param userId
     * @return
     */
    Result<Map<String,Object>>  PersonalCenterAll(@RequestParam("userId") Long userId);

}
