package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 14:26
 */
@Data
public class AttendantUsersVo {
    /**
     *跟班主键
     */
    private long aId;
    /**
     *跟班图片
     */
    private String atImg;
    /**
     *跟班名称
     */
    private String atName;
    /**
     *食物主键
     */
    private long foodId;
    /**
     *跟班一天生产的时间
     */
    private long exp1;
    /**
     * 被抓人id
     */
    private Long caughtPeopleId;
}
