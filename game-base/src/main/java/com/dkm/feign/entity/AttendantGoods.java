package com.dkm.feign.entity;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/20 9:08
 */
@Data
public class AttendantGoods {
   /**
    *跟班图片
     */
    private String atImg;
    /**
     *跟班名称
     */
    private String atName;
    /**
     *跟班一天生产的时间
     */
    private String exp1;

    private Long id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品图片地址
     */
    private String url;
}
