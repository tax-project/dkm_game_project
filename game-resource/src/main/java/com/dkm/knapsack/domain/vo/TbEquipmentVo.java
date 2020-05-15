package com.dkm.knapsack.domain.vo;




import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;


/**
 * <p>
 * 装备表和装备详情表的vo
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
public class TbEquipmentVo{

    private static final long serialVersionUID = 1L;

	/**
	 * 装备主键
	 */
	private Long equipmentId;
	/**
	 * 宝箱主键
	 */
	private Long boxId;
	/**
	 * 装备等级
	 */
	private Integer equipmentLevel;
	/**
	 * 装备图片
	 */
	private String equipmentImage;
	/**
	 * 拓展字段1
	 */
	private String exp1;
	/**
	 * 道具50金币 穿戴品只有5金币
	 */
	private String exp2;
	/**
	 * 1为穿戴品 2为道具
	 */
	private String exp3;

	/**
	 * 装备详情主键
	 */
	private Long edId;

	/**
	 * 详情描述
	 */
	private String edDetailedDescription;
	/**
	 * 装备声望
	 */
	private Integer edEquipmentReputation;
	/**
	 * 红包加速
	 */
	private BigDecimal edRedEnvelopeAcceleration;
	/**
	 * 箱子编号
	 */
	private String boxNo;
	/**
	 * 箱子类型 1为普通箱子 2为VIP箱子
	 */
	private Integer boxType;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
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

	public Long getEdId() {
		return edId;
	}

	public void setEdId(Long edId) {
		this.edId = edId;
	}

	public String getEdDetailedDescription() {
		return edDetailedDescription;
	}

	public void setEdDetailedDescription(String edDetailedDescription) {
		this.edDetailedDescription = edDetailedDescription;
	}

	public Integer getEdEquipmentReputation() {
		return edEquipmentReputation;
	}

	public void setEdEquipmentReputation(Integer edEquipmentReputation) {
		this.edEquipmentReputation = edEquipmentReputation;
	}

	public BigDecimal getEdRedEnvelopeAcceleration() {
		return edRedEnvelopeAcceleration;
	}

	public void setEdRedEnvelopeAcceleration(BigDecimal edRedEnvelopeAcceleration) {
		this.edRedEnvelopeAcceleration = edRedEnvelopeAcceleration;
	}

	@Override
	public String toString() {
		return "TbEquipmentTwo{" +
			"equipmentId=" + equipmentId +
			", boxId=" + boxId +
			", equipmentLevel=" + equipmentLevel +
			", equipmentImage=" + equipmentImage +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			"}";
	}
}
