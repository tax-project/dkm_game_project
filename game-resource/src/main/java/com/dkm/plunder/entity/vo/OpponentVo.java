package com.dkm.plunder.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/22 14:50
 */
@Data
public class OpponentVo {

    /**
     * 用户头像
     */
    private String weChatHeadImgUrl;

    /**
     * 用户名称
     */
    private String weChatHickName;

    /**
     * 用户等级
     */
    private Integer userInfoGrade;

    /**
     * 用户声望
     */
    private Integer userInfoRenown;
}
