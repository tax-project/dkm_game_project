package com.dkm.plunder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/22 14:24
 */
@Data
@TableName("tb_opponent")
public class Opponent extends Model<Opponent> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 自己用户id
     */
    private Long userId;

    /**
     * 匹配过的对手id
     */
    private Long opponentId;

}
