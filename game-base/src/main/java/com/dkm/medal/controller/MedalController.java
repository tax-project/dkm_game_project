package com.dkm.medal.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.medal.entity.vo.MedalUserInfoVo;
import com.dkm.medal.service.MedalService;
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
 * @description: 勋章接口
 * @author: zhd
 * @create: 2020-06-05 10:17
 **/
@Api(tags = "勋章API")
@RestController
@RequestMapping("/medal")
public class MedalController {

    @Resource
    private LocalUser localUser;
    @Resource
    private MedalService medalService;

    @ApiOperation("获取用户勋章列表")
    @ApiImplicitParam(name = "type", value = "0礼物勋章1幸运勋章", required = true, dataType = "Integer", paramType = "path")
    @GetMapping("/getUserMedal")
    @CheckToken
    @CrossOrigin
    public List<MedalUserInfoVo> getUserMedal(Integer type){
        if(type==null||type>1||type<0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return  medalService.getUserMedal(localUser.getUser().getId(),type);
    }

    @ApiOperation("查看用户勋章详情")
    @ApiImplicitParam(name = "medalId", value = "勋章id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/getOneUserMedal")
    @CheckToken
    @CrossOrigin
    public MedalUserInfoVo getOneUserMedal(Long  medalId){
        if(medalId==null||medalId<0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return  medalService.getOneUserMedal(localUser.getUser().getId(),medalId);
    }

}
