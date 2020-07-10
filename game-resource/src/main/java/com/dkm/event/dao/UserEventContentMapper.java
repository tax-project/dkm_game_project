package com.dkm.event.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.event.entity.UserEventContent;
import com.dkm.event.entity.vo.UserEventContentVo;
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
public interface UserEventContentMapper extends BaseMapper<UserEventContent> {

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
