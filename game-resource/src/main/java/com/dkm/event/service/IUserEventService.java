package com.dkm.event.service;

import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.entity.vo.UserEventVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:09
 */
public interface IUserEventService {


    /**
     * 查询抢夺过或怎么怎么样子的用户信息
     * @return
     */
    List<UserEventVo> queryAllEvent();

    /**
     * 添加事件
     * @param userEventContentVo
     * @return
     */
    int addEvent(UserEventContentVo userEventContentVo);
}
