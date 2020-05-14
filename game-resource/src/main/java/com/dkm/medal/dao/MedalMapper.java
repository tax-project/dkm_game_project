package com.dkm.medal.dao;

import com.dkm.medal.entity.Medal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 15:37
 */
@Component
public interface MedalMapper {
    /**
     * 根据用id查询勋章(详情)
     * @param userId
     * @return  List<Medal>
     */
    List<Medal> queryMedalById(long userId);

    /**
     * 根据用id查询勋章详情
     * @param medalId
     * @return  List<Medal>
     */
    List<Medal> queryMedalByIdDetails(Integer medalId);
}
