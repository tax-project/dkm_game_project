package com.dkm.pets.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhd
 * @date 2020/5/9 9:05
 */
@Data
@TableName("tb_pet_user")
public class PetUserEntity {
    @TableId
    private Long pId;
    /**
     * 用户id
     * */
    private Long userId;
    /**
     * 宠物等级
     * */
    private Integer pGrade;
    /**
     * 喂食进度
     * */
    private Integer pNowFood;
    /**
     * 宠物id
     * */
    private Long petId;
}
