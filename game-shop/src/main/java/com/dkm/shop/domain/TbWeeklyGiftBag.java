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
 * 
 * </p>
 *
 * @author zy
 * @since 2020-06-10
 */
@TableName("tb_weekly_gift_bag")
@Data
public class TbWeeklyGiftBag extends Model<TbWeeklyGiftBag> {

    private static final long serialVersionUID = 1L;

    /**
     * 每周礼包主键
     */
    @TableId("twg_id")
	private Long twgId;
    /**
     * 商品标题
     */
	@TableField("twg_title")
	private String twgTitle;
    /**
     * 商品图片
     */
	@TableField("twg_img")
	private String twgImg;
    /**
     * 商品描述
     */
	@TableField("twg_contnet")
	private String twgContnet;
    /**
     * 价格花费钻石
     */
	@TableField("twg_money")
	private BigDecimal twgMoney;
    /**
     * 商品所得卷
     */
	@TableField("twg_quan")
	private Integer twgQuan;
	/**
	 * 物品表外键 卷的外键
	 */
	@TableField("twi_qid")
	private Long twiQid;
    /**
     * 商品所得金币
     */
	@TableField("twg_jinbi")
	private Integer twgJinbi;
	/**
	 * 物品表外键 体力瓶外键
	 */
	@TableField("twg_tili_id")
	private Long twgTiliId;
    /**
     * 商品所得体力瓶
     */
	@TableField("twg_tili")
	private Integer twgTili;
	/**
	 * 物品表外键 技能外键
	 */
	@TableField("twg_jin_id")
	private Long twgJinId;
    /**
     * 商品所得技能道具
     */
	@TableField("twg_jin")
	private Integer twgJin;
	/**
	 * 物品表外键 幸运星外键
	 */
	@TableField("twg_lucky_id")
	private Long twgLuckyId;
    /**
     * 商品幸运星
     */
	@TableField("twg_lucky")
	private Integer twgLucky;
	/**
	 * 物品表外键万能碎片外键
	 */
	@TableField("twg_sp_id")
	private Integer twgSpId;
    /**
     * 商品万能碎片
     */
	@TableField("twg_sp")
	private Integer twgSp;
	/**
	 * 物品表外键传奇碎片外键
	 */
	@TableField("twg_wnsp_id")
	private Integer twgWnspId;
    /**
     * 商品传奇碎片
     */
	@TableField("twg_wnsp")
	private Integer twgWnsp;



	@Override
	protected Serializable pkVal() {
		return this.twgId;
	}

	@Override
	public String toString() {
		return "TbWeeklyGiftBag{" +
			"twgId=" + twgId +
			", twgTitle=" + twgTitle +
			", twgImg=" + twgImg +
			", twgContnet=" + twgContnet +
			", twgMoney=" + twgMoney +
			", twgQuan=" + twgQuan +
			", twgJinbi=" + twgJinbi +
			", twgTili=" + twgTili +
			", twgJin=" + twgJin +
			", twgLucky=" + twgLucky +
			", twgSp=" + twgSp +
			", twgWnsp=" + twgWnsp +
			"}";
	}
}
