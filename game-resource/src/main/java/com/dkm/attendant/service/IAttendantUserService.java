package com.dkm.attendant.service;

import com.dkm.attendant.entity.AttendantUser;

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

    AttendantUser queryOne(Long caughtPeopleId);


}
