package com.dkm.attendant.entity.bo;

import com.dkm.plunder.entity.UserProduce;
import com.dkm.produce.entity.Produce;
import lombok.Data;

/**
 * @auther qf
 * @date 2020/7/28
 * @vesion 1.0
 **/
@Data
public class ProducePutBO {

   private Produce produce;

   private UserProduce userProduce;
}
