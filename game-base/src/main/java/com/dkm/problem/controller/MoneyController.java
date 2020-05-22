package com.dkm.problem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.vo.*;
import com.dkm.problem.service.IMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Slf4j
@Api(tags = "红包API")
@RestController
@RequestMapping("/v1/money")
public class MoneyController {

   @Autowired
   private IMoneyService moneyService;

   @Autowired
   private UserFeignClient feignClient;

   @ApiOperation(value = "发红包", notes = "发红包")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "diamonds", value = "钻石数量", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "number", value = "红包领取人数", required = true, dataType = "int", paramType = "path")
   })
   @PostMapping("/handOutRedEnvelopes")
   @CrossOrigin
   @CheckToken
   public HandOutRedEnvelopesVo handOutRedEnvelopes (@RequestBody MoneyVo vo) {

      if (vo.getDiamonds() == null || vo.getNumber() == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      return moneyService.handOutRedEnvelopes(vo);
   }



   @ApiOperation(value = "分页展示所有未开始或者正在进行中的红包", notes = "分页展示所有未开始或者正在进行中的红包")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "size", value = "每页显示的条数", required = true, dataType = "int", paramType = "path")
   })
   @GetMapping("/listAllMoney")
   @CrossOrigin
   @CheckToken
   public Page<Money> listAllMoney (@RequestParam("current") Integer current, @RequestParam("size") Integer size) {

      if (current == null || size == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      MoneyPageVo vo = new MoneyPageVo();
      vo.setCurrent(current);
      vo.setSize(size);

      return moneyService.listAllMoney(vo);
   }




   @ApiOperation(value = "金主排行榜", notes = "金主排行榜")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "size", value = "每页显示条数", required = true, dataType = "int", paramType = "path"),
         @ApiImplicitParam(name = "status", value = "（0--查询一周内数据  1--查询全部数据）", required = true, dataType = "int", paramType = "path")
   })
   @GetMapping("/countHandOutRedEnvelopes")
   @CrossOrigin
   @CheckToken
   public Page<MoneyCountVo> countHandOutRedEnvelopes (@RequestParam("current") Integer current,
                                                       @RequestParam("size") Integer size,
                                                       @RequestParam("status") Integer status) {

      if (current == null || size == null || status == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      Page<MoneyCountVo> page = new Page<>(current,size);

      return moneyService.countHandOutRedEnvelopes(page,status);
   }


   @GetMapping("/get")
   public Object get () {
      Result<UserInfoQueryBo> result = feignClient.queryUser(712030633500774400L);

      System.out.println(result);

      return result.getData();
   }
}
