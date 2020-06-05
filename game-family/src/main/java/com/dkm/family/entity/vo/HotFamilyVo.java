package com.dkm.family.entity.vo;

import com.dkm.family.entity.FamilyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @program: game_project
 * @description: 热门家族
 * @author: zhd
 * @create: 2020-06-03 16:40
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class HotFamilyVo extends FamilyEntity {
    /**
     * 头像
     */
    private String headImg;
}
