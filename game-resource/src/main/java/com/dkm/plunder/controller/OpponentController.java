package com.dkm.plunder.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.plunder.service.IOpponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author qf
 * @date 2020/7/3
 * @vesion 1.0
 **/
@Api(tags = "对手Api")
@RestController
@Slf4j
@RequestMapping("/v1/opponent")
public class OpponentController {

   @Autowired
   private IOpponentService opponentService;

   @ApiOperation(value = "查询对手信息", notes = "查询对手信息")
   @GetMapping("/getOpponentInfo")
   @CrossOrigin
   @CheckToken
   public Map<String,Object> getOpponentInfo () {
      return opponentService.getOpponentInfo();
   }
}
