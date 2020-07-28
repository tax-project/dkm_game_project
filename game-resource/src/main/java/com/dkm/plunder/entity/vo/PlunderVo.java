package com.dkm.plunder.entity.vo;

import com.dkm.plunder.entity.bo.PlunderIdBO;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class PlunderVo {

   /**
    * 被抢人的用户id
    */
   private Long userId;

   /**
    * 物品id
    */
   private List<PlunderIdBO> goodsIdList;

   /**
    * 被抢人的等级
    */
   private Integer grade;
}
