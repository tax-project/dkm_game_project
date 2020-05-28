package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.LandSeedVo;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.seed.entity.vo.UserInIf;
import com.dkm.seed.service.ISeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 11:47
 */
@Api(tags = "种子api")
@RestController
@RequestMapping("/Seed")
public class SeedController {
    @Autowired
    private ISeedService iSeedService;

    /**
     * 根据用户id得到种子（是否解锁）
     *
     * @return
     */
    @ApiOperation(value = "根据用户id得到种子（是否解锁）", notes = "根据用户id得到种子（是否解锁）")
    @GetMapping("/queryUserIdSeed")
    @CrossOrigin
    @CheckToken
    public List<Seed> queryUserIdSeed() {
        return iSeedService.queryUserIdSeed();
    }

    /**
     * 解锁植物碎片
     */
    @ApiOperation(value = "解锁植物碎片", notes = "解锁植物碎片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "unlockMoney", value = "购买碎片金额"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "grade", value = "种子等级"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedId", value = "种子id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedPresentUnlock", value = "当前解锁进度"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedPresentAggregate", value = "当前解锁总进度")
    })
    @PostMapping("/unlockPlant")
    @CrossOrigin
    @CheckToken //自定义注解 判断用户token是否存在
    public Message unlockPlant(@RequestBody SeedVo seedVo) {
        if (seedVo.getGrade() == null || seedVo.getSeedId() == null || seedVo.getUnlockMoney() == null || seedVo.getSeedPresentUnlock() == null ||
                seedVo.getSeedPresentAggregate() == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedService.unlockPlant(seedVo);
    }


    /**
     * 种植种子
     */
    @ApiOperation(value = "种植种子", notes = "种植种子")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedId", value = "种子id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "grade", value = "种子等级"),
    })
    @PostMapping("/plant")
    @CrossOrigin
    @CheckToken
    public List<LandSeedVo> plant(@RequestBody LandSeed landSeed) {
        if (landSeed.getSeedId() == 0) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
        }
        return iSeedService.queryAlreadyPlantSeed(landSeed);
    }

    /**
     * 收取种子
     */
    @ApiOperation(value = "收取种子", notes = "收取种子")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userGold", value = "用户金币"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoPacketBalance", value = "用户红包可用余额"),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "userInfoNowExperience", value = "种子当前经验"),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "userInfoNextExperience", value = "用户下一等级所需经验值"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoGrade", value = "用户等级"),
    })
    @PostMapping("/gather")
    @CrossOrigin
    @CheckToken
    public Message gather(@RequestBody UserInIf userInIf) {
        if (userInIf.getUserGold() == null  || userInIf.getUserInfoPacketBalance() == null ||
        userInIf.getUserInfoNextExperience()==null || userInIf.getUserInfoNowExperience()==null || userInIf.getUserInfoGrade()==0) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
        }
        int i = iSeedService.updateUser(userInIf);
        Message message=new Message();
        if(i>0){
            message.setMsg("修改成功");
            message.setNum(1);
        }
             return message;
    }

    /**
     * 根据用户id查询已解锁的种子
     */
    @ApiOperation(value = "根据用户id查询已解锁的种子", notes = "根据用户id查询已解锁的种子")
    @GetMapping("/queryAreUnlocked")
    public List<Seed> queryAreUnlocked(@RequestParam(value = "userId") Long userId){
        return iSeedService.queryAreUnlocked(userId);
    }

    /**
     * 根据用户id查询用户所有信息
     */
    @ApiOperation(value = "根据用户id查询用户所有信息", notes = "根据用户id查询用户所有信息")
    @GetMapping("/queryUserAll")
    @CrossOrigin
    @CheckToken
    public Result<UserInfoQueryBo> queryUserAll(){
       return iSeedService.queryUserAll();
    }

}
