package com.dkm.familyMine.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 家族金矿的矿区
 *
 *
 *
 */
@TableName("tb_family_mine_battle_field_user")
@Data
public class BattleFieldUserEntity {
    @TableId
    private Long userId;
    /**
     * 与战场ID绑定
     */
    private Long battleFieldId;
}
