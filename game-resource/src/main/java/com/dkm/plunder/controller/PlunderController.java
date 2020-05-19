package com.dkm.plunder.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.plunder.entity.vo.PlunderVo;
import com.dkm.plunder.service.IPlunderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Api(tags = "掠夺API")
@RestController
@RequestMapping("/v1/plunder")
public class PlunderController {

   @Autowired
   private IPlunderService plunderService;

   @ApiOperation(value = "增加掠夺表", notes = "增加掠夺表")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "userId", value = "抢劫的用户id", required = true, dataType = "Long", paramType = "path"),
         @ApiImplicitParam(name = "goodsId", value = "物品id", required = true, dataType = "Long", paramType = "path")
   })
   @PostMapping("/insertPlunder")
   @CrossOrigin
   @CheckToken
   public void insertPlunder (@RequestBody PlunderVo vo) {
      if (vo.getGoodsId() == null || vo.getUserId() == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      plunderService.insertPlunder(vo);
   }


   @ApiOperation(value = "展示掠夺物品的列表", notes = "展示掠夺物品的列表")
   @GetMapping("/queryPlunderList")
   @CrossOrigin
   @CheckToken
   public Map<String,Object> queryPlunderList () {
      return plunderService.queryPlunderList();
   }
}
