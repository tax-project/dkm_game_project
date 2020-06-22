package com.dkm.land.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:土地
 * @USER: 刘梦祺
 * @DATE: 2020/5/9 9:21
 */
@Data
@TableName("tb_land")
public class Land {
    /**
     * 土地主键
     */
    private Long laId;
    /**
     * 土地编号
     */
    private Integer laNo;
    /**
     * 土地名字
     */
    private String laName;
    /**
     * 土地状态
     */
    private Integer laStatus;

}
