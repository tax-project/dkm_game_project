package com.dkm.campaign.service;

/**
 * 关于神秘商店的抽取系统
 *
 * @author FKL
 */
public interface ILotterySystemService {

    /**
     * 初始化神秘商店的数据,单实例应当仅启动一次
     */
    void initData();

    /**
     * 是否初始化完成
     */
    boolean isInit();

    /**
     * 刷新
     */
    void refresh();
}
