package com.dkm.scheduled;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.seed.dao.LandSeedMapper;
import com.dkm.seed.dao.SeedsFallMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.service.ISeedFallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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


   @Autowired
   private ISeedFallService iSeedFallService;

   @Scheduled(cron = "0 */1 * * * ?")
   public void toDeleteApply () {
      iSeedFallService.seedDrop();
   }
}
