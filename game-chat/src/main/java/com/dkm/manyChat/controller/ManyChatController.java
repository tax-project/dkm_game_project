package com.dkm.manyChat.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.friend.entity.vo.ResultVo;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.manyChat.entity.vo.ManyChatVo;
import com.dkm.manyChat.service.IManyChatService;
import com.dkm.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qf
 * @date 2020/5/16
 * @vesion 1.0
 **/
@Api(tags = "群聊API")
@Slf4j
@RestController
@RequestMapping("/v1/manyChat")
public class ManyChatController {

   @Autowired
   private IManyChatService manyChatService;

   @ApiOperation(value = "建立群聊", notes = "建立群聊")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "manyName", value = "群聊名字", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "list", value = "id集合", required = true, dataType = "list", paramType = "path"),
   })
   @CrossOrigin
   @CheckToken
   @PostMapping("/insertManyChat")
   public ResultVo insertManyChat (@RequestBody ManyChatVo vo) {

      if (StringUtils.isBlank(vo.getManyName())) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "群聊名字不能为空");
      }

      manyChatService.insertManyChat(vo);

      ResultVo resultVo = new ResultVo();
      resultVo.setResult("ok");

      return resultVo;
   }
}
