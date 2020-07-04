package com.dkm.skill.controller;

import com.dkm.entity.bo.SkillBo;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.meskill.entity.Skill;
import com.dkm.skill.service.ISkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Slf4j
@RestController
@Api(tags = "技能基础API")
@RequestMapping("/v1/skill")
public class SkillController {

   @Autowired
   private ISkillService skillService;


   @ApiOperation(value = "展示所有技能", notes = "展示所有技能")
   @GetMapping("/listAllSkill")
   @CheckToken
   @CrossOrigin
   public List<Skill> listAllSkill () {
      return skillService.listAllSkill();
   }


   @GetMapping("/queryAllSkillByUserId/{userId}")
   public List<SkillBo> queryAllSkillByUserId (@PathVariable("userId") Long userId) {
      return skillService.queryAllSkillByUserId(userId);
   }
}
