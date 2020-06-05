package com.dkm.attendant.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/20 11:00
 */
@Data
@TableName("tb_attendant_user")
public class AttendantUser extends Model<AttendantUser> {

    /**
     * 跟班用户id
     */
    @TableId
    private long atuId;

    /**
     * 跟班id
     */
    private Long attendantId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 被抓人id
     */
    private Long caughtPeopleId;

    /**
     * 时间
     */
    private long exp1;

}
