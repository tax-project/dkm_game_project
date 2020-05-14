package com.dkm.pets.entity.dto;

import lombok.Data;

/**
 * @author zhd
 * @date 2020/5/9 15:32
 */
@Data
public class UserInfo {
    /**
     * 用户金币
     * */
    private  Integer userInfoGold;
    /**
     * 用户声望
     * */
    private Integer  userInfoRenown;
    /**
     * 用户等级
     * */
    private Integer userInfoGrade;
}
