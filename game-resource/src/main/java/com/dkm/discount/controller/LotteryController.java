package com.dkm.discount.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fkl
 *
 */
@Api("神秘商店 API ")
@RequestMapping("/lottery")
@RestController
public class LotteryController {
    /**
     * 得到所有的神秘商店开奖信息
     * @return 开奖信息
     */
    @GetMapping("/{userId}/getAllLotteries")
    public Object getAllLotteries(@PathVariable String userId){
        return null;
    }




}
