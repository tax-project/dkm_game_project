package com.dkm.skill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dkm.integral.entity.Stars;
import lombok.Data;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:24
 */
@Data
@TableName("tb_user_skill")
public class UserSkill {
    /**
     * 主键
     */
    private long usId;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 技能id
     */
    private long skId;
    /**
     * 技能等级
     */
    private Integer skGrad;
    /**
     * 数量的
     */
    private String skDegreeProficiency;
    /**
     * 当前成功率
     */
    private Integer skCurrentSuccessRate;


}
