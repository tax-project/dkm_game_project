package com.dkm.goldMine.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_family_mine_item")
public class MineItemEntity {
    @TableId
    private Long goldItemId;
    @TableId
    private Long familyId;
    private Long battle_id;
    private Long userId;
    private LocalDateTime fightEndDate;
    private LocalDateTime fightStartDate;
    private Integer level;
    private Integer index;
    private Integer location_x;
    private Integer location_y;
}
