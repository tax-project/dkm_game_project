package com.dkm.scheduled;



import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * 种子掉落  10S一次
 * @author qf
 * @date 2020/6/29
 * @vesion 1.0
 **/
@Slf4j
@Component
public class SeedScheduledTask {


   @Scheduled(cron = "0/10 * * * * ?")
   public void toDeleteApply () {



   }
}
