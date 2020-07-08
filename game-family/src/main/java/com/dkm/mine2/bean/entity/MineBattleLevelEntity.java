package com.dkm.mine2.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author OpenE
 */
@Data
@TableName("tb_mine_battle_level")
public class MineBattleLevelEntity {

    @TableId
    private Integer level;
    private String npcName;
    private Integer goldYield;
    private Integer integralYield;
    private Integer npcLevel;
}
