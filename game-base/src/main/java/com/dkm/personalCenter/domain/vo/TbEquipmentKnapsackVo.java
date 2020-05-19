package com.dkm.personalCenter.domain.vo;



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
     * 食物主键
     */
    private Long foodId;
    /**
     * 食物数量
     */
    private Integer foodNumber;
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
     * 食物名字
     */
    private String foodName;
    /**
     * 食物图片地址
     */
    private String foodUrl;
    /**
     * 售价
     */
    private Integer foodGold;
}
