package com.dkm.event.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:03
 */
@Data
@TableName("tb_event")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Event extends Model<Event> {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 自己用户id
     */
    private Long userId;

    /**
     * 时间
     */
    private LocalDateTime evTime;

    /**
     * 事件内容 （里）
     */
    private String evMsgIn;

    /**
     * 事件内容 （外）
     */
    private String evMsgExternal;

    /**
     * 状态
     */
    private Integer evStatus;
    /**
     * 他人的用户id
     */
    private Long heUserId;
}
