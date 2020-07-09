package com.dkm.event.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.event.dao.UserEventMapper;
import com.dkm.event.entity.UserEvent;
import com.dkm.event.entity.UserEventContent;
import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.entity.vo.UserEventVo;
import com.dkm.event.service.IUserEventContentService;
import com.dkm.event.service.IUserEventService;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.skill.entity.Stars;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class UserEventServiceImpl extends ServiceImpl<UserEventMapper, UserEvent> implements IUserEventService {


    @Autowired
    private LocalUser localUser;

    @Autowired
    private IUserEventContentService iUserEventContentService;

    @Autowired
    private IdGenerator idGenerator;

    /**
     *  只查看本月的事件记录
     *  查询所有事件
     * @return
     *
     * 其实就是 自己登录的那个userId
     */
    @Override
    public List<UserEventVo> queryAllEvent() {
        List<UserEventVo> userEventVos = baseMapper.queryAllEvent(localUser.getUser().getId());
        for (int i = 0; i < userEventVos.size(); i++) {
            List<UserEventContent> userEventContents = iUserEventContentService.queryAllEventContentById(userEventVos.get(i).getHeUserId());
            for (int j = 0; j <userEventContents.size() ; j++) {
                userEventContents.get(j).setTime(DateUtils.formatDateTime(userEventContents.get(i).getToRobTime()));
                userEventVos.get(i).setList(userEventContents);
            }
        }
        return userEventVos;
    }

    @Override
    public int addEvent(UserEventContentVo userEventContentVo) {

        /**
         * 查看本月这个用户是否抢夺我什么东西
         * 如果没有则添加
         */
        UserEvent userEvent1 = baseMapper.queryUserIsExistence(userEventContentVo.getHeUserId(), localUser.getUser().getId());

        /**
         * 那就说明这个用户之前没有来抢夺什么之类的 可以添加数据
         */
        if(userEvent1==null){
            UserEvent userEvent=new UserEvent();
            userEvent.setId(idGenerator.getNumberId());
            userEvent.setUserId(localUser.getUser().getId());
            userEvent.setEventTime(LocalDateTime.now());
            userEvent.setHeUserId(userEventContentVo.getHeUserId());
            baseMapper.insert(userEvent);
        }

        userEventContentVo.setToRobTime(LocalDateTime.now());
        int i = iUserEventContentService.addEvent(userEventContentVo);
        if(i<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"添加事件内容失败");
        }
        return i;
    }


}
