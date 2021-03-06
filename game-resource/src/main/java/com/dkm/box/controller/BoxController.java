package com.dkm.box.controller;

import com.alibaba.fastjson.JSON;
import com.dkm.backpack.entity.vo.OpenEquipmentVo;
import com.dkm.box.service.IAutoSellEqService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.box.entity.vo.BoxInfoVo;
import com.dkm.box.service.IUserBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private IAutoSellEqService autoSellEqService;

    @ApiOperation(value = "获取用户宝箱列表")

    @GetMapping("/selectBoxInfo")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CrossOrigin
    @CheckToken
    List<BoxInfoVo> selectBoxInfo(){
        return  userBoxService.getBoxInfo(localUser.getUser().getId());
    }

    @ApiOperation(value = "开启宝箱")
    @GetMapping(value = "/openBoxes")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "boxId", required = true, dataType = "long", value = "开启的宝箱id 传0全部开启")
    })
    @CrossOrigin
    @CheckToken
    public List<OpenEquipmentVo> openBoxes(@RequestParam("boxId") Long  boxId){
        if(boxId==null){throw new ApplicationException(CodeType.SERVICE_ERROR,"参数异常"); }
        return  userBoxService.openBox(localUser.getUser().getId(),boxId);
    }
    @ApiOperation(value = "设置用户自动出售装备信息")
    @GetMapping(value = "/autoSellEq")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "autoSell", required = true, dataType = "List", value = "自动出售装备的等级")
    })
    @CrossOrigin
    @CheckToken
    public void autoSellEq(@RequestParam(value = "autoSell",required = false) List<Long> autoSell){
        autoSellEqService.setAutoSell(localUser.getUser().getId(),autoSell==null?"[]": JSON.toJSONString(autoSell));
    }
    @ApiOperation(value = "获取用户自动出售装备信息")
    @GetMapping(value = "/getAutoSellEq")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    })
    @CrossOrigin
    @CheckToken
    public List<Integer> getAutoSellEq(){
       return autoSellEqService.getAutoSellInfo(localUser.getUser().getId());
    }
}
