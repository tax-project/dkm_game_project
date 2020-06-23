package com.dkm.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

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
@Data
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
	/**
	 * 物品外键
	 */
	@TableField("ts_id")
	private Long tsId;
	private String exp2;
	private String exp3;
	@TableField("shop_image")
	private String shopImage;
	@TableField("shop_info")
	private String shopInfo;


	public static long getSerialVersionUID() {
		return serialVersionUID;
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
			", tsId=" + tsId +
			", exp2=" + exp2 +
			", exp3=" + exp3 +
			", shopImage=" + shopImage +
			", shopInfo=" + shopInfo +
			"}";
	}
}
