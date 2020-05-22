package com.dkm.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:35
 */
@Data
@TableName("tb_user_integral")
public class UserIntegral {
    /**
     * 用户id
     */
    private long userId;
    /**
     * 积分id
     */
    private long iId;
    /**
     * 我的积分
     */
    private Integer iMyIntegral;
}
