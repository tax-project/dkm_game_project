package com.dkm.personalcenter.controller;

import com.dkm.attendant.service.IAttendantService;
import com.dkm.blackHouse.domain.vo.TbBlackHouseVo;
import com.dkm.blackHouse.service.TbBlackHouseService;
import com.dkm.entity.bo.SkillBo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoCenter;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoFour;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoTwo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.produce.service.IProduceService;
import com.dkm.seed.entity.vo.SeedUnlockVo;
import com.dkm.seed.service.ISeedService;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.SkillVo;
import com.dkm.skill.service.ISkillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/5 20:33
 */
@RestController
@RequestMapping("/center")
public class PersonalCenterController {

    @Autowired
    private ISeedService iSeedService;
    @Autowired
    private ISkillService iSkillService;
    @Autowired
    private IProduceService iProduceService;
    @Autowired
    private IAttendantService iAttendantService;

    @Autowired
    private ITbEquipmentKnapsackService tbEquipmentKnapsackService;

    @Autowired
    private TbBlackHouseService tbBlackHouseService;

    @GetMapping("/PersonalCenterAll")
    public Map<String,Object> personalCenterAll(@RequestParam("userId") Long userId){
        Map<String,Object> map=new HashMap<>(7);

        /*//初始化技能
        iSkillService.initSkill(userId);*/
        System.out.println("进入了服务");
        /**
         * 查询已经解锁种子
         */
        List<SeedUnlockVo> seedUnlockVos = iSeedService.queryAreUnlocked(userId);
        System.out.println("查询已经解锁种子");
        /**
         *查询我的技能
         */
        List<SkillVo> skillVos = iSkillService.queryAllSkillByUserIdImgGrade(userId);
        System.out.println("查询我的技能");
        /**
         *查询跟班产出的产物
         */
        Map<String, Object> map1 = iProduceService.queryImgFood(userId);
        System.out.println("查询跟班产出的产物");
        /**
         * 主人信息
         */
        Map<String, Object> stringObjectMap = iAttendantService.queryAidUser();
        System.out.println("主人信息");

        /**
         * 根据当前用户查询装备
         * @return
         */
        List<TbEquipmentKnapsackVoCenter> tbEquipmentKnapsackVos = tbEquipmentKnapsackService.selectUserIdThree(userId);
        System.out.println("根据当前用户查询装备");

        TbBlackHouseVo houseVo = tbBlackHouseService.selectIsBlackTwo(userId);


        System.out.println("黑屋");

        /**
         * 查询个人主页的体力瓶数据
         */
        List<TbEquipmentKnapsackVoFour> listOne=tbEquipmentKnapsackService.selectPersonCenter(userId);
        System.out.println("查询个人主页的体力瓶数据");
        map.put("bottle",listOne);
        map.put("Seed",seedUnlockVos);
        map.put("queryMySkill",skillVos);
        map.put("AttendantGoods",map1);
        map.put("equipment",tbEquipmentKnapsackVos);
        map.put("blackHouse",houseVo);
        map.put("queryAidUser",stringObjectMap);
        System.out.println("返回结果"+map.size());
        return map;
    }
}
