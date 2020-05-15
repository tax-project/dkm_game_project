package com.dkm.shop.domain;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;



import java.io.Serializable;

/**
 * <p>
 * 每日特惠表
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@TableName("tb_day_cheap")
public class TbDayCheap extends Model<TbDayCheap> {

    private static final long serialVersionUID = 1L;

    /**
     * 特惠主键
     */
    @TableId("cheap_id")
	private Long cheapId;
	@TableField("good_id")
	private Long goodId;
    /**
     * 特惠名称
     */
	@TableField("cheap_name")
	private String cheapName;
    /**
     * 特惠内容
     */
	@TableField("cheap_info")
	private String cheapInfo;
    /**
     * 特惠价格
     */
	@TableField("cheap_money")
	private BigDecimal cheapMoney;
	@TableField("cheap_gold")
	private Integer cheapGold;
	@TableField("cheap_dm")
	private Integer cheapDm;
	@TableField("cheap_fm")
	private Integer cheapFm;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCheapFm() {
		return cheapFm;
	}

	public void setCheapFm(Integer cheapFm) {
		this.cheapFm = cheapFm;
	}

	public Long getCheapId() {
		return cheapId;
	}

	public void setCheapId(Long cheapId) {
		this.cheapId = cheapId;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public String getCheapName() {
		return cheapName;
	}

	public void setCheapName(String cheapName) {
		this.cheapName = cheapName;
	}

	public String getCheapInfo() {
		return cheapInfo;
	}

	public void setCheapInfo(String cheapInfo) {
		this.cheapInfo = cheapInfo;
	}

	public BigDecimal getCheapMoney() {
		return cheapMoney;
	}

	public void setCheapMoney(BigDecimal cheapMoney) {
		this.cheapMoney = cheapMoney;
	}

	public Integer getCheapGold() {
		return cheapGold;
	}

	public void setCheapGold(Integer cheapGold) {
		this.cheapGold = cheapGold;
	}

	public Integer getCheapDm() {
		return cheapDm;
	}

	public void setCheapDm(Integer cheapDm) {
		this.cheapDm = cheapDm;
	}

	@Override
	protected Serializable pkVal() {
		return this.cheapId;
	}

	@Override
	public String toString() {
		return "TbDayCheap{" +
			"cheapId=" + cheapId +
			", goodId=" + goodId +
			", cheapName=" + cheapName +
			", cheapInfo=" + cheapInfo +
			", cheapMoney=" + cheapMoney +
			", cheapGold=" + cheapGold +
			", cheapDm=" + cheapDm +
			"}";
	}
}
