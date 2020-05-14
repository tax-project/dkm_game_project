package com.dkm.problem.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.problem.entity.Problem;
import com.dkm.problem.entity.vo.ProblemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * @author qf
 * @date 2020/5/8
 * @vesion 1.0
 **/
@Component
public interface ProblemMapper extends IBaseMapper<Problem> {


   /**
    *  批量增加问题
    * @param list
    * @return
    */
   Integer insertAllProblem(List<ProblemVo> list);

   /**
    *   修改红包次数
    * @param date 更新时间
    * @param userId  用户id
    * @return 返回结果
    */
   Integer updateMuch (@Param("date") LocalDate date, @Param("userId") Long userId);
}
