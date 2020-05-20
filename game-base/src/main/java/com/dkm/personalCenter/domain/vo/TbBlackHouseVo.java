package com.dkm.personalCenter.domain.vo;

import java.util.Date;

/**
 * <p>
 * 黑屋表
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
public class TbBlackHouseVo {


    /**
     * 黑屋主键
     */
	private Long blackId;
	private Long fromId;
	private Long toId;
    /**
     * 是否释放
     */
	private Integer isBlack;
    /**
     * 被关时间
     */
	private Date time;

	/**
	 * 微信用户呢称
	 */
	private String weChatNickName;

	/**
	 * 用户等级
	 */
	private Integer userInfoGrade;

	/**
	 * 用户声望值
	 */
	private Integer userInfoRenown;

	/**
	 * 被关黑屋的用户ID
	 */
	private Long userId;

	public Long userId() {
		return userId;
	}

	public void setUser_id(Long userId) {
		this.userId = userId;
	}


	public String getWeChatNickName() {
		return weChatNickName;
	}

	public void setWeChatNickName(String weChatNickName) {
		this.weChatNickName = weChatNickName;
	}

	public Integer getUserInfoGrade() {
		return userInfoGrade;
	}

	public void setUserInfoGrade(Integer userInfoGrade) {
		this.userInfoGrade = userInfoGrade;
	}

	public Integer getUserInfoRenown() {
		return userInfoRenown;
	}

	public void setUserInfoRenown(Integer userInfoRenown) {
		this.userInfoRenown = userInfoRenown;
	}

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
