package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.LandSeedVo;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.seed.service.ISeedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SeedController{
    @Autowired
    private ISeedService iSeedService;

    /**
     * 根据用户id得到种子（是否解锁）
     * @return
     */
    @ApiOperation(value = "根据用户id得到种子（是否解锁）", notes = "根据用户id得到种子（是否解锁）")
    @GetMapping("/queryUserIdSeed")
    @CrossOrigin
    public List<Seed> queryUserIdSeed(){
        return iSeedService.queryUserIdSeed();
    }

    /**
     * 解锁植物碎片
     */
    @ApiOperation(value = "解锁植物碎片", notes = "解锁植物碎片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "unlockmony", value = "购买碎片金额"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "grade ", value = "种子等级"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedid ", value = "种子id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedPresentUnlock ", value = "当前解锁进度")
    })
    @PostMapping("/unlockPlant")
    @CrossOrigin
    @CheckToken //自定义注解 判断用户token是否存在
    public Message unlockPlant(SeedVo seedVo){
         if (seedVo.getGrade() == 0 && seedVo.getGrade() == null  || seedVo.getSeedid()==0&&seedVo.getSeedid()==null
                 || seedVo.getUnlockmoeny()==0&&seedVo.getUnlockmoeny()==null ||seedVo.getSeedPresentUnlock()==0&&seedVo.getSeedPresentUnlock()==null){
             throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
         }
         return iSeedService.unlockplant(seedVo.getUnlockmoeny(),seedVo.getGrade(),seedVo.getSeedid(),seedVo.getSeedPresentUnlock());
    }


    /**
     * 种植种子
     */
    @ApiOperation(value = "种植种子", notes = "种植种子")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedid ", value = "种子id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "grade ", value = "种子等级"),
    })
    @PostMapping("/plant")
    @CrossOrigin
    public List<LandSeedVo> plant(@RequestBody  LandSeed landSeed){
        if(landSeed.getSeedId() == 0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数为空");
        }
        return iSeedService.queryAlreadyPlantSeed(landSeed);
    }

}
