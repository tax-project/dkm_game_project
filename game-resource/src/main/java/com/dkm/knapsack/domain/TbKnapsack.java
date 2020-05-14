package com.dkm.knapsack.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 背包表
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@TableName("tb_knapsack")
public class TbKnapsack extends Model<TbKnapsack> {

    private static final long serialVersionUID = 1L;

    /**
     * 背包主键
     */
    @TableId("knapsack_id")
	private Long knapsackId;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 背包容量 默认 30 VIP容纳60
     */
	@TableField("knapsack_capacity")
	private Integer knapsackCapacity;


	public Long getKnapsackId() {
		return knapsackId;
	}

	public void setKnapsackId(Long knapsackId) {
		this.knapsackId = knapsackId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getKnapsackCapacity() {
		return knapsackCapacity;
	}

	public void setKnapsackCapacity(Integer knapsackCapacity) {
		this.knapsackCapacity = knapsackCapacity;
	}

	@Override
	protected Serializable pkVal() {
		return this.knapsackId;
	}

	@Override
	public String toString() {
		return "TbKnapsack{" +
			"knapsackId=" + knapsackId +
			", userId=" + userId +
			", knapsackCapacity=" + knapsackCapacity +
			"}";
	}
}
