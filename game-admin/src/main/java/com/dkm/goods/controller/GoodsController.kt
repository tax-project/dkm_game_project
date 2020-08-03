package com.dkm.goods.controller


import com.dkm.goods.mapper.vo.GoodsVo
import com.dkm.goods.service.IGoodService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

/**
 * @author
 */
@Api(tags = ["物品相关"])
@RestController
@RequestMapping("/admin/config/goods")
class GoodsController {
    @Resource
    private lateinit var iGoodService: IGoodService

    @ApiOperation("获取所有物品")
    @GetMapping("/getAllGoods", produces = ["application/json"])
    fun getAllGoods() = iGoodService.getAllGoods()

    @ApiOperation("添加一个物品")
    @PostMapping("/add", consumes = ["application/json"], produces = ["application/json"])
    fun addGoods(@RequestBody goods: GoodsVo) = iGoodService.addGoodsItem(goods)

    @ApiOperation("更新一个物品")
    @PostMapping("/{id}/update", consumes = ["application/json"], produces = ["application/json"])
    fun updateGoods(@ApiParam("物品ID，仅针对现有的物品")@PathVariable id: Long, @RequestBody goods: GoodsVo ) = iGoodService.updateItemById(id,goods)

    @ApiOperation("删除一个物品")
    @GetMapping("/{id}/delete", consumes = ["application/json"], produces = ["application/json"])
    fun deleteGoodsById(@ApiParam("物品ID，仅针对现有的物品") @PathVariable id: String) = iGoodService.deleteItemById(id)
}