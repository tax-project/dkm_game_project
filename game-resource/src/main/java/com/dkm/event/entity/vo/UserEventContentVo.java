package com.dkm.event.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/10 11:50
 */
@Data
public class UserEventContentVo {

    /**
     * 事件内容
     */
    private String toRobContent;

    /**
     * 时间
     */
    private LocalDateTime eventTime;

    private String time;
}
