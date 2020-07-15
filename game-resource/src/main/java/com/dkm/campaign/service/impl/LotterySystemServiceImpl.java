package com.dkm.campaign.service.impl;

import com.dkm.campaign.dao.LotteryItemDao;
import com.dkm.campaign.entity.LotteryItemEntity;
import com.dkm.campaign.service.ILotterySystemService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fkl
 */
@Slf4j
@Service
public class LotterySystemServiceImpl implements ILotterySystemService {
    private volatile boolean initStatus = false;
    @Resource
    private LotteryItemDao lotteryItemDao;

    /**
     * 结束时间
     */
    private volatile LocalDateTime stopDate;

    private final List<LotteryItemEntity> items = new LinkedList<>();

    @Override
    public void initData() throws Exception {
        if (initStatus) {
            throw new RuntimeException("此实体已经被初始化，请勿重新初始化方法！");
        }
        initStatus = true;
        final val entities = lotteryItemDao.selectAll();
        items.addAll(entities);
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
