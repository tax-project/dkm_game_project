package com.dkm.problem.entities.vo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty


@ApiModel("問題")
data class ProblemVo(
        @ApiModelProperty("id")
        val id: String?,
        @ApiModelProperty("問題標題")
        val problemSubject: String,
        @ApiModelProperty("問題答案 A")
        val problemAnswerA: String?,
        @ApiModelProperty("問題答案 B")
        val problemAnswerB: String?,
        @ApiModelProperty("問題答案 C")
        val problemAnswerC: String?,
        @ApiModelProperty("問題答案 D")
        val problemAnswerD: String?,
        @ApiModelProperty("正確答案")
        val problemAnswer: String?

)
