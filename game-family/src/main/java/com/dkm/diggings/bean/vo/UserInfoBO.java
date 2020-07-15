package com.dkm.diggings.bean.vo;

import lombok.Data;

/**
 * @author OpenE
 */
@Data
public class UserInfoBO {
    private long userId;
    private int userInfoDiamonds;
    private int userInfoGold;
    private int userInfoNowExperience;
    private int userInfoRenown;
}
