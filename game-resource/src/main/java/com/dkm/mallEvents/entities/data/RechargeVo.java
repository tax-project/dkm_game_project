package com.dkm.mallEvents.entities.data;

import lombok.Data;

import java.util.List;

/**
 *  Recharge Info
 *
 *  @date 2020/7/29
 *
 */
@Data
public class RechargeVo {
    /**
     * result content size.
     */
    private Integer content = 0;

    List<RechargeItemVo> prizes;


}
