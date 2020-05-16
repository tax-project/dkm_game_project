package com.dkm.pets.entity.dto;

import com.dkm.pets.entity.PetUserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/9 11:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PetsDto extends PetUserEntity {
    /**
     * 宠物名称
     * */
    private String petName;
    /**
     * 宠物图片地址
     * */
    private String petUrl;
    /**
     * 喂食进度
     * */
    private Double schedule;
    /**
    * 所需喂食
    * */
    private List<EatFoodDto> eatFood;
}
