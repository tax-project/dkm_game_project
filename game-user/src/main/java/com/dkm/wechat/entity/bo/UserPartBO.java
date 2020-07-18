package com.dkm.wechat.entity.bo;

import lombok.Data;

/**
 * @author jie
 */
@Data
public class UserPartBO {

    /**
     *  用户ID
     */
    private Long userId;
    /**
     * 微信昵称
     */
    private String weChatNickName;
}
