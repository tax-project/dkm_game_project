package com.dkm.attendant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.Message;
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
     * 获取用户抓到的跟班信息
     * @return
     */
    @ApiOperation(value = "获取用户抓到的跟班信息", notes = "获取用户抓到的跟班信息")
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

    /**
     * 根据用户id查询食物
     * @return
     */
    @ApiOperation(value = "根据用户id查询食物",notes = "成功返回数据 反则为空")
    @GetMapping("/selectUserIdAndFood")
    @CrossOrigin
    public  List<TbEquipmentKnapsackVo> selectUserIdAndFood(){
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = iAttendantService.selectUserIdAndFood();
        return tbEquipmentKnapsackVos;
    }

    /**
     * 随机查询用户表9条数
     * @return
     */
    @ApiOperation(value = "随机查询用户表9条数",notes = "成功返回数据 反则为空")
    @GetMapping("/queryRandomUser")
    @CrossOrigin
    @CheckToken
    public List<User> queryRandomUser(){
        return iAttendantService.queryRandomUser();
    }

    /**
     *解雇
     */
    @ApiOperation(value = "解雇",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",value = "跟班id"),
    })
    @GetMapping("/dismissal")
    @CrossOrigin
    public Message dismissal(Long id){
        Message message=new Message();
        if(id==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数错误");
        }
        int dismissal = iAttendantService.dismissal(id);
        if(dismissal>0){
            message.setNum(1);
            message.setMsg("解雇成功");
        }else{
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"解雇异常");
        }
        return message;
    }
    /**
     * 宠物战斗
     */


}
