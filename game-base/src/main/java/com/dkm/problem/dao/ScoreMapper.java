package com.dkm.problem.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.problem.entity.Score;
import com.dkm.problem.entity.vo.MoneyCountVo;
import com.dkm.problem.entity.vo.ScoreListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/10
 * @vesion 1.0
 **/
@Component
public interface ScoreMapper extends IBaseMapper<Score> {

   /**
    * 分页展示所有答题的排行
    * @param page
    * @param moneyId
    * @return
    */
   Page<ScoreListVo> pageScore(Page<ScoreListVo> page,
                               @Param("moneyId") Long moneyId);

   /**
    * 统计收红包的金额或者答题达人
    * @param page
    * @param status
    * @param type
    * @param startDate
    * @param endDate
    * @return
    */
   Page<MoneyCountVo> countListMax(Page<MoneyCountVo> page,
                                   @Param("status") Integer status,
                                   @Param("type") Integer type,
                                   @Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);
}
