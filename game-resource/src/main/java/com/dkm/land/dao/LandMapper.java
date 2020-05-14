package com.dkm.land.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.land.entity.Land;
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
public interface LandMapper extends IBaseMapper<Land> {

    /**
     * 土地批量增加
     * @param list
     * @return
     */
    int insertLand(List<Land> list);

    /**
     * 根据用户id查询土地
     * @param userId
     * @return List<Land>
     */
    List<Land> queryLand(long userId);

}
