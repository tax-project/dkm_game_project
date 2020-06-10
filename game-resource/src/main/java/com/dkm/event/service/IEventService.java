package com.dkm.event.service;

import com.dkm.event.entity.vo.UserEventVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:09
 */
public interface IEventService {


    /**
     * 查询所有事件
     */
    List<UserEventVo> queryAllEvent(Long userId);
}
