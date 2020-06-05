package com.dkm.personalcenter.controller;

import com.dkm.attendant.service.IAttendantService;
import com.dkm.entity.bo.SkillBo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.produce.entity.vo.AttendantGoods;
import com.dkm.produce.service.IProduceService;
import com.dkm.seed.entity.vo.LandYesVo;
import com.dkm.seed.service.ISeedService;
import com.dkm.skill.service.ISkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/PersonalCenterController")
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

    @GetMapping("/PersonalCenterAll")
    public Map<String,Object> PersonalCenterAll(Long userId){
        Map<String,Object> map=new HashMap<>();
        /**
         * 查询已经解锁种子
         */
        List<LandYesVo> landYesVos = iSeedService.queryAlreadyPlantSd();
        /**
         *查询我的技能
         */
        List<SkillBo> skillBos = iSkillService.queryAllSkillByUserId(userId);
        /**
         *查询跟班产出的产物
         */
        List<AttendantGoods> attendantGoods = iProduceService.queryJoinOutPutGoods(userId);
        /**
         * 查询出用户的主人
         */
        Map<String, Object> map1 = iAttendantService.queryAidUser(userId);

        /**
         * 根据当前用户查询装备
         * @return
         */
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = tbEquipmentKnapsackService.selectUserIdTwo(userId);


        map.put("Seed",landYesVos);
        map.put("queryMySkill",skillBos);
        map.put("AttendantGoods",attendantGoods);
        map.put("queryAidUser",map1);
        map.put("equipment",tbEquipmentKnapsackVos);
        return map;
    }
}
