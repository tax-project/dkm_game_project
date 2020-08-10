package com.dkm.skill.controllers

import com.dkm.config.annotations.CheckAdminPermission
import com.dkm.skill.entities.vo.SkillVo
import com.dkm.skill.services.ISkillService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

@Api(tags = ["技能相关"])
@RestController
@RequestMapping("/config/skill")
class SkillController {
    @Resource
    private lateinit var skinService: ISkillService

    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @ApiOperation("获取所有技能")
    @GetMapping("/getAllSkins", produces = ["application/json"])
    fun getAllProblems() = skinService.getAll()

    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @ApiOperation("添加一個技能")
    @PostMapping("/add", produces = ["application/json"])
    fun insertProblem(@RequestBody skillVo: SkillVo) = skinService.insert(skillVo)

    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @ApiOperation("更新一个技能")
    @PostMapping("/{id}/update", produces = ["application/json"])
    fun updateProblem(@RequestBody skillVo: SkillVo, @PathVariable id: Long) = skinService.update(id, skillVo)

    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @ApiOperation("删除一个技能")
    @GetMapping("/{id}/delete", produces = ["application/json"])
    fun deleteProblem(@PathVariable id: Long) = skinService.delete(id)

}