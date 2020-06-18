package com.dkm.feign;

import com.dkm.data.Result;
import com.dkm.feign.entity.GiftRankingDto;
import com.dkm.feign.entity.PetsDto;
import com.dkm.feign.fallback.BaseFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/1 14:43
 */
@FeignClient(value = "base", fallback = BaseFeignClientFallback.class)
public interface BaseFeignClient {
    /**
     * 战斗获取宠物信息（内部）
     * @param userId
     */
    @GetMapping("/v1/pets/getPetInfo")
    Result<List<PetsDto>> getPetInfo(@RequestParam("userId") Long userId);

    /**
     * 查询（富豪魅力排行榜）
     * @param type
     */
    @GetMapping("/gift/getGiftRanking")
    Result<List<GiftRankingDto>> getGiftRanking(@RequestParam("type") Integer type);
}
