package com.dkm.plunder.entity.vo;

import com.dkm.good.entity.vo.GoodQueryVo;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/7/3
 * @vesion 1.0
 **/
@Data
public class OpponentResultVo {

   private Long userId;

   /**
    *  头像
    */
   private String imgUrl;

   /**
    * 昵称
    */
   private String nickName;

   /**
    * 等级
    */
   private Integer grade;

   /**
    * 声望
    */
   private Integer userInfoRenown;

   /**
    *  物品列表
    */
   private List<GoodQueryVo> goodList;
}
