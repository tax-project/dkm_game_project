package com.dkm.skill.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.skill.entity.vo.MySkillVo;
import com.dkm.skill.service.ISkillService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @CheckToken //自定义注解 判断用户token是否存在
    public List<MySkillVo> queryMySkill() {
        return iSkillService.queryMySkill();
    }

}
