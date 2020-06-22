package com.dkm.knapsack.domain.vo;



import lombok.Data;

import java.math.BigDecimal;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
@Data
public class TbEquipmentKnapsackVo {

    /**
     * 装备背包表主键
     */
    private Long tekId;
    /**
     * 装备主键
     */
    private Long equipmentId;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 背包主键
     */
    private Long knapsackId;
    /**
     * 我的装备 1为装备上了 2为背包装备
     */
    private Integer tekSell;
    /**
     * 道具50金币 穿戴品只有5金币
     */
    private Integer tekMoney;
    /**
     * 1为穿戴品 2为道具 3为食物
     */
    private Integer tekDaoju;
    /**
     * 1为没有被卖 0被卖了
     */
    private Integer tekIsva;

    /**
     * 道具主键
     */
    private Long foodId;
    /**
     * 道具拥有数量
     */
    private Integer foodNumber;
    /**
     * 物品前端跳转路径
     */
    private String tabUrl;
    /**
     * 物品类型
     */
    private Integer goodType;
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
     * 食物名字
     */
    private String name;
    /**
     * 食物图片地址
     */
    private String url;
    /**
     * 售价
     */
    private Integer goodMoney;


}
