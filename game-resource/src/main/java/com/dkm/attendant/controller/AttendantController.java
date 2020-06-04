package com.dkm.attendant.controller;

import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.AttendantUserVo;
import com.dkm.attendant.entity.vo.AttendantVo;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.land.entity.vo.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private IGoodsService iGoodsService;
    /**
     * 获取用户抓到的跟班信息
     * @return
     */
    @ApiOperation(value = "获取用户抓到的跟班信息", notes = "获取用户抓到的跟班信息")
    @GetMapping("/queryThreeAtt")
    @CrossOrigin
    @CheckToken
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
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "被抓人id"),
    })
    @GetMapping("/dismissal")
    @CrossOrigin
    @CheckToken
    public Message dismissal(@RequestParam(value = "caughtPeopleId") Long caughtPeopleId){
        Message message=new Message();
        if(caughtPeopleId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数错误");
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
    public Map<String,Object> petBattle(@RequestParam(value = "caughtPeopleId") Long caughtPeopleId){
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
    @GetMapping("/combatResults")
    @CrossOrigin
    @CheckToken
   public Map<String,Object>  combatResults(AttendantVo vo){
        if(vo.getOtherForce()==null || vo.getMyCapabilities()==null || vo.getOtherHealth()==null || vo.getStatus()==null || vo.getMyHealth()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return iAttendantService.combatResults(vo);
    }

    /**
     * 抓跟班
     */
    @ApiOperation(value = "抓跟班",notes = "成功返回数据 反则为空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "被抓人id"),
    })
    @GetMapping("/graspFollowing")
    @CrossOrigin
    @CheckToken
    public Long graspFollowing(@RequestParam(value = "caughtPeopleId") Long caughtPeopleId){
        Long aLong = iAttendantService.addGraspFollowing(caughtPeopleId);
        if(aLong<=0){
            log.info("抓跟班异常");
            throw new ApplicationException(CodeType.SERVICE_ERROR);
        }
        return aLong;
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
    public Message gather(@RequestParam(value = "atuId") Integer atuId){
        Message message=new Message();
        int gather = iAttendantService.gather(atuId);
        if(gather<0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"收取失败");
        }
        message.setMsg("收取成功");
        message.setNum(1);
        return message;
    }

    /**
     * 查询自己的一个主人信息
     * @param CaughtPeopleId 当前用户id
     * @return
     */
    @GetMapping("/queryAidUser")
    public Map<String,Object> queryAidUser(@RequestParam(value = "CaughtPeopleId") Long CaughtPeopleId){
        if(CaughtPeopleId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }
        return iAttendantService.queryAidUser(CaughtPeopleId);
    }


}
