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

   @Autowired
   private SeedsFallMapper seedsFallMapper;

   @Autowired
   private LandSeedMapper landSeedMapper;


   @Scheduled(cron = "0 */1 * * * ?")
   public void toDeleteApply () {
    /*  //查询已经种植的种子
      LambdaQueryWrapper<LandSeed> queryWrapper  = new LambdaQueryWrapper<LandSeed>()
              .eq(LandSeed::getLeStatus, 1);

      List<LandSeed> landSeedList = landSeedMapper.selectList(queryWrapper);

      List<Long> list=new ArrayList<>();

      for (int i = 0; i < landSeedList.size(); i++) {
         if(System.currentTimeMillis()/1000>landSeedList.get(i).getPlantTime().toEpochSecond(ZoneOffset.of("+8"))){
            list.add(landSeedList.get(i).getId());
         }
      }

      if(list.size()!=0){
         //批量修改
         seedsFallMapper.updateLeStatus(list);
      }*/


      iSeedFallService.seedDrop();
   }
}
