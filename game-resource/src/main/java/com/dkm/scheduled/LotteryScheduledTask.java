package com.dkm.scheduled;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author fkl
 */
@EnableScheduling
@Configuration
public class LotteryScheduledTask {

    /**
     * 刷新奖池数据，30秒更新一次，到时间自动开奖
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void refreshLottery() {
        try {

        } catch (Throwable ignore) {
        } finally {

        }
    }
}
