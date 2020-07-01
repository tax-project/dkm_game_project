package com.dkm.apparel.entity.vo;

import com.dkm.apparel.entity.ApparelOrderEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-22 15:15
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApparelOrderVo extends ApparelOrderEntity {
    private String apparelUrl;
    private String apparelName;
    private String time;
}
