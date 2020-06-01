package com.dkm.attendant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class AttendantUser {
    @Id
    private long atuId;
    private Long aId;
    private Long userId;
    private Long caughtPeopleId;
    private long exp1;

}
