package com.dkm.problem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.bo.MoneyRandomBo;
import com.dkm.problem.entity.vo.HandOutRedEnvelopesVo;
import com.dkm.problem.entity.vo.MoneyCountVo;
import com.dkm.problem.entity.vo.MoneyPageVo;
import com.dkm.problem.entity.vo.MoneyVo;

import java.util.Map;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
public interface IMoneyService {


   /**
    *  发红包
    * @param vo 钻石  ，人数
    * @return 返回获得的奖励
    */
   HandOutRedEnvelopesVo handOutRedEnvelopes(MoneyVo vo);

   /**
    *  查询所有红包
    * @param vo
    * @return
    */
   Map<String, Object> listAllMoney(MoneyPageVo vo);

   /**
    * 修改红包状态
    * 以及红包答题人数
    * @param id
    * @param status
    */
   void updateMoneyStatus(Long id, Integer status);

   /**
    * 查询设定的人数
    * @param id
    * @return
    */
   Money queryNumber(Long id);

   /**
    * 将红包状态改成已完成
    * @param id
    */
   void updateMoney(Long id);

   /**
    * * 统计发红包
    *  0--一周内
    *  1--全部
    * @param page
    * @param status
    * @return
    */
   Page<MoneyCountVo> countHandOutRedEnvelopes(Page<MoneyCountVo> page, Integer status);


   /**
    *  查询红包活动
    * @return 返回前端需要的数据
    */
   MoneyRandomBo queryMoneyRandom ();


}
