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
public class TbEquipmentKnapsackVoTwo {


    /**
     * 装备主键
     */
    private Long equipmentId;
    /**
     * 我的装备 1为装备上了 2为背包装备
     */
    private Integer tekSell;
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



}
