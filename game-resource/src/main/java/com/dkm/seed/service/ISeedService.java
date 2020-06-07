package com.dkm.seed.service;

import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 16:17
 */
public interface ISeedService {
    /**
     * 根据用户id得到种子信息
     */
    List<SeedPlantUnlock> queryUserIdSeed();

    /**
     * 解锁植物
     * @return
     */
    Message unlockPlant(SeedVo seedVo);
    /**
     * 种植
     *
     */
    void queryAlreadyPlantSeed(SeedPlantVo seedPlantVo);

    /**
     * 根据种子查询种子
     *
     */
    SeedDetailsVo querySeedById(Integer seeId);
    /**
     * 修改用户信息
     */
   // int updateUser(UserInIf userInIf);

    /**
     * 根据用户id查询已解锁的种子
     * @return
     */
    List<SeedUnlockVo> queryAreUnlocked(Long userId);

    /**
     * 根据用户id查询所有用户信息
     * @return
     */
    Result<UserInfoQueryBo> queryUserAll();

    /**
     * 查询已经种植的种子
     */
    List<LandYesVo> queryAlreadyPlantSd();

    /**
     * 种子掉落
     */
    int seedDrop(Long id,Long seedId);
    



}
