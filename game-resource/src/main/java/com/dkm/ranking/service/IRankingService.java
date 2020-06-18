package com.dkm.ranking.service;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/18 15:20
 */
public interface IRankingService {

    /**
     * 查询排行榜（声望，魅力，富豪）
     * @return
     */
    Map<String,Object> queryRankingList();
}
