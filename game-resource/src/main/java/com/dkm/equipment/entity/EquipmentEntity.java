package com.dkm.equipment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: game_project
 * @description: 装备属性
 * @author: zhd
 * @create: 2020-07-17 20:51
 **/
@Data
@TableName("tb_user_equipment")
public class EquipmentEntity {
    /**
     * 背包id
     */
    @TableId
    private Long backpackId;
    /**
     * 血量
     */
    private Integer blood;
    /**
     * 血量加成
     */
    private BigDecimal bloodAdd;
    /**
     * 声望
     */
    private Integer renown;
    /**
     * 等级
     */
    private Integer grade;
    /**
     * 才华
     */
    private Integer talent;
    /**
     * 才华加成
     */
    private BigDecimal talentAdd;
    /**
     * 经验掉落
     */
    private Integer exp;
    /**
     * 暴击率
     */
    private BigDecimal crit;
    /**
     * 装备掉落
     */
    private BigDecimal eqDrop;
    /**
     * 需要等级
     */
    private Integer needGrade;
    /**
     * 装备类型
     */
    private Integer eqType;
    /**
     * 是否装备 1为装备
     */
    private Integer isEquip;
}
