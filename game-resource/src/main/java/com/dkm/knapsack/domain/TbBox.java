package com.dkm.knapsack.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
     * 箱子类型 1为普通箱子 2为VIP箱子
     */
	@TableField("box_type")
	private Integer boxType;


	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public Integer getBoxType() {
		return boxType;
	}

	public void setBoxType(Integer boxType) {
		this.boxType = boxType;
	}

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
