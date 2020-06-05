package com.dkm.family.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @program: game_project
 * @description: 家族工资记录表
 * @author: zhd
 * @create: 2020-06-03 21:05
 **/
@Data
@TableName("tb_family_wage")
public class FamilyWageEntity {
    @TableId
    private Long familyWageId;
    /*
     * 领取时间
     */
    private LocalDate familyWageTime;
    /*
     * userId
     */
    private Long userId;
    /*
     * 第一天工资
     */
    private Integer day1;
    /*
     * 第二天工资
     */
    private Integer day2;
    /*
     * 第三天工资
     */
    private Integer day3;
    /*
     * 最后一天工资
     */
    private Integer day4;
}
