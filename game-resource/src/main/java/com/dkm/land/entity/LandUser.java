package com.dkm.land.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:土地用户
 * @USER: 刘梦祺
 * @DATE: 2020/5/9 9:24
 */
@Data
@TableName("tb_land_user")
public class LandUser {
    /**
     * ID
     */
    private Integer id;
    /**
     * 用户id
     */
    private long userid;
    /**
     * 土地主键
     */
    private Integer laid;
}
