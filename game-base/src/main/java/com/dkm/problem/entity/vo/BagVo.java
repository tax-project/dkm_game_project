package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Data
public class BagVo {

   /**
    * 0--声望
    * 1--金币
    * 2--钻石
    */
   private Integer bagNo;

   /**
    * 具体名称
    */
   private String bagName;

   /**
    * 开出的数量
    */
   private Integer bagNumber;
}
