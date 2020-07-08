package com.dkm.family.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.entity.vo.HotFamilyVo;
import com.dkm.family.entity.vo.UserCenterFamilyVo;
import com.dkm.family.service.FamilyService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * @program: game_project
 * @description: 家族
 * @author: zhd
 * @create: 2020-05-20 09:45
 **/
@Api(tags = "家族API")
@RestController
@RequestMapping("/family")
public class FamilyController {
    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyService familyService;

    @ApiOperation("创建家族")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "家族名称",name="familyName",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(value = "家族介绍",name="familyIntroduce",paramType = "path",dataType = "String",required = true),
            @ApiImplicitParam(value = "欢迎词",name="familyWelcomeWords",paramType = "path",dataType = "String",required = true)
    })
    @PostMapping("/createFamily")
    @CheckToken
    @CrossOrigin
    public void createFamily(@RequestBody FamilyEntity family){
        if(family==null||StringUtils.isBlank(family.getFamilyIntroduce())
                ||StringUtils.isBlank(family.getFamilyWelcomeWords())){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        familyService.creatFamily(localUser.getUser().getId(),family);
    }

    @ApiOperation("我的家族详情")
    @GetMapping("/familyInfo")
    @CheckToken
    @CrossOrigin
    public Map<String,Object> familyInfo(){
        return familyService.getFamilyInfo(localUser.getUser().getId());
    }

    @ApiOperation("其他家族信息根据familyId查")
    @ApiImplicitParam(value = "查看家族的id",name="familyId",paramType = "path",dataType = "Long",required = true)
    @GetMapping("/otherFamilyInfo")
    @CheckToken
    @CrossOrigin
    public Map<String,Object> otherFamilyInfo(Long familyId){
        if(familyId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return familyService.otherFamilyInfo(familyId);
    }

    @ApiOperation("获取家族二维码")
    @GetMapping("/getQrcode")
    @CrossOrigin
    public String getQrcode(Long familyId){
        return familyService.getQrcode(familyId);
    }

    @ApiOperation("我的家族")
    @GetMapping("/myFamily")
    @CheckToken
    @CrossOrigin
    public Map<String,Object> myFamily(){
        return familyService.getMyFamily(localUser.getUser().getId());
    }

    @ApiOperation("退出家族")
    @PostMapping("/exitFamily")
    @CheckToken
    @CrossOrigin
    public void exitFamily(){
        familyService.exitFamily(localUser.getUser().getId());
    }

    @ApiOperation("感兴趣家族")
    @GetMapping("/hotFamily")
    @CrossOrigin
    @CheckToken
    public List<HotFamilyVo> hotFamily(){
        return familyService.getHotFamily();
    }

    @ApiOperation("加入家族")
    @GetMapping("/joinFamily")
    @ApiImplicitParam(value = "家族id",name="familyId",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    public void joinFamily(Long familyId){
        if(familyId==null){
            throw  new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        familyService.joinFamily(localUser.getUser().getId(),familyId);
    }

    @ApiOperation("族长 设置/取消 管理员")
    @PostMapping("/setAdmin")
    @ApiImplicitParam(value = "设置用户id",name="setUserId",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    public void setAdmin(@RequestBody Long setUserId){
        if(setUserId==null){
            throw  new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        familyService.setAdmin(localUser.getUser().getId(),setUserId);
    }

    @ApiOperation("转让族长")
    @PostMapping("/transfer")
    @ApiImplicitParam(value = "设置用户id",name="setUserId",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    public void transfer(@RequestBody Long setUserId){
        if(setUserId==null){
            throw  new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        familyService.transfer(localUser.getUser().getId(),setUserId);
    }

    @ApiOperation("族长踢出成员")
    @PostMapping("/kickOutUser")
    @ApiImplicitParam(value = "踢出用户id",name="outUserId",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    public void kickOutUser(@RequestBody Long outUserId){
        if(outUserId==null){
            throw  new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        familyService.kickOutUser(localUser.getUser().getId(),outUserId);
    }


    @ApiOperation("用户家族信息（查当前用户传userId=0）")
    @GetMapping("/getUserCenterFamily")
    @CheckToken
    @CrossOrigin
    public UserCenterFamilyVo getUserCenterFamily(@RequestParam("userId") Long userId){
        if(userId==null||userId==0){
            userId=localUser.getUser().getId();
        }
        return familyService.getUserCenterFamily(userId);
    }
}
