package com.dkm.plunder.entity.bo;

import com.dkm.good.entity.vo.GoodQueryVo;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/7/24
 * @vesion 1.0
 **/
@Data
public class PlunderBO {

   /**
    *  数据类表
    */
   private List<GoodQueryVo> list;

   /**
    *  0--不是vip
    *  1--是vip
    */
   private Integer isVip;
}
