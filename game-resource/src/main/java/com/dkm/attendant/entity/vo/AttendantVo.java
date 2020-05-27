package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/27 16:22
 */
@Data
public class AttendantVo {

    /**
     * 我方血量
     */
    private Integer myHealth;

    /**
     * 他方血量
     */
    private Integer otherHealth;

    /**
     * 0--me
     * 1--other
     */
    private Integer status;

    /**
     * me战斗力
     */
    private Integer myCapabilities;

    /**
     * other战斗力
     */
    private Integer otherForce;
}
