package com.dkm.campaign.service.impl;

import com.dkm.campaign.service.ILotterySystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author fkl
 */
@Slf4j
@Service
public class LotterySystemServiceImpl implements ILotterySystemService {
    private volatile boolean initStatus = false;


    @Override
    public void initData() {
        if (initStatus) {
            throw new RuntimeException("此实体已经被初始化，请勿重新初始化方法！");
        }
        initStatus = true;
    }

    @Override
    public boolean isInit() {
        return initStatus;
    }

    @Override
    public void refresh() {
        log.debug("神秘商店的奖池刷新了！");
    }
}
