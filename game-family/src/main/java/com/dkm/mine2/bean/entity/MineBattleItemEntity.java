package com.dkm.mine2.bean.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OpenE
 */
@Data
@TableName("tb_mine_battle_item")
public class MineBattleItemEntity {
    /**
     *  ID
     */
    @TableId
    private Long id;
    /**
     * 战场 ID
     */
    private Long battleId;
    /**
     * 设置矿区归属
     */
    private Integer belongItem;
    /**
     * 用户 ID
     */
    private Integer userId;
    /**
     * 矿区等级
     */
    @TableField("item_level")
    private Integer level;
}
