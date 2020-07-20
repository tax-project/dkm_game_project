package com.dkm.feign.entity;

import lombok.Data;

@Data
public class UserNameVo {
    /**
     * 用户 Id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String  weChatNickName;
}
