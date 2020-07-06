package com.dkm.skill.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.skill.service.ISkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/6 14:23
 */
@Api(tags = "技能api")
@RestController
@RequestMapping("/SkillController")
public class SkillController {


    @Autowired
    private ISkillService iSkillService;

    /**
     * 根据用户id查询所有技能
     * @return
     */
    @ApiOperation(value = "根据用户id查询所有技能", notes = "根据用户id查询所有技能")
    @GetMapping("/queryAllSkillByUserId")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> queryAllSkillByUserId(){
        return iSkillService.queryAllSkillByUserId();
    }
}
