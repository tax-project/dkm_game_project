package com.dkm.problem.service;

import com.dkm.problem.entity.Problem;
import com.dkm.problem.entity.vo.ProblemVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/8
 * @vesion 1.0
 **/
public interface IProblemService {


   /**
    *  批量增加问题
    * @param list
    */
   void insertAllProblem(List<ProblemVo> list);


   /**
    *  随机返回10条数据
    * @param id
    * @return
    */
   List<Problem> listProblem(Long id);
}
