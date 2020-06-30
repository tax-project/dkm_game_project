package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.*;
import com.dkm.seed.service.ISeedService;
import com.dkm.seed.vilidata.TimeLimit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.dkm.seed.vilidata.TimeLimit.TackBackLimit;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private LocalUser localUser;


    /**
     * 根据用户id得到种子信息
     *
     * @return
     */
    @ApiOperation(value = "根据用户id得到种子信息", notes = "根据用户id得到种子")
    @GetMapping("/queryUserIdSeed")
    @CrossOrigin
    @CheckToken
    public List<SeedPlantUnlock> queryUserIdSeed() {
        return iSeedService.queryUserIdSeed(localUser.getUser().getId());
    }


    @GetMapping("/queryUserIdSeeds")
    @CrossOrigin
    public List<SeedPlantUnlock> queryUserIdSeed(Long userId) {
        return iSeedService.queryUserIdSeed(userId);
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
    @CheckToken
    public Message unlockPlant(@RequestBody SeedVo seedVo) {
        if (seedVo.getGrade() == null || seedVo.getSeedId() == null || seedVo.getUnlockMoney() == null || seedVo.getSeedPresentUnlock() == null ||
                seedVo.getSeedPresentAggregate() == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedService.unlockPlant(seedVo);
    }


    /**
     * 种植种子
     * 收取种子
     */
    @ApiOperation(value = "种植种子", notes = "种植种子")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedId", value = "种子id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "status", value = "1为种植，2为收取"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedGrade", value = "种子等级"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedGold", value = "种子种植金币"),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "种子土地id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoPacketBalance", value = "用户红包可用余额"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoNowExperience", value = "种子当前经验"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoNextExperience", value = "用户下一等级所需经验值"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoGrade", value = "用户等级"),
    })
    @PostMapping("/plant")
    @CrossOrigin
    @CheckToken
    public void plant(@RequestBody SeedPlantVo seedPlantVo) {
         iSeedService.queryAlreadyPlantSeed(seedPlantVo);
    }


    @PostMapping("/plants")
    @CrossOrigin
    public void plants(@RequestBody SeedPlantVo seedPlantVo) {
        iSeedService.queryAlreadyPlantSeed(seedPlantVo);
    }


    /**
     * 查询已经种植的种子
     */
    @ApiOperation(value = "查询已经种植的种子", notes = "查询已经种植的种子")
    @GetMapping("/queryAlreadyPlantSd")
    @CrossOrigin
    @CheckToken
    public  List<LandYesVo> queryAlreadyPlantSd(){
        return iSeedService.queryAlreadyPlantSd();
    }


    /**
     * 根据用户id查询已解锁的种子
     */
    @ApiOperation(value = "根据用户id查询已解锁的种子", notes = "根据用户id查询已解锁的种子")
    @GetMapping("/queryAreUnlocked/{userId}")
    public List<SeedUnlockVo> queryAreUnlocked(@PathVariable("userId") Long userId){
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

    /**
     * 根据种植id查询种植信息
     */
    @ApiOperation(value = "根据种植id查询种植信息", notes = "根据种植id查询种植信息")
    @GetMapping("/querySeedById")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedId", value = "种子id"),
    })
    @CrossOrigin
    @CheckToken
    public SeedDetailsVo querySeedById(@RequestParam(value = "seedId") Integer seedId){
        if(seedId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
           return iSeedService.querySeedById(seedId);
    }


}
