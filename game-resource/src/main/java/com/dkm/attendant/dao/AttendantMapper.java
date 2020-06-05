package com.dkm.attendant.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.vo.AttUserAllInfoVo;
import com.dkm.attendant.entity.vo.AttendantUserVo;
import com.dkm.attendant.entity.vo.AttendantUsersVo;
import com.dkm.attendant.entity.vo.User;
import org.apache.ibatis.annotations.Param;
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
     *  获取用户抓到的跟班信息
     * @param userId 用户id
     * @param status 0--系统跟班  1--用户跟班
     * @return
     */
    List<AttUserAllInfoVo> queryThreeAtt(@Param("userId") Long userId,
                                         @Param("status") Integer status);

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
     * @param atuId
     * @return
     */
    int gather(Long exp1, Long atuId);
    /**
     * 查询自己的一个主人信息
     * @param CaughtPeopleId 当前用户id
     * @return
     */
     AttendantUserVo queryAidUser(Long CaughtPeopleId);

}
