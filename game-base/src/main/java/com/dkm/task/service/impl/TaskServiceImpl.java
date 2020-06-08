package com.dkm.task.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.task.dao.TaskDao;
import com.dkm.task.entity.TaskEntity;
import com.dkm.task.entity.vo.TaskUserDetailVo;
import com.dkm.task.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 任务
 * @author: zhd
 * @create: 2020-06-08 15:00
 **/
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskDao taskDao;

    @Override
    public List<TaskUserDetailVo> selectUserTask(Long userId, Integer type) {
        return taskDao.selectUserTask(type,userId);
    }

    @Override
    public void getTaskReward(Long userId, Long taskId) {
        TaskEntity taskEntity = taskDao.selectById(taskId);
        if(taskEntity==null){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND,"不存在该任务");
        }

    }
}
