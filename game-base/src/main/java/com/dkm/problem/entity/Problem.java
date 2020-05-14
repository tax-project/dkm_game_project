package com.dkm.problem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author qf
 * @date 2020/5/8
 * @vesion 1.0
 **/
@TableName("tb_problem")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class Problem extends Model<Problem> {

   private Long id;

   /**
    * 问题
    */
   private String problemSubject;

   /**
    * 答案A
    */
   private String problemAnswerA;

   /**
    * 答案B
    */
   private String problemAnswerB;

   /**
    * 答案C
    */
   private String problemAnswerC;

   /**
    * 答案D
    */
   private String problemAnswerD;

   /**
    * 正确答案
    */
   private String problemAnswer;
}
