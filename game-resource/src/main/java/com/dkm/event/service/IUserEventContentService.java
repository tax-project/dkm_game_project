package com.dkm.event.service;

import com.dkm.event.entity.UserEventContent;
import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.entity.vo.UserEventVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:09
 */
public interface IUserEventContentService {


    /**
     * 根据抢夺者id 查看抢夺内容
     * @param heUserId
     * @return
     */
    List<UserEventContent> queryAllEventContentById(Long heUserId);

    /**
     * 添加事件
     * @param userEventContentVo
     * @return
     */
    int addEvent(UserEventContentVo userEventContentVo);
}
