package com.dkm.goldMine.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/***
 *  对照 tb_family_mine_item 数据表
 */
@Data
@TableName("tb_family_mines_item")
public class MineItemEntity {
    /**
     * 金矿 ID
     */
    @TableId
    private Long goldItemId;
    @TableId
    private Long familyId;
    private Long battleId;
    // 内部使用
    private Long userId;
    private Integer local;
    private LocalDateTime fightEndDate;
    private LocalDateTime fightStartDate;
    private Integer level;
    private Integer itemIndex;
}
