package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/29 10:57
 */
@Data
public class UserLandUnlock {
    private long userId ;
    /**
     * 土地编号
     */
    private long laNo ;
    /**
     * 状态
     */
    private Integer laStatus;
    /**
     * 土地名字
     */
    private String laName;
}
