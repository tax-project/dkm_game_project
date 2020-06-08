package com.dkm.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 任务表
 * @author: zhd
 * @create: 2020-06-08 15:03
 **/
@Data
@TableName("tb_task")
public class TaskEntity {

    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 任务id
     */
    private Integer taskProcess;
    /**
     * 任务id
     */
    private String taskDetail;
    /**
     * 任务id
     */
    private Integer taskGold;
    /**
     * 任务类型
     */
    private Integer taskType;
    /**
     * 任务经验
     */
    private Integer taskExperience;



}
