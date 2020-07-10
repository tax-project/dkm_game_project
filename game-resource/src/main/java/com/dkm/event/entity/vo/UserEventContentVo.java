package com.dkm.event.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/9 20:49
 */
@Data
public class UserEventContentVo {

    /**
     * 他人的用户id
     */
    public Long heUserId;

    /**
     * 事件内容
     */
    public String toRobContent;

    /**
     * 时间
     */
    private LocalDateTime toRobTime;
}
