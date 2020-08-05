package com.dkm.event.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.event.dao.UserEventMapper;
import com.dkm.event.entity.UserEvent;
import com.dkm.event.entity.vo.AddUserEventVo;
import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.entity.vo.UserEventVo;
import com.dkm.event.service.IUserEventService;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
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
    private IdGenerator idGenerator;

    /**
     *  只查看本月的事件记录
     *  查询所有事件
     * @return
     *
     */
    @Override
    public List<UserEventVo> queryAllEvent() {
        System.out.println(localUser.getUser().getId());
        List<UserEventVo> userEventVos = baseMapper.queryAllEvent(localUser.getUser().getId());
        for (int i = 0; i < userEventVos.size(); i++) {
            List<UserEventContentVo> userEvents = baseMapper.queryUserIsExistence(localUser.getUser().getId(),userEventVos.get(i).getHeUserId());
            for (int j = 0; j < userEvents.size(); j++) {
                userEvents.get(j).setTime(DateUtils.formatDateTime(userEvents.get(j).getEventTime()));
                userEventVos.get(i).setList(userEvents);
            }
        }
        return userEventVos;
    }

    @Override
    public int addEvent(AddUserEventVo addUserEventVo) {

        UserEvent userEvent=new UserEvent();

        userEvent.setId(idGenerator.getNumberId());
        userEvent.setHeUserId(addUserEventVo.getHeUserId());
        userEvent.setEventTime(LocalDateTime.now());
        userEvent.setUserId(localUser.getUser().getId());
        userEvent.setToRobContent(addUserEventVo.getToRobContent());

        int insert = baseMapper.insert(userEvent);
        if(insert<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"添加事件失败");
        }
        return insert;
    }


}
