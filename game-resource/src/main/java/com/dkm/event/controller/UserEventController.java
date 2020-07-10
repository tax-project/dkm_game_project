package com.dkm.event.controller;

import com.dkm.event.entity.vo.AddUserEventVo;
import com.dkm.event.entity.vo.UserEventVo;
import com.dkm.event.service.IUserEventService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/UserEventController")
public class UserEventController {

    @Autowired
    private IUserEventService iEventService;



    /**
     * 只查看本月的事件记录
     *  查询所有事件
     */
    @ApiOperation(value = "只查看本月的事件记录 查询所有事件",notes = "成功返回数据 反则为空")
    @GetMapping("/queryAllEvent")
    @CrossOrigin
    @CheckToken
    public List<UserEventVo> queryAllEvent(){
       return iEventService.queryAllEvent();
    }

    /**
     * 添加事件
     */
    @ApiOperation(value = "添加事件",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "heUserId", value = "抢夺方用户id"),
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "toRobContent", value = "事件内容"),
    })
    @PostMapping("/addEvent")
    @CrossOrigin
    @CheckToken
    public int addEvent(@RequestBody AddUserEventVo addUserEventVo){
        return iEventService.addEvent(addUserEventVo);
    }


}
