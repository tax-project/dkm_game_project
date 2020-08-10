package com.dkm.problem.controllers

import com.dkm.config.annon.CheckAdminPermission
import com.dkm.problem.entities.vo.ProblemVo
import com.dkm.problem.services.IProblemService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

@Api(tags = ["问答相关"])
@RestController
@RequestMapping("/config/problem")
class ProblemController {
    @Resource
    private lateinit var problemService: IProblemService

    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckAdminPermission
    @ApiOperation("获取所有问答")
    @GetMapping("/getAllProblem", produces = ["application/json"])
    fun getAllProblems() = problemService.getAllProblems()


    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")    @ApiOperation("添加一個问答")
    @PostMapping("/add", produces = ["application/json"])
    fun insertProblem(@RequestBody problemVo: ProblemVo) = problemService.insert(problemVo)


    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")    @ApiOperation("更新一个问答")
    @PostMapping("/{id}/update", produces = ["application/json"])
    fun updateProblem(@RequestBody problemVo: ProblemVo, @PathVariable id: Long) = problemService.update(id, problemVo)


    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")    @ApiOperation("删除一个问答")
    @GetMapping("/{id}/delete", produces = ["application/json"])
    fun deleteProblem(@PathVariable id: Long) = problemService.delete(id)

}