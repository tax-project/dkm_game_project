package com.dkm.scheduled;

import com.dkm.campaign.service.ILotterySystemService;
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
    private ILotterySystemService lotterySystemService;

    /**
     * 刷新奖池数据，30秒更新一次，到时间自动开奖
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void refreshLottery() {
        if (!lotterySystemService.isInit()) {
            log.warn("神秘商店未初始化，已跳过刷新数据。(如果多次看到此消息则可能是神秘商店数据加载失败)");
        } else {
            try {
                lotterySystemService.refresh();
            } catch (Exception e) {
                log.error("神秘商店刷新时出现错误 ！", e);
            }
        }
    }
}
