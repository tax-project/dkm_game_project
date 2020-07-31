package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.bo.SeedDropBO;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;
import com.dkm.seed.service.ISeedFallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 16:18
 */
@Api(tags = "种子掉落金币红包Api")
@RestController
@RequestMapping("/SeedsFallController")
public class SeedsFallController {

    @Autowired
    private ISeedFallService iSeedFallService;


    @ApiOperation(value = "前端调掉落接口", notes = "前端调掉落接口")
    @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoGrade", value = "用户等级")
    @GetMapping("/seedDrop")
    @CrossOrigin
    @CheckToken
    public SeedDropBO seedDrop(@RequestParam(value = "seedGrade") Integer userInfoGrade){
        if (userInfoGrade == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedFallService.seedDrop(userInfoGrade);
    }


    @ApiOperation(value = "上线就调用的接口", notes = "上线就调用的接口")
    @ApiImplicitParams({
          @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedId", value = "种子id"),
          @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userInfoGrade", value = "用户等级"),
    })
    @GetMapping("/redBagDroppedSeparately")
    @CrossOrigin
    @CheckToken
    public List<SeedDropBO> redBagDroppedSeparately(@RequestParam("seedId") Long seedId,
                                                    @RequestParam("userInfoGrade") Integer userInfoGrade){
        if (seedId == null || userInfoGrade == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedFallService.redBagDroppedSeparately(seedId, userInfoGrade);
    }

}
