package com.dkm.task.entity.vo;

import com.dkm.task.entity.TaskEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description: 用户任务表
 * @author: zhd
 * @create: 2020-06-08 16:08
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskUserDetailVo extends TaskEntity {

    /**
     * 用户当前进度
     */
    private Integer taskProcess;

}
