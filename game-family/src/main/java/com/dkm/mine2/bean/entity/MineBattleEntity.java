package com.dkm.mine2.bean.entity;

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
    private Long id;
    private Long firstFamilyId;
    private Long secondFamilyId;
    private Long thirdFamilyId;
    private Long fourthFamilyId;

}
