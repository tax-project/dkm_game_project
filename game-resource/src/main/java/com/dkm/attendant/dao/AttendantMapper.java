package com.dkm.attendant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.bo.CollectResultBo;
import com.dkm.attendant.entity.vo.AttUserAllInfoVo;
import com.dkm.attendant.entity.vo.AttendantUserVo;
import com.dkm.attendant.entity.vo.User;
import com.dkm.entity.vo.UserInfoAttVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
     *  解雇
     * @param caughtPeopleId
     * @param aId
     */
    void dismissal(@Param("caughtPeopleId") Long caughtPeopleId, @Param("aId") Long aId);

    /**
     * 收取产出
     * @param userId 用户id
     * @param attUserId 跟班用户id
     * @return 返回产出集合
     */
    List<CollectResultBo> collect(@Param("userId") Long userId,
                                  @Param("attUserId") Long attUserId);


    /**
     * 查询自己的一个主人信息
     * @param caughtPeopleId 当前用户id
     * @return
     */
     AttendantUserVo queryAidUser(Long caughtPeopleId);

    /**
     *  修改所有物品的产出状态
     * @param list
     * @return
     */
     Integer updateProduceStatus (List<Long> list);

    /**
     *  随机查询一个跟班
     * @return 返回跟班信息
     */
    AttenDant queryAttendant();

    /**
     *  修改产出的次数
     * @param attUserId 用户跟班id
     * @param status 0--产出次数+1   1--清0
     * @return 返回修改结果
     */
    Integer updateMuch(@Param("attUserId") Long attUserId, @Param("status") Integer status);

}
