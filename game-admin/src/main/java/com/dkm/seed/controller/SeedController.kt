package com.dkm.seed.controller

import com.dkm.seed.mapper.vo.SeedVo
import com.dkm.seed.service.ISeedService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

@Api(tags = ["种子相关"])
@RestController
@RequestMapping("/admin/config/seed")
class SeedController {
    @Resource
    private lateinit var seedService: ISeedService

    @ApiOperation("获取所有种子")
    @GetMapping("/getAllSeed", produces = ["application/json"])
    fun getAllProblems() = seedService.getAll()

    @ApiOperation("添加一個种子")
    @PostMapping("/add", produces = ["application/json"])
    fun insertProblem(@RequestBody seedVo: SeedVo) = seedService.insert(seedVo)

    @ApiOperation("更新一个种子")
    @PostMapping("/{id}/update", produces = ["application/json"])
    fun updateProblem(@RequestBody seedVo: SeedVo, @PathVariable id: Long) = seedService.update(id, seedVo)

    @ApiOperation("删除一个种子")
    @GetMapping("/{id}/delete", produces = ["application/json"])
    fun deleteProblem(@PathVariable id: Long) = seedService.delete(id)

}