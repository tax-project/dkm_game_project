package com.dkm.attendant.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:37
 */
@Data
@TableName("tb_user_info")
public class User {
    /**
     * 用户id
     */
    private long userId;
    /**
     *用户声望
     */
    private Integer userInfoRenown;
    /**
     *用户金币
     */
    private Integer userInfoGold;
}
