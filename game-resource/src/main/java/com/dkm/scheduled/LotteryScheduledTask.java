package com.dkm.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author fkl
 */
@EnableScheduling
@Configuration
@Slf4j
public class LotteryScheduledTask {

    /**
     * 刷新奖池数据，30秒更新一次，到时间自动开奖
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void refreshLottery() {
        try {
            log.debug("奖池刷新了！");
        } catch (Throwable ignore) {
        } finally {

        }
    }
}
