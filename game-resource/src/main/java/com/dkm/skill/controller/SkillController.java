package com.dkm.skill.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.UserSkillVo;
import com.dkm.skill.service.ISkillService;
import com.dkm.skill.service.IUserSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 技能升级
     */
    @ApiOperation(value = "技能升级", notes = "技能升级")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "用户技能主键id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "status", value = "状态 1（消耗金币10000） 2（消耗钻石20）"),
    })
    @GetMapping("/upgradeSkills")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> upgradeSkills(@RequestParam("id") Long id,@RequestParam("status") Integer status){
        return iSkillService.upgradeSkills(id,status);
    }

    @GetMapping("/querySkillByUserId")
    public List<UserSkillVo> querySkillByUserId(@RequestParam("userId") Long userId){
        return iSkillService.querySkillByUserId(userId);
    }
}
