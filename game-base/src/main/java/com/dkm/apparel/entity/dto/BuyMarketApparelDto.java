package com.dkm.apparel.entity.dto;

import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-30 14:53
 **/
@Data
public class BuyMarketApparelDto {
    private Long apparelMarketId;
    private Long buyUserId;
    private Long sellUserId;
}
