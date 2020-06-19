package com.dkm.ranking.controller;

import com.dkm.ranking.service.IRankingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/18 15:19
 */
@Api(tags = "排行榜Api")
@RestController
@Slf4j
@RequestMapping("/RankingController")
public class RankingController {

    @Autowired
    private IRankingService iRankingService;


    /**
     * 查询排行榜（声望，魅力，富豪）
     * @return
     */
    @ApiOperation("查询排行榜（声望，魅力，富豪）")
    @ApiImplicitParam(name = "type",value = "1富豪2魅力",required = true,paramType = "Integer",dataType = "path")
    @GetMapping("/queryRankingList")
    @CrossOrigin
    public Map<String,Object> queryRankingList(@RequestParam(value = "type") Integer type){
        return iRankingService.queryRankingList(type);
    }
}
