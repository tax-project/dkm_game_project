package com.dkm.attendant.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
   List<AttenDant> queryThreeAtt();

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold();

    /**
     * 根据用户id查询食物信息
     * @return
     */
    public  List<TbEquipmentKnapsackVo> selectUserIdAndFood();

    /**
     * 随机查询用户表20条数
     */
    List<User> queryRandomUser();
    /**
     * 解雇
     */
    int dismissal(Long id);
    /**
     * 宠物战斗，查询用户信息
     */
    List<User> queryUserPetBattle();
    /**
     * 抓跟班
     */
    int addGraspFollowing();

}
