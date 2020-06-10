package com.dkm.event.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.event.entity.Event;
import com.dkm.event.entity.vo.UserEventVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:10
 */
@Component
public interface EventMapper extends IBaseMapper<Event> {

    /**
     * 查询所有事件
     */
    List<UserEventVo> queryAllEvent(Long heUserId);
}
