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
    @TableId
    private long atuId;

    private Long attendantId;

    private Long userId;

    private Long caughtPeopleId;

    private long exp1;

}
