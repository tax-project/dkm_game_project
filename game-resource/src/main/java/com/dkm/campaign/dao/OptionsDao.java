package com.dkm.campaign.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author OpenE dragon
 */
@Mapper
public interface OptionsDao {
    /**
     * 查询刷新时间
     *
     * @return
     */
    String selectRefreshDateStr();

    /**
     * 查询下次刷新时间
     *
     * @return
     */
    String selectNextUpdateDateStr();


    /**
     * @param date
     * @return
     */
    int updateNextUpdateDate(@Param("date") String date);

    /**
     * @param date
     * @return
     */
    int updateRefreshDate(@Param("date") String date);


}
