package com.dkm.mounts.entity.dto;

import com.dkm.mounts.entity.MountsDetailEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description: 装备详情等
 * @author: zhd
 * @create: 2020-05-19 09:58
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MountsDetailDto extends MountsDetailEntity {
    /**
     * 装备状态
     */
    private Integer equip;
    /**
     * id
     */
    private Long id;
    /**
     * 是否拥有
     */
    private Integer buy;
}
