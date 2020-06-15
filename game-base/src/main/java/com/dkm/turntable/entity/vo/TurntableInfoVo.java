package com.dkm.turntable.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: game_project
 * @description: 转盘物品表
 * @author: zhd
 * @create: 2020-06-11 16:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurntableInfoVo {

    private Integer number;
    private String url;
    private String name;

}
