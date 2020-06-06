package com.dkm.produce.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/30 9:11
 */
@Data
public class UserAttendantGoods {

    /**
     * 产出表id
     */
    private Long id;

    /**
     * 物品数量
     */
    private Integer number;

    /**
     * 物品名字
     */
    private String name;

    /**
     * 物品图片
     */
    private String url;

    /**
     * 跟班名称
     */
    private String atName;

    /**
     * 跟班图片
     */
    private String atImg;

}
