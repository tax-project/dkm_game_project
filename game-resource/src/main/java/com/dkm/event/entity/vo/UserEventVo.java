package com.dkm.event.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:16
 */
@Data
public class UserEventVo {

    /**
     * 用户名称
     */
    private String weChatNickName;

    /**
     * 用户性别
     */
    private Integer userSex;

    /**
     * 用户头像
     */
    private String weChatHeadImgUrl;

    /**
     * 用户声望
     */
    private Integer userInfoRenown;

    /**
     * 时间
     */
    private LocalDateTime evTime;

    /**
     * 事件内容 （里）
     */
    private String evMsgIn;

    /**
     * 事件内容 （外）
     */
    private String evMsgExternal;

}
