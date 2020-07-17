package com.dkm.equipment.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 装备属性
 * @author: zhd
 * @create: 2020-07-17 20:51
 **/
@Data
@TableName("tb_user_equipment")
public class EquipmentEntity {
    @TableId
    private Long eqId;
    /**
     * 背包id
     */
    private Integer backpackId;
    /**
     * 血量
     */
    private Integer blood;
    /**
     * 血量加成
     */
    private Integer bloodAdd;
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
    private Integer talentAdd;
    /**
     * 经验掉落
     */
    private Integer exp;
    /**
     * 暴击率
     */
    private Integer crit;
    /**
     * 装备掉落
     */
    private Integer eqDrop;
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
