package com.dkm.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2020-06-09
 */
@TableName("tb_privilege_mall")
public class TbPrivilegeMall extends Model<TbPrivilegeMall> {

    private static final long serialVersionUID = 1L;

    /**
     * 特权商城主键
     */
    @TableId("pri_id")
	private Long priId;
    /**
     * 商品名字
     */
	@TableField("pri_name")
	private String priName;
    /**
     * 商品详情
     */
	@TableField("pri_content")
	private String priContent;
    /**
     * 商品购买类型 1为财富卷 2为钻石
     */
	@TableField("pri_type")
	private Integer priType;
    /**
     * 商品价格
     */
	@TableField("pri_money")
	private BigDecimal priMoney;
    /**
     * 需要财富十级购买 1代表十级
     */
	private String exp1;
    /**
     * 1为商品物品 2为别的地方物品
     */
	private String exp2;
    /**
     * 拓展字段3
     */
	private String exp3;
    /**
     * 商品图片
     */
	@TableField("pri_img")
	private String priImg;
    /**
     * 小详情
     */
	@TableField("pr_content_one")
	private String prContentOne;
	/**
	 * 物品外键
	 */
	@TableId("pri_id_one")
	private Long priIdOne;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getPriIdOne() {
		return priIdOne;
	}

	public void setPriIdOne(Long priIdOne) {
		this.priIdOne = priIdOne;
	}

	public Long getPriId() {
		return priId;
	}

	public void setPriId(Long priId) {
		this.priId = priId;
	}

	public String getPriName() {
		return priName;
	}

	public void setPriName(String priName) {
		this.priName = priName;
	}

	public String getPriContent() {
		return priContent;
	}

	public void setPriContent(String priContent) {
		this.priContent = priContent;
	}

	public Integer getPriType() {
		return priType;
	}

	public void setPriType(Integer priType) {
		this.priType = priType;
	}

	public BigDecimal getPriMoney() {
		return priMoney;
	}

	public void setPriMoney(BigDecimal priMoney) {
		this.priMoney = priMoney;
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

	public String getPriImg() {
		return priImg;
	}

	public void setPriImg(String priImg) {
		this.priImg = priImg;
	}

	public String getPrContentOne() {
		return prContentOne;
	}

	public void setPrContentOne(String prContentOne) {
		this.prContentOne = prContentOne;
	}

	@Override
	protected Serializable pkVal() {
		return this.priId;
	}

	@Override
	public String toString() {
		return "TbPrivilegeMall{" +
			"priId=" + priId +
			", priName=" + priName +
			", priContent=" + priContent +
			", priType=" + priType +
			", priMoney=" + priMoney +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			", priImg=" + priImg +
			", prContentOne=" + prContentOne +
			"}";
	}
}
