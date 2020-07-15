package com.dkm.boot;

import com.dkm.campaign.service.ILotterySystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 神秘商店的启动加载
 *
 * @author fkl
 */
@Slf4j
@Component
public class LotteryRunner implements ApplicationRunner {

    @Resource
    private ILotterySystemService lotterySystemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("为 \"\\活动\\神秘商店\" 初始化数据.");
        lotterySystemService.initData();
        log.info("\"神秘商店\" 初始化完成！");
    }
}
