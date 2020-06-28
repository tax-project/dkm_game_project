package com.dkm.attendant.controller;

import com.dkm.attendant.entity.vo.AttUserAllInfoVo;
import com.dkm.attendant.entity.vo.AttUserVo;
import com.dkm.attendant.entity.vo.AttendantVo;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
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
    public Map<String, Object>  queryThreeAtt() {
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
    @ApiImplicitParams({
          @ApiImplicitParam(paramType = "query",dataType = "Long",name = "caughtPeopleId",value = "被抓人id"),
          @ApiImplicitParam(paramType = "query",dataType = "Long",name = "aId",value = "跟班id")
    })
    @GetMapping("/dismissal")
    @CrossOrigin
    @CheckToken
    public void dismissal(@RequestParam("caughtPeopleId") Long caughtPeopleId, @RequestParam("aId") Long aId){

       if(caughtPeopleId == null || aId == null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数不能为空");
        }

        iAttendantService.dismissal(caughtPeopleId, aId);
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
          @ApiImplicitParam(paramType = "query",dataType = "Long",name = "attUserId",value = "attUserId"),
    })
    @GetMapping("/gather")
    @CrossOrigin
    @CheckToken
    public Map<String, Object> gather(@RequestParam("atuId") Long atuId, @RequestParam("attUserId") Long attUserId){

        if(atuId==null || attUserId == null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"参数为空");
        }

        return iAttendantService.collect(atuId, attUserId);
    }

    /**
     * 查询自己的一个主人信息
     * @return
     */
    @ApiOperation(value = "查询自己的一个主人信息",notes = "成功返回数据 反则为空")
    @GetMapping("/queryAidUser")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> queryAidUser(){
        return iAttendantService.queryAidUser();
    }

    /**
     * 内部调用
     * @return
     */
    @GetMapping("/queryAidMaster")
    public Map<String,Object> queryAidMaster(){
        return iAttendantService.queryAidUser();
    }

    /**
     * 内部调用
     * @return
     */
    @GetMapping("/queryUserIdMaster")
    @CrossOrigin
    public Map<String,Object> queryUserIdMaster(Long userId){
        return iAttendantService.queryUserIdMaster(userId);
    }


}
