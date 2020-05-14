package com.dkm.turntable.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/10 11:10
 * @Version: 1.0V
 */
@Data
@TableName("tb_turntable")
public class Turntable {
    /**
     * 转盘物品ID
     */
    private Long turntableId;
    /**
     * 当前物品对应的用户等级数 1~10为1 11~20为2 以此类推
     */
    private Integer turntableUserLevel;
    /**
     * 转盘槽1~6的物品
     */
    private Long turntableSlotOneId;
    private Long turntableSlotTwoId;
    private Long turntableSlotThreeId;
    private Long turntableSlotFourId;
    private Long turntableSlotFiveId;
    private Long turntableSlotSixId;
}
