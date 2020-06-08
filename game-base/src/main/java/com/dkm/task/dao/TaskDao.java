package com.dkm.task.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.task.entity.TaskEntity;
import com.dkm.task.entity.vo.TaskUserDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 任务
 * @author: zhd
 * @create: 2020-06-08 15:27
 **/
@Repository
public interface TaskDao extends BaseMapper<TaskEntity> {


    /**
     * 查询用户任务进度
     * @param type
     * @param userId
     * @return
     */
    @Select("SELECT t.*,tu.tu_process FROM ( " +
            "SELECT*FROM tb_task WHERE task_type=#{type}) t LEFT JOIN ( " +
            "SELECT task_id,tu_process FROM tb_task_user WHERE user_id=#{userId}) tu ON t.task_id=tu.task_id")
    List<TaskUserDetailVo> selectUserTask(@Param("type") Integer type,@Param("userId") Long userId);

}
