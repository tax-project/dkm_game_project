package com.dkm.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.utils.StringUtils;
import com.dkm.wechat.entity.vo.ResultVo;
import com.dkm.wechat.entity.vo.UserLoginVo;
import com.dkm.wechat.entity.vo.UserRegisterVo;
import com.dkm.wechat.service.IWeChatService;
import com.dkm.wechat.util.BodyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: HuangJie
 * @Date: 2020/5/8 16:16
 * @Version: 1.0V
 */
@Api(tags = "微信登录API")
@RestController
@RequestMapping("/v1/we/chat")
public class WeCharController {

    @Autowired
    private IWeChatService weChatService;

    @PostMapping("/login")
    @ApiOperation(value = "微信登录接口",notes = "传入微信登录code码，换取微信信息，和Token",produces = "application/json")
    @ApiImplicitParam(name = "code",value = "微信登录code码",dataType = "String", required = true, paramType = "body")
    public Map<String, Object> weChatLoginUserInfo(HttpServletRequest request){
        JSONObject json = BodyUtils.bodyJson(request);
        String code = json.getString("code");
        if (StringUtils.isBlank(code)){
            throw new ApplicationException(CodeType.SERVICE_ERROR, "参数不能为空");
        }
        return weChatService.weChatLoginUserInfo(code);
    }


    @ApiOperation(value = "注册用户", notes = "注册用户")
    @ApiImplicitParams({
          @ApiImplicitParam(name = "userName", value = "账号", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping("/registerUser")
    @CrossOrigin
    public ResultVo registerUser (@RequestBody UserRegisterVo vo) {

        if (StringUtils.isBlank(vo.getNickName())
              || StringUtils.isBlank(vo.getUserName()) || StringUtils.isBlank(vo.getPassword())) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }

        weChatService.userRegister(vo);

        ResultVo resultVo = new ResultVo();
        resultVo.setResult("ok");

        return resultVo;
    }


    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
          @ApiImplicitParam(name = "userName", value = "账号", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping("/loginUser")
    @CrossOrigin
    public Map<String, Object> loginUser (@RequestBody UserLoginVo vo) {

        if (StringUtils.isBlank(vo.getUserName()) || StringUtils.isBlank(vo.getPassword())) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }

        return weChatService.userLogin(vo);
    }



    @GetMapping("/queryUser/{id}")
    public UserInfoQueryBo queryUser (@PathVariable("id") Long id) {
        if (id == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return weChatService.queryUser(id);
    }


}
