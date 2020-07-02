package com.dkm.mine2.bean.entity;


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
    @TableId
    private Long id;
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
     * 结束时的时间
     */
    private LocalDateTime miningStopDate;
    /**
     * 矿区等级
     */
    private Integer level;
}
