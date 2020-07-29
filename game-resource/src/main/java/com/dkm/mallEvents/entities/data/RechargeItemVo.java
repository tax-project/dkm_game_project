package com.dkm.mallEvents.entities.data;

import lombok.Data;

/**
 * Recharge item info
 *
 * @date 2020/7/29
 */
@Data
public class RechargeItemVo {

    /**
     * item status , for example :
     * <ul>
     *     <li><i>0</i>:  Does not comply.</li>
     *     <li><i>1</i>:  Eligible but not collected.</li>
     *     <li><i>2</i>:  Eligible and has been collected.</li>
     * </ul>
     */
    private Integer status;


}
