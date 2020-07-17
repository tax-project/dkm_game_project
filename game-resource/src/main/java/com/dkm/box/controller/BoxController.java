package com.dkm.box.controller;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.box.entity.vo.BoxInfoVo;
import com.dkm.box.service.IUserBoxService;
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
 * @description: 保箱
 * @author: zhd
 * @create: 2020-07-17 14:52
 **/
@RestController
@RequestMapping("/box")
@Api(tags = "宝箱api")
public class BoxController {
    @Resource
    private IUserBoxService userBoxService;

    @Resource
    private LocalUser localUser;

    @ApiOperation(value = "获取宝箱列表")
    @GetMapping("/selectBoxInfo")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CrossOrigin
    @CheckToken
    List<BoxInfoVo> selectBoxInfo(){
        return  userBoxService.getBoxInfo(localUser.getUser().getId());
    }
}
