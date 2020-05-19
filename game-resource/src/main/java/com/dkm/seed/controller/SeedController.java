package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
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
    public Message unlockPlant(SeedVo seedVo) {
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
    public List<LandSeedVo> plant(LandSeed landSeed) {
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
    })
    @PostMapping("/gather")
    @CrossOrigin
    @CheckToken
    public Message gather(UserInIf userInIf) {
        if (userInIf.getUserGold() == null  || userInIf.getUserInfoPacketBalance() == null ) {
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户Id"),
    })
    @PostMapping("/queryAreUnlocked")
    @CrossOrigin
    @CheckToken
    List<Seed> queryAreUnlocked(Long userId){
        if(userId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数为空");
        }
        return iSeedService.queryAreUnlocked(userId);
    }

}
