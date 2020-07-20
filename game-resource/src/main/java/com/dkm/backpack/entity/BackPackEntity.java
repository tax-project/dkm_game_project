package com.dkm.backpack.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 21:19
 **/
@Data
@TableName("tb_user_backpack")
public class BackPackEntity {
    /**
     * 背包id
     */
    @TableId
    private Long backpackId;
    /**
     * 物品id
     */
    private Long goodId;
    /**
     * userId
     */
    private Long userId;
    /**
     * 物品数量
     */
    private Integer number;
}
