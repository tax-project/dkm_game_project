package com.dkm.event.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/9 20:09
 */
@Data
@TableName("tb_user_event")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserEvent extends Model<UserEvent> {

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
    private LocalDateTime eventTime;

    /**
     * 他人的用户id
     */
    private Long heUserId;
}
