package com.dkm.problem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_score")
public class Score extends Model<Score> {

   private Long id;

   /**
    * 用户id（谁答的题）
    */
   private Long userId;

   /**
    * 红包ID（谁发的红包）
    */
   private Long moneyId;

   /**
    * 积分
    */
   private Integer score;

   /**
    * 收的红包
    */
   private Double price;

   /**
    * 创建时间
    */
   private LocalDateTime createDate;
}
