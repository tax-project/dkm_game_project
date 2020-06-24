package com.dkm.apparel.entity.vo;

import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.entity.ApparelMarketEntity;
import com.dkm.apparel.entity.ApparelUserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description: 集市服饰信息
 * @author: zhd
 * @create: 2020-06-22 14:51
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApparelMarketDetailVo extends ApparelMarketEntity {
    private String apparelUrl;
    private Integer apparelGold;
    private Integer apparelSex;
    private String apparelName;
    private String time;
}
