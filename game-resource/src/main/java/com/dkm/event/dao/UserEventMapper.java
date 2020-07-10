package com.dkm.event.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.event.entity.UserEvent;
import com.dkm.event.entity.vo.UserEventContentVo;
import com.dkm.event.entity.vo.UserEventVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:10
 */
@Component
public interface UserEventMapper extends IBaseMapper<UserEvent> {

    /**
     * 查询抢夺过或怎么怎么样子的用户信息
     * @param userId
     * @return
     */
    List<UserEventVo> queryAllEvent(Long userId);

    /**
     * 查询这个用户是否存在
     */
    List<UserEventContentVo> queryUserIsExistence(@Param("userId") Long userId, @Param("heUserId") Long heUserId);
}
