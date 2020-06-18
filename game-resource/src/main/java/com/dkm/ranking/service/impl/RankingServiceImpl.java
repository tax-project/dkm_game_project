package com.dkm.ranking.service.impl;

import com.dkm.data.Result;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.entity.GiftRankingDto;
import com.dkm.ranking.service.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/18 15:20
 */
public class RankingServiceImpl implements IRankingService {


    @Autowired
    private BaseFeignClient baseFeignClient;


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



        map.put("giftRanking",giftRanking.getData());
        return map;
    }
}
