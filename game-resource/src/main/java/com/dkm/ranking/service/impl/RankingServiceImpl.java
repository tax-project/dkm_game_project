package com.dkm.ranking.service.impl;

import com.dkm.data.Result;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.GiftRankingDto;
import com.dkm.feign.entity.ReputationRankingBO;
import com.dkm.ranking.service.IRankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/18 15:20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RankingServiceImpl implements IRankingService {


    @Autowired
    private BaseFeignClient baseFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;


    /**
     * 查询排行榜（声望，魅力，富豪）
     * @return
     */
    @Override
    public Map<String, Object> queryRankingList(Integer type) {
        Map<String,Object> map=new HashMap<>(16);

        /**
         * 查询排行榜（魅力，富豪）
         */
        Result<List<GiftRankingDto>> giftRanking = baseFeignClient.getGiftRanking(type);

        System.out.println("giftRanking = " + giftRanking.getData());
        /**
         * 查询声望排行榜
         */
        Result<List<ReputationRankingBO>> listResult = userFeignClient.reputationRanking();

        map.put("giftRanking",giftRanking.getData());
        map.put("listResult",listResult.getData());

        return map;
    }
}
