package com.dkm.attendant.service;

import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.bo.AttUserInfoBo;
import com.dkm.produce.entity.bo.ProduceBO;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 17:52
 */
public interface IAttendantUserService {

    /**
     * 增加跟班用户
     *
     * @param attendantUser
     */
    void insert(AttendantUser attendantUser);

    /**
     *  查询跟班用户信息
     * @param caughtPeopleId
     * @return
     */
    AttendantUser queryOne(Long caughtPeopleId);

    /**
     *  根据用户Id查询所有跟班
     * @param userId 用户Id
     * @return 返回结果
     */
    List<AttendantUser> queryListByUserId (Long userId);

    /**
     * 查询主人的跟班信息
     * @param caughtPeopleId
     * @param attendantId
     * @return
     */
    AttendantUser queryAttendantUser (Long caughtPeopleId, Long attendantId);

    /**
     *  根据id更新用户跟班时间
     * @param endDate
     * @param id
     */
    void updateAttTime (String endDate, Long id);

    /**
     *  根据id查询跟班用户信息
     * @param id
     * @return
     */
    AttendantUser queryAttUser (Long id);

    /**
     *  根据用户Id查询主人信息
     * @param userId 用户Id
     * @return 返回其主人的信息
     */
    AttUserInfoBo queryAttUserInfo (Long userId);

    /**
     *  修改次数
     * @param list id集合
    */
    void updateStatus (List<ProduceBO> list);

    /**
     *  修改产出次数
     * @param list 参数集合
     */
    void updateProduce (List<ProduceBO> list);
}
