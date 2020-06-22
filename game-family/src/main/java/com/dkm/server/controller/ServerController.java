package com.dkm.server.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.server.service.IServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: game_project
 * @description: 服务器
 * @author: zhd
 * @create: 2020-06-19 19:42
 **/
@Api(tags = "服务器")
@RestController
@RequestMapping("/server")
public class ServerController {

    @Resource
    private IServerService serverService;

    @ApiOperation("服务器列表")
    @CheckToken
    @CrossOrigin
    @GetMapping("/getServerList")
    public Map<String,Object> getServerList(){
        return serverService.getServerList();
    }
}
