package com.dkm.bill.controller;

import com.dkm.bill.entity.Bill;
import com.dkm.bill.entity.vo.BillVo;
import com.dkm.bill.service.IBillService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:55
 */
@Api(tags = "账单api")
@RestController
@RequestMapping("/BillController")
public class BillController {

    @Autowired
    private IBillService iBillService;

    @ApiOperation(value = "查询账单", notes = " 查询账单")
    @PostMapping("/queryAllBill")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "bType", value = "1(金币) 2(钻石) 3(财富卷)"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "bIncomeExpenditure", value = "1收入 2支出")
    }
    )
    @CrossOrigin
    @CheckToken
    public List<Bill> queryAllBill(@RequestBody BillVo vo){
       return iBillService.queryAllBill(vo);
    }
}
