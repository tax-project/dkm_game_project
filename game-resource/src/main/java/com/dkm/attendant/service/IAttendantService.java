package com.dkm.attendant.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.bo.AttInfoWithPutBo;
import com.dkm.attendant.entity.bo.CollectResultBo;
import com.dkm.attendant.entity.vo.*;
import com.dkm.backpack.entity.vo.FoodInfoVo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.land.entity.vo.Message;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:06
 */
public interface IAttendantService {

    /**
     *获取用户抓到的跟班信息
     * @return
     */
    Map<String, Object>  queryThreeAtt();

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold();

    /**
     * 根据用户id查询食物信息
     * @return
     */
    List<FoodInfoVo> selectUserIdAndFood();

   /**
    *  随机查询用户表9条数
    * @return 返回结果
    */
    Map<String, Object> queryRandomUser();


   /**
    *  解雇
    * @param caughtPeopleId
    * @param aId
    */
    void dismissal(Long caughtPeopleId, Long aId);


    /**
     * 宠物战斗，查询用户信息
     */
    Map<String,Object> petBattle(Long caughtPeopleId,Integer status);


   /**
    *  抓跟班
    * @param caughtPeopleId 被抓人Id
    * @param status 0--系统跟班  1--用户跟班
    * @param attendantId  跟班id
    * @return
    */
   AttUserVo addGraspFollowing(Long caughtPeopleId, Integer status, Long attendantId);

   /**
    *  收取
    * @param atuId
    * @param attUserId
    * @return
    */
   Map<String, Object> collect(Long atuId, Long attUserId);

    /**
     * 战斗过程
     * @param vo
     * @return
     */
   Map<String,Object>  combatResults(AttendantVo vo);

    /**
     * 根据登录人id查询主人
     * @return
     */
    Map<String,Object> queryAidUser();

    /**
     * 根据用户id查看主人
     * @param userId
     * @return
     */
    Map<String,Object> queryUserIdMaster(Long userId);


   /**
    *  修改产出的次数
    * @param attUserId 用户跟班id
    * @param status 0--产出次数+1   1--清0
    */
   void updateMuch (Long attUserId, Integer status);

   /**
    *  随机查询一个跟班
    * @return 返回跟班信息
    */
   AttenDant queryAttendant();

}
