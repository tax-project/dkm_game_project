package com.dkm.event.controller;

import com.dkm.event.entity.vo.UserEventVo;
import com.dkm.event.service.IEventService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:10
 */
@Api(tags = "事件Api")
@RestController
@Slf4j
@RequestMapping("/EventController")
public class EventController {

    @Autowired
    private IEventService iEventService;

    @Autowired
    private LocalUser localUser;


    /**
     * 查询所有事件
     */
    @ApiOperation(value = "查询所有事件",notes = "成功返回数据 反则为空")
    @GetMapping("/queryAllEvent")
    @CrossOrigin
    @CheckToken
    public List<UserEventVo> queryAllEvent(){
       return iEventService.queryAllEvent(localUser.getUser().getId());
    }
}
