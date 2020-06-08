package com.dkm.attendant.entity.bo;

import com.dkm.produce.entity.vo.AttendantPutVo;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Data
public class AttPutAllBo {

   /**
    *  跟班id
    */
   private Long attendantId;

   /**
    * 对应的产出物品集合
    */
   private List<AttendantPutVo> list;
}
