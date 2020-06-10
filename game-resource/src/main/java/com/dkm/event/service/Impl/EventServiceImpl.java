package com.dkm.event.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.event.dao.EventMapper;
import com.dkm.event.entity.Event;
import com.dkm.event.entity.vo.UserEventVo;
import com.dkm.event.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:11
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EventServiceImpl extends ServiceImpl<EventMapper,Event> implements IEventService {


    /**
     *  查询所有事件
     * @return
     *
     * 其实就是 自己登录的那个userId
     */
    @Override
    public List<UserEventVo> queryAllEvent(Long userId) {
        return baseMapper.queryAllEvent(userId);
    }
}
