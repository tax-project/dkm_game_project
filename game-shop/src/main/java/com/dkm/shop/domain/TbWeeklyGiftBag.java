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
 * @since 2020-06-10
 */
@TableName("tb_weekly_gift_bag")
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
     * 商品所得金币
     */
	@TableField("twg_jinbi")
	private Integer twgJinbi;
    /**
     * 商品所得体力瓶
     */
	@TableField("twg_tili")
	private Integer twgTili;
    /**
     * 商品所得技能道具
     */
	@TableField("twg_jin")
	private Integer twgJin;
    /**
     * 商品幸运星
     */
	@TableField("twg_lucky")
	private Integer twgLucky;
    /**
     * 商品万能碎片
     */
	@TableField("twg_sp")
	private Integer twgSp;
    /**
     * 商品传奇碎片
     */
	@TableField("twg_wnsp")
	private Integer twgWnsp;


	public Long getTwgId() {
		return twgId;
	}

	public void setTwgId(Long twgId) {
		this.twgId = twgId;
	}

	public String getTwgTitle() {
		return twgTitle;
	}

	public void setTwgTitle(String twgTitle) {
		this.twgTitle = twgTitle;
	}

	public String getTwgImg() {
		return twgImg;
	}

	public void setTwgImg(String twgImg) {
		this.twgImg = twgImg;
	}

	public String getTwgContnet() {
		return twgContnet;
	}

	public void setTwgContnet(String twgContnet) {
		this.twgContnet = twgContnet;
	}

	public BigDecimal getTwgMoney() {
		return twgMoney;
	}

	public void setTwgMoney(BigDecimal twgMoney) {
		this.twgMoney = twgMoney;
	}

	public Integer getTwgQuan() {
		return twgQuan;
	}

	public void setTwgQuan(Integer twgQuan) {
		this.twgQuan = twgQuan;
	}

	public Integer getTwgJinbi() {
		return twgJinbi;
	}

	public void setTwgJinbi(Integer twgJinbi) {
		this.twgJinbi = twgJinbi;
	}

	public Integer getTwgTili() {
		return twgTili;
	}

	public void setTwgTili(Integer twgTili) {
		this.twgTili = twgTili;
	}

	public Integer getTwgJin() {
		return twgJin;
	}

	public void setTwgJin(Integer twgJin) {
		this.twgJin = twgJin;
	}

	public Integer getTwgLucky() {
		return twgLucky;
	}

	public void setTwgLucky(Integer twgLucky) {
		this.twgLucky = twgLucky;
	}

	public Integer getTwgSp() {
		return twgSp;
	}

	public void setTwgSp(Integer twgSp) {
		this.twgSp = twgSp;
	}

	public Integer getTwgWnsp() {
		return twgWnsp;
	}

	public void setTwgWnsp(Integer twgWnsp) {
		this.twgWnsp = twgWnsp;
	}

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
