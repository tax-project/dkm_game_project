package com.dkm.shop.domain;


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
 * @since 2020-06-21
 */
@TableName("tb_accumulated_one")
public class TbAccumulatedOne extends Model<TbAccumulatedOne> {

    private static final long serialVersionUID = 1L;

    /**
     * 累计积累领取记录主键
     */
    @TableId("talo_id")
	private Long taloId;
    /**
     * 用户外键
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 累计充值外键
     */
	@TableField("tal_id")
	private Long talId;
    /**
     * 拓展字段1
     */
	private String exp1;
    /**
     * 拓展字段2
     */
	private String exp2;


	public Long getTaloId() {
		return taloId;
	}

	public void setTaloId(Long taloId) {
		this.taloId = taloId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTalId() {
		return talId;
	}

	public void setTalId(Long talId) {
		this.talId = talId;
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

	@Override
	protected Serializable pkVal() {
		return this.taloId;
	}

	@Override
	public String toString() {
		return "TbAccumulatedOne{" +
			"taloId=" + taloId +
			", userId=" + userId +
			", talId=" + talId +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			"}";
	}
}
