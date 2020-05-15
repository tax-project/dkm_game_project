package com.dkm.knapsack.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 装备详情
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@TableName("tb_equipment_details")
public class TbEquipmentDetailsTwo extends Model<TbEquipmentDetailsTwo> {

    private static final long serialVersionUID = 1L;

    /**
     * 装备详情主键
     */
    @TableId("ed_id")
	private Long edId;
    /**
     * 装备主键
     */
	@TableField("equipment_id")
	private Long equipmentId;
	@TableField("ed_detailed_description")
	private String edDetailedDescription;
	@TableField("ed_Equipment_reputation")
	private Integer edEquipmentReputation;
	@TableField("ed_Red_envelope_acceleration")
	private BigDecimal edRedEnvelopeAcceleration;


	public Long getEdId() {
		return edId;
	}

	public void setEdId(Long edId) {
		this.edId = edId;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
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
	protected Serializable pkVal() {
		return this.edId;
	}

	@Override
	public String toString() {
		return "TbEquipmentDetailsTwo{" +
			"edId=" + edId +
			", equipmentId=" + equipmentId +
			", edDetailedDescription=" + edDetailedDescription +
			", edEquipmentReputation=" + edEquipmentReputation +
			", edRedEnvelopeAcceleration=" + edRedEnvelopeAcceleration +
			"}";
	}
}
