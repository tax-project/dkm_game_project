package com.dkm.box.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 自动出售装备等级信息
 * @author: zhd
 * @create: 2020-08-03 09:36
 **/
@Data
@TableName("tb_auto_sell")
public class AutoSellEntity {
    @TableId
    private Long userId;
    /**
     * 出售等级 为数组字符串
     */
    private String autoSellOrder;
}
