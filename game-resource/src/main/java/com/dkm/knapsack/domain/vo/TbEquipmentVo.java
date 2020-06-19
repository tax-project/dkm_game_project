package com.dkm.knapsack.domain.vo;

import lombok.Data;

import java.math.BigDecimal;


/**
 * <p>
 * 装备表和装备详情表的vo
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@Data
public class TbEquipmentVo{

    private static final long serialVersionUID = 1L;

	/**
	 * 装备主键
	 */
	private Long equipmentId;
	/**
	 * 宝箱主键
	 */
	private Long boxId;
	/**
	 * 装备等级
	 */
	private Integer equipmentLevel;
	/**
	 * 装备图片
	 */
	private String equipmentImage;
	/**
	 * 拓展字段1
	 */
	private String exp1;
	/**
	 * 道具50金币 穿戴品只有5金币
	 */
	private String exp2;
	/**
	 * 1为穿戴品 2为道具
	 */
	private String exp3;

	/**
	 * 装备详情主键
	 */
	private Long edId;

	/**
	 * 详情描述
	 */
	private String edDetailedDescription;
	/**
	 * 装备声望
	 */
	private Integer edEquipmentReputation;
	/**
	 * 红包加速
	 */
	private BigDecimal edRedEnvelopeAcceleration;

	/**
	 * 防御才华
	 */
	private BigDecimal edDefense;

	/**
	 * 属性加成 1就代表有加成 0代表没有加成
	 */
	private BigDecimal edAttribute;


	/**
	 * 装备生命
	 */
	private BigDecimal edLife;


	/**
	 * 攻击力
	 */
	private BigDecimal edAttack;


	/**
	 * 1 为生命加成 2为才华加成
	 */
	private BigDecimal edType;

	/**
	 * 生命或才华的值
	 */
	private BigDecimal edTypevalue;


	/**
	 * 1为踢出群聊 2为胡言乱语闪避
	 */
	private BigDecimal edTypeone;

	/**
	 * 踢出群聊和胡言乱语闪避的值
	 */
	private BigDecimal edTypeonevalue;


	/**
	 * 箱子编号
	 */
	private String boxNo;
	/**
	 * 箱子类型 1为普通箱子 2为白银VIP箱子 3为黄金VIP箱子 4为白金vip
	 */
	private Integer boxType;
	/**
	 * 继续开箱子 要花费的钻石数量
	 */
	private Integer boxMoney;
	/**
	 * 种子的id
	 */
	private Long boxBid;

}
