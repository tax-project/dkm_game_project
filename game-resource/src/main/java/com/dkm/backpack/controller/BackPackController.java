package com.dkm.backpack.controller;

import com.dkm.backpack.entity.vo.UserBackpackGoodsVo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 背包
 * @author: zhd
 * @create: 2020-07-17 20:38
 **/
@Api(tags = "背包api")
@RestController
@RequestMapping("/backpack")
public class BackPackController {

    @Resource
    private IBackpackService backpackService;

    @Resource
    private LocalUser localUser;

    @ApiOperation(value = "获取宝箱列表")
    @GetMapping("/selectBoxInfo")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CrossOrigin
    @CheckToken
    List<UserBackpackGoodsVo> getUserBackpackGoods(){
        return  backpackService.getUserBackpackGoods(localUser.getUser().getId());
    }
}
