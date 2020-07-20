package com.dkm.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.ParamBo;
import com.dkm.entity.bo.UserHeardUrlBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.utils.StringUtils;
import com.dkm.wechat.entity.User;
import com.dkm.wechat.entity.bo.UserDataBO;
import com.dkm.wechat.entity.bo.UserPartBO;
import com.dkm.wechat.entity.vo.ResultVo;
import com.dkm.wechat.entity.vo.UserChatInfoVo;
import com.dkm.wechat.entity.vo.UserLoginVo;
import com.dkm.wechat.entity.vo.UserRegisterVo;
import com.dkm.wechat.service.IWeChatService;
import com.dkm.wechat.util.BodyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @PostMapping("/queryAllHeardByUserId")
    public List<UserHeardUrlBo> queryAllHeardByUserId (@RequestBody ParamBo bo) {
        if (bo.getList() == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return weChatService.queryAllHeardByUserId(bo.getList());
    }


    @ApiOperation(value = "修改用户信息资料", notes = "修改用户信息资料")
    @ApiImplicitParams({
          @ApiImplicitParam(name = "heardUrl", value = "头像", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "userAge", value = "年龄日期 例:2020-06-08", required = true, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "userSex", value = "性别 1-男 2-女", required = false, dataType = "int", paramType = "path"),
          @ApiImplicitParam(name = "userSign", value = "个性签名", required = false, dataType = "String", paramType = "path"),
          @ApiImplicitParam(name = "userExplain", value = "个人说明", required = false, dataType = "String", paramType = "path"),
    })
    @PostMapping("/updateUserData")
    @CrossOrigin
    @CheckToken
    public void updateUserData (@RequestBody UserDataBO bo) {
        if (StringUtils.isBlank(bo.getHeardUrl()) ||
              StringUtils.isBlank(bo.getNickName()) || StringUtils.isBlank(bo.getUserAge())) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        weChatService.updateUserData(bo);
    }


    @ApiOperation(value = "根据用户名或者昵称查询好友", notes = "根据用户名或者昵称查询好友")
    @ApiImplicitParam(name = "userName", value = "账号", required = true, dataType = "String", paramType = "path")
    @GetMapping("/queryUserByName")
    @CrossOrigin
    public List<User> queryUserByName (@RequestParam("userName") String userName) {

        if (StringUtils.isBlank(userName)) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return weChatService.queryUserByName(userName);
    }


    @ApiOperation(value = "查询个人信息", notes = "查询个人信息")
    @GetMapping("/queryUserQrInfo")
    @CrossOrigin
    @CheckToken
    public UserChatInfoVo queryUserQrInfo () {
        return weChatService.queryUserQrInfo();
    }

    /**
     * 根据ID集合查询用户信息
     * @param userIdList 用户ID集合
     * @return 用户信息集合
     */
    @PostMapping("query/list/user/part")
    @CrossOrigin
    public List<UserPartBO> queryUserPartAll(@RequestBody List<Long> userIdList){
        return weChatService.queryUserPartAll(userIdList);
    }
}
