package com.dkm.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 20:10
 */
@Data
@TableName("tb_stars")
public class Stars {

    /**
     * 星星id
     */
    private long sId;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 图片
     *
     */
    private String sImg;
    /**
     * 星星类型
     */
    private Integer sStar;
    /**
     * 当前拥有的数来
     */
    private Integer sCurrentlyHasNum;
    /**
     * 需要消耗数量
     */
    private Integer sTotalConsumedQuantity;
}
