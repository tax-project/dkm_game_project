package com.dkm.skill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:22
 */
@Data
@TableName("tb_skill")
public class Skill {
    /**
     *技能主键
     */
    private long skId;
    /**
     *技能名称
     */
    private String skName;
    /**
     *技能图片
     */
    private String skImg;
}
