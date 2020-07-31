package com.dkm.land.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.UserLandUnlock;
import com.dkm.seed.entity.LandSeed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:土地
 * @USER: 刘梦祺
 * @DATE: 2020/5/9 9:37
 */
@Component
public interface LandMapper extends IBaseMapper<LandSeed> {

    /**
     * 土地批量增加
     * @param list
     * @return
     */
    int insertLand(List<Land> list);

    List<UserLandUnlock> queryUserByIdLand(Long userId);

    /**
     * 批量增加用户拥有土地
     * @param list
     * @return
     */
    int addLand(List<UserLandUnlock> list);

    /**
     * 查询解锁土地
     * @param userId
     * @return
     */
    List<UserLandUnlock> queryUnlockLand(Long userId);

    /**
     * 查询未解锁土地
     * @param userId
     * @return
     */
    List<UserLandUnlock> queryNotUnlocked(Long userId);

    /**
     *  updateStatus
     * @param userId
     * @param laNo
     * @return
     */
    int updateStatus(@Param("userId") Long userId, @Param("laNo") Integer laNo);


}
