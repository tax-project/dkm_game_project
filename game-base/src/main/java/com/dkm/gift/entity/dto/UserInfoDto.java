package com.dkm.gift.entity.dto;

import lombok.Data;

/**
 * @program: game_project
 * @description: 送礼所需用户信息
 * @author: zhd
 * @create: 2020-05-29 09:43
 **/
@Data
public class UserInfoDto {

    /**
     * userId
     */
    private Long userId;
    /**
     * 用户金币
     */
    private Integer userInfoGold;
    /**
     * 用户钻石
     */
    private Integer userInfoDiamonds;

    /**
     * 用户声望
     */
    private Integer userInfoCharm;
}
