package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/3 10:41
 */
@Data
public class AttendantUserVo {
    /**
     * 用户头像
     */
    private String weChatHeadImgUrl;
    /**
     * 用户名称
     */
    private String weChatNickName;
    /**
     * 用户声望
     */
    private Integer userInfoRenown;
    /**
     * 用户id
     */
    private Long userId;
}
