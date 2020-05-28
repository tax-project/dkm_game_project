package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.AttendantGoods;
import com.dkm.feign.entity.MySkillVo;
import com.dkm.feign.entity.Skill;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.personalCenter.domain.Seed;
import com.dkm.personalCenter.domain.vo.TbBlackHouseVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 黑屋的用户信息对象
     * @return
     */
    @GetMapping("/dkm/tbBlackHouse/selectIsBlackTwo")
    Result<TbBlackHouseVo> selectIsBlackTwo();

    /**
     * 查询当前用户已经解锁的种子
     * @return
     */
    @PostMapping("/Seed/queryAreUnlocked")
    Result<List<Seed>> queryAreUnlocked(@RequestParam("userId")Long userId);

    /**
     * 管家收装备
     * @param boxId
     * @return
     */
    @PostMapping("/dkm/tbBox/selectByBoxIdTwo")
    Result<List<TbEquipmentVo>> selectByBoxIdTwo(@RequestBody List<Long> boxId);

    /**
     * 查询我的技能
     */
    @GetMapping("/v1/skill/listAllSkill")
    Result<List<Skill>>  listAllSkill();

    /**
     * 根据用户id查询跟班和跟班产生的物品
     */
    @GetMapping("/Attendant/queryJoinOutPutGoods")
    Result<List<AttendantGoods>> queryJoinOutPutGoods(@RequestParam("userId")Long userId);


}
