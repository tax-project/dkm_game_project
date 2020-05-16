package com.dkm.pets.entity.vo;

import lombok.Data;

/**
 * @program: game_project
 * @description: 喂食接受信息
 * @author: zhd
 * @create: 2020-05-16 21:16
 **/
@Data
public class FeedPetInfoVo {
    /**
     * 宠物等级
     */
    private Long pGrade;
    /**
     * 宠物id
     */
    private Long pId;
    /**
     * userid
     */
    private Long userId;
    /**
     * 背包id
     */
    private Long tekId;
}
