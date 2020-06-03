package com.dkm.family.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.service.FamilyService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
            @ApiImplicitParam(value = "家族介绍",name="familyIntroduce",paramType = "path",dataType = "String",required = true)
    })
    @PostMapping("/createFamily")
    @CheckToken
    @CrossOrigin
    public void createFamily(@RequestBody FamilyEntity family){
        if(family==null||family.getFamilyName()==null||"".equals(family.getFamilyName())){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数为空");
        }
        familyService.creatFamily(localUser.getUser().getId(),family);
    }

    @ApiOperation("家族详情")
    @GetMapping("/familyInfo")
    @CheckToken
    @CrossOrigin
    public Map<String,Object> familyInfo(){
        return familyService.getFamilyInfo(localUser.getUser().getId());
    }

    @ApiOperation("我的家族")
    @GetMapping("/myFamily")
    @CheckToken
    @CrossOrigin
    public FamilyEntity myFamily(){
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
    public List<FamilyEntity> hotFamily(){
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
    @GetMapping("/setAdmin")
    @ApiImplicitParam(value = "用户id",name="userId",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    public void setAdmin(Long userId){
        if(userId==null){
            throw  new ApplicationException(CodeType.PARAMETER_ERROR);
        }
    }
}
