package com.dkm.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:31
 */
@Data
@TableName("tb_integral")
public class Integral {
    /**
     *积分主键
     */
    private long iId;
    /**
     *名称
     */
    private String iProductName;
    /**
     *图片
     */
    private String iImg;
    /**
     *能得到的产品数量
     */
    private Integer iProductNumber;
    /**
     *需要消耗的积分
     */
    private Integer iIntegralNumber;

}
