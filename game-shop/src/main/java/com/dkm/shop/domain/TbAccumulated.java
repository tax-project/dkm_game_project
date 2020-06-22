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
@TableName("tb_accumulated")
public class TbAccumulated extends Model<TbAccumulated> {

    private static final long serialVersionUID = 1L;

    /**
     * 累计充值主键
     */
    @TableId("tal_id")
	private Long talId;
    /**
     * 标题
     */
	@TableField("tal_title")
	private String talTitle;
    /**
     * 赠送的金币
     */
	@TableField("tal_jb")
	private Integer talJb;
    /**
     * 金币图片
     */
	@TableField("tal_jbimg")
	private String talJbimg;
    /**
     * 星星数量
     */
	@TableField("tal_xx")
	private Integer talXx;
    /**
     * 星星图片
     */
	@TableField("tal_xximg")
	private String talXximg;
    /**
     * 徽章数量
     */
	@TableField("tal_hz")
	private Integer talHz;
    /**
     * 徽章图片
     */
	@TableField("tal_hzimg")
	private String talHzimg;
    /**
     * 拓展字段1
     */
	private String exp1;
    /**
     * 拓展字段2
     */
	private String exp2;


	public Long getTalId() {
		return talId;
	}

	public void setTalId(Long talId) {
		this.talId = talId;
	}

	public String getTalTitle() {
		return talTitle;
	}

	public void setTalTitle(String talTitle) {
		this.talTitle = talTitle;
	}

	public Integer getTalJb() {
		return talJb;
	}

	public void setTalJb(Integer talJb) {
		this.talJb = talJb;
	}

	public String getTalJbimg() {
		return talJbimg;
	}

	public void setTalJbimg(String talJbimg) {
		this.talJbimg = talJbimg;
	}

	public Integer getTalXx() {
		return talXx;
	}

	public void setTalXx(Integer talXx) {
		this.talXx = talXx;
	}

	public String getTalXximg() {
		return talXximg;
	}

	public void setTalXximg(String talXximg) {
		this.talXximg = talXximg;
	}

	public Integer getTalHz() {
		return talHz;
	}

	public void setTalHz(Integer talHz) {
		this.talHz = talHz;
	}

	public String getTalHzimg() {
		return talHzimg;
	}

	public void setTalHzimg(String talHzimg) {
		this.talHzimg = talHzimg;
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
		return this.talId;
	}

	@Override
	public String toString() {
		return "TbAccumulated{" +
			"talId=" + talId +
			", talTitle=" + talTitle +
			", talJb=" + talJb +
			", talJbimg=" + talJbimg +
			", talXx=" + talXx +
			", talXximg=" + talXximg +
			", talHz=" + talHz +
			", talHzimg=" + talHzimg +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			"}";
	}
}
