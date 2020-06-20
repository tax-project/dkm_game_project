package com.dkm.discount.controller;

import com.dkm.discount.entity.vo.LotteryInfoVo;
import com.dkm.discount.service.ILotteryService;
import com.dkm.discount.service.impl.LotteryServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fkl
 */
@Api("神秘商店 API ")
@RequestMapping("/lottery")
@RestController
public class LotteryController {
    @Resource
    private ILotteryService iLotteryService;

    /**
     * 得到所有的神秘商店开奖信息
     *
     * @return 开奖信息
     */
    @GetMapping("/{userId}/getAllLotteries")
    public LotteryInfoVo getAllLotteries(@PathVariable String userId) {
        return iLotteryService.getAllLotteries(userId);
    }


}
