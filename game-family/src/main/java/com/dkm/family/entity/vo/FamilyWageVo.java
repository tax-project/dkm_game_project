package com.dkm.family.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: game_project
 * @description: 工资列表
 * @author: zhd
 * @create: 2020-06-05 13:45
 **/
@Data
@AllArgsConstructor
public class FamilyWageVo {
    /**
     * 0可领取1已领取2未到领取时间
     */
    private Integer status;
    /**
     * 工资金额
     */
    private Integer wage;
}
