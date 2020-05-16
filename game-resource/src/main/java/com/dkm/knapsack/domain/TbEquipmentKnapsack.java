package com.dkm.knapsack.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@TableName("tb_equipment_knapsack")
public class TbEquipmentKnapsack extends Model<TbEquipmentKnapsack> {

    private static final long serialVersionUID = 1L;

    /**
     * 装备背包表主键
     */
    @TableId("tek_id")
	private Long tekId;
    /**
     * 装备主键
     */
	@TableField("equipment_id")
	private Long equipmentId;
    /**
     * 背包主键
     */
	@TableField("knapsack_id")
	private Long knapsackId;
    /**
     * 我的装备 1为装备上了 2为背包装备
     */
	@TableField("tek_sell")
	private Integer tekSell;
    /**
     * 道具50金币 穿戴品只有5金币
     */
	@TableField("tek_money")
	private Integer tekMoney;
    /**
     * 1为穿戴品 2为道具
     */
	@TableField("tek_daoju")
	private Integer tekDaoju;
    /**
     * 1为没有被卖 0被卖了
     */
	@TableField("tek_isva")
	private Integer tekIsva;
	/**
	 * 食物主键
	 */
	@TableField("food_id")
	private Long foodId;
	/**
	 * 食物数量
	 */
	@TableField("food_number")
	private Integer foodNumber;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getFoodId() {
		return foodId;
	}

	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	public Integer getFoodNumber() {
		return foodNumber;
	}

	public void setFoodNumber(Integer foodNumber) {
		this.foodNumber = foodNumber;
	}

	public Long getTekId() {
		return tekId;
	}

	public void setTekId(Long tekId) {
		this.tekId = tekId;
	}

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

	public Integer getTekSell() {
		return tekSell;
	}

	public void setTekSell(Integer tekSell) {
		this.tekSell = tekSell;
	}

	public Integer getTekMoney() {
		return tekMoney;
	}

	public void setTekMoney(Integer tekMoney) {
		this.tekMoney = tekMoney;
	}

	public Integer getTekDaoju() {
		return tekDaoju;
	}

	public void setTekDaoju(Integer tekDaoju) {
		this.tekDaoju = tekDaoju;
	}

	public Integer getTekIsva() {
		return tekIsva;
	}

	public void setTekIsva(Integer tekIsva) {
		this.tekIsva = tekIsva;
	}

	@Override
	protected Serializable pkVal() {
		return this.tekId;
	}

	@Override
	public String toString() {
		return "TbEquipmentKnapsack{" +
			"tekId=" + tekId +
			", equipmentId=" + equipmentId +
			", knapsackId=" + knapsackId +
			", tekSell=" + tekSell +
			", tekMoney=" + tekMoney +
			", tekDaoju=" + tekDaoju +
			", tekIsva=" + tekIsva +
			"}";
	}
}
