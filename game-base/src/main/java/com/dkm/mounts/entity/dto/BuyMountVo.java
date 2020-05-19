package com.dkm.mounts.entity.dto;

import lombok.Data;

/**
 * @program: game_project
 * @description: 购买座驾vo
 * @author: zhd
 * @create: 2020-05-19 14:10
 **/
@Data
public class BuyMountVo {
    public Long mountId;
    public Integer gold;
    public Integer diamond;
}
