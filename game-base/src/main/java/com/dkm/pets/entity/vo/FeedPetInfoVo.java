package com.dkm.pets.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: game_project
 * @description: 喂食接受信息
 * @author: zhd
 * @create: 2020-05-16 21:16
 **/
@Data
public class FeedPetInfoVo{
    /**
     * 宠物等级
     */
    public Integer pGrade;
    /**
     * 宠物id
     */
    public Long pId;
    /**
     * userId
     */
    public Long userId;
}
