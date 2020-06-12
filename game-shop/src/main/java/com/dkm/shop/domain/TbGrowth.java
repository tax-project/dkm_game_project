package com.dkm.shop.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2020-06-11
 */
@TableName("tb_growth")
@Data
public class TbGrowth extends Model<TbGrowth> {

    private static final long serialVersionUID = 1L;

    /**
     * 成长奖励充值记录主键
     */
	@TableField("tb_gid")
	private Long tbGid;
    /**
     * 用户外键
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 充值时间
     */
	@TableField("tb_date")
	private Date tbDate;
    /**
     * 1 代表充值了 2代表领取钻石
     */
	@TableField("tb_isva")
	private Integer tbIsva;
	/**
	 * 加入是2 那么这是领取数量
	 */
	@TableField("tb_num")
	private Integer tbNum;
	/**
	 * 加入是2 那么这是领取数量
	 */
	@TableField("tb_dj")
	private Integer tbDj;

	@Override
	public String toString() {
		return "TbGrowth{" +
			"tbGid=" + tbGid +
			", userId=" + userId +
			", tbDate=" + tbDate +
			", tbIsva=" + tbIsva +
			"}";
	}
}
