package com.dkm.union.entity.vo;

import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-18 11:23
 **/
@Data
public class UnionUserVo {

    private Long userId;
    private String  weChatHeadImgUrl;
    private  String weChatNickName;
    private Integer level;
}
