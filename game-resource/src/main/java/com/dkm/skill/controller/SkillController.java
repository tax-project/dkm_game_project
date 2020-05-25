package com.dkm.skill.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.vo.MySkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;
import com.dkm.skill.service.ISkillService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:46
 */
@RestController
@Slf4j
@RequestMapping("SkillController")
public class SkillController {
    @Autowired
    private ISkillService iSkillService;


    /**
     * 查询我的技能
     */
    @ApiOperation(value = "查询我的技能", notes = "查询我的技能")
    @PostMapping("/queryMySkill")
    @CrossOrigin
    @CheckToken
    public List<MySkillVo> queryMySkill() {
        return iSkillService.queryMySkill();
    }


    /**
     * 查看技能详情
     */
    @ApiOperation(value = "查看技能详情", notes = "查看技能详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "skId", value = "用户技能主键"),
    })
    @PostMapping("/querySkillsDetails")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> querySkillsDetails(@RequestParam(value = "skId") Long skId){
        if(skId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return iSkillService.querySkillsDetails(skId);
    }






}
