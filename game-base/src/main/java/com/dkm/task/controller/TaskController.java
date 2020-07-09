package com.dkm.task.controller;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.task.entity.vo.TaskUserDetailVo;
import com.dkm.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 任务接口
 * @author: zhd
 * @create: 2020-06-08 10:27
 **/
@Api(tags = "任务API")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private LocalUser localUser;

    @Resource
    private TaskService taskService;

    @ApiOperation("获取任务列表")
    @ApiImplicitParam(name = "type",value = "0 成长 1 日常 2成就",paramType = "path",dataType = "Integer",required = true)
    @CrossOrigin
    @GetMapping("/getUserTask")
    @CheckToken
    public List<TaskUserDetailVo> getUserTask(@RequestParam("type") Integer type){
        return taskService.selectUserTask(localUser.getUser().getId(),type);
    }

    @ApiOperation("领取任务奖励")
    @ApiImplicitParam(name = "taskId",value = "任务id",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @GetMapping("/getTaskReward")
    @CheckToken
    public void getTaskReward(@RequestParam("taskId")Long taskId){
        taskService.getTaskReward(localUser.getUser().getId(),taskId);
    }

}
