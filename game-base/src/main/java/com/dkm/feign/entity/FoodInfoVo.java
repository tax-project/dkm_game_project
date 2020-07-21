package com.dkm.feign.entity;

import lombok.Data;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-20 15:15
 **/
@Data
public class FoodInfoVo {
    private Long foodId;
    private String url;
    private String name;
    private Integer foodNumber;
}
