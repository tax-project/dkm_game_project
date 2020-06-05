package com.dkm.skill.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.vo.LandSeedVo;
import com.dkm.skill.entity.vo.ResultSkillVo;
import com.dkm.skill.entity.vo.UserSkillResultVo;
import com.dkm.skill.entity.vo.UserSkillUpGradeVo;
import com.dkm.skill.entity.vo.UserSkillVo;
import com.dkm.skill.service.IUserSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags = "用户技能API")
@Slf4j
@RestController
@RequestMapping("/v1/userSkill")
public class UserSkillController {

   @Autowired
   private IUserSkillService userSkillService;


   @ApiOperation(value = "初始化技能", notes = "初始化技能")
   @ApiImplicitParam(name = "skId", value = "技能id", required = true, dataType = "Long", paramType = "path")
   @PostMapping("/insertUserSkill")
   @CrossOrigin
   @CheckToken
   public void insertUserSkill(@RequestBody UserSkillVo vo) {
      if (vo.getSkId() == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
      }
      userSkillService.insertUserSkill(vo);
   }



   @ApiOperation(value = "升级", notes = "升级")
   @ApiImplicitParam(name = "id", value = "用户技能id", required = true, dataType = "Long", paramType = "path")
   @PostMapping("/upGrade")
   @CrossOrigin
   @CheckToken
   public ResultSkillVo upGrade(@RequestBody UserSkillUpGradeVo vo) {
      if (vo.getId() == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
      }
      return userSkillService.upGrade(vo);
   }


   @ApiOperation(value = "点击消耗增加技能升级成功率", notes = "点击消耗增加技能升级成功率")
   @ApiImplicitParam(name = "id", value = "用户技能id", required = true, dataType = "Long", paramType = "path")
   @GetMapping("/consume")
   @CrossOrigin
   @CheckToken
   public void consume(@RequestParam("id") Long id) {
      if (id == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
      }
      userSkillService.consume(id);
   }


   @ApiOperation(value = "展示技能详情", notes = "展示技能详情")
   @ApiImplicitParam(name = "skillId", value = "技能id", required = true, dataType = "Long", paramType = "path")
   @GetMapping("/getSkillResult")
   @CrossOrigin
   @CheckToken
   public UserSkillResultVo getSkillResult(@RequestParam("skillId") Long skillId) {
      if (skillId == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数为空");
      }
      return userSkillService.getSkillResult(skillId);
   }



}
