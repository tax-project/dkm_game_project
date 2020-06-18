package com.dkm.ranking.controller;

import com.dkm.ranking.service.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/18 15:19
 */
public class RankingController {

    @Autowired
    private IRankingService iRankingServicel;


    /**
     * 查询排行榜（声望，魅力，富豪）
     * @return
     */
    public Map<String,Object> queryRankingList(){
        return iRankingServicel.queryRankingList();
    }
}
