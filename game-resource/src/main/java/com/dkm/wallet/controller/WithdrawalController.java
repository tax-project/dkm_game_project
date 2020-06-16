package com.dkm.wallet.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.land.entity.vo.Message;
import com.dkm.wallet.service.IWithdrawalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 16:33
 */
@Api(tags = "提现api")
@RestController
@RequestMapping("/WithdrawalController")
public class WithdrawalController {

    @Autowired
    private IWithdrawalService iWithdrawalService;



    /**
     *  查询所有提现数据
     */
    @ApiOperation(value = " 查询所有提现数据", notes = " 查询所有提现数据")
    @PostMapping("/queryAllWithdrawalData")
    @CrossOrigin
    @CheckToken
   public Map<String,Object> queryAllWithdrawalData(){
       return iWithdrawalService.queryAllWithdrawalData();
   }


    /**
     * 提现
     */
    @ApiOperation(value = " 提现", notes = " 查询所有提现数据")
    @PostMapping("/withdrawal")
    @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "id")
    @CrossOrigin
    @CheckToken
    public Message withdrawal(@RequestParam("id") Long id){
      return iWithdrawalService.withdrawal(id);
    }
}
