package com.dkm.shop.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@TableName("tb_thedaily_one")
@Data
public class TbThedailyOne extends Model<TbThedailyOne> {

    private static final long serialVersionUID = 1L;

    /**
     * 每日礼包购买记录主键
     */
	@TableField("tho_id")
	private Long thoId;
    /**
     * 用户的外键
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 每日礼包外键
     */
	@TableField("thd_id")
	private Long thdId;
    /**
     * 购买日期
     */
	@TableField("tho_date")
	private Date thoDate;
    /**
     * 拓展字段1
     */
	private String exp1;
    /**
     * 拓展字段2
     */
	private String exp2;

	@Override
	public String toString() {
		return "TbThedailyOne{" +
			"thoId=" + thoId +
			", userId=" + userId +
			", thdId=" + thdId +
			", thoDate=" + thoDate +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			"}";
	}
}
