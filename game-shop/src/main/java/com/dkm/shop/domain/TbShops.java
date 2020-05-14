package com.dkm.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;

import java.io.Serializable;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@TableName("tb_shops")
public class TbShops extends Model<TbShops> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品主键
     */
    @TableId("shop_id")
	private Long shopId;
    /**
     * 商品名称
     */
	@TableField("shop_name")
	private String shopName;
    /**
     * 商品价格
     */
	@TableField("shop_money")
	private BigDecimal shopMoney;
	private String exp1;
	private String exp2;
	private String exp3;
	@TableField("shop_image")
	private String shopImage;
	@TableField("shop_info")
	private String shopInfo;


	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public BigDecimal getShopMoney() {
		return shopMoney;
	}

	public void setShopMoney(BigDecimal shopMoney) {
		this.shopMoney = shopMoney;
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

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(String shopInfo) {
		this.shopInfo = shopInfo;
	}

	@Override
	protected Serializable pkVal() {
		return this.shopId;
	}

	@Override
	public String toString() {
		return "TbShops{" +
			"shopId=" + shopId +
			", shopName=" + shopName +
			", shopMoney=" + shopMoney +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			", shopImage=" + shopImage +
			", shopInfo=" + shopInfo +
			"}";
	}
}
