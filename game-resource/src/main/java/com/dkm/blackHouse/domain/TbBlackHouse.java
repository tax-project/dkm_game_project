package com.dkm.blackHouse.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 黑屋表
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@TableName("tb_black_house")
public class TbBlackHouse extends Model<TbBlackHouse> {

    private static final long serialVersionUID = 1L;

    /**
     * 黑屋主键
     */
    @TableId("black_id")
	private Long blackId;
	@TableField("from_id")
	private Long fromId;
	@TableField("to_id")
	private Long toId;
    /**
     * 是否释放
     */
	@TableField("is_black")
	private Integer isBlack;
    /**
     * 被关时间
     */
	private Date time;


	public Long getBlackId() {
		return blackId;
	}

	public void setBlackId(Long blackId) {
		this.blackId = blackId;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	protected Serializable pkVal() {
		return this.blackId;
	}

	@Override
	public String toString() {
		return "TbBlackHouse{" +
			"blackId=" + blackId +
			", fromId=" + fromId +
			", toId=" + toId +
			", isBlack=" + isBlack +
			", time=" + time +
			"}";
	}
}
