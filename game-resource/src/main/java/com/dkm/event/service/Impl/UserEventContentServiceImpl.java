package com.dkm.event.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.event.dao.UserEventContentMapper;
import com.dkm.event.dao.UserEventMapper;
import com.dkm.event.entity.UserEventContent;
import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.service.IUserEventContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/9 20:20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserEventContentServiceImpl extends ServiceImpl<UserEventContentMapper, UserEventContent> implements IUserEventContentService {

    @Override
    public List<UserEventContent> queryAllEventContentById(Long heUserId) {
        return baseMapper.queryAllEventContentById(heUserId);
    }

    @Override
    public int addEvent(UserEventContentVo userEventContentVo) {
        return baseMapper.addEvent(userEventContentVo);
    }


}
