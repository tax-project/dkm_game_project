package com.dkm.family.entity.vo;

import lombok.Data;

/**
 * @program: game_project
 * @description: 家族成员信息
 * @author: zhd
 * @create: 2020-06-02 20:29
 **/
@Data
public class FamilyUsersVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 家族等级 0 普通 1管理员 2 族长
     */
    private Integer isAdmin;
    /**
     * 用户昵称
     */
    private String weChatNickName;
    /**
     * 用户头像
     */
    private String weChatHeadImgUrl;
}
