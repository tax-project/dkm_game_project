package com.dkm.medal.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.medal.entity.Medal;
import com.dkm.medal.service.IMedalService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 15:36
 */
@Api(tags = "勋章Api")
@RestController
@RequestMapping("/Medal")
public class MedalController {
    @Resource
    private IMedalService iMedalService;

    /**
     * 根据用id查询勋章
     * @return
     */
    @ApiOperation(value = "根据用id查询勋章", notes = "如果查询成功返回list集合，失败返回为null")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户id")
    })
    @GetMapping("/queryMedalById")
    @CrossOrigin
    public List<Medal> queryMedalById(){
       return  iMedalService.queryMedalById();
    }



    /**
     * 根据用id查询勋章详情
     * @param medalId
     * @return
     */
    @ApiOperation(value = "根据用id查询勋章详情", notes = "如果查询成功返回list集合，失败返回为null")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "medalId", value = "勋章id")
    })
    @GetMapping("/queryMedalByIdDetails")
    @CrossOrigin
    public List<Medal> queryMedalByIdDetails(@RequestParam(value = "medalId") Integer medalId){
        if(medalId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "勋章id不能为空");
        }
        return  iMedalService.queryMedalByIdDetails(medalId);
    }
}
