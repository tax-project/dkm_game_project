package com.dkm.skill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/4 15:56
 */
@Data
@TableName("tb_stars")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Stars extends Model<Stars> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 星星类型
     */
    private Integer sStar;

    /**
     *  当前拥有的数量
     */
    private Integer sCurrentlyHasNum;

    /**
     * 需要消耗数量
     */
   private Integer sTotalConsumedQuantity;
}
