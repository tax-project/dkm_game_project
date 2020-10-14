package com.dkm.admin.controllers;


import com.dkm.admin.entities.vo.UserLoginResultVo;
import com.dkm.admin.entities.vo.UserLoginVo;
import com.dkm.admin.services.IAdminUserLoginService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author fkl
 */
@Api(tags = "用户登录")
@RequestMapping("/admin")
@RestController
public class AdminUserController {
    @Resource
    private IAdminUserLoginService userLoginService;

    @PostMapping("/login")
    @CrossOrigin
    public UserLoginResultVo login(@RequestBody UserLoginVo loginVo) {
        return userLoginService.login(loginVo);

    }
}
