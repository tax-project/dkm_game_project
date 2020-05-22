package com.dkm.apparel.controller;

import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.service.ApparelService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-15 09:16there are always
 **/
@Api(tags = "服饰API")
@RestController
@RequestMapping("/apparel")
public class ApparelController {

    @Resource
    private ApparelService apparelService;

    @Resource
    private LocalUser localUser;

    @ApiOperation("获取所有服饰信息")
    @ApiImplicitParam(name = "type",value = "1 上衣 2裤子 空全部",paramType = "path",dataType = "Integer",required = false)
    @CrossOrigin
    @GetMapping("/getAll")
    public List<ApparelEntity> getAll(Integer type) {
        return apparelService.getAllApparels(type);
    }

    @ApiOperation("获取拥有服饰")
    @CrossOrigin
    @GetMapping("/getHave")
    @CheckToken
    public List<ApparelEntity> getHave() {
        return apparelService.getUserApparel(localUser.getUser().getId());
    }


    @ApiOperation("制作服饰")
    @CrossOrigin
    @PostMapping("/doApparel")
    @CheckToken
    public void doApparel(Long apparelId){
        apparelService.doApparel(apparelId,localUser.getUser().getId());
    }
}
