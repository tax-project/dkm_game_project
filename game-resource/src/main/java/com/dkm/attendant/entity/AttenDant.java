package com.dkm.attendant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 13:57
 */
@Data
@TableName("tb_attendant")
public class AttenDant {
    /**
     *跟班主键
     */
    private long id;
    /**
     *用户id
     */
    private long userId;
    /**
     *物品主键
     */
    private long gId;
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
    private String exp1;
    /**
     *
     */
    private String exp2;
    /**
     *
     */
    private String exp3;

    /**
     *被抓人id
     */
    private Long caughtPeopleId;
}
