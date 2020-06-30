package com.dkm.seed.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;
import com.dkm.seed.service.ISeedFallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 16:18
 */
@Api(tags = "种子掉落金币红包Api")
@RestController
@RequestMapping("/SeedsFallController")
public class SeedsFallController {

    @Autowired
    private ISeedFallService iSeedFallService;


    /**
     * 种子掉落
     * 金币红包掉落
     */
    @ApiOperation(value = "种子掉落（金币红包掉落）", notes = "种子掉落（金币红包掉落）")
    @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "seedGrade", value = "种子等级")
    @PostMapping("/seedDrop")
    @CrossOrigin
    @CheckToken
    public List<GoldOrMoneyVo> seedDrop(@RequestParam(value = "seedGrade") Integer seedGrade){
        if (seedGrade == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedFallService.seedDrop();
    }



    /**
     * 单独掉落红包
     */
    @ApiOperation(value = "单独掉落红包", notes = "单独掉落红包")
    @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "money", value = "种子首次产出的红包金额")
    @PostMapping("/redBagDroppedSeparately")
    @CrossOrigin
    @CheckToken
    List<Double> redBagDroppedSeparately(@RequestParam(value = "money") Double money){
        if (money == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return iSeedFallService.redBagDroppedSeparately(money);
    }


    /**
     * 查询已掉落的数据
     */
    @ApiOperation(value = "查询已掉落的数据", notes = "查询已掉落的数据")
    @PostMapping("/queryDroppedItems")
    @CrossOrigin
    @CheckToken
    List<SeedsFallVo> queryDroppedItems(){
        return iSeedFallService.queryDroppedItems();
    }

    @ApiOperation(value = " 修改种子状态为2 待收取", notes = " 修改种子状态为2 待收取")
    @PostMapping("/updateLeStatus")
    @CrossOrigin
    @CheckToken
    public int updateLeStatus(@RequestParam(value = "id[]")Integer[] id){

        List<Long> list=new ArrayList<>();

        for (int i = 0; i < id.length; i++) {
            list.add(Long.valueOf(id[i]));
        }
        return iSeedFallService.updateLeStatus(list);
    }


}
