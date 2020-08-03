package com.dkm.problem.mapper.bo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tb_problem")
data class ProblemEntity(
        @TableId
        val id: Long,
        val problemSubject: String,
        val problemAnswerA: String?,
        val problemAnswerB: String?,
        val problemAnswerC: String?,
        val problemAnswerD: String?,
        val problemAnswer: String?


)
