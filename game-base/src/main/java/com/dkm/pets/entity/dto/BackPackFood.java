package com.dkm.pets.entity.dto;

import lombok.Data;

/**
 * @program: game_project
 * @description: 背包食物信息
 * @author: zhd
 * @create: 2020-05-15 11:30
 **/
@Data
public class BackPackFood {
    private Long id;
    private String foodName;
    private String foodUrl;
    private Integer foodGold;
    private Integer foodNumber;
}
