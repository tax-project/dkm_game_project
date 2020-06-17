package com.dkm.goldMine.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.goldMine.bean.ao.UserInfoBO;
import com.dkm.goldMine.bean.vo.*;
import com.dkm.goldMine.service.IMineService;
import com.dkm.jwt.islogin.CheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/family/goldMine/")
public class MineController {

    @Resource
    private IMineService mineService;
//
//    @CrossOrigin
//    @CheckToken

    /**
     *  获取家族矿区信息
     * @param familyId
     * @return
     */
    @GetMapping("/{familyId}/getInfo")
    public FamilyGoldMineVo getFamilyGoldMine(@PathVariable String familyId) {
        long familyIdInt;
        try {
            familyIdInt = Long.parseLong(familyId);
        } catch (NumberFormatException e) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "家族ID 无法格式化！[" + familyId + "].");
        }
        return   mineService.getFamilyGoldMine(familyIdInt);
    }

    /**
     * 获取金矿信息 ID
     *
     * @param familyId
     * @param goldItemId
     * @return
     */
    @GetMapping("/{familyId}/{goldItemId}/getStatus")
    public MineItemNpcVo getFamilyGoldMine(@PathVariable Long familyId, @PathVariable Long goldItemId) {
       return mineService.getGoldMineItemInfo(familyId,goldItemId);

    }

    /**
     * 攻击矿区 ，实时返回结果，
     *
     * @param familyId
     * @param goldItemId
     * @return
     */
    @GetMapping("/{familyId}/{goldItemId}/fight")
    public FightVo fight(@PathVariable Long familyId, @PathVariable Long goldItemId) {
        return mineService.fightMineItem(familyId,goldItemId);
    }
    /**
     * 攻击矿区 ，实时返回结果，
     *
     * @param familyId
     * @param goldItemId
     * @return
     */
    @GetMapping("/{familyId}/{goldItemId}/tryFight")
    public TryFightVo tryFight(@PathVariable Long familyId, @PathVariable Long goldItemId) {
        return mineService.tryFightMineItem(familyId,goldItemId);
    }

//    @Autowired
//    UserFeignClient userFeignClient;
//    @PutMapping("/{userId}/addMoney")
//    public void addMoney(@PathVariable("userId") long userId,@RequestParam("money") int money){
//        UserInfoBO userInfoBO = new UserInfoBO();
//        userInfoBO.setUserId(userId);
//        userInfoBO.setUserInfoGold(money);
//        userFeignClient.updateGold(userInfoBO);
//    }

}
