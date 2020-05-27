package com.dkm.plunder.entity.vo;

import com.dkm.good.entity.vo.GoodQueryVo;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/25
 * @vesion 1.0
 **/
@Data
public class PlunderUserGoodVo {

   /**
    * 用户id
    */
   private Long userId;

   /**
    * 用户头像
    */
   private String heardUrl;

   /**
    * 用户名
    */
   private String userName;

   /**
    * 等级
    */
   private Integer grade;

   /**
    *  产出物品
    */
   private List<GoodQueryVo> goodList;
}
