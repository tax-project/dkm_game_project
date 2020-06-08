package com.dkm.medal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 用户勋章
 * @author: zhd
 * @create: 2020-06-05 15:01
 **/
@Data
@TableName("tb_medal_user")
public class MedalUserEntity {

    @TableId
    private Long muId;
    /**
     * userId
     */
    private Long userId;
    /**
     * 总进度
     */
    private Integer process;
    /**
     * 勋章等级
     */
    private Integer medalLevel;
    /**
     * 勋章表id
     */
    private Long medalId;
    /**
     * 领取次数
     */
    private Integer medalReceiveCount;
}
