package com.dkm.personalcenter.controller;

import com.dkm.attendant.service.IAttendantService;
import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.backpack.service.IEquipmentService;
import com.dkm.blackHouse.domain.vo.TbBlackHouseVo;
import com.dkm.blackHouse.service.TbBlackHouseService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.SkillBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoCenter;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoFour;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVoTwo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.personalcenter.entity.bo.PsBottleBo;
import com.dkm.personalcenter.entity.vo.UserPsVo;
import com.dkm.produce.service.IProduceService;
import com.dkm.seed.entity.vo.SeedUnlockVo;
import com.dkm.seed.service.ISeedService;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.SkillVo;
import com.dkm.skill.service.ISkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api("用户信息")
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
    private UserFeignClient userFeignClient;

    @Resource
    private IEquipmentService equipmentService;

    @Resource
    private IBackpackService backpackService;
    @Autowired
    private TbBlackHouseService tbBlackHouseService;

    @Resource
    private LocalUser localUser;

    @ApiOperation("获取用户体力信息")
    @GetMapping("/getUserPsInfo")
    @CheckToken
    @CrossOrigin
    public UserPsVo getUserPsInfo(){
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(localUser.getUser().getId());
        if(userInfoQueryBoResult.getCode()!=0){throw  new ApplicationException(CodeType.DATABASE_ERROR); }
        UserPsVo userPsVo = new UserPsVo();
        userPsVo.setPs(userInfoQueryBoResult.getData().getUserInfoStrength());
        userPsVo.setPsAll(userInfoQueryBoResult.getData().getUserInfoAllStrength());
        userPsVo.setPsBottleBo(backpackService.getPsBottle(localUser.getUser().getId()));
        return userPsVo;
    }
    @ApiOperation("使用体力瓶")
    @GetMapping("/usePsBottle")
    @ApiImplicitParam(value = "背包id",name = "backpackId",paramType = "path")
    @CheckToken
    @CrossOrigin
    public void usePsBottle(@RequestParam("backpackId")Long backpackId){
        backpackService.updateNumberByBackpackId(localUser.getUser().getId(),backpackId);
    }

    @ApiOperation("用户信息页面接口")
    @ApiImplicitParam(value = "用户id",paramType = "path")
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
