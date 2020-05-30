package com.dkm.land.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/29 14:37
 */
@Data
public class UserLandUnlock {
    private long userId ;
    /**
     * 状态
     */
    private Integer laStatus;
    /**
     * 土地编号
     */
    private Integer laNo;
    /**
     * 土地名字
     */
    private String laName;
}
