package com.dkm.goods.controllers


import com.dkm.config.annon.CheckAdminPermission
import com.dkm.goods.entities.vo.GoodsVo
import com.dkm.goods.services.IGoodService
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
@RequestMapping("/config/goods")
class GoodsController {
    @Resource
    private lateinit var iGoodService: IGoodService


    @CheckAdminPermission
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @ApiOperation("获取所有物品列表",notes = "通过此接口来查询所有的基础物品表")
    @GetMapping("/getAllGoods", produces = ["application/json"])
    fun getAllGoods() = iGoodService.getAllGoods()

    @CheckAdminPermission
    @ApiOperation("添加一个物品")
    @PostMapping("/add", consumes = ["application/json"], produces = ["application/json"])
    fun addGoods(@RequestBody goods: GoodsVo) = iGoodService.addGoodsItem(goods)

    @CheckAdminPermission
    @ApiOperation("更新一个物品")
    @PostMapping("/{id}/update", consumes = ["application/json"], produces = ["application/json"])
    fun updateGoods(@ApiParam("物品ID，仅针对现有的物品")@PathVariable id: Long, @RequestBody goods: GoodsVo ) = iGoodService.updateItemById(id,goods)

    @CheckAdminPermission
    @ApiOperation("删除一个物品")
    @GetMapping("/{id}/delete", consumes = ["application/json"], produces = ["application/json"])
    fun deleteGoodsById(@ApiParam("物品ID，仅针对现有的物品") @PathVariable id: String) = iGoodService.deleteItemById(id)
}