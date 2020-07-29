package com.dkm.scheduled;

import com.dkm.campaign.service.ILotteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * @author fkl
 */
@EnableScheduling
@Configuration
@Slf4j
public class LotteryScheduledTask {

    @Resource
    private ILotteryService lotterySystemService;

    /**
     * 刷新奖池数据，30秒更新一次，到时间自动开奖
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void refreshLottery() {

        try {
            lotterySystemService.refresh();
        } catch (Exception e) {
            log.error("神秘商店刷新时出现错误 ！", e);
        }

    }
}
