package com.dkm.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 首充豪礼表
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@TableName("tb_charge")
public class TbCharge extends Model<TbCharge> {

    private static final long serialVersionUID = 1L;

    /**
     * 首充豪礼主键
     */
    @TableId("charge_id")
	private Long chargeId;
    /**
     * 规则说明
     */
	@TableField("rule_description")
	private String ruleDescription;
    /**
     * 首充图片
     */
	@TableField("charge_image")
	private String chargeImage;
    /**
     * 充值金额
     */
	@TableField("charge_money")
	private BigDecimal chargeMoney;
	private String exp1;
	private String exp2;
	private String exp3;
	@TableField("create_time")
	private Date createTime;


	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public String getChargeImage() {
		return chargeImage;
	}

	public void setChargeImage(String chargeImage) {
		this.chargeImage = chargeImage;
	}

	public BigDecimal getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(BigDecimal chargeMoney) {
		this.chargeMoney = chargeMoney;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.chargeId;
	}

	@Override
	public String toString() {
		return "TbCharge{" +
			"chargeId=" + chargeId +
			", ruleDescription=" + ruleDescription +
			", chargeImage=" + chargeImage +
			", chargeMoney=" + chargeMoney +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			", createTime=" + createTime +
			"}";
	}
}
