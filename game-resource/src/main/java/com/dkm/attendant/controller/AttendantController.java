package com.dkm.attendant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.land.entity.Land;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:01
 */
@Api(tags = "跟班Api")
@RestController
@RequestMapping("/Attendant")
public class AttendantController {
    @Autowired
    private IAttendantService iAttendantService;

    /**
     * 获取三个默认的跟班
     * @return
     */
    @ApiOperation(value = "获取三个默认的跟班", notes = "获取三个默认的跟班")
    @GetMapping("/queryThreeAtt")
    @CrossOrigin
    public List<AttenDant> queryThreeAtt() {
        return iAttendantService.queryThreeAtt();
    }

    /**
     * 获取用户声望和金币
     * @return
     */
    @ApiOperation(value = "获取用户声望和金币", notes = "获取用户声望和金币")
    @GetMapping("/queryUserReputationGold")
    @CrossOrigin
    public User queryUserReputationGold(){
        return iAttendantService.queryUserReputationGold();

    }

}
