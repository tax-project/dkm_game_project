package com.dkm.knapsack.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 宝箱表
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@TableName("tb_box")
@Data
public class TbBox extends Model<TbBox> {

    private static final long serialVersionUID = 1L;

    /**
     * 宝箱主键
     */
    @TableId("box_id")
	private Long boxId;
    /**
     * 箱子编号
     */
	@TableField("box_no")
	private String boxNo;
    /**
     * 箱子类型 1为普通箱子 2为白银VIP箱子 3为黄金VIP箱子 4为白金vip
     */
	@TableField("box_type")
	private Integer boxType;
	/**
	 * 继续开箱子 要花费的钻石数量
	 */
	@TableField("box_money")
	private Integer boxMoney;
	/**
	 * 种子的id
	 */
	@TableField("box_bid")
	private Long boxBid;
	/**
	 * 箱子编号
	 */
	@TableField("box_name")
	private String boxName;
	/**
	 * 箱子编号
	 */
	@TableField("box_img")
	private String boxImg;


	@Override
	protected Serializable pkVal() {
		return this.boxId;
	}

	@Override
	public String toString() {
		return "TbBox{" +
			"boxId=" + boxId +
			", boxNo=" + boxNo +
			", boxType=" + boxType +
			"}";
	}
}
