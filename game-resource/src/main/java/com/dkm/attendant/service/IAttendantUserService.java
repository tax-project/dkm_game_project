package com.dkm.attendant.service;

import com.dkm.attendant.entity.AttendantUser;

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
}
