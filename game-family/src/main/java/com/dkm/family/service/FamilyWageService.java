package com.dkm.family.service;

import java.util.List;
import java.util.Map;

/**
 * @program: game_project
 * @description: 家族工资发放api
 * @author: zhd
 * @create: 2020-06-03 14:36
 **/
public interface FamilyWageService {

    /**
     * 根据家族权限获取工资
     * @param userId
     */
    Map<String,Object> getWageList(Long userId);

    /**
     * 领取工资
     * @param wage
     * @param userId
     */
    void updateUserWage(Integer wage,Long userId);

}
