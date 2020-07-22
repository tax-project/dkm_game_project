package com.dkm.box.controller;

import com.dkm.backpack.entity.vo.OpenEquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
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
}
