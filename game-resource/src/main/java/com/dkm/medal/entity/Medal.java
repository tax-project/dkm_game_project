package com.dkm.medal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 15:44
 */
@Data
@TableName("tb_medal")
public class Medal {
    /**
     * 勋章ID
     */
    private long medalId;
    /**
     *用户ID
     */
    private Integer medalUserId;
    /**
     *勋章名称
     */
    private String medalName;
    /**
     *勋章图片
     */
    private String medalImage;
    /**
     *勋章星级
     */
    private Integer medalLevel;
    /**
     *当前进度
     */
    private Integer medalProcess;
    /**
     *总进度
     */
    private Integer medalAllProcess;
}
