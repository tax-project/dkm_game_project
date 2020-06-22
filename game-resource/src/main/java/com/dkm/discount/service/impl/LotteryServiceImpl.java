package com.dkm.discount.service.impl;

import com.dkm.discount.bean.vo.LotteryInfoVo;
import com.dkm.discount.service.ILotteryService;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * @author fkl
 */
@Service
public class LotteryServiceImpl implements ILotteryService {

    @Override
    public LotteryInfoVo getAllLotteries(String userId) {
        val lotteryInfoVo = new LotteryInfoVo();

//        lotteryInfoVo.setNextRefreshDate();
        return lotteryInfoVo;
    }
}
