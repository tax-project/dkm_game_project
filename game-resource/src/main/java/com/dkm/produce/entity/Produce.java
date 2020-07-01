package com.dkm.produce.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 21:22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_produce")
public class Produce extends Model<Produce> {
    /**
     * 产出id
     */
    private Long id;

    /**
     *  跟班用户id
     */
    private Long attUserId;

    /**
     * 跟班id
     */
    private Long attendantId;

    /**
     * 物品id
     */
    private Long goodId;
    /**
     * 数量
     */
    private Integer number;

    /**
     * 默认--0  未收取
     * 1--已收取
     */
    private Integer status;
}
