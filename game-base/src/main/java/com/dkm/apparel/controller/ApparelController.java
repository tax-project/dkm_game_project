package com.dkm.apparel.controller;

import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.service.ApparelService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @CrossOrigin
    @GetMapping("/getAll")
    public List<ApparelEntity> getAll() {
        return apparelService.getAllApparels();
    }

    @ApiOperation("获取拥有服饰")
    @CrossOrigin
    @GetMapping("/getHave")
    @CheckToken
    public List<ApparelEntity> getHave() {
        return apparelService.getUserApparel(localUser.getUser().getId());
    }

}
