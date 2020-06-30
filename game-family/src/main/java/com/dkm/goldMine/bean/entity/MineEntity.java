package com.dkm.goldMine.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_family_mines")
public class MineEntity {
    @TableId
    private Long id;
    private Long familyId;

}
