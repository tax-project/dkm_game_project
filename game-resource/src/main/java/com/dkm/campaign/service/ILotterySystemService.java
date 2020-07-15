package com.dkm.campaign.service;

/**
 * 关于神秘商店的抽取系统
 *
 * @author FKL
 */
public interface ILotterySystemService {

    /**
     * 初始化神秘商店的数据,单实例应当仅启动一次
     *
     * @throws Exception 如果出现错误表明神秘商店出现灾难性问题
     */
    void initData() throws Exception;

    /**
     * 是否初始化完成
     * @return 初始化完成
     */
    boolean isInit();

    /**
     * 刷新
     */
    void refresh();
}
