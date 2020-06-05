package com.dkm.medal.entity.vo;

import com.dkm.medal.entity.MedalEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: game_project
 * @description: 用户勋章详情
 * @author: zhd
 * @create: 2020-06-05 15:19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MedalUserInfoVo extends MedalEntity {
    /**
     * 当前进度
     */
    private Integer process;
    /**
     * 当前等级
     */
    private Integer medalLevel;
    /**
     * 送出礼物名称
     */
    private String giName;
    /**
     * 勋章图标
     */
    private String medalImage;
}
