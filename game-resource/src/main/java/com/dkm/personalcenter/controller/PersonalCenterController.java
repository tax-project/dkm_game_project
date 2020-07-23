package com.dkm.personalcenter.controller;

import com.dkm.attendant.service.IAttendantService;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IEquipmentService;
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

import javax.annotation.Resource;
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

    @Resource
    private IEquipmentService equipmentService;

    @Autowired
    private ITbEquipmentKnapsackService tbEquipmentKnapsackService;

    @Autowired
    private TbBlackHouseService tbBlackHouseService;

    @GetMapping("/PersonalCenterAll")
    public Map<String,Object> personalCenterAll(@RequestParam("userId") Long userId){
        Map<String,Object> map=new HashMap<>(7);

        //初始化技能
       /* iSkillService.initSkill(userId);*/

        /**
         * 查询已经解锁种子
         */
        List<SeedUnlockVo> seedUnlockVos = iSeedService.queryAreUnlocked(userId);

        /**
         *查询我的技能
         */
        List<SkillVo> skillVos = iSkillService.queryAllSkillByUserIdImgGrade(userId);

        /**
         *查询跟班产出的产物
         */
        Map<String, Object> map1 = iProduceService.queryImgFood(userId);

        /**
         * 主人信息
         */
        Map<String, Object> stringObjectMap = iAttendantService.queryUserIdMaster(userId);

        /**
         * 根据当前用户查询装备
         * @return
         */
        List<UserEquipmentVo> userEquipment = equipmentService.getUserEquipment(userId);

        /**
         * 查询黑屋
         */
        TbBlackHouseVo houseVo = tbBlackHouseService.selectIsBlackTwo(userId);

        map.put("Seed",seedUnlockVos);
        map.put("queryMySkill",skillVos);
        map.put("AttendantGoods",map1);
        map.put("equipment",userEquipment);
        map.put("blackHouse",houseVo);
        map.put("queryAidUser",stringObjectMap);
        return map;
    }
}
