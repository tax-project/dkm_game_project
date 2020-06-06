package com.dkm.attendant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.bo.AttInfoWithPutBo;
import com.dkm.attendant.entity.vo.*;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.good.entity.Goods;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:01
 */
@Api(tags = "跟班Api")
@RestController
@Slf4j
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
    @CheckToken
    public List<AttInfoWithPutBo> queryThreeAtt() {
        return iAttendantService.queryThreeAtt();
    }

    /**
     * 获取用户声望和金币
     * @return
     */
    @ApiOperation(value = "获取用户声望和金币", notes = "获取用户声望和金币")
    @GetMapping("/queryUserReputationGold")
    @CrossOrigin
    @CheckToken
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
    @CheckToken
    public  List<TbEquipmentKnapsackVo> selectUserIdAndFood(){
        return iAttendantService.selectUserIdAndFood();
    }

    /**
     * 随机查询用户表9条数
     * @return
     */
    @ApiOperation(value = "随机查询用户表9条数",notes = "成功返回数据 反则为空")
    @GetMapping("/queryRandomUser")
    @CrossOrigin
    @CheckToken
    public Map<String, Object> queryRandomUser(){
        return iAttendantService.queryRandomUser();
    }

    /**
     *解雇
     */
    @ApiOperation(value = "解雇",notes = "成功返回数据 反则为空")
    @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "被抓人id")
    @GetMapping("/dismissal")
    @CrossOrigin
    @CheckToken
    public Message dismissal(@RequestParam("caughtPeopleId") Long caughtPeopleId){
        Message message=new Message();
        if(caughtPeopleId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }

        int dismissaMeassg = iAttendantService.dismissal(caughtPeopleId);
        if(dismissaMeassg>0){
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
    @ApiOperation(value = "宠物战斗",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "战斗人id"),
    })
    @GetMapping("/petBattle")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> petBattle(@RequestParam("caughtPeopleId") Long caughtPeopleId){
        if(caughtPeopleId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"被抓人Id不能为空");
        }
        return iAttendantService.petBattle(caughtPeopleId);
    }


    /**
     * 战斗过程
     */
    @ApiOperation(value = "战斗过程",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "myHealth",value = "我方血量"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "otherHealth",value = "他方血量"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "status",value = "谁先动手 0为me，1为other"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "myCapabilities",value = "我战斗力"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "otherForce",value = "对方战斗力"),
    })
    @PostMapping("/combatResults")
    @CrossOrigin
    @CheckToken
   public Map<String,Object>  combatResults(@RequestBody AttendantVo vo){
        if(vo.getOtherForce()==null || vo.getMyCapabilities()==null || vo.getOtherHealth()==null || vo.getStatus()==null || vo.getMyHealth()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return iAttendantService.combatResults(vo);
    }

    /**
     * 让他干活
     * 抓跟班
     */
    @ApiOperation(value = "抓跟班",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
          @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "被抓人id"),
          @ApiImplicitParam(paramType = "query",dataType = "int",name = "status",value = "0--系统跟班 1--用户的跟班"),
          @ApiImplicitParam(paramType = "query",dataType = "Long",name = "aId",value = "跟班Id")
    })
    @GetMapping("/graspFollowing")
    @CrossOrigin
    @CheckToken
    public AttUserVo graspFollowing(@RequestParam("caughtPeopleId") Long caughtPeopleId,
                                    @RequestParam("status") Integer status,
                                    @RequestParam("aId") Long aId){
        return iAttendantService.addGraspFollowing(caughtPeopleId, status, aId);
    }

    /**
     * 收取
     */
    @ApiOperation(value = "收取",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "atuId",value = "id"),
    })
    @GetMapping("/gather")
    @CrossOrigin
    @CheckToken
    public void gather(@RequestParam("atuId") Long atuId){
        if(atuId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数为空");
        }
        iAttendantService.gather(atuId);
    }

    /**
     * 查询自己的一个主人信息
     * @param CaughtPeopleId 当前用户id
     * @return
     */
    @GetMapping("/queryAidUser")
    public Map<String,Object> queryAidUser(@RequestParam("CaughtPeopleId") Long CaughtPeopleId){
        if(CaughtPeopleId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return iAttendantService.queryAidUser(CaughtPeopleId);
    }


}
