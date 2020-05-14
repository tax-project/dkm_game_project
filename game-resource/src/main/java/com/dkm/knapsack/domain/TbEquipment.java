package com.dkm.knapsack.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 装备表
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@TableName("tb_equipment")
public class TbEquipment extends Model<TbEquipment> {

    private static final long serialVersionUID = 1L;

    /**
     * 装备主键
     */
    @TableId("equipment_id")
	private Long equipmentId;
    /**
     * 背包主键
     */
	@TableField("knapsack_id")
	private Long knapsackId;
    /**
     * 宝箱主键
     */
	@TableField("box_id")
	private Long boxId;
    /**
     * 装备等级
     */
	@TableField("equipment_level")
	private Integer equipmentLevel;
    /**
     * 装备图片
     */
	@TableField("equipment_image")
	private String equipmentImage;
	private String exp1;
	private String exp2;
	private String exp3;


	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Long getKnapsackId() {
		return knapsackId;
	}

	public void setKnapsackId(Long knapsackId) {
		this.knapsackId = knapsackId;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public Integer getEquipmentLevel() {
		return equipmentLevel;
	}

	public void setEquipmentLevel(Integer equipmentLevel) {
		this.equipmentLevel = equipmentLevel;
	}

	public String getEquipmentImage() {
		return equipmentImage;
	}

	public void setEquipmentImage(String equipmentImage) {
		this.equipmentImage = equipmentImage;
	}

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	public String getExp2() {
		return exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

	public String getExp3() {
		return exp3;
	}

	public void setExp3(String exp3) {
		this.exp3 = exp3;
	}

	@Override
	protected Serializable pkVal() {
		return this.equipmentId;
	}

	@Override
	public String toString() {
		return "TbEquipment{" +
			"equipmentId=" + equipmentId +
			", knapsackId=" + knapsackId +
			", boxId=" + boxId +
			", equipmentLevel=" + equipmentLevel +
			", equipmentImage=" + equipmentImage +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			"}";
	}
}
