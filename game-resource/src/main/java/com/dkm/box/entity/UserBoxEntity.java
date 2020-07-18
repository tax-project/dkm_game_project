package com.dkm.box.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 装备宝箱
 * @author: zhd
 * @create: 2020-07-17 14:25
 **/
@Data
@TableName("tb_user_box")
public class UserBoxEntity {

    @TableId
    private Long boxId;
    /**
     * userId
     */
    private Long userId;
    /**
     * 开箱时间
     */
    private LocalDateTime openTime;
    /**
     * 宝箱等级
     */
    private Integer boxLevel;
}
