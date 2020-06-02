package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.entity.bo.SkillBo;
import com.dkm.feign.entity.AttendantGoods;
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
     * 根据当前用户查询装备
     * @return
     */
    @GetMapping("/dkm/tbEquipmentKnapsack/userCenterTwo/{userId}")
    Result<List<com.dkm.personalCenter.domain.vo.TbEquipmentKnapsackVo>> userCenterTwo(@PathVariable("userId") Long userId);

    /**
     * 黑屋的用户信息对象
     * @return
     */
    @GetMapping("/dkm/tbBlackHouse/selectIsBlackThree/{userId}")
    Result<TbBlackHouseVo> selectIsBlackTwo(@PathVariable("userId") Long userId);

    /**
     * 查询当前用户已经解锁的种子
     * @return
     */
    @GetMapping("/Seed/queryAreUnlocked/{userId}")
    Result<List<SeedUnlockVo>> queryAreUnlocked(@PathVariable("userId")Long userId);

    /**
     * 管家收装备
     * @param boxId
     * @return
     */
    @GetMapping("/dkm/tbBox/selectByBoxIdTwo")
    Result<List<TbEquipmentVo>> selectByBoxIdTwo(@RequestBody List<Long> boxId);


    /**
     * 根据用户id查询跟班和跟班产生的物品
     */
    @GetMapping("/v1/produce/queryJoinOutPutGoods/{userId}")
    Result<List<AttendantGoods>> queryJoinOutPutGoods(@PathVariable("userId")Long userId);


    /**
     * qf
     *  根据用户id查询所有技能信息
     * @param userId 用户id
     * @return 返回技能图片和等级
     */
    @GetMapping("/v1/skill/queryAllSkillByUserId/{userId}")
    Result<List<SkillBo>> queryAllSkillByUserId (@PathVariable("userId") Long userId);


}
