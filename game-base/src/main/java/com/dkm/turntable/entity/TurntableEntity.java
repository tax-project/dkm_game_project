package com.dkm.turntable.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 转盘物品
 * @author: zhd
 * @create: 2020-06-11 11:59
 **/
@Data
@TableName("tb_turntable")
public class TurntableEntity {

    @TableId
    private Long turntableId;

    /**
     * 物品id
     */
    private Long turntableGoodId;

    /**
     * 转盘类型
     */
    private Integer  turntableType;
    /**
     * 物品类型 0食物 1金币 2 经验
     */
    private Integer turntableGoodType;
    /**
     * 物品名称
     */
    private String turntableGoodName;
}
