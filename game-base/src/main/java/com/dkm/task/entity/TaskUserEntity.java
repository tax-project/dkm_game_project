package com.dkm.task.entity;

import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-08-13 09:35
 **/
@Data
public class TaskUserEntity {
    /**
     * id
     */
    private Long  tuId;

    /**
     * userId
     */
    private Long userId;
    /**
     * 当前进度
     */
    private Integer tuProcess;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 1为已完成任务
     */
    private Integer complete;
}
