package com.dkm.produce.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 17:18
 */
@Data
public class AttendantPutVo {

    /**
     * 数量
     */
    private Integer number;

    /**
     * 物品ID
     */
    private Long goodId;

    /**
     * 物品名字
     */
    private String goodName;

    /**
     * 物品图片
     */
    private String imgUrl;

    /**
     *  跟班id
     */
    private Long attendantId;

    /**
     *  被抓人id
     */
    private Long caughtPeopleId;
}
