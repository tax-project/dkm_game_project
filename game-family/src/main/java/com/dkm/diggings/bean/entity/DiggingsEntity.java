package com.dkm.diggings.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 符凯龙
 */
@Data
@TableName("tb_diggings_battle")
public class DiggingsEntity {
    @TableId
    private long id;
    private long firstFamilyId;
    private long secondFamilyId;
    private long thirdFamilyId;
    private long fourthFamilyId;

}
