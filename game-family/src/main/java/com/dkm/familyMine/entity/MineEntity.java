package com.dkm.familyMine.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 * 家族金矿
 *
 * @author FKL
 * @description:  familyGoldMine
 */
@Data
@TableName("tb_family_mine")
public class MineEntity {

    /**
     * 一个家族仅有一个金矿，此ID 取家族ID
     */
    @TableId
    private Long familyId;

    /**
     * 战场 ID
     */
    private Long battleFieldId;

}
