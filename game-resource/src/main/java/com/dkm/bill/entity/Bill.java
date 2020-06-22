package com.dkm.bill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:37
 */
@Data
@TableName("tb_bill")
public class Bill extends Model<Bill> {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 服务名称
     */
    private String bName;

    /**
     * 数量
     */
     private Integer bNumber;

    /**
     * 时间
     */
    private LocalDateTime bTime;

    /**
     * 1(金币) 2(钻石) 3(财富卷)
     */
    private Integer bType;

    /**
     * 1收入 2支出
     */
    private Integer bIncomeExpenditure;

    /**
     * 0交易失败 1交易成功
     */
    private Integer bIsSuccess;

    /**
     * 从数据库取出来转换的时间
     */
    private String time;


}
