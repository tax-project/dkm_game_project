package com.dkm.attendant.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.*;
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
    List<AttUserAllInfoVo> queryThreeAtt();

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold();

    /**
     * 根据用户id查询食物信息
     * @return
     */
     List<TbEquipmentKnapsackVo> selectUserIdAndFood();

   /**
    *  随机查询用户表9条数
    * @return 返回结果
    */
    Map<String, Object> queryRandomUser();
    /**
     * 解雇
     */
    int dismissal(Long caughtPeopleId);
    /**
     * 宠物战斗，查询用户信息
     */
    Map<String,Object> petBattle(Long caughtPeopleId);


   /**
    *  抓跟班
    * @param caughtPeopleId 被抓人Id
    * @param status 0--系统跟班  1--用户跟班
    * @param attendantId  跟班id
    * @return
    */
   AttUserVo addGraspFollowing(Long caughtPeopleId, Integer status, Long attendantId);

   /**
    * 收取
    * @return
    */
   int gather(Integer atuId);

    /**
     * 战斗过程
     * @param vo
     * @return
     */
   Map<String,Object>  combatResults(AttendantVo vo);
    /**
     * 查询自己的一个主人信息
     * @param CaughtPeopleId 当前用户id
     * @return
     */
    Map<String,Object> queryAidUser(Long CaughtPeopleId);

    /**
     * 查询所有跟班
     * @return
     */
    List<AttenDant> listAttenDant ();

}
