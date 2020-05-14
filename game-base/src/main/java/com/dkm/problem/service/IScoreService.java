package com.dkm.problem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.problem.entity.vo.MoneyCountVo;
import com.dkm.problem.entity.vo.ScoreListVo;
import com.dkm.problem.entity.vo.ScoreVo;

/**
 * @author qf
 * @date 2020/5/10
 * @vesion 1.0
 **/
public interface IScoreService {

   /**
    *  增加用户积分表
    *  返回金额
    *  修改红包状态
    * @param moneyId
    * @param score
    * @return
    */
   ScoreVo insertScore(Long moneyId, Integer score);

   /**
    * 分页展示所有答题的排行
    * @param page
    * @param moneyId
    * @return
    */
   Page<ScoreListVo> pageScore(Page<ScoreListVo> page, Long moneyId);

   /**
    *  统计收红包的金额或者答题达人
    *  type==0  收红包的金额排行
    *  type==1 答题达人
    *  status==0 一周内
    *  status==1 全部数据
    * @param page
    * @param status
    * @param type
    * @return
    */
   Page<MoneyCountVo> countListMax(Page<MoneyCountVo> page, Integer status, Integer type);
}
