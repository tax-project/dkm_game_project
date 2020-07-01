package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.seed.entity.Pirate;
import com.dkm.seed.entity.vo.SeedPlantVo;
import com.dkm.seed.service.IPirateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/1 16:22
 */
@Api(tags = "被盗信息api")
@RestController
@RequestMapping("/PirateController")
public class PirateController {


    @Autowired
    private IPirateService iPirateService;

    /**
     * 添加被盗信息
     */
    @ApiOperation(value = "添加被盗信息", notes = "添加被盗信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "pirateToId", value = "盗方用户id"),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "pirateFromId", value = "被盗方用户id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pirateLossGold", value = "被到金币"),
            @ApiImplicitParam(paramType = "query", dataType = "BigDecimal", name = "pirateLossRed", value = "被盗红包"),
    })
    @PostMapping("/plant")
    @CrossOrigin
    @CheckToken
    public int addPirate(@RequestBody Pirate pirate) {
        if(pirate.getPirateToId()==null && pirate.getPirateFromId()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
      return iPirateService.addPirate(pirate);
    }
}
