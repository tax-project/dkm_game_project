package com.dkm.knapsack.domain.vo;



import java.math.BigDecimal;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
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
    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Integer getFoodNumber() {
        return foodNumber;
    }

    public void setFoodNumber(Integer foodNumber) {
        this.foodNumber = foodNumber;
    }

    public Long getTekId() {
        return tekId;
    }

    public void setTekId(Long tekId) {
        this.tekId = tekId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Integer getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(Integer equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public String getEquipmentImage() {
        return equipmentImage;
    }

    public void setEquipmentImage(String equipmentImage) {
        this.equipmentImage = equipmentImage;
    }

    public String getExp1() {
        return exp1;
    }

    public void setExp1(String exp1) {
        this.exp1 = exp1;
    }

    public String getExp2() {
        return exp2;
    }

    public void setExp2(String exp2) {
        this.exp2 = exp2;
    }

    public String getExp3() {
        return exp3;
    }

    public void setExp3(String exp3) {
        this.exp3 = exp3;
    }

    public Long getEdId() {
        return edId;
    }

    public void setEdId(Long edId) {
        this.edId = edId;
    }

    public String getEdDetailedDescription() {
        return edDetailedDescription;
    }

    public void setEdDetailedDescription(String edDetailedDescription) {
        this.edDetailedDescription = edDetailedDescription;
    }

    public Integer getEdEquipmentReputation() {
        return edEquipmentReputation;
    }

    public void setEdEquipmentReputation(Integer edEquipmentReputation) {
        this.edEquipmentReputation = edEquipmentReputation;
    }

    public BigDecimal getEdRedEnvelopeAcceleration() {
        return edRedEnvelopeAcceleration;
    }

    public void setEdRedEnvelopeAcceleration(BigDecimal edRedEnvelopeAcceleration) {
        this.edRedEnvelopeAcceleration = edRedEnvelopeAcceleration;
    }

    public Long getKnapsackId() {
        return knapsackId;
    }

    public void setKnapsackId(Long knapsackId) {
        this.knapsackId = knapsackId;
    }

    public Integer getTekSell() {
        return tekSell;
    }

    public void setTekSell(Integer tekSell) {
        this.tekSell = tekSell;
    }

    public Integer getTekMoney() {
        return tekMoney;
    }

    public void setTekMoney(Integer tekMoney) {
        this.tekMoney = tekMoney;
    }

    public Integer getTekDaoju() {
        return tekDaoju;
    }

    public void setTekDaoju(Integer tekDaoju) {
        this.tekDaoju = tekDaoju;
    }

    public Integer getTekIsva() {
        return tekIsva;
    }

    public void setTekIsva(Integer tekIsva) {
        this.tekIsva = tekIsva;
    }
}
