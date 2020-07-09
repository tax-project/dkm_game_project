package com.dkm.diggings.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 符凯龙
 */
@Data
@TableName("tb_mine_battle")
public class MineBattleEntity {
    @TableId
    private long id;
    private long firstFamilyId;
    private long secondFamilyId;
    private long thirdFamilyId;
    private long fourthFamilyId;

}
