package com.dkm.familyMine.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 家族金矿的矿区
 *
 *
 *
 */
@TableName("tb_family_mine_battle_field_item")
@Data
public class BattleFieldItemEntity {


    @TableId
    private Long id;
    /**
     * 与战场ID绑定
     */
    private Long goldItemId;
    /**
     * 是否被占领矿区被挖掘中，如果数值为0则表示无人开采
     */
    private Long userId;

    /**
     * 矿区等级
     */
    private Integer level;

    /**
     * 矿区归属，如果数值为0 则表示为公共矿区
     */
    private Long familyId;

    /**
     * 矿区索引
     */
    private Integer index;

    /*
     * 关于矿区的坐标，公共矿区大小为 14 x 12，而私有矿区的大小则为4x4
     */

    /**
     * 矿区的 X 坐标
     */
    private Integer location_x;

    /**
     * 矿区的 Y 坐标
     */
    private Integer location_y;


}
