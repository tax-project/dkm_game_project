package com.dkm.attendant.entity;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 9:15
 */
@Data
public class ProductionSchedule {
    /**
     * 跟班生产主键id
     */
    private Long psId;
    /**
     * 跟班id
     */
    private Integer id;
    /**
     * 跟班生产名称
     */
    private String psName;
    /**
     * 跟班生产图片
     */
    private String psUrl;
    /**
     * 数量
     */
    private Integer psNum;
}
