package com.dkm.goldMine.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.goldMine.bean.vo.*;
import com.dkm.goldMine.service.IMineService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 注意，此处需要鉴权！！！，未作鉴权判断
 */
@Api(tags = "家族矿区 API")
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

    @ApiOperation("查询家族矿区信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "家族ID",name="familyId",paramType = "path",dataType = "Long",required = true),
    })
    @GetMapping("/{familyId}/getInfo")
    @CrossOrigin
    public GoldMineVo getFamilyGoldMine(@PathVariable String familyId) {
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
    @ApiOperation("获取金矿信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "家族ID",name="familyId",paramType = "path",dataType = "Long",required = true),
            @ApiImplicitParam(value = "矿区ID",name="goldItemId",paramType = "path",dataType = "Long",required = true),
    })
    @GetMapping("/{familyId}/{goldItemId}/getStatus")
    @CrossOrigin

    public MineItemFightVo getFamilyGoldMine(@PathVariable Long familyId, @PathVariable Long goldItemId) {
       return mineService.getGoldMineItemInfo(familyId,goldItemId);
    }

    /**
     * 注意，此处需要鉴权！！！，未作鉴权判断
     */
    @ApiOperation("矿区对战")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "家族ID",name="familyId",paramType = "path",dataType = "Long",required = true),
            @ApiImplicitParam(value = "矿区ID",name="goldItemId",paramType = "path",dataType = "Long",required = true),
            @ApiImplicitParam(value = "用户ID",name="userId",paramType = "path",dataType = "Long",required = true),
    })
    @GetMapping("/{familyId}/{goldItemId}/fight/{userId}/now")
    @CrossOrigin
    public FightVo fight(@PathVariable Long familyId, @PathVariable Long goldItemId,@PathVariable Long userId) {
        return mineService.fight(familyId,goldItemId,userId);
    }

    /**
     * 注意，此处需要鉴权！！！，未作鉴权判断
     */
    @ApiOperation("取消矿区对战")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "家族ID",name="familyId",paramType = "path",dataType = "Long",required = true),
            @ApiImplicitParam(value = "矿区ID",name="goldItemId",paramType = "path",dataType = "Long",required = true),
            @ApiImplicitParam(value = "用户ID",name="userId",paramType = "path",dataType = "Long",required = true),
    })
    @GetMapping("/{familyId}/{goldItemId}/fight/{userId}/kill")
    @CrossOrigin
    public FightKillVo fightKill(@PathVariable Long familyId, @PathVariable Long goldItemId,@PathVariable Long userId) {
        return mineService.fightKill(familyId,goldItemId,userId);
    }

//    /**
//     * 攻击矿区 ，实时返回结果，
//     *
//     * @param familyId
//     * @param goldItemId
//     * @return
//     */
//    @GetMapping("/{familyId}/{goldItemId}/fight")
//    public FightVo fight(@PathVariable Long familyId, @PathVariable Long goldItemId) {
//        return mineService.fightMineItem(familyId,goldItemId);
//    }
//    /**
//     * 攻击矿区 ，实时返回结果，
//     *
//     * @param familyId
//     * @param goldItemId
//     * @return
//     */
//    @GetMapping("/{familyId}/{goldItemId}/tryFight")
//    public TryFightVo tryFight(@PathVariable Long familyId, @PathVariable Long goldItemId) {
//        return mineService.tryFightMineItem(familyId,goldItemId);
//    }

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
