package com.dkm.familyMine.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;


@Data
@TableName("tb_family_mine_battle_field")
public class MineBattleFieldEntity {
    @TableId
    private Long battleFieldId;
    /**
     * 对应的家族ID，如果ID 为0 则表示无其他用户，默认随机查找用户并填入
     */
    private Long familyIdByFirst;
    private Long familyIdBySecond;
    private Long familyIdByThird;
    private Long familyIdByForth;

}
