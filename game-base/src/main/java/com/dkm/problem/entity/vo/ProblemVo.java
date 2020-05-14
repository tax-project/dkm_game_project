package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Data
public class ProblemVo {


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
