package com.dkm.userInfo.entity.bo;

import lombok.Data;

/**
 * @Author: HuangJie
 */
@Data
public class ReputationRankingBO {
    /**
     * 昵称
     */
    private String weChatNickName;
    /**
     * 头像地址
     */
    private String weChatHeadImgUrl;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 声望
     */
    private String userInfoRenown;
}
