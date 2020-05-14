package com.dkm.problem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.problem.entity.vo.*;
import com.dkm.problem.service.IScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qf
 * @date 2020/5/10
 * @vesion 1.0
 **/
@Api(tags = "积分API")
@Slf4j
@RestController
@RequestMapping("/v1/score")
public class ScoreController {

   @Autowired
   private IScoreService scoreService;

   @ApiOperation(value = "答题完毕返回金额", notes = "答题完毕返回金额")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "moneyId", value = "红包id", required = true, dataType = "Long", paramType = "path"),
         @ApiImplicitParam(name = "score", value = "答题正确的条数", required = true, dataType = "int", paramType = "path")
   })
   @GetMapping("/getScore")
   @CrossOrigin
   @CheckToken
   public ScoreVo getScore (@RequestParam("moneyId") Long moneyId, @RequestParam("score") Integer score) {

      if (score == null || moneyId == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      return scoreService.insertScore(moneyId, score);
   }


   @ApiOperation(value = "分页展示用户答题结束的数据", notes = "分页展示用户答题结束的数据")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "size", value = "每页显示条数", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "moneyId", value = "红包id", required = true, dataType = "Long", paramType = "path")
   })
   @GetMapping("/pageScore")
   @CrossOrigin
   @CheckToken
   public Page<ScoreListVo> pageScore (@RequestParam("moneyId") Long moneyId,
                                      @RequestParam("current") Integer current,
                                      @RequestParam("size") Integer size) {

      if (current == null || moneyId == null || size == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      Page<ScoreListVo> page = new Page<>(current,size);

      return scoreService.pageScore(page,moneyId);
   }


   @ApiOperation(value = "收红包排行榜或者答题达人", notes = "收红包排行榜或者答题达人")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "size", value = "每页显示条数", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "status", value = "(0--一周内数据 1--全部数据)", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "type", value = "(0--收红包排行榜 1--答题达人)", required = true, dataType = "int", paramType = "path")
   })
   @GetMapping("/countListMax")
   @CrossOrigin
   @CheckToken
   public Page<MoneyCountVo> countListMax (@RequestParam("status") Integer status,
                                           @RequestParam("current") Integer current,
                                           @RequestParam("size") Integer size,
                                           @RequestParam("type") Integer type) {

      if (current == null || status == null || size == null || type == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      Page<MoneyCountVo> page = new Page<>(current,size);

      return scoreService.countListMax(page,status,type);
   }
}
