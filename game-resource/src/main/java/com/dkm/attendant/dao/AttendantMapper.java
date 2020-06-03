package com.dkm.attendant.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.vo.AttendantUserVo;
import com.dkm.attendant.entity.vo.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:05
 */
@Component
public interface AttendantMapper extends BaseMapper<AttenDant> {

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold(Long userId);
    /**
     * 随机查询用户表20条数
     */
    List<User> queryRandomUser();
    /**
     *获取用户抓到的跟班信息
     */
    List<AttenDant> queryThreeAtt(Long userId);

    /**
     * 解雇
     * @param dismissal 跟班id
     * @return
     */
    int dismissal(Long dismissal);

    /**
     * 抓跟班
     * @param attendantUser
     * @return
     */
    int addGraspFollowing(AttendantUser attendantUser);
    /**
     * 收取
     * @param autId
     * @return
     */
    int gather(Long exp1, Long autId);
    /**
     * 根据跟班id查询用户
     */
     AttendantUserVo queryAidUser(Integer aId);

}
