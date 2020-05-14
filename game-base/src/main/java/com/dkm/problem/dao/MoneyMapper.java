package com.dkm.problem.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.vo.MoneyCountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Component
public interface MoneyMapper extends IBaseMapper<Money> {

   /**
    * 修改红包状态
    * 以及红包答题人数
    * @param id
    * @param status
    * @return
    */
   Integer updateMoney(@Param("id") Long id,
                       @Param("status") Integer status);


   /**
    * 统计发红包
    * @param page
    * @param status
    * @param startDate
    * @param endDate
    * @return
    */
   Page<MoneyCountVo> countHandOutRedEnvelopes(Page<MoneyCountVo> page,
                                               @Param("status") Integer status,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
}
