package com.dkm.integral.dao;

import com.dkm.integral.entity.Integral;
import com.dkm.integral.entity.Stars;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:44
 */
@Component
public interface IntegralMapper {
    /**
     * 查询所有积分产品
     * @return
     */
    List<Integral> queryAllIntegral();

    /**
     * 根据用户id查询用户积分
     * @param userId
     * @return
     */
    Integer queryUserIdIntegral(Long userId);

    /**
     * 根据用户id修改用户积分
     * @param iMyIntegral
     * @param userId
     * @return
     */
    int updateUserIntegral(@Param("iMyIntegral") Integer iMyIntegral, @Param("userId") Long userId);

    /**
     * 根据用户id修改用户所拥有的消耗数量
     * @param stars
     * @return
     */
    int updateUserStarsNumber(Stars stars);
}
