package com.dkm.feign.entity;

import lombok.Data;

/**
 * @author zhd
 * @date 2020/5/9 11:20
 */
@Data
public class PetsDto {
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
}
