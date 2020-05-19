package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/18 19:08
 */
@Data
public class UserPet {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     *用户头像
     */
    private String weChatHeadImgUrl;
    /**
     *用户名称
     */
    private String weChatNickName;
    /**
     *用户声望
     */
    private Integer userInfoRenown;
    /**
     *宠物名称
     */
    private String petName;
    /**
     *宠物图片
     */
    private String petUrl;
    /**
     *宠物等级
     */
    private Integer pGrade;
}
