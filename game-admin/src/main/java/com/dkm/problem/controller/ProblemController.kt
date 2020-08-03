package com.dkm.problem.controller

import com.dkm.problem.mapper.vo.ProblemVo
import com.dkm.problem.service.IProblemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

@Api(tags = ["问答相关"])
@RestController
@RequestMapping("/admin/config/problem")
class ProblemController {
    @Resource
    private lateinit var problemService: IProblemService

    @ApiOperation("获取所有问答")
    @GetMapping("/getAllProblem", produces = ["application/json"])
    fun getAllProblems() = problemService.getAllProblems()

    @ApiOperation("添加一個问答")
    @PostMapping("/add", produces = ["application/json"])
    fun insertProblem(@RequestBody problemVo: ProblemVo) = problemService.insert(problemVo)

    @ApiOperation("更新一个问答")
    @PostMapping("/{id}/update", produces = ["application/json"])
    fun updateProblem(@RequestBody problemVo: ProblemVo, @PathVariable id: Long) = problemService.update(id, problemVo)

    @ApiOperation("删除一个问答")
    @GetMapping("/{id}/delete", produces = ["application/json"])
    fun deleteProblem(@PathVariable id: Long) = problemService.delete(id)

}