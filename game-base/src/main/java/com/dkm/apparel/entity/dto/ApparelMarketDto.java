package com.dkm.apparel.entity.dto;

import com.dkm.apparel.entity.ApparelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-30 14:40
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApparelMarketDto extends ApparelEntity {
    private Integer gold;
    private Long apparelMarketId;
    private Long userId;
}
