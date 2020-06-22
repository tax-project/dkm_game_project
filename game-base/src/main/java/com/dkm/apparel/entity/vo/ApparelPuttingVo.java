package com.dkm.apparel.entity.vo;

import com.dkm.apparel.entity.ApparelMarketEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description: 上架服饰信息
 * @author: zhd
 * @create: 2020-06-22 15:03
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApparelPuttingVo extends ApparelMarketEntity {
    private String apparelUrl;
    private Integer apparelGold;
    private Integer apparelSex;
    private String apparelName;
}
